package it.pliot.equipment.casupport;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class CertAndKey {

    public final X509Certificate certificate;
    public final PrivateKey privateKey;

    public CertAndKey(X509Certificate cert, PrivateKey key) {
        this.certificate = cert;
        this.privateKey = key;
    }


}
