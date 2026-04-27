import java.io.*;
import java.net.*;
import java.security.*;
import javax.crypto.*;

public class CipherClient {
    public static void main(String[] args) throws Exception{

   
            String message = "The quick brown fox jumps over the lazy dog.";
            String host = "localhost";
            int port = 7999;
            Socket s = new Socket(host, port);

            File keyFile = new File("DES.key");
            Key key;

            if (!keyFile.exists()) {
                KeyGenerator generator = KeyGenerator.getInstance("DES");
                generator.init(new SecureRandom());
                key = generator.generateKey();

                ObjectOutputStream keyOut = new ObjectOutputStream(
                new FileOutputStream(keyFile));
                keyOut.writeObject(key);
                keyOut.close();

                System.out.println("New key generated.");
            } else {
                ObjectInputStream keyIn = new ObjectInputStream(
                 new FileInputStream(keyFile));
                key = (Key) keyIn.readObject();
                keyIn.close();

                System.out.println("Existing key loaded.");
            }


         
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            CipherOutputStream cipherOut = new CipherOutputStream(s.getOutputStream(), cipher);

            cipherOut.write(message.getBytes("UTF-8"));
            cipherOut.flush();
            cipherOut.close();

            s.close();


    }
}