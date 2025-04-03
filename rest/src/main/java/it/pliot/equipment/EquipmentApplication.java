package it.pliot.equipment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@SpringBootApplication
@EnableScheduling
public class EquipmentApplication {

    public static void test(String s ) {
        try {
            System.out.println( " nel file ");
            File myObj = new File( s );
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch ( FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void main(String... args) {
        if  (args != null ) {
            for ( int i = 0 ; i< args.length ; i ++ ){
                System.out.println(" value " + args[ i ] );
                String [] ss = args[ i ].split( "=");
                if ( ss != null && ss.length > 1 )
                    test( ss[ 1 ] );


            }

        }
        SpringApplication.run(EquipmentApplication.class, args);
    }
}
