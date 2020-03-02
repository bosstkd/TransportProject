package interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import fct.XmlFile;

import Mission.mission;
import Suivi.SuivCmd;
import Suivi.SuivFct;
import Tableau.de.board.page;

public class FirstPg extends JFrame {

	private JPanel contentPane;

	public static Connection con;
	
	JDesktopPane desktopPane;

	private BufferedImage img;
		
	public final static String IMAGE_TYPE_JPEG = "jpeg";
	
	static String path = "C:/JTrans/conf.xml";
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedLookAndFeelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				
				try {
					String[] previlege = new String[]{"true","true","true","true","true","true","true","true","true"};
						FirstPg frame = new FirstPg(previlege);
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
	public FirstPg(String[] Previlege) {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int w = (int) screenSize.getWidth();
		int h = (int) screenSize.getHeight();
		
		boolean[] blAcces = new boolean[Previlege.length];
		
		for(int i = 0; i < Previlege.length; i++){
			blAcces[i] = Boolean.parseBoolean(Previlege[i]);
		}
		System.out.println();
		con = fct.DataBase.connect(XmlFile.readValue(path, "ipAdresse"), XmlFile.readValue(path, "dataBase"), XmlFile.readValue(path, "userDB"), XmlFile.readValue(path, "pswDB"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(FirstPg.class.getResource("/icones/original/Inc.jpg")));
		setTitle("TransPro One v1.0.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, w, h);
		
		try {
			logo(new Dimension(w, h),"C:/JTrans/camion.jpg",IMAGE_TYPE_JPEG,"jpg");
            img = ImageIO.read(new File("C:/JTrans/camion.jpg"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Utsaah", Font.PLAIN, 19));
		setJMenuBar(menuBar);
		
		JMenu mnOuvrir = new JMenu("Ouvrir");
		menuBar.add(mnOuvrir);
		
		JMenuItem mntmNouveauUtilisateur = new JMenuItem("Nouveau utilisateur");
		mntmNouveauUtilisateur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Users().setVisible(true);
			}
		});
		mntmNouveauUtilisateur.setEnabled(blAcces[1]);
		mntmNouveauUtilisateur.setIcon(new ImageIcon(FirstPg.class.getResource("/icones/original/A16/utilisateur-icone-9647-128_RS.png")));
		mnOuvrir.add(mntmNouveauUtilisateur);
		
		JMenuItem mntmPrvilege = new JMenuItem("Privileges utilisateurs");
		mntmPrvilege.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new conf_operateur().setVisible(true);
			}
		});
		mntmPrvilege.setEnabled(blAcces[1]);
		mntmPrvilege.setIcon(new ImageIcon(FirstPg.class.getResource("/icones/original/A16/ccsm-icone-8124-128_RS.png")));
		mnOuvrir.add(mntmPrvilege);
		
		JMenuItem mntmFermer = new JMenuItem("Fermer");
		mntmFermer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		desktopPane = new JDesktopPane() {
            @Override
            protected void paintComponent(Graphics grphcs) {
                super.paintComponent(grphcs);
                
                grphcs.drawImage(img, 0, 0, null);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(img.getWidth(), img.getHeight());
            }
        };
		
		JMenuItem mntmInformationEntreprise = new JMenuItem("Information Entreprise");
		mntmInformationEntreprise.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Info_perso cl = new Info_perso();
				cl.setVisible(true);
				desktopPane.add(cl);
			}
		});
		
		mntmInformationEntreprise.setEnabled(blAcces[0]);
		
		mntmInformationEntreprise.setIcon(new ImageIcon(FirstPg.class.getResource("/icones/original/A16/panneau-configuration-parametres-icone-8974-128_RS.png")));
		mnOuvrir.add(mntmInformationEntreprise);
		mntmFermer.setIcon(new ImageIcon(FirstPg.class.getResource("/icones/original/A16/application-icone-4104-128_RS.png")));
		mnOuvrir.add(mntmFermer);
		
		JMenu mnOprations = new JMenu("Op\u00E9rations");
		menuBar.add(mnOprations);
		
		JMenuItem mntmCreationMission = new JMenuItem("Creation mission");
		mntmCreationMission.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mission cl = new mission();
				cl.setVisible(true);
				desktopPane.add(cl);
			}
		});
		mntmCreationMission.setEnabled(blAcces[2]);
		mntmCreationMission.setIcon(new ImageIcon(FirstPg.class.getResource("/icones/original/A16/livre-document-icone-6883-128_RS.png")));
		mnOprations.add(mntmCreationMission);
		
		JMenuItem mntmLocationDunVehicule = new JMenuItem("Commande pour location");
		mntmLocationDunVehicule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BonCommande cl = new BonCommande();
				cl.setVisible(true);
				desktopPane.add(cl);
			}
		});
		mntmLocationDunVehicule.setEnabled(blAcces[3]);
		mntmLocationDunVehicule.setIcon(new ImageIcon(FirstPg.class.getResource("/icones/original/A16/mail-repondre-icone-6652-128_RS.png")));
		mnOprations.add(mntmLocationDunVehicule);
		
		JMenuItem mntmFacturation = new JMenuItem("Facturation");
		mntmFacturation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				facturation cl = new facturation();
				cl.setVisible(true);
				desktopPane.add(cl);
			}
		});
		mntmFacturation.setEnabled(blAcces[4]);
		mntmFacturation.setIcon(new ImageIcon(FirstPg.class.getResource("/icones/original/A16/ooo-math-icone-9372-128_RS.png")));
		mnOprations.add(mntmFacturation);
		
		JMenuItem mntmCalendrier = new JMenuItem("Calendrier");
		mntmCalendrier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				timeSheet cl = new timeSheet(con);
				cl.setVisible(true);
				desktopPane.add(cl);
			}
		});
		mntmCalendrier.setEnabled(blAcces[5]);
		mntmCalendrier.setIcon(new ImageIcon(FirstPg.class.getResource("/icones/original/A16/calendrier-date-icone-4087-48_RS.png")));
		mnOprations.add(mntmCalendrier);
		
		JMenuItem mntmTableauDeBord = new JMenuItem("Tableau de bord");
		mntmTableauDeBord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				page cl = new page();
				cl.setVisible(true);
				desktopPane.add(cl);
			}
		});
		mntmTableauDeBord.setEnabled(blAcces[6]);
		mntmTableauDeBord.setIcon(new ImageIcon(FirstPg.class.getResource("/icones/original/A16/simulateur-icone-9597-128_RS.png")));
		mnOprations.add(mntmTableauDeBord);
		
		JMenu mnSuivi = new JMenu("Suivi");
		mnSuivi.setEnabled(blAcces[7]);
		mnSuivi.setIcon(new ImageIcon(FirstPg.class.getResource("/icones/original/A16/bleu-dossier-ouvrez-icone-9861-48_RS.png")));
		mnOprations.add(mnSuivi);
		
		JMenuItem mntmFactures = new JMenuItem("Creance");
		mntmFactures.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SuivFct cl = new SuivFct();
				cl.setVisible(true);
				desktopPane.add(cl);
			}
		});
		mnSuivi.add(mntmFactures);
		
		JMenuItem mntmCommandes = new JMenuItem("Dette");
		mntmCommandes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SuivCmd cl = new SuivCmd();
				cl.setVisible(true);
				desktopPane.add(cl);
			}
		});
		mnSuivi.add(mntmCommandes);
		
		JMenu mnInsertions = new JMenu("Insertions");
		mnInsertions.setEnabled(blAcces[8]);
		menuBar.add(mnInsertions);
		
		JMenuItem mntmClient = new JMenuItem("Clients");
		mntmClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Clients cl = new Clients();
				cl.setVisible(true);
				desktopPane.add(cl);
			}
		});
		mntmClient.setIcon(new ImageIcon(FirstPg.class.getResource("/icones/original/A16/client_RS.jpg")));
		mnInsertions.add(mntmClient);
		
		JMenuItem mntmChauffeurs = new JMenuItem("Chauffeurs");
		mntmChauffeurs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Chauffeurs cl = new Chauffeurs();
				cl.setVisible(true);
				desktopPane.add(cl);
			}
		});
		mntmChauffeurs.setIcon(new ImageIcon(FirstPg.class.getResource("/icones/original/A16/chauffeur_RS.jpg")));
		mnInsertions.add(mntmChauffeurs);
		
		JMenuItem mntmAccompagnateurs = new JMenuItem("Accompagnateurs");
		mntmAccompagnateurs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Accomp cl = new Accomp();
				cl.setVisible(true);
				desktopPane.add(cl);
			}
		});
		mntmAccompagnateurs.setIcon(new ImageIcon(FirstPg.class.getResource("/icones/original/A16/accomp_RS.jpg")));
		mnInsertions.add(mntmAccompagnateurs);
		
		JMenuItem mntmVehicule = new JMenuItem("Vehicules");
		mntmVehicule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Vehicule cl = new Vehicule();
				cl.setVisible(true);
				desktopPane.add(cl);
			}
		});
		mntmVehicule.setIcon(new ImageIcon(FirstPg.class.getResource("/icones/original/A16/vehicule_RS.jpg")));
		mnInsertions.add(mntmVehicule);
		
		JMenuItem mntmRemorques = new JMenuItem("Remorques");
		mntmRemorques.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Remorque cl = new Remorque();
				cl.setVisible(true);
				desktopPane.add(cl);
			}
		});
		mntmRemorques.setIcon(new ImageIcon(FirstPg.class.getResource("/icones/original/A16/system-config-nfs-icone-5742-128_RS.png")));
		mnInsertions.add(mntmRemorques);
		
		JMenuItem mntmFournisseurs = new JMenuItem("Fournisseurs");
		mntmFournisseurs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Fournisseurs cl = new Fournisseurs();
				cl.setVisible(true);
				desktopPane.add(cl);
			}
		});
		mntmFournisseurs.setIcon(new ImageIcon(FirstPg.class.getResource("/icones/original/A16/fournisseur_RS.jpg")));
		mnInsertions.add(mntmFournisseurs);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Desktop TransPro One v1.0.0...", TitledBorder.LEADING, TitledBorder.BELOW_BOTTOM, null, new Color(0, 0, 0)));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		desktopPane.setBackground(SystemColor.activeCaption);
	
		contentPane.add(desktopPane, BorderLayout.CENTER);
		desktopPane.setLayout(null);
		
		JLabel lblTransProOne = new JLabel("TransPro One v1.0.0");
		lblTransProOne.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTransProOne.setFont(new Font("Consolas", Font.PLAIN, 14));
		lblTransProOne.setBounds(w-295, 11, 276, 14);
		desktopPane.add(lblTransProOne);
		
		JLabel lblSupportTechnique = new JLabel("Support technique 0670 298 533");
		lblSupportTechnique.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSupportTechnique.setFont(new Font("Consolas", Font.PLAIN, 14));
		lblSupportTechnique.setBounds(w-295, 36, 276, 14);
		desktopPane.add(lblSupportTechnique);
		
		JLabel lblContactCommercial = new JLabel("Contact commercial 0661 394 351");
		lblContactCommercial.setHorizontalAlignment(SwingConstants.RIGHT);
		lblContactCommercial.setFont(new Font("Consolas", Font.PLAIN, 14));
		lblContactCommercial.setBounds(w-295, 61, 276, 14);
		desktopPane.add(lblContactCommercial);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(0, 0, 208, 33);
		desktopPane.add(toolBar);
		
		JButton button = new JButton("");
		button.setEnabled(blAcces[2]);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mission cl = new mission();
				cl.setVisible(true);
				desktopPane.add(cl);
			}
		});
		button.setToolTipText("Cr\u00E9ation mission");
		button.setIcon(new ImageIcon(FirstPg.class.getResource("/icones/original/A32/livre-document-icone-6883-128_RS.png")));
		toolBar.add(button);
		
		JButton button_1 = new JButton("");
		button_1.setEnabled(blAcces[3]);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BonCommande cl = new BonCommande();
				cl.setVisible(true);
				desktopPane.add(cl);
			}
		});
		button_1.setToolTipText("Commande pour location");
		button_1.setIcon(new ImageIcon(FirstPg.class.getResource("/icones/original/A32/mail-repondre-icone-6652-128_RS.png")));
		toolBar.add(button_1);
		
		JButton button_2 = new JButton("");
		button_2.setEnabled(blAcces[6]);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				page cl = new page();
				cl.setVisible(true);
				desktopPane.add(cl);
			}
		});
		button_2.setToolTipText("Tableau de bord");
		button_2.setIcon(new ImageIcon(FirstPg.class.getResource("/icones/original/A32/simulateur-icone-9597-128_RS.png")));
		toolBar.add(button_2);
		
		JButton button_3 = new JButton("");
		button_3.setEnabled(blAcces[5]);
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				timeSheet cl = new timeSheet(con);
				cl.setVisible(true);
				desktopPane.add(cl);
			}
		});
		button_3.setToolTipText("Calendrier");
		button_3.setIcon(new ImageIcon(FirstPg.class.getResource("/icones/original/A32/calendrier-date-icone-4087-48_RS.png")));
		toolBar.add(button_3);
		
		JButton button_4 = new JButton("");
		button_4.setEnabled(blAcces[4]);
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				facturation cl = new facturation();
				cl.setVisible(true);
				desktopPane.add(cl);
			}
		});
		button_4.setToolTipText("Facturation");
		button_4.setIcon(new ImageIcon(FirstPg.class.getResource("/icones/original/A32/ooo-math-icone-9372-128_RS.png")));
		toolBar.add(button_4);
	}
	
	
	
	static BufferedImage buf; 		
	public static void logo(Dimension dms, String pictureName, String compressionType, String format) {
	    
	
				File[] fs= new File[1];
				fs[0] = new File("C:/JTrans/etat/camion.jpg");
				
				for( int i = 0; i<fs.length; ++i){	
				
			File imgsup = new File("C:/JTrans/camion."+format); 
		    imgsup.delete();
		    pictureName = "C:/JTrans/camion."+format;
			
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
