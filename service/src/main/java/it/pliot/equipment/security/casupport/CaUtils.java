package it.pliot.equipment.security.casupport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class KeyStoreUtils {

    public static CertAndKey getPKCS12FromByteArray( String alias , String password,  byte[] bytes ) throws Exception {
        KeyStore ks = KeyStore.getInstance("PKCS12");
        InputStream fis = new ByteArrayInputStream( bytes );
        ks.load(fis, password.toCharArray());
        PrivateKey privateKey = (PrivateKey) ks.getKey(alias, password.toCharArray());
        X509Certificate certificate = (X509Certificate) ks.getCertificate( alias );
        return new CertAndKey(certificate, privateKey);
    }
}
