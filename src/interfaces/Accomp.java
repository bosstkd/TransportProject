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
import fct.str;


public class Accomp extends JInternalFrame {
	private JTextField tf_nom;
	private JTextField tfPoste;
	private JTextField tfTel;
	JLabel lb_code = new JLabel("");
	JButton btnAjouter = new JButton("Ajouter");
	JButton btnSupprimer = new JButton("Supprimer");
	JTextArea taAdresse = new JTextArea();
	JCheckBox ch_Rech = new JCheckBox("Recherche par Nom");

	
	
	JTable tab_fact;
	DefaultTableModel def_t;
	Object [][] d_tab= new Object[20][7];
    String[] t_tab_b = { "Nom","ID","TEL","Poste"};
	JCheckBox chckbxVisible = new JCheckBox("Visible");

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Accomp frame = new Accomp();
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
	public Accomp() {
		setToolTipText("Configuration Accompagnateur");
		getContentPane().setFont(new Font("Century Gothic", Font.ITALIC, 14));
		setTitle("Accompagnateurs");
		setFrameIcon(new ImageIcon(Accomp.class.getResource("/icones/original/A32/accomp_RS.jpg")));
		setClosable(true);
		setIconifiable(true);
		setBounds(100, 20, 600, 488);
		getContentPane().setLayout(null);
		
		JLabel lblNom = new JLabel("Nom");
		lblNom.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		lblNom.setBounds(10, 21, 52, 27);
		getContentPane().add(lblNom);
		
		JLabel lblNrc = new JLabel("Poste");
		lblNrc.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		lblNrc.setBounds(10, 59, 82, 27);
		getContentPane().add(lblNrc);
		
		JLabel lblAdresse = new JLabel("Adresse");
		lblAdresse.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		lblAdresse.setBounds(450, 11, 101, 27);
		getContentPane().add(lblAdresse);
		
		JLabel lblTel = new JLabel("TEL");
		lblTel.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		lblTel.setBounds(10, 97, 41, 27);
		getContentPane().add(lblTel);
		
		tf_nom = new JTextField();
		tf_nom.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				lb_code.setText(fct.codification.cd_prs(tf_nom.getText().replaceAll("'", "")));
				client_exist(lb_code.getText());
				if(ch_Rech.isSelected()) recherche(lb_code.getText());
			}
		});
		
		tf_nom.setFont(new Font("Century Gothic", Font.ITALIC, 12));
		tf_nom.setBounds(61, 23, 274, 25);
		getContentPane().add(tf_nom);
		tf_nom.setColumns(10);
		
		tfPoste = new JTextField();
		
		
		tfPoste.setFont(new Font("Century Gothic", Font.ITALIC, 12));
		tfPoste.setColumns(10);
		tfPoste.setBounds(106, 60, 229, 27);
		getContentPane().add(tfPoste);
		
		tfTel = new JTextField();
		tfTel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String dtj = "";
				try{
					int i = Integer.parseInt(tfTel.getText().toString());
				}catch(Exception exp){
					try{
						dtj = tfTel.getText();
						tfTel.setText(dtj.substring(0,tfTel.getText().toString().length()-1));
					}catch(Exception e1){}
				}
			}
		});
		tfTel.setFont(new Font("Century Gothic", Font.ITALIC, 12));
		tfTel.setColumns(10);
		tfTel.setBounds(106, 98, 229, 27);
		getContentPane().add(tfTel);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(369, 11, 9, 142);
		getContentPane().add(separator);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(380, 38, 194, 106);
		getContentPane().add(scrollPane);
		
		
		scrollPane.setViewportView(taAdresse);
		taAdresse.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		
		
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(!str.isName(tf_nom.getText())||tf_nom.getText().replaceAll("'", "").length()<4 || tfPoste.getText().replaceAll("'", "").length()<6 ){
			    	  JOptionPane.showMessageDialog(null, "veuillez remplir les informations svp !!","Attention",JOptionPane.WARNING_MESSAGE);
				}else{
					
					 def_t = new DefaultTableModel();
		               def_t.setColumnIdentifiers(t_tab_b);
					
					try {
						int s_etat = 0;
						
						String str_nom = tf_nom.getText();
						str_nom = fct.str.nameForm(str_nom);
						tf_nom.setText(str_nom);
						
			
						int x = 1;
						if(chckbxVisible.isSelected()) x = 1;
						else x = 0;
							  
if(btnAjouter.getText().equals("Modifier")){
	
	
	String req = "Declare " +
			 
								 	 "begin " +
								 	  "update TRANS_ACCOMP set NOM = '"+tf_nom.getText().replaceAll("'", "")+"', TEL = '"+tfTel.getText()+"', ADRESSE = '"+taAdresse.getText()+"', POSTE = '"+tfPoste.getText()+"', visible = "+x+"  where ID like '"+lb_code.getText()+"'; " +
								 	 "end;";

								CallableStatement  cstm = FirstPg.con.prepareCall(req);
							    cstm.executeQuery();
							    cstm.close();
}else{
	String req = "Declare " +
			 
								 	 "begin " +
								 	 "delete from TRANS_ACCOMP where ID like '"+lb_code.getText()+"'; " +
								 	 "insert into TRANS_ACCOMP (NOM, POSTE, TEL, ADRESSE, ID, visible) values ('"+tf_nom.getText().replaceAll("'", "")+"','"+tfPoste.getText()+"','"+tfTel.getText()+"','"+taAdresse.getText()+"', '"+lb_code.getText()+"', "+x+"); " +
								 	 "end;";

								CallableStatement  cstm = FirstPg.con.prepareCall(req);
							    cstm.executeQuery();
							    cstm.close();
	btnAjouter.setText("Modifier");
	btnSupprimer.setEnabled(true);
}
							    
								
								
								
						  JOptionPane.showMessageDialog(null, "Insertion effectuer avec succès.","Information",JOptionPane.INFORMATION_MESSAGE);

						  String req = "select NOM, ID, TEL, POSTE from TRANS_ACCOMP order by NOM";
						  
						  ResultSet rst = FirstPg.con.createStatement().executeQuery(req);
						  
						  while(rst.next()){
				             	 def_t.addRow(new Object[]{rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4)});					  
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
		btnAjouter.setBounds(239, 164, 194, 36);
		getContentPane().add(btnAjouter);
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int conf = JOptionPane.showConfirmDialog(null, "Est-vous sur de vouloir supprimer cet Accompagnateur !!?", "confirmation de suppression",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if(conf == JOptionPane.OK_OPTION){
					
					try {
					 def_t = new DefaultTableModel();
		               def_t.setColumnIdentifiers(t_tab_b);
					
						
						String req  = "";
						Statement stm = FirstPg.con.createStatement();
						
						req = "Declare " +
							 	 "begin " +
							 	 "delete from TRANS_ACCOMP where ID like '"+lb_code.getText()+"'; " +
							 	 "end;";
					
							CallableStatement  cstm = FirstPg.con.prepareCall(req);
						    cstm.executeQuery();
						    cstm.close();
							btnAjouter.setText("Ajouter");
							btnSupprimer.setEnabled(false);
							
					  JOptionPane.showMessageDialog(null, "Suppression effectuer avec succès.","Information",JOptionPane.INFORMATION_MESSAGE);

					  req = "select NOM, ID, TEL, POSTE from TRANS_ACCOMP order by NOM";
					  
					  ResultSet rst = FirstPg.con.createStatement().executeQuery(req);
					  
					  while(rst.next()){
			             	 def_t.addRow(new Object[]{rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4)});					  
					  }
					  
					  tab_fact.setModel(def_t);
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
		btnSupprimer.setBounds(438, 164, 124, 36);
		getContentPane().add(btnSupprimer);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 155, 564, 15);
		getContentPane().add(separator_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 211, 564, 208);
		getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		
		tab_fact = new JTable(d_tab,t_tab_b);
		tab_fact.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				int row = tab_fact.getSelectedRow();
				
				tf_nom.setText(""+tab_fact.getValueAt(row, 0));
				tfPoste.setText(""+tab_fact.getValueAt(row, 3));
				tfTel.setText(""+tab_fact.getValueAt(row, 2));
				
				String req = "select ADRESSE from TRANS_ACCOMP where ID like '"+tab_fact.getValueAt(row, 1)+"' ";
				
				try {
					Statement stm = FirstPg.con.createStatement();
					ResultSet rst = stm.executeQuery(req);
				
					
					while(rst.next()){
						taAdresse.setText(rst.getString(1));
					}
				
					client_exist(lb_code.getText());
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				chckbxVisible.setSelected(DataBase.isVisible(tab_fact.getValueAt(row, 1)+"", "TRANS_ACCOMP where ID like "));

				
 
			}				
		});
		
		
		tab_fact.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		tab_fact.setRowHeight(30);
				
	   JScrollPane scrollPane1 = new JScrollPane(tab_fact);
	   panel.add(scrollPane1);
		
		JLabel lblCodeF = new JLabel("ID");
		lblCodeF.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblCodeF.setBounds(10, 163, 27, 27);
		getContentPane().add(lblCodeF);
		
		
		lb_code.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lb_code.setBounds(61, 163, 150, 27);
		getContentPane().add(lb_code);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setBounds(222, 155, 9, 48);
		getContentPane().add(separator_2);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(10, 203, 213, 15);
		getContentPane().add(separator_3);
		
		
		ch_Rech.setBounds(10, 425, 158, 23);
		getContentPane().add(ch_Rech);
		
		JButton btnActualis = new JButton("Actualis\u00E9");
		btnActualis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				recherche("%");
			}
		});
		btnActualis.setBounds(174, 425, 89, 23);
		getContentPane().add(btnActualis);
		
		chckbxVisible.setSelected(true);
		chckbxVisible.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxVisible.setBounds(10, 130, 115, 23);
		getContentPane().add(chckbxVisible);

	new Thread(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			  
			 def_t = new DefaultTableModel();
             def_t.setColumnIdentifiers(t_tab_b);
			
			try {
				String req = "select NOM, ID, TEL, POSTE from TRANS_ACCOMP order by NOM";
				  
				  ResultSet rst = FirstPg.con.createStatement().executeQuery(req);
				  
				  while(rst.next()){
		             	 def_t.addRow(new Object[]{rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4)});					  
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
			String req = "select NOM, ID, TEL, POSTE from TRANS_ACCOMP where ID like '"+matricule+"' order by NOM";
			   ResultSet rst = FirstPg.con.createStatement().executeQuery(req);
			  
			   while(rst.next()){
	             	 def_t.addRow(new Object[]{rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4)});					  
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
			     String req ="select * from TRANS_ACCOMP where ID like '"+code_c+"' and  ID not LIKE 'Inconnu' ";
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
