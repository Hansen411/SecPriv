import java.io.*;
import java.net.*;
import java.math.BigInteger;
import java.security.SecureRandom;

public class RSAAlice {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("localhost", 7999);

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        BigInteger bobN = (BigInteger) in.readObject();
        BigInteger bobE = (BigInteger) in.readObject();

        SecureRandom rand = new SecureRandom();
        BigInteger p = BigInteger.probablePrime(512, rand);
        BigInteger q = BigInteger.probablePrime(512, rand);

        BigInteger n = p.multiply(q);
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        BigInteger e = new BigInteger("65537");
        BigInteger d = e.modInverse(phi);

        String text = "Hello World";
        BigInteger message = new BigInteger(text.getBytes());

        BigInteger cipher = message.modPow(bobE, bobN);

        BigInteger signature = message.modPow(d, n);

        out.writeObject(n);      
        out.writeObject(e);
        out.writeObject(cipher);
        out.writeObject(signature);

        socket.close();
    }
}