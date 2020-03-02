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
import javax.swing.table.TableColumn;

import fct.codification;
import fct.dt;
import fct.nbr;
import fct.table;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

import Etat.EtatPdf;
import Mission.CMissionPan;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Calcule extends JPanel {

	JTable tab_fact;
	DefaultTableModel def_t;
	Object [][] d_tab= new Object[20][10];
    String[] t_tab_b = { "N°Mission","Client","Date","Prix HT","Frais de mission","Nbr Bon Gasoil", "Prix de location","Brute"};
    private dt dtDu;
    private dt dtAu;
    private JTextField tfHt;
    private JTextField tfFraisMs;
    private JTextField tfBon;
    private JTextField tfPrixLocation;
    private JTextField tfPrixRevien;
    private JLabel lblPrixBonGasoil;
	JSpinner spPrixGasoil = new JSpinner();
	JComboBox cbClient = new JComboBox();
	JCheckBox chMasque = new JCheckBox("Titre a masqu\u00E9");
	
	
	public Calcule() {
		setLayout(null);
		tab_fact = new JTable(d_tab,t_tab_b);
		tab_fact.getTableHeader().addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(chMasque.isSelected()){
					int i =	tab_fact.columnAtPoint(e.getPoint());
					if( i>2){
						TableColumn column = tab_fact.getColumnModel().getColumn(i);
						tab_fact.removeColumn(column);
					}
				}
			}
		 });
		table panel = new table(tab_fact);
		panel.setBounds(10, 60, 776, 327);
		add(panel);
		
		dtDu = new dt();
		dtDu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rechTab(dtDu.getDate(),dtAu.getDate(), cbClient.getSelectedItem()+"");
			}
		});
		dtDu.setBounds(152, 11, 117, 28);
		add(dtDu);
		
		dtAu = new dt();
		dtAu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rechTab(dtDu.getDate(),dtAu.getDate(),cbClient.getSelectedItem()+"");
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
		
		JLabel lblTotal = new JLabel("Total");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setFont(new Font("Consolas", Font.BOLD, 16));
		lblTotal.setBounds(229, 398, 57, 28);
		add(lblTotal);
		
		tfHt = new JTextField();
		tfHt.setForeground(Color.RED);
		tfHt.setBackground(Color.GREEN);
		tfHt.setEnabled(false);
		tfHt.setHorizontalAlignment(SwingConstants.CENTER);
		tfHt.setFont(new Font("Consolas", Font.PLAIN, 11));
		tfHt.setBounds(291, 398, 95, 28);
		add(tfHt);
		tfHt.setColumns(10);
		
		tfFraisMs = new JTextField();
		tfFraisMs.setForeground(Color.RED);
		tfFraisMs.setBackground(Color.YELLOW);
		tfFraisMs.setEnabled(false);
		tfFraisMs.setHorizontalAlignment(SwingConstants.CENTER);
		tfFraisMs.setFont(new Font("Consolas", Font.PLAIN, 11));
		tfFraisMs.setColumns(10);
		tfFraisMs.setBounds(390, 398, 95, 28);
		add(tfFraisMs);
		
		tfBon = new JTextField();
		tfBon.setForeground(Color.RED);
		tfBon.setBackground(Color.YELLOW);
		tfBon.setEnabled(false);
		tfBon.setHorizontalAlignment(SwingConstants.CENTER);
		tfBon.setFont(new Font("Consolas", Font.PLAIN, 11));
		tfBon.setColumns(10);
		tfBon.setBounds(489, 398, 87, 28);
		add(tfBon);
		
		tfPrixLocation = new JTextField();
		tfPrixLocation.setForeground(Color.RED);
		tfPrixLocation.setBackground(Color.ORANGE);
		tfPrixLocation.setEnabled(false);
		tfPrixLocation.setHorizontalAlignment(SwingConstants.CENTER);
		tfPrixLocation.setFont(new Font("Consolas", Font.PLAIN, 11));
		tfPrixLocation.setColumns(10);
		tfPrixLocation.setBounds(580, 398, 95, 28);
		add(tfPrixLocation);
		
		tfPrixRevien = new JTextField();
		tfPrixRevien.setForeground(Color.RED);
		tfPrixRevien.setBackground(new Color(0, 191, 255));
		tfPrixRevien.setEnabled(false);
		tfPrixRevien.setHorizontalAlignment(SwingConstants.CENTER);
		tfPrixRevien.setFont(new Font("Consolas", Font.PLAIN, 11));
		tfPrixRevien.setColumns(10);
		tfPrixRevien.setBounds(680, 398, 106, 28);
		add(tfPrixRevien);
		
		lblPrixBonGasoil = new JLabel("Prix Bon Gasoil");
		lblPrixBonGasoil.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrixBonGasoil.setFont(new Font("Consolas", Font.PLAIN, 14));
		lblPrixBonGasoil.setBounds(443, 11, 127, 28);
		add(lblPrixBonGasoil);
		
		spPrixGasoil.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(100)));
		spPrixGasoil.setFont(new Font("Consolas", Font.PLAIN, 14));
		spPrixGasoil.setBounds(580, 12, 78, 28);
		add(spPrixGasoil);
		
		JButton btnEtatPdf = new JButton("Etat PDF");
		btnEtatPdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
          		EtatPdf.etat(tab_fact, "Etat de valorisation des missions du "+dt.form_Aff(dtDu.getDate())+" Au "+dt.form_Aff(dtAu.getDate()));
			}
		});
		btnEtatPdf.setFont(new Font("Consolas", Font.PLAIN, 14));
		btnEtatPdf.setBounds(680, 13, 106, 28);
		add(btnEtatPdf);
		
		
		chMasque.setFont(new Font("Consolas", Font.PLAIN, 11));
		chMasque.setBounds(10, 394, 109, 32);
		add(chMasque);
		
		
		cbClient.setModel(new DefaultComboBoxModel(new String[] {"Tous les clients"}));
		cbClient.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		cbClient.setBounds(119, 398, 117, 28);
		add(cbClient);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				rechTab(dtDu.getDate(),dtAu.getDate(),cbClient.getSelectedItem()+"");
				try {
					RechClient();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	void rechTab(Date dbRch, Date fRech, String clients){
		try {
			def_t = new DefaultTableModel();
			def_t.setColumnIdentifiers(t_tab_b);
			Statement stm = FirstPg.con.createStatement();
			Statement stm1 = FirstPg.con.createStatement();
			String req = "";
			if(clients.equals("Tous les clients")){
				 req = "select NUMMIS, CODE_C, DATEALLEZ, PRIXHT, DATERETOUR from TRANS_MISSION where  DATEALLEZ between '"+dt.form_Aff(dbRch)+"' and '"+dt.form_Aff(fRech)+"' order by DATEALLEZ DESC";
			}else{
				 req = "select NUMMIS, CODE_C, DATEALLEZ, PRIXHT, DATERETOUR from TRANS_MISSION where CODE_C like '"+codification.cd_prs(clients)+"' and DATEALLEZ between '"+dt.form_Aff(dbRch)+"' and '"+dt.form_Aff(fRech)+"' order by DATEALLEZ DESC";
			}
			ResultSet rst = stm.executeQuery(req);
			ResultSet rst1;
			double prixHT = 0;
			double fraisMission = 0;
			double nbrBon = 0;
			double prixLoc = 0;
			double prixReviens = 0;
			
			double prixHTt = 0;
			double fraisMissiont = 0;
			double nbrBont = 0;
			double prixLoct = 0;
			double prixRevienst = 0;
			
			
			
			while(rst.next()){
				 prixHT = 0;
				 fraisMission = 0;
				 nbrBon = 0;
				 prixLoc = 0;
				 prixReviens = 0;
			            //"N°Mission","Client","Date","Prix HT","Frais de mission","Nbr Bon Gasoil", "Prix de location","Prix de reviens"};
				prixHT = rst.getDouble(4);
				prixHTt = prixHTt + prixHT;
				tfHt.setText(nbr.dbToDf(prixHTt));
						req = "select FRAISMISSION from TRANS_VW_CLT where NUMMIS like '"+rst.getString(1)+"' ";
						rst1 = stm1.executeQuery(req);
						while(rst1.next()){
							fraisMission = rst1.getDouble(1);
						}
						fraisMissiont = fraisMissiont + fraisMission;
						tfFraisMs.setText(nbr.dbToDf(fraisMissiont));
						req = "select count(NUMBON) from TRANS_VW_BNGASOIL where NUMMIS like '"+rst.getString(1)+"' ";
						rst1 = stm1.executeQuery(req);
						while(rst1.next()){
							nbrBon = rst1.getInt(1)* nbr.toDouble(spPrixGasoil.getValue()+"");
						}
						nbrBont = nbrBont + nbrBon;
						tfBon.setText(nbr.dbToDf(nbrBont));
						req = "select IDVEHICULELOUER, IDREMORQUELOUER from TRANS_ETAT_MISSION where NUMMIS like '"+rst.getString(1)+"' ";
						rst1 = stm1.executeQuery(req);
						String idV = "";
						String idR = "";

						while(rst1.next()){
							idV = rst1.getString(1);
							idR = rst1.getString(2);
						}
						
						if(idV==null && idR==null){
							prixLoc = 0;
						}else{
							req = "select DATEAQ, DATELB, PRIX from TRANS_VEHICULE_LOCATION where DATEAQ <= '"+dt.form_Aff(rst.getDate(3))+"' and DATELB >= '"+dt.form_Aff(rst.getDate(5))+"' and (MATRICULE like '"+idV+"' OR MATRICULE like '"+idR+"')";
							rst1 = stm1.executeQuery(req);
							long nbNuite = 0;
							while(rst1.next()){
								nbNuite = dt.nuitee(rst1.getDate(2), rst1.getDate(1));
								prixLoc = rst1.getDouble(3)/nbNuite;
							}
							prixLoc =  dt.nuitee(rst.getDate(5), rst.getDate(3))*prixLoc;
						}
						prixLoct = prixLoct + prixLoc;
						tfPrixLocation.setText(nbr.dbToDf(prixLoct));

						prixReviens = prixHT - (fraisMission+nbrBon+prixLoc);
						prixRevienst = prixRevienst + prixReviens;
						tfPrixRevien.setText(nbr.dbToDf(prixRevienst));
						def_t.addRow(new Object[]{rst.getString(1), RechClientStr(rst.getString(2)),dt.form_Aff(rst.getDate(3)), nbr.dbToDf(prixHT),nbr.dbToDf(fraisMission),nbr.dbToDf(nbrBon),nbr.dbToDf(prixLoc),nbr.dbToDf(prixReviens)});
			}	
			tab_fact.setModel(def_t);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	 String RechClientStr(String code_c)throws SQLException{
	    	String str = "";
			Statement stm = FirstPg.con.createStatement();
			String req = "select NOM from TRANS_CLIENT where CODE_C like '"+code_c+"' ";
			ResultSet rst = stm.executeQuery(req);
			while (rst.next()){
				str=rst.getString(1);
			}
			stm.close();
			return str;
		}
	 
	 void RechClient()throws SQLException{
			Statement stm = FirstPg.con.createStatement();

			String req = "select NOM from TRANS_CLIENT where visible = 1 order by NOM";
			ResultSet rst = stm.executeQuery(req);
			cbClient.removeAllItems();
			cbClient.addItem("Tous les clients");
			while (rst.next()){
				cbClient.addItem(rst.getString(1));
			}
		}
}
