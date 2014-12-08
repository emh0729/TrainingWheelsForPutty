import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.jcraft.jsch.*;

public class MainWindow {

	private static UserInfo ui;
	private static SSHManager sm;
	private static Terminal terminal;
	private static Session session;
	
	private static void createAndShowGUI() {
		//Create and set up the window.
		JFrame frame = new JFrame("TopLevelDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create the menu bar.  Make it have a green background.
		JMenuBar greenMenuBar = new JMenuBar();
		greenMenuBar.setOpaque(true);
		greenMenuBar.setBackground(new Color(154, 165, 127));
		greenMenuBar.setPreferredSize(new Dimension(200, 20));
		
		ServerFileBrowser sfb = new ServerFileBrowser();
		//sm.connect(ui);
		sm.sesConnection = session;
		sfb.sshmanager = sm;
		JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                sfb, //detailView);
                terminal);
        frame.add(splitPane, BorderLayout.CENTER);
		
		//Set the menu bar and add the label to the content pane.
		frame.setJMenuBar(greenMenuBar);
		
		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		
		
		terminal = new Terminal();
		try{
		      JSch jsch=new JSch();

		      //jsch.setKnownHosts("/home/foo/.ssh/known_hosts");

		      String host=null;
		      if(args.length>0){
		        host=args[0];
		      }
		      else{
		        host=JOptionPane.showInputDialog("Enter username@hostname",
		                                         System.getProperty("user.name")+
		                                         "@localhost"); 
		      }
		      String user=host.substring(0, host.indexOf('@'));
		      host=host.substring(host.indexOf('@')+1);

		      session=jsch.getSession(user, host, 22);

		      //String passwd = JOptionPane.showInputDialog("Enter password");
		      JPasswordField pwd = new JPasswordField(32);
		      int action = JOptionPane.showConfirmDialog(null, pwd, "Enter Password", JOptionPane.OK_CANCEL_OPTION);
		      
		      String passwd = "";
		      if(action < 0) JOptionPane.showMessageDialog(null, "Cancel, X or escape key selected");
		      else 
			  {
			  	passwd = new String(pwd.getPassword());
			  }
		      
		      session.setPassword(passwd);

		      ui = new MyUserInfo(){
		        public void showMessage(String message){
		          JOptionPane.showMessageDialog(null, message);
		        }
		        public boolean promptYesNo(String message){
		          Object[] options={ "yes", "no" };
		          int foo=JOptionPane.showOptionDialog(null, 
		                                               message,
		                                               "Warning", 
		                                               JOptionPane.DEFAULT_OPTION, 
		                                               JOptionPane.WARNING_MESSAGE,
		                                               null, options, options[0]);
		          return foo==0;
		        }

		        // If password is not given before the invocation of Session#connect(),
		        // implement also following methods,
		        //   * UserInfo#getPassword(),
		        //   * UserInfo#promptPassword(String message) and
		        //   * UIKeyboardInteractive#promptKeyboardInteractive()

		      };
		      
		      sm = new SSHManager(user, passwd, host, null);
		      session.setUserInfo(ui);
		      session.setConfig("PreferredAuthentications", 
		              "publickey,keyboard-interactive,password");

		      // It must not be recommended, but if you want to skip host-key check,
		      // invoke following,
		      // session.setConfig("StrictHostKeyChecking", "no");

		      //session.connect();
		      session.connect(30000);   // making a connection with timeout.
		      Channel channel=session.openChannel("shell");
		      ((ChannelShell) channel).setPtyType("dumb");
		      // Enable agent-forwarding.
		      //((ChannelShell)channel).setAgentForwarding(true);

		      channel.setInputStream(System.in);
		      /*
		      // a hack for MS-DOS prompt on Windows.
		      channel.setInputStream(new FilterInputStream(System.in){
		          public int read(byte[] b, int off, int len)throws IOException{
		            return in.read(b, off, (len>1024?1024:len));
		          }
		        });
		       */
		//see http://stackoverflow.com/questions/12945537/how-to-set-output-stream-to-textarea
		  //for more info on redirecting output to TextArea
		//possibly http://www.dreamincode.net/forums/topic/273874-redirect-systemin-to-jtextarea/
		  //for system.in redirect
		//also see https://github.com/samuelclay/NewsBlur/blob/master/utils/ssh.sh
		  //for more info on color codes
		//http://www.termsys.demon.co.uk/vtansi.htm
		      
		      
		      channel.setOutputStream(System.out);

		      /*
		      // Choose the pty-type "vt102".
		      ((ChannelShell)channel).setPtyType("vt102");
		      */

		      /*
		      // Set environment variable "LANG" as "ja_JP.eucJP".
		      ((ChannelShell)channel).setEnv("LANG", "ja_JP.eucJP");
		      */

		      //channel.connect();
		      channel.connect(3*1000);
	    }
	    catch(Exception e){
		      System.out.println(e);
	    }
		
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
		
		
    }
	
	  public static abstract class MyUserInfo
      implements UserInfo, UIKeyboardInteractive{
		public String getPassword(){ return null; }
		public boolean promptYesNo(String str){ return false; }
		public String getPassphrase(){ return null; }
		public boolean promptPassphrase(String message){ return false; }
		public boolean promptPassword(String message){ return false; }
		public void showMessage(String message){ }
		public String[] promptKeyboardInteractive(String destination,
		                          String name,
		                          String instruction,
		                          String[] prompt,
		                          boolean[] echo){
		return null;
		}
		}
}

