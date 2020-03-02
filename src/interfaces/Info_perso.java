package interfaces;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Info_perso extends JInternalFrame {
	private JTextField tf_entreprise;
	private JTextField tf_nrc;
	private JTextField tf_nif;
	private JTextField tf_nis;
	private JTextField tf_rib;
	private JTextField tf_tel;
	private JTextField tf_mail;
	private JTextField tf_fax;
	JTextArea ta_adresse = new JTextArea();
	JButton btnInsertion = new JButton("Confirmer");
	JButton btnSupprimer = new JButton("Supprimer");
	JCheckBox ch_tva = new JCheckBox("Non soumis a la TVA");

	
	public final static String IMAGE_TYPE_JPEG = "jpeg";

	public final static String IMAGE_TYPE_GIF = "gif";

	public final static String IMAGE_TYPE_PNG = "png";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Info_perso frame = new Info_perso();
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
	public Info_perso() {
		getContentPane().setFont(new Font("Century Gothic", Font.ITALIC, 13));
		setFrameIcon(new ImageIcon(Info_perso.class.getResource("/icones/original/A32/utilisateur-icone-9647-128_RS.png")));
		setTitle("Informations personnel");
		setClosable(true);
		setIconifiable(true);
		setBounds(100, 100, 372, 464);
		getContentPane().setLayout(null);
		
		JLabel lblNom = new JLabel("Nom Entreprise");
		lblNom.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblNom.setBounds(10, 13, 129, 25);
		getContentPane().add(lblNom);
		
		JLabel lblAdresse = new JLabel("Adresse");
		lblAdresse.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblAdresse.setBounds(53, 59, 98, 25);
		getContentPane().add(lblAdresse);
		
		JLabel lblNrc = new JLabel("NRC");
		lblNrc.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblNrc.setBounds(73, 142, 46, 14);
		getContentPane().add(lblNrc);
		
		JLabel lblNif = new JLabel("NIF");
		lblNif.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblNif.setBounds(73, 179, 46, 14);
		getContentPane().add(lblNif);
		
		JLabel lblNis = new JLabel("NIS");
		lblNis.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblNis.setBounds(73, 216, 46, 14);
		getContentPane().add(lblNis);
		
		JLabel lblRib = new JLabel("RIB");
		lblRib.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblRib.setBounds(73, 252, 46, 14);
		getContentPane().add(lblRib);
		
		JLabel lblTelfax = new JLabel("TEL/FAX");
		lblTelfax.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblTelfax.setBounds(43, 289, 76, 14);
		getContentPane().add(lblTelfax);
		
		JLabel lblMail = new JLabel("Mail");
		lblMail.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblMail.setBounds(73, 325, 46, 14);
		getContentPane().add(lblMail);
		
		tf_entreprise = new JTextField();
	
		tf_entreprise.setFont(new Font("Consolas", Font.PLAIN, 12));
		tf_entreprise.setBounds(118, 11, 226, 30);
		getContentPane().add(tf_entreprise);
		tf_entreprise.setColumns(10);
		
		tf_nrc = new JTextField();
		tf_nrc.setFont(new Font("Consolas", Font.PLAIN, 12));
		tf_nrc.setColumns(10);
		tf_nrc.setBounds(118, 137, 226, 30);
		getContentPane().add(tf_nrc);
		
		tf_nif = new JTextField();
		tf_nif.setFont(new Font("Consolas", Font.PLAIN, 12));
		tf_nif.setColumns(10);
		tf_nif.setBounds(118, 174, 226, 30);
		getContentPane().add(tf_nif);
		
		tf_nis = new JTextField();
		tf_nis.setFont(new Font("Consolas", Font.PLAIN, 12));
		tf_nis.setColumns(10);
		tf_nis.setBounds(118, 211, 226, 30);
		getContentPane().add(tf_nis);
		
		tf_rib = new JTextField();
		tf_rib.setFont(new Font("Consolas", Font.PLAIN, 12));
		tf_rib.setColumns(10);
		tf_rib.setBounds(118, 247, 226, 30);
		getContentPane().add(tf_rib);
		
		tf_tel = new JTextField();
		tf_tel.addKeyListener(new KeyAdapter() {
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
		tf_tel.setFont(new Font("Consolas", Font.PLAIN, 12));
		tf_tel.setColumns(10);
		tf_tel.setBounds(118, 284, 98, 30);
		getContentPane().add(tf_tel);
		
		tf_mail = new JTextField();
		tf_mail.setFont(new Font("Consolas", Font.PLAIN, 12));
		tf_mail.setColumns(10);
		tf_mail.setBounds(118, 320, 226, 30);
		getContentPane().add(tf_mail);
		
		tf_fax = new JTextField();
		tf_fax.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String dtj = "";
				try{
					int i = Integer.parseInt(tf_fax.getText().toString());
				}catch(Exception exp){
					try{
						dtj = tf_fax.getText();
						tf_fax.setText(dtj.substring(0,tf_fax.getText().toString().length()-1));
					}catch(Exception e1){}
				}
			}
		});
		tf_fax.setFont(new Font("Consolas", Font.PLAIN, 12));
		tf_fax.setColumns(10);
		tf_fax.setBounds(246, 284, 98, 30);
		getContentPane().add(tf_fax);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(118, 64, 226, 62);
		getContentPane().add(scrollPane);
		
	
		ta_adresse.setFont(new Font("Consolas", Font.PLAIN, 14));
		scrollPane.setViewportView(ta_adresse);
		
		
		btnInsertion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(tf_entreprise.getText().replaceAll("'", "").length()<4 || tf_nrc.getText().replaceAll("'", "").length()<6 || tf_nif.getText().replaceAll("'", "").length()<6){
			    	  JOptionPane.showMessageDialog(null, "veuillez remplir les informations svp !!","Attention",JOptionPane.WARNING_MESSAGE);
				}else{
					
					
					try {
						
						
						int langueur = tf_nrc.getText().replaceAll("'", "").length();
						
						if(langueur < tf_nis.getText().replaceAll("'", "").length()) langueur = tf_nis.getText().replaceAll("'", "").length();
						if(langueur < tf_nif.getText().replaceAll("'", "").length()) langueur = tf_nif.getText().replaceAll("'", "").length();
						
						String s1 = tf_nrc.getText().replaceAll("'", "");
						String s2 = tf_nis.getText().replaceAll("'", "");
						String s3 = tf_nif.getText().replaceAll("'", "");
						
						for(int i = tf_nrc.getText().replaceAll("'", "").length(); i < langueur; i++) {
							s1 = s1 + "_";
						}
						for(int i = tf_nis.getText().replaceAll("'", "").length(); i < langueur; i++) {
							s2 = s2 + "_";
						}
						for(int i = tf_nif.getText().replaceAll("'", "").length(); i < langueur; i++) {
							s3 = s3 + "_";
						}
						tf_nrc.setText(s1);
						tf_nis.setText(s2);
						tf_nif.setText(s3);
						
						System.out.println(s2);
						
						
						String nn_soumis = "non";
						
						if(ch_tva.isSelected()){
							nn_soumis = "oui";
						}
						
						
						String req = "Declare " +
									 
								 	 "begin " +
								 	 "delete from TRANS_INFO_PERSO; " +
								 	 "insert into TRANS_INFO_PERSO (NOM, ADRESSE, NRC, NIF, NIS, RIB, TEL, FAX, MAIL,NON_SOUMIS_TVA) values ('"+tf_entreprise.getText().replaceAll("'", "")+"','"+ta_adresse.getText()+"','"+tf_nrc.getText().replaceAll("'", "")+"','"+tf_nif.getText().replaceAll("'", "")+"','"+tf_nis.getText().replaceAll("'", "")+"','"+tf_rib.getText().replaceAll("'", "")+"','"+tf_tel.getText()+"','"+tf_fax.getText()+"','"+tf_mail.getText()+"','"+nn_soumis+"'); " +
								 	 "end;";
						
								CallableStatement  cstm = FirstPg.con.prepareCall(req);
							    cstm.executeQuery();
								btnInsertion.setText("Modifier");
								btnSupprimer.setEnabled(true);
								
						
								 int ajim = JOptionPane.showConfirmDialog(null,"Voulez vous (Ajouter/Modifier) le logo !?","Information",JOptionPane.OK_OPTION);

									if(ajim == 0){
										logo(new Dimension(120,80),"C:/JTrans/Logo.jpg",IMAGE_TYPE_JPEG,"jpg");
									}
									
								     ajim = JOptionPane.showConfirmDialog(null,"Voulez vous (Ajouter/Modifier) le pied de page !?","Information",JOptionPane.OK_OPTION);

										if(ajim == 0){
											logo_pied(new Dimension(829,87),"C:/JTrans/pied.jpg",IMAGE_TYPE_JPEG,"jpg");
										}	
								

						  JOptionPane.showMessageDialog(null, "Insertion effectuer avec succès.","Information",JOptionPane.INFORMATION_MESSAGE);

						  
						  
						  
					} catch (SQLException e1) {
				    	  JOptionPane.showMessageDialog(null, "Erreur "+e1,"erreur",JOptionPane.ERROR_MESSAGE);
					}
					
					
				}
				
				
			}
		});
		btnInsertion.setFont(new Font("Consolas", Font.BOLD, 14));
		btnInsertion.setBounds(10, 397, 203, 29);
		getContentPane().add(btnInsertion);
		
		
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				int conf = JOptionPane.showConfirmDialog(null, "Est-vous sur de vouloir supprimer les informations !!?", "confirmation de suppression",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if(conf == JOptionPane.OK_OPTION){
				 try {
					
					String req = "Declare " +
								 
							 	 "begin " +
							 	 "delete from TRANS_INFO_PERSO; " +
							 	 "end;";
					
					CallableStatement  cstm = FirstPg.con.prepareCall(req);
				    cstm.executeQuery();
							btnInsertion.setText("Confirmer");
							btnSupprimer.setEnabled(false);
					  JOptionPane.showMessageDialog(null, "Suppression effectuer avec succès.","Information",JOptionPane.INFORMATION_MESSAGE);

				} catch (SQLException e1) {
			    	  JOptionPane.showMessageDialog(null, "Erreur "+e1,"erreur",JOptionPane.ERROR_MESSAGE);
				}
				}
				
				
				
			}
		});
		btnSupprimer.setFont(new Font("Consolas", Font.BOLD, 14));
		btnSupprimer.setBounds(223, 397, 121, 29);
		getContentPane().add(btnSupprimer);
		
	
		ch_tva.setFont(new Font("Consolas", Font.PLAIN, 12));
		ch_tva.setBounds(64, 360, 175, 30);
		getContentPane().add(ch_tva);

		
		new Thread(new Runnable() {
			
			@Override
			public void run() {


try {
					
					java.sql.Statement  stm = FirstPg.con.createStatement();
					
					     String req ="select * from TRANS_INFO_PERSO";
						 ResultSet rst = stm.executeQuery(req);
							boolean bl = false;	
							
							while(rst.next()){
								bl = true;
								tf_entreprise.setText(rst.getString(1));
								ta_adresse.setText(rst.getString(2));
								tf_nrc.setText(rst.getString(3));
								tf_nif.setText(rst.getString(4));
								tf_nis.setText(rst.getString(5));
								tf_rib.setText(rst.getString(6));
								tf_tel.setText(rst.getString(7));
								tf_fax.setText(rst.getString(8));
								tf_mail.setText(rst.getString(9));
								
								if(rst.getString(10).equals("oui"))
									ch_tva.setSelected(true);
								else 
									ch_tva.setSelected(false);
								
								
							}
							
							
					
								if(bl){
									btnInsertion.setText("Modifier");
									btnSupprimer.setEnabled(true);
								}
								else {
									btnInsertion.setText("Confirmer");
									btnSupprimer.setEnabled(false);
								}
								
				} catch (SQLException e1) {
			    	  JOptionPane.showMessageDialog(null, "Erreur "+e1,"erreur",JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		}).start();
		
	}
	
//--------------------------------------------------------------------------------

	
static BufferedImage buf; 
static BufferedImage buf_2; 
	
public static void logo(Dimension dms, String pictureName, String compressionType, String format) {
    
	
	String ce_trouve = "";
	Dimension dms_2 = new Dimension(309,90);
	 String pictureName_2 = pictureName;
		final JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png");
		
		chooser.setMultiSelectionEnabled(true);
		
		
		chooser.setFileFilter(filter);	
		
		
		int returnVal = chooser.showOpenDialog(chooser);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
						
			
			File[] fs=chooser.getSelectedFiles();
			
			for( int i = 0; i<fs.length; ++i){	
			
		File imgsup = new File("C:/JTrans/Logo."+format); 
	    imgsup.delete();
	    
	    File imgsup_2 = new File("C:/JTrans/logo_fci."+format); 
	    imgsup_2.delete();
	    
	    pictureName = "C:/JTrans/Logo."+format;
	    pictureName_2 = "C:/JTrans/logo_fci."+format;
		
		try {
			buf = ImageIO.read(new File(fs[i].getAbsolutePath()));
			buf_2 = ImageIO.read(new File(fs[i].getAbsolutePath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    BufferedImage bufFinal = null; // Notre capture d'écran redimensionnée
	    BufferedImage bufFinal_2 = null;
	    // Création de la capture finale
	    bufFinal = new BufferedImage(dms.width,dms.height, BufferedImage.TYPE_INT_RGB);
	    bufFinal_2 = new BufferedImage(dms_2.width,dms_2.height, BufferedImage.TYPE_INT_RGB);

	    // Redimensionnement de la capture originale
	    Graphics2D g = (Graphics2D) bufFinal.getGraphics();
	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g.drawImage(buf, 0, 0, dms.width,dms.height, null);
	    g.dispose();
	    
	    
	    Graphics2D g_2 = (Graphics2D) bufFinal_2.getGraphics();
	    g_2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g_2.drawImage(buf_2, 0, 0, dms_2.width,dms_2.height, null);
	    g_2.dispose();

	    // Ecriture de notre capture d'écran redimensionnée
	    try {
	        ImageIO.write(bufFinal, compressionType, new File(pictureName));
	        ImageIO.write(bufFinal_2, compressionType, new File(pictureName_2));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	  //  ce_trouve = ce_trouve+"\n"+pictureName;
	    
	    
			}
		}
		
	}

public static void logo_pied(Dimension dms, String pictureName, String compressionType, String format) {
    
	
	String ce_trouve = "";
		final JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png");
		
		chooser.setMultiSelectionEnabled(true);
		chooser.setFileFilter(filter);	
		
		
		int returnVal = chooser.showOpenDialog(chooser);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
						
			
			File[] fs=chooser.getSelectedFiles();
			
			for( int i = 0; i<fs.length; ++i){	
			
		File imgsup = new File("C:/JTrans/pied."+format); 
	    imgsup.delete();
	    
	    
	    pictureName = "C:/JTrans/pied."+format;
		
		try {
			buf = ImageIO.read(new File(fs[i].getAbsolutePath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    BufferedImage bufFinal = null; // Notre capture d'écran redimensionnée
	    // Création de la capture finale
	    bufFinal = new BufferedImage(dms.width,dms.height, BufferedImage.TYPE_INT_RGB);

	    // Redimensionnement de la capture originale
	    Graphics2D g = (Graphics2D) bufFinal.getGraphics();
	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g.drawImage(buf, 0, 0, dms.width,dms.height, null);
	    g.dispose();
	    

	    // Ecriture de notre capture d'écran redimensionnée
	    try {
	        ImageIO.write(bufFinal, compressionType, new File(pictureName));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    
	    
			}
		}
		
	}
}
