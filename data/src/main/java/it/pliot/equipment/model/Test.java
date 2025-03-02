package it.pliot.equipment.model;

public class Test {


    public static void main( String [] arg ){

        int sec = 59;
        int sec10 = ( ( sec  )  / 10 ) *10;
        int sec30 = ( ( sec ) / 30 ) *30;
        System.out.print(" sec  " + sec );
        System.out.print(" sec 10 -> " + sec10 );
        System.out.println(" sec 30 -> " + sec30 );
    }
}
