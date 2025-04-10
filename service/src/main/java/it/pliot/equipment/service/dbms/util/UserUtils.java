package it.pliot.equipment.service.dbms.util;

import it.pliot.equipment.io.UserGrpTO;
import it.pliot.equipment.io.UserTO;
import it.pliot.equipment.model.User;
import it.pliot.equipment.model.UserGrp;

import java.util.ArrayList;
import java.util.List;

public class UserUtils extends BaseConvertUtil<User, UserTO> {


    private static UserUtils INSTANCE = new UserUtils();

    public static UserUtils instance() {
        return INSTANCE;
    }

    private UserUtils() {
    }

    @Override
    public User io2data(UserTO in) {
        if (in == null) return null;
        User out = new User();
        return cp2data( in , out );
    }

    public User cp2data(UserTO in , User out  ) {
        if (in == null) return null;

        out.setAddress(in.getAddress());
        out.setUserId(in.getUserId());
        out.setFirstName(in.getFirstName());
        out.setLastName(in.getLastName());
        out.setEmail(in.getEmail());
        out.setGender(in.getGender());
        out.setIdpId(in.getIdpId());
        out.setPhone(in.getPhone());
        out.setUserGroups(UserGrpUtils.instance().converListIO2data(in.getUsrGrp()));
        out.setTenant(in.getTenant());
        out.setCreatedDttm(in.getCreatedDttm());
        out.setUpdateDttm(in.getCreatedDttm());
        out.setType(in.getType());
        return out;
    }

    @Override
    public UserTO data2io(User in) {
        if (in == null) return null;
        UserTO out = new UserTO();
        out.setAddress(in.getAddress());
        out.setUserId(in.getUserId());
        out.setFirstName(in.getFirstName());
        out.setLastName(in.getLastName());
        out.setEmail(in.getEmail());
        out.setGender(in.getGender());
        out.setIdpId(in.getIdpId());
        out.setPhone(in.getPhone());
        out.setTenant(in.getTenant());
        out.setUsrGrp(UserGrpUtils.instance().converListData2IO(in.getUserGroups()));
        out.setCreatedDttm(in.getCreatedDttm());
        out.setUpdateDttm(in.getCreatedDttm());
        out.setType(in.getType());
       return out;

    }

    public List<UserGrpTO> convertListData2Io(List<UserGrp> userGrps) {
        List<UserGrpTO> result = new ArrayList<>();

        userGrps.forEach(group -> {
            UserGrpTO to = new UserGrpTO();
           to.setGrpName(group.getGrpName());
            to.setDescription(group.getDescription());

            result.add(to);
        });

        return result;
    }

}
