package Suivi;

import fct.DataBase;
import fct.Message;
import fct.codification;
import fct.dt;
import fct.nbr;
import fct.tField;
import fct.table;
import interfaces.FirstPg;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

import Etat.EtatPdf;

public class SuivFct extends JInternalFrame {

	
	JMenuItem miPF;
     
	JMenuItem miPB;
	JPopupMenu menu = new JPopupMenu();
	JTable tab_fact;
	DefaultTableModel def_t;
	Object [][] d_tab= new Object[20][7];
    String[] t_tab_b = { "N° Versement","Reste","Montant","Date"};
    
    JTable tab_fct;
	DefaultTableModel def_fct;
	Object [][] d_tabFct= new Object[20][7];
    
	String[] t_tab_bFct = { "N° Facture","Montant","Versement","NUMVERS"};
    String[] t_tab_bFctDetail = { "N° Facture","Montant","Total Versement"};
    
    
    JTable tab_bon;
   	DefaultTableModel def_bon;
   	Object [][] d_tabBon= new Object[20][7];
       
   	String[] t_tab_bBon = { "N° Mission","Montant","Versement","NUMVERS"};
       String[] t_tab_bBonDetail = { "N° Mission","Montant","Total Versement"};
    
    table panel_4;
    table panel_5;
    JPanel panel_3 = new JPanel();

    private tField tfVrs;
    private dt dtVrs;
    JSpinner spMontant = new JSpinner();              
    JComboBox cbClient = new JComboBox();

    JComboBox cbClientsv = new JComboBox();
    JCheckBox chckbxDetails = new JCheckBox("Details");
    
    JComboBox cbBons = new JComboBox();
    JCheckBox chDetailBon = new JCheckBox("Details");

    
	public SuivFct() {
		setFrameIcon(new ImageIcon(SuivFct.class.getResource("/icones/original/A16/signature-icone-5295-128_RS.png")));
		setIconifiable(true);
		setClosable(true);
		setTitle("Suivi Facturation");
		setBounds(100, 100, 430, 449);
       getContentPane().setLayout(new BorderLayout(0, 0));
       
       JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
       tabbedPane.setFont(new Font("Consolas", Font.PLAIN, 13));
       getContentPane().add(tabbedPane, BorderLayout.CENTER);
       
       JPanel panel = new JPanel();
       tabbedPane.addTab("Versement", null, panel, null);
       panel.setLayout(null);
       
       JLabel lblClient = new JLabel("Client");
       lblClient.setHorizontalAlignment(SwingConstants.RIGHT);
       lblClient.setFont(new Font("Consolas", Font.PLAIN, 14));
       lblClient.setBounds(10, 21, 102, 24);
       panel.add(lblClient);
       
       JLabel lblNVersement = new JLabel("N\u00B0 Versement");
       lblNVersement.setHorizontalAlignment(SwingConstants.RIGHT);
       lblNVersement.setFont(new Font("Consolas", Font.PLAIN, 14));
       lblNVersement.setBounds(10, 58, 102, 24);
       panel.add(lblNVersement);
       
       JLabel lblMontant = new JLabel("Montant");
       lblMontant.setHorizontalAlignment(SwingConstants.RIGHT);
       lblMontant.setFont(new Font("Consolas", Font.PLAIN, 14));
       lblMontant.setBounds(10, 93, 102, 24);
       panel.add(lblMontant);
       
       JLabel lblDate = new JLabel("Date");
       lblDate.setFont(new Font("Consolas", Font.PLAIN, 14));
       lblDate.setBounds(235, 58, 54, 24);
       panel.add(lblDate);
       
       JButton btnAnnuler = new JButton("Annuler");
       btnAnnuler.addActionListener(new ActionListener() {
       	public void actionPerformed(ActionEvent arg0) {
       		
       		String[] args0 = new String[] {"CODE_C","NUMVRS","RESTE","MONTANT","DATEVRS"};
    		String[] argsFct = new String[] {"NUMFCT","PRIXTTC","VERSEMENT","NUMVERS","CODE_C"};
    		String[] argsBon = new String[] {"NUMMIS","PRIXHT","VERSEMENT","NUMVERS","CODE_C"};

    		try {
    			DataBase.TabVrsRmv("TRANS_VRS_CLT", "TRANS_CREANCE_FCT", tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"", FirstPg.con, args0, argsFct);
    			DataBase.TabVrsRmv("TRANS_VRS_CLT", "TRANS_CREANCE_MIS", tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"", FirstPg.con, args0, argsBon);
    			DataBase.droppedInvoice();	
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
       		rech();
       		
       	}
       });
       btnAnnuler.setFont(new Font("Consolas", Font.PLAIN, 17));
       btnAnnuler.setBounds(300, 128, 102, 45);
       panel.add(btnAnnuler);
       
       
       //panel_1.setLayout(new BorderLayout(0, 0));

      
       tab_fact = new JTable(d_tab,t_tab_b);
       
       tab_fact.addMouseListener(new MouseAdapter() {
       	@Override
       	public void mouseClicked(MouseEvent arg0) {
       		
       		if(arg0.getButton() == 3){	
       			menu.removeAll();
       			miPF = new JMenuItem("Payement Factures");
				miPB = new JMenuItem("Payement Bons Transports");
				menu.add(miPF);
				menu.add(miPB);
				miPF.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						payementFacture();
					}
				});
				miPB.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						payementBon();
					}
				});
				menu.show(tab_fact, arg0.getX(), arg0.getY());
       		}
       		
       	}
       });
       
       
       table panel_1 = new table(tab_fact);
       
      
       
      
       panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
       panel_1.setBounds(10, 184, 392, 176);
       
              panel.add(panel_1);
              cbClient.addActionListener(new ActionListener() {
              	public void actionPerformed(ActionEvent arg0) {
              		rech();
              	}
              });
              
              cbClient.setBounds(122, 21, 244, 24);
              panel.add(cbClient);
              
              tfVrs = new tField();
              tfVrs.setBounds(122, 58, 103, 24);
              panel.add(tfVrs);
              tfVrs.setColumns(10);
              
              dtVrs = new dt();
              dtVrs.setBounds(271, 58, 119, 24);
              panel.add(dtVrs);
              

              spMontant.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(100)));
              spMontant.setFont(new Font("Consolas", Font.PLAIN, 16));
              spMontant.setBounds(122, 93, 244, 24);
              panel.add(spMontant);
              
              JButton btnPayementBons = new JButton("Versement");
              btnPayementBons.addActionListener(new ActionListener() {
              	public void actionPerformed(ActionEvent arg0) {
              		if(cbClient.getSelectedItem().equals("")||tfVrs.getText().equals("")|| nbr.toDouble(spMontant.getValue()+"")<=0){
               			new Message(1, "Veuillez vérifier les informations saisie svp !!");
               		}else{
               			String[] args0 = new String[] {"CODE_C","NUMVRS","RESTE","MONTANT","DATEVRS"};
	       				String[] vals = new String[] {codification.cd_prs(cbClient.getSelectedItem()+""),tfVrs.getText(),spMontant.getValue()+"",spMontant.getValue()+"",dt.form_Aff(dtVrs.getDate())};
	       				String[] argsFct = new String[] {"NUMFCT","PRIXTTC","VERSEMENT","NUMVERS","CODE_C"};
	   					try {
							DataBase.TabVrs("TRANS_VRS_CLT", "TRANS_CREANCE_FCT", args0, vals, FirstPg.con);
							rech();
							new Message(0, "Versement effectuer avec succées.");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
               		}
              		
              	}
              });
              btnPayementBons.setFont(new Font("Consolas", Font.PLAIN, 20));
              btnPayementBons.setBounds(10, 128, 280, 45);
              panel.add(btnPayementBons);
              
              JButton btnNewButton = new JButton("Etat PDF");
              btnNewButton.addActionListener(new ActionListener() {
              	public void actionPerformed(ActionEvent arg0) {
              		EtatPdf.etat(tab_fact, "Etat des versements de "+cbClient.getSelectedItem());
              	}
              });
              btnNewButton.setFont(new Font("Century Gothic", Font.PLAIN, 14));
              btnNewButton.setBounds(288, 365, 114, 23);
              panel.add(btnNewButton);
              
              JPanel panel_2 = new JPanel();
              tabbedPane.addTab("Suivi Facture", null, panel_2, null);
              panel_2.setLayout(null);
              
              JLabel label = new JLabel("Client");
              label.setHorizontalAlignment(SwingConstants.RIGHT);
              label.setFont(new Font("Consolas", Font.PLAIN, 14));
              label.setBounds(10, 11, 102, 24);
              panel_2.add(label);
              cbClientsv.addActionListener(new ActionListener() {
              	public void actionPerformed(ActionEvent arg0) {
      				def_fct = new DefaultTableModel();
                 if(chckbxDetails.isSelected()){
          				def_fct.setColumnIdentifiers(t_tab_bFctDetail);
          				try {
							rechDetail(def_fct);
						} catch (SQLException e) {
							e.printStackTrace();
						}
          		}else{
          				def_fct.setColumnIdentifiers(t_tab_bFct);
          				//tab_fct.setModel(def_fct);
          				try {
							rechFct(def_fct);
						} catch (SQLException e) {
							e.printStackTrace();
						}
          		}
              		
              	}
              });
              
             

              cbClientsv.setBounds(122, 11, 244, 24);
              panel_2.add(cbClientsv);
              
              panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
              panel_3.setBounds(10, 102, 389, 279);
              panel_2.add(panel_3);
              panel_3.setLayout(new BorderLayout(0, 0));
              
              tab_fct = new JTable(d_tabFct,t_tab_bFct);
              
            
              panel_3.add(new table(tab_fct), BorderLayout.CENTER);
              chckbxDetails.addActionListener(new ActionListener() {
              	public void actionPerformed(ActionEvent arg0) {
              		def_fct = new DefaultTableModel();
              		
              		if(chckbxDetails.isSelected()){
							              				def_fct.setColumnIdentifiers(t_tab_bFctDetail);
							              				try {
															rechDetail(def_fct);
														} catch (SQLException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
              										}else{
								          				def_fct.setColumnIdentifiers(t_tab_bFct);
								          				
								          				try {
															rechFct(def_fct);
														} catch (SQLException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
              		}
              	}
              });
              
              chckbxDetails.setFont(new Font("Consolas", Font.PLAIN, 14));
              chckbxDetails.setBounds(66, 51, 97, 23);
              panel_2.add(chckbxDetails);
              
              JButton btnEtatPdf = new JButton("Etat PDF");
              btnEtatPdf.addActionListener(new ActionListener() {
              	public void actionPerformed(ActionEvent arg0) {
              		if(chckbxDetails.isSelected()){
              			EtatPdf.etat(tab_fct, "Etat des payements facture détailler de "+cbClient.getSelectedItem());
              		}else{
              			EtatPdf.etat(tab_fct, "Etat des payements facture de "+cbClient.getSelectedItem());
              		}
              	}
              });
              btnEtatPdf.setFont(new Font("Century Gothic", Font.PLAIN, 14));
              btnEtatPdf.setBounds(264, 51, 102, 23);
              panel_2.add(btnEtatPdf);
              
              JPanel panel_6 = new JPanel();
              tabbedPane.addTab("Suivi Bons de Transport", null, panel_6, null);
              panel_6.setLayout(null);
              
              JLabel label_1 = new JLabel("Client");
              label_1.setHorizontalAlignment(SwingConstants.RIGHT);
              label_1.setFont(new Font("Consolas", Font.PLAIN, 14));
              label_1.setBounds(10, 11, 102, 24);
              panel_6.add(label_1);
              cbBons.addActionListener(new ActionListener() {
              	public void actionPerformed(ActionEvent arg0) {
              		def_bon = new DefaultTableModel();
              		if(chDetailBon.isSelected()){
              											def_bon.setColumnIdentifiers(t_tab_bBonDetail);
							              				try {
															rechDetailBon(def_bon);
														} catch (SQLException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
              										}else{
              											def_bon.setColumnIdentifiers(t_tab_bBon);
								          				try {
															rechBon(def_bon);
														} catch (SQLException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
              		}
              	}
              });
              
              cbBons.setBounds(122, 11, 244, 24);
              panel_6.add(cbBons);
              chDetailBon.addActionListener(new ActionListener() {
              	public void actionPerformed(ActionEvent arg0) {
              		def_bon = new DefaultTableModel();
              		
              		if(chDetailBon.isSelected()){
              											def_bon.setColumnIdentifiers(t_tab_bBonDetail);
							              				try {
															rechDetailBon(def_bon);
														} catch (SQLException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
              										}else{
								          				
								          				def_bon.setColumnIdentifiers(t_tab_bBon);
								          				try {
															rechBon(def_bon);
														} catch (SQLException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
              		}
              	}
              });
              
              
              chDetailBon.setFont(new Font("Consolas", Font.PLAIN, 14));
              chDetailBon.setBounds(66, 51, 97, 23);
              panel_6.add(chDetailBon);
              
              
              tab_bon = new JTable(d_tabBon,t_tab_bBon);
              table panel_7 = new table(tab_bon);
              
              
              panel_7.setBorder(new LineBorder(new Color(0, 0, 0)));
              panel_7.setBounds(10, 99, 389, 280);
              panel_6.add(panel_7);
              
              JButton btnEtatPdf_1 = new JButton("Etat PDF");
              btnEtatPdf_1.addActionListener(new ActionListener() {
              	public void actionPerformed(ActionEvent arg0) {
              		if(chckbxDetails.isSelected()){
              			EtatPdf.etat(tab_bon, "Etat des payements des BTs détailler de "+cbClient.getSelectedItem());
              		}else{
              			EtatPdf.etat(tab_bon, "Etat des payements des BTs de "+cbClient.getSelectedItem());
              		}
              	}
              });
              btnEtatPdf_1.setFont(new Font("Century Gothic", Font.PLAIN, 14));
              btnEtatPdf_1.setBounds(264, 51, 102, 23);
              panel_6.add(btnEtatPdf_1);
            // panel_7.setLayout(new BorderLayout(0, 0));

              new Thread(new Runnable() {
				
				@Override
				public void run() {
					 try {
						RechClient("");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 rech();
				}
			}).start();
              
              
	}
	
	void rech(){
		def_t = new DefaultTableModel();
		def_t.setColumnIdentifiers(t_tab_b);
		String[] dBargs = new String[]{"NUMVRS", "RESTE", "MONTANT", "DATEVRS"};
		int[] dBargType = new int[]{0, 1, 1, 2};
		table.ReachForTab(def_t, tab_fact, dBargs, dBargType,"TRANS_VRS_CLT", "where CODE_C like '"+codification.cd_prs(cbClient.getSelectedItem()+"")+"' ", FirstPg.con);
	}

	 void RechClient(String condition)throws SQLException{
			Statement stm = FirstPg.con.createStatement();
			if (!condition.equals("")) condition = " and "+condition;
			String req = "select NOM from TRANS_CLIENT where visible = 1 "+condition+" order by NOM";
			ResultSet rst = stm.executeQuery(req);
			cbClient.removeAllItems();
			cbClientsv.removeAllItems();
			cbBons.removeAllItems();
			while (rst.next()){
				cbClient.addItem(rst.getString(1));
				cbClientsv.addItem(rst.getString(1));
				cbBons.addItem(rst.getString(1));
			}
		}
	 
	 
	 void rechFct(DefaultTableModel def) throws SQLException{
		 Statement stm = FirstPg.con.createStatement();
		 String req = "select NUMFCT, PRIXTTC, VERSEMENT, NUMVERS from TRANS_CREANCE_FCT where CODE_C like '"+codification.cd_prs(cbClientsv.getSelectedItem()+"")+"'";
		 ResultSet rst = stm.executeQuery(req);
		 while(rst.next()){
			 if(rst.getDouble(3)>0)
			 def.addRow(new Object[]{rst.getString(1), nbr.dbToDf(rst.getDouble(2)),nbr.dbToDf(rst.getDouble(3)), rst.getString(4)});
		 }
		 tab_fct.setModel(def);
		 stm.close();
	 }
	 
	 void rechDetail(DefaultTableModel def) throws SQLException{
		 Statement stm = FirstPg.con.createStatement();
		 Statement stm1 = FirstPg.con.createStatement();

		 String req = "select distinct NUMFCT, PRIXTTC from TRANS_CREANCE_FCT where CODE_C like '"+codification.cd_prs(cbClientsv.getSelectedItem()+"")+"'";
		 ResultSet rst = stm.executeQuery(req);
		 ResultSet rst1 ;

		 while(rst.next()){
			 req = "select sum(VERSEMENT) from TRANS_CREANCE_FCT where CODE_C like '"+codification.cd_prs(cbClientsv.getSelectedItem()+"")+"' and NUMFCT like '"+rst.getString(1)+"'";
			 rst1 = stm1.executeQuery(req);
			 String inf = "";
			 while(rst1.next())inf=nbr.dbToDf(rst1.getDouble(1));
			 def.addRow(new Object[]{rst.getString(1), nbr.dbToDf(rst.getDouble(2)), inf});
		 }
		 tab_fct.setModel(def);
		 stm.close();
		 stm1.close();
	 }
	 
	 
	 void rechBon(DefaultTableModel def) throws SQLException{
		 Statement stm = FirstPg.con.createStatement();
		 String req = "select NUMMIS, PRIXHT, VERSEMENT, NUMVERS from TRANS_CREANCE_MIS where CODE_C like '"+codification.cd_prs(cbClientsv.getSelectedItem()+"")+"'";
		 ResultSet rst = stm.executeQuery(req);
		 while(rst.next()){
			 if(rst.getDouble(3)>0)
			 def.addRow(new Object[]{rst.getString(1), nbr.dbToDf(rst.getDouble(2)),nbr.dbToDf(rst.getDouble(3)), rst.getString(4)});
		 }
		 tab_bon.setModel(def);
		 stm.close();
	 }
	 
	 void rechDetailBon(DefaultTableModel def) throws SQLException{
		 Statement stm = FirstPg.con.createStatement();
		 Statement stm1 = FirstPg.con.createStatement();

		 String req = "select distinct NUMMIS, PRIXHT from TRANS_CREANCE_MIS where CODE_C like '"+codification.cd_prs(cbClientsv.getSelectedItem()+"")+"'";
		 ResultSet rst = stm.executeQuery(req);
		 ResultSet rst1 ;

		 while(rst.next()){
			 req = "select sum(VERSEMENT) from TRANS_CREANCE_MIS where CODE_C like '"+codification.cd_prs(cbClientsv.getSelectedItem()+"")+"' and NUMMIS like '"+rst.getString(1)+"'";
			 rst1 = stm1.executeQuery(req);
			 String inf = "";
			 while(rst1.next())inf=nbr.dbToDf(rst1.getDouble(1));
			 def.addRow(new Object[]{rst.getString(1), nbr.dbToDf(rst.getDouble(2)), inf});
		 }
		 tab_bon.setModel(def);
		 stm.close();
		 stm1.close();
	 }
	 
	 
	void payementFacture(){
   	try {
		int conf = JOptionPane.showConfirmDialog(null, "Veuillez confirmer le versement svp.", "Demande de Confirmation",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
   			if(conf == JOptionPane.OK_OPTION){
   				String nm = tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+""; 
   				String[] args0 = new String[] {"CODE_C","NUMVRS","RESTE","MONTANT","DATEVRS"};
	   				String[] vals = new String[] {codification.cd_prs(cbClient.getSelectedItem()+""),tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"",nbr.dfToDb(tab_fact.getValueAt(tab_fact.getSelectedRow(), 1)+"")+"",nbr.dfToDb(tab_fact.getValueAt(tab_fact.getSelectedRow(), 2)+"")+"",tab_fact.getValueAt(tab_fact.getSelectedRow(), 3)+""};
   				String[] argsFct = new String[] {"NUMFCT","PRIXTTC","VERSEMENT","NUMVERS","CODE_C"};
   				try {
   					DataBase.PayementByVrs("TRANS_VRS_CLT", "TRANS_CREANCE_FCT", args0, vals, argsFct, FirstPg.con);
   					DataBase.payedInvoice();
   					new Message(0, "Payement effectuer avec succées.");
   				} catch (SQLException e) {
   					new Message(2, "Erreur:\nVersement déja effectuer.\nVeuillez vérifier le numéro de versement svp!!");
   				}
		   			rech();
		   		}
			} catch (Exception e) {
					new Message(2, "Erreur:\nVeuillez selectionner le versement a utilisé svp !!");
			}
	}
	
	void payementBon(){
   			 try {
   				String nm = tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+""; 
   				int conf = JOptionPane.showConfirmDialog(null, "Veuillez confirmer le versement svp.", "Demande de Confirmation",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
   	   			if(conf == JOptionPane.OK_OPTION){
   	   				String[] args0 = new String[] {"CODE_C","NUMVRS","RESTE","MONTANT","DATEVRS"};
   	   				String[] vals = new String[] {codification.cd_prs(cbClient.getSelectedItem()+""),tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"",nbr.dfToDb(tab_fact.getValueAt(tab_fact.getSelectedRow(), 1)+"")+"",nbr.dfToDb(tab_fact.getValueAt(tab_fact.getSelectedRow(), 2)+"")+"",tab_fact.getValueAt(tab_fact.getSelectedRow(), 3)+""};
   	   				String[] argsFct = new String[] {"NUMMIS","PRIXHT","VERSEMENT","NUMVERS","CODE_C"};
   	   				try {
   	   					DataBase.PayementByVrs("TRANS_VRS_CLT", "TRANS_CREANCE_MIS", args0, vals, argsFct, FirstPg.con);
   	   					new Message(0, "Payement effectuer avec succées.");
   	   				} catch (SQLException e) {
   	   					new Message(2, "Erreur:\nVersement déja effectuer.\nVeuillez vérifier le numéro de versement svp!!");
   	   				}
   	   			}
   	   			rech();
			} catch (Exception e) {
  					new Message(2, "Erreur:\nVeuillez selectionner le versement a utilisé svp !!");
			}      			
   		}
}
