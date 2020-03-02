package interfaces;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;

import fct.DataBase;
import fct.Message;
import fct.XmlFile;
import fct.codification;
import fct.dt;


public class identificateur extends JFrame {

	private JPanel contentPane;
	public static JTextField tf_user;
	public static JPasswordField tf_psw;
	static String path = "C:/JTrans/conf.xml";

static String theme = "";
private JTextField tf_adresse;
private JTextField tf_user_bdd;
private JPasswordField tf_psw_bdd;
boolean aff = false;

JButton btn_affiche = new JButton("▼");
private JButton button;

	public static void main(String[] args) {
	
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {}
			/*
				identificateur frame = new identificateur(DataBase.connect("127.0.0.1", "XE", "transp", "j18M01a87"));
				frame.setVisible(true);
			*/	
	}

	/**
	 * Create the frame.
	 */
	public identificateur() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(identificateur.class.getResource("/icones/original/amarok-icone-6650-128.png")));
		setTitle("Identification");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 328, 329);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNomDutilisateur = new JLabel("Nom d'utilisateur");
		lblNomDutilisateur.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNomDutilisateur.setFont(new Font("Verdana", Font.PLAIN, 13));
		lblNomDutilisateur.setBounds(10, 11, 118, 23);
		contentPane.add(lblNomDutilisateur);
		
		JLabel lblMotDePasse = new JLabel("Mot de passe");
		lblMotDePasse.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMotDePasse.setFont(new Font("Verdana", Font.PLAIN, 13));
		lblMotDePasse.setBounds(10, 50, 118, 23);
		contentPane.add(lblMotDePasse);
		
		tf_user = new JTextField();
		tf_user.setSize(100, 0);
		tf_user.setFont(new Font("Verdana", Font.PLAIN, 13));
		tf_user.setBounds(150, 11, 153, 26);
		contentPane.add(tf_user);
		tf_user.setColumns(10);
		
		tf_psw = new JPasswordField();
		tf_psw.addKeyListener(new KeyAdapter() {
		});
		tf_psw.setSize(100, 0);
		tf_psw.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==10){

					try {
						Date dtSup = dt.strToDate(codification.decrypt(XmlFile.readValue(path, "dtAct")), "dd/MM/yyyy");
						Date dtInf = dt.strToDate(codification.decrypt(XmlFile.readValue(path, "dtActive")), "dd/MM/yyyy");
						Date dtBd  = DataBase.date_act(DataBase.connect(tf_adresse.getText(), XmlFile.readValue(path, "dataBase"), tf_user_bdd.getText(), tf_psw_bdd.getText()));

						
						if(dt.nbrYearsBetween(dtInf, dtSup) <= 0){
							new Message(1,"Clé d'activation expiré.\nContacter le service commercial: 0661 394 351.\nService technique: 0670 298 533.");
						}else if(!dt.form_Aff(new Date()).equals(dt.form_Aff(dtBd))){
							new Message(1,"Veuillez corrigé la date de votre ordinateur svp.");
						}else{
							
							
							if(!userExist(tf_user.getText(), tf_psw.getText())){
								new Message(1,"Veuillez vérifier le nom d'utilisation et le mot de passe svp !!");
							}else{
								
							Vector vctVals = new Vector();
							Vector vctProp = new Vector();
							vctVals = XmlFile.readAllValues(path);
							vctProp = XmlFile.readAllProperties(path);
							String[] values = new String[vctVals.size()];
							String[] Props = new String[vctProp.size()];
							
							for(int i = 0; i < vctProp.size(); i++){
								Props[i] = vctProp.elementAt(i).toString();
							}
							
							for(int i = 0; i < vctVals.size(); i++){
								if(Props[i].equals("dtAct")){
									values[i] = codification.encrypt(dt.form_Aff(new Date()));
								}else
								if(Props[i].equals("pswDB")){
									values[i] = tf_psw_bdd.getText();
								}else
								if(Props[i].equals("ipAdresse")){
									values[i] = tf_adresse.getText();
								}else
								if(Props[i].equals("userDB")){
									values[i] = tf_user_bdd.getText();
								}else{
									values[i] = vctVals.elementAt(i).toString();
								}  
							}
							XmlFile.CreateWrite(path, Props, values);
							String str[] = previlege(tf_user.getText());
							new FirstPg(str).setVisible(true);
							setVisible(false);
							}

						}
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
		});
		tf_psw.setFont(new Font("Verdana", Font.PLAIN, 13));
		tf_psw.setBounds(150, 50, 153, 26);
		contentPane.add(tf_psw);
		
		JButton btnValider = new JButton("Valider");
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			try {
					Date dtSup = dt.strToDate(codification.decrypt(XmlFile.readValue(path, "dtAct")), "dd/MM/yyyy");
					Date dtInf = dt.strToDate(codification.decrypt(XmlFile.readValue(path, "dtActive")), "dd/MM/yyyy");
					Date dtBd  = DataBase.date_act(DataBase.connect(tf_adresse.getText(), XmlFile.readValue(path, "dataBase"), tf_user_bdd.getText(), tf_psw_bdd.getText()));
					
					String codeDisk = XmlFile.readValue(path, "dur");
					
					String sn = fct.DiskUtils.getSerialNumber("C");
					
					
					if(!codeDisk.equals(sn)){
						new Message(1,"L'application ne marche pas en copier coller veuillez la réinitialisé svp !!");
					}else if(dt.nbrYearsBetween(dtInf, dtSup) <= 0){
						new Message(1,"Clé d'activation expiré.\nContacter le service commercial: 0661 394 351.\nService technique: 0670 298 533.");
					}else if(!dt.form_Aff(new Date()).equals(dt.form_Aff(dtBd))){
						new Message(1,"Veuillez corrigé la date de votre ordinateur svp.");
					}else{
						
						
						if(!userExist(tf_user.getText(), tf_psw.getText())){
							new Message(1,"Veuillez vérifier le nom d'utilisation et le mot de passe svp !!");
						}else{
							
						Vector vctVals = new Vector();
						Vector vctProp = new Vector();
						vctVals = XmlFile.readAllValues(path);
						vctProp = XmlFile.readAllProperties(path);
						String[] values = new String[vctVals.size()];
						String[] Props = new String[vctProp.size()];
						
						for(int i = 0; i < vctProp.size(); i++){
							Props[i] = vctProp.elementAt(i).toString();
						}
						
						for(int i = 0; i < vctVals.size(); i++){
							if(Props[i].equals("dtAct")){
								values[i] = codification.encrypt(dt.form_Aff(new Date()));
							}else
							if(Props[i].equals("pswDB")){
								values[i] = tf_psw_bdd.getText();
							}else
							if(Props[i].equals("ipAdresse")){
								values[i] = tf_adresse.getText();
							}else
							if(Props[i].equals("userDB")){
								values[i] = tf_user_bdd.getText();
							}else{
								values[i] = vctVals.elementAt(i).toString();
							}  
						}
						XmlFile.CreateWrite(path, Props, values);
						String str[] = previlege(tf_user.getText());
						new FirstPg(str).setVisible(true);
						setVisible(false);
						}

					}
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnValider.setFont(new Font("Century Gothic", Font.BOLD, 15));
		btnValider.setBounds(80, 92, 154, 43);
		contentPane.add(btnValider);
		
		btn_affiche.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!aff){
					setBounds(100, 100, 328, 340);
					btn_affiche.setText("▲");
					aff = true;
				}else{
					setBounds(100, 100, 328, 179);
					btn_affiche.setText("▼");
					aff = false;
				}
			}
		});
		btn_affiche.setBounds(269, 112, 43, 23);
		contentPane.add(btn_affiche);
		
		JLabel lblIpAdresse = new JLabel("IP Adresse");
		lblIpAdresse.setFont(new Font("Century Gothic", Font.ITALIC, 13));
		lblIpAdresse.setBounds(10, 195, 80, 26);
		contentPane.add(lblIpAdresse);
		
		JLabel lblUserBdd = new JLabel("User Bdd");
		lblUserBdd.setFont(new Font("Century Gothic", Font.ITALIC, 13));
		lblUserBdd.setBounds(10, 232, 80, 25);
		contentPane.add(lblUserBdd);
		
		JLabel lblPswBdd = new JLabel("Psw Bdd");
		lblPswBdd.setFont(new Font("Century Gothic", Font.ITALIC, 13));
		lblPswBdd.setBounds(10, 268, 80, 25);
		contentPane.add(lblPswBdd);
		
		tf_adresse = new JTextField();
		tf_adresse.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		tf_adresse.setHorizontalAlignment(SwingConstants.CENTER);
		tf_adresse.setText("127.0.0.1");
		tf_adresse.setBounds(100, 195, 203, 26);
		contentPane.add(tf_adresse);
		tf_adresse.setColumns(10);
		
		tf_user_bdd = new JTextField();
		tf_user_bdd.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		tf_user_bdd.setHorizontalAlignment(SwingConstants.CENTER);
		tf_user_bdd.setText("system");
		tf_user_bdd.setColumns(10);
		tf_user_bdd.setBounds(100, 232, 203, 26);
		contentPane.add(tf_user_bdd);
		
		tf_psw_bdd = new JPasswordField();
		tf_psw_bdd.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		tf_psw_bdd.setHorizontalAlignment(SwingConstants.CENTER);
		tf_psw_bdd.setText("amine");
		tf_psw_bdd.setColumns(10);
		tf_psw_bdd.setBounds(100, 269, 203, 26);
		contentPane.add(tf_psw_bdd);
		
		tf_adresse.setText(XmlFile.readValue(path, "ipAdresse"));
		tf_user_bdd.setText(XmlFile.readValue(path, "userDB"));
		tf_psw_bdd.setText(XmlFile.readValue(path, "pswDB"));
		
		button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector vctVals = new Vector();
				Vector vctProp = new Vector();
				vctVals = XmlFile.readAllValues(path);
				vctProp = XmlFile.readAllProperties(path);
				String[] values = new String[vctVals.size()];
				String[] Props = new String[vctProp.size()];
				
				for(int i = 0; i < vctProp.size(); i++){
					Props[i] = vctProp.elementAt(i).toString();
				}
				
				for(int i = 0; i < vctVals.size(); i++){
					if(Props[i].equals("ipAdresse")){
						values[i] = tf_adresse.getText();
					}else if(Props[i].equals("userDB")){
						values[i] = tf_user_bdd.getText();
					}else if(Props[i].equals("pswDB")){
						values[i] = tf_psw_bdd.getText();
					}else{
						values[i] = vctVals.elementAt(i).toString();
					}  
				}
				
				XmlFile.CreateWrite(path, Props, values);
				DataBase.createTables(tf_adresse.getText(), XmlFile.readValue(path, "dataBase"), tf_user_bdd.getText(), tf_psw_bdd.getText());
				Connection con = DataBase.connect(tf_adresse.getText(), XmlFile.readValue(path, "dataBase"), tf_user_bdd.getText(), tf_psw_bdd.getText());
				
				try {
					Statement stm = con.createStatement();
					String req = "insert into TRANS_USER (ID, NOM, PSW, ADMIN) values ('admin', 'admin', 'admin' , 'admin')";
					stm.executeQuery(req);
				} catch (SQLException e1) {
					System.out.println("admin existe");
				}
				
			}
		});
		
		button.setToolTipText("Création ou mise a jour de la Bdd");
		button.setIcon(new ImageIcon(identificateur.class.getResource("/icones/original/A32/application-icone-9642-128_RS.png")));
		button.setBounds(279, 152, 32, 32);
		contentPane.add(button);
	}
		
	boolean userExist(String id, String psw) throws SQLException{
		Connection con = DataBase.connect(tf_adresse.getText(), XmlFile.readValue(path, "dataBase"), tf_user_bdd.getText(), tf_psw_bdd.getText());
		Statement stm = con.createStatement();
		String req = "select * from TRANS_USER where ID like '"+id+"' and PSW like '"+psw+"' ";
		ResultSet rst = stm.executeQuery(req);
		
		while(rst.next()){
			return true;
		}
		
		return false;
	}
	
	
	String[] previlege(String id) throws SQLException{
		String [] str = new String[9];
		
		Connection con = DataBase.connect(tf_adresse.getText(), XmlFile.readValue(path, "dataBase"), tf_user_bdd.getText(), tf_psw_bdd.getText());
		Statement stm = con.createStatement();
		String req = "select INF_ENTREPRISE, INF_USER, MISSION, COMMANDE, FACTURATION, CALENDRIER, TBOARD, SUIVI, INSERTION from TRANS_ACCES where ID like '"+id+"' ";
		ResultSet rst = stm.executeQuery(req);
		boolean ok = false;
		while(rst.next()){
			ok = true;
			for(int i = 1; i < 10; i++){
				str[i-1] = rst.getString(i);
			}
		}
		
		if(!ok){
			for(int i = 0; i < 9; i++){
				str[i] = "true";
			}
		}
		
		return str;
	}
	
}
