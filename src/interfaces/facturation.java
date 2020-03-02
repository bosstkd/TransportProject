package interfaces;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import fct.*;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.view.JasperViewer;

import org.jdesktop.swingx.JXDatePicker;

import com.ibm.icu.text.RuleBasedNumberFormat;

import fct.DataBase;
import fct.codification;
import fct.dt;
import fct.nbr;
import fct.numRote;
import fct.tField;


public class facturation extends JInternalFrame {

	boolean conf = true;
	boolean modif = false;
	JComboBox cb_client = new JComboBox();
    JComboBox cb_type_p = new JComboBox();
	JCheckBox chckbxArrondirLeDroit = new JCheckBox("Arrondir le droit de timbre");

    
    JComboBox cb_client_rech = new JComboBox();
    
    JScrollPane sc_pane = new JScrollPane();
	Vector vc_ = new Vector();
	JList list_;
	DefaultListModel listModel_;
	
    private JXDatePicker dt_du =  new JXDatePicker();
    private JXDatePicker dt_au =  new JXDatePicker();
	
	
	 JCheckBox ch_paye = new JCheckBox("Pay\u00E9");
	 JCheckBox ch_date = new JCheckBox("Dat\u00E9");
	 JCheckBox ch_fctBl = new JCheckBox("Facture / Mission");

	JTable tab_alerte;
	DefaultTableModel def_t;
	Object [][] d_tab= new Object[20][9];
    String[] t_tab_b = { "N° Mission","Client","Date","Etat","Lier"};
    JScrollPane scrollPane1; 
    JButton btnBc = new JButton("Afficher la facture");
    JButton btnSupprimer = new JButton("Rejeter");

    
    JTable tab_b_cmd;
	DefaultTableModel def_t_b_cmd;
	Object [][] d_tab_b_cmd= new Object[20][9];
    String[] t_tab_b_b_cmd = { "N° Facture","Client","Date"};
	
	Vector vc = new Vector();
    DefaultListModel listModel;
    
    
    Vector vc_f = new Vector();
    DefaultListModel listModel_f;
    private JTextField tf_n_fact;
    private JTextField tf_fact_rech;
	JComboBox cbTva = new JComboBox();

	
	public facturation() {
		setIconifiable(true);
		setClosable(true);
		setFrameIcon(new ImageIcon(facturation.class.getResource("/icones/original/A32/kivio-icone-3870-128_RS.png")));
		setTitle("Facturation");
		setBounds(100, 30, 1115, 564);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Bon a li\u00E9e", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 55, 431, 476);
		getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		tab_alerte = new JTable(d_tab,t_tab_b);
		tab_alerte.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				int row = tab_alerte.getSelectedRow();

			}				
		});
		
        		
		tab_alerte.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		tab_alerte.setRowHeight(30);
	
		
	   scrollPane1 = new JScrollPane(tab_alerte);
	   panel.add(scrollPane1);
		
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_1.setBounds(451, 21, 634, 508);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		btnBc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				b_liv(tab_b_cmd.getValueAt(tab_b_cmd.getSelectedRow(), 0)+"");		

			}
		});
		
		btnBc.setEnabled(false);
		btnBc.setFont(new Font("Century Gothic", Font.ITALIC, 15));
		btnBc.setBounds(398, 426, 230, 41);
		panel_1.add(btnBc);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Liste des factures", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(59, 59, 59)));
		panel_3.setBounds(10, 243, 363, 254);
		
		
		tab_b_cmd = new JTable(d_tab_b_cmd,t_tab_b_b_cmd);
		tab_b_cmd.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
			 int row_c = tab_b_cmd.getSelectedRow();

			 btnSupprimer.setEnabled(true);
			 btnBc.setEnabled(true);
			 
			  vc_.removeAllElements();
   			    String req =  "select NUMMIS from TRANS_MISSION where FACTURATION like '"+tab_b_cmd.getValueAt(tab_b_cmd.getSelectedRow(), 0)+"' ";
   			    Statement stm;
				try {
					stm = FirstPg.con.createStatement();
					 ResultSet	rst = stm.executeQuery(req);  
					 
	   			    	while(rst.next()){
	   			    	    vc_.addElement(rst.getString(1));
	   			    	}
	   			    	
	   			    		list_.setListData(vc_);
	   			    	    list_.repaint();
	   			    	    
	   			    	    if(vc_.size()>0){
	   			    	    	btnSupprimer.setEnabled(true);
	   			    	    }else{
	   			    	    	btnSupprimer.setEnabled(false);
	   			    	    }
	   			    	    
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}				
		});
		panel_3.setLayout(new BorderLayout(0, 0));
		
		
		tab_b_cmd.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		tab_b_cmd.setRowHeight(30);
				
	   JScrollPane scrollPane1_c_b = new JScrollPane(tab_b_cmd);
	   panel_3.add(scrollPane1_c_b);
		
		
		panel_1.add(panel_3);
		
		btnSupprimer.setEnabled(false);
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int conf = JOptionPane.showConfirmDialog(null, "veuillez confirmer l'opération SVP.", "confirmation de creation",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if(conf == JOptionPane.OK_OPTION){
					String nFcvtt = numRote.factureNum(nfct());
				
					Statement stm;
					try {
						stm = FirstPg.con.createStatement();
						String req = "select VERSEMENT from TRANS_CREANCE_FCT where NUMFCT like '"+tab_b_cmd.getValueAt(tab_b_cmd.getSelectedRow(), 0)+"'";
						ResultSet rst = stm.executeQuery(req);
						boolean bl = false;
						while (rst.next()){
							if(rst.getDouble(1) > 0){
								bl = true;
								break;
							}
						}
				
					if(bl){
						new Message(1, "Des versements ont été efféctuer sur cette facture.\nVeuillez supprimer les versements pour pouvoir rejeter la facture.");
					}else{
						  req = "declare "+
									"begin " +
									"for item in (select NUMMIS, CODE_C, DATEMIS, DATEALLEZ, DATERETOUR, DISTINATION, DETAIL, PRIXHT, FACTURATION, PAYE, IDCHAUF, IDACCOMP from TRANS_MISSION where FACTURATION like '"+tab_b_cmd.getValueAt(tab_b_cmd.getSelectedRow(), 0)+"' ) loop " +
										"insert into TRANS_MISSION_RJT (NUMMIS, CODE_C, DATEMIS, DATEALLEZ, DATERETOUR, DISTINATION, DETAIL, PRIXHT, FACTURATION, PAYE, IDCHAUF, IDACCOMP) values (item.NUMMIS, item.CODE_C, item.DATEMIS, item.DATEALLEZ, item.DATERETOUR, item.DISTINATION, item.DETAIL, item.PRIXHT, item.FACTURATION, item.PAYE, item.IDCHAUF, item.IDACCOMP); " +
										"insert into TRANS_CREANCE_MIS(NUMMIS, PRIXHT, VERSEMENT, NUMVERS, CODE_C)values(item.NUMMIS, item.PRIXHT, 0, '', item.CODE_C); "+
									"end loop; " +
										
									"for item in (select NUMMIS, CODE_C, DATEMIS, DATEALLEZ, DATERETOUR, DISTINATION, DETAIL, PRIXHT, FACTURATION, PAYE, IDCHAUF, IDACCOMP from TRANS_MISSION where FACTURATION like '"+tab_b_cmd.getValueAt(tab_b_cmd.getSelectedRow(), 0)+"' ) loop " +
										"insert into TRANS_MISSION_RJT (NUMMIS, CODE_C, DATEMIS, DATEALLEZ, DATERETOUR, DISTINATION, DETAIL, PRIXHT, FACTURATION, PAYE, IDCHAUF, IDACCOMP) values (item.NUMMIS, item.CODE_C, item.DATEMIS, item.DATEALLEZ, item.DATERETOUR, item.DISTINATION, item.DETAIL, item.PRIXHT, '"+nFcvtt+"', item.PAYE, item.IDCHAUF, item.IDACCOMP); " +
									"end loop; " +
									
									"for item in (select DATEFCT, CODE_C, PRIXHT, PRIXTTC, TYPEPAYEMENT, PAYE, AVOIR, NUMFCTCH, TVA from TRANS_FACTURATION where NUMFCT like '"+tab_b_cmd.getValueAt(tab_b_cmd.getSelectedRow(), 0)+"' ) loop " +
									  "INSERT INTO TRANS_FACTURATION (NUMFCT, DATEFCT, CODE_C, PRIXHT, PRIXTTC, TYPEPAYEMENT, PAYE, AVOIR, NUMFCTCH, TVA) VALUES ('"+numRote.factureNum(nfct())+"',item.DATEFCT, item.CODE_C, item.PRIXHT, item.PRIXTTC, item.TYPEPAYEMENT, item.PAYE, 1, '"+tab_b_cmd.getValueAt(tab_b_cmd.getSelectedRow(), 0)+"', item.TVA);"+
									"end loop; "+
									  
									"Update TRANS_MISSION set FACTURATION = '' where FACTURATION like '"+tab_b_cmd.getValueAt(tab_b_cmd.getSelectedRow(), 0)+"'; "+
									"Update TRANS_FACTURATION set AVOIR = 1 where NUMFCT like '"+tab_b_cmd.getValueAt(tab_b_cmd.getSelectedRow(), 0)+"'; "+
									
									"Delete from TRANS_CREANCE_FCT where NUMFCT like '"+tab_b_cmd.getValueAt(tab_b_cmd.getSelectedRow(), 0)+"'; "+
									
									"end; ";
				       
				       
						CallableStatement cstm = FirstPg.con.prepareCall(req);
						cstm.execute();
						cstm.close();
						
						rech_comb(2);
						qte_alerte("%");
						JOptionPane.showMessageDialog(null, "Opération effectué avec succès","Information",JOptionPane.INFORMATION_MESSAGE);

					}
					
				            
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				       

			}
		});
		btnSupprimer.setFont(new Font("Century Gothic", Font.ITALIC, 15));
		btnSupprimer.setBounds(398, 468, 230, 33);
		panel_1.add(btnSupprimer);
		
		listModel = new DefaultListModel();
		vc.addElement("ok ok");
	        
	        
	        listModel_f = new DefaultListModel();
			vc_f.addElement("ok ok");
		        
		        JButton btnFactureCommune = new JButton("Création facture(s)");
		        btnFactureCommune.addActionListener(new ActionListener() {
		        	public void actionPerformed(ActionEvent e) {
		        		
		        		boolean passe = false;
		        		for(int i = 0; i<tab_alerte.getRowCount(); i++){
		        			if(tab_alerte.getValueAt(i, 4).toString().equals("true")){
		        				passe = true;
		        				break;
		        			}
		        		}
		        		
		        		if(passe){
		        			int conf = JOptionPane.showConfirmDialog(null, "Veuillez confirmer l'opération SVP.", "confirmation de creation",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
						if(conf == JOptionPane.OK_OPTION){
							
							def_t_b_cmd = new DefaultTableModel(){ public boolean isCellEditable(int row, int column) { return false;} };
							def_t_b_cmd.setColumnIdentifiers(t_tab_b_b_cmd);						
						
							
							try {
								String verif = "";
								for(int i = 0; i<tab_alerte.getRowCount(); i++){
									if(tab_alerte.getValueAt(i, 4).toString().equals("true")){
										verif = verif + ", "+InvoicedBon(tab_alerte.getValueAt(i, 0)+"");
									}
								}
								
								if(!verif.equals(", ")){
									new Message(1, "Attention:\nDes versements ont été déja effectuer sur les bons suivant:\n"+verif+"\nVeuillez annuler les versements sur ces bons pour créer la facture liée.");
								}else{
																										
								if(ch_fctBl.isSelected()){
													for(int i = 0; i<tab_alerte.getRowCount(); i++){
														
														String req = "Declare " +
																 "PRIX number(11,2):=0; " +
																 "TOTHT number(11,2):=0; " +
																 "DTimbre number(11,2):=0; " +
																 "Begin ";
														
														
											        			if(tab_alerte.getValueAt(i, 4).toString().equals("true")){
											        				req = req + " Update TRANS_MISSION set FACTURATION = '"+numRote.factureNum(nfct())+"' where NUMMIS like '"+tab_alerte.getValueAt(i, 0).toString()+"'; ";
											        				req = req + " Select PRIXHT into PRIX from TRANS_MISSION where NUMMIS like '"+tab_alerte.getValueAt(i, 0).toString()+"'; " +
											        						    " TOTHT:= TOTHT + PRIX; " +
											        				            " delete from TRANS_CREANCE_MIS where NUMMIS like '"+tab_alerte.getValueAt(i, 0).toString()+"'; ";
											        			}
									        		
																req = req + " PRIX := (TOTHT*"+(cbTva.getSelectedIndex()+1)+")/100; ";
																req = req + " PRIX := TOTHT + PRIX; ";
																if(cb_type_p.getSelectedItem().equals("Espèce")){
																	req = req + "DTimbre := PRIX*0.01; " +
																			"IF(DTimbre > 2500) THEN DTimbre := 2500; " +
																			"END IF; ";
																	req = req + " PRIX := DTimbre + PRIX; ";	
																}
																int py = 0; if(ch_paye.isSelected()) py = 1;
																  req = req + " INSERT INTO TRANS_FACTURATION (NUMFCT, DATEFCT, CODE_C, PRIXHT, PRIXTTC, TYPEPAYEMENT, PAYE, AVOIR, NUMFCTCH, TVA) VALUES ('"+numRote.factureNum(nfct())+"','"+dt.form_Aff(DataBase.date_act(FirstPg.con))+"','"+codification.cd_prs(cb_client.getSelectedItem()+"")+"', TOTHT, PRIX, '"+cb_type_p.getSelectedItem()+"', "+py+", 0,'',"+(cbTva.getSelectedIndex()+1)+" );";
																 
																  if(!ch_paye.isSelected()){
																	  req = req + " INSERT INTO TRANS_CREANCE_FCT (NUMFCT, PRIXTTC, VERSEMENT, NUMVERS, CODE_C) VALUES ('"+numRote.factureNum(nfct())+"',PRIX,0,'','"+codification.cd_prs(cb_client.getSelectedItem()+"")+"');";
																  }
																  
																  req = req + " end;";
																	CallableStatement cstm = FirstPg.con.prepareCall(req);
																	cstm.execute();
													}		
											}else{
												
												String req = "Declare " +
														 "PRIX number(11,2):=0; " +
														 "TOTHT number(11,2):=0; " +
														 "DTimbre number(11,2):=0; " +
														 "Begin ";
												
															for(int i = 0; i<tab_alerte.getRowCount(); i++){
											        			if(tab_alerte.getValueAt(i, 4).toString().equals("true")){
											        				req = req + " Update TRANS_MISSION set FACTURATION = '"+tf_n_fact.getText()+"' where NUMMIS like '"+tab_alerte.getValueAt(i, 0).toString()+"'; ";
											        				req = req + " Select PRIXHT into PRIX from TRANS_MISSION where NUMMIS like '"+tab_alerte.getValueAt(i, 0).toString()+"'; " +
											        						    " TOTHT:= TOTHT + PRIX; "+
											        						    " delete from TRANS_CREANCE_MIS where NUMMIS like '"+tab_alerte.getValueAt(i, 0).toString()+"'; ";

											        			}
											        		}
															
															req = req + " PRIX := (TOTHT*"+(cbTva.getSelectedIndex()+1)+")/100; ";
															req = req + " PRIX := TOTHT + PRIX; ";
															if(cb_type_p.getSelectedItem().equals("Espèce")){
																req = req + "DTimbre := PRIX*0.01; " +
																		"IF(DTimbre > 2500) THEN DTimbre := 2500; " +
																		"END IF; ";
																req = req + " PRIX := DTimbre + PRIX; ";	
															}
															int py = 0; if(ch_paye.isSelected()) py = 1;
															req = req + " INSERT INTO TRANS_FACTURATION (NUMFCT, DATEFCT, CODE_C, PRIXHT, PRIXTTC, TYPEPAYEMENT, PAYE, AVOIR, NUMFCTCH, TVA) VALUES ('"+tf_n_fact.getText()+"','"+dt.form_Aff(DataBase.date_act(FirstPg.con))+"','"+codification.cd_prs(cb_client.getSelectedItem()+"")+"', TOTHT, PRIX, '"+cb_type_p.getSelectedItem()+"', "+py+", 0,'',"+(cbTva.getSelectedIndex()+1)+" );";
															  if(!ch_paye.isSelected()){
																  req = req + " INSERT INTO TRANS_CREANCE_FCT (NUMFCT, PRIXTTC, VERSEMENT, NUMVERS, CODE_C) VALUES ('"+tf_n_fact.getText()+"',PRIX,0,'','"+codification.cd_prs(cb_client.getSelectedItem()+"")+"');";
															  }
															req = req + " end;";
															
															CallableStatement cstm = FirstPg.con.prepareCall(req);
															cstm.execute();
								}
								
								qte_alerte("%");
								tf_n_fact.setText(numRote.factureNum(nfct()));
								rech_comb(2);
								
									JOptionPane.showMessageDialog(null, "Opération effectué avec succès","Information",JOptionPane.INFORMATION_MESSAGE);
								
								}
									
									
									
								} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
						}
		        		
		        		}else{
							JOptionPane.showMessageDialog(null, "Veuillez selectionné les bons a liée SVP.","Attention",JOptionPane.WARNING_MESSAGE);
		        		}
		        		
		        		
		        	}
		        });
		        btnFactureCommune.setFont(new Font("Consolas", Font.PLAIN, 14));
		        btnFactureCommune.setBounds(74, 198, 230, 33);
		        panel_1.add(btnFactureCommune);
		        
		        JSeparator separator = new JSeparator();
		        separator.setOrientation(SwingConstants.VERTICAL);
		        separator.setBounds(385, 10, 15, 489);
		        panel_1.add(separator);
		        ch_paye.setFont(new Font("Consolas", Font.PLAIN, 14));
		        ch_paye.setBounds(25, 47, 76, 26);
		        panel_1.add(ch_paye);
		        
		        
		        ch_date.setSelected(true);
		        ch_date.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 16));
		        ch_date.setBounds(398, 382, 76, 33);
		        panel_1.add(ch_date);
		        
		        JLabel lblTypeDePaiement = new JLabel("Type de paiement");
		        lblTypeDePaiement.setHorizontalAlignment(SwingConstants.RIGHT);
		        lblTypeDePaiement.setFont(new Font("Consolas", Font.PLAIN, 14));
		        lblTypeDePaiement.setBounds(10, 85, 144, 26);
		        panel_1.add(lblTypeDePaiement);
		        
		
		        cb_type_p.setFont(new Font("Consolas", Font.PLAIN, 14));
		        cb_type_p.setModel(new DefaultComboBoxModel(new String[] {"Esp\u00E8ce", "Virement", "A terme", "Cheque"}));
		        cb_type_p.setBounds(171, 80, 133, 33);
		        panel_1.add(cb_type_p);
		       
		        
		        
		        
		        list_ = new JList(vc_);
  		 		 sc_pane.setBounds(398, 236, 220, 117);
  		 		 panel_1.add(sc_pane);
  		 		 sc_pane.setViewportView(list_);
  		 		 list_.setFont(new Font("Century Gothic", Font.PLAIN, 14));
  		 		 
  		 		 JLabel lblBonLie = new JLabel("Bon li\u00E9e");
  		 		 lblBonLie.setHorizontalAlignment(SwingConstants.CENTER);
  		 		 lblBonLie.setFont(new Font("Century Gothic", Font.ITALIC, 14));
  		 		 lblBonLie.setBounds(398, 217, 220, 26);
  		 		 panel_1.add(lblBonLie);
  		 		 
  		 		 JPanel panel_2 = new JPanel();
  		 		 panel_2.setBorder(new TitledBorder(null, "Recherche", TitledBorder.CENTER, TitledBorder.TOP, null, null));
  		 		 panel_2.setBounds(391, 10, 237, 203);
  		 		 panel_1.add(panel_2);
  		 		 panel_2.setLayout(null);
  		 		 
  		 		 JLabel lblClient_1 = new JLabel("Client");
  		 		 lblClient_1.setFont(new Font("Century Gothic", Font.PLAIN, 12));
  		 		 lblClient_1.setBounds(27, 19, 46, 23);
  		 		 panel_2.add(lblClient_1);
  		 		 
  		 		 JLabel lblDate = new JLabel("Date");
  		 		 lblDate.setFont(new Font("Century Gothic", Font.PLAIN, 12));
  		 		 lblDate.setBounds(16, 58, 46, 16);
  		 		 panel_2.add(lblDate);
  		 		 
  		 		 JLabel lblNumFacture = new JLabel("Num Facture");
  		 		 lblNumFacture.setHorizontalAlignment(SwingConstants.CENTER);
  		 		 lblNumFacture.setFont(new Font("Century Gothic", Font.PLAIN, 12));
  		 		 lblNumFacture.setBounds(46, 129, 159, 23);
  		 		 panel_2.add(lblNumFacture);
  		 		 
  		 		 JLabel lblDu = new JLabel("Du");
  		 		 lblDu.setFont(new Font("Century Gothic", Font.PLAIN, 12));
  		 		 lblDu.setBounds(61, 58, 30, 16);
  		 		 panel_2.add(lblDu);
  		 		 
  		 		 JLabel lblAu = new JLabel("Au");
  		 		 lblAu.setFont(new Font("Century Gothic", Font.PLAIN, 12));
  		 		 lblAu.setBounds(61, 92, 30, 16);
  		 		 panel_2.add(lblAu);
  		 		 cb_client_rech.addActionListener(new ActionListener() {
  		 		 	public void actionPerformed(ActionEvent e) {
  		 		 	 rech_comb(0);
  		 		 	}
  		 		 });
  		 		 
  		 	
  		 		 cb_client_rech.setBounds(72, 17, 159, 26);
  		 		 panel_2.add(cb_client_rech);
  		 		 
  		 		 tf_fact_rech = new JTextField();
  		 		 tf_fact_rech.addKeyListener(new KeyAdapter() {
  		 		 	@Override
  		 		 	public void keyReleased(KeyEvent e) {
  		 		 	 rech_comb(2);
  		 		 	}
  		 		 });
  		 		 tf_fact_rech.setBounds(46, 150, 159, 28);
  		 		 panel_2.add(tf_fact_rech);
  		 		 tf_fact_rech.setColumns(10);
  		 		 
  		 		 
  		 		 dt_du.addActionListener(new ActionListener() {
  					public void actionPerformed(ActionEvent e) {
  						
  						Date dt_d = dt_du.getDate();
  						Date dt_f = dt_au.getDate();
  						
  						if(dt_d.getTime()>dt_f.getTime()){
  						    JOptionPane.showMessageDialog(null,"La date de début de recherche ne peut etre supérieur a la date de fin de recherche","Information",JOptionPane.INFORMATION_MESSAGE);
  						    dt_au.setDate(dt_du.getDate());
  						}
  						
  						
  					}
  				});
  				
  				
  				dt_du.setDate(new Date());
  				dt_du.setFont(new Font("Century Gothic", Font.PLAIN, 11));
  				dt_du.setBounds(89, 55, 142, 25);
  				panel_2.add(dt_du);
  				dt_au.addActionListener(new ActionListener() {
  					public void actionPerformed(ActionEvent e) {
  						
  						Date dt_d = dt_du.getDate();
  						Date dt_f = dt_au.getDate();
  						
  						if(dt_d.getTime()>dt_f.getTime()){
  						    JOptionPane.showMessageDialog(null,"La date de fin de recherche ne peut etre inférieur a la date de debut de recherche","Information",JOptionPane.INFORMATION_MESSAGE);
  						    dt_du.setDate(dt_au.getDate());
  						}
  						
  						rech_comb(1);
  						
  					}
  				});
  				
  				dt_au.setDate(new Date());
  				dt_au.setFont(new Font("Century Gothic", Font.PLAIN, 11));
  				dt_au.setBounds(89, 86, 142, 25);
  				panel_2.add(dt_au);
  				
  				
  				
  				JLabel lblNumCmd = new JLabel("Num Facture");
  				lblNumCmd.setHorizontalAlignment(SwingConstants.RIGHT);
  				lblNumCmd.setBounds(10, 7, 144, 25);
  				panel_1.add(lblNumCmd);
  				lblNumCmd.setFont(new Font("Consolas", Font.PLAIN, 14));
  				
  				tf_n_fact = new JTextField();
  				tf_n_fact.setHorizontalAlignment(SwingConstants.CENTER);
  				tf_n_fact.setBounds(166, 6, 138, 26);
  				panel_1.add(tf_n_fact);
  				tf_n_fact.setForeground(new Color(0, 100, 0));
  				tf_n_fact.setBackground(Color.ORANGE);
  				tf_n_fact.setEditable(false);
  				tf_n_fact.setFont(new Font("Consolas", Font.PLAIN, 18));
  				tf_n_fact.setColumns(10);
  				
  				tf_n_fact.setText(numRote.factureNum(nfct()));
  				ch_fctBl.setHorizontalAlignment(SwingConstants.RIGHT);
  				ch_fctBl.setFont(new Font("Consolas", Font.PLAIN, 14));
  				ch_fctBl.setBounds(133, 48, 171, 24);
  				panel_1.add(ch_fctBl);
  				
  				chckbxArrondirLeDroit.setSelected(true);
  				chckbxArrondirLeDroit.setFont(new Font("Century Gothic", Font.ITALIC, 14));
  				chckbxArrondirLeDroit.setBounds(398, 357, 220, 26);
  				panel_1.add(chckbxArrondirLeDroit);
  				
  				JLabel lblTvaAppliqu = new JLabel("TVA Appliqu\u00E9");
  				lblTvaAppliqu.setHorizontalAlignment(SwingConstants.RIGHT);
  				lblTvaAppliqu.setFont(new Font("Consolas", Font.PLAIN, 14));
  				lblTvaAppliqu.setBounds(10, 118, 144, 26);
  				panel_1.add(lblTvaAppliqu);
  				
  				cbTva.setFont(new Font("Consolas", Font.PLAIN, 14));
  				cbTva.setBounds(171, 115, 76, 33);
  				panel_1.add(cbTva);
  				
  				for(int i = 1; i<51; i++){
  					cbTva.addItem(i+" %");
  				}
  				
  				cbTva.setSelectedIndex(18);
  				
  				JLabel lblClient = new JLabel("Client");
  				lblClient.setBounds(57, 23, 76, 33);
  				getContentPane().add(lblClient);
  				lblClient.setFont(new Font("Century Gothic", Font.ITALIC, 15));
  				cb_client.setBounds(122, 21, 229, 35);
  				getContentPane().add(cb_client);
  				cb_client.addActionListener(new ActionListener() {
  					public void actionPerformed(ActionEvent e) {
  						
  						qte_alerte(codification.cd_prs(cb_client.getSelectedItem()+""));
  						
  					}
  				});
  				
  				
  				cb_client.setFont(new Font("Century Gothic", Font.ITALIC, 16));

  				
  				
  				new Thread(new Runnable() {
					
					@Override
					public void run() {

						def_t_b_cmd = new DefaultTableModel(){ public boolean isCellEditable(int row, int column) { return false;} };
						def_t_b_cmd.setColumnIdentifiers(t_tab_b_b_cmd);
						
						Statement stm;
						try {
							stm = FirstPg.con.createStatement();
							String req = "select NUMFCT, CODE_C, DATEFCT from TRANS_FACTURATION order by  DATEFCT DESC";
						    ResultSet rst = stm.executeQuery(req);
							
						    while (rst.next()){
						    	def_t_b_cmd.addRow(new Object[]{rst.getString(1),DataBase.RechClientStr(" CODE_C like '"+rst.getString(2)+"'"), dt.form_Aff(rst.getDate(3))});
							}
						     tab_b_cmd.setModel(def_t_b_cmd);
						     
						    req = "select distinct NOM from TRANS_CLIENT order by nom";
						    
						    rst = stm.executeQuery(req);
						    
						    while(rst.next()){
						    	cb_client.addItem(rst.getString(1));
						    	cb_client_rech.addItem(rst.getString(1));
						    }

						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			
					}
				}).start();  
		        
	}
	
	
void qte_alerte(String ok){
		
		def_t = new DefaultTableModel(){
			 private static final long serialVersionUID = 1L;
			 
	            public Class getColumnClass(int column) {
	                switch (column) {
	                    case 0:
	                        return String.class;
	                    case 1:
	                        return String.class;
	                    case 2:
	                        return String.class;
	                    case 3:
	                        return String.class;
	                    default:
	                        return Boolean.class;
	                }
	            }
		};
        def_t.setColumnIdentifiers(t_tab_b);
		
		String req = "select NUMMIS, CODE_C, PAYE, DATEMIS from TRANS_MISSION where PAYE = 0 and CODE_C like '"+ok+"' and FACTURATION is null ORDER BY DATEMIS DESC";

		try {
			Statement stm = FirstPg.con.createStatement();
			ResultSet rst = stm.executeQuery(req);

			String py = "Payé";
			while(rst.next()){
				if(rst.getBoolean(3)) py = "Payé";
				else py = "Non Payé";
			   def_t.addRow(new Object[]{rst.getString(1),DataBase.RechClientStr( " CODE_C like '"+rst.getString(2)+"' "),dt.form_Aff(rst.getDate(4)),py});
			}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 tab_alerte.setModel(def_t);
			for(int i = 0; i < tab_alerte.getRowCount(); i++){
				tab_alerte.setValueAt(false, i, 4);
			}
			
	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

void rech_comb(int s){
	def_t_b_cmd = new DefaultTableModel(){ public boolean isCellEditable(int row, int column) { return false;} };
	def_t_b_cmd.setColumnIdentifiers(t_tab_b_b_cmd);
	
	Statement stm;
		try {
			stm = FirstPg.con.createStatement();
			String req = "";
		if(s==0){
			 req = "select NUMFCT, CODE_C, DATEFCT from TRANS_FACTURATION where CODE_C like '"+codification.cd_prs(cb_client_rech.getSelectedItem()+"")+"'  order by DATEFCT DESC";
		}else if(s==1){
			 req = "select NUMFCT, CODE_C, DATEFCT from TRANS_FACTURATION where DATEFCT between '"+dt.form_Aff(dt_du.getDate())+"' and '"+dt.form_Aff(dt_au.getDate())+"'  order by  DATEFCT DESC";
		}else{		
			 req = "select NUMFCT, CODE_C, DATEFCT from TRANS_FACTURATION where NUMFCT like '"+tf_fact_rech.getText()+"%'  order by  DATEFCT DESC";
		}
		
	    ResultSet rst = stm.executeQuery(req);
		
	    while (rst.next()){
	    	def_t_b_cmd.addRow(new Object[]{rst.getString(1),DataBase.RechClientStr("CODE_C like '"+rst.getString(2)+"'"), dt.form_Aff(rst.getDate(3))});
		}
		
	   
	     tab_b_cmd.setModel(def_t_b_cmd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

Vector nfct(){
	Vector vct = new Vector();
	try {
		Statement stm = FirstPg.con.createStatement();
		String req = "select NUMFCT from TRANS_FACTURATION";
		ResultSet rst = stm.executeQuery(req);
		while(rst.next()) vct.addElement(rst.getString(1));
		stm.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return vct;
}

void b_liv(String n_bon){
	
	try {
		Statement stm   = FirstPg.con.createStatement();
		String req = "select NOM, ADRESSE, NRC, NIF, RIB, TEL, FAX, MAIL, NIS from TRANS_INFO_PERSO";
		
		ResultSet rst = stm.executeQuery(req);
		String adresse = "";
		String nrc = "";
		String nif = "";
		String nis = "";
		String rib = "";
		String tel = "";
		String fax = "";
		String mail = "";
		String nom = "";
		while(rst.next()){
			adresse = rst.getString(2);
			nrc = rst.getString(3);
			nif = rst.getString(4);
			rib = rst.getString(5);
			tel = rst.getString(6);
			fax = rst.getString(7);
			mail = rst.getString(8);
			nom = rst.getString(1);
			nis = rst.getString(9);
		 }
		
		
	req = "select NOM, NRC, NIF, NAI, ADRESSE, TEL from TRANS_CLIENT where CODE_C like '"+fct.codification.cd_prs(tab_b_cmd.getValueAt(tab_b_cmd.getSelectedRow(), 1).toString())+"' ";
		
		rst = stm.executeQuery(req);
	   String nm_client = "";
	   String nrc_client = "";
	   String nif_client = "";

	   String adresse_client = "";
	   String tel_client = "";
	   String nai_client = "";
		
		while(rst.next()){
							    nm_client = rst.getString(1);
							    nrc_client = rst.getString(2);
							    nif_client = rst.getString(3);
							    nai_client = rst.getString(4);
							    adresse_client = rst.getString(5);
							    tel_client = rst.getString(6);
				 		 }
		
		
		try {
		  // JasperDesign jasperDesign;
			Map<String, Object> parameters = new HashMap<String, Object>(5);
			
			parameters.put("nom_entreprise", nom);
			if(ch_date.isSelected())parameters.put("dts", tab_b_cmd.getValueAt(tab_b_cmd.getSelectedRow(), 2));
			parameters.put("cd_client", fct.codification.cd_prs(tab_b_cmd.getValueAt(tab_b_cmd.getSelectedRow(), 1).toString()));
			parameters.put("adresse", adresse);
			parameters.put("tel", tel+"/"+fax);
			parameters.put("mail", mail);
			parameters.put("rc", nrc);
			parameters.put("compte", rib);
			parameters.put("ai", nis);
			parameters.put("nif", nif);
			parameters.put("nm_client", nm_client);
			parameters.put("adresse_client", adresse_client);
			parameters.put("nbl", n_bon);
			parameters.put("rc_client", nrc_client);
			parameters.put("nai_client", nai_client);
			parameters.put("nif_client", nif_client);
			parameters.put("telClient", tel_client);
			
		
      

		req = "select PRIXHT, TVA, TYPEPAYEMENT, AVOIR, NUMFCTCH, PRIXTTC from TRANS_FACTURATION where NUMFCT like '"+n_bon+"'";
		rst = stm.executeQuery(req);
		double ttc = 0; 
		String str = "";
		boolean blok = true;
		while(rst.next()){
			blok = rst.getBoolean(4);
			str = rst.getString(5);
			if(str != null){
				parameters.put("factureAvoir", "Facture d'avoir de la facture N° "+rst.getString(5));
				parameters.put("nomination", "Facture d'avoir");
			}
			else{
				parameters.put("nomination", "Facture");
				if(rst.getBoolean(4))parameters.put("Rejeter", "REJETER");
			}
			parameters.put("total_ht", fct.nbr.dbToDf(rst.getDouble(1)));
			parameters.put("tva", fct.nbr.dbToDf((rst.getDouble(1)*rst.getInt(2))/100));
			parameters.put("type_p", rst.getString(3));
			if(rst.getString(3).equals("Espèce")){
				if(chckbxArrondirLeDroit.isSelected()){
					double dTimbre = nbr.majorDb((((rst.getDouble(1)*rst.getInt(2))/100)+(rst.getDouble(1)))*0.01);
					if(dTimbre > 2500) dTimbre = 2500;
					parameters.put("d_timbre", fct.nbr.dbToDf(dTimbre));
					//ttc = ((rst.getDouble(1)*rst.getInt(2))/100)+(rst.getDouble(1))+nbr.majorDb((((rst.getDouble(1)*rst.getInt(2))/100)+(rst.getDouble(1)))*0.01);
				}else{
					double dTimbre = (((rst.getDouble(1)*rst.getInt(2))/100)+(rst.getDouble(1)))*0.01;
					parameters.put("d_timbre", fct.nbr.dbToDf(dTimbre));
					//ttc = ((rst.getDouble(1)*rst.getInt(2))/100)+(rst.getDouble(1))+((((rst.getDouble(1)*rst.getInt(2))/100)+(rst.getDouble(1)))*0.01);
				}
			parameters.put("ttc", fct.nbr.dbToDf(rst.getDouble(6))+" Da.");	
			}else{
				//ttc = ((rst.getDouble(1)*rst.getInt(2))/100)+(rst.getDouble(1));
				parameters.put("ttc", fct.nbr.dbToDf(rst.getDouble(6))+" Da.");	
			}
			ttc = rst.getDouble(6);
		}
		
		parameters.put("lettre", fct.nbr.EnLettre(ttc, "Dinar Algérien"));

		
	  		JasperPrint jasperPrint;
			
	  		if(blok){
	  			jasperPrint = JasperFillManager.fillReport("C:/JTrans/etat/Rejeter.jasper", parameters,FirstPg.con);
	  		}else{
	  			jasperPrint = JasperFillManager.fillReport("C:/JTrans/etat/facture.jasper", parameters,FirstPg.con);
	  		}
				    JasperViewer jw = new JasperViewer(jasperPrint,false);
	    	        jw.setVisible(true);
	    			
			} catch (JRException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		 stm.executeUpdate(req);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	}

String InvoicedBon(String nmMission) throws SQLException{
	String str = "";
	Statement stm = FirstPg.con.createStatement();
	String req = "select NUMMIS, VERSEMENT from TRANS_CREANCE_MIS where NUMMIS like '"+nmMission+"'";
	ResultSet rst = stm.executeQuery(req);
	while(rst.next()){
		if(rst.getDouble(2) > 0)str = rst.getString(1);
	}
	return str;
}


}
