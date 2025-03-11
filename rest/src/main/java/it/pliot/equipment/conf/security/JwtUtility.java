package it.pliot.equipment.conf.security;

import io.jsonwebtoken.*;

import it.pliot.equipment.security.User;
import it.pliot.equipment.security.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class JwtUtility {


    private static final Logger log = LoggerFactory.getLogger(JwtUtility.class);



    public boolean validateToken(String token) {

        return true;
    }

    private JwtParser parcer = null;

    @Value("${pliot.sec.authPubKey}")
    private String authPubKey;

    public JwtParser getParser() {
        synchronized (this){
            if ( parcer != null )
                return parcer;
            JwtParserBuilder builder = Jwts.parser();
            if ( authPubKey != null ) {
                try {
                    RSAPublicKey pubKey = convertStringToPublicKey(authPubKey);
                    builder.verifyWith(pubKey);
                }catch ( Exception e ){
                    log.warn( " certificato non validato {} " , e.getMessage() );
                }
                parcer = builder.build();
            }

        }
        return parcer;
    }

    public User extractUser( String token ){
        User u = new User();
        Claims c = extractClaims( token );
        u.setUserId( c.get("username", String.class) );
        /**
         *
         *             Claims claims = jwrUtil.extractClaims(jwt);
         *
         *             String userId = claims.getSubject();
         *             String username = claims.get("username", String.class);
         *             String[] roles = JwtUtility.extractRoles(jwt);
         *
         *
         *
         * */
        return u;
    }

    public Claims extractClaims(String token  ) {

        try {

            return getParser().parseUnsecuredClaims( token ).getPayload();
        } catch (Exception e) {
            throw new SecurityExceptions("Could not verify JWT token integrity!", e);
        }
    }



    // Estrae il nome utente dal token JWT
    public String extractUsername(String token) {
        return extractClaims(token).get("username", String.class);
    }

    // Estrae i ruoli dal token JWT
    public String[] extractRoles(String token) {
        String roles = extractClaims(token).get("roles", String.class);
        return roles.split(",");
    }

    public static RSAPublicKey convertStringToPublicKey(String publicKeyStr) throws Exception {
        // Rimuovi i prefissi e suffissi PEM (se presenti)
        publicKeyStr = publicKeyStr.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");  // Rimuovi gli spazi bianchi

        // Decodifica la stringa da Base64
        byte[] encoded = Base64.getDecoder().decode(publicKeyStr);

        // Crea la chiave pubblica RSA utilizzando KeyFactory
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        return publicKey;
    }
}
