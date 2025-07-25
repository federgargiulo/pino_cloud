import java.io.IOException;

public class CertificateGenerator {

    public static void main(String[] args) throws IOException {
        String hostname = "myapp.example.com"; // Sostituisci con il nome dell'host
        String keystoreName = "mykeystore.jks";
        String alias = "myserver";
        String password = "changeit"; // Usa una password sicura

        generateCertificate(hostname, keystoreName, alias, password);
    }

    private static void generateCertificate(String hostname, String keystoreName, String alias, String password) throws IOException {
        String command = String.format(
                "keytool -genkeypair -alias %s -keyalg RSA -keysize 2048 -keystore %s -validity 365 " +
                        "-dname \"CN=%s, OU=IT, O=MyCompany, L=City, S=State, C=US\" -storepass %s -keypass %s",
                alias, keystoreName, hostname, password, password
        );

        Process process = Runtime.getRuntime().exec(command);

        try {
            process.waitFor();
            System.out.println("Keystore " + keystoreName + " generato con successo!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}