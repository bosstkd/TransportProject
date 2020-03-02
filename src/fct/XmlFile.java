package fct;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

public class XmlFile {


static public void CreateWrite(String path, String[] keys, String[] values){
	try {
		Properties properties = new Properties();
		
		for(int i = 0; i < keys.length; i++){
			properties.setProperty(keys[i], values[i]);
		}
		
		File file = new File(path);
		FileOutputStream fileOut = new FileOutputStream(file);
		properties.storeToXML(fileOut, "Mahmoudi Access file");
		fileOut.close();
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
}

static public String readValue(String path, String key){
	String info = "";
			try {
				File file = new File(path);
				FileInputStream fileInput = new FileInputStream(file);
				Properties properties = new Properties();
				properties.loadFromXML(fileInput);
				fileInput.close();
		
				Enumeration enuKeys = properties.keys();
				while (enuKeys.hasMoreElements()) {
					String ke = (String) enuKeys.nextElement();
					if(ke.equals(key)){
						String value = properties.getProperty(key);
						info = value;
					}
					
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	return info;
}

static public Vector readAllProperties(String path){
	Vector info = new Vector();
			try {
				File file = new File(path);
				FileInputStream fileInput = new FileInputStream(file);
				Properties properties = new Properties();
				properties.loadFromXML(fileInput);
				fileInput.close();
				Enumeration enuKeys = properties.keys();
				int i = 0;
				while (enuKeys.hasMoreElements()) {
					String key = (String) enuKeys.nextElement();
					//String value = properties.getProperty(key);
					//info = value;
					info.addElement(key);
					i++;
					//System.out.println(key + ": " + value);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	return info;
}

static public Vector readAllValues(String path){
	Vector vct = new Vector();
			try {
				File file = new File(path);
				FileInputStream fileInput = new FileInputStream(file);
				Properties properties = new Properties();
				properties.loadFromXML(fileInput);
				fileInput.close();
				Enumeration enuKeys = properties.keys();
				int i = 0;
				while (enuKeys.hasMoreElements()) {
					String key = (String) enuKeys.nextElement();
					String value = properties.getProperty(key);
					vct.addElement(value);
					
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	return vct;
}
	
}
