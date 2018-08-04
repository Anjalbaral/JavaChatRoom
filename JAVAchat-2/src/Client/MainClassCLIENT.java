package Client;
import java.io.IOException;

import javax.swing.JFrame;

public class MainClassCLIENT {
	public static void main(String [] args)throws IOException
	{
		try {
			
		ClientPAGE obj;
		obj = new ClientPAGE("127.0.0.1");
		obj . setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj . StartRunning();
		}catch(NullPointerException nop)
		{
			nop.printStackTrace();
		}
	}

}
