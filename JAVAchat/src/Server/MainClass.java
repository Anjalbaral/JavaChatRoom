package Server;
import java.awt.*;
import java.io.IOException;

import javax.swing.*;
public class MainClass {
	public static void main(String []args) throws IOException
	{
     ServerPAGE sp=new ServerPAGE();
     sp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     sp.StartRunning();
     
	}
}
