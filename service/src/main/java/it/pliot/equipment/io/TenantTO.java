package it.pliot.equipment.io;

import it.pliot.equipment.model.BaseEntity;

import java.util.Date;
import java.util.Objects;


public class TenantTO  {
    private String tenantId;

    private String name;
    private String description;

    private Date createdDttm;

    private Date updateDttm;

    private String email;

    private String profile;

    private String country;

    private String state;

    private String zipCode;

    private String address;

    private String idpGrpId;

    public String getIdpGrpId() {
        return idpGrpId;
    }

    public void setIdpGrpId(String idpGrpId) {
        this.idpGrpId = idpGrpId;
    }

    public static TenantTO newrtenant(String tenId , String name, String descr, String email, String address, String zipCode, String country, String profile, String state) {
        TenantTO t = new TenantTO();
        t.setTenantId( tenId );
        t.setName( name );
        t.setDescription( descr );
        t.setEmail(email);
        t.setAddress(address);
        t.setZipCode(zipCode);
        t.setCountry(country);
        t.setProfile(profile);
        t.setState(state);
        return t;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TenantTO tenantTO = (TenantTO) o;
        return Objects.equals(tenantId, tenantTO.tenantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantId);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    private Long version;

    public Date getUpdateDttm() {
        return updateDttm;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void setUpdateDttm(Date updateDttm) {
        this.updateDttm = updateDttm;
    }

    public Date getCreatedDttm() {
        return createdDttm;
    }

    public void setCreatedDttm(Date createdDttm) {
        this.createdDttm = createdDttm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
