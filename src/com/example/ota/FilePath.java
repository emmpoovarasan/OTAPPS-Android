package com.example.ota;

import java.io.File;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


public class FilePath {

	public static String getExternalPath() {
		
		String actualPathName = null;

		try {
			if (Environment.getExternalStorageDirectory().isDirectory() == true) {
				/*Log.d("ExternalStoageDirectory Found",
						"ExternalStoageDirectory is found "
								+ Environment.getExternalStorageDirectory()
										.isDirectory());*/
				String pathName = Environment.getExternalStorageDirectory().toString()
						+ "//OTA";
				//Log.d("Files", "Path : " + pathName);
				File f = new File(pathName);
				File fl[] = f.listFiles();
				//Log.d("Files", "Size : " + fl.length);
				for (int i = 0; i < fl.length; i++) {
					//Log.d("Files", "File Name : " + fl[i].getName());
					//Log.d("Files", "AbsFile Path : " + fl[i].getAbsolutePath());
					if("ORDER APPS.xls".equalsIgnoreCase(fl[i].getName())){
						actualPathName = fl[i].getAbsolutePath();
					}
				}

			} else {
				/*Log.d("No ExternalStoageDirectory",
						"No ExternalStoageDirectory");*/
				Toast.makeText(null, "External Stoage is not found. Please insert memory card.", Toast.LENGTH_LONG).show();
				actualPathName = null;

			}
		} catch (Exception e) {
			//Log.d("File Not Found", "File Not Found");
			Toast.makeText(null, "File not found. Please check your memory card", Toast.LENGTH_LONG).show();
			actualPathName = null;
		}
		return actualPathName;
	}
	
	public static String getPropertyFilePath(){
		String actualPathName = null;

		try {
			if (Environment.getExternalStorageDirectory().isDirectory() == true) {
				/*Log.d("ExternalStoageDirectory Found",
						"ExternalStoageDirectory is found "
								+ Environment.getExternalStorageDirectory()
										.isDirectory());*/
				String pathName = Environment.getExternalStorageDirectory().toString()
						+ "//OTA";
				//Log.d("Files", "Path : " + pathName);
				File f = new File(pathName);
				File fl[] = f.listFiles();
				//Log.d("Files", "Size : " + fl.length);
				for (int i = 0; i < fl.length; i++) {
					/*Log.d("Files", "File Name : " + fl[i].getName());
					Log.d("Files", "AbsFile Path : " + fl[i].getAbsolutePath());*/
					if("OTAAPP.properties".equalsIgnoreCase(fl[i].getName())){
						actualPathName = fl[i].getAbsolutePath();
					}
					
				}

			} else {
				/*Log.d("No ExternalStoageDirectory",
						"No ExternalStoageDirectory");*/
				Toast.makeText(null, "External Stoage is not found. Please insert memory card.", Toast.LENGTH_LONG).show();
				actualPathName = null;

			}
		} catch (Exception e) {
			//Log.d("File Not Found", "File Not Found");
			Toast.makeText(null, "File not found. Please check your memory card", Toast.LENGTH_LONG).show();
			actualPathName = null;
		}
		return actualPathName;
	}

}
