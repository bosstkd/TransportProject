package fct;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Vector;

import com.ibm.icu.text.RuleBasedNumberFormat;

public class nbr {
	
	
	public static void main(String[] args) {
	//	System.out.println( dbToDf(toDouble("yacine6546.36 nan55an63a",0))+", "+EnLettre(toDouble("yacine6546.36 nan55an63a",0), "Euro"));
	}
	
	
public static String chfrLtr(double dl){
	RuleBasedNumberFormat rbnf = new RuleBasedNumberFormat(Locale.FRANCE, RuleBasedNumberFormat.SPELLOUT);
	String str = rbnf.format(dl);
	return str;
}
	
public static double dfToDb(String nb){
	String rst = "";
    int x = 0;
	for(int i = 0; i < nb.length(); i++){
		try {
			x = Integer.parseInt(nb.charAt(i)+"");
			rst = rst + x;
		} catch (Exception e) {
			if(nb.charAt(i)==',' || nb.charAt(i)=='.') rst = rst+".";
		}
	}
	return Double.parseDouble(rst);
}	

public static String EnLettre(double d, String devise){
	String str0 = ""+d;
	String detect = "";
	String s1 = "";
	String s2 = "";
	int j = 0;
	for(int i = 0; i < str0.length();i++){
		detect = str0.substring(i, i+1);
		if(detect.equals(".")){
			j = i;
		}
	}
	if(j > 0){
		s1 = str0.substring(0,j);
		s2 = str0.substring(j+1,str0.length());			
	}else{
		s1 = str0.substring(0,str0.length());			
	}
	double ss1 = Double.valueOf(s1).doubleValue();
	double ss2 = Double.valueOf(s2).doubleValue();
	String chaine = chfrLtr(ss1)+" "+devise+" et "+chfrLtr(ss2)+" Centimes";
	if(s2.charAt(0)=='0' && ss2 > 0){
		 chaine = chfrLtr(ss1)+" "+devise+" et zero "+chfrLtr(ss2)+" Centimes";
	}else if(ss2<10 && ss2 >= 1){
		ss2 = ss2*10;
		 chaine = chfrLtr(ss1)+" "+devise+" et "+chfrLtr(ss2)+" Centimes";
	}
	return chaine;
}

public static String dbToDf(double tot){
	DecimalFormat df = new DecimalFormat();
	df.setMaximumFractionDigits(2);
	String ecrir = df.format(tot);
		if(ecrir.contains(",")){
		int index_point = ecrir.indexOf(",");
		    if(ecrir.length() - index_point == 2)ecrir = ecrir + "0";
		}else{
			ecrir = ecrir + ",00";
		}
     return ecrir;
}

public static boolean isInt(String str){
	try {
	    Integer.parseInt(str);
		return true;
	} catch (Exception e) {
		return false;
	}
}

public static int toInt(String str, int position){
	int i = 0;
	Vector vct = new Vector();
	String getInt = "";
	
	if(str.length()>0){
			for(int j = 0; j < str.length(); j++){
				try {
					   Integer.parseInt(str.substring(j, j+1));  
					   getInt = getInt + str.substring(j, j+1);
					 }catch (Exception e) {
					   if(!getInt.equals("")){
						    vct.addElement(getInt);
						    getInt = "";
					   }
					 }
			}
		   vct.addElement(getInt);
		
	}else{
		i = 0;
	}

	if((position + 1 > vct.size()) || position < 0) 
		i = 0;
	else{
		i = Integer.parseInt(vct.elementAt(position).toString());
	}
		
	return i;
}

public static int toInt(String str){
	int i = 0;
	  if(isInt(str)){
		  i = Integer.parseInt(str);
	  }else{
		  i = 0;
	  }
	return i;
}

public static double db2Digits(double db){
	DecimalFormat df = new DecimalFormat();
	df.setMaximumFractionDigits(2);
	String str = df.format(db);
	db = dfToDb(str);
	return db;
}

public static boolean isDouble(String str){
	try {
	    Double.parseDouble(str);
		return true;
	} catch (Exception e) {
		return false;
	}
}

public static double toDouble(String str,int position){
	double i = 0;
	Vector vct = new Vector();
	String getDouble = "";
	
	if(str.length()>0){
			for(int j = 0; j < str.length(); j++){
				try {
					   
					   Integer.parseInt(str.substring(j, j+1));  
					   getDouble = getDouble + str.substring(j, j+1);
					 }catch (Exception e) {
						if(str.substring(j, j+1).equals(".")){
							try {
								Integer.parseInt(str.substring(j-1, j));  
								getDouble = getDouble + str.substring(j, j+1);
							} catch (Exception e2) { }
						}else{
							   if(!getDouble.equals("")){
														     vct.addElement(getDouble);
														     getDouble = "";
							   							}
						}
					 
					 }
			}
		   vct.addElement(getDouble);
		
	}else{
		i = 0;
	}

	if((position + 1 > vct.size()) || position < 0) 
		i = 0;
	else{
		try {
			i = Double.parseDouble(vct.elementAt(position).toString());
		} catch (Exception e) {
			i = 0;
		}
		
	}
		
	return i;
}

public static double toDouble(String str){
	double i = 0;
	  if(isDouble(str)){
		  i = Double.parseDouble(str);
	  }else{
		  i = 0;
	  }
	return i;
}

public static double majorDb(double x){
	double opr = 0;
		
	if(x<10){
		opr = 10;
	}else if(x < 100 && x >= 11){
		double dix = x%10;
		if(dix < 1){
			opr = x - dix;
		}else{
			opr = x + (10 - dix);
		}
	}else{
		double dix = x%100;
		if(dix < 5){
			opr = x - dix;
		}else{
			opr = x + (100 - dix);
	}
	}
		return opr;
	}

}
