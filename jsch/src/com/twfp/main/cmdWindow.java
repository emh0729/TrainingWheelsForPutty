package com.twfp.main;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Component;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;

import java.awt.AWTEventMulticaster;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class cmdWindow {

	public Component favButton1 = new JButton(" ");
	public Component favButton2 = new JButton(" ");
	public Component favButton3 = new JButton(" ");
	public Component favButton4 = new JButton(" ");
	public Component favButton5 = new JButton(" ");
	
	public Component recButton1 = new JButton(" ");
	public Component recButton2 = new JButton(" ");
	public Component recButton3 = new JButton(" ");
	public Component recButton4 = new JButton(" ");
	public Component recButton5 = new JButton(" ");
	
	public Component cmnButton1 = new JButton("mkdir -p /lib/modules");
	public Component cmnButton2 = new JButton("touch lol.html");
	public Component cmnButton3 = new JButton("g++ a.cpp");
	public Component cmnButton4 = new JButton(" ");
	public Component cmnButton5 = new JButton(" ");
	
	public static void addButtons(SpringLayout layOut, Container pane, Component recentcomp, Component commoncomp, Component favcomp, Component[] favarray, Component[] cmnarray, Component[] recarray) {
				
		pane.add(favcomp);
		pane.add(recentcomp);
		pane.add(commoncomp);
		
		for(int i=0; i < favarray.length; ++i){
			
			pane.add(favarray[i]);
		}
		for(int i=0; i < recarray.length; ++i){
			
			pane.add(recarray[i]);
		}
		for(int i=0; i < cmnarray.length; ++i){
			
			pane.add(cmnarray[i]);
		}
		
		
		
		
		for(int i=0; i < favarray.length; ++i){
			
			if(i==0){
				layOut.putConstraint(SpringLayout.NORTH , favarray[i], 0, SpringLayout.SOUTH, favcomp);
			}
			else{
				layOut.putConstraint(SpringLayout.NORTH , favarray[i], 0, SpringLayout.SOUTH, favarray[i-1]);
			}
			
		}
		for(int i=0; i < recarray.length; ++i){
			
			if(i==0){
				layOut.putConstraint(SpringLayout.NORTH , recarray[i], 0, SpringLayout.SOUTH, favcomp);
			}
			else{
				layOut.putConstraint(SpringLayout.NORTH , recarray[i], 0, SpringLayout.SOUTH, recarray[i-1]);
			}
			
		}
		for(int i=0; i < cmnarray.length; ++i){
			
			if(i==0){
				layOut.putConstraint(SpringLayout.NORTH , cmnarray[i], 0, SpringLayout.SOUTH, favcomp);
			}
			else{
				layOut.putConstraint(SpringLayout.NORTH , cmnarray[i], 0, SpringLayout.SOUTH, cmnarray[i-1]);
			}
			
		}
		
		for(int i=0; i < cmnarray.length; ++i){
			cmnarray[i].setVisible(false);
		}
		for(int i=0; i < recarray.length; ++i){
			recarray[i].setVisible(false);
		}
		
	}
	public static void addMenuMouseListeners(Component recentcomp, Component commoncomp, Component favcomp, final Component[] favarray, final Component[] cmnarray, final Component[] recarray){
		
		
		favcomp.addMouseListener(new MouseAdapter() {
		     public void mousePressed(MouseEvent arg0) {
		    	 
		    	 for(int i=0; i < favarray.length; ++i){
		    		 
		    		 favarray[i].setVisible(true);
		    	 }
		    	 for(int i=0; i < cmnarray.length; ++i){
		    		 
		    		 cmnarray[i].setVisible(false);
		    	 }
		    	 for(int i=0; i < recarray.length; ++i){
		    		 
		    		 recarray[i].setVisible(false);
		    	 }
		    	 
		     }
		  });
		
		recentcomp.addMouseListener(new MouseAdapter() {
		     public void mousePressed(MouseEvent arg0) {

		    	 for(int i=0; i < favarray.length; ++i){
		    		 
		    		 favarray[i].setVisible(false);
		    	 }
		    	 for(int i=0; i < cmnarray.length; ++i){
		    		 
		    		 cmnarray[i].setVisible(false);
		    	 }
		    	 for(int i=0; i < recarray.length; ++i){
		    		 
		    		 recarray[i].setVisible(true);
		    	 }
		    	 
		     }
		  });
		
		commoncomp.addMouseListener(new MouseAdapter() {
		     public void mousePressed(MouseEvent arg0) {
		    	 
		    	 for(int i=0; i < favarray.length; ++i){
		    		 
		    		 favarray[i].setVisible(false);
		    	 }
		    	 for(int i=0; i < cmnarray.length; ++i){
		    		 
		    		 cmnarray[i].setVisible(true);
		    	 }
		    	 for(int i=0; i < recarray.length; ++i){
		    		 
		    		 recarray[i].setVisible(false);
		    	 }
		    	 
		     }
		  });
		
	}
	public static void setprefSize(Component favButton1, Component favButton2, Component favButton3, Component favButton4, Component favButton5, 
			Component cmnButton1, Component cmnButton2, Component cmnButton3, Component cmnButton4, Component cmnButton5,
			Component recButton1, Component recButton2, Component recButton3, Component recButton4, Component recButton5){
		
		for(int i=0; i < 5; ++i){
			if(i==0){
				favButton1.setPreferredSize(new Dimension(254, 25));
				recButton1.setPreferredSize(new Dimension(254, 25));
				cmnButton1.setPreferredSize(new Dimension(254, 25));
			}
			else if(i==1){
				favButton2.setPreferredSize(new Dimension(254, 25));
				recButton2.setPreferredSize(new Dimension(254, 25));
				cmnButton2.setPreferredSize(new Dimension(254, 25));
			}
			else if(i==2){
				favButton3.setPreferredSize(new Dimension(254, 25));
				recButton3.setPreferredSize(new Dimension(254, 25));
				cmnButton3.setPreferredSize(new Dimension(254, 25));
			}	
			else if(i==3){
				favButton4.setPreferredSize(new Dimension(254, 25));
				recButton4.setPreferredSize(new Dimension(254, 25));
				cmnButton4.setPreferredSize(new Dimension(254, 25));
			}	
			else if(i==4){
				favButton5.setPreferredSize(new Dimension(254, 25));
				recButton5.setPreferredSize(new Dimension(254, 25));
				cmnButton5.setPreferredSize(new Dimension(254, 25));
			}		

		}
		
	}
	public static void addCmdMouseListeners( MouseAdapter mouseadapt, Component favButton1, Component favButton2, Component favButton3, Component favButton4, Component favButton5, 
			Component cmnButton1, Component cmnButton2, Component cmnButton3, Component cmnButton4, Component cmnButton5,
			Component recButton1, Component recButton2, Component recButton3, Component recButton4, Component recButton5){
		
		favButton1.addMouseListener(mouseadapt);
		favButton2.addMouseListener(mouseadapt);
		favButton3.addMouseListener(mouseadapt);
		favButton4.addMouseListener(mouseadapt);
		favButton5.addMouseListener(mouseadapt);
		
		cmnButton1.addMouseListener(mouseadapt);
		cmnButton2.addMouseListener(mouseadapt);
		cmnButton3.addMouseListener(mouseadapt);
		cmnButton4.addMouseListener(mouseadapt);
		cmnButton5.addMouseListener(mouseadapt);
		
		recButton1.addMouseListener(mouseadapt);
		recButton2.addMouseListener(mouseadapt);
		recButton3.addMouseListener(mouseadapt);
		recButton4.addMouseListener(mouseadapt);
		recButton5.addMouseListener(mouseadapt);
	}
	
	private JTextField terminalinput;
	public cmdWindow(JTextField terminalinput){
		
		this.terminalinput = terminalinput;
		
		initialize();
	}
	public Container initialize(){
			
		setprefSize( favButton1,  favButton2,  favButton3,  favButton4,  favButton5, 
				 cmnButton1,  cmnButton2,  cmnButton3,  cmnButton4,  cmnButton5,
				 recButton1,  recButton2,  recButton3,  recButton4,  recButton5);
		
		//arrays that hold the buttons
		final Component[] favoritesArray = {favButton1, favButton2, favButton3, favButton4, favButton5};
		final Component[] recentArray = {recButton1, recButton2, recButton3, recButton4, recButton5};
		final Component[] commonArray = {cmnButton1, cmnButton2, cmnButton3, cmnButton4, cmnButton5};
		
		MouseAdapter mouseadapt = new MouseAdapter(){
		     public void mousePressed(MouseEvent arg0) {
		    	 
		    	 terminalinput.setText( ((JButton)(arg0.getSource())).getText()) ;
		     }
		  };
		
		
		JFrame frame = new JFrame("SpringLayout");
		Container contentPane = frame.getContentPane();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);;
		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);

		
		Component Favorites = new JButton("Favorites");		
		Component Recent = new JButton("Recent");
		Component Common = new JButton("Common");
		
		addMenuMouseListeners(Recent, Common, Favorites, favoritesArray, commonArray, recentArray);
		addCmdMouseListeners(mouseadapt, favButton1,  favButton2,  favButton3,  favButton4,  favButton5, 
				 cmnButton1,  cmnButton2,  cmnButton3,  cmnButton4,  cmnButton5,
				 recButton1,  recButton2,  recButton3,  recButton4,  recButton5);
		addButtons(layout, contentPane, Recent, Common, Favorites, favoritesArray, commonArray, recentArray);
		
		
		//Favorites Button
		//the WEST edge of 'favorites' will be 0 pixels from the WEST edge of contentPane
		layout.putConstraint(SpringLayout.WEST, Favorites, 0, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, Favorites, 0, SpringLayout.NORTH, contentPane);
		
		//Recent Button
		layout.putConstraint(SpringLayout.NORTH, Recent, 0, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.WEST, Recent, 0, SpringLayout.EAST, Favorites);
		
		//Common Button
		layout.putConstraint(SpringLayout.NORTH, Common, 0, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.EAST, Common, 0, SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.WEST , Common, 0, SpringLayout.EAST, Recent);
		

		frame.setSize(270, 189);
		//frame.setVisible(true);
		

		return contentPane;
	}
	
}
