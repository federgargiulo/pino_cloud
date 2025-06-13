package it.pliot.equipment.security;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.jcajce.provider.asymmetric.RSA;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

public class CaServer {

    BouncyCastleProvider provider = new BouncyCastleProvider();



    public void generate() throws Exception {
        // Genera la CA autofirmata
        KeyPair caKeyPair = generateRSAKeyPair();
        X509Certificate caCert = generateSelfSignedCertificate(caKeyPair, "CN=MyRootCA");

        // Salva CA su file (keystore) per riferimento
        saveKeyStore("ca.p12", "capassword", caKeyPair.getPrivate(), caCert, "ca");

        // Genera certificato server firmato dalla CA per un hostname


        String hostname = "myserver.local";


        KeyPair serverKeyPair = generateRSAKeyPair();
        X509Certificate serverCert = generateCertSignedByCA(hostname, serverKeyPair.getPublic(), caKeyPair.getPrivate(), caCert);

        // Salva il certificato server (keystore) per HTTPS
        saveKeyStore(hostname + ".p12", "serverpassword", serverKeyPair.getPrivate(), serverCert, "server");

        System.out.println("CA e certificati generati e salvati in file .p12");
    }



    public static AsymmetricCipherKeyPair generateRSAKeyPair() {
        RSAKeyPairGenerator generator = new RSAKeyPairGenerator();
        generator.init(new RSAKeyGenerationParameters(
                new BigInteger("10001", 16),
                new SecureRandom(),
                2048,
                64
        ));
        return generator.generateKeyPair();
    }



}
