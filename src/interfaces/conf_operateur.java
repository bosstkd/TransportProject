package interfaces;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class conf_operateur extends JFrame{

	private JPanel contentPane;
	private JTable tab_ig;
	DefaultTableModel def_ig;
	String[] tit_ig = {"ID", "INF_ENTREPRISE", "INF_USER", "MISSION", "COMMANDE", "FACTURATION", "CALENDRIER", "TBOARD", "SUIVI", "INSERTION"};
	private JPanel p0;
	private JPanel p_table;

	public conf_operateur() {
		setResizable(false);
		//setClosable(true);
		setTitle("Configuration des acc\u00E8s op\u00E9rateurs");
		setIconImage(Toolkit.getDefaultToolkit().getImage(conf_operateur.class.getResource("/icones/original/A32/atome-icone-9016-128_RS.png")));
		setBounds(0, 100, 831, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		p0 = new JPanel();
		p0.setBounds(6, 6, 813, 303);
		contentPane.add(p0);
		p0.setLayout(new BorderLayout(0, 0));
		
		p_table = new JPanel(new BorderLayout());
		p_table.setPreferredSize(new Dimension(810,250));
		p0.add(new JScrollPane(p_table), BorderLayout.CENTER);
		
		def_ig = new DefaultTableModel();
		def_ig.setColumnIdentifiers(tit_ig);
		
		tab_ig = new JTable(def_ig){
			 private static final long serialVersionUID = 1L;
			 
	            public Class getColumnClass(int column) {
	                switch (column) {
	                    case 0:
	                        return String.class;
	                    case 1:
	                    	 return Boolean.class;
	                    case 2:
	                    	 return Boolean.class;
	                    case 3:
	                    	 return Boolean.class;
	                    case 4:
	                    	 return Boolean.class;
	                    case 5:
	                    	 return Boolean.class;
	                    case 6:
	                    	 return Boolean.class;
	                    case 7:
	                    	 return Boolean.class;
	                    case 8:
	                    	 return Boolean.class;
	                    case 9:
	                    	 return Boolean.class;
	                    case 10:
	                    	 return Boolean.class;
	                    default:
	                        return Boolean.class;
	                }
	            }
		 };
		tab_ig.setFont(new Font("Segoe Print", Font.PLAIN, 11));
		p_table.add(new JScrollPane(tab_ig),BorderLayout.CENTER);
		
		JButton btnEnregistrer = new JButton("Enregistrer");
		btnEnregistrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String req = "";
				try {
					Statement stm = FirstPg.con.createStatement();
					
					for(int i = 0; i < tab_ig.getRowCount(); i++ ){
						req = "update TRANS_ACCES set INF_ENTREPRISE = '"+tab_ig.getValueAt(i, 1)+"', INF_USER = '"+tab_ig.getValueAt(i, 2)+"',	MISSION = '"+tab_ig.getValueAt(i, 3)+"',	COMMANDE = '"+tab_ig.getValueAt(i, 4)+"',	FACTURATION = '"+tab_ig.getValueAt(i, 5)+"',	CALENDRIER = '"+tab_ig.getValueAt(i, 6)+"',	TBOARD = '"+tab_ig.getValueAt(i, 7)+"',	SUIVI = '"+tab_ig.getValueAt(i, 8)+"',	INSERTION = '"+tab_ig.getValueAt(i, 9)+"' where ID like '"+tab_ig.getValueAt(i, 0)+"' ";
						stm.executeUpdate(req);
					}

					   JOptionPane.showMessageDialog(null, "Mise a jour effectuée avec succès. ","information",JOptionPane.INFORMATION_MESSAGE);
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		btnEnregistrer.setFont(new Font("Century Gothic", Font.ITALIC, 13));
		btnEnregistrer.setBounds(660, 314, 133, 42);
		contentPane.add(btnEnregistrer);
		
		
		
		new Thread(new Runnable() {
			public void run() {

				def_ig = new DefaultTableModel(){ public boolean isCellEditable(int row, int column) { return column != 0 ;} };
				def_ig.setColumnIdentifiers(tit_ig);
				
                   try {
					Statement stm = FirstPg.con.createStatement();
					String req = "select * from TRANS_ACCES";
					
					ResultSet rst = stm.executeQuery(req);
					
					while(rst.next()){
						def_ig.addRow(new Object[]{rst.getString(1), Boolean.parseBoolean(rst.getString(2)),Boolean.parseBoolean(rst.getString(3)),Boolean.parseBoolean(rst.getString(4)),Boolean.parseBoolean(rst.getString(5)),Boolean.parseBoolean(rst.getString(6)),Boolean.parseBoolean(rst.getString(7)),Boolean.parseBoolean(rst.getString(8)),Boolean.parseBoolean(rst.getString(9)),Boolean.parseBoolean(rst.getString(10))});
					}
					tab_ig.setModel(def_ig);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}                   
				
			}
		}).start();
		
		
	}
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try {
			        	 UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");        	
			        } catch (ClassNotFoundException e) {
					} catch (InstantiationException e) {
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					}
					conf_operateur frame = new conf_operateur();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
