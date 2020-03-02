package Mission;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Font;

public class mission extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mission frame = new mission();
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
	public mission() {
		setFrameIcon(new ImageIcon(mission.class.getResource("/icones/original/A16/atome-icone-9016-128_RS.png")));
		setTitle("Controle de mission");
		setClosable(true);
		setIconifiable(true);
		setBounds(100, 100, 793, 525);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Consolas", Font.PLAIN, 12));
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		CMissionPan panel = new CMissionPan();
		tabbedPane.addTab("Creation Mission", null, panel, null);
		
		BonGasoil panel_1 = new BonGasoil();
		tabbedPane.addTab("Bons de route liée", null, panel_1, null);
		


	}

}
