package it.pliot.equipment.security.casupport;

import java.io.*;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class CaPersistence {

    private static String SERVER_PATH = "c:\\tmp" ;



  public void save( ByteArrayOutputStream byteArray, String alias ){
        FileOutputStream f = null;
        try {
            f = new FileOutputStream( SERVER_PATH + "/" + alias );
            f.write( byteArray.toByteArray() );
            f.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if ( f != null ){
                try{ f.close(); } catch ( Exception e ){}
            }
        }


    }

 public ByteArrayOutputStream read(String id) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String fileName = SERVER_PATH + "/" + id;

        try (FileInputStream f = new FileInputStream( fileName )) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = f.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            return null;
        }

        return out;
   }






}
