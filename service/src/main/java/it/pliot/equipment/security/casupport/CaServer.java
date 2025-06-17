package it.pliot.equipment.security.casupport;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;


import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.io.ByteArrayOutputStream;

import java.math.BigInteger;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Date;

public class CaServer {


    CaPersistence persistence = new CaPersistence();


    private static final String CA_KEY = "ca";
    private static final String CA_PASSWORD = "capassword";
    private static final String CA_DN = "CN=MyRootCA";


    public CertAndKey generateHostCertificates(String hostname, CertAndKey serverKeyPair) throws Exception {

        KeyPair k = CaUtils.generateRSAKeyPair();

        X509Certificate serverCert = generateCertSignedByCA(hostname,
                k.getPublic(),
                serverKeyPair.privateKey,
                serverKeyPair.certificate);


        return serverKeyPair;

    }


    public static X509Certificate generateSelfSignedCertificate(KeyPair keyPair, String dn) throws Exception {
        long now = System.currentTimeMillis();
        Date startDate = new Date(now);

        X500Name issuer = new X500Name(dn);
        BigInteger serial = BigInteger.valueOf(now);
        Date endDate = new Date(now + 365L * 24 * 60 * 60 * 1000); // 1 anno

        SubjectPublicKeyInfo
                subPubKeyInfo = SubjectPublicKeyInfo.getInstance(keyPair.getPublic().getEncoded());

        X509v3CertificateBuilder certBuilder =
                new X509v3CertificateBuilder(issuer, serial, startDate, endDate, issuer, subPubKeyInfo);

        ContentSigner signer = new JcaContentSignerBuilder("SHA256withRSA").build(keyPair.getPrivate());

        X509CertificateHolder certHolder = certBuilder.build(signer);

        return
                new JcaX509CertificateConverter().getCertificate(certHolder);
    }


    public static void main(String[] arg) throws Exception {
        CaServer ca = new CaServer();

        String hostname = "myserver.local";
        ca.getEdgeKeyStore(hostname, "password");

    }


    public static X509Certificate generateCertSignedByCA(String hostname,
                                                         PublicKey serverPubKey,
                                                         PrivateKey caPrivKey,
                                                         X509Certificate caCert) throws Exception {

        long now = System.currentTimeMillis();
        Date startDate = new Date(now);

        X500Name issuer = new X500Name(caCert.getSubjectX500Principal().getName());
        X500Name subject = new X500Name("CN=" + hostname);

        BigInteger serial = BigInteger.valueOf(now + 1);
        Date endDate = new Date(now + 365L * 24 * 60 * 60 * 1000); // 1 anno

        SubjectPublicKeyInfo subPubKeyInfo = SubjectPublicKeyInfo.getInstance(serverPubKey.getEncoded());

        X509v3CertificateBuilder certBuilder = new X509v3CertificateBuilder(
                issuer, serial, startDate, endDate, subject, subPubKeyInfo);


        ContentSigner signer = new JcaContentSignerBuilder("SHA256withRSA").build(caPrivKey);

        X509CertificateHolder certHolder = certBuilder.build(signer);

        return new JcaX509CertificateConverter().getCertificate(certHolder);
    }

    public byte[] getEdgeKeyStore(String hostname, String password) throws Exception {
        ByteArrayOutputStream b = persistence.read(hostname);
        if (b != null)
            return b.toByteArray();
        ByteArrayOutputStream ca = persistence.read(CA_KEY);
        CertAndKey caSertAndKey = null;
        if (ca != null)
            caSertAndKey = CaUtils.getPKCS12FromByteArray(CA_KEY, CA_PASSWORD, ca.toByteArray());
        else {
            caSertAndKey = generateCA();
            ByteArrayOutputStream bio = CaUtils.saveKeyStoreToBuffer(CA_KEY, CA_PASSWORD, caSertAndKey);
            persistence.save(bio, CA_KEY);
        }

        CertAndKey hostCertAndKey = generateHostCertificates(hostname, caSertAndKey);
        ByteArrayOutputStream bio = CaUtils.saveKeyStoreToBuffer(hostname, password, hostCertAndKey);
        persistence.save(bio, hostname);
        return bio.toByteArray();

    }


    public CertAndKey generateCA() throws Exception {

        KeyPair caKeyPair = CaUtils.generateRSAKeyPair();
        X509Certificate caCert = generateSelfSignedCertificate(caKeyPair, CA_DN);

        return new CertAndKey(caCert, caKeyPair.getPrivate());
    }


}
