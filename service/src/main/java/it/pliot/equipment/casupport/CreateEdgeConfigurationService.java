package it.pliot.equipment.casupport;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class CreateEdgeConfigurationService {
    private CaServer caServer = null;
    public CreateEdgeConfigurationService( String cn , String password ){
        caServer = new CaServer( cn , password );
    }

    public byte [] createReleaseFile( String edge ) throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try ( TarArchiveOutputStream taos = new TarArchiveOutputStream( baos ) ) {
            String kewStore = "keystore.p12";
            TarArchiveEntry entry = new TarArchiveEntry(kewStore);
            byte[] content = caServer.getEdgeKeyStore( edge , "password");
            entry.setSize(content.length);
            taos.putArchiveEntry(entry);
            taos.write(content);
            taos.closeArchiveEntry();
        }
        return baos.toByteArray();

    }


    private static final String CA_CN = "CN=PinoCA";
    private static final String CA_PASSWORD = "capassword";


    public static void main( String [] args ) throws Exception{


            CreateEdgeConfigurationService edge =
                    new CreateEdgeConfigurationService( CA_CN ,  CA_PASSWORD );
            byte [] buffer = edge.createReleaseFile( "host.local");
            FileOutputStream f = new FileOutputStream( "c:/tmp/edge.tar");
            f.write( buffer );
            f.close();


    }



}
