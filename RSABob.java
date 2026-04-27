import java.io.*;
import java.net.*;
import java.math.BigInteger;
import java.security.SecureRandom;

public class RSABob {

    public static void main(String[] args) throws Exception {

        ServerSocket server = new ServerSocket(7999);
        Socket socket = server.accept();

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        SecureRandom rand = new SecureRandom();
        BigInteger p = BigInteger.probablePrime(512, rand);
        BigInteger q = BigInteger.probablePrime(512, rand);

        BigInteger n = p.multiply(q);
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        BigInteger e = new BigInteger("65537");
        BigInteger d = e.modInverse(phi);

        out.writeObject(n);
        out.writeObject(e);

        BigInteger aliceN = (BigInteger) in.readObject();
        BigInteger aliceE = (BigInteger) in.readObject();
        BigInteger cipher = (BigInteger) in.readObject();
        BigInteger signature = (BigInteger) in.readObject();

        BigInteger message = cipher.modPow(d, n);

        BigInteger check = signature.modPow(aliceE, aliceN);



        if (check.equals(message))
            System.out.println("Signature Accepted");
        else
            System.out.println("Signature Failed");
        System.out.println("Message: " + new String(message.toByteArray()));
        
        socket.close();
        server.close();
    }
}