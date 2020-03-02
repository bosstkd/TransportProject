package interfaces;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.view.JasperViewer;

import org.jdesktop.swingx.JXDatePicker;

import fct.codification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SpinnerNumberModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JCheckBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.BorderLayout;

public class BonCommande extends JInternalFrame {

	  private JXDatePicker dt_act =  new JXDatePicker();
	  private JXDatePicker dt_du =  new JXDatePicker();
	  private JXDatePicker dt_au =  new JXDatePicker();
	  private JTextField tfBC;
	  private JTextField tfMarque;
	  
	  JSpinner spPrix = new JSpinner();

   	  JCheckBox chRech = new JCheckBox("Mode Recherche");
   	  JCheckBox chDette = new JCheckBox("Dette");
	  
		JComboBox cbFournisseur = new JComboBox();			
		JComboBox cbMatricule = new JComboBox();
		JComboBox cbTypeVehicule = new JComboBox();
		JComboBox cbPolytique = new JComboBox();

		JButton btnConfirmer = new JButton("Confirmer");
		JButton btnModifier = new JButton("Modifier");
		JButton btnSupprimer = new JButton("Supprimer");
		
		JTable tab_fact;
		DefaultTableModel def_t;
		Object [][] d_tab= new Object[20][7];
	    String[] t_tab_b = { "N°BC","Fournisseur","Date","Montant"};
	    private JTextField tfComment;
		
		
	public BonCommande() {
		setFrameIcon(new ImageIcon(BonCommande.class.getResource("/icones/original/A32/fournisseur_RS.jpg")));
		setTitle("Location V\u00E9hicule");
		setClosable(true);
		setIconifiable(true);
		setBounds(100, 100, 506, 500);
		getContentPane().setLayout(null);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDate.setFont(new Font("Consolas", Font.PLAIN, 13));
		lblDate.setBounds(10, 11, 106, 20);
		getContentPane().add(lblDate);
		
		JLabel lblNumBc = new JLabel("Num BC");
		lblNumBc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNumBc.setFont(new Font("Consolas", Font.PLAIN, 13));
		lblNumBc.setBounds(286, 9, 65, 20);
		getContentPane().add(lblNumBc);
		
		JLabel lblFournisseur = new JLabel("Fournisseur");
		lblFournisseur.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFournisseur.setFont(new Font("Consolas", Font.PLAIN, 13));
		lblFournisseur.setBounds(10, 42, 106, 20);
		getContentPane().add(lblFournisseur);
		
		JLabel lblMatricule = new JLabel("Matricule");
		lblMatricule.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMatricule.setFont(new Font("Consolas", Font.PLAIN, 13));
		lblMatricule.setBounds(10, 73, 106, 20);
		getContentPane().add(lblMatricule);
		
		JLabel lblTypeVhicule = new JLabel("Type V\u00E9hicule");
		lblTypeVhicule.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTypeVhicule.setFont(new Font("Consolas", Font.PLAIN, 13));
		lblTypeVhicule.setBounds(9, 104, 106, 20);
		getContentPane().add(lblTypeVhicule);
		
		JLabel lblMarque = new JLabel("Marque");
		lblMarque.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMarque.setFont(new Font("Consolas", Font.PLAIN, 13));
		lblMarque.setBounds(280, 104, 71, 20);
		getContentPane().add(lblMarque);
		
		JLabel lblDateAquisition = new JLabel("Date Aquisition");
		lblDateAquisition.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDateAquisition.setFont(new Font("Consolas", Font.PLAIN, 13));
		lblDateAquisition.setBounds(0, 134, 116, 20);
		getContentPane().add(lblDateAquisition);
		
		JLabel lblDateLibration = new JLabel("Date Lib\u00E9ration");
		lblDateLibration.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDateLibration.setFont(new Font("Consolas", Font.PLAIN, 13));
		lblDateLibration.setBounds(245, 132, 106, 20);
		getContentPane().add(lblDateLibration);
		
		JLabel lblPrix = new JLabel("Prix");
		lblPrix.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrix.setFont(new Font("Consolas", Font.PLAIN, 13));
		lblPrix.setBounds(45, 165, 71, 20);
		getContentPane().add(lblPrix);
		
		JLabel lblPolytiqueLocation = new JLabel("Polytique Location");
		lblPolytiqueLocation.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPolytiqueLocation.setFont(new Font("Consolas", Font.PLAIN, 13));
		lblPolytiqueLocation.setBounds(211, 165, 140, 20);
		getContentPane().add(lblPolytiqueLocation);
		
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
			dt_du.setFont(new Font("Consolas", Font.PLAIN, 11));
			dt_du.setBounds(118, 132, 123, 20);
			getContentPane().add(dt_du);
			
			dt_au.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					Date dt_d = dt_du.getDate();
					Date dt_f = dt_au.getDate();
					
					if(dt_d.getTime()>dt_f.getTime()){
					    JOptionPane.showMessageDialog(null,"La date de début de recherche ne peut etre supérieur a la date de fin de recherche","Information",JOptionPane.INFORMATION_MESSAGE);
					    dt_au.setDate(dt_du.getDate());
					}
					
					
				}
			});
			
			
			dt_au.setDate(new Date());
			dt_au.setFont(new Font("Consolas", Font.PLAIN, 11));
			dt_au.setBounds(361, 132, 116, 20);
			getContentPane().add(dt_au);
			
			dt_act.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
				if(chRech.isSelected()){
					recherche(fct.dt.form_Aff(dt_act.getDate()),1);
				}else{
					if(fct.dt.superieur(dt_act.getDate(),fct.DataBase.date_act(FirstPg.con))){
						  JOptionPane.showMessageDialog(null,"Veillez vérifier la date de commande svp !!","Information",JOptionPane.INFORMATION_MESSAGE);
						  dt_act.setDate(fct.DataBase.date_act(FirstPg.con));
					}
				}

				}
			});
			
			
			dt_act.setDate(new Date());
			dt_act.setFont(new Font("Consolas", Font.PLAIN, 11));
			dt_act.setBounds(118, 11, 123, 20);
			getContentPane().add(dt_act);
			
			tfBC = new JTextField();
			tfBC.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent arg0) {
					if(chRech.isSelected()){
						recherche(tfBC.getText(),0);
					}
				}
			});
			tfBC.setBackground(Color.WHITE);
			tfBC.setEditable(false);
			tfBC.setFont(new Font("Consolas", Font.PLAIN, 12));
			tfBC.setBounds(361, 10, 114, 20);
			getContentPane().add(tfBC);
			tfBC.setColumns(10);
			
			tfMarque = new JTextField();
			tfMarque.setFont(new Font("Consolas", Font.PLAIN, 12));
			tfMarque.setColumns(10);
			tfMarque.setBounds(361, 104, 116, 20);
			getContentPane().add(tfMarque);
			cbFournisseur.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(chRech.isSelected()){
				    	recherche(cbFournisseur.getSelectedItem().toString(),2);
						try {
							getImmat();
						} catch (Exception e) {
							// TODO: handle exception
						}
					}else{
						try {
							getImmat();
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				}
			});
			
			
		
			cbFournisseur.setFont(new Font("Consolas", Font.PLAIN, 12));
			cbFournisseur.setBounds(118, 41, 256, 20);
			getContentPane().add(cbFournisseur);
			cbMatricule.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					 try {
							Statement stm = FirstPg.con.createStatement();
							String req = "select distinct TYPE, MARQUE from TRANS_VEHICULE_LOCATION where MATRICULE like '"+cbMatricule.getSelectedItem()+"' ";
							ResultSet rst = stm.executeQuery(req);
							
							while(rst.next()){
								cbTypeVehicule.setSelectedItem(rst.getString(1));
								tfMarque.setText(rst.getString(2));
							}
							stm.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			});
			
			cbMatricule.setEditable(true);
			cbMatricule.setFont(new Font("Consolas", Font.PLAIN, 12));
			cbMatricule.setBounds(118, 73, 173, 20);
			getContentPane().add(cbMatricule);
			
			
			cbTypeVehicule.setModel(new DefaultComboBoxModel(new String[] {"Tete semi-remorque", "Camion", "Transport", "Leger", "Benne", "Siterne", "Porte char", "Autres"}));
			cbTypeVehicule.setFont(new Font("Consolas", Font.PLAIN, 12));
			cbTypeVehicule.setBounds(118, 104, 173, 20);
			getContentPane().add(cbTypeVehicule);
			
			
			cbPolytique.setModel(new DefaultComboBoxModel(new String[] {"Jour", "Km", "Mission", "Autres"}));
			cbPolytique.setFont(new Font("Consolas", Font.PLAIN, 12));
			cbPolytique.setBounds(354, 164, 123, 20);
			getContentPane().add(cbPolytique);
			
			spPrix.setFont(new Font("Consolas", Font.PLAIN, 12));
			spPrix.setModel(new SpinnerNumberModel(new Long(0), new Long(0), null, new Long(100)));
			spPrix.setBounds(118, 164, 100, 20);
			getContentPane().add(spPrix);
			
			JButton btnActualiser = new JButton("Actualiser");
			btnActualiser.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					recherche("", 0);

					dt_du.setDate(fct.DataBase.date_act(FirstPg.con));
					dt_au.setDate(dt_du.getDate());
					spPrix.setValue(0);
					cbPolytique.setSelectedIndex(0);
					dt_act.setDate(dt_du.getDate());
					tfComment.setText("");
					tfMarque.setText("");
					
					getFournisseur();
					
				}
			});
			btnActualiser.setFont(new Font("Consolas", Font.PLAIN, 11));
			btnActualiser.setBounds(384, 40, 93, 23);
			getContentPane().add(btnActualiser);
			
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Bon de commandes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel.setBounds(10, 257, 467, 173);
			
			tab_fact = new JTable(d_tab,t_tab_b);
			tab_fact.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					try {
						Statement stm = FirstPg.con.createStatement();
						Statement stmf = FirstPg.con.createStatement();
						String req = "select CODE_F, MATRICULE, TYPE, MARQUE, DATEAQ, DATELB, PRIX, POLYTIQUELOCATION, DATES, COMENT from TRANS_VEHICULE_LOCATION where NUMBC like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"' ";
						
						ResultSet rst = stm.executeQuery(req);
						ResultSet rstf;
						while(rst.next()){
							
							req = "select nom from TRANS_FOURNISSEUR where CODE_F like '"+rst.getString(1)+"'";
							rstf = stmf.executeQuery(req);
							while(rstf.next()) cbFournisseur.setSelectedItem(rstf.getString(1));
							
							cbMatricule.setSelectedItem(rst.getString(2));
							dt_du.setDate(rst.getDate(5));
							dt_au.setDate(rst.getDate(6));
							spPrix.setValue(rst.getDouble(7));
							cbPolytique.setSelectedItem(rst.getString(8));
							dt_act.setDate(rst.getDate(9));
							tfComment.setText(rst.getString(10));
							cbTypeVehicule.setSelectedItem(rst.getString(3));
						}
						chDette.setSelected(fct.DataBase.isDetteBC(tab_fact.getValueAt(tab_fact.getSelectedRow(), 0).toString(), FirstPg.con));
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
			
			getContentPane().add(panel);
			chRech.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(chRech.isSelected()){
						tfBC.setEditable(true);
						btnConfirmer.setEnabled(false);
						btnModifier.setEnabled(false);
						btnSupprimer.setEnabled(false);
					}else{
						tfBC.setEditable(false);
						btnConfirmer.setEnabled(true);
						btnModifier.setEnabled(true);
						btnSupprimer.setEnabled(true);
						tfBC.setText("");
					    dt_act.setDate(fct.DataBase.date_act(FirstPg.con));
					    dt_du.setDate(dt_act.getDate());
					    dt_au.setDate(dt_act.getDate());
					    
					}
				}
			});
			
			chRech.setBounds(10, 437, 116, 23);
			getContentPane().add(chRech);
			
			
			chDette.setHorizontalAlignment(SwingConstants.RIGHT);
			chDette.setBounds(45, 231, 71, 23);
			getContentPane().add(chDette);
			btnConfirmer.setFont(new Font("Consolas", Font.PLAIN, 11));
			btnConfirmer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(tfMarque.getText().length()<3 || cbMatricule.getSelectedItem().toString().length()<5){
						  JOptionPane.showMessageDialog(null,"Veillez Précisé l'immatriculation et la marque svp !!","Attention",JOptionPane.WARNING_MESSAGE);
					}else{
						try {
							Statement stm = FirstPg.con.createStatement();
							
							String req = "select NUMBC from TRANS_VEHICULE_LOCATION";
							ResultSet rst = stm.executeQuery(req);
							Vector vct = new Vector();
							while(rst.next()) vct.add(rst.getString(1));
							String nmbon = fct.numRote.BonNum(vct);
							       req = "insert into TRANS_VEHICULE_LOCATION " +
									     "(NUMBC, CODE_F, MATRICULE, TYPE, MARQUE, DATEAQ, DATELB, PRIX, POLYTIQUELOCATION, DATES, COMENT) values " +
										 "('"+nmbon+"', '"+fct.codification.cd_prs(cbFournisseur.getSelectedItem()+"")+"', '"+cbMatricule.getSelectedItem()+"','"+cbTypeVehicule.getSelectedItem()+"', '"+tfMarque.getText()+"', '"+fct.dt.form_Aff(dt_du.getDate())+"', '"+fct.dt.form_Aff(dt_au.getDate())+"', "+spPrix.getValue()+", '"+cbPolytique.getSelectedItem()+"', '"+fct.dt.form_Aff(dt_act.getDate())+"', '"+tfComment.getText()+"')";
						   
						   stm.executeUpdate(req);
						   
						   if(chDette.isSelected()){
							   req = "insert into TRANS_SUIVI_BC (NUMBC, PRIX, VERSEMENT, NUMVERS, CODE_F) values ('"+nmbon+"', "+spPrix.getValue()+", 0, '', '"+codification.cd_prs(cbFournisseur.getSelectedItem()+"")+"')";
							   stm.executeUpdate(req);
						   }
						   
						   JOptionPane.showMessageDialog(null,"Insertion effectuée avec succée","Information",JOptionPane.INFORMATION_MESSAGE);

						  
						   
						   recherche("", 0);
							stm.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
			});
			
			btnConfirmer.setBounds(118, 231, 106, 23);
			getContentPane().add(btnConfirmer);
			btnModifier.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					if(tfMarque.getText().length()<3 || cbMatricule.getSelectedItem().toString().length()<5){
						  JOptionPane.showMessageDialog(null,"Veillez Précisé l'immatriculation et la marque svp !!","Attention",JOptionPane.WARNING_MESSAGE);
					}else{
						
						if( fct.DataBase.isDetteBC(tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"", FirstPg.con)){
							  JOptionPane.showMessageDialog(null,"Opération non effectuée.\nVeillez supprimer le suivi du BC N° : '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"' pour effectuée la modification.","Attention",JOptionPane.WARNING_MESSAGE);
						}else{
							try {
							tab_fact.getValueAt(tab_fact.getSelectedRow(), tab_fact.getSelectedColumn());

							Statement stm = FirstPg.con.createStatement();
							
							String req = "select NUMBC from TRANS_VEHICULE_LOCATION";
							ResultSet rst = stm.executeQuery(req);
							Vector vct = new Vector();
							while(rst.next()) vct.add(rst.getString(1));
							String nmbon = fct.numRote.BonNum(vct);
							       req = "update TRANS_VEHICULE_LOCATION set " +
									     "CODE_F = '"+fct.codification.cd_prs(cbFournisseur.getSelectedItem()+"")+"'" +
									     		", MATRICULE = '"+cbMatricule.getSelectedItem()+"'" +
									     				", TYPE = '"+cbTypeVehicule.getSelectedItem()+"'" +
									     						", MARQUE = '"+tfMarque.getText()+"'" +
									     								", DATEAQ = '"+fct.dt.form_Aff(dt_du.getDate())+"'" +
									     										", DATELB = '"+fct.dt.form_Aff(dt_au.getDate())+"'" +
									     												", PRIX = "+spPrix.getValue()+"" +
									     														", POLYTIQUELOCATION = '"+cbPolytique.getSelectedItem()+"'" +
									     																", DATES = '"+fct.dt.form_Aff(dt_act.getDate())+"'" +
									     																		", COMENT = '"+tfComment.getText()+"'" +
										                                                                          " where NUMBC like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"' ";
						   
						   stm.executeUpdate(req);
						   
						   if(chDette.isSelected()){
							   
							   
								boolean bl = fct.DataBase.isDetteBC(tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"", FirstPg.con);

								if(!bl){
									  req = "insert into TRANS_SUIVI_BC (NUMBC, PRIX, VERSEMENT, NUMVERS, CODE_F) values ('"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"', "+spPrix.getValue()+", 0, '', '"+codification.cd_prs(cbFournisseur.getSelectedItem()+"")+"')";
									  stm.executeUpdate(req);
								}
							   
						   }else{
								req = "delete from TRANS_SUIVI_BC where NUMBC like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"' ";
								stm.executeUpdate(req);
						   }
						   JOptionPane.showMessageDialog(null,"Modification effectuée avec succée","Information",JOptionPane.INFORMATION_MESSAGE);

						   stm.close();
						   
						   recherche("", 0);
						   
						} catch (SQLException e) {
							  JOptionPane.showMessageDialog(null,"Veillez selectionné le bon a modifier !!","Erreur",JOptionPane.ERROR_MESSAGE);
							  e.printStackTrace();
						}
						}
						
						
					}
					
				}
			});
			btnModifier.setFont(new Font("Consolas", Font.PLAIN, 11));
			
			
			btnModifier.setBounds(245, 231, 106, 23);
			getContentPane().add(btnModifier);
			btnSupprimer.setFont(new Font("Consolas", Font.PLAIN, 11));
			btnSupprimer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					try {
						tab_fact.getValueAt(tab_fact.getSelectedRow(), tab_fact.getSelectedColumn());
						Statement stm = FirstPg.con.createStatement();
						String req = "select VERSEMENT from TRANS_SUIVI_BC where NUMBC like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"'";
						ResultSet rst = stm.executeQuery(req);
						double vrs = 0;
						while(rst.next()) vrs = rst.getDouble(1);
						
						if(vrs > 0){
							int conf = JOptionPane.showConfirmDialog(null, "Attention:\nUn versement est déja effectuer sur ce BC.\nVoulez vous vraiment effacer ce BC !!?", "confirmation de suppression",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
							if(conf == JOptionPane.OK_OPTION){
								req = "delete from TRANS_SUIVI_BC where NUMBC like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"' ";
							    stm.executeUpdate(req);
								
								req = "delete from TRANS_VEHICULE_LOCATION where NUMBC like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"' ";
								stm.executeUpdate(req);
								JOptionPane.showMessageDialog(null,"Suppression effectuée avec succée","Information",JOptionPane.INFORMATION_MESSAGE);
							}
							
						}else{
							
							req = "delete from TRANS_SUIVI_BC where NUMBC like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"' ";
						    stm.executeUpdate(req);
							req = "delete from TRANS_VEHICULE_LOCATION where NUMBC like '"+tab_fact.getValueAt(tab_fact.getSelectedRow(), 0)+"' ";
							stm.executeUpdate(req);

							JOptionPane.showMessageDialog(null,"Suppression effectuée avec succée","Information",JOptionPane.INFORMATION_MESSAGE);

						}

						recherche("", 0);
					} catch (Exception e) {
						  JOptionPane.showMessageDialog(null,"Veillez selectionné le bon a éliminé !!\n"+e,"Attention",JOptionPane.WARNING_MESSAGE);
					
					}
					
				}
			});
			
			
			btnSupprimer.setBounds(371, 231, 106, 23);
			getContentPane().add(btnSupprimer);
			
			JLabel lblCommentaire = new JLabel("Commentaire");
			lblCommentaire.setHorizontalAlignment(SwingConstants.RIGHT);
			lblCommentaire.setFont(new Font("Consolas", Font.PLAIN, 13));
			lblCommentaire.setBounds(30, 200, 84, 20);
			getContentPane().add(lblCommentaire);
			
			tfComment = new JTextField();
			tfComment.setFont(new Font("Consolas", Font.PLAIN, 12));
			tfComment.setColumns(10);
			tfComment.setBounds(118, 200, 359, 20);
			getContentPane().add(tfComment);
			
			JButton btnImprimer = new JButton("Afficher");
			btnImprimer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					b_liv(tab_fact.getValueAt(tab_fact.getSelectedRow(), 0).toString());
				}
			});
			btnImprimer.setFont(new Font("Consolas", Font.PLAIN, 11));
			btnImprimer.setBounds(371, 437, 106, 23);
			getContentPane().add(btnImprimer);
		
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					getFournisseur();
					recherche("",0);
				}
			}).start();

	}
	
	public void getFournisseur(){
	    try {
			Statement stm = FirstPg.con.createStatement();
			String req = "select NOM from TRANS_FOURNISSEUR order by NOM";
			ResultSet rst = stm.executeQuery(req);
			cbFournisseur.removeAllItems();
			while(rst.next()){
				cbFournisseur.addItem(rst.getString(1));
			}
			stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getImmat(){
	    try {
			Statement stm = FirstPg.con.createStatement();
			String req = "select distinct MATRICULE from TRANS_VEHICULE_LOCATION where CODE_F like '"+fct.codification.cd_prs(cbFournisseur.getSelectedItem().toString())+"' ";
			ResultSet rst = stm.executeQuery(req);
			cbMatricule.removeAllItems();
			while(rst.next()){
				cbMatricule.addItem(rst.getString(1));
			}
			stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void recherche(String condition, int x){
		try {
			 def_t = new DefaultTableModel();
             def_t.setColumnIdentifiers(t_tab_b);
             
			Statement stm = FirstPg.con.createStatement();
			Statement stmf = FirstPg.con.createStatement();
			
			String comp = "";
			
			if(x==0){
				comp = "NUMBC like '"+condition+"%'";
			}else if(x==1){
				comp = "DATES <= '"+condition+"' ";
			}else{
				comp = "CODE_F like '"+fct.codification.cd_prs(condition)+"'";
			}
			
			String req = "select NUMBC, CODE_F, DATES, PRIX from TRANS_VEHICULE_LOCATION where "+comp+" order by DATES DESC";
			ResultSet rst = stm.executeQuery(req);
			ResultSet rstf;
			
			while(rst.next()){
				
				req = "select nom from TRANS_FOURNISSEUR where CODE_F like '"+rst.getString(2)+"'";
				rstf = stmf.executeQuery(req);
				while(rstf.next()) req = rstf.getString(1);
				
				 def_t.addRow(new Object[]{rst.getString(1),req,fct.dt.form_Aff(rst.getDate(3)),fct.nbr.dbToDf(rst.getDouble(4))});					  
				 
			}
			  tab_fact.setModel(def_t);
			  fct.table.changeSize(70, 20, tab_fact, 0);
			  fct.table.changeSize(150, 20, tab_fact, 1);
			  
			  for(int i = 0; i< tab_fact.getRowCount();i++){
		        	 fct.table.changeSizeh(20,tab_fact,i);
		        }
			  stmf.close();
			  
			  req = "select NUMBC from TRANS_VEHICULE_LOCATION";
			    rst = stm.executeQuery(req);
			    Vector vct = new Vector();
				while(rst.next()) vct.add(rst.getString(1));
			    tfBC.setText(fct.numRote.BonNum(vct));
			  
			stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			
			
		req = "select NOM, NRC, NIF, NAI, ADRESSE, TEL from TRANS_FOURNISSEUR where CODE_F like '"+fct.codification.cd_prs(tab_fact.getValueAt(tab_fact.getSelectedRow(), 1).toString())+"' ";
			
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
				//JasperDesign jasperDesign;
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
				
				
	        req = "select POLYTIQUELOCATION, DATES, COMENT, PRIX from TRANS_VEHICULE_LOCATION where NUMBC like '"+n_bon+"' ";			
			rst = stm.executeQuery(req);
			
			while(rst.next()){
				parameters.put("politique", rst.getString(1));
				parameters.put("dts", fct.dt.form_Aff(rst.getDate(2)));
				parameters.put("Remarque", rst.getString(3));
				parameters.put("total_ht", fct.nbr.dbToDf(rst.getDouble(4))+" Da.");
			}

				

				  
		  		JasperPrint jasperPrint;
				
						jasperPrint = JasperFillManager.fillReport("C:/JTrans/etat/bc.jasper", parameters,FirstPg.con);
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
	

	
}
