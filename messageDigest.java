import java.security.MessageDigest;
import java.util.Scanner;

public class messageDigest {

    public static void main(String[] args) throws Exception {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String text = input.nextLine();

        
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] md5Bytes = md5.digest(text.getBytes());

       
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        byte[] sha1Bytes = sha1.digest(text.getBytes());

        
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        byte[] sha256Bytes = sha256.digest(text.getBytes());

        System.out.println("\nMD5: " + toHex(md5Bytes));
        System.out.println("SHA-1: " + toHex(sha1Bytes));
        System.out.println("SHA-256: " + toHex(sha256Bytes));

        input.close(); 
    }

    public static String toHex(byte[] bytes) {
        String result = "";
        for (int i = 0; i < bytes.length; i++) {
            result += String.format("%02x", bytes[i]);
        }
        return result;
    }
}