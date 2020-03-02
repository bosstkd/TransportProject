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
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;

import fct.DataBase;


public class Vehicule extends JInternalFrame {
	private JTextField tfMarque;
	private JTextField tfMatricule;
	JButton btnAjouter = new JButton("Ajouter");
	JButton btnSupprimer = new JButton("Supprimer");
    JComboBox cbType = new JComboBox();
	JCheckBox ch_Rech = new JCheckBox("Recherche par Matriculation");
	JCheckBox chckbxVisible = new JCheckBox("Visible");
	
	JTable tab_fact;
	DefaultTableModel def_t;
	Object [][] d_tab= new Object[20][7];
    String[] t_tab_b = { "Immatriculation","Type","Marque" };
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vehicule frame = new Vehicule();
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
	public Vehicule() {
		setToolTipText("Configuration Fournisseurs");
		getContentPane().setFont(new Font("Century Gothic", Font.ITALIC, 14));
		setTitle("Vehicules");
		setFrameIcon(new ImageIcon(Vehicule.class.getResource("/icones/original/A32/vehicule_RS.jpg")));
		setClosable(true);
		setIconifiable(true);
		setBounds(100, 40, 356, 425);
		getContentPane().setLayout(null);
		
		JLabel lblNom = new JLabel("Type");
		lblNom.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNom.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		lblNom.setBounds(0, 86, 79, 27);
		getContentPane().add(lblNom);
		
		JLabel lblNrc = new JLabel("Marque");
		lblNrc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNrc.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		lblNrc.setBounds(10, 50, 69, 27);
		getContentPane().add(lblNrc);
		
		JLabel lblTel = new JLabel("Matricule");
		lblTel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTel.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		lblTel.setBounds(10, 11, 69, 27);
		getContentPane().add(lblTel);
		
		tfMarque = new JTextField();
		tfMarque.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {

			}
		});
		
		tfMarque.setFont(new Font("Century Gothic", Font.ITALIC, 12));
		tfMarque.setColumns(10);
		tfMarque.setBounds(109, 51, 148, 27);
		getContentPane().add(tfMarque);
		
		tfMatricule = new JTextField();
		tfMatricule.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				code_gen(tfMatricule.getText());
				if(ch_Rech.isSelected()) recherche(tfMatricule.getText());
			}
		});
		tfMatricule.setHorizontalAlignment(SwingConstants.CENTER);
	
		tfMatricule.setFont(new Font("Century Gothic", Font.ITALIC, 12));
		tfMatricule.setColumns(10);
		tfMatricule.setBounds(109, 12, 217, 27);
		getContentPane().add(tfMatricule);
		
		
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(  tfMarque.getText().replaceAll("'", "").length()<3 || tfMatricule.getText().replaceAll("'", "").length()<6 ){
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
								 	 "Update TRANS_VEHICULE set TYPE = '"+cbType.getSelectedItem()+"', MARQUE = '"+tfMarque.getText()+"', visible = "+x+" where MATRICULE like '"+tfMatricule.getText()+"'; " +
								 	 "end;";
						
								CallableStatement  cstm = FirstPg.con.prepareCall(req);
							    cstm.executeQuery();
								btnAjouter.setText("Modifier");
								btnSupprimer.setEnabled(true);
						}else{
							String req = "Declare " +
									 
								 	 "begin " +
								 	 "insert into TRANS_VEHICULE (MATRICULE, TYPE, MARQUE, visible) values ('"+tfMatricule.getText()+"','"+cbType.getSelectedItem()+"','"+tfMarque.getText()+"', "+x+"); " +
								 	 "end;";
						
								CallableStatement  cstm = FirstPg.con.prepareCall(req);
							    cstm.executeQuery();
								btnAjouter.setText("Modifier");
								btnSupprimer.setEnabled(true);
						}
								
								
								
						  JOptionPane.showMessageDialog(null, "Insertion effectuer avec succès.","Information",JOptionPane.INFORMATION_MESSAGE);

						 String req = "select MATRICULE, TYPE, MARQUE from TRANS_VEHICULE order by MARQUE";
						  
						   ResultSet rst = FirstPg.con.createStatement().executeQuery(req);
						  
						  while(rst.next()){
			             	  def_t.addRow(new Object[]{rst.getString(1),rst.getString(2),rst.getString(3)});
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
		
		
		btnAjouter.setFont(new Font("Consolas", Font.PLAIN, 14));
		btnAjouter.setBounds(20, 156, 176, 36);
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
							 	 "delete from TRANS_VEHICULE where MATRICULE like '"+tfMatricule.getText()+"'; " +
							 	 "end;";
					
							CallableStatement  cstm = FirstPg.con.prepareCall(req);
						    cstm.executeQuery();
							btnAjouter.setText("Ajouter");
							btnSupprimer.setEnabled(false);

					  JOptionPane.showMessageDialog(null, "Suppression effectuer avec succès.","Information",JOptionPane.INFORMATION_MESSAGE);

					  req = "select MATRICULE, TYPE, MARQUE from TRANS_VEHICULE order by MARQUE";
					  
					   ResultSet rst = FirstPg.con.createStatement().executeQuery(req);
					  
					  while(rst.next()){
		             	  def_t.addRow(new Object[]{rst.getString(1),rst.getString(2),rst.getString(3)});
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
		
		
		btnSupprimer.setFont(new Font("Consolas", Font.PLAIN, 14));
		btnSupprimer.setBounds(206, 156, 120, 36);
		getContentPane().add(btnSupprimer);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Liste Fournisseurs", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(0, 203, 340, 169);
		getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		
		tab_fact = new JTable(d_tab,t_tab_b);
		tab_fact.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				int row = tab_fact.getSelectedRow();
				
				try {
					tfMarque.setText(""+tab_fact.getValueAt(row, 2));
					tfMatricule.setText(""+tab_fact.getValueAt(row, 0));
					cbType.setSelectedItem(""+tab_fact.getValueAt(row, 1));
					chckbxVisible.setSelected(DataBase.isVisible(tab_fact.getValueAt(row, 0)+"", "TRANS_VEHICULE where MATRICULE like "));
					
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
			}				
		});
		
		
		tab_fact.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		tab_fact.setRowHeight(30);
				
	   JScrollPane scrollPane1 = new JScrollPane(tab_fact);
	   panel.add(scrollPane1);
	   
	   cbType.setFont(new Font("Consolas", Font.PLAIN, 14));
	   cbType.setModel(new DefaultComboBoxModel(new String[] {"Tete semi-remorque", "Camion", "Transport", "Leger"}));
	   cbType.setBounds(109, 88, 217, 27);
	   getContentPane().add(cbType);
	   
	   ch_Rech.setBounds(0, 373, 166, 23);
	   getContentPane().add(ch_Rech);
	   
	   JButton btnActualis = new JButton("Actualis\u00E9");
	   btnActualis.addActionListener(new ActionListener() {
	   	public void actionPerformed(ActionEvent arg0) {
	   		recherche("%");
	   	}
	   });
	   btnActualis.setBounds(168, 373, 89, 23);
	   getContentPane().add(btnActualis);
	   chckbxVisible.setSelected(true);
	   
	 
	   chckbxVisible.setFont(new Font("Tahoma", Font.PLAIN, 14));
	   chckbxVisible.setBounds(20, 120, 166, 23);
	   getContentPane().add(chckbxVisible);

	new Thread(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			  
			 def_t = new DefaultTableModel();
             def_t.setColumnIdentifiers(t_tab_b);
			
			try {
				 String req = "select MATRICULE, TYPE, MARQUE from TRANS_VEHICULE order by MARQUE";
				  
				   ResultSet rst = FirstPg.con.createStatement().executeQuery(req);
				  
				  while(rst.next()){
	             	  def_t.addRow(new Object[]{rst.getString(1),rst.getString(2),rst.getString(3)});
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
	
	
	void code_gen(String codes){

		

		try {
			
			java.sql.Statement  stm = FirstPg.con.createStatement();
			
			     String req ="select * from TRANS_VEHICULE where MATRICULE like '"+codes+"' ";
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
						stm.close();
		} catch (SQLException e1) {
	    	  JOptionPane.showMessageDialog(null, "Erreur "+e1,"erreur",JOptionPane.ERROR_MESSAGE);
		}

	}
	
	void recherche(String matricule){
		 def_t = new DefaultTableModel();
         def_t.setColumnIdentifiers(t_tab_b);
		
		try {
			 String req = "select MATRICULE, TYPE, MARQUE from TRANS_VEHICULE where MATRICULE like '"+matricule+"' order by MARQUE";
			  
			   ResultSet rst = FirstPg.con.createStatement().executeQuery(req);
			  
			  while(rst.next()){
             	  def_t.addRow(new Object[]{rst.getString(1),rst.getString(2),rst.getString(3)});
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
