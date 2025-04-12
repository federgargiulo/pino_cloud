package it.pliot.equipment.controller;

import it.pliot.equipment.conf.ApiPrefixController;
import it.pliot.equipment.io.UserTO;
import it.pliot.equipment.service.business.UserGrpServices;
import it.pliot.equipment.service.business.UserServices;
import jakarta.ws.rs.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@ApiPrefixController
public class UserController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private UserGrpServices userGrpServices;

    @GetMapping("/users")
    public List<UserTO> search(@QueryParam( "tenant") String tenant ) {


        return userServices.findUsersByTenant( tenant );
    }

    @GetMapping("/users/{userId}")
    public UserTO getUserById(@PathVariable("userId") String userId) {
        UserTO user = userServices.findById(userId);
        return user;
    }

    @PostMapping("/users")
    public ResponseEntity<UserTO> createUser(@RequestBody UserTO userTO) {
        try {

            UserTO t = userServices.create( userTO );
            return new ResponseEntity<>(t, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        userServices.delete(id);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<UserTO> updateUser(@PathVariable("id") String id , @RequestBody UserTO userTO ) {
        try {
            userTO.setIdpId(id);
            userTO = userServices.save( userTO );
            return new ResponseEntity<>(userTO, HttpStatus.OK );
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
