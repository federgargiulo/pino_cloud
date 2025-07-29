public class Test {

    public static void main( String [ ] arg ){

        String grupName = "jrjj";
        System.out.println(extractTenantName(  grupName ) );
        String grupName2 = Const.GROUP_PREFIX + "";

        System.out.println( extractTenantName(  grupName2 ) );

    }

    private static String extractTenantName( String x ){
        if ( x == null ) return null;

        int i = x.indexOf( Const.GROUP_PREFIX );

        if ( i < 0 )
            return null;
        return x.substring( Const.GROUP_PREFIX.length() , x.length() );
    }
}
