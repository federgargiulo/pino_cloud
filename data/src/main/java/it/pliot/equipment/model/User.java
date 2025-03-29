package it.pliot.equipment.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="users")
public class User extends BaseEntity {

    @Id
    private String idpId;


    private String userId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String type;

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    private String tenant;

    private String email;

    private String phone;


    private String gender;

    private String address;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdpId() {
        return idpId;
    }

    public void setIdpId(String idpId) {
        this.idpId = idpId;
    }


    private String firstName;

    private String lastName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<UserGrp> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(List<UserGrp> userGroups) {
        this.userGroups = userGroups;
    }


    @ManyToMany(fetch = FetchType.EAGER )
    @JoinTable(
            name="users_group",
            joinColumns={@JoinColumn(name="Users", referencedColumnName="idpId")},
            inverseJoinColumns={@JoinColumn(name="grpName", referencedColumnName="grpName")})

    private List<UserGrp> userGroups = new ArrayList<>();
}
