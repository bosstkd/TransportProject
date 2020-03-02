package Tableau.de.board;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class page extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					page frame = new page();
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
	public page() {
		setFrameIcon(new ImageIcon(page.class.getResource("/icones/original/A16/simulateur-icone-9597-128_RS.png")));
		setTitle("Page d'informations");
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 815, 496);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Consolas", Font.PLAIN, 14));
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		Chauffeur panel = new Chauffeur();
		tabbedPane.addTab("Chauffeurs", null, panel, null);
		
		JButton btnImpression = new JButton("Impression");
		btnImpression.setFont(new Font("Consolas", Font.PLAIN, 12));
		btnImpression.setBounds(641, 11, 107, 29);
		panel.add(btnImpression);
		
		Clients panel_2 = new Clients();
		tabbedPane.addTab("Clients", null, panel_2, null);
		
		Calcule panel_1 = new Calcule();
		tabbedPane.addTab("Missions", null, panel_1, null);

	}
}
