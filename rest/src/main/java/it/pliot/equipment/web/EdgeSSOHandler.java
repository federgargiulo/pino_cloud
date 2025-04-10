package it.pliot.equipment.web;

import it.pliot.equipment.conf.WebConf;
import it.pliot.equipment.io.EdgeTO;
import it.pliot.equipment.service.business.EdgeServices;
import it.pliot.equipment.service.dbms.util.ConvertUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.io.IOException;


public class EdgeSSOHandler extends ResourceHttpRequestHandler {

    EdgeServices edgeServices;

    public EdgeSSOHandler(EdgeServices edgeServices){
        this.edgeServices = edgeServices;

    }


    @Override
    public void handleRequest(HttpServletRequest request , HttpServletResponse response ) throws IOException {
        String edgeId = request.getParameter(WebConf.SSO_EDGE_ID_PAR_KEY );
        if ( ! ConvertUtils.isNullOrEmpty( edgeId) ){
            EdgeTO edge = edgeServices.findById( edgeId );
            if ( edge == null){
                response.sendError( 401 );
                return;
            }
            String redirUrl = edge.getEdgeUrl();

            // Redirect 302
            response.setStatus(HttpServletResponse.SC_FOUND); // 302
            response.setHeader("Location", redirUrl );
        }


    }

    }
