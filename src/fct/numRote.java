package fct;

import java.util.Date;
import java.util.Vector;

public class numRote {


	public static void main(String[] args) {
     Vector vct = new Vector();
		for(int i = 1; i <= 9; i++) vct.addElement("000"+i+"/05/2018");
		System.out.println(BonNum(vct));
	}
	
	public static String factureNum(Vector vctFact){
		
	
		Vector vct = new Vector();
		String numFact = "";
		String annee = "";
		
		if(vctFact.size()==0){
			 annee = dt.Annee(new Date());
		}else{
			 annee = vctFact.elementAt(0).toString();
			 annee = annee.substring(annee.indexOf("/")+1, annee.length());
		}
	   
		
		
		String svct = "";
		int fctToInt = 0;
		String an = "";
		for(int i = 0; i < vctFact.size(); i++){
			
			if(vctFact.size() > 0 && (vctFact.elementAt(i).toString().length() != 9 || !vctFact.elementAt(i).toString().contains("/"))){
				return null;
			}
			
		    svct = (String) vctFact.elementAt(i);
			svct = svct.substring(0, svct.length()-5);
			
			 an = vctFact.elementAt(i).toString();
			 an = an.substring(an.indexOf("/")+1, an.length());
			if(an.equals(annee)){
					 try {
					    	fctToInt = Integer.parseInt(svct);
					    	vct.addElement(fctToInt);
						} catch (Exception e) {
							return null;
						}
			}else{
				return null;
			}
		   
			
		}
		vct = OrdIntMnMx(vct);
		
		
			for(int i = 0; i < vct.size(); i++){
				for(int j = 0; j < vct.size(); j++){
					if(vct.elementAt(i) == vct.elementAt(j) && j!=i){
						return null;
					}
				}
			}
		

			boolean ok = false;
			
			if(vct.size()==0) 
					numFact = "000"+1+"/"+annee;
			else{
					String nm = "";
					for(int i = 0; i < vct.size(); i++){
						if((int)vct.elementAt(i) != i+1){
							nm = (i+1)+"";
							for(int j = nm.length(); j < 4; j++){
								nm = "0"+nm;
							}
							numFact = nm+"/"+annee;
							ok = true;
							break;
						}
					}
					if(!ok){
						nm = (vct.size()+1)+"";
						for(int j = nm.length(); j < 4; j++){
							nm = "0"+nm;
						}
						numFact = nm+"/"+annee;
					 }
			}
		
		return numFact;
	}
		
	public static String BonNum(Vector vctBon){

		Vector vct = new Vector();
		String numFact = "";
		String mois = "";
		String annee = "";
		
		if(vctBon.size()==0){
			 annee = dt.Annee(new Date());
			 mois = dt.Mois(new Date());
		}else{
			 annee = vctBon.elementAt(0).toString();
			 mois = vctBon.elementAt(0).toString();
			 annee = annee.substring(annee.length()-4, annee.length());
			 mois = mois.substring(mois.length()-7, mois.length()-5);
		}
	   
		
		
		String svct = "";
		int fctToInt = 0;
		String an = "";
		String ms = "";
		for(int i = 0; i < vctBon.size(); i++){
			
			if(vctBon.size() > 0 && (vctBon.elementAt(i).toString().length() != 12 || !vctBon.elementAt(i).toString().contains("/"))){
				 return null;
			}
			
		    svct = (String) vctBon.elementAt(i);
			svct = svct.substring(0, svct.length()-8);
			
			 an = vctBon.elementAt(i).toString();
			 an = an.substring(an.length()-4, an.length());
			 
			 ms = vctBon.elementAt(i).toString();
			 ms = ms.substring(ms.length()-7, ms.length()-5);
			 
			if((ms+"/"+an).equals(mois+"/"+annee)){
					 try {
					    	fctToInt = Integer.parseInt(svct);
					    	vct.addElement(fctToInt);
						} catch (Exception e) {
							return null;
						}
			}else{
				return null;
			}
		   
			
		}
		vct = OrdIntMnMx(vct);
		
		
			for(int i = 0; i < vct.size(); i++){
				for(int j = 0; j < vct.size(); j++){
					if(vct.elementAt(i) == vct.elementAt(j) && j!=i){
						return null;
					}
				}
			}
		

			boolean ok = false;
			
			if(vct.size()==0) 
					numFact = "000"+1+"/"+mois+"/"+annee;
			else{
					String nm = "";
					for(int i = 0; i < vct.size(); i++){
						if((int)vct.elementAt(i) != i+1){
							nm = (i+1)+"";
							for(int j = nm.length(); j < 4; j++){
								nm = "0"+nm;
							}
							numFact = nm+"/"+mois+"/"+annee;
							ok = true;
							break;
						}
					}
					if(!ok){
						nm = (vct.size()+1)+"";
						for(int j = nm.length(); j < 4; j++){
							nm = "0"+nm;
						}
						numFact = nm+"/"+mois+"/"+annee;
					 }
			}
		
		return numFact;
	}
	
	public static Vector OrdIntMnMx (Vector vct){
		Vector vctOrd = new Vector();
		Vector save = vct;	
		int x = 0;
		while(save.size()>0){
			for(int i = 0 ; i < save.size(); i++) x = min(save);
				vctOrd.addElement(x);
				save.removeElementAt(save.indexOf(x));
		}
		return vctOrd;
	}
	
	public static Vector OrdIntMxMn (Vector vct){
		Vector vctOrd = new Vector();
		Vector save = vct;	
		int x = 0;
		while(save.size()>0){
			for(int i = 0 ; i < save.size(); i++) x = max(save);
				vctOrd.addElement(x);
				save.removeElementAt(save.indexOf(x));
		}
		return vctOrd;
	}
	
	public static int min(Vector vct){
		int mn = 999999999;
		for(int i = 0; i < vct.size(); i++){
			if(mn > (int) vct.elementAt(i) && (int) vct.elementAt(i) > 0){
				mn = (int) vct.elementAt(i);
			}
		}
		return mn;
	}
	
	public static int max(Vector vct){
		int mx = -1;
		for(int i = 0; i < vct.size(); i++){
			if(mx < (int) vct.elementAt(i) && (int) vct.elementAt(i) > 0){
				mx = (int) vct.elementAt(i);
			}
		}
		return mx;
	}
	

}
