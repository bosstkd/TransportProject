package Access;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import interfaces.identificateur;
import fct.DataBase;
import fct.XmlFile;
import fct.codification;

public class startSystem {

	 static String path = "C:/JTrans/conf.xml";
	 String[] keys = new String[]{"dataBase","ipAdresse","userDB","pswDB","dtAct","dtActive","dur"};
	 String[] values = new String[]{"x","x","x","x","x","Vv6dbJkIyAoceyGpNxztKw==","x"};
	 
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String dtAct = XmlFile.readValue(path, "dtAct");
		try {
			dtAct = codification.decrypt(dtAct);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(dtAct.equals("01/01/1970")){
			new Activation().setVisible(true);
		}else{
			new identificateur().setVisible(true);
		}
		
	}

}
