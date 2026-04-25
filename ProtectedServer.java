import java.io.*;
import java.net.*;
import java.security.*;

public class ProtectedServer
{
	public boolean authenticate(InputStream inStream) throws IOException, NoSuchAlgorithmException 
	{
		DataInputStream in = new DataInputStream(inStream);

		String user = in.readUTF();
		long t1 = in.readLong();
		double q1 = in.readDouble();

		int length = in.readInt();
		byte[] digest = new byte[length];
		in.readFully(digest);

		String password = lookupPassword(user);
		byte[] expectedDigest = Protection.makeDigest(user, password, t1, q1);

		if (MessageDigest.isEqual(digest, expectedDigest)) 
			{
				return true;
			} else {
			return false;
			}
			
		
	}

	protected String lookupPassword(String user) { return "abc123"; }

	public static void main(String[] args) throws Exception 
	{
		int port = 7999;
		ServerSocket s = new ServerSocket(port);
		Socket client = s.accept();

		ProtectedServer server = new ProtectedServer();

		if (server.authenticate(client.getInputStream()))
		  System.out.println("Client logged in.");
		else
		  System.out.println("Client failed to log in.");

		s.close();
	}
}