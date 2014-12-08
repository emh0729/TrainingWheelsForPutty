package com.twfp.main;
/* -*-mode:java; c-basic-offset:2; indent-tabs-mode:nil -*- */
/**
 * This program enables you to connect to sshd server and get the shell prompt.
 *   $ CLASSPATH=.:../build javac Shell.java 
 *   $ CLASSPATH=.:../build java Shell
 * You will be asked username, hostname and passwd. 
 * If everything works fine, you will get the shell prompt. Output may
 * be ugly because of lacks of terminal-emulation, but you can issue commands.
 *
 */
import com.jcraft.jsch.*;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Shell{
	public static final String APP_TITLE = "SSH";
  public static void main(String[] arg){
	  java.awt.EventQueue.invokeLater(new Runnable() {
          public void run() {
              Terminal t = new Terminal();
              t.setVisible(true);
              t.setTitle(APP_TITLE);
              t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        	  new FileBrowser();
          }
      });
//	  SwingUtilities.invokeLater(new Runnable() {
//          public void run() {
//              try {
//                  // Significantly improves the look of the output in
//                  // terms of the file names returned by FileSystemView!
//                  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//              } catch(Exception weTried) {
//              }
//              JFrame f = new JFrame(APP_TITLE);
//              f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//              FileBrowser FileBrowser = new FileBrowser();
//              f.setContentPane(FileBrowser.getGui());
//
//              try {
//                  URL urlBig = FileBrowser.getClass().getResource("fb-icon-32x32.png");
//                  URL urlSmall = FileBrowser.getClass().getResource("fb-icon-16x16.png");
//                  ArrayList<Image> images = new ArrayList<Image>();
//                  images.add( ImageIO.read(urlBig) );
//                  images.add( ImageIO.read(urlSmall) );
//                  f.setIconImages(images);
//              } catch(Exception weTried) {}
//
//              f.pack();
//              f.setLocationByPlatform(true);
//              f.setMinimumSize(f.getSize());
//              f.setVisible(true);
//
//              FileBrowser.showRootFile();
//          }
//      });
    try{
      JSch jsch=new JSch();

      //jsch.setKnownHosts("/home/foo/.ssh/known_hosts");

      String host=null;
      if(arg.length>0){
        host=arg[0];
      }
      else{
        host=JOptionPane.showInputDialog("Enter username@hostname",
                                         System.getProperty("user.name")+
                                         "@localhost"); 
      }
      String user=host.substring(0, host.indexOf('@'));
      host=host.substring(host.indexOf('@')+1);

      Session session=jsch.getSession(user, host, 22);

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

      UserInfo ui = new MyUserInfo(){
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

      session.setUserInfo(ui);
      session.setConfig("PreferredAuthentications", 
              "publickey,keyboard-interactive,password");

      // It must not be recommended, but if you want to skip host-key check,
      // invoke following,
      // session.setConfig("StrictHostKeyChecking", "no");

      //session.connect();
      session.connect(30000);   // making a connection with timeout.
      Channel channel=session.openChannel("shell");

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
