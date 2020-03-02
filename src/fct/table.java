package fct;

import interfaces.FirstPg;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;



public class table extends JPanel{

	public static void changeSizeh(int height, JTable tableau, int ncol){
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
	
	public static void changeSize(int width, int height, JTable tableau, int ncol){
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
	
	public table (final JTable tab, final JPopupMenu Menu, final String[] argsMenu, final Thread[] thd){
		setLayout(new BorderLayout(0, 0));
		tab.setFont(new Font("Consolas", Font.PLAIN, 12));
		tab.setRowHeight(30);
		JScrollPane scrollPane1 = new JScrollPane(tab);
		add(scrollPane1);
		
		tab.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == 3){	
					JMenuItem[] mnArgs = new JMenuItem[argsMenu.length];
					 int i = 0;
					for( i = 0; i<argsMenu.length; i++){
						mnArgs[i] =  new JMenuItem(argsMenu[i]);
						Menu.add(mnArgs[i]);
						final int x = i;
						mnArgs[i].addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent e){
								try {
									thd[x].start();
								} catch (Exception e2) {
									
								}
							}
						});
					}
					Menu.show(tab, e.getX(), e.getY());
				}
			}
		});
		
	}
	
	public table (JTable tab){
		setLayout(new BorderLayout(0, 0));
		tab.setFont(new Font("Consolas", Font.PLAIN, 12));
		tab.setRowHeight(30);
		JScrollPane scrollPane1 = new JScrollPane(tab);
		add(scrollPane1);
	}
	
	
	public static void ReachForTab(DefaultTableModel def,JTable tab, String dBargs[], int[] type,String dbTab, String Condition, Connection con){
		try {
			String req = "select ";
			for(int i = 0; i < dBargs.length; i++){
				req = req + dBargs[i]+",";
			}
			req = req.substring(0, req.length()-1);
					
			req = req + " from "+dbTab+" "+Condition;

			  ResultSet rst = FirstPg.con.createStatement().executeQuery(req);
			  Object[] obj = null ;
			  int x = dBargs.length;
			  
			  while(rst.next()){
				 obj = new Object[x];
				  for(int i = 0; i < x; i++){
					 
					  if(type[i]==0){
						  obj[i] = rst.getString(i+1);
					  }else if(type[i]==1){
						  obj[i] = nbr.dbToDf(rst.getDouble(i+1));
					  }else{
						  obj[i] = dt.form_Aff(rst.getDate(i+1));
					  }
					  
					  
				  }
            	 def.addRow(obj);
			  }
			  tab.setModel(def);
			for(int i = 0; i< tab.getRowCount();i++){
				 changeSizeh(25,tab,i);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
