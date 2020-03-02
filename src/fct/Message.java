package fct;

import javax.swing.JOptionPane;

public class Message extends JOptionPane{

	public Message(int x, String Message) {
		if(x==0){
			JOptionPane.showMessageDialog(null,Message,"Information",JOptionPane.INFORMATION_MESSAGE);
		}
		else if(x==1){
			JOptionPane.showMessageDialog(null,Message,"Attention",JOptionPane.WARNING_MESSAGE);
		}else if(x==2){
			JOptionPane.showMessageDialog(null,Message,"Erreur",JOptionPane.ERROR_MESSAGE);
		}else{
			JOptionPane.showConfirmDialog(null, Message, "Demande de Confirmation",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	public Message(){
		
	}
	
	
}
