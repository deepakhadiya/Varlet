package script;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import generic.BaseTest;


public class VerifyLoginPage extends BaseTest{
	
	@Test
	public void testLoginPage()
	{
		Reporter.log("LoginPage displayed",true);
		Assert.fail();
	}

}
