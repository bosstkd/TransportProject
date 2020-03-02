package fct;

import java.util.Date;


public class str {
	
	
	public static void main(String[] args) {
		System.out.println( nameForm("      yacine nanana"));
	}
	
public static boolean isName(String str){
	boolean ok = true;
	if(str.length() < 1){
		ok = false;
	}else{
		for(int i = 0; i < str.length(); i++){
				try {
					Integer.parseInt(str.substring(i, i+1));
					ok = false;
				} catch (Exception e) {}
		}
	}
	
	return ok;
}

public static boolean isMail(String str){
	
	boolean ok = true;
	if(str.length() < 9){
		ok = false;
	}else{
		int adr = 0;
		int pnt = 0;
		if(str.contains("@") && str.contains(".")){
				adr = str.indexOf("@");
				pnt = str.indexOf(".");
				if(pnt < adr) ok = false;
		adr = 0;
		pnt = 0;
				for(int i = 0; i < str.length(); i++){
					if(str.charAt(i) == '@') adr ++;
				}
		if(adr > 1) ok = false;			
		String fin = str.substring(str.indexOf("."), str.length());
		if(fin.length() < 3 || fin.length()>5) ok = false;
		}else ok = false;
		if(str.contains(" ")) ok = false;
		if(str.contains("'")) ok = false;
		if(str.contains("/")) ok = false;
		if(str.contains("\""))ok = false;
		if(str.contains("/")) ok = false;
		if(str.contains(",")) ok = false;
		if(str.contains("#")) ok = false;
		if(str.contains("$")) ok = false;
		if(str.contains("&")) ok = false;
		if(str.contains("|")) ok = false;
		if(str.contains("<")) ok = false;
		if(str.contains(">")) ok = false;
		if(str.contains("%")) ok = false;
	}

	return ok;
}

public static String nameForm(String str){
	if(!isName(str)){
		str = null;
	}else{
		boolean ok = true;
		str.replaceAll("-", " ");
		str.replaceAll("_", " ");
		
		while(str.substring(0, 1).equals(" ")) {
			try {
				str = str.substring(1, str.length());
			} catch (Exception e) {
				str = null;
				ok = false;
				break;
			}
		}
	if(ok)
		while(str.substring(0, 1).equals("_")) {
			try {
				str = str.substring(1, str.length());
			} catch (Exception e) {
				str = null;
				ok = false;
				break;
			}
		}

	if(ok)
		while(str.contains("  ")) {
			str = str.replaceAll("  ", " ");
		}
	
	if(ok)
		while(str.substring(str.length()-1, str.length()).equals(" "))str = str.substring(0, str.length()-1); 
		
		if(!isName(str)){
			str = null;
			ok = false;
		}
		
		if(ok){
			str = str.substring(0, 1).toUpperCase() + str.substring(1, str.length()).toLowerCase();
			for(int i = 0; i<str.length(); i++){
				if(str.substring(i, i+1).equals(" ")){
					
					str = str.substring(0, i+1) + str.substring(i+1, i+2).toUpperCase() + str.substring(i+2, str.length()).toLowerCase();
				}
			}
		}
		
		
	}
	return str;
}

public static String strToBdd(String str){
	if(str.contains("'")){
		while(str.substring(0, 1).equals("'")){
			try {
				str = str.substring(1, str.length());	
			} catch (Exception e) {
				str = null;
				break;
			}
		}
		if(str != null){
			while(str.substring(str.length()-1, str.length()).equals("'")){
				try {
					str = str.substring(0 ,str.length()-1);
				} catch (Exception e) {
					str = null;
					break;
				}
			}
			if(str != null){
				if(str.length() > 0){
										str = str.replaceAll("'", "''");
										while(str.contains("'''")) str = str.replaceAll("'''", "''");
									}else{
										str = null;
									}
			}
			
		}
		
		
	}
	return str;
}

public static boolean psw(String str){
	boolean ok = true;
	
	if(str.contains("'")) ok = false;
	if(str.length() < 8) ok = false;
	
	return ok;
}

public static String getInt(String nb){
	String str = "";
	boolean neg = false;
	 for(int i = 0; i < nb.length(); i++){
		 if(i == 0 && nb.substring(0,1).equals("-")) neg = true;
		 try {
			   Integer.parseInt(nb.substring(i, i+1));
			   str = str + nb.substring(i, i+1);
		 } catch (Exception e) {}
	 }
	 if(neg) str = "-"+str;
	return str;
}

public static String getFloat(String nb){
	String str = "";
	boolean neg = false;
	 for(int i = 0; i < nb.length(); i++){
		 try {
			 if(i == 0 && nb.substring(0,1).equals("-")) neg = true;
			 if(nb.substring(i, i+1).equals(".")){
				 str = str + nb.substring(i, i+1);
			 }else{
				 Integer.parseInt(nb.substring(i, i+1));
			     str = str + nb.substring(i, i+1);
			 }
			   
		 } catch (Exception e) {}
	 }
	 if(neg) str = "-"+str;
	return str;
}

public static boolean isEmpty(String str){
	boolean ok = false;
	if(str.equals("") || str == null)ok = true;
	return ok;
}

}
