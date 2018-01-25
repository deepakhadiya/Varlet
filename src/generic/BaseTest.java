package generic;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.automationtesting.excelreport.Xl;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest implements Constant{

	public WebDriver driver;
	public DesiredCapabilities dc;
	
	static
	{
		System.setProperty(CHROME_KEY, CHROME_VALUE);
		System.setProperty(GECKO_KEY, GECKO_VALUE);
		System.setProperty(IE_KEY, IE_VALUE);
	}
	
	@Parameters({"browser","node"})
	@BeforeMethod(alwaysRun=true)
	public void openApplicaton(@Optional("chrome") String browser,@Optional("localhost") String node) throws MalformedURLException
	{
		dc=new DesiredCapabilities();
		dc.setBrowserName(browser);
		URL system=new URL("http://"+node+":4444/wd/hub");
		driver=new RemoteWebDriver(system,dc);
		driver.manage().timeouts().implicitlyWait(Long.parseLong(Lib.getPropertyValue(CONFIG_PATH, "ITO")), TimeUnit.SECONDS);
		driver.get(Lib.getPropertyValue(CONFIG_PATH, "URL"));
	}
	
	@AfterMethod(alwaysRun=true)
	public void closeApplication(ITestResult result)
	{
		
		if(result.getStatus()==ITestResult.FAILURE)
		{
			Date dateObj=new Date();
			DateFormat df=new SimpleDateFormat("dd_MM_yy HH_mm_ss");
			String dateAndTime=df.format(dateObj);
			String pathWithName=SCREENSHOT_PATH+dateAndTime.substring(0, 8)+"/"+dc.getBrowserName()+"_"+result.getName()+"_"+dateAndTime.substring(9)+".png";
			Lib.takeScreenshot(driver, pathWithName);
		}
		driver.quit();
	}
	
	@AfterSuite(alwaysRun=true)
	public void generateSuiteReport() throws Exception
	{
		DateFormat df=new SimpleDateFormat("dd_MM_yy & HH_mm_ss");
		Date dateObj=new Date();
		String dateAndTime=df.format(dateObj);
		String xlFileName=dateAndTime+".xlsx";
		Xl.generateReport(EXCELREPORT_PATH, xlFileName);
	}
	
}
