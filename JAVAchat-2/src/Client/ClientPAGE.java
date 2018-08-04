package Client;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.awt.event.*;

public class ClientPAGE extends JFrame{
	private JPanel panel1;
	private JTextField userName;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message="";
	private String ServerIP;
	private Socket connection;
	
	public ClientPAGE(String host)
	{
		super("client BOX");
		
		

		setLayout(null);
		
		panel1=new JPanel();
		panel1.setBounds(0,0,600,32);
		panel1.setBackground(Color.LIGHT_GRAY);
		panel1.setLayout(null);
		getContentPane().add(panel1);
		
		
		ServerIP=host;
		userName=new JTextField("Type Here. . .",20);
		userName.setEditable(false);
		userName.setBounds(1,2,595,27);
		userName.setBorder(null);
		
		userName.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent event)
			{
				sendMessage(event.getActionCommand());
				userName.setText("");
			}
		});
		
		
		chatWindow=new JTextArea();
		chatWindow.setEditable(false);
		chatWindow.setBounds(0,40,600,1000);
		chatWindow.setBackground(Color.LIGHT_GRAY);
		add(chatWindow);
		
		setSize(600,400);
		setVisible(true);
		add(userName);
	}
  //CONNECT TO THE SERVER
	public void StartRunning() throws ConnectException, IOException
	{
		try {
			connectToServer();
			setUpStreams();
			whileChatting();
			
		}catch(EOFException eof){
			showMessage("client Terminated connection");
		}
		catch(IOException exception)
		{
			exception.printStackTrace();
		}
		finally {
			closeCrap();
		}
	}
	//connect to server
	private void connectToServer() throws IOException
	{
		showMessage("\nConnecting to server\n");
		
		connection=new Socket(InetAddress.getByName(ServerIP),6000);
		
		showMessage("\nConnected to - "+connection.getInetAddress().getHostName());
		
		
	}
	
	
	//Setting UP streams
	private void setUpStreams() throws IOException
	{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input=new ObjectInputStream(connection.getInputStream());
		showMessage("\n Dude Your message are ready to go now\n");
	}
	
	//while Chatting with server
  private void whileChatting() throws IOException
  {
	  ableToType(true);
	  do {
		  try {
			  message=(String) input.readObject();
			  showMessage("\n"+message);
		  }catch(ClassNotFoundException CNF)
		  {
			  showMessage("Object Type is not known");
		  }
		  
	  }while(!message.equals("SERVER - END"));
	 
  }
  //close the streams and socket
  private void closeCrap() throws ConnectException ,IOException 
  {
	  showMessage("\n Server is Down.... TRY LATER...\n");
	  ableToType(false);
	 
		  input.close();
		  output.close();
		  connection.close();
		  
	  
  }
  
  //send Message
  private void sendMessage(String message)
  {
	  try {
		  
		output.writeObject("CLIENT-"+message);  
		output.flush();
		showMessage("\nCLIENT- "+message);
	  }catch(IOException ioexception){
		 chatWindow.append("something wrong on sending message"); 
	  }
  }
  
  //Show message
  private void showMessage(final String m)
  {
	  SwingUtilities.invokeLater(
				new Runnable()
				  {
					public void run() {
						// TODO Auto-generated method stub
						 chatWindow.append(m);
					}
			   }	  
				  
	 ); 
  }
  
  //able to type
  private void ableToType(final boolean tof)
  {
	  SwingUtilities.invokeLater(
				new Runnable()
				  {
					public void run() {
						// TODO Auto-generated method stub
						userName.setEditable(tof);
					}
			   }	  
				  
		 );
	  
  }
}
