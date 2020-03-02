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
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

import Etat.EtatPdf;

public class SuivCmd extends JInternalFrame {

	
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
    
	String[] t_tab_bFct = { "N° BC","Montant","Versement","NUMVERS"};
    String[] t_tab_bFctDetail = { "N° BC","Montant","Total Versement"};
    
    
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

    
	public SuivCmd() {
		setFrameIcon(new ImageIcon(SuivCmd.class.getResource("/icones/original/A16/signature-icone-5295-128_RS.png")));
		setIconifiable(true);
		setClosable(true);
		setTitle("Suivi Dettes Fournisseurs");
		setBounds(100, 100, 430, 449);
       getContentPane().setLayout(new BorderLayout(0, 0));
       
       JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
       tabbedPane.setFont(new Font("Consolas", Font.PLAIN, 13));
       getContentPane().add(tabbedPane, BorderLayout.CENTER);
       
       JPanel panel = new JPanel();
       tabbedPane.addTab("Versement", null, panel, null);
       panel.setLayout(null);
       
       JLabel lblClient = new JLabel("Fournisseur");
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
       		
       		String[] args0 = new String[] {"CODE_F","NUMVRS","RESTE","MONTANT","DATEVRS"};
    		String[] argsFct = new String[] {"NUMBC","PRIX","VERSEMENT","NUMVERS","CODE_F"};

    		try {
    			DataBase.TabVrsRmv("TRANS_VRS_FRS", "TRANS_SUIVI_BC", tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"", FirstPg.con, args0, argsFct);
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
       			miPF = new JMenuItem("Payement des commandes");
				menu.add(miPF);
				
				miPF.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						payementFacture();
					}
				});
				
				menu.show(tab_fact, arg0.getX(), arg0.getY());
       		}
       		
       	}
       });
       
       
       table panel_1 = new table(tab_fact);
       
      
       
      
       panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
       panel_1.setBounds(10, 184, 392, 177);
       
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
               			String[] args0 = new String[] {"CODE_F","NUMVRS","RESTE","MONTANT","DATEVRS"};
	       				String[] vals = new String[] {codification.cd_prs(cbClient.getSelectedItem()+""),tfVrs.getText(),spMontant.getValue()+"",spMontant.getValue()+"",dt.form_Aff(dtVrs.getDate())};
	   					try {
							DataBase.TabVrs("TRANS_VRS_FRS", "TRANS_SUIVI_BC", args0, vals, FirstPg.con);
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
              
              JButton btnEtatPdf = new JButton("Etat PDF");
              btnEtatPdf.addActionListener(new ActionListener() {
              	public void actionPerformed(ActionEvent arg0) {
              		EtatPdf.etat(tab_fact, "Etat des versements pour "+cbClient.getSelectedItem());
              	}
              });
              btnEtatPdf.setFont(new Font("Consolas", Font.PLAIN, 14));
              btnEtatPdf.setBounds(283, 365, 119, 24);
              panel.add(btnEtatPdf);
              
              JPanel panel_2 = new JPanel();
              tabbedPane.addTab("Suivi Bons de commandes", null, panel_2, null);
              panel_2.setLayout(null);
              
              JLabel lblFournisseur = new JLabel("Fournisseur");
              lblFournisseur.setHorizontalAlignment(SwingConstants.RIGHT);
              lblFournisseur.setFont(new Font("Consolas", Font.PLAIN, 14));
              lblFournisseur.setBounds(10, 11, 102, 24);
              panel_2.add(lblFournisseur);
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
              panel_3.setBounds(10, 102, 389, 277);
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
              chckbxDetails.setBounds(26, 49, 97, 23);
              panel_2.add(chckbxDetails);
              
              JButton btnEtatPdf_1 = new JButton("Etat PDF");
              btnEtatPdf_1.addActionListener(new ActionListener() {
              	public void actionPerformed(ActionEvent arg0) {
              		if(chckbxDetails.isSelected()){
              			EtatPdf.etat(tab_fct, "Etat des payements détailler pour "+cbClient.getSelectedItem());
              		}else{
              			EtatPdf.etat(tab_fct, "Etat des payements pour "+cbClient.getSelectedItem());
              		}
              	}
              });
              btnEtatPdf_1.setFont(new Font("Consolas", Font.PLAIN, 14));
              btnEtatPdf_1.setBounds(169, 46, 197, 28);
              panel_2.add(btnEtatPdf_1);

              
              tab_bon = new JTable(d_tabBon,t_tab_bBon);
              table panel_7 = new table(tab_bon);
              
              
              panel_7.setBorder(new LineBorder(new Color(0, 0, 0)));
              panel_7.setBounds(10, 99, 389, 280);
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
		table.ReachForTab(def_t, tab_fact, dBargs, dBargType,"TRANS_VRS_FRS", "where CODE_F like '"+codification.cd_prs(cbClient.getSelectedItem()+"")+"' ", FirstPg.con);
	}

	 void RechClient(String condition)throws SQLException{
			Statement stm = FirstPg.con.createStatement();
			if (!condition.equals("")) condition = " and "+condition;
			String req = "select NOM from TRANS_FOURNISSEUR where visible = 1 "+condition+" order by NOM";
			ResultSet rst = stm.executeQuery(req);
			cbClient.removeAllItems();
			cbClientsv.removeAllItems();
			while (rst.next()){
				cbClient.addItem(rst.getString(1));
				cbClientsv.addItem(rst.getString(1));
			}
		}
	 
	 
	 void rechFct(DefaultTableModel def) throws SQLException{
		 Statement stm = FirstPg.con.createStatement();
		 String req = "select NUMBC, PRIX, VERSEMENT, NUMVERS from TRANS_SUIVI_BC where CODE_F like '"+codification.cd_prs(cbClientsv.getSelectedItem()+"")+"'";
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

		 String req = "select distinct NUMBC, PRIX from TRANS_SUIVI_BC where CODE_F like '"+codification.cd_prs(cbClientsv.getSelectedItem()+"")+"'";
		 ResultSet rst = stm.executeQuery(req);
		 ResultSet rst1 ;

		 while(rst.next()){
			 req = "select sum(VERSEMENT) from TRANS_SUIVI_BC where CODE_F like '"+codification.cd_prs(cbClientsv.getSelectedItem()+"")+"' and NUMBC like '"+rst.getString(1)+"'";
			 rst1 = stm1.executeQuery(req);
			 String inf = "";
			 while(rst1.next())inf=nbr.dbToDf(rst1.getDouble(1));
			 def.addRow(new Object[]{rst.getString(1), nbr.dbToDf(rst.getDouble(2)), inf});
		 }
		 tab_fct.setModel(def);
		 stm.close();
		 stm1.close();
	 }
	 

	void payementFacture(){
   	try {
		int conf = JOptionPane.showConfirmDialog(null, "Veuillez confirmer le versement svp.", "Demande de Confirmation",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
   			if(conf == JOptionPane.OK_OPTION){
   				String nm = tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+""; 
   				String[] args0 = new String[] {"CODE_F","NUMVRS","RESTE","MONTANT","DATEVRS"};
	   				String[] vals = new String[] {codification.cd_prs(cbClient.getSelectedItem()+""),tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"",nbr.dfToDb(tab_fact.getValueAt(tab_fact.getSelectedRow(), 1)+"")+"",nbr.dfToDb(tab_fact.getValueAt(tab_fact.getSelectedRow(), 2)+"")+"",tab_fact.getValueAt(tab_fact.getSelectedRow(), 3)+""};
   				String[] argsFct = new String[] {"NUMBC","PRIX","VERSEMENT","NUMVERS","CODE_F"};
   				try {
   					DataBase.PayementByVrs("TRANS_VRS_FRS", "TRANS_SUIVI_BC", args0, vals, argsFct, FirstPg.con);
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
}
