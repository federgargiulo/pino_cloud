package it.pliot.equipment.security.casupport;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.X509Certificate;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class CaUtils {



    public static CertAndKey getPKCS12FromByteArray( String alias , String password,  byte[] bytes ) throws Exception {
        KeyStore ks = KeyStore.getInstance("PKCS12");
        InputStream fis = new ByteArrayInputStream( bytes );
        ks.load(fis, password.toCharArray());
        PrivateKey privateKey = (PrivateKey) ks.getKey(alias, password.toCharArray());
        X509Certificate certificate = (X509Certificate) ks.getCertificate( alias );
        return new CertAndKey(certificate, privateKey);
    }

    public static ByteArrayOutputStream saveKeyStoreToBuffer(String alias , String password,
                                              CertAndKey cerAndKey
                                                             ) throws Exception {
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(null, null);
        ks.setKeyEntry(alias, cerAndKey.privateKey , password.toCharArray(), new java.security.cert.Certificate[]{cerAndKey.certificate});
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ks.store(bos, password.toCharArray());
        return bos;

    }

    public static KeyPair generateRSAKeyPair() throws Exception {
        RSAKeyPairGenerator generator = new RSAKeyPairGenerator();
        generator.init(new RSAKeyGenerationParameters(
                new BigInteger("10001", 16), // Esponente pubblico (65537)
                new SecureRandom(),
                2048,
                64
        ));

        AsymmetricCipherKeyPair asymmetricKeyPair = generator.generateKeyPair();
        org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters privateKeyParams =
                (org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters) asymmetricKeyPair.getPrivate();
        org.bouncycastle.crypto.params.RSAKeyParameters publicKeyParams =
                (org.bouncycastle.crypto.params.RSAKeyParameters) asymmetricKeyPair.getPublic();

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(
                publicKeyParams.getModulus(),
                publicKeyParams.getExponent()
        );
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        RSAPrivateCrtKeySpec privateKeySpec = new RSAPrivateCrtKeySpec(
                privateKeyParams.getModulus(),
                privateKeyParams.getPublicExponent(),
                privateKeyParams.getExponent(),
                privateKeyParams.getP(),
                privateKeyParams.getQ(),
                privateKeyParams.getDP(),
                privateKeyParams.getDQ(),
                privateKeyParams.getQInv()
        );
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
        return new KeyPair(publicKey, privateKey);
    }
}
