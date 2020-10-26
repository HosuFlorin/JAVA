
package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {
    
    public static void main(String args[]) throws Exception
	{
		Socket sk=new Socket("10.110.12.13",5000);
		BufferedReader sin=new BufferedReader(new InputStreamReader(sk.getInputStream()));
		PrintStream sout=new PrintStream(sk.getOutputStream());
		BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
		String s;
		while (  true )
		{
			s=sin.readLine();
			System.out.print("Server : "+s+"\n");
                        s=stdin.readLine();
			sout.println(s);
  			if ( s.equalsIgnoreCase("BYE") )
                        {
                           System.out.println("Connection ended by client");
 			   break;
                        }
		}
		 sk.close();
		 sin.close();
		 sout.close();
 		stdin.close();
	}
    
}