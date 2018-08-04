package Server;
import java.io.*;
import java.awt.*;
import java.net.*;
import java.awt.event.*;
import javax.swing.*;

public class ServerPAGE extends JFrame {
    private JPanel panel1;
    private JPanel panel2;
	private JTextField userName;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;
	private Icon background;
	
	
	public ServerPAGE() throws IOException
	{
		super("Server Chat Box");
		setLayout(null);
		
		panel1=new JPanel();
		panel1.setBounds(0,0,600,32);
		panel1.setBackground(Color.LIGHT_GRAY);
		panel1.setLayout(null);
		getContentPane().add(panel1);
		background=new ImageIcon(getClass().getResource("img.jpg"));
		
		userName=new JTextField("Type Here. . .",20);
		userName.setEditable(true);
		userName.setBounds(1,2,595,27);
		userName.setBorder(null);
		try {
		userName.addActionListener(new ActionListener()
				{
			public void actionPerformed(ActionEvent event)
			{
				sendMessage(event.getActionCommand());
				userName.setText("");
			}
				}
		
		);
		}catch(NullPointerException e) {
			showMessage("CLIENT ARE NOT READY");
			e.printStackTrace();
			
		}
		
		//panel2=new JPanel();
		//panel2.setBounds(0,37,600,400);
		//panel2.setBackground(Color.lightGray);
		//panel2.setLayout(null);
		//getContentPane().add(panel2);
		
		chatWindow=new JTextArea();
		
		chatWindow.setEditable(false);
		chatWindow.setBounds(0,40,600,1000);
		chatWindow.setBackground(Color.LIGHT_GRAY);
		
	//	JScrollPane jp = new JScrollPane(chatWindow);
		add(chatWindow);
		Font font2=new Font("serifBold",Font.BOLD,15);
		
		setSize(600,400);
		setVisible(true);
		panel1.add(userName);
	}
	
//set UP and Run Server
	public void StartRunning()
	{
		try
		{
			server=new ServerSocket(6000,100);//100 is no. of devices for waiting in server
			
			while(true)
			{
				try {
					waitForConnection();
					setUpStream();
					whileChatting();
				}catch(Exception exp){
					showMessage("\n server Colesed");
				}
				finally
				{
					closeCrap();
				}
			}
			
		}catch(IOException ioException)
		{
			 ioException.printStackTrace();
		}
	}
	
	//wait for connection
	private void waitForConnection() throws IOException
	{
		Font font2=new Font("serifBold",Font.BOLD,15);
		showMessage("\nwaiting for someone to connect . . . \n");
		connection=server.accept();
		showMessage("connected to "+connection.getInetAddress().getHostName());
	}
	
	//Get Stream TO send and receive data
	
	public void setUpStream() throws IOException
	{
		output=new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input=new ObjectInputStream(connection.getInputStream());
		showMessage("\nNow Stream Setups Are ready");
	}
	
	//during Chat Conversation
	
	public void whileChatting() throws IOException
	{
		String message="you are connected now";
		sendMessage(message);
		ableToType(true);
		do
		{
			//chat
			try {
				message=(String) input.readObject();
				showMessage("\n"+message);
			}catch(ClassNotFoundException CNFexception){
				showMessage("this is error message");
			}
		}while(!message.equals("CLIENT-END"));
	}
	
	//close Streams
	public void closeCrap()
	{
		showMessage("\n closing connection...\n");
		ableToType(false);
		try {
			output.close();
            input.close();
            connection.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	//send Message To clients
	private void sendMessage(String message)
	{
		try {
			
			output.writeObject("SERVER-" + message);
			output.flush();
			showMessage("\nSERVER-"+ message);
		}catch(IOException ioException)
		{
			chatWindow.append("Sorry Dude I cant send your message");
		}
		catch(NullPointerException NPE) {
			showMessage("CLIENT ARE NOT READY");
			NPE.printStackTrace();
		}
	}
	
	//chatWindow updates showMessage method
  private void showMessage(final String text)
  {
	  SwingUtilities.invokeLater(
			new Runnable()
			  {
				public void run() {
					// TODO Auto-generated method stub
					 chatWindow.append(text);
				}
		   }	  
			  
	 );
	  
  }
  
  //let user able to be able to type
  public void ableToType(final boolean tof)
  {
	  SwingUtilities.invokeLater(
				new Runnable()
				  {
					
					public void run() {
						// TODO Auto-generated method stub
						 userName.setEditable(true);
					}
			   }	  
				  
		 );
	  
  }
	
	
}
