package Mission;

import fct.DataBase;
import fct.Message;
import fct.codification;
import fct.dt;
import fct.numRote;
import fct.str;
import fct.tField;
import fct.table;
import interfaces.FirstPg;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JButton;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.view.JasperViewer;

import org.jdesktop.swingx.JXDatePicker;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CMissionPan extends JPanel {
	private JTextField tfDetail;
	private JTextField tfNumMission;

	  private JXDatePicker dt_act =  new JXDatePicker();
	  private JXDatePicker dt_du =  new JXDatePicker();
	  private JXDatePicker dt_au =  new JXDatePicker();
	  private JTextField tfDistination;
	  JCheckBox chCreance = new JCheckBox("Créance");
	  JSpinner spPrixHT = new JSpinner();
	  
		JCheckBox chckbxRecherche = new JCheckBox("Recherche");	
		
		JComboBox cbChauffeur = new JComboBox();
		JComboBox cbAccomp = new JComboBox();
		JComboBox cbVehicule = new JComboBox();
		JComboBox cbRemorque = new JComboBox();
		JComboBox cbClient = new JComboBox();
		JComboBox cbBcVeh = new JComboBox();
		JComboBox cbBcRmc = new JComboBox();
		
		JTable tab_fact;
		DefaultTableModel def_t;
		Object [][] d_tab= new Object[20][7];
	    String[] t_tab_b = { "N°Mission","Client","Chauffeur","Date","Montant","Etat"};
		
	    JButton btnConfirmer = new JButton("Confirmer");		
		JButton btnModifier = new JButton("Modifier");
		JButton btnSupprimer = new JButton("Annuler");


	public CMissionPan() {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(null);
		
		JLabel lblChauffeur = new JLabel("Chauffeur");
		lblChauffeur.setHorizontalAlignment(SwingConstants.RIGHT);
		lblChauffeur.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblChauffeur.setBounds(10, 11, 107, 22);
		add(lblChauffeur);
		
		JLabel lblAccompagnateur = new JLabel("Accompagnateur");
		lblAccompagnateur.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAccompagnateur.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblAccompagnateur.setBounds(10, 44, 107, 22);
		add(lblAccompagnateur);
		
		JLabel lblVehicule = new JLabel("Vehicule ID");
		lblVehicule.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVehicule.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblVehicule.setBounds(10, 77, 107, 22);
		add(lblVehicule);
		
		JLabel lblRemorque = new JLabel("Remorque ID");
		lblRemorque.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRemorque.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblRemorque.setBounds(302, 77, 107, 22);
		add(lblRemorque);
		
		JLabel lblNMission = new JLabel("N\u00B0 Mission");
		lblNMission.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNMission.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblNMission.setBounds(518, 44, 107, 22);
		add(lblNMission);
		
		JLabel lblDateMission = new JLabel("Date Mission");
		lblDateMission.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDateMission.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblDateMission.setBounds(518, 11, 107, 22);
		add(lblDateMission);
		
		JLabel lblDateAllez = new JLabel("Date D\u00E9part");
		lblDateAllez.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDateAllez.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblDateAllez.setBounds(10, 145, 107, 22);
		add(lblDateAllez);
		
		JLabel lblDateRetour = new JLabel("Date Retour");
		lblDateRetour.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDateRetour.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblDateRetour.setBounds(301, 145, 107, 22);
		add(lblDateRetour);
		
		JLabel lblDestination = new JLabel("Client");
		lblDestination.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDestination.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblDestination.setBounds(10, 190, 107, 22);
		add(lblDestination);
		
		JLabel lblDtailsMission = new JLabel("D\u00E9tails mission");
		lblDtailsMission.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDtailsMission.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblDtailsMission.setBounds(10, 225, 107, 22);
		add(lblDtailsMission);
		
		JLabel lblAdresse = new JLabel("Adresse Destination");
		lblAdresse.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAdresse.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblAdresse.setBounds(254, 190, 155, 22);
		add(lblAdresse);
		chCreance.setSelected(true);
		chCreance.setFont(new Font("Consolas", Font.PLAIN, 12));
		
		
		chCreance.setHorizontalAlignment(SwingConstants.RIGHT);
		chCreance.setBounds(285, 257, 80, 23);
		add(chCreance);
		
		JLabel lblPrixHt = new JLabel("Prix HT");
		lblPrixHt.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrixHt.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblPrixHt.setBounds(10, 258, 107, 22);
		add(lblPrixHt);
		

		btnConfirmer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(str.isEmpty(tfDistination.getText())){
					JOptionPane.showMessageDialog(null,"Attention : \nVeuillez entré l'adresse de destination svp !!","Attention",JOptionPane.WARNING_MESSAGE);
					tField.goTo(tfDistination);
					tField.precise(tfDistination);
				}else{
					try {
						
						tfNumMission.setText(numMission());
						String errline = ErrLineMission(0);

						if(!errline.equals("")){
							JOptionPane.showMessageDialog(null,"Attention:\n"+errline,"Attention",JOptionPane.WARNING_MESSAGE);
						}else{
								   int x = 1;
									if(chCreance.isSelected()) x = 0;
									
								   String req = "Declare Begin ";
									
									req =  req + " insert into TRANS_MISSION (NUMMIS, CODE_C, DATEMIS, DATEALLEZ, DATERETOUR, DISTINATION, DETAIL, PRIXHT, FACTURATION, PAYE,IDCHAUF, IDACCOMP)values " +
																		   "('"+tfNumMission.getText()+"','"+codification.cd_prs(cbClient.getSelectedItem()+"")+"','"+dt.form_Aff(dt_act.getDate())+"','"+dt.form_Aff(dt_du.getDate())+"','"+dt.form_Aff(dt_au.getDate())+"'," +
																		   													"'"+tfDistination.getText()+"','"+tfDetail.getText()+"',"+spPrixHT.getValue()+",'',"+x+", " +
																		   																								    "'"+codification.cd_prs(cbChauffeur.getSelectedItem()+"")+"', '"+codification.cd_prs(cbAccomp.getSelectedItem()+"")+"'); ";
									int vlouer = 0;
									if(cbBcVeh.getItemCount()>0 && cbBcRmc.getItemCount()>0) vlouer = 3;
								    if(cbBcVeh.getItemCount()>0 ) {
								    	if(cbBcVeh.getSelectedItem().toString().length()>0)vlouer = 1;
								    }
								    if(cbBcRmc.getItemCount()>0 ) {
								    	if(cbBcRmc.getSelectedItem().toString().length()>0)vlouer = 2;
								    }
								    	if(vlouer==0){
									        req = req + " insert into TRANS_ETAT_MISSION (NUMMIS, IDVEHICULE, IDREMORQUE, FRAISMISSION, KM, IDVEHICULELOUER, IDREMORQUELOUER,INDX) values ('"+tfNumMission.getText()+"','"+cbVehicule.getSelectedItem()+"','"+cbRemorque.getSelectedItem()+"',0, 0, '','',"+vlouer+"); ";
								    	}else if(vlouer==1){
									        req = req + " insert into TRANS_ETAT_MISSION (NUMMIS, IDVEHICULE, IDREMORQUE, FRAISMISSION, KM, IDVEHICULELOUER, IDREMORQUELOUER,INDX) values ('"+tfNumMission.getText()+"','','"+cbRemorque.getSelectedItem()+"',0, 0, '"+cbVehicule.getSelectedItem()+"','',"+vlouer+"); ";
								    	}else if(vlouer==2){
									        req = req + " insert into TRANS_ETAT_MISSION (NUMMIS, IDVEHICULE, IDREMORQUE, FRAISMISSION, KM, IDVEHICULELOUER, IDREMORQUELOUER,INDX) values ('"+tfNumMission.getText()+"','"+cbVehicule.getSelectedItem()+"','',0, 0, '','"+cbRemorque.getSelectedItem()+"',"+vlouer+"); ";
								    	}else{
									        req = req + " insert into TRANS_ETAT_MISSION (NUMMIS, IDVEHICULE, IDREMORQUE, FRAISMISSION, KM, IDVEHICULELOUER, IDREMORQUELOUER,INDX) values ('"+tfNumMission.getText()+"','','',0, 0, '"+cbVehicule.getSelectedItem()+"','"+cbRemorque.getSelectedItem()+"',"+vlouer+"); ";
								    	}
								    	
									if(chCreance.isSelected()){
										req = req + " insert into TRANS_CREANCE_MIS (NUMMIS, PRIXHT, VERSEMENT, NUMVERS, CODE_C) values ('"+tfNumMission.getText()+"',"+spPrixHT.getValue()+",0,'','"+codification.cd_prs(cbClient.getSelectedItem()+"")+"'); ";
									}
									
									req = req + " end;";
									req = req.replaceAll("null", "");
									CallableStatement cstm = FirstPg.con.prepareCall(req);
									cstm.execute();
			
									JOptionPane.showMessageDialog(null,"Insertion effectuée avec succée","Information",JOptionPane.INFORMATION_MESSAGE);
									tfNumMission.setText(numMission());
									RechTeb("");
									}
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		btnConfirmer.setFont(new Font("Consolas", Font.PLAIN, 12));
		btnConfirmer.setBounds(10, 302, 147, 25);
		add(btnConfirmer);
		btnModifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(str.isEmpty(tfDistination.getText())){
					JOptionPane.showMessageDialog(null,"Attention : \nVeuillez entré l'adresse de destination svp !!","Attention",JOptionPane.WARNING_MESSAGE);
					tField.goTo(tfDistination);
					tField.precise(tfDistination);
				}else{
					try {

						String errline = ErrLineMission(1);
						
						if(!errline.equals("")){
							JOptionPane.showMessageDialog(null,"Attention:\n"+errline,"Attention",JOptionPane.WARNING_MESSAGE);
						}else{
							tab_fact.getValueAt(tab_fact.getSelectedRow(), tab_fact.getSelectedColumn());
							Statement stm = FirstPg.con.createStatement();
							String req = "select VERSEMENT from TRANS_CREANCE_MIS where NUMMIS like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"'";
							ResultSet rst = stm.executeQuery(req);
							double vrs = 0;
							while(rst.next()) vrs = rst.getDouble(1);
							
						    req = "select FRAISMISSION from TRANS_ETAT_MISSION where NUMMIS like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"'";
							rst = stm.executeQuery(req);
							double vrs_ = 0;
							while(rst.next()) vrs_ = rst.getDouble(1);
							
							req = "select * from TRANS_BGASOIL_MISSION where NUMMIS like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"'";
							 rst = stm.executeQuery(req);
							boolean tst = false;
							while(rst.next()) {
								tst = true;
								break;
							}
							
							
							req = "select FACTURATION from TRANS_MISSION where NUMMIS like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"'";
							 rst = stm.executeQuery(req);
								String fctNum = "";
								while(rst.next()) {
									fctNum = rst.getString(1);
									break;
								}
							
								try {
									fctNum.equals("");
								} catch (Exception e) {
									fctNum = "";
									}
								
							if(!fctNum.equals("")){
								new Message(1,"Attention:\nN°Mission affecter a l'a facture N°: "+fctNum+".\nVeuillez Rejeter la facture pour pouvoir modifier la mission.");
							}else if(vrs > 0 || tst || vrs_ > 0){
								 
								JOptionPane.showMessageDialog(null,"Attention:\nVeuillez effacer les versements, les bon de gasoil et les frais de mission effectuer sur cette mission.\nModification Impossible.","Attention",JOptionPane.WARNING_MESSAGE);
							}else{
								int vlouer = 0;
								if(cbBcVeh.getItemCount()>0 && cbBcRmc.getItemCount()>0) vlouer = 3;
							    if(cbBcVeh.getItemCount()>0 ) {
							    	if(cbBcVeh.getSelectedItem().toString().length()>0)vlouer = 1;
							    }
							    if(cbBcRmc.getItemCount()>0 ) {
							    	if(cbBcRmc.getSelectedItem().toString().length()>0)vlouer = 2;
							    }
	
	
								
								req =       "declare begin ";
								req = req + " Update TRANS_CREANCE_MIS set PRIXHT = "+spPrixHT.getValue()+" where NUMMIS like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"'; ";
								req = req + "delete from TRANS_ETAT_MISSION where NUMMIS like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"'; ";
	
								if(vlouer==0){
							        req = req + " insert into TRANS_ETAT_MISSION (NUMMIS, IDVEHICULE, IDREMORQUE, FRAISMISSION, KM, IDVEHICULELOUER, IDREMORQUELOUER,INDX) values ('"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"','"+cbVehicule.getSelectedItem()+"','"+cbRemorque.getSelectedItem()+"',0, 0, '','',"+vlouer+"); ";
						    	}else if(vlouer==1){
							        req = req + " insert into TRANS_ETAT_MISSION (NUMMIS, IDVEHICULE, IDREMORQUE, FRAISMISSION, KM, IDVEHICULELOUER, IDREMORQUELOUER,INDX) values ('"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"','','"+cbRemorque.getSelectedItem()+"',0, 0, '"+cbVehicule.getSelectedItem()+"','',"+vlouer+"); ";
						    	}else if(vlouer==2){
							        req = req + " insert into TRANS_ETAT_MISSION (NUMMIS, IDVEHICULE, IDREMORQUE, FRAISMISSION, KM, IDVEHICULELOUER, IDREMORQUELOUER,INDX) values ('"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"','"+cbVehicule.getSelectedItem()+"','',0, 0, '','"+cbRemorque.getSelectedItem()+"',"+vlouer+"); ";
						    	}else{
							        req = req + " insert into TRANS_ETAT_MISSION (NUMMIS, IDVEHICULE, IDREMORQUE, FRAISMISSION, KM, IDVEHICULELOUER, IDREMORQUELOUER,INDX) values ('"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"','','',0, 0, '"+cbVehicule.getSelectedItem()+"','"+cbRemorque.getSelectedItem()+"',"+vlouer+"); ";
						    	}
								
								int x = 0;
								if(!chCreance.isSelected()){
									x = 1;
										req = req + " Delete from TRANS_CREANCE_MIS where NUMMIS like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"'; "; 	
								}else{
									x = 0;
									String req0 = "select * from TRANS_CREANCE_MIS where NUMMIS like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"' "; 	
									rst = stm.executeQuery(req0);
									
									boolean bld = false;
									while(rst.next()) bld = true;
									if(!bld){
										req = req + " insert into TRANS_CREANCE_MIS (NUMMIS, PRIXHT, VERSEMENT, NUMVERS, CODE_C) values ('"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"',"+spPrixHT.getValue()+",0,'','"+codification.cd_prs(cbClient.getSelectedItem()+"")+"'); ";
									}
								}
								
								req = req + " Update TRANS_MISSION set PAYE = "+x+" ,CODE_C = '"+codification.cd_prs(cbClient.getSelectedItem()+"")+"', DATEMIS = '"+dt.form_Aff(dt_act.getDate())+"', DATEALLEZ = '"+dt.form_Aff(dt_du.getDate())+"', DATERETOUR = '"+dt.form_Aff(dt_au.getDate())+"', DISTINATION = '"+tfDistination.getText()+"',DETAIL = '"+tfDetail.getText()+"',PRIXHT = "+spPrixHT.getValue()+", IDCHAUF = '"+codification.cd_prs(cbChauffeur.getSelectedItem()+"")+"', IDACCOMP = '"+codification.cd_prs(cbAccomp.getSelectedItem()+"")+"' where NUMMIS like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"'; ";
								req = req + " end;";
								req = req.replaceAll("null", "");
								
								CallableStatement cstm = FirstPg.con.prepareCall(req);
								cstm.execute();
								cstm.close();
	
								JOptionPane.showMessageDialog(null,"Modification effectuée avec succée","Information",JOptionPane.INFORMATION_MESSAGE);
	
							}
							stm.close();
							RechTeb("");
						}
						
					} catch (Exception e) {
						  JOptionPane.showMessageDialog(null,"Veillez selectionné la mission a Modifier svp !"+e,"Attention",JOptionPane.WARNING_MESSAGE);
					
					}
				}
			}
		});
		
		btnModifier.setFont(new Font("Consolas", Font.PLAIN, 12));
		btnModifier.setBounds(10, 330, 147, 25);
		add(btnModifier);
		
		
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					tab_fact.getValueAt(tab_fact.getSelectedRow(), tab_fact.getSelectedColumn());
					Statement stm = FirstPg.con.createStatement();
					String req = "select VERSEMENT from TRANS_CREANCE_MIS where NUMMIS like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"'";
					ResultSet rst = stm.executeQuery(req);
					double vrs = 0;
					while(rst.next()) vrs = rst.getDouble(1);
					
					req = "select FACTURATION from TRANS_MISSION where NUMMIS like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"'";
					 rst = stm.executeQuery(req);
						
						String fctNum = "";
						while(rst.next()) {
							fctNum = rst.getString(1);
							break;
						}
					
						try {
							fctNum.equals("");
						} catch (Exception e) {
							fctNum = "";
							}
						
					if(!fctNum.equals("")){
						new Message(1,"Attention:\nN°Mission affecter a l'a facture N°: "+fctNum+".\nVeuillez Rejeter la facture pour pouvoir supprimer la mission.");
					}else if(vrs > 0){
						int conf = JOptionPane.showConfirmDialog(null, "Attention:\nUn versement est déja effectuer sur cette mission.\nVoulez vous vraiment effacer Annuler la mission !!?", "confirmation de suppression",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
						if(conf == JOptionPane.OK_OPTION){
							
							req = "declare begin ";
							
							req = req + " delete from TRANS_CREANCE_MIS where NUMMIS like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"'; ";
							req = req + " delete from TRANS_ETAT_MISSION where NUMMIS like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"'; ";
							req = req + " delete from TRANS_BGASOIL_MISSION where NUMMIS like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"'; ";
							req = req + " delete from TRANS_MISSION where NUMMIS like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"'; ";
							req = req + " end;";
							req = req.replaceAll("null", "");

							CallableStatement cstm = FirstPg.con.prepareCall(req);
							cstm.execute();
							cstm.close();
							JOptionPane.showMessageDialog(null,"Suppression effectuée avec succée","Information",JOptionPane.INFORMATION_MESSAGE);
						}
						
					}else{
						
						req =       "declare begin ";
						req = req + " delete from TRANS_CREANCE_MIS where NUMMIS like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"'; ";
						req = req + " delete from TRANS_BGASOIL_MISSION where NUMMIS like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"'; ";
						req = req + " delete from TRANS_ETAT_MISSION where NUMMIS like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"'; ";
						req = req + " delete from TRANS_MISSION where NUMMIS like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"'; ";
						req = req + " end;";
						req = req.replaceAll("null", "");
						
						CallableStatement cstm = FirstPg.con.prepareCall(req);
						cstm.execute();
						cstm.close();

						JOptionPane.showMessageDialog(null,"Suppression effectuée avec succée","Information",JOptionPane.INFORMATION_MESSAGE);

					}
					stm.close();
					RechTeb("");
				} catch (Exception e) {
					  JOptionPane.showMessageDialog(null,"Veillez selectionné la mission a annuler !"+e,"Attention",JOptionPane.WARNING_MESSAGE);
				
				}
			}
		});
		btnSupprimer.setFont(new Font("Consolas", Font.PLAIN, 12));
		btnSupprimer.setBounds(10, 412, 147, 25);
		add(btnSupprimer);
		
		tfDetail = new JTextField();
		tfDetail.setFont(new Font("Consolas", Font.PLAIN, 12));
		tfDetail.setBounds(127, 225, 629, 22);
		add(tfDetail);
		tfDetail.setColumns(10);
		
		tfNumMission = new JTextField();
		tfNumMission.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(chckbxRecherche.isSelected())
				try {
					RechTeb(" where NUMMIS like '"+tfNumMission.getText()+"' ");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		tfNumMission.setBackground(Color.WHITE);
		tfNumMission.setEditable(false);
		tfNumMission.setFont(new Font("Consolas", Font.PLAIN, 12));
		tfNumMission.setBounds(635, 44, 121, 20);
		add(tfNumMission);
		tfNumMission.setColumns(10);
		
		dt_act.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(chckbxRecherche.isSelected()){
					try {
						RechTeb(" where DATEMIS <= '"+dt.form_Aff(dt_act.getDate())+"' ");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
		
			}
		});

		dt_act.setDate(new Date());
		dt_act.setFont(new Font("Consolas", Font.PLAIN, 11));
		dt_act.setBounds(635, 11, 121, 22);
		add(dt_act);
		
		
		dt_du.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date dt_d = dt_du.getDate();
				Date dt_f = dt_au.getDate();
				
				if(dt_d.getTime()>dt_f.getTime()){
				    JOptionPane.showMessageDialog(null,"La date de début de ne peut etre supérieur a la date de fin de mission","Information",JOptionPane.WARNING_MESSAGE);
				    dt_au.setDate(dt_du.getDate());
				}
				
				try {
					Statement stm = FirstPg.con.createStatement();
					String req = "select DATEAQ ,DATELB from TRANS_VEHICULE_LOCATION where NUMBC like '"+cbBcVeh.getSelectedItem()+"' ";
					ResultSet rst = stm.executeQuery(req);
					boolean ok = false;
					while(rst.next()){
						if(!dt.isInPeriode(rst.getDate(1), rst.getDate(2), dt_au.getDate()) || !dt.isInPeriode(rst.getDate(1), rst.getDate(2), dt_du.getDate())){
							JOptionPane.showMessageDialog(null,"Attention : \nLe Vehicule '"+cbVehicule.getSelectedItem()+"' est louer du '"+rst.getDate(1)+"' au '"+rst.getDate(2)+"' ","Attention",JOptionPane.WARNING_MESSAGE);
								dt_au.setDate(rst.getDate(2));
								dt_du.setDate(DataBase.date_act(FirstPg.con));
							
						}
					}stm.close();
				}catch (Exception e2) {
						// TODO: handle exception
					}
				
				try {
					Statement stm = FirstPg.con.createStatement();
					String req = "select DATEAQ ,DATELB from TRANS_VEHICULE_LOCATION where NUMBC like '"+cbBcRmc.getSelectedItem()+"' ";
					ResultSet rst = stm.executeQuery(req);
					boolean ok = false;
					while(rst.next()){
						if(!dt.isInPeriode(rst.getDate(1), rst.getDate(2), dt_au.getDate()) || !dt.isInPeriode(rst.getDate(1), rst.getDate(2), dt_du.getDate())){
							JOptionPane.showMessageDialog(null,"Attention : \nLa Remorque '"+cbVehicule.getSelectedItem()+"' est louer du '"+rst.getDate(1)+"' au '"+rst.getDate(2)+"' ","Attention",JOptionPane.WARNING_MESSAGE);
								dt_au.setDate(rst.getDate(2));
								dt_du.setDate(DataBase.date_act(FirstPg.con));
							
						}
					}stm.close();
				}catch (Exception e2) {
						// TODO: handle exception
					}
				
				
				if(dt.superieur(dt_du.getDate(), dt_act.getDate())){
					 JOptionPane.showMessageDialog(null,"La date de mission ne peut etre inférieur a la date actuel.","Attention",JOptionPane.WARNING_MESSAGE);
					 dt_du.setDate(dt_act.getDate());
				}
		
			}
		});

		dt_du.setDate(new Date());
		dt_du.setFont(new Font("Consolas", Font.PLAIN, 11));
		dt_du.setBounds(127, 147, 155, 20);
		add(dt_du);
		
		
		dt_au.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Date dt_d = dt_du.getDate();
				Date dt_f = dt_au.getDate();
				
				if(dt_d.getTime()>dt_f.getTime()){
				    JOptionPane.showMessageDialog(null,"La date de début de recherche ne peut etre supérieur a la date de fin de recherche","Information",JOptionPane.INFORMATION_MESSAGE);
				    dt_au.setDate(dt_du.getDate());
				}
				
				try {
					Statement stm = FirstPg.con.createStatement();
					String req = "select DATEAQ ,DATELB from TRANS_VEHICULE_LOCATION where NUMBC like '"+cbBcVeh.getSelectedItem()+"' ";
					ResultSet rst = stm.executeQuery(req);
					boolean ok = false;
					while(rst.next()){
						if(!dt.isInPeriode(rst.getDate(1), rst.getDate(2), dt_au.getDate()) || !dt.isInPeriode(rst.getDate(1), rst.getDate(2), dt_du.getDate())){
							JOptionPane.showMessageDialog(null,"Attention : \nLe Vehicule '"+cbVehicule.getSelectedItem()+"' est louer du '"+rst.getDate(1)+"' au '"+rst.getDate(2)+"' ","Attention",JOptionPane.WARNING_MESSAGE);
						    dt_au.setDate(rst.getDate(2));
						}
					}
					stm.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				try {
					Statement stm = FirstPg.con.createStatement();
					String req = "select DATEAQ ,DATELB from TRANS_VEHICULE_LOCATION where NUMBC like '"+cbBcRmc.getSelectedItem()+"' ";
					ResultSet rst = stm.executeQuery(req);
					boolean ok = false;
					while(rst.next()){
						if(!dt.isInPeriode(rst.getDate(1), rst.getDate(2), dt_au.getDate()) || !dt.isInPeriode(rst.getDate(1), rst.getDate(2), dt_du.getDate())){
							JOptionPane.showMessageDialog(null,"Attention : \nLa Remorque '"+cbVehicule.getSelectedItem()+"' est louer du '"+rst.getDate(1)+"' au '"+rst.getDate(2)+"' ","Attention",JOptionPane.WARNING_MESSAGE);
								dt_au.setDate(rst.getDate(2));
								dt_du.setDate(DataBase.date_act(FirstPg.con));
							
						}
					}stm.close();
				}catch (Exception e2) {
						// TODO: handle exception
					}
				
			}
		});

		dt_au.setDate(new Date());
		dt_au.setFont(new Font("Consolas", Font.PLAIN, 11));
		dt_au.setBounds(418, 146, 155, 20);
		add(dt_au);
		
		
		spPrixHT.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(100)));
		spPrixHT.setFont(new Font("Consolas", Font.PLAIN, 12));
		spPrixHT.setBounds(127, 258, 147, 22);
		add(spPrixHT);
		
		tfDistination = new JTextField();
		tfDistination.setFont(new Font("Consolas", Font.PLAIN, 12));
		tfDistination.setColumns(10);
		tfDistination.setBounds(419, 191, 337, 22);
		add(tfDistination);
		
		JButton btnOrdreDeMission = new JButton("Ordre de mission");
		btnOrdreDeMission.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			try {
				b_liv(tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,"Veuillez Préciser la mission svp !!","Attention",JOptionPane.WARNING_MESSAGE);
			}
				
			}
		});
		btnOrdreDeMission.setFont(new Font("Consolas", Font.PLAIN, 12));
		btnOrdreDeMission.setBounds(10, 384, 147, 25);
		add(btnOrdreDeMission);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Mission", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(163, 291, 593, 172);
		
		tab_fact = new JTable(d_tab,t_tab_b);
		tab_fact.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
			
		    	 Statement stm;
				try {
					stm = FirstPg.con.createStatement();
					String req = "select NUMMIS, CODE_C, DATEMIS, DATEALLEZ, DATERETOUR, DISTINATION, DETAIL, PRIXHT, FACTURATION, PAYE, IDCHAUF, IDACCOMP from TRANS_MISSION where NUMMIS like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"'";
			    	ResultSet rst = stm.executeQuery(req);
			    	while (rst.next()){

			    		cbClient.setSelectedItem(RechClientStr(" code_c like '"+rst.getString(2)+"'"));
			    		dt_act.setDate(rst.getDate(3));
			    		dt_du.setDate(rst.getDate(4));
			    		dt_au.setDate(rst.getDate(5));
			    	    tfDistination.setText(rst.getString(6));
			    	    tfDetail.setText(rst.getString(7));
			    	    spPrixHT.setValue(rst.getDouble(8));
			    	    chCreance.setSelected(!rst.getBoolean(10));
			    	    cbChauffeur.setSelectedItem(RechChaufStr(" ID like '"+rst.getString(11)+"'"));
			    	    cbAccomp.setSelectedItem(RechAccStr(" ID like '"+rst.getString(12)+"'"));
			    	    
			    	}
			    	
			    	req = "select IDVEHICULE, IDREMORQUE, IDVEHICULELOUER, INDX, IDREMORQUELOUER from TRANS_ETAT_MISSION where NUMMIS like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"'";
			    	rst = stm.executeQuery(req);
			    	while(rst.next()){
			    		if(rst.getInt(4)==0){
			    			cbVehicule.setSelectedItem(rst.getString(1));
			    			try {Thread.sleep(100);} catch (InterruptedException e1) {e1.printStackTrace();}
			    			cbRemorque.setSelectedItem(rst.getString(2));
			    		}else if(rst.getInt(4)==1){
			    			cbVehicule.setSelectedItem(rst.getString(3));
			    			try {Thread.sleep(100);} catch (InterruptedException e1) {e1.printStackTrace();}
			    			cbRemorque.setSelectedItem(rst.getString(2));
			    		}else if(rst.getInt(4)==2){
			    			cbVehicule.setSelectedItem(rst.getString(1));
			    			try {Thread.sleep(100);} catch (InterruptedException e1) {e1.printStackTrace();}
			    			cbRemorque.setSelectedItem(rst.getString(5));
			    		}else{
			    			cbVehicule.setSelectedItem(rst.getString(3));
			    			try {Thread.sleep(100);} catch (InterruptedException e1) {e1.printStackTrace();}
			    			cbRemorque.setSelectedItem(rst.getString(5));
			    		}
			    	}
			    	stm.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    
		    	
			}				
		});
		panel.setLayout(new BorderLayout(0, 0));
		
		
		tab_fact.setFont(new Font("Consolas", Font.PLAIN, 12));
		tab_fact.setRowHeight(30);
				
	   JScrollPane scrollPane1 = new JScrollPane(tab_fact);
	   panel.add(scrollPane1);
		
		add(panel);
		chckbxRecherche.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chckbxRecherche.isSelected()){
					tfNumMission.setEditable(true);
					tfNumMission.setText("");
					btnConfirmer.setEnabled(false);
					btnModifier.setEnabled(false);
					btnSupprimer.setEnabled(false);
				}else{
					tfNumMission.setEditable(false);
					tfNumMission.setText(numMission());
					btnConfirmer.setEnabled(true);
					btnModifier.setEnabled(true);
					btnSupprimer.setEnabled(true);
				}
			}
		});
		

		chckbxRecherche.setFont(new Font("Consolas", Font.PLAIN, 12));
		chckbxRecherche.setHorizontalAlignment(SwingConstants.LEFT);
		chckbxRecherche.setBounds(10, 440, 147, 23);
		add(chckbxRecherche);
		cbChauffeur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
				
					if(chckbxRecherche.isSelected()){
						RechTeb(" where IDCHAUF like '"+codification.cd_prs(cbChauffeur.getSelectedItem()+"")+"' ");
					}else{
						Statement stm = FirstPg.con.createStatement();
						String req = "select PCB, PCC1, PCC2, PCD, PCE from TRANS_CHAUFFEUR where ID like '"+codification.cd_prs(cbChauffeur.getSelectedItem()+"")+"'";
					
					ResultSet rst = stm.executeQuery(req);
					cbVehicule.removeAllItems();

					while(rst.next()){
	
						if(rst.getBoolean(1)){
							RechVehic("where TYPE like 'Leger'");
						}
						
						if(rst.getBoolean(2)){
							RechVehic("where TYPE like 'Camion'");				
						}
						
						if(rst.getBoolean(3)){
							RechVehic("where TYPE like 'Tete semi-remorque'");	
						}
						
						if(rst.getBoolean(4)){
							RechVehic("where TYPE like 'Leger' or TYPE like 'Transport'");
						}
						
						if(rst.getBoolean(5)){
							RechVehic("where TYPE like 'Leger'");
						}
					}
					try {
						if(cbChauffeur.getSelectedItem().equals("")) RechVehicAll();
					} catch (Exception e) {
						// TODO: handle exception
					}
						
						cbVehicule.addItem("");
					}
					
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		

		cbChauffeur.setFont(new Font("Consolas", Font.PLAIN, 12));
		cbChauffeur.setBounds(127, 11, 282, 22);
		add(cbChauffeur);
		cbAccomp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chckbxRecherche.isSelected())
					try {
						RechTeb(" where IDACCOMP like '"+codification.cd_prs(cbAccomp.getSelectedItem()+"")+"' ");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

			}
		});
		
		cbAccomp.setFont(new Font("Consolas", Font.PLAIN, 12));
		cbAccomp.setBounds(127, 44, 282, 22);
		add(cbAccomp);
		cbVehicule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Statement stm;
				try {
					if(chckbxRecherche.isSelected()){
						RechTeb(" where NUMMIS like (select NUMMIS from TRANS_ETAT_MISSION where IDVEHICULE like '"+cbVehicule.getSelectedItem()+"') ");
					}else{
						stm = FirstPg.con.createStatement();
						String req = "select TYPE from TRANS_VEHICULE where MATRICULE like '"+cbVehicule.getSelectedItem()+"' ";
						ResultSet rst = stm.executeQuery(req);
						String bl = "";
						while (rst.next()){
						  bl = rst.getString(1);
						}
						
						if(bl.equals("")){
							req = "select TYPE, NUMBC from TRANS_VEHICULE_LOCATION where MATRICULE like '"+cbVehicule.getSelectedItem()+"' order by DATES Desc";
							rst = stm.executeQuery(req);
							 bl = "";
							 cbBcVeh.removeAllItems();
							while (rst.next()){
							  bl = rst.getString(1);
							  cbBcVeh.addItem(rst.getString(2));
							}
						}
						
						
						if(bl.equals("Tete semi-remorque")){
							cbRemorque.removeAllItems();
							RechRmc("where Type like 'Benne' or Type like 'Siterne' or Type like 'Porte char' ");
						}else{
							cbRemorque.removeAllItems();
							RechRmc("where Type like 'Autres' ");
						}
						
						
						req = "select NUMBC from TRANS_VEHICULE_LOCATION where MATRICULE like '"+cbVehicule.getSelectedItem()+"' order by DATES Desc";
						rst = stm.executeQuery(req);
						 cbBcVeh.removeAllItems();
						while (rst.next()){
						  cbBcVeh.addItem(rst.getString(1));
						}
						stm.close();
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				
			}
		});
		
		
		cbVehicule.setFont(new Font("Consolas", Font.PLAIN, 12));
		cbVehicule.setBounds(127, 77, 190, 22);
		add(cbVehicule);
		cbRemorque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Statement stm;
				try {
					stm = FirstPg.con.createStatement();
					String req = "select NUMBC from TRANS_VEHICULE_LOCATION where MATRICULE like '"+cbRemorque.getSelectedItem()+"' order by DATES Desc";
					ResultSet rst = stm.executeQuery(req);
					 cbBcRmc.removeAllItems();
					while (rst.next()){
						cbBcRmc.addItem(rst.getString(1));
					}
					cbBcRmc.addItem("");
					stm.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		
		cbRemorque.setFont(new Font("Consolas", Font.PLAIN, 12));
		cbRemorque.setBounds(419, 77, 190, 22);
		add(cbRemorque);
		cbClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chckbxRecherche.isSelected()){
					try {
						RechTeb(" where CODE_C like '"+codification.cd_prs(cbClient.getSelectedItem()+"")+"' ");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					try {
						RechAdresseClt(fct.codification.cd_prs(""+cbClient.getSelectedItem()));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				 
				
			}
		});
		
		
		cbClient.setFont(new Font("Consolas", Font.PLAIN, 12));
		cbClient.setBounds(127, 190, 145, 22);
		add(cbClient);
		
		JButton btnActualiser = new JButton("Actualiser");
		btnActualiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					tfDetail.setText("");
					
					RechChauf("");
					RechAcc("");
					RechClient("");
					tfNumMission.setText(numMission());
					chckbxRecherche.setSelected(false);
					chCreance.setSelected(false);
					RechTeb("");
					spPrixHT.setValue(0);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		btnActualiser.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnActualiser.setBounds(635, 258, 121, 25);
		add(btnActualiser);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.RED));
		panel_1.setBounds(10, 105, 569, 32);
		add(panel_1);
		panel_1.setLayout(null);
		cbBcRmc.setBounds(408, 5, 152, 22);
		panel_1.add(cbBcRmc);
		
		JLabel lblSuiteBcN = new JLabel("Suite au BC N\u00B0");
		lblSuiteBcN.setBounds(2, 5, 107, 22);
		panel_1.add(lblSuiteBcN);
		lblSuiteBcN.setForeground(Color.RED);
		lblSuiteBcN.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSuiteBcN.setFont(new Font("Consolas", Font.PLAIN, 12));
		cbBcVeh.setBounds(119, 5, 137, 22);
		panel_1.add(cbBcVeh);
		
		JButton btnBonDeTransport = new JButton("Bon de transport");
		btnBonDeTransport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					b_liv_t(tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,"Veuillez Préciser la mission svp !!","Attention",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnBonDeTransport.setFont(new Font("Consolas", Font.PLAIN, 12));
		btnBonDeTransport.setBounds(10, 357, 147, 25);
		add(btnBonDeTransport);
		
		JButton btnMissionPourAujourdhui = new JButton("Mission pour aujourd'hui");
		btnMissionPourAujourdhui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
					try {
						RechTeb(" where DATEALLEZ = '"+dt.form_Aff(DataBase.date_act(FirstPg.con))+"' ");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
			}
		});
		btnMissionPourAujourdhui.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnMissionPourAujourdhui.setBounds(440, 258, 185, 25);
		add(btnMissionPourAujourdhui);
		cbBcVeh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					Statement stm = FirstPg.con.createStatement();
					String req = "select DATEAQ ,DATELB from TRANS_VEHICULE_LOCATION where NUMBC like '"+cbBcVeh.getSelectedItem()+"' ";
					ResultSet rst = stm.executeQuery(req);
					boolean ok = false;
					while(rst.next()){
						if(!dt.isInPeriode(rst.getDate(1), rst.getDate(2), dt_au.getDate()) || !dt.isInPeriode(rst.getDate(1), rst.getDate(2), dt_du.getDate())){
							JOptionPane.showMessageDialog(null,"Attention : \nLe Vehicule '"+cbVehicule.getSelectedItem()+"' est louer du '"+rst.getDate(1)+"' au '"+rst.getDate(2)+"' ","Attention",JOptionPane.WARNING_MESSAGE);
								dt_au.setDate(rst.getDate(2));
								dt_du.setDate(DataBase.date_act(FirstPg.con));
							
						}
					}
				}catch (Exception e2) {
						// TODO: handle exception
					}
			}
		});
		
		cbBcRmc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					Statement stm = FirstPg.con.createStatement();
					String req = "select DATEAQ ,DATELB from TRANS_VEHICULE_LOCATION where NUMBC like '"+cbBcRmc.getSelectedItem()+"' ";
					ResultSet rst = stm.executeQuery(req);
					boolean ok = false;
					while(rst.next()){
						if(!dt.isInPeriode(rst.getDate(1), rst.getDate(2), dt_au.getDate()) || !dt.isInPeriode(rst.getDate(1), rst.getDate(2), dt_du.getDate())){
							JOptionPane.showMessageDialog(null,"Attention : \nLa Remorque '"+cbVehicule.getSelectedItem()+"' est louer du '"+rst.getDate(1)+"' au '"+rst.getDate(2)+"' ","Attention",JOptionPane.WARNING_MESSAGE);
								dt_au.setDate(rst.getDate(2));
								dt_du.setDate(DataBase.date_act(FirstPg.con));
							
						}
					}
				}catch (Exception e2) {}
			}
		});
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					RechChauf("");
					RechAcc("");
					RechClient("");
					tfNumMission.setText(numMission());
					RechTeb("");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
	
			}
		}).start();

	}
	
	
	public String numMission(){
		String str = "";

		try {
			Statement stm = FirstPg.con.createStatement();
			String req = "select NUMMIS from TRANS_MISSION"; 
			
			ResultSet rst = stm.executeQuery(req);
			Vector vct = new Vector();
			while(rst.next()){
				vct.addElement(rst.getString(1));
			}
			
			str = numRote.BonNum(vct);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	void RechChauf(String condition) throws SQLException{
		Statement stm = FirstPg.con.createStatement();
		if (!condition.equals("")) condition = " and "+condition;
		String req = "select NOM from TRANS_CHAUFFEUR  where VISIBLE = 1  "+condition+" order by NOM";
		ResultSet rst = stm.executeQuery(req);
		cbChauffeur.removeAllItems();
		while (rst.next()){
			cbChauffeur.addItem(rst.getString(1));
		}
		cbChauffeur.addItem("");
		stm.close();
	}
	
	public static String RechChaufStr(String condition) throws SQLException{
		String str = "";
		Statement stm = FirstPg.con.createStatement();
		if (!condition.equals("")) condition = " and "+condition;
		String req = "select NOM from TRANS_CHAUFFEUR  where visible = 1 "+condition+" ";
		ResultSet rst = stm.executeQuery(req);

		while (rst.next()){
			str = rst.getString(1);
		}
		stm.close();
		return str;
	}
	
    void RechAcc(String condition) throws SQLException{
		Statement stm = FirstPg.con.createStatement();
		if (!condition.equals("")) condition = " and "+condition;
		String req = "select NOM from TRANS_ACCOMP  where visible = 1 "+condition+" order by NOM";
		ResultSet rst = stm.executeQuery(req);
		cbAccomp.removeAllItems();
		while (rst.next()){
			cbAccomp.addItem(rst.getString(1));
		}
		cbAccomp.addItem("");
		stm.close();
	}
    
    public String RechAccStr(String condition) throws SQLException{
    	String str = "";
		Statement stm = FirstPg.con.createStatement();
		if (!condition.equals("")) condition = " and "+condition;
		String req = "select NOM from TRANS_ACCOMP  where visible = 1  "+condition+" ";
		ResultSet rst = stm.executeQuery(req);
		while (rst.next()){
			str = rst.getString(1);
		}
		stm.close();
		return str;
	}
   
    void RechVehic(String condition)throws SQLException{
		Statement stm = FirstPg.con.createStatement();
		if (!condition.equals("")) condition = " and "+condition;
		String req = "select MATRICULE from TRANS_VEHICULE  where visible = 1  "+condition.replaceAll("where", "")+" order by MATRICULE";
		ResultSet rst = stm.executeQuery(req);
		boolean bl = false;
		while (rst.next()){
			//cbVehicule.addItem(rst.getString(1));
			 bl = false;
				for(int i = 0; i < cbVehicule.getItemCount(); i++){
					if(rst.getString(1).equals(cbVehicule.getItemAt(i))){
						bl = true;
						break;
					}
				}
				if(!bl)cbVehicule.addItem(rst.getString(1));
		}
		
		 req = "select MATRICULE,  DATEAQ, DATELB from TRANS_VEHICULE_LOCATION "+condition.replaceAll("and", "")+" order by MATRICULE";
		 rst = stm.executeQuery(req);
		while (rst.next()){
								 bl = false;
								for(int i = 0; i < cbVehicule.getItemCount(); i++){
									if(rst.getString(1).equals(cbVehicule.getItemAt(i))){
										bl = true;
										break;
									}
								}
								if(dt.isInPeriode(rst.getDate(2), rst.getDate(3), dt_act.getDate()))if(!bl)cbVehicule.addItem(rst.getString(1));
							}
		
		boolean testVide = false;
		
		
		
		
		stm.close();
	}
    
    void RechVehicAll()throws SQLException{
		Statement stm = FirstPg.con.createStatement();

		String req = "select MATRICULE from TRANS_VEHICULE  where visible = 1 order by MATRICULE";
		ResultSet rst = stm.executeQuery(req);
		boolean bl = false;
		while (rst.next()){
			//cbVehicule.addItem(rst.getString(1));
			 bl = false;
				for(int i = 0; i < cbVehicule.getItemCount(); i++){
					if(rst.getString(1).equals(cbVehicule.getItemAt(i))){
						bl = true;
						break;
					}
				}
				if(!bl)cbVehicule.addItem(rst.getString(1));
		}
		
		 req = "select MATRICULE,  DATEAQ, DATELB from TRANS_VEHICULE_LOCATION order by MATRICULE";
		 rst = stm.executeQuery(req);
		while (rst.next()){
								 bl = false;
								for(int i = 0; i < cbVehicule.getItemCount(); i++){
									if(rst.getString(1).equals(cbVehicule.getItemAt(i))){
										bl = true;
										break;
									}
								}
								if(dt.isInPeriode(rst.getDate(2), rst.getDate(3), dt_act.getDate()))if(!bl)cbVehicule.addItem(rst.getString(1));
							}
		stm.close();
	}
    
    void RechRmc(String condition)throws SQLException{
		Statement stm = FirstPg.con.createStatement();
		if (!condition.equals("")) condition = " and "+condition;
		String req = "select MATRICULE from TRANS_REMORQUE  where visible = 1 "+condition.replaceAll("where", "")+" order by MATRICULE";
		ResultSet rst = stm.executeQuery(req);
		boolean bl = false;
		cbRemorque.removeAllItems();
		while (rst.next()){
			 bl = false;
				for(int i = 0; i < cbRemorque.getItemCount(); i++){
					if(rst.getString(1).equals(cbRemorque.getItemAt(i))){
						bl = true;
						break;
					}
				}
				if(!bl)cbRemorque.addItem(rst.getString(1));
		}
		
		 req = "select MATRICULE, DATEAQ, DATELB from  TRANS_VEHICULE_LOCATION "+condition.replaceAll("and", "")+" order by MATRICULE";
		 rst = stm.executeQuery(req);
		while (rst.next()){
			 bl = false;
			for(int i = 0; i < cbRemorque.getItemCount(); i++){
				if(rst.getString(1).equals(cbRemorque.getItemAt(i))){
					bl = true;
					break;
				}
			}
			if(dt.isInPeriode(rst.getDate(2), rst.getDate(3), dt_act.getDate()))if(!bl)cbRemorque.addItem(rst.getString(1));
		}
		cbRemorque.addItem("");
		stm.close();
	}
    
    void RechClient(String condition)throws SQLException{
		Statement stm = FirstPg.con.createStatement();
		if (!condition.equals("")) condition = " and "+condition;
		String req = "select NOM from TRANS_CLIENT where visible = 1 "+condition+" order by NOM";
		ResultSet rst = stm.executeQuery(req);
		cbClient.removeAllItems();
		while (rst.next()){
			cbClient.addItem(rst.getString(1));
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
    
    void RechAdresseClt(String cClient)throws SQLException{
		Statement stm = FirstPg.con.createStatement();
		String req = "select Adresse from TRANS_CLIENT where CODE_C like '"+cClient+"' ";
		ResultSet rst = stm.executeQuery(req);
		tfDistination.setText("");

		while (rst.next()){
			tfDistination.setText(rst.getString(1));
		}
		stm.close();

    }
    
    void RechTeb(String condition) throws SQLException{
    	def_t = new DefaultTableModel();
    	 def_t.setColumnIdentifiers(t_tab_b);
    	 Statement stm = FirstPg.con.createStatement();
    	String req = "select NUMMIS, CODE_C, IDCHAUF, DATEMIS, PRIXHT, PAYE from TRANS_MISSION "+condition+" order by DATEMIS DESC";
    	ResultSet rst = stm.executeQuery(req);
    	while (rst.next()){
    		String p = "";
    		if(rst.getBoolean(6))p = "Payé";else p = "Non Payé";
        	 def_t.addRow(new Object[]{rst.getString(1),RechClientStr(" code_c like '"+rst.getString(2)+"' "),RechChaufStr(" ID like '"+rst.getString(3)+"' "),dt.form_Aff(rst.getDate(4)),fct.nbr.dbToDf(rst.getDouble(5)),p});					  
    	}
    	
    	 tab_fact.setModel(def_t);
	        for(int i = 0; i< tab_fact.getRowCount();i++){
	        	 table.changeSizeh(25,tab_fact,i);
	        }
    }
    
    Vector RechNumMisVR(String IDVEHICULE, String nmmis,int s)throws SQLException{
    	Vector numMis = new Vector();
 		Statement stm = FirstPg.con.createStatement();
 		String req = "";
 		
 		if(!nmmis.equals("")){
 			nmmis = "NUMMIS NOT LIKE '"+nmmis+"' and ";
 		}
 		
 		if(s==0){
 	 		 req = "select NUMMIS from TRANS_ETAT_MISSION where "+nmmis+" IDVEHICULE like '"+IDVEHICULE+"' OR IDVEHICULELOUER like '"+IDVEHICULE+"' ";
 		}else{
 	 		 req = "select NUMMIS from TRANS_ETAT_MISSION where "+nmmis+" IDREMORQUE like '"+IDVEHICULE+"' OR IDREMORQUELOUER like '"+IDVEHICULE+"' ";
 		}
 		ResultSet rst = stm.executeQuery(req);

 		while (rst.next()){
 			numMis.addElement(rst.getString(1));
 		}
 		stm.close();
 		return numMis;
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
			
			
		req = "select NOM, NRC, NIF, NAI, ADRESSE, TEL from TRANS_CLIENT where CODE_C like '"+fct.codification.cd_prs(tab_fact.getValueAt(tab_fact.getSelectedRow(), 1).toString())+"' ";
			
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
				parameters.put("cd_client", fct.codification.cd_prs(tab_fact.getValueAt(tab_fact.getSelectedRow(), 1).toString()));
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
				
			
	        req = "select DISTINATION, DATEMIS, DATEALLEZ, DATERETOUR, IDCHAUF, IDACCOMP, DETAIL from TRANS_MISSION where NUMMIS like '"+n_bon+"' ";			
			rst = stm.executeQuery(req);
			
			while(rst.next()){
				parameters.put("destination", rst.getString(1));
				
				parameters.put("dts", fct.dt.form_Aff(rst.getDate(2)));
				parameters.put("DtDu", fct.dt.form_Aff(rst.getDate(3)));
				parameters.put("DtAu", fct.dt.form_Aff(rst.getDate(4)));
				
				parameters.put("chauffeur", RechChaufStr(" ID like '"+rst.getString(5)+"' "));
				parameters.put("accomp", RechAccStr(" ID like '"+rst.getString(6)+"' "));
				parameters.put("detail", rst.getString(7));
			}
			
			
			 req = "select DISTINATION, DATEMIS, DATEALLEZ, DATERETOUR, IDCHAUF, IDACCOMP, DETAIL from TRANS_MISSION where NUMMIS like '"+n_bon+"' ";			
				rst = stm.executeQuery(req);
				
				while(rst.next()){
					parameters.put("destination", rst.getString(1));
					
					parameters.put("dts", fct.dt.form_Aff(rst.getDate(2)));
					parameters.put("DtDu", fct.dt.form_Aff(rst.getDate(3)));
					parameters.put("DtAu", fct.dt.form_Aff(rst.getDate(4)));
					
					parameters.put("chauffeur", RechChaufStr(" ID like '"+rst.getString(5)+"' "));
					parameters.put("accomp", RechAccStr(" ID like '"+rst.getString(6)+"' "));
					parameters.put("detail", rst.getString(7));
				}
				
				req = "select INDX from TRANS_ETAT_MISSION where NUMMIS like '"+n_bon+"' ";
				rst = stm.executeQuery(req);
				int x = 0;
				while (rst.next()) x = rst.getInt(1);
				
		    //--------------		
				if(x == 0){
					req = "select MATRICULE, TYPE, MARQUE from TRANS_VEHICULE where MATRICULE like (select distinct IDVEHICULE from TRANS_ETAT_MISSION where NUMMIS like '"+n_bon+"')";
					rst = stm.executeQuery(req);
					while (rst.next()){
						parameters.put("vehicule", rst.getString(3)+" Type "+rst.getString(2)+" Immatriculation : "+rst.getString(1)+".");
					}
					
					 req = "select MATRICULE, TYPE, MARQUE, CAPACITE, UNITQTE from TRANS_REMORQUE where MATRICULE like (select distinct IDREMORQUE from TRANS_ETAT_MISSION where NUMMIS like '"+n_bon+"')";
					 rst = stm.executeQuery(req);
						while (rst.next()){
							parameters.put("remorque", rst.getString(3)+" Type "+rst.getString(2)+" Immatriculation : "+rst.getString(1)+", Capacité : "+rst.getString(4)+" "+rst.getString(5)+".");
						}
			//--------------		
				}else if(x == 1){
					req = "select MATRICULE, TYPE, MARQUE from TRANS_VEHICULE_LOCATION where MATRICULE like (select distinct IDVEHICULELOUER from TRANS_ETAT_MISSION where NUMMIS like '"+n_bon+"')";
					rst = stm.executeQuery(req);
					while (rst.next()){
						parameters.put("vehicule", rst.getString(3)+" Type "+rst.getString(2)+" Immatriculation : "+rst.getString(1)+".");
					}
					
					 req = "select MATRICULE, TYPE, MARQUE, CAPACITE, UNITQTE from TRANS_REMORQUE where MATRICULE like (select distinct IDREMORQUE from TRANS_ETAT_MISSION where NUMMIS like '"+n_bon+"')";
					 rst = stm.executeQuery(req);
						while (rst.next()){
							parameters.put("remorque", rst.getString(3)+" Type "+rst.getString(2)+" Immatriculation : "+rst.getString(1)+", Capacité : "+rst.getString(4)+" "+rst.getString(5)+".");
						}
			 //--------------		
				}else if(x == 2){
					 req = "select MATRICULE, TYPE, MARQUE, CAPACITE, UNITQTE from TRANS_VEHICULE_LOCATION where MATRICULE like (select distinct IDREMORQUELOUER from TRANS_ETAT_MISSION where NUMMIS like '"+n_bon+"')";
					 rst = stm.executeQuery(req);
						while (rst.next()){
							parameters.put("remorque", rst.getString(3)+" Type "+rst.getString(2)+" Immatriculation : "+rst.getString(1)+", Capacité : "+rst.getString(4)+" "+rst.getString(5)+".");
						}
						
					req = "select MATRICULE, TYPE, MARQUE from TRANS_VEHICULE where MATRICULE like (select distinct IDVEHICULE from TRANS_ETAT_MISSION where NUMMIS like '"+n_bon+"')";
					rst = stm.executeQuery(req);
					while (rst.next()){
						parameters.put("vehicule", rst.getString(3)+" Type "+rst.getString(2)+" Immatriculation : "+rst.getString(1)+".");
					}
			//--------------		
				}else{
					 req = "select MATRICULE, TYPE, MARQUE, CAPACITE, UNITQTE from TRANS_VEHICULE_LOCATION where MATRICULE like (select distinct IDREMORQUELOUER from TRANS_ETAT_MISSION where NUMMIS like '"+n_bon+"')";
					 rst = stm.executeQuery(req);
						while (rst.next()){
							parameters.put("remorque", rst.getString(3)+" Type "+rst.getString(2)+" Immatriculation : "+rst.getString(1)+", Capacité : "+rst.getString(4)+" "+rst.getString(5)+".");
						}
					 
					 req = "select MATRICULE, TYPE, MARQUE from TRANS_VEHICULE_LOCATION where MATRICULE like (select distinct IDVEHICULELOUER from TRANS_ETAT_MISSION where NUMMIS like '"+n_bon+"')";
					 rst = stm.executeQuery(req);
						while (rst.next()){
							parameters.put("vehicule", rst.getString(3)+" Type "+rst.getString(2)+" Immatriculation : "+rst.getString(1)+".");
						}
				}
				
			
		  		JasperPrint jasperPrint;
				
						jasperPrint = JasperFillManager.fillReport("C:/JTrans/etat/Mission.jasper", parameters,FirstPg.con);
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

    void b_liv_t(String n_bon){
		
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
			
			
		req = "select NOM, NRC, NIF, NAI, ADRESSE, TEL from TRANS_CLIENT where CODE_C like '"+fct.codification.cd_prs(tab_fact.getValueAt(tab_fact.getSelectedRow(), 1).toString())+"' ";
			
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
				parameters.put("cd_client", fct.codification.cd_prs(tab_fact.getValueAt(tab_fact.getSelectedRow(), 1).toString()));
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
				
			
	        req = "select DISTINATION, DATEMIS, DATEALLEZ, DATERETOUR, IDCHAUF, IDACCOMP, DETAIL from TRANS_MISSION where NUMMIS like '"+n_bon+"' ";			
			rst = stm.executeQuery(req);
			
			while(rst.next()){
				parameters.put("destination", rst.getString(1));
				
				parameters.put("dts", fct.dt.form_Aff(rst.getDate(2)));
				parameters.put("DtDu", fct.dt.form_Aff(rst.getDate(3)));
				parameters.put("DtAu", fct.dt.form_Aff(rst.getDate(4)));
				
				parameters.put("chauffeur", RechChaufStr(" ID like '"+rst.getString(5)+"' "));
				parameters.put("accomp", RechAccStr(" ID like '"+rst.getString(6)+"' "));
				parameters.put("detail", rst.getString(7));
			}
			
			
			 req = "select DISTINATION, DATEMIS, DATEALLEZ, DATERETOUR, IDCHAUF, IDACCOMP, DETAIL, PRIXHT from TRANS_MISSION where NUMMIS like '"+n_bon+"' ";			
				rst = stm.executeQuery(req);
				
				while(rst.next()){
					parameters.put("destination", rst.getString(1));
					
					parameters.put("dts", fct.dt.form_Aff(rst.getDate(2)));
					parameters.put("DtDu", fct.dt.form_Aff(rst.getDate(3)));
					parameters.put("DtAu", fct.dt.form_Aff(rst.getDate(4)));
					
					parameters.put("chauffeur", RechChaufStr(" ID like '"+rst.getString(5)+"' "));
					parameters.put("accomp", RechAccStr(" ID like '"+rst.getString(6)+"' "));
					parameters.put("detail", rst.getString(7));
					parameters.put("totHt", fct.nbr.dbToDf(rst.getDouble(8))+" Da.");
					parameters.put("En lettre", fct.nbr.EnLettre(rst.getDouble(8), "Dinar algérien") );
					
				}
				
				req = "select INDX from TRANS_ETAT_MISSION where NUMMIS like '"+n_bon+"' ";
				rst = stm.executeQuery(req);
				int x = 0;
				while (rst.next()) x = rst.getInt(1);
				
		    //--------------		
				if(x == 0){
					req = "select MATRICULE, TYPE, MARQUE from TRANS_VEHICULE where MATRICULE like (select distinct IDVEHICULE from TRANS_ETAT_MISSION where NUMMIS like '"+n_bon+"')";
					rst = stm.executeQuery(req);
					while (rst.next()){
						parameters.put("vehicule", rst.getString(3)+" Type "+rst.getString(2)+" Immatriculation : "+rst.getString(1)+".");
					}
					
					 req = "select MATRICULE, TYPE, MARQUE, CAPACITE, UNITQTE from TRANS_REMORQUE where MATRICULE like (select distinct IDREMORQUE from TRANS_ETAT_MISSION where NUMMIS like '"+n_bon+"')";
					 rst = stm.executeQuery(req);
						while (rst.next()){
							parameters.put("remorque", rst.getString(3)+" Type "+rst.getString(2)+" Immatriculation : "+rst.getString(1)+", Capacité : "+rst.getString(4)+" "+rst.getString(5)+".");
						}
			//--------------		
				}else if(x == 1){
					req = "select MATRICULE, TYPE, MARQUE from TRANS_VEHICULE_LOCATION where MATRICULE like (select distinct IDVEHICULELOUER from TRANS_ETAT_MISSION where NUMMIS like '"+n_bon+"')";
					rst = stm.executeQuery(req);
					while (rst.next()){
						parameters.put("vehicule", rst.getString(3)+" Type "+rst.getString(2)+" Immatriculation : "+rst.getString(1)+".");
					}
					
					 req = "select MATRICULE, TYPE, MARQUE, CAPACITE, UNITQTE from TRANS_REMORQUE where MATRICULE like (select distinct IDREMORQUE from TRANS_ETAT_MISSION where NUMMIS like '"+n_bon+"')";
					 rst = stm.executeQuery(req);
						while (rst.next()){
							parameters.put("remorque", rst.getString(3)+" Type "+rst.getString(2)+" Immatriculation : "+rst.getString(1)+", Capacité : "+rst.getString(4)+" "+rst.getString(5)+".");
						}
			 //--------------		
				}else if(x == 2){
					 req = "select MATRICULE, TYPE, MARQUE, CAPACITE, UNITQTE from TRANS_VEHICULE_LOCATION where MATRICULE like (select distinct IDREMORQUELOUER from TRANS_ETAT_MISSION where NUMMIS like '"+n_bon+"')";
					 rst = stm.executeQuery(req);
						while (rst.next()){
							parameters.put("remorque", rst.getString(3)+" Type "+rst.getString(2)+" Immatriculation : "+rst.getString(1)+", Capacité : "+rst.getString(4)+" "+rst.getString(5)+".");
						}
						
					req = "select MATRICULE, TYPE, MARQUE from TRANS_VEHICULE where MATRICULE like (select distinct IDVEHICULE from TRANS_ETAT_MISSION where NUMMIS like '"+n_bon+"')";
					rst = stm.executeQuery(req);
					while (rst.next()){
						parameters.put("vehicule", rst.getString(3)+" Type "+rst.getString(2)+" Immatriculation : "+rst.getString(1)+".");
					}
			//--------------		
				}else{
					 req = "select MATRICULE, TYPE, MARQUE, CAPACITE, UNITQTE from TRANS_VEHICULE_LOCATION where MATRICULE like (select distinct IDREMORQUELOUER from TRANS_ETAT_MISSION where NUMMIS like '"+n_bon+"')";
					 rst = stm.executeQuery(req);
						while (rst.next()){
							parameters.put("remorque", rst.getString(3)+" Type "+rst.getString(2)+" Immatriculation : "+rst.getString(1)+", Capacité : "+rst.getString(4)+" "+rst.getString(5)+".");
						}
					 
					 req = "select MATRICULE, TYPE, MARQUE from TRANS_VEHICULE_LOCATION where MATRICULE like (select distinct IDVEHICULELOUER from TRANS_ETAT_MISSION where NUMMIS like '"+n_bon+"')";
					 rst = stm.executeQuery(req);
						while (rst.next()){
							parameters.put("vehicule", rst.getString(3)+" Type "+rst.getString(2)+" Immatriculation : "+rst.getString(1)+".");
						}
				}
				
			
		  		JasperPrint jasperPrint;
				
						jasperPrint = JasperFillManager.fillReport("C:/JTrans/etat/bt.jasper", parameters,FirstPg.con);
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
	    
    public Vector DateDP(String condition){
	 Vector dtr = new Vector(); 
	 Statement stm;
	try {
		stm = FirstPg.con.createStatement(); 
	
		String req = "select DATEALLEZ from TRANS_MISSION where "+condition;
		System.out.println(req);
		ResultSet rst = stm.executeQuery(req);
	
	    while(rst.next()){
			dtr.addElement(rst.getDate(1));
		}
		stm.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}	
	
	 return dtr;
}

    public Vector DateAR(String condition){
	 Vector dtr = new Vector(); 
	 Statement stm;
	try {
		stm = FirstPg.con.createStatement(); 
	
		String req = "select distinct DATERETOUR from TRANS_MISSION where "+condition;			
		ResultSet rst = stm.executeQuery(req);
	    while(rst.next()){
	    	dtr.addElement(rst.getDate(1));
		}
		stm.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}	
	
	 return dtr;
}

    String ErrLineMission(int x) throws SQLException{
	//------test chauffeur-----------
	String condition = "";
	if(x == 1){
			condition = " and NUMMIS not like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"' ";
	} 
	
	
	Vector DDpA = DateDP("IDCHAUF like '"+codification.cd_prs(cbChauffeur.getSelectedItem()+"")+"'"+condition);
	Vector DArA = DateAR("IDCHAUF like '"+codification.cd_prs(cbChauffeur.getSelectedItem()+"")+"'"+condition);

	String erreurLine = "";
	
	for(int i = 0; i < DDpA.size(); i++){
		if(dt.dateIntoPeriode((Date)DDpA.elementAt(i), (Date)DArA.elementAt(i), dt_du.getDate(), dt_au.getDate())){
			erreurLine = "Opération impossible chauffeur en mission du:"+dt.form_Aff((Date)DDpA.elementAt(i))+" au "+dt.form_Aff((Date)DArA.elementAt(i))+"\n";
		}
		break;
	}
	
	//------test Accompagnateur-----------
	 DDpA = DateDP("IDACCOMP like '"+codification.cd_prs(cbAccomp.getSelectedItem()+"")+"'"+condition);
	 DArA = DateAR("IDACCOMP like '"+codification.cd_prs(cbAccomp.getSelectedItem()+"")+"'"+condition);
	
	for(int i = 0; i < DDpA.size(); i++){
		if(dt.dateIntoPeriode((Date)DDpA.elementAt(i), (Date)DArA.elementAt(i), dt_du.getDate(), dt_au.getDate())){
			erreurLine = erreurLine+"Opération impossible Accompagnateur en mission du:"+dt.form_Aff((Date)DDpA.elementAt(i))+" au "+dt.form_Aff((Date)DArA.elementAt(i))+"\n";
		}
		break;
	}
	
	//------test Vehicule-----------
	String nmr = "";
	if(x==1){
		nmr = tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"";
	}
	Vector vct = RechNumMisVR(cbVehicule.getSelectedItem()+"",nmr,0);
	
	for(int i = 0; i < vct.size(); i++){
		 DDpA = DateDP("NUMMIS like '"+vct.elementAt(i)+"'");
		 DArA = DateAR("NUMMIS like '"+vct.elementAt(i)+"'");
									
		for(int xs = 0; xs < DDpA.size(); xs++){
			if(dt.dateIntoPeriode((Date)DDpA.elementAt(xs), (Date)DArA.elementAt(xs), dt_du.getDate(), dt_au.getDate())){
				erreurLine = erreurLine+"Opération impossible Véhicule en mission du:"+dt.form_Aff((Date)DDpA.elementAt(xs))+" au "+dt.form_Aff((Date)DArA.elementAt(xs))+"\n";
				break;
			}
		}
	}
	
	
	
	//------test Remorque-----------

    vct = RechNumMisVR(cbVehicule.getSelectedItem()+"",nmr,1);
	for(int i = 0; i < vct.size(); i++){
		 DDpA = DateDP("NUMMIS like '"+vct.elementAt(i)+"'");
		 DArA = DateAR("NUMMIS like '"+vct.elementAt(i)+"'");
									
		for(int xs = 0; xs < DDpA.size(); xs++){
			if(dt.dateIntoPeriode((Date)DDpA.elementAt(xs), (Date)DArA.elementAt(xs), dt_du.getDate(), dt_au.getDate())){
				erreurLine = erreurLine+"Opération impossible Remorque en mission du:"+dt.form_Aff((Date)DDpA.elementAt(xs))+" au "+dt.form_Aff((Date)DArA.elementAt(xs))+"\n";
				break;
			}
		}
	}
	
	
	return erreurLine;
}
}
