package Etat;

import java.util.Vector;

import javax.swing.JTable;

public class EtatPdf {

	static public void etat(JTable tab, String Titre){
		Vector titre = new Vector();
		Vector contenu = new Vector();
		
		for(int i =0; i< tab.getColumnCount(); i++){
			String col = tab.getColumnName(i);
			titre.addElement(col);
		}
		
		for(int i = 0; i < tab.getRowCount(); i++)
			for(int j = 0; j < tab.getColumnCount(); j++) contenu.addElement(tab.getValueAt(i, j));
		
   		new etat_pdf_rech( titre, contenu, tab.getColumnCount(),Titre.toString());
	}
	
	
}
