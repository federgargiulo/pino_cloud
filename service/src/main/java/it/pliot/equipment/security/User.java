package it.pliot.equipment.security;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    private String userId;

    private String tenantId;

    private List<String> roles;

    private List<String> groups;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
