package com.sm.admDecretos.Core.Service;

import com.sm.admDecretos.Core.Entity.Db.Archivo;
import com.sm.admDecretos.Core.Repository.ArchivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * Este servicio se encarga de encriptar y desencriptar los documentos.
 */
@Service
public class EncodeService extends AbstractService<Archivo> {
    @Autowired
    ArchivoRepository archivoRepository;

    // TODO: El SECRET_KEY DEBERIA SER CONCATENADO CON UN VALOR DINAMICO.. DEPENDIENDO DEL ID DOCUMENTO
    private static final String SECRET_KEY = "cRrEkl6EMnkt5IEGi6YiIuF1xXPmPmeEoYZcaXPRd5qir8gHf0zuzcjW03tt4AEL";
    private static final String SALT = "88u7YxTWKra1W0WV";

    public String des(String text) throws Exception {
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        IvParameterSpec ivspec = new IvParameterSpec(iv);

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
        return new String(cipher.doFinal(Base64.getDecoder().decode(text)));
    }

    public String enc(String text) throws Exception {
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        IvParameterSpec ivspec = new IvParameterSpec(iv);

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
        return Base64.getEncoder()
                .encodeToString(cipher.doFinal(text.getBytes(StandardCharsets.UTF_8)));
    }
}
