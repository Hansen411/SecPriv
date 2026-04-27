import java.io.*;
import java.net.*;
import java.security.*;
import javax.crypto.*;

public class CipherServer {
    public static void main(String[] args) throws Exception{

      
            int port = 7999;
            ServerSocket server = new ServerSocket(port);
            Socket s = server.accept();
         

       
            ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream("DES.key"));
            Key key = (Key) keyIn.readObject();
            keyIn.close();

     
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);

            CipherInputStream cipherIn =
                    new CipherInputStream(s.getInputStream(), cipher);


            byte[] data = cipherIn.readAllBytes();

            cipherIn.close();
            s.close();

            String message = new String(data, "UTF-8");

            System.out.println("Decrypted message:");
            System.out.println(message);

            server.close();


    }
}