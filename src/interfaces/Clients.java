package interfaces;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JCheckBox;

import fct.DataBase;
import fct.codification;


public class Clients extends JInternalFrame {
	private JTextField tf_fournisseur;
	private JTextField tf_nrc;
	private JTextField tf_nif;
	private JTextField tf_tel;
	JLabel lb_code = new JLabel("");
	JButton btnAjouter = new JButton("Ajouter");
	JButton btnSupprimer = new JButton("Supprimer");
	JTextArea ta_adresse = new JTextArea();
	JCheckBox ch_tva = new JCheckBox("Exonor\u00E9 de la TVA.");
	JCheckBox ch_Rech = new JCheckBox("Recherche par Client");
	
	JTable tab_fact;
	DefaultTableModel def_t;
	Object [][] d_tab= new Object[20][7];
    String[] t_tab_b = { "Nom","NRC","NIF","Adresse","Tel","Code_C"};
    private JTextField tf_nai;
    JCheckBox chckbxVisible = new JCheckBox("Visible");
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Clients frame = new Clients();
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
	public Clients() {
		setToolTipText("Configuration Clients");
		getContentPane().setFont(new Font("Century Gothic", Font.ITALIC, 14));
		setTitle("Clients");
		setFrameIcon(new ImageIcon(Clients.class.getResource("/icones/original/A32/client_RS.jpg")));
		setClosable(true);
		setIconifiable(true);
		setBounds(100, 20, 600, 545);
		getContentPane().setLayout(null);
		
		JLabel lblNom = new JLabel("Client");
		lblNom.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		lblNom.setBounds(34, 20, 52, 27);
		getContentPane().add(lblNom);
		
		JLabel lblNrc = new JLabel("NRC");
		lblNrc.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		lblNrc.setBounds(34, 58, 52, 27);
		getContentPane().add(lblNrc);
		
		JLabel lblNif = new JLabel("NIF");
		lblNif.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		lblNif.setBounds(222, 59, 41, 27);
		getContentPane().add(lblNif);
		
		JLabel lblAdresse = new JLabel("Adresse");
		lblAdresse.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		lblAdresse.setBounds(450, 11, 101, 27);
		getContentPane().add(lblAdresse);
		
		JLabel lblTel = new JLabel("TEL");
		lblTel.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		lblTel.setBounds(34, 134, 41, 27);
		getContentPane().add(lblTel);
		
		tf_fournisseur = new JTextField();
		
		tf_fournisseur.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				lb_code.setText(fct.codification.cd_prs(tf_fournisseur.getText().replaceAll("'", "")));
				client_exist(lb_code.getText());
				if(ch_Rech.isSelected()) recherche(lb_code.getText());
			}
		});
		
		tf_fournisseur.setFont(new Font("Century Gothic", Font.ITALIC, 12));
		tf_fournisseur.setBounds(85, 22, 274, 25);
		getContentPane().add(tf_fournisseur);
		tf_fournisseur.setColumns(10);
		
		tf_nrc = new JTextField();
		
		
		tf_nrc.setFont(new Font("Century Gothic", Font.ITALIC, 12));
		tf_nrc.setColumns(10);
		tf_nrc.setBounds(85, 58, 108, 27);
		getContentPane().add(tf_nrc);
		
		tf_nif = new JTextField();
		
		
		tf_nif.setFont(new Font("Century Gothic", Font.ITALIC, 12));
		tf_nif.setColumns(10);
		tf_nif.setBounds(251, 59, 108, 27);
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
		tf_tel.setBounds(83, 135, 148, 27);
		getContentPane().add(tf_tel);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(369, 11, 9, 182);
		getContentPane().add(separator);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(380, 38, 194, 144);
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
		          
		           /*    
					String str_nom = tf_fournisseur.getText();
					str_nom = fct.str.nameForm(str_nom);
					tf_fournisseur.setText(str_nom);
					*/
					try {
						int s_etat = 0;
						
						if(ch_tva.isSelected())s_etat = 1;
						else s_etat = 0;
						
					
						
					
						
						if(tf_nai.getText().equals(""))tf_nai.setText("_");
						
						int x = 1;
						if(chckbxVisible.isSelected()) x = 1;
						else x = 0;
							  
if(btnAjouter.getText().equals("Modifier")){
	String req = "Declare " +
			 
								 	 "begin " +
								 	  "update TRANS_CLIENT set NOM = '"+tf_fournisseur.getText().replaceAll("'", "")+"', NRC = '"+tf_nrc.getText().replaceAll("'", "")+"', NIF = '"+tf_nif.getText().replaceAll("'", "")+"',NAI = '"+tf_nai.getText()+"',ADRESSE = '"+ta_adresse.getText().replaceAll("'", "")+"', TEL = '"+tf_tel.getText()+"' , EXONORE = '"+s_etat+"', visible = "+x+" where CODE_C like '"+lb_code.getText()+"'; " +
								 	 "end;";

								CallableStatement  cstm = FirstPg.con.prepareCall(req);
							    cstm.executeQuery();
							    cstm.close();
}else{
	String req = "Declare " +
			 
								 	 "begin " +
								 	 "delete from TRANS_CLIENT where CODE_C like '"+lb_code.getText()+"'; " +
								 	 "insert into TRANS_CLIENT (NOM, NRC, NIF,NAI ,ADRESSE, TEL, CODE_C, EXONORE, visible) values ('"+tf_fournisseur.getText().replaceAll("'", "")+"','"+tf_nrc.getText().replaceAll("'", "")+"','"+tf_nif.getText().replaceAll("'", "")+"','"+tf_nai.getText()+"','"+ta_adresse.getText().replaceAll("'", "")+"','"+tf_tel.getText()+"','"+lb_code.getText()+"','"+s_etat+"',"+x+"); " +
								 	 "end;";

								CallableStatement  cstm = FirstPg.con.prepareCall(req);
							    cstm.executeQuery();
							    cstm.close();
	btnAjouter.setText("Modifier");
	btnSupprimer.setEnabled(true);
}
							    
								
								
								
						  JOptionPane.showMessageDialog(null, "Insertion effectuer avec succès.","Information",JOptionPane.INFORMATION_MESSAGE);

						
								String req = "select NOM, NRC, NIF, ADRESSE, TEL, code_c from TRANS_CLIENT where CODE_C not LIKE 'Inconnu' order by NOM";
								ResultSet	rst = FirstPg.con.createStatement().executeQuery(req);  
								
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
		btnAjouter.setBounds(251, 201, 194, 36);
		getContentPane().add(btnAjouter);
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int conf = JOptionPane.showConfirmDialog(null, "Est-vous sur de vouloir supprimer ce Client !!?", "confirmation de suppression",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if(conf == JOptionPane.OK_OPTION){
					
					try {
					 def_t = new DefaultTableModel();
		               def_t.setColumnIdentifiers(t_tab_b);
					
						
						String req  = "";
						Statement stm = FirstPg.con.createStatement();
						
						req = "Declare " +
							 	 "begin " +
							 	 "delete from TRANS_CLIENT where CODE_C like '"+lb_code.getText()+"'; " +
							 	 "end;";
					
							CallableStatement  cstm = FirstPg.con.prepareCall(req);
						    cstm.executeQuery();
						    cstm.close();
							btnAjouter.setText("Ajouter");
							btnSupprimer.setEnabled(false);
							
					  JOptionPane.showMessageDialog(null, "Suppression effectuer avec succès.","Information",JOptionPane.INFORMATION_MESSAGE);

							req = "select NOM, NRC, NIF, ADRESSE, TEL, code_c from TRANS_CLIENT where CODE_C not LIKE 'Inconnu' order by NOM";
							ResultSet	rst = FirstPg.con.createStatement().executeQuery(req);  
							
							while(rst.next()){
				             	 if(!rst.getString(6).equals("Inconnu"))def_t.addRow(new Object[]{rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6)});					  
						  }
					      stm.close();
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
		btnSupprimer.setBounds(450, 201, 124, 36);
		getContentPane().add(btnSupprimer);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 193, 564, 15);
		getContentPane().add(separator_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 267, 564, 208);
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
				
				String req = "select EXONORE, NAI from TRANS_CLIENT where CODE_C like '"+tab_fact.getValueAt(row, 5)+"' ";
				
				try {
					Statement stm = FirstPg.con.createStatement();
					ResultSet rst = stm.executeQuery(req);
					int exo = 0;
					
					while(rst.next()){
						exo = rst.getInt(1);
						tf_nai.setText(rst.getString(2));
					}
					
					if(exo==1)ch_tva.setSelected(true);
					else ch_tva.setSelected(false);
					client_exist(lb_code.getText());
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				chckbxVisible.setSelected(DataBase.isVisible(codification.cd_prs(""+tab_fact.getValueAt(row, 0)), "TRANS_CLIENT where CODE_C like "));

				
 
			}				
		});
		
		
		tab_fact.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		tab_fact.setRowHeight(30);
				
	   JScrollPane scrollPane1 = new JScrollPane(tab_fact);
	   panel.add(scrollPane1);
		
		JLabel lblCodeF = new JLabel("Code C");
		lblCodeF.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblCodeF.setBounds(10, 201, 71, 27);
		getContentPane().add(lblCodeF);
		
		
		lb_code.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lb_code.setBounds(65, 201, 146, 27);
		getContentPane().add(lb_code);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setBounds(222, 193, 9, 48);
		getContentPane().add(separator_2);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(10, 241, 213, 15);
		getContentPane().add(separator_3);
		
		JLabel lblNai = new JLabel("NAI");
		lblNai.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		lblNai.setBounds(34, 97, 52, 27);
		getContentPane().add(lblNai);
		
		tf_nai = new JTextField();
		tf_nai.setFont(new Font("Century Gothic", Font.ITALIC, 12));
		tf_nai.setColumns(10);
		tf_nai.setBounds(85, 97, 108, 27);
		getContentPane().add(tf_nai);
		
	
		ch_tva.setFont(new Font("Tahoma", Font.ITALIC, 11));
		ch_tva.setBounds(237, 135, 122, 27);
		getContentPane().add(ch_tva);
		
		
		ch_Rech.setBounds(10, 482, 137, 23);
		getContentPane().add(ch_Rech);
		
		JButton btnActualis = new JButton("Actualis\u00E9");
		btnActualis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				recherche("%");
			}
		});
		btnActualis.setBounds(174, 482, 89, 23);
		getContentPane().add(btnActualis);
		chckbxVisible.setSelected(true);
		
		
		chckbxVisible.setFont(new Font("Tahoma", Font.ITALIC, 14));
		chckbxVisible.setBounds(34, 166, 122, 27);
		getContentPane().add(chckbxVisible);

	new Thread(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			  
			 def_t = new DefaultTableModel();
             def_t.setColumnIdentifiers(t_tab_b);
			
			try {
				String req = "select NOM, NRC, NIF, ADRESSE, TEL, code_c from TRANS_CLIENT where CODE_C not LIKE 'Inconnu' order by NOM";
				ResultSet	rst = FirstPg.con.createStatement().executeQuery(req);  
				
				while(rst.next()){
	             	 if(!rst.getString(6).equals("Inconnu"))def_t.addRow(new Object[]{rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6)});					  
			  }
			  
			  tab_fact.setModel(def_t);
			
       for(int i = 0; i< tab_fact.getRowCount();i++){
       	 changeSizeh(25,tab_fact,i);
       }
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}
	}).start();
	
	
	}

	void recherche(String matricule){
		 def_t = new DefaultTableModel();
       def_t.setColumnIdentifiers(t_tab_b);
		
		try {
			String req = "select NOM, NRC, NIF, ADRESSE, TEL, code_c from TRANS_CLIENT where CODE_C like '"+matricule+"' order by NOM";

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
	
	
	void client_exist(String code_c){		
		try {
			java.sql.Statement  stm = FirstPg.con.createStatement();
			     String req ="select * from TRANS_CLIENT where CODE_C like '"+code_c+"' and  CODE_C not LIKE 'Inconnu' ";
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
