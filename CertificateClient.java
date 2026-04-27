import java.io.*;
import java.net.*;
import java.security.*;
import java.security.cert.*;
import javax.crypto.Cipher;

public class CertificateClient {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("localhost", 7999);
        System.out.println("Connected to server.");

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        CertificateFactory factory = CertificateFactory.getInstance("X.509");

        FileInputStream fis = new FileInputStream("servercert.cer");
        X509Certificate serverCert = (X509Certificate) factory.generateCertificate(fis);
        fis.close();

        serverCert.checkValidity(); 

        PublicKey serverPublicKey = serverCert.getPublicKey();
        System.out.println("Server certificate loaded.");


        KeyStore keyStore = KeyStore.getInstance("JKS");
        FileInputStream kis = new FileInputStream("clientkeystore.jks");

        keyStore.load(kis, "password".toCharArray());
        kis.close();

        PrivateKey clientPrivateKey = (PrivateKey) keyStore.getKey("clientkey", "password".toCharArray());

        X509Certificate clientCert = (X509Certificate) keyStore.getCertificate("clientkey");

        out.writeObject(clientCert.getEncoded());

        String message = "Hello World";

        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, serverPublicKey);

        byte[] encryptedMessage =
                encryptCipher.doFinal(message.getBytes());

        Cipher signCipher = Cipher.getInstance("RSA");
        signCipher.init(Cipher.ENCRYPT_MODE, clientPrivateKey);

        byte[] signature =
                signCipher.doFinal(message.getBytes());

        out.writeObject(encryptedMessage);
        out.writeObject(signature);
        out.flush();

        System.out.println("Message sent securely.");

        socket.close();
    }
}