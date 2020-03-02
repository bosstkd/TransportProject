package Mission;

import interfaces.FirstPg;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSpinner;

import fct.dt;
import fct.str;
import fct.tField;
import fct.table;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BonGasoil extends JPanel {
	private JTextField tfBg;

	JComboBox cbMission = new JComboBox();		
	JList listBg;		
	JSpinner spFMission = new JSpinner();
	JSpinner spApprox = new JSpinner();
	
	DefaultListModel listModel_;
	
	public BonGasoil() {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(null);
		
		JLabel lblNMission = new JLabel("N\u00B0 Mission");
		lblNMission.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNMission.setFont(new Font("Consolas", Font.PLAIN, 14));
		lblNMission.setBounds(179, 23, 95, 23);
		add(lblNMission);
		cbMission.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Vector vct = listBg(cbMission.getSelectedItem()+"");
					listBg.setListData(vct);
					listBg.repaint();
					String req = "select FRAISMISSION, KM from TRANS_ETAT_MISSION where NUMMIS like '"+cbMission.getSelectedItem()+"' ";
					Statement stm = FirstPg.con.createStatement();
					ResultSet rst = stm.executeQuery(req);
					while (rst.next()){
						spFMission.setValue(rst.getDouble(1));
						spApprox.setValue(rst.getInt(2));
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				
				//
			}
		});
		

		cbMission.setFont(new Font("Consolas", Font.PLAIN, 14));
		cbMission.setBounds(291, 23, 175, 23);
		add(cbMission);
		
		JLabel lblBonDeGasoil = new JLabel("Bon de Gasoil Li\u00E9e");
		lblBonDeGasoil.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBonDeGasoil.setFont(new Font("Consolas", Font.PLAIN, 14));
		lblBonDeGasoil.setBounds(82, 67, 190, 23);
		add(lblBonDeGasoil);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(291, 113, 175, 121);
		add(scrollPane);
		
		listBg = new JList(new Vector());
		listBg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				tfBg.setText(listBg.getSelectedValue()+"");
			}
		});
		listBg.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listBg.setFont(new Font("Consolas", Font.PLAIN, 14));
		scrollPane.setViewportView(listBg);
		
		tfBg = new JTextField();
		tfBg.setFont(new Font("Consolas", Font.PLAIN, 14));
		tfBg.setBounds(291, 67, 175, 23);
		add(tfBg);
		tfBg.setColumns(10);
		
		JButton button = new JButton("+");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(str.isEmpty(tfBg.getText())){
					tField.goTo(tfBg);
					tField.precise(tfBg);
				}else{
					   String req = "declare " +
									"begin " +
									"for item in (select IDCHAUF, IDACCOMP from TRANS_MISSION where NUMMIS like '"+cbMission.getSelectedItem()+"')loop " +
											"insert into TRANS_BGASOIL_MISSION (NUMMIS, NUMBON, ID_CH, ID_ACC) values ('"+cbMission.getSelectedItem()+"', '"+tfBg.getText()+"', item.IDCHAUF, item.IDACCOMP); " +
									"end loop; " +
									"end;";
					try {
						CallableStatement cstm = FirstPg.con.prepareCall(req);
						cstm.execute();
						cstm.close();
						try {
							Vector vct = listBg(cbMission.getSelectedItem()+"");
							listBg.setListData(vct);
							listBg.repaint();
						} catch (Exception e) {
							// TODO: handle exception
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null,"Bon déja inséré !!!","Erreur",JOptionPane.ERROR_MESSAGE);
					}
					
				}
			}
		});
		button.setFont(new Font("Consolas", Font.PLAIN, 20));
		button.setBounds(377, 90, 89, 23);
		add(button);
		
		JButton button_1 = new JButton("-");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(str.isEmpty(tfBg.getText())){
					tField.goTo(tfBg);
					tField.precise(tfBg);
				}else{
					   String req = "declare " +
									"begin " +
									 "delete from TRANS_BGASOIL_MISSION where NUMBON like '"+tfBg.getText()+"'; "+
									"end;";
					try {
						CallableStatement cstm = FirstPg.con.prepareCall(req);
						cstm.execute();
						cstm.close();
						try {
							Vector vct = listBg(cbMission.getSelectedItem()+"");
							listBg.setListData(vct);
							listBg.repaint();
						} catch (Exception e) {
							// TODO: handle exception
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null,"Erreur SQL : "+e,"Erreur",JOptionPane.ERROR_MESSAGE);
					}
					
				}
			}
		});
		button_1.setFont(new Font("Consolas", Font.PLAIN, 20));
		button_1.setBounds(291, 90, 87, 23);
		add(button_1);
		
		JLabel lblFraisDeMission = new JLabel("Frais de mission");
		lblFraisDeMission.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFraisDeMission.setFont(new Font("Consolas", Font.PLAIN, 14));
		lblFraisDeMission.setBounds(84, 257, 190, 23);
		add(lblFraisDeMission);
		
		spFMission.setFont(new Font("Consolas", Font.PLAIN, 12));
		spFMission.setBounds(294, 257, 172, 23);
		add(spFMission);
		
		JLabel lblKmApproximatif = new JLabel("Km Parcouru");
		lblKmApproximatif.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKmApproximatif.setFont(new Font("Consolas", Font.PLAIN, 14));
		lblKmApproximatif.setBounds(84, 291, 190, 23);
		add(lblKmApproximatif);
		
		
		spApprox.setFont(new Font("Consolas", Font.PLAIN, 12));
		spApprox.setBounds(294, 291, 172, 23);
		add(spApprox);
		
		JButton btnConfirmer = new JButton("Confirmer");
		btnConfirmer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				 String req = "declare " +
							"begin " +
							"Update TRANS_ETAT_MISSION set FRAISMISSION	= "+spFMission.getValue()+", KM = "+spApprox.getValue()+" where NUMMIS like '"+cbMission.getSelectedItem()+"'; "+
							"end;";
			
				
				try {
					CallableStatement cstm = FirstPg.con.prepareCall(req);
					cstm.execute();
					cstm.close();
					JOptionPane.showMessageDialog(null,"Modification effectuée avec succée","Information",JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,e,"Erreur",JOptionPane.ERROR_MESSAGE);
				}
			
			}
		});
		btnConfirmer.setFont(new Font("Consolas", Font.PLAIN, 18));
		btnConfirmer.setBounds(291, 325, 175, 40);
		add(btnConfirmer);
		
		JButton btnActualiser = new JButton("Actualiser");
		btnActualiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					RechMission();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnActualiser.setFont(new Font("Consolas", Font.PLAIN, 12));
		btnActualiser.setBounds(481, 23, 103, 23);
		add(btnActualiser);

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					RechMission();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		
	}
	
	void RechMission() throws SQLException{
    	Statement stm = FirstPg.con.createStatement();
    	String req = "select NUMMIS from TRANS_MISSION  order by DATEMIS DESC";
    	ResultSet rst = stm.executeQuery(req);
    	cbMission.removeAllItems();
    	while (rst.next()){
    		cbMission.addItem(rst.getString(1));
    	}
    	stm.close();
    }
	
	public Vector listBg(String nummiss) throws SQLException{
		Vector vct = new Vector();
    	Statement stm = FirstPg.con.createStatement();
    	String req = "select NUMBON from TRANS_BGASOIL_MISSION where NUMMIS like '"+nummiss+"'";
    	ResultSet rst = stm.executeQuery(req);

    	while (rst.next()){
    		vct.addElement(rst.getString(1));
    	}
    	
    	stm.close();
    	return vct;
    }
	
}
