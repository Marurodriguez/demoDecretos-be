package com.sm.admDecretos.Core.Service;

import com.sm.admDecretos.Core.Entity.Db.*;
import com.sm.admDecretos.Core.Repository.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.stream.Collectors;

@Service
public class TestService extends AbstractService<Archivo> {
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

    /**
     * Se usa para crear documentos y subir archivos
     *
     * POR FAVOR NO TOCAR!!!!!
     *
     * @return
     */
    public boolean testCreateDocumentos() {
        boolean bAdd = false;
        // LO HARDCODEO YA QUE NECESITO ACTUALIZARLO
        this.syncFolder = "K:\\Documentos\\01\\PC_1";
        String[] dirs = getSubDirs(this.syncFolder);

        int dirIndex = 0;
        for (String dir : dirs) {
            dirIndex++;
            System.out.println("CARPETA: " + dir + " (" + dirIndex + " de " + dirs.length + ")");

            try {
                String path = this.syncFolder + "\\" + dir;
                File file = new File(path + "\\data.xml");
                //an instance of builder to parse the specified xml file
                bAdd =false;
                String content = getFileContent(file.getAbsolutePath());
                String sPrefijo = getValueNode(content, "exp_prefijo");
                String sNumero = getValueNode(content, "exp_numero");
                String sAnio = getValueNode(content, "exp_anio");
                String sMatricula = getValueNode(content, "matricula");
                String sAlcance = getValueNode(content, "exp_alcance");
                String sCaratula = getValueNode(content, "caratula");
                String sPcId = getValueNode(content, "pc_id");
                String dependenciaNombre = getValueNode(content, "dependencia_nombre");
                String tipoDocumentoNombre = getValueNode(content, "tipo_documento_nombre");
                /*<dependencia_nombre>DIR GARANTIAS Y DERECHOS HUMANOS</dependencia_nombre>
                <tipo_documento_nombre>INTERNOS - DIRECCION - DIR. GARANTIAS Y DERECHOS HUMANOS</tipo_documento_nombre>*/
                // no se usan
                //String sId = getValueNode(content,"id");
                //String sElaboracion = getValueNode(content,"elaboracion");
                //String sFechaDigitalizacion = getValueNode(content,"date_created");


                Dependencia dependencia = this.dependenciaRepository.findFirstByNombreEqualsAndStatusEquals(dependenciaNombre,0);
                if(dependencia == null) { //No existe.. Agregar dependencia
                    dependencia = new Dependencia();
                    dependencia.setCodigo("");
                    dependencia.setNombre(dependenciaNombre);
                    this.dependenciaRepository.save(dependencia);
                }
                DocumentoTipo documentoTipo = this.documentoTipoRepository.findFirstByNombreEqualsAndStatusEquals(tipoDocumentoNombre,0);
                if(documentoTipo == null) { //No existe.. Agregar dependencia
                    documentoTipo = new DocumentoTipo();
                    documentoTipo.setCodigo("");
                    documentoTipo.setNombre(tipoDocumentoNombre);
                    this.documentoTipoRepository.save(documentoTipo);
                }

                CentroDigitalizacion centroDigitalizacion = this.centroDigitalizacionRepository.findFirstByIdEquals(1);

                Digitalizador digitalizador = this.digitalizadorRepository.findFirstByIdEquals(1);

                // Revisa si existe
                Documento documento = this.documentoRepository.findFirstByPrefijoEqualsAndNumeroExpedienteEqualsAndAnioEqualsAndAlcanceEqualsAndStatusEquals(Integer.parseInt(sPrefijo),
                        Integer.parseInt(sNumero), Integer.parseInt(sAnio), Integer.parseInt(sAlcance), 0);

                // Una vez grabado el documento Sube todos los archivos y los asocia a él
                // SOLO AGREGA CUANDO ES NUEVO!!! OJOOOO
                bAdd = false;
                if (documento == null) {
                    documento = new Documento();
                    bAdd = true;
                }
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
                if(bAdd == true){
                    this.uploadArchivos(path, documento);
                }
            } catch (Exception ex) {
                System.out.println("Error en la funcion testCreateDocumentos:");
                System.out.println(ex.getMessage());
            }
        }
        return true;
    }

    public String getFileExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");

        // Si no hay punto en el nombre del archivo o si es el primer carácter, no hay extensión
        if (lastIndex == -1 || lastIndex == 0) {
            return "";
        }

        // En caso contrario, retornar la cadena de caracteres desde después del último punto hasta el final
        return fileName.substring(lastIndex + 1).toLowerCase();
    }





    public boolean uploadArchivos(String pathOrigen, Documento documento) {
        String path = pathOrigen + "\\Imagenes";
        File folder = new File(path);
        File[] files = folder.listFiles();
        int orden = 0;
        for (File file : files) {
            String filename = file.getAbsolutePath();
            if(getFileExtension(filename).equals("jpg")) {
                orden++;
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
                Archivo archivoGrande = new Archivo();
                archivoGrande.setArchivoTipo(archivoTipo);
                archivoGrande.setOrden(orden);

                archivoGrande.setExtension("jpg");
                archivoGrande.setDocumento(documento);
                archivoGrande.setDateCreated(new Timestamp(System.currentTimeMillis()));

                // Guardar la Data del archivo
                ArchivoData archivoData = new ArchivoData();
                archivoData.setData(encode);
                archivoData.setFileSize(fileSize);
                archivoData.setEncriptado(1); //La información está encriptada!!
                archivoData = this.archivoDataRepository.save(archivoData);
                //-------------------------------->

                archivoGrande.setArchivoData(archivoData.getId());
                this.archivoRepository.save(archivoGrande);

                String uuidGrande = archivoGrande.getUuid();
                //FIN DE ARCHIVO GRANDE---------------------------------------------------------------->

                // PREVIEW!
                // Archivo TIPO = 2 -> PREVIEW
                archivoTipo = archivoTipoRepository.findFirstByIdEquals(2);
                Archivo archivoPrev = new Archivo();
                archivoPrev.setArchivoTipo(archivoTipo);
                archivoPrev.setOrden(orden);


                archivoPrev.setExtension("jpg");
                archivoPrev.setDocumento(documento);
                archivoPrev.setDateCreated(new Timestamp(System.currentTimeMillis()));

                // Guardar la Data del archivo

                archivoData = new ArchivoData();
                archivoData.setData(getFilePreview(file));
                archivoData.setFileSize(0);
                archivoData.setEncriptado(0); // EL PREVIEW NO ESTA ENCRIPTADO!!!
                archivoData = this.archivoDataRepository.save(archivoData);
                //-------------------------------->

                archivoPrev.setArchivoData(archivoData.getId());

                // Modifica el uuid de la imagen grande
                archivoPrev.setUuidAlternativo(uuidGrande);
                this.archivoRepository.save(archivoPrev);

                //Guardar el uuid alternativo del archivo chico!
                archivoGrande.setUuidAlternativo(archivoPrev.getUuid());
                this.archivoRepository.save(archivoGrande);
            }
        }


        // SUBIR LOS PDFS!!
        String pathPdf = pathOrigen + "\\pdf";
        folder = new File(pathPdf);
        files = folder.listFiles();
        orden = 0;
        for (File file : files) {
            String filename = file.getAbsolutePath();
            if(getFileExtension(filename).equals("pdf")) {
                orden = orden + 1;
                String encode = this.encodeFile(filename);
                long fileSize = 0;
                try {
                    fileSize = Files.size(file.toPath());
                } catch (Exception ex) {
                    fileSize = 0;
                }

                // PDF>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>!
                // Archivo TIPO = 4 -> pdf
                ArchivoTipo archivoTipo = archivoTipoRepository.findFirstByIdEquals(4);
                Archivo archivoPDF = new Archivo();
                archivoPDF.setArchivoTipo(archivoTipo);
                archivoPDF.setOrden(orden);

                archivoPDF.setExtension("pdf");
                archivoPDF.setDocumento(documento);
                archivoPDF.setDateCreated(new Timestamp(System.currentTimeMillis()));


                // Guardar la Data del archivo
                ArchivoData archivoData = new ArchivoData();
                archivoData.setData(encode);
                archivoData.setFileSize(fileSize);
                archivoData.setEncriptado(1); //La información está encriptada!!
                archivoData = this.archivoDataRepository.save(archivoData);
                archivoPDF.setArchivoData(archivoData.getId());
                this.archivoRepository.save(archivoPDF);
            }
        }


        // SUBIR LOS PDFS!!
        String pathOcr = pathOrigen + "\\Texto";
        folder = new File(pathOcr);
        files = folder.listFiles();
        orden = 0;
        for (File file : files) {
            String filename = file.getAbsolutePath();
            if(getFileExtension(filename).equals("txt")) {
                orden = orden + 1;
                String encode = this.encodeFile(filename);

                long fileSize = 0;
                try {
                    fileSize = Files.size(file.toPath());
                } catch (Exception ex) {
                    fileSize = 0;
                }

                // OCR>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>!
                // Archivo TIPO = 3 -> OCR
                ArchivoTipo archivoTipo = archivoTipoRepository.findFirstByIdEquals(3);
                Archivo archivoOcr = new Archivo();
                archivoOcr.setArchivoTipo(archivoTipo);
                archivoOcr.setOrden(orden);

                archivoOcr.setExtension("txt");
                archivoOcr.setDocumento(documento);
                archivoOcr.setDateCreated(new Timestamp(System.currentTimeMillis()));


                // Guardar la Data del archivo
                ArchivoData archivoData = new ArchivoData();
                archivoData.setData(encode);
                archivoData.setFileSize(fileSize);
                archivoData.setEncriptado(1); //La información está encriptada!!
                archivoData = this.archivoDataRepository.save(archivoData);
                archivoOcr.setArchivoData(archivoData.getId());
                this.archivoRepository.save(archivoOcr);
            }
        }

        return true;
    }


    public boolean test() throws Exception {
        File folder = new File("K:\\Documentos\\01\\PC_1\\18219\\Imagenes");
        File[] files = folder.listFiles();

        long tiempoTotal = 0;
        long tamanioTotal = 0;
        long tiempo = 0;
        tiempoTotal = System.currentTimeMillis();

        for (File file : files) {
            String filename = file.getAbsolutePath();
            tamanioTotal = tamanioTotal + Files.size(file.toPath());
            System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
            System.out.println("ENCODEANDO : " + filename);
            System.out.println("TAMAÑO : " + Files.size(file.toPath()));

            tiempo = System.currentTimeMillis();
            String encode = encodeFile(filename);
            System.out.println("TIEMPO : " + (System.currentTimeMillis() - tiempo) + " ms");

            // Guardar archivo:
            Path path = Paths.get("K:\\Documentos\\01\\PC_1\\18219\\imagenes_encriptadas\\" + file.getName());
            byte[] bContent = encode.getBytes();
            Files.write(path, bContent);

            path = Paths.get("K:\\Documentos\\01\\PC_1\\18219\\imagenes_preview\\" + file.getName());
            BufferedImage img = new BufferedImage(210, 352, BufferedImage.TYPE_INT_RGB);
            img.createGraphics().drawImage(ImageIO.read(file).getScaledInstance(210, 352, Image.SCALE_SMOOTH), 0, 0, null);
            ImageIO.write(img, "jpg", path.toFile());

            if (encode.length() >= 60) {
                System.out.println("RESULTADO(ULTIMOS 60 CARACTERES) : " + encode.substring(encode.length() - 60));
            } else {
                System.out.println("RESULTADO(PRIMERAS 60 CARACTERES) : [VACIO-ERROR]");
            }
            System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");

        }

        System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
        System.out.println("TIEMPO TOTAL : " + (System.currentTimeMillis() - tiempoTotal) + " ms");
        System.out.println("ARCHIVOS : " + files.length);
        System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");

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
/*
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(nombreArchivo), "UTF-8"))
*/
            final BufferedReader reader =
                    Files.newBufferedReader(path, Charset.forName("windows-1252"));


            String contenido = reader.lines().collect(Collectors.joining());

            //String contenido = new String(Files.readAllBytes(path));
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

    public boolean testDecode() {
        String strFolder = "K:\\Documentos\\01\\PC_1\\18219\\imagenes_encriptadas\\";
        File folder = new File(strFolder);
        File[] files = folder.listFiles();
        for (File file : files) {
            String filename = strFolder + file.getName();
            try {
                decodeFile(filename);
            } catch (Exception ex) {
                System.out.println("Error en la funcion testDecode, archivo " + filename + " :");
                System.out.println(ex.getMessage());
            }

        }
        return true;
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

    /**
     * private String getFileContent(String file) {
     * try {
     * BufferedReader br = new BufferedReader(new FileReader(file));
     * <p>
     * StringBuilder sb = new StringBuilder();
     * String line = br.readLine();
     * <p>
     * while (line != null) {
     * sb.append(line);
     * sb.append(System.lineSeparator());
     * line = br.readLine();
     * }
     * <p>
     * String everything = sb.toString();
     * br.close();
     * <p>
     * return everything;
     * } catch (FileNotFoundException e) {
     * throw new RuntimeException(e);
     * } catch (IOException e) {
     * throw new RuntimeException(e);
     * }
     * }
     */

    /*
    private List<String> fetFileContentArray(String file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            List<String> lineas = new ArrayList<>();
            String line = br.readLine();
            while (line != null) {
                lineas.add(line);
                line = br.readLine();
            }
            br.close();
            return lineas;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
     */

}
