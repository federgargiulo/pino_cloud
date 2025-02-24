package it.pliot.equipment.io;

import it.pliot.equipment.model.BaseEntity;

import java.util.Date;
import java.util.Objects;


public class TenantTO extends BaseEntity {
    private String id;

    private String name;
    private String description;

    private Date createdDttm;

    private Date updateDttm;

    public static TenantTO newrtenant( String tenId , String name, String descr) {
        TenantTO t = new TenantTO();
        t.setId( tenId );
        t.setName( name );
        t.setDescription( descr );
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
        return Objects.equals(id, tenantTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Date getUpdateDttm() {
        return updateDttm;
    }

    @Override
    public void setUpdateDttm(Date updateDttm) {
        this.updateDttm = updateDttm;
    }

    @Override
    public Date getCreatedDttm() {
        return createdDttm;
    }

    @Override
    public void setCreatedDttm(Date createdDttm) {
        this.createdDttm = createdDttm;
    }
}
