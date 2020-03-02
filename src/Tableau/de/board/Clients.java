 package Tableau.de.board;

 import interfaces.FirstPg;

 import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;

 import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;

 import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

 import fct.dt;
import fct.nbr;
import fct.table;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

import Etat.EtatPdf;
 import Mission.CMissionPan;

 import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

 public class Clients extends JPanel {

 	JTable tab_fact;
 	DefaultTableModel def_t;
 	Object [][] d_tab= new Object[20][7];
 	String[] t_tab_b = { "Client","Nbre de mission","Distance parcouru","Total HT"};
 	 private dt dtDu;
     private dt dtAu;
     
 	public Clients() {
 		setLayout(null);
 		tab_fact = new JTable(d_tab,t_tab_b);
 		table panel = new table(tab_fact);
 		panel.setBounds(10, 60, 776, 369);
 		add(panel);
 		
 		dtDu = new dt();
 		dtDu.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent arg0) {
 				rechTab(dtDu.getDate(),dtAu.getDate());
 			}
 		});
 		dtDu.setBounds(152, 11, 117, 28);
 		add(dtDu);
 		
 		dtAu = new dt();
 		dtAu.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent arg0) {
 				rechTab(dtDu.getDate(),dtAu.getDate());
 			}
 		});
 		dtAu.setBounds(305, 11, 117, 28);
 		add(dtAu);
 		
 		JLabel lblRechercheDu = new JLabel("Date recherche du");
 		lblRechercheDu.setHorizontalAlignment(SwingConstants.RIGHT);
 		lblRechercheDu.setFont(new Font("Consolas", Font.PLAIN, 14));
 		lblRechercheDu.setBounds(0, 11, 147, 28);
 		add(lblRechercheDu);
 		
 		JLabel lblAu = new JLabel("Au");
 		lblAu.setHorizontalAlignment(SwingConstants.RIGHT);
 		lblAu.setFont(new Font("Consolas", Font.PLAIN, 14));
 		lblAu.setBounds(267, 10, 35, 28);
 		add(lblAu);
 		
 		JButton btnEtatPdf = new JButton("Etat PDF");
 		btnEtatPdf.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent arg0) {
          		EtatPdf.etat(tab_fact, "Etat des mouvements des missions du "+dt.form_Aff(dtDu.getDate())+" Au "+dt.form_Aff(dtAu.getDate()));
 			}
 		});
 		btnEtatPdf.setFont(new Font("Consolas", Font.PLAIN, 14));
 		btnEtatPdf.setBounds(582, 11, 184, 28);
 		add(btnEtatPdf);
 		
 		new Thread(new Runnable() {
 			
 			@Override
 			public void run() {
 				// TODO Auto-generated method stub
 				rechTab(dtDu.getDate(),dtAu.getDate());
 			}
 		}).start();
 	}
 	
 	void rechTab(Date dbRch, Date fRech){
 		try {
 			def_t = new DefaultTableModel();
 			def_t.setColumnIdentifiers(t_tab_b);
 			Statement stm = FirstPg.con.createStatement();
 			String req = "select CODE_C from TRANS_CLIENT where VISIBLE = 1 ORDER BY NOM";
 			
 			ResultSet rst = stm.executeQuery(req);
 			Vector vct = new Vector();
 			Object[] obj = new Object[4];
 			while(rst.next()){
 				vct.addElement(rst.getString(1));
 			}
 			
 			for(int i = 0; i < vct.size(); i++){
 						obj = new Object[5];
 						obj[0] = RechClientStr(" CODE_C like '"+vct.elementAt(i)+"' ");
 						
 						req = "select count(NUMMIS) from TRANS_MISSION where CODE_C like '"+vct.elementAt(i)+"' and DATEALLEZ between '"+dt.form_Aff(dbRch)+"' and '"+dt.form_Aff(fRech)+"' ";
 						rst = stm.executeQuery(req);
 						obj[1]= 0;
 						while(rst.next())try {obj[1]=rst.getInt(1);} catch (Exception e) {obj[1] = 0;}
 						
 						req = "select sum(KM) from TRANS_VW_CLT where CODE_C like '"+vct.elementAt(i)+"' and DATEALLEZ between '"+dt.form_Aff(dbRch)+"' and '"+dt.form_Aff(fRech)+"'";
 						rst = stm.executeQuery(req);
 						obj[2]= 0;
 						while(rst.next())try {obj[2]=rst.getInt(1);} catch (Exception e) {obj[2] = 0;}
 						
 						req = "select sum(PRIXHT) from TRANS_VW_CLT where CODE_C like '"+vct.elementAt(i)+"' and DATEALLEZ between '"+dt.form_Aff(dbRch)+"' and '"+dt.form_Aff(fRech)+"'";
 						rst = stm.executeQuery(req);
 						obj[3]= 0;
 						while(rst.next())try {obj[3]=rst.getInt(1);} catch (Exception e) {obj[3] = 0;}
 						
 					def_t.addRow(obj);
 				}
 			
 			tab_fact.setModel(def_t);
 			
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 	}
 	
 	
 	String RechClientStr(String condition)throws SQLException{
    	String str = "";
		Statement stm = FirstPg.con.createStatement();
		if (!condition.equals("")) condition = " and "+condition;
		String req = "select NOM from TRANS_CLIENT where visible = 1 "+condition+" ";
		ResultSet rst = stm.executeQuery(req);
		while (rst.next()){
			str=rst.getString(1);
		}
		return str;
	}
 }
