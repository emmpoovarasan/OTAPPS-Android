package com.example.ota;

import java.io.File;

import android.os.Environment;
import android.util.Log;


public class FilePath {

	public static String getExternalPath() {
		
		String actualPathName = null;

		try {
			if (Environment.getExternalStorageDirectory().isDirectory() == true) {
				Log.d("ExternalStoageDirectory Found",
						"ExternalStoageDirectory is found "
								+ Environment.getExternalStorageDirectory()
										.isDirectory());
				String pathName = Environment.getExternalStorageDirectory().toString()
						+ "//OTA";
				Log.d("Files", "Path : " + pathName);
				File f = new File(pathName);
				File fl[] = f.listFiles();
				Log.d("Files", "Size : " + fl.length);
				for (int i = 0; i < fl.length; i++) {
					Log.d("Files", "File Name : " + fl[i].getName());
					Log.d("Files", "AbsFile Path : " + fl[i].getAbsolutePath());
					if("ORDER APPS.xls".equalsIgnoreCase(fl[i].getName())){
						actualPathName = fl[i].getAbsolutePath();
					}
					
				}

			} else {
				Log.d("No ExternalStoageDirectory",
						"No ExternalStoageDirectory");
				actualPathName = null;

			}
		} catch (Exception e) {
			Log.d("File Not Found", "File Not Found");
			actualPathName = null;
		}
		return actualPathName;
	}
	
	public static String getPropertyFilePath(){
		String actualPathName = null;

		try {
			if (Environment.getExternalStorageDirectory().isDirectory() == true) {
				Log.d("ExternalStoageDirectory Found",
						"ExternalStoageDirectory is found "
								+ Environment.getExternalStorageDirectory()
										.isDirectory());
				String pathName = Environment.getExternalStorageDirectory().toString()
						+ "//OTA";
				Log.d("Files", "Path : " + pathName);
				File f = new File(pathName);
				File fl[] = f.listFiles();
				Log.d("Files", "Size : " + fl.length);
				for (int i = 0; i < fl.length; i++) {
					Log.d("Files", "File Name : " + fl[i].getName());
					Log.d("Files", "AbsFile Path : " + fl[i].getAbsolutePath());
					if("OTAAPP.properties".equalsIgnoreCase(fl[i].getName())){
						actualPathName = fl[i].getAbsolutePath();
					}
					
				}

			} else {
				Log.d("No ExternalStoageDirectory",
						"No ExternalStoageDirectory");
				actualPathName = null;

			}
		} catch (Exception e) {
			Log.d("File Not Found", "File Not Found");
			actualPathName = null;
		}
		return actualPathName;
	}

}
