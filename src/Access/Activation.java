package Access;
import interfaces.identificateur;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import fct.DataBase;
import fct.Message;
import fct.XmlFile;
import fct.codification;
import fct.dt;


public class Activation extends JFrame {

	private JPanel contentPane;
	static String path = "C:/JTrans/conf.xml";
	
	
	static boolean Active = false;
	 static JTextField act;
	 
	 static Activation frame;
	 private static JTextField act2;
	 private JTextField act3;
	 private JTextField act4;

	 String[] keys = new String[]{"dataBase","ipAdresse","userDB","pswDB","dtAct","dtActive","dur"};
	 String[] values = new String[]{"x","x","x","x","x","Vv6dbJkIyAoceyGpNxztKw==","x"};
	 

	public Activation() {
		setResizable(false);
		
		
		// XmlFile.CreateWrite(path, keys, values);

		setIconImage(Toolkit.getDefaultToolkit().getImage(Activation.class.getResource("/icones/original/A32/demande-pgp-signature-icone-6670-48_RS.png")));
		setTitle("Activation TransPro One");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 463, 147);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		act = new JTextField();
		act.setHorizontalAlignment(SwingConstants.CENTER);
		act.setFont(new Font("Tahoma", Font.PLAIN, 14));
		act.setBounds(102, 12, 69, 27);
		contentPane.add(act);
		act.setColumns(10);
		

		
		
		JButton btnActiver = new JButton("Fermer");
		btnActiver.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnActiver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnActiver.setBounds(352, 50, 84, 33);
		contentPane.add(btnActiver);
		
		JLabel lblClDactivation = new JLabel("Clé d'activation : ");
		lblClDactivation.setHorizontalAlignment(SwingConstants.RIGHT);
		lblClDactivation.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		lblClDactivation.setBounds(0, 11, 103, 27);
		contentPane.add(lblClDactivation);
		
		JLabel label = new JLabel("-");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.BOLD, 16));
		label.setBounds(169, 11, 23, 28);
		contentPane.add(label);
		
		act2 = new JTextField();
		act2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		act2.setHorizontalAlignment(SwingConstants.CENTER);
		act2.setBounds(192, 12, 69, 27);
		contentPane.add(act2);
		act2.setColumns(10);
		
		JLabel lblEnvoyerLaCl = new JLabel("Au cas de probleme d'identification veuillez nous contacter sur a-ek@homail.fr");
		lblEnvoyerLaCl.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnvoyerLaCl.setBounds(0, 80, 442, 25);
		contentPane.add(lblEnvoyerLaCl);
		
		act3 = new JTextField();
		act3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		act3.setHorizontalAlignment(SwingConstants.CENTER);
		act3.setColumns(10);
		act3.setBounds(280, 12, 69, 27);
		contentPane.add(act3);
		
		JLabel label_1 = new JLabel("-");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		label_1.setBounds(258, 11, 23, 28);
		contentPane.add(label_1);
		
		act4 = new JTextField();
		act4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		act4.setHorizontalAlignment(SwingConstants.CENTER);
		act4.setColumns(10);
		act4.setBounds(367, 12, 69, 27);
		contentPane.add(act4);
		
		JLabel label_2 = new JLabel("-");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		label_2.setBounds(347, 12, 23, 28);
		contentPane.add(label_2);
		
		JButton button = new JButton("Activer");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*
				XmlFile.CreateWrite(path, keys, values);
				*/
				Connection con = DataBase.connectMySql("www.monhotel-dz.com","jlvljuzg_ownc701","jlvljuzg_ownc701","j18M01a87@123");
				try {
					Statement stm = con.createStatement();
					String key = act.getText()+"-"+act2.getText()+"-"+act3.getText()+"-"+act4.getText();
					String req = "select age, postes, dates from `transp` where `key` like '"+key+"' ";
					ResultSet rst = stm.executeQuery(req);
					boolean ok = false;
					int age = 0;
					int postes = 0;
					Date dates = new Date();
					
					while(rst.next()){
						ok = true;
						age = rst.getInt(1);
						postes = rst.getInt(2);
						dates = rst.getDate(3);
					}
					
					req = "select now()";
					rst = stm.executeQuery(req);
					Date dtAct = new Date();
					while(rst.next()){
						dtAct = rst.getDate(1);
					}

					
					if(!ok){
						new Message(1,"Veuillez vérifier votre code d'activation svp !!");
					}else{

						if(!dt.form_Aff(dtAct).equals(dt.form_Aff(new Date()))){
							new Message(1,"Veuillez corriger la date de votre ordinateur svp !!");
						}else if(postes <= 0){
							new Message(1,"Cette clé est consommé.\nPour plus d'information veuillez contacter le support technique.");
						}else{
							
							req = "UPDATE `transp` set postes = postes - 1, dates = now() where `key` like '"+key+"' ";
							stm.executeUpdate(req);
							
							String dataBase = XmlFile.readValue(path, "dataBase");
							String ipAdresse = XmlFile.readValue(path, "ipAdresse");
							String userDB = XmlFile.readValue(path, "userDB");
							String pswDB = XmlFile.readValue(path, "pswDB");
							String dtAct0 = XmlFile.readValue(path, "dtAct");
							String dtActive = XmlFile.readValue(path, "dtActive");
							
							
									try {
										
										  if(dt.form_Aff(dates).equals("01/01/1970")){
												 dtAct0 = dt.form_Aff(dtAct);
												 dtActive = codification.encrypt(dt.form_Aff(dt.longToDate(dt.addYear(dtAct, age))));
										   }else{
												 dtAct0 = dt.form_Aff(dates);
												 dtActive = codification.encrypt(dt.form_Aff(dt.longToDate(dt.addYear(dates, age))));
										  }
								   
										} catch (Exception e1) {
											e1.printStackTrace();
										}
						    try {
								values = new String[]{dataBase,ipAdresse,userDB,pswDB,codification.encrypt(dtAct0),dtActive,fct.DiskUtils.getSerialNumber("C")};
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							XmlFile.CreateWrite(path, keys, values);
							new Message(0, "Activation effectuée avec succées.\nOn vous remercie de nous avoir fait confiance.");
							setVisible(false);
							new identificateur().setVisible(true);
						}
					}
					
					
					stm.close();
				} catch (SQLException e) {
					new Message(2, "Connexion internet introuvable.\nVeuillez vous connecté a internet puis reactiver l'application.");
				}
				
				
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 15));
		button.setBounds(216, 50, 126, 33);
		contentPane.add(button);
	}
	
	

   
	    public static String getSerialNumber(String drive) {
	    String result = "";
	      try {
	        File file = File.createTempFile("realhowto",".vbs");
	        file.deleteOnExit();
	        FileWriter fw = new java.io.FileWriter(file);

	        String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
	                    +"Set colDrives = objFSO.Drives\n"
	                    +"Set objDrive = colDrives.item(\"" + drive + "\")\n"
	                    +"Wscript.Echo objDrive.SerialNumber";  // see note
	        fw.write(vbs);
	        fw.close();
	        Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
	        BufferedReader input =
	          new BufferedReader
	            (new InputStreamReader(p.getInputStream()));
	        String line;
	        while ((line = input.readLine()) != null) {
	           result += line;
	        }
	        input.close();
	      }
	      catch(Exception e){
	          e.printStackTrace();
	      }
	      return result.trim();
	    }
}
