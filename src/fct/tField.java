package fct;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;


public class tField extends JTextField{
	
	
	public tField(){
		
		addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e1) {
				if(getText().equals("")){
					setBackground(Color.yellow);
				}else{
					setBackground(Color.white);
				}
			}
			});
	}
	
	tField(int Upper){
		
	}
	
	
	public static JTextField tf = new JTextField();
	
	public static void precise(final JTextField tf){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					for(int i = 0; i<3; i++){
						tf.setBackground(Color.red);
						Thread.sleep(50);
						tf.setBackground(Color.white);
						Thread.sleep(100);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
	}
	
	
	
	public static void goTo(JTextField tf){
		tf.requestFocusInWindow();
	}
	

	public static class UpperTf extends DocumentFilter {
	    public void insertString(DocumentFilter.FilterBypass fb, int offset,
	            String text, AttributeSet attr) throws BadLocationException {
	        fb.insertString(offset, text.toUpperCase(), attr);
	    }

	    public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
	            String text, AttributeSet attrs) throws BadLocationException {
	        	fb.replace(offset, length, text.toUpperCase(), attrs);
	    }
	}
	
}
