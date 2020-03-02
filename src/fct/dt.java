package fct;

import java.awt.Font;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdesktop.swingx.JXDatePicker;

public class dt extends JXDatePicker{
	
	public static void main(String[] args) {
		System.out.println(addYear(new Date(),3));
		System.out.println(nbrYearsBetween(new Date(1626374014968L), new Date(1626374014968L)));
	}
	
public dt(){
	setDate(new Date());
	setFont(new Font("Consolas", Font.PLAIN, 11));
}
	

public static Date TSampToDate(Timestamp ts){
		Date dts = new Date(ts.getTime());
		return dts;
}	

public static String TSampToDateStr(Timestamp ts){
	Date dts = new Date(ts.getTime());
	String dt = form_Aff(dts);
	return dt;
}	

public static Timestamp dateToTStamp(Date dt){
	Timestamp ts = new Timestamp(dt.getTime());
	return ts;
}

public static String TSampToHour(Timestamp ts){
	Date dts = new Date(ts.getTime());
	String dt = new SimpleDateFormat("HH:mm:ss").format(dts);
	return dt;
}	
	
public static String form_Aff(Date dt){
	String dts = new SimpleDateFormat("dd/MM/yyyy").format(dt);
	return dts;
}
	
public static String form_Insr(Date dt){
	String dts = new SimpleDateFormat("yyyy-MM-dd").format(dt);
	return dts;
}

public static String Jour(Date dt){
	String dts = new SimpleDateFormat("dd").format(dt);
	return dts;
}

public static String Mois(Date dt){
	String dts = new SimpleDateFormat("MM").format(dt);
	return dts;
}

public static String Annee(Date dt){
	String dts = new SimpleDateFormat("yyyy").format(dt);
	return dts;
}

public static long nuitee (Date dt_a, Date dt_d){
	   long nb_nuit = (long) (dt_d.getTime() - dt_a.getTime()) / (1000 * 60 * 60 * 24); 
	   return nb_nuit;
}
	
public static boolean superieur(Date dt_1, Date dt_2){
	long dif = (long) dt_2.getTime() - (long) dt_1.getTime();
			if(dif <= 0) 
				 return false;
			else 
				 return true;
}

public static Date strToDate(String str_dt, String formDate){
	Date dt = new Date();
	try {
		dt = new SimpleDateFormat(formDate).parse(str_dt);
	} catch (ParseException e) {
		e.printStackTrace();
	}
	return dt;
}

public static boolean dateIntoPeriode(Date dt_a, Date dt_d, Date dtANew, Date dtDNew){
	
boolean ok = false;
	
	long dt_a_l = dt_a.getTime();
	long dt_d_l = dt_d.getTime();
	long dt_an_l = dtANew.getTime();
	long dt_dn_l = dtDNew.getTime();

	if(dt_dn_l < dt_an_l && !ok) {
		ok = true;	
	}
	if(dt_an_l == dt_a_l && !ok) {
		ok = true;
	}
	if((dt_an_l < dt_d_l && dt_an_l >= dt_a_l) && !ok) {
		ok = true;
	}

	 if((dt_an_l < dt_a_l && dt_dn_l >= dt_d_l) && !ok){
		 ok = true;
	 }
	
return ok;
}

public static boolean isInPeriode(Date dt_a, Date dt_d, Date dt_test){
	boolean ok = false;
	long dt1 = dt_a.getTime();
	long dt2 = dt_d.getTime();
	long dt3 = dt_test.getTime();
	
	if(dt3 >= dt1 && dt3 < dt2 ) ok = true;
	
	return ok;
}

public static boolean isValidDateRes(Date dt_a, Date dt_d, Date dt_r){
	
	boolean ok = true;
	
	long dt1 = dt_a.getTime();
	long dt2 = dt_d.getTime();
	long dt3 = dt_r.getTime();
	
	if(dt2 <= dt1) ok = false;
	if(dt3 > dt1) ok = false;
	
	return ok;
}

public static long addYear(Date dtAct, int yearNbr){
	long newDate = 0;
	long nb = 31536000000L;
	
		newDate = dtAct.getTime() + nb*yearNbr;
	
	return newDate;
}

public static Date longToDate(long nb){
	Date dte = new Date(nb);
	return dte;
}

public static double nbrYearsBetween(Date dtSup, Date dtInf){
	double dif = dtSup.getTime() - dtInf.getTime();
	dif = dif/31536000000L;
	return dif;
}

}
