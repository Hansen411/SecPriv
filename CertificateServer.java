import java.io.*;
import java.net.*;
import java.security.*;
import java.security.cert.*;
import javax.crypto.Cipher;

public class CertificateServer {

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(7999);
        Socket socket = serverSocket.accept();

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        //ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream("serverkeystore.jks"), "password".toCharArray());

        PrivateKey serverPrivateKey = (PrivateKey) keyStore.getKey("serverkey", "password".toCharArray());

        byte[] certData = (byte[]) in.readObject();

        CertificateFactory factory = CertificateFactory.getInstance("X.509");

        X509Certificate clientCert = (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(certData));

        System.out.println("Client Certificate");
        System.out.println(clientCert);

        
        PublicKey clientPublicKey = clientCert.getPublicKey();

  
        byte[] encryptedMessage = (byte[]) in.readObject();
        byte[] signature = (byte[]) in.readObject();

        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, serverPrivateKey);
        String message = new String(decryptCipher.doFinal(encryptedMessage));

        Cipher verifyCipher = Cipher.getInstance("RSA");
        verifyCipher.init(Cipher.DECRYPT_MODE, clientPublicKey);

        String signedMessage = new String(verifyCipher.doFinal(signature));

        if (signedMessage.equals(message)) {
            System.out.println("Signature Accepted");
        } else {
            System.out.println("Signature Failed");
        }
        System.out.println("Received message: " + message);


        socket.close();
        serverSocket.close();

    }
}