package interfaces;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JCheckBox;

import fct.DataBase;


public class Remorque extends JInternalFrame {
	private JTextField tfMarque;
	private JTextField tfMatricule;
	JButton btnAjouter = new JButton("Ajouter");
	JButton btnSupprimer = new JButton("Supprimer");
    JComboBox cbType = new JComboBox();
    JSpinner spCapacite = new JSpinner();
    JComboBox cb_Unit = new JComboBox();
	JCheckBox ch_rech = new JCheckBox("Recherche par Matriculation");
	  JCheckBox chckbxVisible = new JCheckBox("Visible");
	JTable tab_fact;
	DefaultTableModel def_t;
	Object [][] d_tab= new Object[20][7];
    String[] t_tab_b = { "Immatriculation","Type","Marque","Capacité","Unité" };
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Remorque frame = new Remorque();
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
	public Remorque() {
		setToolTipText("Configuration Fournisseurs");
		getContentPane().setFont(new Font("Century Gothic", Font.ITALIC, 14));
		setTitle("Remorque");
		setFrameIcon(new ImageIcon(Remorque.class.getResource("/icones/original/A32/vehicule_RS.jpg")));
		setClosable(true);
		setIconifiable(true);
		setBounds(100, 40, 479, 481);
		getContentPane().setLayout(null);
		
		JLabel lblNom = new JLabel("Type");
		lblNom.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNom.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		lblNom.setBounds(71, 88, 79, 27);
		getContentPane().add(lblNom);
		
		JLabel lblNrc = new JLabel("Marque");
		lblNrc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNrc.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		lblNrc.setBounds(81, 50, 69, 27);
		getContentPane().add(lblNrc);
		
		JLabel lblTel = new JLabel("Matricule");
		lblTel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTel.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		lblTel.setBounds(81, 11, 69, 27);
		getContentPane().add(lblTel);
		
		tfMarque = new JTextField();
		tfMarque.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {

			}
		});
		
		tfMarque.setFont(new Font("Century Gothic", Font.ITALIC, 12));
		tfMarque.setColumns(10);
		tfMarque.setBounds(180, 51, 148, 27);
		getContentPane().add(tfMarque);
		
		tfMatricule = new JTextField();
		tfMatricule.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				code_gen(tfMatricule.getText());
				if(ch_rech.isSelected()) recherche(tfMatricule.getText());
			}
		});
		tfMatricule.setHorizontalAlignment(SwingConstants.CENTER);
	
		tfMatricule.setFont(new Font("Century Gothic", Font.ITALIC, 12));
		tfMatricule.setColumns(10);
		tfMatricule.setBounds(180, 12, 217, 27);
		getContentPane().add(tfMatricule);
		
		
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if( tfMarque.getText().replaceAll("'", "").length()<3 || tfMatricule.getText().replaceAll("'", "").length()<6 ){
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
								 	 "Update TRANS_REMORQUE set TYPE = '"+cbType.getSelectedItem()+"', MARQUE = '"+tfMarque.getText()+"', CAPACITE = "+spCapacite.getValue()+", UNITQTE = '"+cb_Unit.getSelectedItem()+"', visible = "+x+" where MATRICULE like '"+tfMatricule.getText()+"'; " +
								 	 "end;";
						
								CallableStatement  cstm = FirstPg.con.prepareCall(req);
							    cstm.executeQuery();
								btnAjouter.setText("Modifier");
								btnSupprimer.setEnabled(true);
						}else{
							String req = "Declare " +
									 
								 	 "begin " +
								 	 "insert into TRANS_REMORQUE (MATRICULE, TYPE, MARQUE, CAPACITE, UNITQTE, Visible) values ('"+tfMatricule.getText()+"','"+cbType.getSelectedItem()+"','"+tfMarque.getText()+"', "+spCapacite.getValue()+", '"+cb_Unit.getSelectedItem()+"',"+x+"); " +
								 	 "end;";
						
								CallableStatement  cstm = FirstPg.con.prepareCall(req);
							    cstm.executeQuery();
								btnAjouter.setText("Modifier");
								btnSupprimer.setEnabled(true);
						}
								
								
								
						  JOptionPane.showMessageDialog(null, "Insertion effectuer avec succès.","Information",JOptionPane.INFORMATION_MESSAGE);

						 String req = "select MATRICULE, TYPE, MARQUE, CAPACITE, UNITQTE from TRANS_REMORQUE order by MARQUE";
						  
						   ResultSet rst = FirstPg.con.createStatement().executeQuery(req);
						  
						  while(rst.next()){
			             	  def_t.addRow(new Object[]{rst.getString(1),rst.getString(2),rst.getString(3), rst.getInt(4), rst.getString(5)});
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
		btnAjouter.setBounds(92, 202, 176, 36);
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
							 	 "delete from TRANS_REMORQUE where MATRICULE like '"+tfMatricule.getText()+"'; " +
							 	 "end;";
					
							CallableStatement  cstm = FirstPg.con.prepareCall(req);
						    cstm.executeQuery();
							btnAjouter.setText("Ajouter");
							btnSupprimer.setEnabled(false);

					  JOptionPane.showMessageDialog(null, "Suppression effectuer avec succès.","Information",JOptionPane.INFORMATION_MESSAGE);

						  req = "select MATRICULE, TYPE, MARQUE, CAPACITE, UNITQTE from TRANS_REMORQUE order by MARQUE";
					  
					   ResultSet rst = FirstPg.con.createStatement().executeQuery(req);
					  
					  while(rst.next()){
		             	  def_t.addRow(new Object[]{rst.getString(1),rst.getString(2),rst.getString(3), rst.getInt(4), rst.getString(5)});
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
		btnSupprimer.setBounds(278, 202, 120, 36);
		getContentPane().add(btnSupprimer);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Liste Fournisseurs", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(0, 249, 463, 169);
		getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		
		tab_fact = new JTable(d_tab,t_tab_b);
		tab_fact.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				int row = tab_fact.getSelectedRow();
				
				tfMarque.setText(""+tab_fact.getValueAt(row, 2));
				tfMatricule.setText(""+tab_fact.getValueAt(row, 0));
				cbType.setSelectedItem(""+tab_fact.getValueAt(row, 1));
				cb_Unit.setSelectedItem(""+tab_fact.getValueAt(row, 4));
				spCapacite.setValue(fct.nbr.toInt(tab_fact.getValueAt(row, 3)+""));
				chckbxVisible.setSelected(DataBase.isVisible(tab_fact.getValueAt(row, 0)+"", "TRANS_REMORQUE where MATRICULE like "));

			}				
		});
		
		
		tab_fact.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		tab_fact.setRowHeight(30);
				
	   JScrollPane scrollPane1 = new JScrollPane(tab_fact);
	   panel.add(scrollPane1);
	   
	   cbType.setFont(new Font("Consolas", Font.PLAIN, 14));
	   cbType.setModel(new DefaultComboBoxModel(new String[] {"Benne", "Siterne", "Porte char", "Autres"}));
	   cbType.setBounds(180, 90, 148, 27);
	   getContentPane().add(cbType);
	   
	   JLabel lblCapacit = new JLabel("Capacit\u00E9");
	   lblCapacit.setHorizontalAlignment(SwingConstants.RIGHT);
	   lblCapacit.setFont(new Font("Century Gothic", Font.ITALIC, 14));
	   lblCapacit.setBounds(81, 126, 69, 27);
	   getContentPane().add(lblCapacit);
	   
	  
	   spCapacite.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
	   spCapacite.setFont(new Font("Consolas", Font.PLAIN, 14));
	   spCapacite.setBounds(180, 127, 69, 26);
	   getContentPane().add(spCapacite);
	   
	   JLabel lblUnit = new JLabel("Unit\u00E9");
	   lblUnit.setHorizontalAlignment(SwingConstants.RIGHT);
	   lblUnit.setFont(new Font("Century Gothic", Font.ITALIC, 14));
	   lblUnit.setBounds(269, 126, 49, 27);
	   getContentPane().add(lblUnit);
	  
	   cb_Unit.setModel(new DefaultComboBoxModel(new String[] {"Tonne", "Kg", "M3", "Litre", "Unit", "Autre"}));
	   cb_Unit.setFont(new Font("Consolas", Font.PLAIN, 14));
	   cb_Unit.setBounds(328, 127, 69, 27);
	   getContentPane().add(cb_Unit);
	   
	   ch_rech.setBounds(0, 421, 166, 23);
	   getContentPane().add(ch_rech);
	   
	   JButton btnActualis = new JButton("Actualis\u00E9");
	   btnActualis.addActionListener(new ActionListener() {
	   	public void actionPerformed(ActionEvent arg0) {
	   		recherche("%");
	   	}
	   });
	   btnActualis.setBounds(178, 421, 89, 23);
	   getContentPane().add(btnActualis);
	   
	 
	   chckbxVisible.setSelected(true);
	   chckbxVisible.setFont(new Font("Tahoma", Font.PLAIN, 14));
	   chckbxVisible.setBounds(92, 172, 166, 23);
	   getContentPane().add(chckbxVisible);

	new Thread(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			  
			 def_t = new DefaultTableModel();
             def_t.setColumnIdentifiers(t_tab_b);
			
			try {
				 String req = "select MATRICULE, TYPE, MARQUE, CAPACITE, UNITQTE from TRANS_REMORQUE order by MARQUE";
				  
				   ResultSet rst = FirstPg.con.createStatement().executeQuery(req);
				  
				  while(rst.next()){
	             	  def_t.addRow(new Object[]{rst.getString(1),rst.getString(2),rst.getString(3), rst.getInt(4), rst.getString(5)});
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
			
			     String req ="select * from TRANS_REMORQUE where MATRICULE like '"+codes+"' ";
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
	
	void recherche(String matricule){
		 def_t = new DefaultTableModel();
        def_t.setColumnIdentifiers(t_tab_b);
		
		try {
			 String req = "select MATRICULE, TYPE, MARQUE, CAPACITE, UNITQTE from TRANS_REMORQUE where MATRICULE like '"+matricule+"' order by MARQUE";

			   ResultSet rst = FirstPg.con.createStatement().executeQuery(req);
			  
			  while(rst.next()){
             	  def_t.addRow(new Object[]{rst.getString(1),rst.getString(2),rst.getString(3), rst.getInt(4), rst.getString(5)});
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
