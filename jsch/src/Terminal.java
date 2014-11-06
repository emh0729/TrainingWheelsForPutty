

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class Terminal extends javax.swing.JPanel {
	/** Creates new form CelsiusConverterGUI */
    public Terminal() {
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
    	CapturePane capturePane = new CapturePane();
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
    	TexfFieldStreamer ts = new TexfFieldStreamer(tempTextField);
    	tempTextField.addActionListener(ts);
    	
    	System.setIn(ts);
    	
    	//setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    	//setTitle("SSH");
    	
    	javax.swing.SpringLayout layout = new javax.swing.SpringLayout();
    	layout.putConstraint(SpringLayout.NORTH, capturePane, 5, SpringLayout.NORTH, this);
    	layout.putConstraint(SpringLayout.WEST, capturePane, 5, SpringLayout.WEST, this);
    	//layout.putConstraint(SpringLayout.SOUTH, prevCmds, 5, SpringLayout.NORTH, tempTextField);
    	layout.putConstraint(SpringLayout.EAST, this, 5, SpringLayout.EAST, capturePane);
    	layout.putConstraint(SpringLayout.SOUTH, this, 35, SpringLayout.SOUTH, capturePane);
    	layout.putConstraint(SpringLayout.NORTH, tempTextField, 5, SpringLayout.SOUTH, capturePane);
    	layout.putConstraint(SpringLayout.WEST, tempTextField, 5, SpringLayout.WEST, this);
    	//getRootPane().setLayout(layout);
    	//getRootPane().add(tempTextField);
    	//getRootPane().add(capturePane);
    	this.setLayout(layout);
    	this.add(tempTextField);
    	this.add(capturePane);
    	
    	//this.pack();
    	
    	PrintStream ps = System.out;
        System.setOut(new PrintStream(new StreamCapturer("STDOUT", capturePane, ps)));
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
     
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel celsiusLabel;
    private javax.swing.JButton convertButton;
    private javax.swing.JLabel fahrenheitLabel;
    private javax.swing.JTextField tempTextField;
    // End of variables declaration//GEN-END:variables
    
    private javax.swing.JTextArea prevCmds;

    public interface Consumer {        
        public void appendText(String text);        
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
                output.append(text);
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
