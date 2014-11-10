import java.awt.Color;
import java.awt.LayoutManager;
import java.io.PrintStream;

import javax.swing.*;


public class ServerFileBrowser extends JPanel {

	// Variables declaration - do not modify//GEN-BEGIN:variables
    JLabel celsiusLabel;
    JButton convertButton;
    JLabel fahrenheitLabel;
    JTextField tempTextField;
    // End of variables declaration//GEN-END:variables
    
    private javax.swing.JTextArea prevCmds;
    
	public ServerFileBrowser() {
		// TODO Auto-generated constructor stub
		initComponents();
	}
	
	public void initComponents()
    {
    	tempTextField = new javax.swing.JTextField(48);
    	prevCmds = new javax.swing.JTextArea(24,24);
    	prevCmds.setBackground(new Color(0,0,0,100));
    	prevCmds.setForeground(new Color(255,255,255,100));
    	prevCmds.setEditable(false);
    	prevCmds.setLineWrap(true);
    	javax.swing.JScrollPane sp = new javax.swing.JScrollPane( prevCmds, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
    	
    	/*tempTextField.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});*/
    	
    	//setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    	//setTitle("SSH");
    	
    	javax.swing.SpringLayout layout = new javax.swing.SpringLayout();
    	//layout.putConstraint(SpringLayout.SOUTH, prevCmds, 5, SpringLayout.NORTH, tempTextField);
    	layout.putConstraint(SpringLayout.WEST, tempTextField, 5, SpringLayout.WEST, this);
    	//getRootPane().setLayout(layout);
    	//getRootPane().add(tempTextField);
    	//getRootPane().add(capturePane);
    	this.setLayout(layout);
    	this.add(tempTextField);
    	
    	//this.pack();
    	
    }

}
