package interfaces;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import fct.Message;

import java.awt.Toolkit;


public class Users extends JFrame {

	private JPanel contentPane;
	private JTextField tf_id;
	private JPasswordField tf_psw_0;
	private JPasswordField tf_psw_1;
	private JTextField tf_nom;
	JButton btnValider = new JButton("Valider");

	
	JComboBox cb_poste = new JComboBox();

	public Users() {
		//setClosable(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Users.class.getResource("/icones/original/A32/utilisateur-icone-9647-128_RS.png")));
		setTitle("Users");
		setResizable(false);
		setBounds(100, 100, 269, 282);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblId = new JLabel("ID");
		lblId.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblId.setBounds(24, 33, 46, 23);
		contentPane.add(lblId);
		
		JLabel lblMotDePasse = new JLabel("Taper le Psw");
		lblMotDePasse.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblMotDePasse.setBounds(24, 103, 106, 23);
		contentPane.add(lblMotDePasse);
		
		JLabel lblRetaperLePsw = new JLabel("Retaper le Psw");
		lblRetaperLePsw.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblRetaperLePsw.setBounds(24, 137, 106, 23);
		contentPane.add(lblRetaperLePsw);
		
		JLabel lblPoste = new JLabel("Poste");
		lblPoste.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblPoste.setBounds(24, 174, 46, 14);
		contentPane.add(lblPoste);
		
		tf_id = new JTextField();
		tf_id.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				
				try {
			          Statement statement = FirstPg.con.createStatement();
			          String req = "select ID,NOM,PSW,ADMIN from TRANS_USER where id like '"+tf_id.getText()+"' ";
			          ResultSet rst = statement.executeQuery(req);
			          String bl = "";
			          while(rst.next()){
			        	  bl = "ok";
			        	  tf_id.setText(rst.getString(1));
			        	  tf_nom.setText(rst.getString(2));
			        	  tf_psw_0.setText(rst.getString(3));
			        	  tf_psw_1.setText(rst.getString(3));
			        	  cb_poste.setSelectedItem(rst.getString(4));
			          }
			          
			          if(bl.equals("ok") ){
			        	  btnValider.setText("Modifier");
			          }else{
			        	  btnValider.setText("Valider");
			          }

		          } catch (SQLException sqle) {
			    	  JOptionPane.showMessageDialog(null, "Erreur sql "+sqle,"erreur",JOptionPane.ERROR_MESSAGE);
			      }
				
			}
		});
		tf_id.setFont(new Font("Verdana", Font.PLAIN, 12));
		tf_id.setBounds(169, 33, 67, 26);
		contentPane.add(tf_id);
		tf_id.setColumns(10);
		
		tf_psw_0 = new JPasswordField();
		tf_psw_0.setFont(new Font("Verdana", Font.PLAIN, 12));
		tf_psw_0.setBounds(130, 103, 106, 26);
		contentPane.add(tf_psw_0);
		
		tf_psw_1 = new JPasswordField();
		tf_psw_1.setFont(new Font("Verdana", Font.PLAIN, 12));
		tf_psw_1.setBounds(130, 135, 106, 26);
		contentPane.add(tf_psw_1);
		cb_poste.setFont(new Font("Verdana", Font.PLAIN, 12));
		cb_poste.setModel(new DefaultComboBoxModel(new String[] {"admin", "op\u00E9rateur"}));
		
		
		cb_poste.setBounds(130, 171, 106, 23);
		contentPane.add(cb_poste);
		
		JLabel lblFaitEntreLes = new JLabel("Fait entr\u00E9e les informations");
		lblFaitEntreLes.setFont(new Font("Verdana", Font.BOLD, 12));
		lblFaitEntreLes.setBounds(24, 0, 200, 22);
		contentPane.add(lblFaitEntreLes);
		
		JLabel lblNomEtPrnm = new JLabel("Nom et Pr\u00E9nm");
		lblNomEtPrnm.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblNomEtPrnm.setBounds(24, 67, 106, 25);
		contentPane.add(lblNomEtPrnm);
		
		tf_nom = new JTextField();
		tf_nom.setFont(new Font("Verdana", Font.PLAIN, 12));
		tf_nom.setBounds(130, 72, 106, 26);
		contentPane.add(tf_nom);
		tf_nom.setColumns(10);
		
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(tf_id.getText().equals("")||tf_nom.getText().equals("")||tf_psw_0.getText().equals("")||tf_psw_1.getText().equals("")){
			    	  JOptionPane.showMessageDialog(null, "veuillez remplir tout les champs SVP !!","erreur",JOptionPane.WARNING_MESSAGE);
				}else if(! tf_psw_0.getText().equals(tf_psw_1.getText())){
			    	  JOptionPane.showMessageDialog(null, "veuillez vérifier le mot de passe SVP !!","erreur",JOptionPane.WARNING_MESSAGE);
				}else{
					
					String msg = "";
				try {
				
				      
			          	Statement statement = FirstPg.con.createStatement();
			          String req = "";
			        	if(btnValider.getText().equals("Valider")){
							req = "insert into TRANS_USER(ID ,NOM ,  PSW ,  ADMIN ) VALUES('"+tf_id.getText()+"','"+tf_nom.getText()+"','"+tf_psw_0.getText()+"','"+cb_poste.getSelectedItem()+"')";
						    msg = "insertion effectuer avec succée";
			        	}else{
			        		if(tf_id.getText().equals("admin"))cb_poste.setSelectedItem("admin");
							req = "update TRANS_USER set PSW = '"+tf_psw_0.getText()+"', NOM = '"+tf_nom.getText()+"',  ADMIN = '"+cb_poste.getSelectedItem()+"' where ID = '"+tf_id.getText()+"' ";
							msg = "modification effectuer avec succée";

			        	}
			        	
					     statement.executeUpdate(req);
					     if(cb_poste.getSelectedIndex() == 0 && btnValider.getText().equals("Modifier")){
					    	 req = "Delete from TRANS_ACCES where ID like '"+tf_id.getText()+"'";
						     statement.executeUpdate(req);
					     }
					     
					     if(cb_poste.getSelectedIndex()==1 && btnValider.getText().equals("Valider")){
								req = "insert into TRANS_ACCES(ID  , INF_ENTREPRISE  , INF_USER  , MISSION  , COMMANDE  , FACTURATION  , CALENDRIER  , TBOARD  , SUIVI  , INSERTION) VALUES('"+tf_id.getText()+"','false','false','false','false','false','false','false','false','false')";
								statement.executeUpdate(req);
					     }
					     
				   JOptionPane.showMessageDialog(null, msg,"information",JOptionPane.INFORMATION_MESSAGE);
				   tf_id.setText("");
				   tf_nom.setText("");
				   tf_psw_0.setText("");
				   tf_psw_1.setText("");
				   cb_poste.setSelectedIndex(0);
				


			      } catch (SQLException sqle) {
			    	  JOptionPane.showMessageDialog(null, "Erreur sql "+sqle,"erreur",JOptionPane.ERROR_MESSAGE);
			      }
				
				}
				

			}
		});
		btnValider.setFont(new Font("Verdana", Font.PLAIN, 12));
		btnValider.setBounds(24, 212, 106, 34);
		contentPane.add(btnValider);
		
		JButton btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(tf_id.getText().equals("admin")){
					new Message(2, "Le compte admin ne peut pas etre supprimer.\nVous pouvez juste changé le mot de passe.");
				}else{
				 try {
			      Statement statement = FirstPg.con.createStatement();
					  String  req = "Delete from TRANS_ACCES where ID like '"+tf_id.getText()+"'";
							  statement.executeUpdate(req);
					     
							  req = "Delete from TRANS_USER where ID like '"+tf_id.getText()+"'";
						      statement.executeUpdate(req);

				   JOptionPane.showMessageDialog(null, "Suppression effectuer avec succées ","information",JOptionPane.INFORMATION_MESSAGE);

				   tf_id.setText("");
				   tf_nom.setText("");
				   tf_psw_0.setText("");
				   tf_psw_1.setText("");
				   cb_poste.setSelectedIndex(0);
				   statement.close();
				   
			       } catch (SQLException sqle) {
			    	  JOptionPane.showMessageDialog(null, "Erreur sql "+sqle,"erreur",JOptionPane.ERROR_MESSAGE);
			      }
				}
				
				
			}
		});
		btnSupprimer.setFont(new Font("Verdana", Font.PLAIN, 12));
		btnSupprimer.setBounds(130, 212, 106, 34);
		contentPane.add(btnSupprimer);
	}
}
