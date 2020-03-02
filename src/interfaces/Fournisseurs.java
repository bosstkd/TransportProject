package interfaces;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.Statement;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JCheckBox;

import fct.DataBase;
import fct.codification;


public class Fournisseurs extends JInternalFrame {
	private JTextField tf_fournisseur;
	private JTextField tf_nrc;
	private JTextField tf_nif;
	private JTextField tf_tel;
	JLabel lb_code = new JLabel("");
	JButton btnAjouter = new JButton("Ajouter");
	JButton btnSupprimer = new JButton("Supprimer");
	JTextArea ta_adresse = new JTextArea();
	JCheckBox ch_Rech = new JCheckBox("Recherche par Fournisseur");

	JTable tab_fact;
	DefaultTableModel def_t;
	Object [][] d_tab= new Object[20][7];
    String[] t_tab_b = { "Nom","NRC","NIF","Adresse","Tel","Code_F"};
    JCheckBox chckbxVisible = new JCheckBox("Visible");
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Fournisseurs frame = new Fournisseurs();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Fournisseurs() {
		setToolTipText("Configuration Fournisseurs");
		getContentPane().setFont(new Font("Century Gothic", Font.ITALIC, 14));
		setTitle("Fournisseurs");
		setFrameIcon(new ImageIcon(Fournisseurs.class.getResource("/icones/original/A32/limewire-icone-5034-128_RS.png")));
		setClosable(true);
		setIconifiable(true);
		setBounds(100, 40, 600, 554);
		getContentPane().setLayout(null);
		
		JLabel lblNom = new JLabel("Fournisseur");
		lblNom.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNom.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		lblNom.setBounds(0, 22, 79, 27);
		getContentPane().add(lblNom);
		
		JLabel lblNrc = new JLabel("NRC");
		lblNrc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNrc.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		lblNrc.setBounds(10, 61, 69, 27);
		getContentPane().add(lblNrc);
		
		JLabel lblNif = new JLabel("NIF");
		lblNif.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNif.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		lblNif.setBounds(203, 61, 44, 27);
		getContentPane().add(lblNif);
		
		JLabel lblAdresse = new JLabel("Adresse");
		lblAdresse.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		lblAdresse.setBounds(450, 11, 101, 27);
		getContentPane().add(lblAdresse);
		
		JLabel lblTel = new JLabel("TEL");
		lblTel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTel.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		lblTel.setBounds(10, 99, 69, 27);
		getContentPane().add(lblTel);
		
		tf_fournisseur = new JTextField();
		tf_fournisseur.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				lb_code.setText(fct.codification.cd_prs(tf_fournisseur.getText().replaceAll("'", "")));
				code_gen(lb_code.getText());
				if(ch_Rech.isSelected()) recherche(lb_code.getText());
			}
		});
		
		tf_fournisseur.setFont(new Font("Century Gothic", Font.ITALIC, 12));
		tf_fournisseur.setBounds(85, 22, 274, 25);
		getContentPane().add(tf_fournisseur);
		tf_fournisseur.setColumns(10);
		
		tf_nrc = new JTextField();
		tf_nrc.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {

			}
		});
		
		tf_nrc.setFont(new Font("Century Gothic", Font.ITALIC, 12));
		tf_nrc.setColumns(10);
		tf_nrc.setBounds(85, 60, 108, 27);
		getContentPane().add(tf_nrc);
		
		tf_nif = new JTextField();
		tf_nif.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {

			}
		});
		
		tf_nif.setFont(new Font("Century Gothic", Font.ITALIC, 12));
		tf_nif.setColumns(10);
		tf_nif.setBounds(251, 61, 108, 27);
		getContentPane().add(tf_nif);
		
		tf_tel = new JTextField();
		tf_tel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String dtj = "";
				try{
					int i = Integer.parseInt(tf_tel.getText().toString());
				}catch(Exception exp){
					try{
						dtj = tf_tel.getText();
						tf_tel.setText(dtj.substring(0,tf_tel.getText().toString().length()-1));
					}catch(Exception e1){}
				}
			}
		});
		tf_tel.setFont(new Font("Century Gothic", Font.ITALIC, 12));
		tf_tel.setColumns(10);
		tf_tel.setBounds(85, 99, 148, 27);
		getContentPane().add(tf_tel);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(369, 11, 9, 159);
		getContentPane().add(separator);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(380, 38, 194, 120);
		getContentPane().add(scrollPane);
		
		
		scrollPane.setViewportView(ta_adresse);
		ta_adresse.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		
		
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(tf_fournisseur.getText().replaceAll("'", "").length()<4 || tf_nrc.getText().replaceAll("'", "").length()<6 || tf_nif.getText().replaceAll("'", "").length()<6){
			    	  JOptionPane.showMessageDialog(null, "veuillez remplir les informations svp !!","Attention",JOptionPane.WARNING_MESSAGE);
				}else{
					
					 def_t = new DefaultTableModel();
		               def_t.setColumnIdentifiers(t_tab_b);
					
					try {
						int x = 1;
						if(chckbxVisible.isSelected()) x = 1;
						else x = 0;
								if(btnAjouter.getText().equals("Modifier")){
									String req = "Declare " +
											 
																 	 "begin " +
																 	  "update TRANS_FOURNISSEUR set NOM = '"+tf_fournisseur.getText().replaceAll("'", "")+"', NRC = '"+tf_nrc.getText().replaceAll("'", "")+"', NIF = '"+tf_nif.getText().replaceAll("'", "")+"',ADRESSE = '"+ta_adresse.getText().replaceAll("'", "")+"', TEL = '"+tf_tel.getText()+"', visible = "+x+" where CODE_F like '"+lb_code.getText()+"'; " +
																 	 "end;";

																CallableStatement  cstm = FirstPg.con.prepareCall(req);
															    cstm.executeQuery();
															    cstm.close();
								}else{
									String req = "Declare " +
											 
																 	 "begin " +
																 	 "insert into TRANS_FOURNISSEUR (NOM, NRC, NIF, ADRESSE, TEL, CODE_F, visible) values ('"+tf_fournisseur.getText().replaceAll("'", "")+"','"+tf_nrc.getText().replaceAll("'", "")+"','"+tf_nif.getText().replaceAll("'", "")+"','"+ta_adresse.getText().replaceAll("'", "")+"','"+tf_tel.getText()+"','"+lb_code.getText()+"', "+x+"); " +
																 	 "end;";

																CallableStatement  cstm = FirstPg.con.prepareCall(req);
															    cstm.executeQuery();
															    cstm.close();
									btnAjouter.setText("Modifier");
									btnSupprimer.setEnabled(true);
								}			
								
						  JOptionPane.showMessageDialog(null, "Insertion effectuer avec succès.","Information",JOptionPane.INFORMATION_MESSAGE);

						  String req = "select NOM, NRC, NIF, ADRESSE , TEL, CODE_F from TRANS_FOURNISSEUR order by NOM";
						  
						  ResultSet rst = FirstPg.con.createStatement().executeQuery(req);
						  
						  while(rst.next()){
			             	  if(!rst.getString(6).equals("Inconnu"))def_t.addRow(new Object[]{rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6)});
						  }
						  
						  tab_fact.setModel(def_t);
						
			        		
			        for(int i = 0; i< tab_fact.getRowCount();i++){
			        	 changeSizeh(25,tab_fact,i);
			        }
						  
					} catch (SQLException e1) {
				    	  JOptionPane.showMessageDialog(null, "Erreur "+e1,"erreur",JOptionPane.ERROR_MESSAGE);
					}
					
					
				}
				
			}
		});
		
		
		btnAjouter.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 14));
		btnAjouter.setBounds(257, 181, 205, 36);
		getContentPane().add(btnAjouter);
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int conf = JOptionPane.showConfirmDialog(null, "Est-vous sur de vouloir supprimer ce fournisseur !!?", "confirmation de suppression",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if(conf == JOptionPane.OK_OPTION){
					try {
					
					
					 def_t = new DefaultTableModel();
		               def_t.setColumnIdentifiers(t_tab_b);
					
		               
						
						String req  = "";
						java.sql.Statement stm = FirstPg.con.createStatement();
						
					 req = "Declare " +
							 	 "begin " +
							 	 "delete from TRANS_FOURNISSEUR where CODE_F like '"+lb_code.getText()+"'; " +
							// 	 "insert into TRANS_FOURNISSEUR (NOM, NRC, NIF, ADRESSE, TEL, CODE_F) values ('"+tf_fournisseur.getText().replaceAll("'", "")+"','"+tf_nrc.getText().replaceAll("'", "")+"','"+tf_nif.getText().replaceAll("'", "")+"','"+ta_adresse.getText().replaceAll("'", "")+"','"+tf_tel.getText()+"','"+lb_code.getText()+"'); " +
							 	 "end;";
					
							CallableStatement  cstm = FirstPg.con.prepareCall(req);
						    cstm.executeQuery();
							btnAjouter.setText("Ajouter");
							btnSupprimer.setEnabled(false);

					  JOptionPane.showMessageDialog(null, "Suppression effectuer avec succès.","Information",JOptionPane.INFORMATION_MESSAGE);

					  req = "select NOM, NRC, NIF, ADRESSE , TEL, CODE_F from TRANS_FOURNISSEUR order by NOM";
					  
					   ResultSet rst = FirstPg.con.createStatement().executeQuery(req);
					  
					  while(rst.next()){
		             	  if(!rst.getString(6).equals("Inconnu"))def_t.addRow(new Object[]{rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6)});
					  }
					  
					  tab_fact.setModel(def_t);
					  
					
		        for(int i = 0; i< tab_fact.getRowCount();i++){
		        	 changeSizeh(25,tab_fact,i);
		        }
						
				} catch (SQLException e1) {
			    	  JOptionPane.showMessageDialog(null, "Erreur "+e1,"erreur",JOptionPane.ERROR_MESSAGE);
				}
				}
				
				
				
			}
		});
		
		
		btnSupprimer.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 14));
		btnSupprimer.setBounds(473, 181, 101, 36);
		getContentPane().add(btnSupprimer);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 169, 564, 15);
		getContentPane().add(separator_1);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Liste Fournisseurs", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 224, 564, 260);
		getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		
		tab_fact = new JTable(d_tab,t_tab_b);
		tab_fact.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				int row = tab_fact.getSelectedRow();
				
				tf_fournisseur.setText(""+tab_fact.getValueAt(row, 0));
				tf_nrc.setText(""+tab_fact.getValueAt(row, 1));
				tf_nif.setText(""+tab_fact.getValueAt(row, 2));
				tf_tel.setText(""+tab_fact.getValueAt(row, 4));
				ta_adresse.setText(""+tab_fact.getValueAt(row, 3));
				code_gen(lb_code.getText());
				chckbxVisible.setSelected(DataBase.isVisible(codification.cd_prs(""+tab_fact.getValueAt(row, 0)), "TRANS_FOURNISSEUR where CODE_F like "));

 
			}				
		});
		
		
		tab_fact.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		tab_fact.setRowHeight(30);
				
	   JScrollPane scrollPane1 = new JScrollPane(tab_fact);
	   panel.add(scrollPane1);
		
		JLabel lblCodeF = new JLabel("Code F");
		lblCodeF.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblCodeF.setBounds(10, 181, 50, 27);
		getContentPane().add(lblCodeF);
		lb_code.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		lb_code.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lb_code.setBounds(65, 181, 168, 27);
		getContentPane().add(lb_code);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setBounds(238, 169, 9, 48);
		getContentPane().add(separator_2);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(10, 217, 230, 15);
		getContentPane().add(separator_3);
		
		ch_Rech.setBounds(10, 491, 181, 23);
		getContentPane().add(ch_Rech);
		
		JButton btnActualis = new JButton("Actualis\u00E9");
		btnActualis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				recherche("%");
			}
		});
		btnActualis.setBounds(203, 491, 89, 23);
		getContentPane().add(btnActualis);
		
		
		chckbxVisible.setSelected(true);
		chckbxVisible.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxVisible.setBounds(10, 133, 97, 23);
		getContentPane().add(chckbxVisible);

	new Thread(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			  
			 def_t = new DefaultTableModel();
             def_t.setColumnIdentifiers(t_tab_b);
			
			try {
				String req = "select NOM, NRC, NIF, ADRESSE , TEL, CODE_F from TRANS_FOURNISSEUR order by NOM";
				ResultSet	rst = FirstPg.con.createStatement().executeQuery(req);  
				
				while(rst.next()){
             	  if(!rst.getString(6).equals("Inconnu"))def_t.addRow(new Object[]{rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6)});
			  }
			  
			  tab_fact.setModel(def_t);
			
       for(int i = 0; i< tab_fact.getRowCount();i++){
       	 changeSizeh(25,tab_fact,i);
       }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  
			
		}
	}).start();
	
	
	}
	
	void recherche(String matricule){
		 def_t = new DefaultTableModel();
      def_t.setColumnIdentifiers(t_tab_b);
		
		try {
			String req = "select NOM, NRC, NIF, ADRESSE , TEL, CODE_F from TRANS_FOURNISSEUR where CODE_F like '"+matricule+"' order by NOM";

			   ResultSet rst = FirstPg.con.createStatement().executeQuery(req);
			  
			  while(rst.next()){
             	  if(!rst.getString(6).equals("Inconnu"))def_t.addRow(new Object[]{rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6)});
			  }
			  
			  tab_fact.setModel(def_t);
		
for(int i = 0; i< tab_fact.getRowCount();i++){
	 changeSizeh(25,tab_fact,i);
}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	void code_gen(String codes){

		

		try {
			
			java.sql.Statement  stm = FirstPg.con.createStatement();
			
			     String req ="select * from TRANS_FOURNISSEUR where code_f like '"+codes+"' ";
				 ResultSet rst = stm.executeQuery(req);
					boolean bl = false;	
					while(rst.next()){
						bl = true;
					}
			
						if(bl){
							btnAjouter.setText("Modifier");
							btnSupprimer.setEnabled(true);
						}
						else {
							btnAjouter.setText("Ajouter");
							btnSupprimer.setEnabled(false);
						}
						
		} catch (SQLException e1) {
	    	  JOptionPane.showMessageDialog(null, "Erreur "+e1,"erreur",JOptionPane.ERROR_MESSAGE);
		}

	}
	
	
	
	
	//---------------------------------------	
	
	public void changeSize(int width, int height, JTable tableau, int ncol){
		//On crée un objet TableColumn afin de travailler sur notre colonne
		TableColumn col;
		for(int i = 0; i < tableau.getColumnCount(); i++){
			if(i == ncol){
				//On récupère le modèle de la colonne
				col = tableau.getColumnModel().getColumn(i);
				//On lui affecte la nouvelle valeur
				col.setPreferredWidth(width);
			}
		}				
		for(int i = 0; i < tableau.getRowCount(); i++){
			//On affecte la taille de la ligne à l'indice spécifié !
			if(i == ncol)
				tableau.setRowHeight(i, height);
		}

	}
	
//---------------------------------------		
	
	public void changeSizeh(int height, JTable tableau, int ncol){
		//On crée un objet TableColumn afin de travailler sur notre colonne
		TableColumn col;
		for(int i = 0; i < tableau.getColumnCount(); i++){
			if(i == ncol){
				//On récupère le modèle de la colonne
				col = tableau.getColumnModel().getColumn(i);
			}
		}				
		for(int i = 0; i < tableau.getRowCount(); i++){
			//On affecte la taille de la ligne à l'indice spécifié !
			if(i == ncol)
				tableau.setRowHeight(i, height);
		}

	}
}
