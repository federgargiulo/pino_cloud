package it.pliot.equipment.web;

import it.pliot.equipment.conf.WebConf;
import it.pliot.equipment.io.EdgeTO;
import it.pliot.equipment.service.business.EdgeServices;
import it.pliot.equipment.service.dbms.util.ConvertUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class EdgeSSOHandler extends ResourceHttpRequestHandler {

    EdgeServices edgeServices;

    public EdgeSSOHandler(EdgeServices edgeServices){
        this.edgeServices = edgeServices;

    }


    @Override
    public void handleRequest(HttpServletRequest request , HttpServletResponse response ) throws IOException {
        String edgeId = request.getParameter(WebConf.SSO_EDGE_ID_PAR_KEY );
        String code = request.getParameter("code");
        String state = request.getParameter("state");

        if ( ! ConvertUtils.isNullOrEmpty( edgeId) ){
            EdgeTO edge = edgeServices.findById( edgeId );
            if ( edge == null){
                response.sendError( 401 );
                return;
            }
            String redirUrl = edge.getEdgeUrl();

            // Aggiunge i parametri code e state se presenti
            StringBuilder finalUrl = new StringBuilder(redirUrl);
            if (code != null && state != null) {
                finalUrl.append(redirUrl.contains("?") ? "&" : "?");
                finalUrl.append("code=").append(URLEncoder.encode(code, StandardCharsets.UTF_8));
                finalUrl.append("&state=").append(URLEncoder.encode(state, StandardCharsets.UTF_8));
            }

            // Redirect 302
            response.setStatus(HttpServletResponse.SC_FOUND); // 302
            response.setHeader("Location", finalUrl.toString() );
        }else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }


    }

    }
