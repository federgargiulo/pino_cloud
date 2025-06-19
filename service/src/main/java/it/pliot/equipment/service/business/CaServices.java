package it.pliot.equipment.service.business;

import it.pliot.equipment.casupport.CertAndKey;

public interface CaServices {

    public CertAndKey generateCA() throws Exception;

    public String certAsBase64String( String alias , String password,
                                      CertAndKey cerAndKey );
}
