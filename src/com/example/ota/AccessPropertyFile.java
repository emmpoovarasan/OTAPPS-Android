package com.example.ota;

import java.io.FileInputStream;
import java.util.Properties;

import android.util.Log;
import android.widget.Toast;

public class AccessPropertyFile {
	
	public static String getAccessPropertyValue(String typeOfValue){
		String filePath = null;
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(FilePath.getPropertyFilePath()));
			if("dataBaseDriver".equalsIgnoreCase(typeOfValue)){
				filePath = prop.getProperty("dataBaseDriver");	
			}
			if("URL".equalsIgnoreCase(typeOfValue)){
				filePath = prop.getProperty("URL");	
			}
			if("userName".equalsIgnoreCase(typeOfValue)){
				filePath = prop.getProperty("userName");	
			}
			if("userPassword".equalsIgnoreCase(typeOfValue)){
				filePath = prop.getProperty("userPassword");	
			}
			//System.out.println(filePath);
			//Log.d("File Path", filePath);
			
		} catch (Exception e) {
			//System.out.println(e.getMessage());
			//Log.d("Exception", e.getMessage());
			Toast.makeText(null, e.getMessage(), Toast.LENGTH_LONG).show();
		}
		return filePath;
	}
}
