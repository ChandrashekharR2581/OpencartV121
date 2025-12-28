package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {

	@Test(groups={"Sanity","Master"})
	public void verify_login()
	{  
		logger.info("********Starting TC_002_LoginTest********");
		
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		LoginPage lp=new LoginPage(driver);
		logger.info("LoginPage Initiated");
		lp.setemail(p.getProperty("email"));
		logger.info("Email Address added");

		lp.setPassword(p.getProperty("password"));
		lp.clickMyLogin();
		
		MyAccountPage mac=new MyAccountPage(driver);
		boolean targetPage=mac.getHeader();
		
		//Assert.assertEquals(targetPage, true,"Login Failed");
		Assert.assertTrue(targetPage);
	}
	
		//logger.info("******Finished TC002_LoginTest***");
		
	}
	
	
	
	
