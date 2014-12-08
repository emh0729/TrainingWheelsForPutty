package com.twfp.main;


import java.awt.AWTKeyStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.UIManager;

public class Terminal extends JFrame {
	private static final String APP_TITLE = "SSH";
	/** Creates new form CelsiusConverterGUI */
    private JTextArea prevCmds;     
    private JTextField terminalInput;
    private JTextArea helpText;
    private Help helpTxt;
    private CapturePane capturePane;
	
    public Terminal() {
    	helpTxt = new Help();
        initComponents();
    }
    
    public String printVector(Vector<String> vec)
    {
    	String out = "";
    	for(int i = 0; i < vec.size(); i++)
    	{
    		out = out + vec.get(i) + "\n"; 
    	}
    	return out;
    }
    
    public void initComponents()
    {
    	try {
		    // Significantly improves the look of the output in
		    // terms of the file names returned by FileSystemView!
		        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		    } catch(Exception weTried) {
		}
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(this.getSize());
    		
    	terminalInput = new javax.swing.JTextField(82);
    	terminalInput.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.<AWTKeyStroke> emptySet());
    	
    	prevCmds = new javax.swing.JTextArea(24,24);
    	prevCmds.setBackground(new Color(0,0,0,100));
    	prevCmds.setForeground(new Color(255,255,255,100));
    	prevCmds.setEditable(false);
    	prevCmds.setLineWrap(true);
    	
    	helpText = new javax.swing.JTextArea();
    	helpText.setEditable(false);
    	helpText.setLineWrap(true);
    	
    	JScrollPane help = new JScrollPane(helpText);
    	
    	capturePane = new CapturePane();
    	
    	terminalInput.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getKeyCode() == KeyEvent.VK_TAB)
				{
					//auto-fill with a file/directory name in the current directory
					//or just send the tab key, if possible
					terminalInput.setText(terminalInput.getText() + ((char)9));
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				Vector<String> tmp;
				if(terminalInput.getText().indexOf(' ') >= 0)
				{
					tmp = helpTxt.keyDescr(terminalInput.getText().substring(0, terminalInput.getText().indexOf(' ')));
				}
				else
				{
					tmp = helpTxt.keyDescr(terminalInput.getText());
				}
				if(tmp != null)
				{
					helpText.setText(printVector(tmp));
				}
				else
				{
					helpText.setText("");
				}
					
			}
		});
    	TexfFieldStreamer ts = new TexfFieldStreamer(terminalInput);
    	terminalInput.addActionListener(ts);
    	
    	System.setIn(ts);
    	
    	TerminalPane tp = new TerminalPane();
    	JSplitPane sps = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tp, help);
    	//sps.setDividerLocation((1.0 / 2.0));
    	
    	FileBrowser fileBro = new FileBrowser();
    	try {
            URL urlBig = fileBro.getClass().getResource("fb-icon-32x32.png");
            URL urlSmall = fileBro.getClass().getResource("fb-icon-16x16.png");
            ArrayList<Image> images = new ArrayList<Image>();
            images.add( ImageIO.read(urlBig) );
            images.add( ImageIO.read(urlSmall) );
            this.setIconImages(images);
        } catch(Exception weTried) {}
    	
    	//fill in with commands component
    	//replace fileBro as well
    	JSplitPane sp2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, fileBro.getGui(), null);
    	
    	JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                sp2,
                sps);
            
    	fileBro.showRootFile();
    	
    	BorderLayout bl = new BorderLayout();
    	this.setLayout(bl);
    	bl.addLayoutComponent(splitPane, BorderLayout.CENTER);
    	this.setPreferredSize(new Dimension(900,480));
    	sps.setDividerLocation((int)(Math.floor(this.getPreferredSize().height * 2.0 / 3.0)));
    	sp2.setDividerLocation((int)(Math.floor(this.getPreferredSize().height / 2.0)));
    	splitPane.setDividerLocation(150);
            
    	this.add(splitPane);
    	
    	this.pack();
    	
    	PrintStream ps = System.out;
        System.setOut(new PrintStream(new StreamCapturer("STDOUT", capturePane, ps)));
    }
    
    public class TerminalPane extends JPanel
    {
    	public TerminalPane()
    	{
    		initialize();
    	}
    	
    	private void initialize()
    	{
        	SpringLayout layout = new SpringLayout();
        	layout.putConstraint(SpringLayout.NORTH, capturePane, 5, SpringLayout.NORTH, this);
        	layout.putConstraint(SpringLayout.WEST, capturePane, 5, SpringLayout.WEST, this);
        	layout.putConstraint(SpringLayout.EAST, capturePane, -5, SpringLayout.EAST, this);
        	layout.putConstraint(SpringLayout.SOUTH, this, 30, SpringLayout.SOUTH, capturePane);
        	layout.putConstraint(SpringLayout.NORTH, terminalInput, 5, SpringLayout.SOUTH, capturePane);
        	layout.putConstraint(SpringLayout.WEST, terminalInput, 5, SpringLayout.WEST, this);
        	layout.putConstraint(SpringLayout.EAST, this, 5, SpringLayout.EAST, terminalInput);
        	
        	this.setLayout(layout);
        	this.add(terminalInput);
        	this.add(capturePane);
    	}
    	
    }
    
    /**
     * @param args the command line arguments
     */
    public static void mains(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Terminal().setVisible(true);
            }
        });
    }

    
    public interface Consumer {        
        public void appendText(String text);        
    }
    
    public boolean isDigits(String txt, int start, int end)
    {
    	for(int i = start; i < end; i++)
    	{
    		if(!((txt.charAt(i) >= '0' && txt.charAt(i) <= '9') || txt.charAt(i) == ';') && !(strIndexOf(txt, '[', i) != -1 && strIndexOf(txt, '[', i) < end))
    		{
    			return false;
    		}
    	}
    	//System.out.println("Is digit");
    	return true;
    }
    
    public int strPrevIndexOf(String txt, char c, int start)
    {
    	if(start >= txt.length() || start < 0)
    	{
    		return -1;
    	}
    	for(int i = start; i >= 0; i--)
    	{
    		if(txt.charAt(i) == c)
    			return i;
    	}
    	return -1;
    }
    
    public int strIndexOf(String txt, char c, int start)
    {
    	if(start >= txt.length() || start < 0)
    	{
    		return -1;
    	}
    	for(int i = start; i < txt.length(); i++)
    	{
    		if(txt.charAt(i) == c)
    			return i;
    	}
    	return -1;
    }
    
    public class CapturePane extends JPanel implements Consumer {

        private JTextArea output;

        public CapturePane() {
            setLayout(new BorderLayout());
            output = new JTextArea();
            output.setEditable(false);
        	output.setLineWrap(true);
            add(new JScrollPane(output));
        }

        @Override
        public void appendText(final String text) {
            if (EventQueue.isDispatchThread()) {
            	String outtxt = text;
            	int pos = 0;
            	while(strIndexOf(outtxt,'[', pos) >= 0 && strIndexOf(outtxt, 'm', outtxt.indexOf("[")) > outtxt.indexOf("[") && isDigits(outtxt, strPrevIndexOf(outtxt, '[', strIndexOf(outtxt, 'm', strIndexOf(outtxt, '[', 0)) )+ 1, strIndexOf(outtxt, 'm', strIndexOf(outtxt, '[', 0))))
            	{
            		outtxt = outtxt.substring(0, strPrevIndexOf(outtxt, '[', strIndexOf(outtxt, 'm', strIndexOf(outtxt, '[', 0)) )) + outtxt.substring(strIndexOf(outtxt, 'm', outtxt.indexOf("[")) + 1);
            	}
            	for(int i =0; i < outtxt.length(); i++)
            	{
            		if(outtxt.charAt(i) == (char)27)
            		{
            			
            		}
            	}
//            	for(int i =0; i < outtxt.length(); i+=5)
//            	{
//            		outtxt = outtxt.substring(0, i) + " " + ((int)outtxt.charAt(i)) / 100 + (((int)outtxt.charAt(i)) % 100) / 10 + ((int)outtxt.charAt(i)) % 10 + " " + outtxt.substring(i + 1);
//            	}
                output.append(outtxt);
                output.setCaretPosition(output.getText().length());
            } else {

                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        appendText(text);
                    }
                });

            }
        }        
    }
    
    public class StreamCapturer extends OutputStream {

        private StringBuilder buffer;
        private String prefix;
        private Consumer consumer;
        private PrintStream old;

        public StreamCapturer(String prefix, Consumer consumer, PrintStream old) {
            this.prefix = prefix;
            buffer = new StringBuilder(128);
            //buffer.append("[").append(prefix).append("] ");
            this.old = old;
            this.consumer = consumer;
        }

        @Override
        public void write(int b) throws IOException {
            char c = (char) b;
            String value = Character.toString(c);
            buffer.append(value);
            if (value.equals("\n")) {
                consumer.appendText(buffer.toString());
                buffer.delete(0, buffer.length());
                //buffer.append("[").append(prefix).append("] ");
            }
            old.print(c);
        }        
    }   
    
    
//http://stackoverflow.com/questions/9244108/redirect-system-in-to-swing-component
    
    public class TexfFieldStreamer extends InputStream implements ActionListener {

        private JTextField tf;
        private String str = null;
        private int pos = 0;

        public TexfFieldStreamer(JTextField jtf) {
            tf = jtf;
        }

        //gets triggered everytime that "Enter" is pressed on the textfield
        @Override
        public void actionPerformed(ActionEvent e) {
            str = tf.getText() + "\n";
            pos = 0;
            tf.setText("");
            synchronized (this) {
                //maybe this should only notify() as multiple threads may
                //be waiting for input and they would now race for input
                this.notifyAll();
            }
        }

        @Override
        public int read() {
            //test if the available input has reached its end
            //and the EOS should be returned 
            if(str != null && pos == str.length()){
                str =null;
                //this is supposed to return -1 on "end of stream"
                //but I'm having a hard time locating the constant
                return java.io.StreamTokenizer.TT_EOF;
            }
            //no input available, block until more is available because that's
            //the behavior specified in the Javadocs
            while (str == null || pos >= str.length()) {
                try {
                    //according to the docs read() should block until new input is available
                    synchronized (this) {
                        this.wait();
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            //read an additional character, return it and increment the index
            return str.charAt(pos++);
        }
    }
}
