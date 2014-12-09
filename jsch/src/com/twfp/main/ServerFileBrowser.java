package com.twfp.main;

import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.*;

import com.twfp.main.Terminal.CapturePane;
import com.twfp.main.Terminal.TextFieldStreamer;


public class ServerFileBrowser extends JPanel {

	// Variables declaration - do not modify//GEN-BEGIN:variables
    JLabel celsiusLabel;
    JButton convertButton;
    JLabel fahrenheitLabel;
    JButton folderButton;
    ArrayList<ArrayList<JButton>> buttonsList;
    int buttonsListIterator;
    public TextFieldStreamer tfs;
    public CapturePane capturePane;
    // End of variables declaration//GEN-END:variables
    
    private javax.swing.JTextArea prevCmds;
    
	public ServerFileBrowser() {
		// TODO Auto-generated constructor stub
		initComponents();
	}
	
	public void initComponents()
    {	
		BoxLayout bl = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		this.setLayout(bl);
		
		buttonsList = new ArrayList<ArrayList<JButton>>(0);
		buttonsListIterator = 0;
		ArrayList<JButton> buttons = new ArrayList<JButton>(0);
		
		folderButton = new JButton("[test]");
		folderButton.addMouseListener(new MyMouseListener());
		buttons.add(folderButton);

		folderButton = new JButton("csce_work");
		folderButton.addMouseListener(new MyMouseListener());
		buttons.add(folderButton);
    	
		folderButton = new JButton("misc");
		folderButton.addMouseListener(new MyMouseListener());
		buttons.add(folderButton);
	
		buttonsList.add(buttons);
		
		buttons = new ArrayList<JButton>(0);
		folderButton = new JButton("..");
		folderButton.addMouseListener(new MyMouseListener());
		buttons.add(folderButton);
    	
		folderButton = new JButton("csce411");
		folderButton.addMouseListener(new MyMouseListener());
		buttons.add(folderButton);
    	
		folderButton = new JButton("csce436");
		folderButton.addMouseListener(new MyMouseListener());
		buttons.add(folderButton);
    	
		folderButton = new JButton("csce440");
		folderButton.addMouseListener(new MyMouseListener());
		buttons.add(folderButton);

		folderButton = new JButton("old");
		folderButton.addMouseListener(new MyMouseListener());
		buttons.add(folderButton);
		
		folderButton = new JButton("test_folder");
		folderButton.addMouseListener(new MyMouseListener());
		buttons.add(folderButton);

		buttonsList.add(buttons);
			
		buttons = new ArrayList<JButton>(0);
		folderButton = new JButton("..");
		folderButton.addMouseListener(new MyMouseListener());
		buttons.add(folderButton);
		
		buttonsList.add(buttons);
		
		buttonsListIterator = 0;
		this.removeAll();
		this.add(new JLabel("Working Directory"));
		for (JButton jb  : buttonsList.get(buttonsListIterator)) 
		{
			this.add(jb);
		}	
    	
    	
    }
	
	public class MyMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			if(arg0.getClickCount() == 2)
			{
				JButton button = (JButton)(arg0.getSource());
				JPanel panel = (JPanel)(button.getParent());
				tfs.tf.setText("cd "+(button.getText()));
				tfs.actionPerformed(null);
				
				if(button.getText().equals("csce_work"))
				{
					buttonsListIterator = 1;
					panel.removeAll();
					panel.add(new JLabel("Working Directory"));
					for (JButton jb  : buttonsList.get(buttonsListIterator)) 
					{
						panel.add(jb);
					}				
				}
				
				if(button.getText().equals("test_folder"))
				{
					buttonsListIterator = 2;
					panel.removeAll();
					panel.add(new JLabel("Working Directory"));
					for (JButton jb  : buttonsList.get(buttonsListIterator)) 
					{
						panel.add(jb);
					}
				}
				
				if(button.getText().equals(".."))
				{
					buttonsListIterator-=1;
					panel.removeAll();
					panel.add(new JLabel("Working Directory"));
					for (JButton jb  : buttonsList.get(buttonsListIterator)) 
					{
						panel.add(jb);
					}
				}
				
				panel.getParent().validate();
				panel.getParent().repaint();

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
