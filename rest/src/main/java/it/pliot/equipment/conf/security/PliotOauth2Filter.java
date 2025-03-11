package it.pliot.equipment.conf.security;

import io.jsonwebtoken.Claims;
import it.pliot.equipment.security.User;
import it.pliot.equipment.security.UserContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.keycloak.jose.jwe.JWEUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class PliotOauth2Filter extends OncePerRequestFilter {


    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";


    @Value("${pliot.sec.idpPubKey:dummy_key} ")
    private String idpPubKey;

    public String getIdpPubKey() {
        return idpPubKey;
    }

    private JwtUtility jwtUtility;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = extractJwtFromRequest(request);

        if (jwt != null && jwtUtility.validateToken(jwt)) {
            User u = jwtUtility.extractUser( jwt );
            UserContext.setUser( u );


        }

        filterChain.doFilter(request, response);
    }

    // Estrai il JWT dal header Authorization
    private String extractJwtFromRequest(HttpServletRequest request) {
        String header = request.getHeader(AUTH_HEADER);
        if (header != null && header.startsWith(BEARER_PREFIX)) {
            return header.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}
