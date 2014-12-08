import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.PrintStream;

import javax.swing.*;


public class ServerFileBrowser extends JPanel {

	// Variables declaration - do not modify//GEN-BEGIN:variables
    JLabel celsiusLabel;
    JButton convertButton;
    JLabel fahrenheitLabel;
    JButton folderButton;
    public SSHManager sshmanager;
    // End of variables declaration//GEN-END:variables
    
    private javax.swing.JTextArea prevCmds;
    
	public ServerFileBrowser() {
		// TODO Auto-generated constructor stub
		initComponents();
	}
	
	public void initComponents()
    {
		folderButton = new JButton("folder");
		folderButton.addMouseListener(new MyMouseListener());
    	this.add(folderButton);
    	
    	//this.pack();
    	
    }
	
	public class MyMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			if(arg0.getClickCount() == 2)
			{
				String bigbird = sshmanager.sendCommand("ls");
				bigbird.replaceAll("\\u001B\\[\\d+;\\d+m", "");
				System.out.println(bigbird);
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
