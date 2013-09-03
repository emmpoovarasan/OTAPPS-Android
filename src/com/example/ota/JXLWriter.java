package com.example.ota;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.DateFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class JXLWriter {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File fp = new File(FilePath.getExternalPath());
		
		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("en","EN"));
		
		Date now = Calendar.getInstance().getTime();
		SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		String theDate = df.format(now);
		System.out.println(theDate);
		
		WritableWorkbook workbook = Workbook.createWorkbook(fp, wbSettings);
		workbook.createSheet(theDate, 0);
		
		
		
	}

}
