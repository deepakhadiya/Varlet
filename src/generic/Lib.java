package generic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

public class Lib {

	public static String getPropertyValue(String path,String key)
	{
		String value="";
		Properties p=new Properties();
		try {
			p.load(new FileInputStream(path));
			value=p.getProperty(key);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return value;
	}
	
	
	public static String getCellValue(String path,String sheet, int row, int cell)
	{
		String value="";
		try {
			Workbook wb=WorkbookFactory.create(new FileInputStream(path));
			value=wb.getSheet(sheet).getRow(row).getCell(cell).getStringCellValue();
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return value;
	}
	
	public static int lastRowNum(String path,String sheet)
	{
		int num=0;
		try {
			Workbook wb=WorkbookFactory.create(new FileInputStream(path));
			num=wb.getSheet(sheet).getLastRowNum();
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return num;
	}
	
	public static void takeScreenshot(WebDriver driver,String pathWithName)
	{
		File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File destFile=new File(pathWithName);
		try {
			FileUtils.copyFile(srcFile, destFile);
			Reporter.log("Screenshot saved to : "+destFile.getAbsolutePath(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
