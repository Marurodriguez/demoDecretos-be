package com.sm.admDecretos.Core.Service;

import com.sm.admDecretos.Core.Entity.Db.*;
import com.sm.admDecretos.Core.Repository.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Base64;

@Service
public class SyncService extends AbstractService<Archivo> {
    @Autowired
    ArchivoRepository archivoRepository;

    @Autowired
    EncodeService encodeService;

    @Autowired
    DependenciaRepository dependenciaRepository;

    @Autowired
    CentroDigitalizacionRepository centroDigitalizacionRepository;

    @Autowired
    DocumentoTipoRepository documentoTipoRepository;

    @Autowired
    DigitalizadorRepository digitalizadorRepository;

    @Autowired
    DocumentoRepository documentoRepository;

    @Autowired
    ArchivoTipoRepository archivoTipoRepository;

    @Autowired
    ArchivoDataRepository archivoDataRepository;

    @Value("${SYNC_FOLDER}")
    public String syncFolder;

    @Value("${SYNC_ENABLED}")
    public String syncEnabled;

    /**
     * Se usa para crear documentos y subir archivos
     *
     * @return
     */

    @Scheduled(initialDelay = 5000, fixedRate = 12 * 1000 * 60 * 60)
    public void syncDocuments() {
        try {
            System.out.println("syncDocuments start");
            if (this.syncEnabled.equals("enable")) {
                int dirIndex = 0;
                String[] folder_meses = getSubDirs(this.syncFolder);
                for (String folder_mes : folder_meses) {
                    String[] folder_dias = getSubDirs(this.syncFolder + "\\" + folder_mes);
                    for (String folder_dia : folder_dias) {
                        String[] folder_pcs = getSubDirs(this.syncFolder + "\\" + folder_mes + "\\" + folder_dia);
                        for (String folder_pc : folder_pcs) {
                            String[] folder_documentos = getSubDirs(this.syncFolder + "\\" + folder_mes + "\\" + folder_dia + "\\" + folder_pc);
                            for (String folder_doc : folder_documentos) {
                                dirIndex++;
                                System.out.println("CARPETA: " + folder_mes + "/" + folder_dia + "/" + folder_pc + "/" + folder_doc +
                                        " (" + dirIndex + " de " + folder_documentos.length + ")");
                                this.createDocumentAndUpload(this.syncFolder + "\\" + folder_mes + "\\" + folder_dia + "\\" + folder_pc + "\\" + folder_doc);
                            }
                        }
                    }
                }
            }
        }catch (Exception ex){

        }

    }

    public boolean createDocumentAndUpload(String path) {
        try {
            File file = new File(path + "\\data.xml");
            //an instance of builder to parse the specified xml file

            String content = getFileContent(file.getAbsolutePath());
            String sPrefijo = getValueNode(content, "exp_prefijo");
            String sNumero = getValueNode(content, "exp_numero");
            String sAnio = getValueNode(content, "exp_anio");
            String sMatricula = getValueNode(content, "matricula");
            String sAlcance = getValueNode(content, "exp_alcance");
            String sCaratula = getValueNode(content, "caratula");
            String sPcId = getValueNode(content, "pc_id");

            // no se usan
            //String sId = getValueNode(content,"id");
            //String sElaboracion = getValueNode(content,"elaboracion");
            //String sFechaDigitalizacion = getValueNode(content,"date_created");

            // Dependencia Hardcode
            Dependencia dependencia = this.dependenciaRepository.findFirstByIdEquals(1);
            CentroDigitalizacion centroDigitalizacion = this.centroDigitalizacionRepository.findFirstByIdEquals(1);
            DocumentoTipo documentoTipo = this.documentoTipoRepository.findFirstByIdEquals(1);
            Digitalizador digitalizador = this.digitalizadorRepository.findFirstByIdEquals(1);

            // Revisa si existe
            Documento documento = this.documentoRepository.findFirstByPrefijoEqualsAndNumeroExpedienteEqualsAndAnioEqualsAndAlcanceEqualsAndStatusEquals(Integer.parseInt(sPrefijo),
                    Integer.parseInt(sNumero), Integer.parseInt(sAnio), Integer.parseInt(sAlcance), 0);

            // Una vez grabado el documento Sube todos los archivos y los asocia a él
            // SOLO AGREGA CUANDO ES NUEVO!!! OJOOOO
            if (documento == null) {
                documento = new Documento();
                documento.setDependencia(dependencia);
                documento.setCentroDigitalizacion(centroDigitalizacion);
                documento.setDocumentoTipo(documentoTipo);
                documento.setDigitalizador(digitalizador);
                documento.setNumeroExpediente(Integer.parseInt(sNumero));
                documento.setPrefijo(Integer.parseInt(sPrefijo));
                documento.setAnio(Integer.parseInt(sAnio));
                documento.setMatricula(Integer.parseInt(sMatricula));
                documento.setCaratula(sCaratula);
                documento.setAlcance(Integer.parseInt(sAlcance));
                documento.setPcId(Long.parseLong(sPcId));
                documento.setFechaDigitalizacion(new Timestamp(System.currentTimeMillis()));
                this.documentoRepository.save(documento);
                this.uploadArchivos(path, documento);
            }
        } catch (Exception ex) {
            System.out.println("Error en la funcion testCreateDocumentos:");
            System.out.println(ex.getMessage());
        }
        return true;
    }

    public boolean uploadArchivos(String pathOrigen, Documento documento) {
        String path = pathOrigen + "\\Imagenes";
        File folder = new File(path);
        File[] files = folder.listFiles();
        int orden = 0;
        for (File file : files) {
            orden++;
            String filename = file.getAbsolutePath();
            String encode = this.encodeFile(filename);
            long fileSize = 0;

            try {
                fileSize = Files.size(file.toPath());
            } catch (Exception ex) {
                fileSize = 0;
            }

            // Imagen Grande>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>!
            // Archivo TIPO = 1 -> Imagen
            ArchivoTipo archivoTipo = archivoTipoRepository.findFirstByIdEquals(1);
            Archivo archivo = new Archivo();
            archivo.setArchivoTipo(archivoTipo);
            archivo.setOrden(orden);

            archivo.setExtension("jpg");
            archivo.setDocumento(documento);
            archivo.setDateCreated(new Timestamp(System.currentTimeMillis()));

            // Guardar la Data del archivo
            ArchivoData archivoData = new ArchivoData();
            archivoData.setData(encode);
            archivoData.setFileSize(fileSize);
            archivoData.setEncriptado(1); //La información está encriptada!!
            archivoData = this.archivoDataRepository.save(archivoData);
            //-------------------------------->

            archivo.setArchivoData(archivoData.getId());
            this.archivoRepository.save(archivo);

            //FIN DE ARCHIVO GRANDE---------------------------------------------------------------->

            // PREVIEW!
            // Archivo TIPO = 2 -> PREVIEW
            archivoTipo = archivoTipoRepository.findFirstByIdEquals(2);
            archivo = new Archivo();
            archivo.setArchivoTipo(archivoTipo);
            archivo.setOrden(orden);

            archivo.setExtension("jpg");
            archivo.setDocumento(documento);
            archivo.setDateCreated(new Timestamp(System.currentTimeMillis()));

            // Guardar la Data del archivo

            archivoData = new ArchivoData();
            archivoData.setData(getFilePreview(file));
            archivoData.setFileSize(0);
            archivoData.setEncriptado(0); // EL PREVIEW NO ESTA ENCRIPTADO!!!
            archivoData = this.archivoDataRepository.save(archivoData);
            //-------------------------------->

            archivo.setArchivoData(archivoData.getId());
            this.archivoRepository.save(archivo);
        }
        return true;
    }

    public String getValueNode(String content, String node) {
        String nodeStart = "<" + node + ">";
        String nodeEnd = "</" + node + ">";
        try {
            return StringUtils.substringBetween(content, nodeStart, nodeEnd);
        } catch (Exception ex) {
            System.out.println("Error en la funcion getValueNode:");
            System.out.println(ex.getMessage());
            return "";
        }
    }

    /**
     * Obtiene el FilePreview de una imagen con un tamaño 4 veces menor y con un smooth
     * Lo obtiene en base64
     *
     * @param fileOriginal
     * @return
     */
    public String getFilePreview(File fileOriginal) {
        try {
            BufferedImage bufOriginal = ImageIO.read(fileOriginal);
            int width = bufOriginal.getWidth() / 4;
            int height = bufOriginal.getHeight() / 4;

            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            img.createGraphics().drawImage(bufOriginal.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", bos);
            byte[] data = bos.toByteArray();
            return Base64.getEncoder().encodeToString(data);
        } catch (Exception ex) {
            System.out.println("Error en la funcion getFilePreview:");
            System.out.println(ex.getMessage());
            return "";
        }
    }

    public String getFileContent(String filename) {
        try {
            Path path = Paths.get(filename);
            String contenido = new String(Files.readAllBytes(path));
            return contenido;
        } catch (Exception ex) {
            System.out.println("Error en la funcion getFileContent:");
            System.out.println(ex.getMessage());
            return "";
        }
    }

    public String[] getSubDirs(String parent) {
        File file = new File(parent);
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        return directories;
    }

    /**
     * Pasa a base64 y encripta el archivo
     *
     * @param filename ruta del archivo a encriptar
     * @return un string con el contenido encriptado
     * @throws Exception
     */
    public String encodeFile(String filename) {
        try {
            String content64 = encodeFileToBase64(new File(filename));
            String encode = this.encodeService.enc(content64);
            return encode;
        } catch (Exception ex) {
            System.out.println("Error en la funcion encodeFile, archivo " + filename + " :");
            System.out.println(ex.getMessage());
            return "";
        }
    }

    public String decodeFile(String filename) throws Exception {
        try {
            Path path = Paths.get(filename);
            String contenido = new String(Files.readAllBytes(path));
            String decode = this.encodeService.des(contenido);
            byte[] des64 = Base64.getDecoder().decode(decode);
            Files.write(path, des64);
        } catch (Exception ex) {
            System.out.println("Error en la funcion decodeFile, archivo " + filename + " :");
            System.out.println(ex.getMessage());
            return "";
        }
        return "";
    }

    private static String encodeFileToBase64(File file) {
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            throw new IllegalStateException("could not read file " + file, e);
        }
    }
}
