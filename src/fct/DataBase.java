package fct;

import interfaces.FirstPg;

import java.lang.Thread.State;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DataBase {

	public static void main(String[] args) {
		//connectMySql("www.jlogconstrium.net","jlogcons_jmag","jlogcons_bosstkd","j18M01a87");
		createTables("127.0.0.1", "XE", "transp", "j18M01a87");
	}

	public static boolean isDetteBC(String numBc, Connection con){
		boolean ok = false;
		try {
			Statement stm = con.createStatement();
			String req = "select * from TRANS_SUIVI_BC where NUMBC like '"+numBc+"' and PRIX > VERSEMENT ";
			ResultSet rst = stm.executeQuery(req);
			while(rst.next()){
				ok = true;
				break;
			}
			stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ok;
	}
	
	public static boolean isVisible(String ID, String tableCondition){
		boolean ok = false;
		try {
			 String req = "select visible from "+tableCondition+" '"+ID+"'";
			  ResultSet rst = FirstPg.con.createStatement().executeQuery(req);
			  while(rst.next()){
				  ok = rst.getBoolean(1);
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ok;
	}
	
	public static Connection connect(String ip,String dbName, String UID, String pwd){
	    // Connexion
	    Connection con = null;
	    try {
	   // Chargement du pilote JDBC
				Class.forName("oracle.jdbc.driver.OracleDriver");  
				String url = "jdbc:oracle:thin:@//"+ip+":1521/"+dbName;
			    String user = UID;
			    String password = pwd;
	 try {
	            con = DriverManager.getConnection(url, user, password);
	     } catch (SQLException ex) {
	              Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
	     } 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return con;
		}
	
	
	public static Connection connectMySql(String IP, String UIBD, String USER, String PSW){
		Connection con = null;
			try {
					Class.forName("com.mysql.jdbc.Driver") ;
				} catch (ClassNotFoundException ex) {
					// TODO Auto-generated catch block
			    	   JOptionPane.showMessageDialog(null, "erreur de connexion :"+ex,"erreur",JOptionPane.ERROR_MESSAGE);
				}
					 try {
						 								con = DriverManager.getConnection("jdbc:mysql://"+IP+":3306/"+UIBD, USER, PSW) ;
						 } catch (SQLException e1) {
						       							JOptionPane.showMessageDialog(null, "erreur de connexion :"+e1,"erreur",JOptionPane.ERROR_MESSAGE);
						 							}
		return con;
		}	
	
	
	public static Date date_act(Connection con){
		Date dt = new Date();
		
		try {
			Statement stm = con.createStatement();
			String req = "select SYSDATE from dual";
			ResultSet rst = stm.executeQuery(req);
			
			while(rst.next()){
				dt = rst.getDate(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return dt;
	}
	
//-----------------------------------------------------------------------------------------
	
	public static void createTables(String ip, String BdName,String UserBD,String PswBd ){
		
		 Connection con = connect(ip, BdName,UserBD,PswBd);
		
		 try {
				boolean maj = false;
			Statement stm = con.createStatement();
		
//---------------ACTEURS---------------------------//	
			
			 String req = "";
			/*
       		 req = "drop table TRANS_CREANCE_MIS";
			try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}
			 req = "drop table TRANS_VRS_CLT";
			try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}
			 req = "drop table TRANS_VRS_FRS";
			try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}			
			 req = "drop table TRANS_CREANCE_FCT";
			try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}
			 req = "drop table TRANS_SUIVI_BC";
			try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}
			 req = "drop table TRANS_ETAT_MISSION";
			try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}
			 req = "drop table TRANS_BGASOIL_MISSION";
			try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}
			 req = "drop table TRANS_MISSION";
			try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}	
			 req = "drop table TRANS_FACTURATION";
			try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}
			 req = "drop table TRANS_REMORQUE";
			try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}
			 req = "drop table TRANS_VEHICULE_LOCATION";
		    try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}
			 req = "drop table TRANS_CLIENT";
			try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}
			 req = "drop table TRANS_FOURNISSEUR";
			try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}
			 req = "drop table TRANS_VEHICULE";
			try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}
			 req = "drop table TRANS_REMORQUE";
			try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}
			 req = "drop table TRANS_CHAUFFEUR";
				try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}
			req = "drop table TRANS_ACCOMP";
				try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}
				*/
	
				
			        req = "CREATE TABLE TRANS_CLIENT(code_c varchar2(60) not null , nom varchar2(60), nrc varchar2(60), nif varchar2(60), nai varchar2(60), tel varchar2(60), adresse varchar2(4000),exonore number(1),visible number(1)," +
												"CONSTRAINT code_c_pk PRIMARY KEY (code_c))";                          
			        try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}

			        req = "CREATE TABLE TRANS_FOURNISSEUR(code_f varchar2(60) not null , nom varchar2(60), nrc varchar2(60), nif varchar2(60), nai varchar2(60), tel varchar2(60), adresse varchar2(4000),visible number(1)," +
			        									"CONSTRAINT code_f_pk PRIMARY KEY (code_f))";  
			       
					try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}

			        req = "CREATE TABLE TRANS_VEHICULE(matricule varchar2(60) not null , type varchar2(60), marque varchar2(60),visible number(1),CONSTRAINT matricule_pk PRIMARY KEY (matricule))";                          
					try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}

			        req = "CREATE TABLE TRANS_REMORQUE(matricule varchar2(60) not null , type varchar2(60), marque varchar2(60), capacite number, unitQte varchar2(8),visible number(1),CONSTRAINT matricule_rq_pk PRIMARY KEY (matricule))";                          
					try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}

			        req = "CREATE TABLE TRANS_CHAUFFEUR(id varchar2(60) not null , nom varchar2(60), PCB NUMBER(1), PCC1 NUMBER(1), PCC2 NUMBER(1), PCD NUMBER(1), PCE NUMBER(1), numPC varchar2(60) unique,ADRESSE VARCHAR2(3000),TEL VARCHAR2(60),visible number(1),CONSTRAINT id_pk PRIMARY KEY (id) )";                          
					try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}
					
				    req = "CREATE TABLE TRANS_ACCOMP(id varchar2(60) not null , nom varchar2(60), poste varchar2(60), TEL varchar2(60), ADRESSE varchar2(3500),visible number(1),CONSTRAINT id_ac_pk PRIMARY KEY (id) )";                          
					try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}
			

			        req = "CREATE TABLE TRANS_VEHICULE_LOCATION(numBC varchar2(60) not null , code_f varchar2(60), DATES DATE, matricule varchar2(60), type varchar2(60), marque varchar2(60), dateAq DATE, dateLb DATE,prix NUMBER(11,2), polytiqueLocation varchar2(60), COMENT VARCHAR2(500),CONSTRAINT numBC_pk PRIMARY KEY (numBC), CONSTRAINT fk_code_f FOREIGN KEY (code_f) REFERENCES TRANS_FOURNISSEUR(code_f))";                          
				    try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}
				   
				    
				    req = "CREATE TABLE TRANS_USER(ID varchar2(100) , NOM varchar2(100), PSW varchar2(100), ADMIN varchar2(20), CONSTRAINT ID_USER PRIMARY KEY (ID))";                          
				    try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}
				  
				
				    req = "CREATE TABLE TRANS_ACCES (ID VARCHAR2(60), INF_ENTREPRISE VARCHAR2(60), INF_USER VARCHAR2(60), MISSION VARCHAR2(60), COMMANDE VARCHAR2(60), FACTURATION VARCHAR2(60), CALENDRIER VARCHAR2(60), TBOARD VARCHAR2(60), SUIVI VARCHAR2(60), INSERTION VARCHAR2(60),  CONSTRAINT fk_ID FOREIGN KEY (ID) REFERENCES TRANS_USER(ID) ) ";
				    try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}
//-----------------ACTION--------------------------//					
					
					 req = "CREATE TABLE TRANS_FACTURATION(numFct varchar2(20), dateFct date, code_c varchar2(60), prixHt number(11,2), prixTtc number(11,2), typePayement varchar2(60),paye number(1), avoir number(1), numFctCh varchar2(20), TVA number, CONSTRAINT numFct_pk PRIMARY KEY (numFct) , CONSTRAINT fk_fact_code_c FOREIGN KEY (code_c) REFERENCES TRANS_CLIENT(code_c)  )";                          
						try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}	
					
					 req = "CREATE TABLE TRANS_MISSION(numMis varchar2(20), code_c varchar2(60), dateMis date, dateAllez date, dateRetour date, distination varchar2(200), detail varchar2(1000), prixHt number(11,2), facturation varchar2(20), paye number(1), idChauf varchar2(60), idAccomp varchar2(60),CONSTRAINT numMis_pk PRIMARY KEY (numMis), CONSTRAINT fk_mis_code_c FOREIGN KEY (code_c) REFERENCES TRANS_CLIENT(code_c),  CONSTRAINT fk_mis_chauf FOREIGN KEY (idChauf) REFERENCES TRANS_CHAUFFEUR(ID),  CONSTRAINT fk_mis_Accomp FOREIGN KEY (idAccomp) REFERENCES TRANS_ACCOMP(ID))";                          
						try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}
						
					 req = "CREATE TABLE TRANS_MISSION_RJT(numMis varchar2(20), code_c varchar2(60), dateMis date, dateAllez date, dateRetour date, distination varchar2(200), detail varchar2(1000), prixHt number(11,2), facturation varchar2(20), paye number(1), idChauf varchar2(60), idAccomp varchar2(60))";                          
							try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}
					
					 req = "CREATE TABLE TRANS_ETAT_MISSION(numMis varchar2(20), idVehicule varchar2(60), idRemorque varchar2(60), fraisMission number(11,2), KM NUMBER, idVehiculeLouer varchar2(60), INDX number(1,0), IDREMORQUELOUER varchar2(60), CONSTRAINT fk_mis_numMis FOREIGN KEY (numMis) REFERENCES TRANS_MISSION(numMis), CONSTRAINT fk_mis_idVehicule FOREIGN KEY (idVehicule) REFERENCES TRANS_VEHICULE(matricule)  , CONSTRAINT fk_mis_idRemorque FOREIGN KEY (idRemorque) REFERENCES TRANS_REMORQUE(matricule)  )";                          
						try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}	
					
					 req = "CREATE TABLE TRANS_BGASOIL_MISSION(numMis varchar2(20), numBon varchar2(60) unique, ID_CH varchar2(60), ID_ACC varchar2(60), CONSTRAINT fk_BG_numMis FOREIGN KEY (numMis) REFERENCES TRANS_MISSION(numMis)  )";                          
						try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}
					
//-----------------SUIVI--------------------------//							
						
					req = "CREATE TABLE TRANS_CREANCE_MIS(numMis varchar2(20), prixHt number(11,2), versement number(11,2), numVers varchar2(200),  CODE_C varchar2(60), CONSTRAINT fk_crt_ms_code_c FOREIGN KEY (code_c) REFERENCES TRANS_CLIENT(code_c),CONSTRAINT fk_SV_numMis FOREIGN KEY (numMis) REFERENCES TRANS_MISSION(numMis)  )";                          
						try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}	
						
					req = "CREATE TABLE TRANS_CREANCE_FCT(numFct varchar2(20), prixTtc number(11,2), versement number(11,2), numVers varchar2(100), CODE_C varchar2(60), CONSTRAINT fk_crt_fct_code_c FOREIGN KEY (code_c) REFERENCES TRANS_CLIENT(code_c),CONSTRAINT fk_SV_numFct FOREIGN KEY (numFct) REFERENCES TRANS_FACTURATION(numFct)  )";                          
						try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}		
						
					req = "CREATE TABLE TRANS_SUIVI_BC(numBC varchar2(20), prix number(11,2), versement number(11,2), numVers varchar2(100), CODE_F varchar2(60), CONSTRAINT fk_crt_bc_code_f FOREIGN KEY (code_f) REFERENCES TRANS_Fournisseur(code_f),CONSTRAINT fk_SV_numBC FOREIGN KEY (numBC) REFERENCES TRANS_VEHICULE_LOCATION(numBC)  )";                          
						try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}	
						
					req ="CREATE TABLE TRANS_INFO_PERSO (NOM VARCHAR2(100), ADRESSE VARCHAR2(1000), NRC VARCHAR2(100), NIF VARCHAR2(100), NIS VARCHAR2(100), RIB VARCHAR2(200), TEL VARCHAR2(100), FAX VARCHAR2(100), MAIL VARCHAR2(100), NON_SOUMIS_TVA VARCHAR2(60))";
						try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}	

//----------------------------------------------------------------------------------------------------
				
					req ="CREATE TABLE TRANS_VRS_CLT (CODE_C VARCHAR2(60), NUMVRS VARCHAR2(100) unique, RESTE NUMBER(15,2), MONTANT NUMBER(15,2), DATEVRS DATE, CONSTRAINT fk_vrs_code_c FOREIGN KEY (code_c) REFERENCES TRANS_CLIENT(code_c))";
						try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}	
						
					req ="CREATE TABLE TRANS_VRS_FRS (CODE_F VARCHAR2(60), NUMVRS VARCHAR2(100) unique, RESTE NUMBER(15,2), MONTANT NUMBER(15,2), DATEVRS DATE, CONSTRAINT fk_vrs_code_f FOREIGN KEY (code_f) REFERENCES TRANS_FOURNISSEUR(code_f))";
						try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}	
							
				    req = "create or replace view TRANS_VW_CLT as select TRANS_MISSION.NUMMIS, TRANS_MISSION.CODE_C, TRANS_MISSION.DATEMIS, TRANS_MISSION.DATEALLEZ, TRANS_MISSION.PRIXHT, TRANS_MISSION.IDCHAUF, TRANS_MISSION.IDACCOMP, TRANS_ETAT_MISSION.FRAISMISSION, TRANS_ETAT_MISSION.KM from TRANS_MISSION, TRANS_ETAT_MISSION where TRANS_MISSION.NUMMIS = TRANS_ETAT_MISSION.NUMMIS" ;
					try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}	
					
					 req = " create or replace view TRANS_VW_BNGASOIL as select TRANS_MISSION.NUMMIS, TRANS_MISSION.CODE_C, TRANS_MISSION.DATEMIS, TRANS_MISSION.DATEALLEZ, TRANS_MISSION.IDCHAUF, TRANS_MISSION.IDACCOMP, TRANS_BGASOIL_MISSION.NUMBON  from TRANS_MISSION, TRANS_BGASOIL_MISSION where TRANS_MISSION.NUMMIS = TRANS_BGASOIL_MISSION.NUMMIS" ;
						try { stm.executeUpdate(req); maj = true; } catch (Exception e) {maj = false;}	
						
			if(maj){
		        JOptionPane.showMessageDialog(null, "Mise à  jour effectuée avec succés.","Information",JOptionPane.INFORMATION_MESSAGE);
			}else{
			    JOptionPane.showMessageDialog(null, "Base des données déja existante !!","Attention",JOptionPane.WARNING_MESSAGE);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
   public static String RechClientStr(String condition)throws SQLException{
    	String str = "";
		Statement stm = FirstPg.con.createStatement();
		if (!condition.equals("")) condition = " and "+condition;
		String req = "select NOM from TRANS_CLIENT where visible = 1 "+condition+" ";
		ResultSet rst = stm.executeQuery(req);
		while (rst.next()){
			str=rst.getString(1);
		}
		return str;
	}
   
   
   public static String RechAccStr(String condition) throws SQLException{
   	String str = "";
		Statement stm = FirstPg.con.createStatement();
		if (!condition.equals("")) condition = " and "+condition;
		String req = "select NOM from TRANS_ACCOMP  where visible = 1  "+condition+" ";
		ResultSet rst = stm.executeQuery(req);
		while (rst.next()){
			str = rst.getString(1);
		}
		stm.close();
		return str;
	}
   
	public static String RechChaufStr(String condition) throws SQLException{
		String str = "";
		Statement stm = FirstPg.con.createStatement();
		if (!condition.equals("")) condition = " and "+condition;
		String req = "select NOM from TRANS_CHAUFFEUR  where visible = 1 "+condition+" ";
		ResultSet rst = stm.executeQuery(req);

		while (rst.next()){
			str = rst.getString(1);
		}
		stm.close();
		return str;
	}
	
public static void TabVrs (String tabVrs, String tabCrtFct, String[] args, String[] vals, Connection con) throws SQLException{		
		String req = "declare " +
					 "Begin ";
		
		       req = req + "insert into "+tabVrs+" ("+args[0]+", "+args[1]+", "+args[2]+", "+args[3]+", "+args[4]+")" +
				                  "VALUES ('"+vals[0]+"', '"+vals[1]+"', "+vals[2]+", "+vals[3]+", '"+vals[4]+"'); ";
		
		req= req + " end;";
		CallableStatement cstm = con.prepareCall(req);
		cstm.execute();
		
	}

public static void PayementByVrs (String tabVrs, String tabCrtFct, String[] args, String[] vals, String[] argsFct, Connection con) throws SQLException{		
	String req = "declare " +
				 "Begin ";
	
	Vector vct = NoPayedList(tabCrtFct, argsFct, vals[0], con);
	double[] vlFct = new double[2];
	double calc = 0;
	for(int i = 0; i < vct.size(); i++){
		vlFct = PVNoPayedList(tabCrtFct, vct.elementAt(i).toString(), argsFct, con);
		calc = vlFct[0] - vlFct[1];
		
		if(calc > 0) if(calc < nbr.toDouble(vals[2])){
													req = req + "update "+tabVrs+" set "+args[2]+" = "+vals[2]+" - "+calc+" where "+args[1]+" like '"+vals[1]+"'; ";
												    req = req + "insert into "+tabCrtFct+" ("+argsFct[0]+","+argsFct[1]+","+argsFct[2]+","+argsFct[3]+","+argsFct[4]+") values ('"+vct.elementAt(i)+"',"+vlFct[0]+","+calc+",'"+vals[1]+"','"+vals[0]+"'); ";
										}else{
													req = req + "update "+tabVrs+" set "+args[2]+" = 0 where "+args[1]+" like '"+vals[1]+"'; ";
													req = req + "insert into "+tabCrtFct+" ("+argsFct[0]+","+argsFct[1]+","+argsFct[2]+","+argsFct[3]+","+argsFct[4]+") values ('"+vct.elementAt(i)+"',"+vlFct[0]+","+vals[2]+",'"+vals[1]+"','"+vals[0]+"'); ";
													break;
											 }
	}
	req= req + " end;";
		
	CallableStatement cstm = con.prepareCall(req);
	cstm.execute();
	
}


public static void TabVrsRmv(String tabVrs, String tabCrtFct, String nmVrs, Connection con, String[] args, String[] argsCrtFct) throws SQLException{
	String req = "Declare " +
				 "Begin " +
				 "delete from "+tabCrtFct+" where "+argsCrtFct[3]+" like '"+nmVrs+"'; " +
				 "delete from "+tabVrs+" where "+args[1]+" like '"+nmVrs+"'; " +
				 "end; ";
	
	CallableStatement cstm = con.prepareCall(req);
	cstm.execute();
}

static Vector NoPayedList(String tab, String[] args, String code_c,Connection con) throws SQLException{
	Vector ok = new Vector();
	Statement stm = con.createStatement();
	String req = "select "+args[0]+" from "+tab+" where "+args[1]+" > "+args[2]+" and "+args[4]+" like '"+code_c+"' ";
		
	ResultSet rst = stm.executeQuery(req);
	boolean exist = false;
	while(rst.next()) {
		for(int i = 0; i<ok.size(); i++) if(rst.getString(1).equals(ok.elementAt(i))){
																					  exist = true;
																					  break;
																					  }
		if(!exist)ok.addElement(rst.getString(1));
		exist = false;
	}
	return ok;
}
	
static double[] PVNoPayedList(String tab, String nmf, String[] args, Connection con) throws SQLException{
	Statement stm = con.createStatement();
	String req = "select "+args[1]+" from "+tab+" where "+args[0]+" like '"+nmf+"' ";
	ResultSet rst = stm.executeQuery(req);
	double[] tb = new double[2]; 
	while(rst.next()) {
		tb[0]= rst.getDouble(1);
	}
	
	 req = "select sum("+args[2]+") from "+tab+" where "+args[0]+" like '"+nmf+"' ";
	 rst = stm.executeQuery(req);
	
	while(rst.next()) {
		tb[1]= rst.getDouble(1);
	}
	
	return tb;
}
	

public static void payedInvoice() throws SQLException{
	 
	String req = "declare " +
				 "Vrs number(11,2) := 0;" +
				 "begin " +
				 "for item in (select NUMFCT, PRIXTTC from TRANS_CREANCE_FCT )loop " +
				 "select SUM(VERSEMENT) into Vrs from TRANS_CREANCE_FCT where NUMFCT like item.NUMFCT; " +
				 "IF(item.PRIXTTC <= Vrs)THEN " +
				 "update TRANS_FACTURATION set PAYE = 1 where NUMFCT like item.NUMFCT; " +
				 "END IF;" +
				 "end loop; " +
				 "end; ";
	
	CallableStatement cstm = FirstPg.con.prepareCall(req);
	cstm.execute();
 }


public static void droppedInvoice() throws SQLException{
	String req = "declare " +
				"Vrs number(11,2) := 0;" +
				"begin " +
				"for item in (select NUMFCT, PRIXTTC from TRANS_CREANCE_FCT )loop " +
				"select SUM(VERSEMENT) into Vrs from TRANS_CREANCE_FCT where NUMFCT like item.NUMFCT; " +
				"IF(item.PRIXTTC > Vrs)THEN " +
				"update TRANS_FACTURATION set PAYE = 0 where NUMFCT like item.NUMFCT; " +
				"END IF;" +
				"end loop; " +
				"end; ";
	CallableStatement cstm = FirstPg.con.prepareCall(req);
	cstm.execute();
	 
 }


}
