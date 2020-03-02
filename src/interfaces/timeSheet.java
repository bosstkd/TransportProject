package interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.print.PrinterException;
import java.lang.Thread.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXDatePicker;

import fct.DataBase;
import fct.Message;
import fct.codification;
import fct.dt;
import fct.nbr;

import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;


public class timeSheet extends JInternalFrame{

	   JLabel lbNumMission = new JLabel("");

	JPanel panel_1 = new JPanel();
	JTable tab_fact;
	DefaultTableModel def_t;
	Object [][] d_tab= new Object[20][100];
    String[] t_tab_b = {"A"} ;
	
    Vector v_titre = new Vector();
   
    
	JComboBox cbClient = new JComboBox();
	
	static dt dt_d =  new dt();
    static dt dt_f =  new dt();
    JButton btnAfficherClient = new JButton("Détails mission");

	
	
	public timeSheet(final Connection con) {
		setFrameIcon(new ImageIcon(timeSheet.class.getResource("/icones/original/A32/calendrier-date-icone-4087-48_RS.png")));
		setIconifiable(true);
		setClosable(true);
		getContentPane().setFont(new Font("Consolas", Font.PLAIN, 14));
	
		setTitle("Recap des chambres");
		
		
		
		setBounds(30, 50, 1291, 550);
		getContentPane().setLayout(null);
		
		JLabel lblEntreprise = new JLabel("Clients");
		lblEntreprise.setFont(new Font("Consolas", Font.BOLD, 13));
		lblEntreprise.setBounds(10, 6, 80, 30);
		getContentPane().add(lblEntreprise);
		
		cbClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				aff(panel_1, 1, con);
				
			}
		});
		
	
		cbClient.setFont(new Font("Century Gothic", Font.ITALIC, 15));
		cbClient.setBounds(70, 6, 154, 30);
		getContentPane().add(cbClient);
		
	
		
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 94, 1259, 414);
		panel.setBorder(new TitledBorder(null, "R\u00E9cape de paie", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setLayout(new BorderLayout(0, 0));
		getContentPane().add(panel);
		
		panel_1.setPreferredSize(new Dimension(2600, 385));

		panel.add(new JScrollPane(panel_1), BorderLayout.CENTER);


tab_fact = new JTable(d_tab,t_tab_b);
tab_fact.addMouseListener(new MouseAdapter() {
	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
	
		String x_date = t_tab_b[tab_fact.getSelectedColumn()];
		String num_ch = tab_fact.getValueAt(tab_fact.getSelectedRow(), 0).toString();
		
		if(tab_fact.getValueAt(tab_fact.getSelectedRow(), tab_fact.getSelectedColumn()).toString().equals("0")){
			lbNumMission.setText("");
			btnAfficherClient.setEnabled(false);
		}else{
			btnAfficherClient.setEnabled(true);
			lbNumMission.setText(numMission(x_date,codification.cd_prs(num_ch)));
			/*try {
					Statement stm = con.createStatement();
					String req = "select CODE_R from HTL_RES_CH where NUM_CH like '"+num_ch+"' and DATE_A <= '"+x_date+"' and DATE_D > '"+x_date+"' ";
					ResultSet rst = stm.executeQuery(req);
				while(rst.next()) {
					lbNumMission.setText(rst.getString(1));
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
		}
		
		
		
	}
});


panel_1.setLayout(new BorderLayout(0, 0));
tab_fact.setFont(new Font("Times New Roman", Font.PLAIN, 14));
tab_fact.setRowHeight(30);

JScrollPane scrollPane1 = new JScrollPane(tab_fact);
panel_1.add(scrollPane1);
JLabel lblDate = new JLabel("Date  Du :");
lblDate.setFont(new Font("Consolas", Font.BOLD, 13));
lblDate.setBounds(234, 6, 70, 30);
getContentPane().add(lblDate);

dt_d.addActionListener(new ActionListener() {
   	public void actionPerformed(ActionEvent arg0) {
   		aff(panel_1, 2, con);
   	}
   });

   dt_d.setBounds(304, 6, 154, 32);
   getContentPane().add(dt_d);
   
   dt_d.setDate(new Date());
   dt_f.addActionListener(new ActionListener() {
   	public void actionPerformed(ActionEvent arg0) {
   		aff(panel_1, 2, con);
   	}
   });
   
   dt_f.setBounds(480, 6, 154, 32);
   getContentPane().add(dt_f);
   
   dt_f.setDate(new Date());
   
   JLabel lblAu = new JLabel("Au:");
   lblAu.setFont(new Font("Consolas", Font.BOLD, 13));
   lblAu.setBounds(458, 6, 34, 30);
   getContentPane().add(lblAu);
   
   JButton btnActualiser = new JButton("Actualiser");
   btnActualiser.setFont(new Font("Consolas", Font.PLAIN, 12));
   btnActualiser.addActionListener(new ActionListener() {
   	public void actionPerformed(ActionEvent arg0) {
   		aff(panel_1, 0, con);
   	}
   });
   btnActualiser.setBounds(70, 53, 198, 30);
   getContentPane().add(btnActualiser);
   
   JButton btnChambreDate = new JButton("Toutes les missions");
   btnChambreDate.addActionListener(new ActionListener() {
   	public void actionPerformed(ActionEvent arg0) {
   		aff(panel_1, 3, con);
   	}
   });
   btnChambreDate.setFont(new Font("Consolas", Font.PLAIN, 12));
   btnChambreDate.setBounds(280, 53, 178, 30);
   getContentPane().add(btnChambreDate);
   
   JLabel lblMissionEffectuer = new JLabel("Mission en cours");
   lblMissionEffectuer.setFont(new Font("Century Gothic", Font.BOLD, 12));
   lblMissionEffectuer.setBounds(712, 6, 127, 16);
   getContentPane().add(lblMissionEffectuer);
   
   JPanel panel_2 = new JPanel();
   panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
   panel_2.setBackground(Color.GREEN);
   panel_2.setBounds(682, 7, 18, 15);
   getContentPane().add(panel_2);
   
   JPanel panel_3 = new JPanel();
   panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
   panel_3.setBackground(Color.ORANGE);
   panel_3.setBounds(682, 31, 18, 15);
   getContentPane().add(panel_3);
   
   JLabel lblMissionAVenir = new JLabel("Mission a venir");
   lblMissionAVenir.setFont(new Font("Century Gothic", Font.BOLD, 12));
   lblMissionAVenir.setBounds(712, 30, 133, 16);
   getContentPane().add(lblMissionAVenir);
   
   JPanel panel_4 = new JPanel();
   panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
   panel_4.setBackground(Color.BLUE);
   panel_4.setBounds(682, 54, 18, 15);
   getContentPane().add(panel_4);
   
   JLabel lblLibre = new JLabel("Libre");
   lblLibre.setFont(new Font("Century Gothic", Font.BOLD, 12));
   lblLibre.setBounds(712, 54, 133, 16);
   getContentPane().add(lblLibre);
   
   JLabel lblCoderDeLa = new JLabel("N\u00B0 de Mission:");
   lblCoderDeLa.setHorizontalAlignment(SwingConstants.RIGHT);
   lblCoderDeLa.setFont(new Font("Century Gothic", Font.BOLD, 12));
   lblCoderDeLa.setBounds(913, 6, 146, 16);
   getContentPane().add(lblCoderDeLa);
   
   lbNumMission.setFont(new Font("Century Gothic", Font.PLAIN, 12));
   lbNumMission.setBounds(1069, 6, 185, 16);
   getContentPane().add(lbNumMission);
   
   JButton btnNewButton = new JButton("Imprimer");
   btnNewButton.addActionListener(new ActionListener() {
   	public void actionPerformed(ActionEvent arg0) {
   		try {
			tab_fact.print();
		} catch (PrinterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	}
   });
   btnNewButton.setFont(new Font("Consolas", Font.PLAIN, 14));
   btnNewButton.setBounds(913, 40, 146, 42);
   getContentPane().add(btnNewButton);
   btnAfficherClient.setEnabled(false);
   
  
   btnAfficherClient.addActionListener(new ActionListener() {
   	public void actionPerformed(ActionEvent arg0) {
   		try {
			Statement stm = FirstPg.con.createStatement();
			String req = "select DATEMIS, DATEALLEZ, DATERETOUR, DISTINATION, DETAIL, PRIXHT, FACTURATION, IDACCOMP from TRANS_MISSION where NUMMIS like '"+lbNumMission.getText()+"' ";
			ResultSet rst = stm.executeQuery(req);
			String msg = "";
			while(rst.next()){
				msg = msg+"Date Création : "+dt.form_Aff(rst.getDate(1))+".\n";
				msg = msg+"Date Départ : "+dt.form_Aff(rst.getDate(2))+".\n";
				msg = msg+"Date Retour : "+dt.form_Aff(rst.getDate(3))+".\n";
				msg = msg+"Distination : "+rst.getString(4)+".\n";
				msg = msg+"Détails mission : "+rst.getString(5)+".\n";
				msg = msg+"Prix HT : "+nbr.dbToDf(rst.getDouble(6))+" Da.\n";
				msg = msg+"N° Facture : "+rst.getString(7)+".\n";
				msg = msg+"Accompagnateur : "+RechAccompStr(" ID like '"+rst.getString(8)+"'")+".\n";
			}
			
			new Message(0,msg);
   		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   		
   	}
   });
   btnAfficherClient.setFont(new Font("Consolas", Font.PLAIN, 14));
   btnAfficherClient.setBounds(1071, 40, 183, 42);
   getContentPane().add(btnAfficherClient);


new Thread(new Runnable() {
	public void run() {
     
		try {
			Statement stm = con.createStatement();
			String req = "select distinct NOM from TRANS_CLIENT";
			ResultSet rst = stm.executeQuery(req);
			cbClient.removeAllItems();
			while(rst.next()){
			  cbClient.addItem(rst.getString(1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
            aff(panel_1, 0, con);
	}
}).start();
}
	
	
void aff(JPanel pan, int indice, Connection con){
	 try {
		 String complement = "";
		 
			 if(indice==0){
			    complement = "";
			 }
					 
			if(indice==1){
				complement = " CODE_C like '"+codification.cd_prs(cbClient.getSelectedItem()+"")+"' ";		 
					 }
			
			if(indice==2){
				complement = " DATEMIS between '"+new SimpleDateFormat("dd/MM/yyyy").format(dt_d.getDate())+"' and '"+new SimpleDateFormat("dd/MM/yyyy").format(dt_f.getDate())+"' "; 
			}
			
			if(indice==3){
				complement = " DATEMIS between '"+new SimpleDateFormat("dd/MM/yyyy").format(dt_d.getDate())+"' and '"+new SimpleDateFormat("dd/MM/yyyy").format(dt_f.getDate())+"' and CODE_C like '%' "; 
			}
		 
			Statement stm = con.createStatement();
	        String req = "select MIN(DATEALLEZ) , MAX(DATERETOUR) from TRANS_MISSION where "+complement;
	        ResultSet rst = null;
	        Date dt_debut = new Date();
 	        Date dt_fin   = new Date();
	        if(!complement.equals("")){
	        	rst = stm.executeQuery(req);
	 	        while(rst.next()){
	 	        	dt_debut = rst.getDate(1);
	 	        	dt_fin   = rst.getDate(2);
	 	        }
	        }
	       
	        
	        Date dt_inct = new Date();

	        if(indice > 1){
	        	dt_debut = dt_d.getDate();
	        	dt_fin = dt_f.getDate();
	        	}
	        
	long dt_debut_l =  dt_debut.getTime();
	long dt_fin_l =  dt_fin.getTime();
	long dt_inct_l =  dt_inct.getTime();
	Vector vct = new Vector();
	vct.addElement("Chauffeurs");		
				for(dt_inct_l = dt_debut_l; dt_inct_l <= dt_fin_l; dt_inct_l = dt_inct_l + 86400000 ){
					vct.addElement(new SimpleDateFormat("dd/MM/yyyy").format(new Date(dt_inct_l)));
				}
				
				 String[] t_tab_v = new String[vct.size()];
				 int pan_taille = 60;
				for(int i = 0; i < vct.size(); i++){
					t_tab_v[i] = vct.elementAt(i).toString();
					pan_taille = pan_taille + 80;
				}
				t_tab_b = t_tab_v;
				 
				def_t = new DefaultTableModel(){ public boolean isCellEditable(int row, int column) { return false;} };
				def_t.setColumnIdentifiers(t_tab_v);
		
	if(indice==3){
		req = "select NUMMIS, IDCHAUF from TRANS_MISSION where CODE_C like '%' ORDER BY DATEMIS desc";		
	}else{
		req = "select NUMMIS, IDCHAUF from TRANS_MISSION where CODE_C like '"+codification.cd_prs(cbClient.getSelectedItem()+"")+"' ORDER BY DATEMIS desc";		
	}

	rst = stm.executeQuery(req);
	
	Vector vct_ch = new Vector();
	Vector vctChauffeur = new Vector();
	
			while(rst.next()){
				vct_ch.addElement(rst.getString(1));
				vctChauffeur.addElement(rst.getString(2));
			}
			long dtd_slct = 0;
			long dtf_slct = 0;
			long dt_tst = 0;
			Date parsed_date = new Date();
			String etat = "";
			Object[] obj = new Object[vct.size()];
			for(int i = 0; i < vct_ch.size(); i++ ){
				req = "select DATEMIS, DATEALLEZ, DATERETOUR from TRANS_MISSION where NUMMIS like '"+vct_ch.elementAt(i)+"' and "+complement+" ORDER BY DATEALLEZ";
				if(!complement.equals("")){
					rst = stm.executeQuery(req);
					obj = new Object[vct.size() + 1];
					while(rst.next()){
						dtd_slct = rst.getDate(2).getTime();
						dtf_slct = rst.getDate(3).getTime();
						
						if(dt.isInPeriode(rst.getDate(1), rst.getDate(2), DataBase.date_act(FirstPg.con))){
							etat = "RESERVER";
						}else{
							etat = "OCCUPER";
						}
											
						for(int j = 0; j < vct.size(); j++){
							if(!vct.elementAt(j).equals("Chauffeurs")){
							try {
									parsed_date = new SimpleDateFormat("dd/MM/yyyy").parse(vct.elementAt(j)+"");
									dt_tst = parsed_date.getTime();							
										
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(dt_tst >= dtd_slct && dt_tst <= dtf_slct){
																			if(etat.equals("OCCUPER")){
																										   obj[j] = "2";
																									  }else{
																										   obj[j] = "1";
																										   }
																		}else{
																			obj[j] = "0";
																		}
							}else{
								obj[j] = RechChaufStr(" ID like '"+vctChauffeur.elementAt(i)+"' ");
							}

							
							
							
						}
						boolean bl_tr = false;
						for(int x = 0; x < def_t.getRowCount(); x++){
							if(def_t.getValueAt(x, 0).equals(obj[0].toString())){
								
								bl_tr = true;
								
								for(int y = 1; y < def_t.getColumnCount(); y++){
									
									if(def_t.getValueAt(x, y).equals("0")){
										if(obj[y].equals("0")){
											def_t.setValueAt("0", x, y);
										}else if(obj[y].equals("1")){
											def_t.setValueAt("1", x, y);
										}else if(obj[y].equals("2")){
											def_t.setValueAt("2", x, y);
										}
									}											
								}
							}
						}
						
						if(!bl_tr) def_t.addRow(obj);
					}
				}
				
				
			}
			
				
				panel_1.setPreferredSize(new Dimension(pan_taille, 385));

				tab_fact.setModel(def_t);
				
				for(int i = 0; i < vct.size(); i++){
					tab_fact.getColumnModel().getColumn(i).setCellRenderer(new CustomRenderer());
				}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
	


class CustomRenderer extends DefaultTableCellRenderer 
{
private static final long serialVersionUID = 6703872492730589499L;

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
     
        try {
				if(table.getValueAt(row, column).equals("0")){
		            cellComponent.setBackground(Color.BLUE);
		            cellComponent.setForeground(Color.BLUE);
		          
			      } else if(table.getValueAt(row, column).equals("1")){
			            cellComponent.setBackground(Color.ORANGE);
			            cellComponent.setForeground(Color.ORANGE);
			      }else if(table.getValueAt(row, column).equals("2")){
			       	 	cellComponent.setBackground(Color.GREEN);
			       	 	cellComponent.setForeground(Color.GREEN);
			      }
		} catch (Exception e) { }
        return cellComponent;
    }
}

String RechChaufStr(String condition) throws SQLException{
	String str = "";
	Statement stm = FirstPg.con.createStatement();
	//if (!condition.equals("")) condition = " and "+condition;
	String req = "select NOM from TRANS_CHAUFFEUR  where "+condition+" ";
	ResultSet rst = stm.executeQuery(req);

	while (rst.next()){
		str = rst.getString(1);
	}
	stm.close();
	return str;
}

String RechAccompStr(String condition) throws SQLException{
	String str = "";
	Statement stm = FirstPg.con.createStatement();
	//if (!condition.equals("")) condition = " and "+condition;
	String req = "select NOM from TRANS_ACCOMP  where "+condition+" ";
	ResultSet rst = stm.executeQuery(req);

	while (rst.next()){
		str = rst.getString(1);
	}
	stm.close();
	return str;
}

public String numMission(String Dates, String IDCH){
	try {
		
		Statement stm = FirstPg.con.createStatement();
		String req = "select NUMMIS from TRANS_MISSION where IDCHAUF like '"+IDCH+"' and DATEALLEZ <= '"+Dates+"' and DATERETOUR >= '"+Dates+"'"; 
		ResultSet rst = stm.executeQuery(req);
		while(rst.next()){
			return rst.getString(1);
		}
	
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return "";
}

}
