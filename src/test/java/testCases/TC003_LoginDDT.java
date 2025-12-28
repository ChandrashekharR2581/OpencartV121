package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {

	@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class,groups="Datadriven")   //getting data provider from diff class
	public void verify_loginDDT(String email, String pwd, String exp)
	{  
		logger.info("*******TC003_LoginDDT Started*******");
		try {
		//homepage
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//login
		LoginPage lp=new LoginPage(driver);
		logger.info("LoginPage Initiated");
		lp.setemail(email);
		logger.info("Email Address added");

		lp.setPassword(pwd);
		lp.clickMyLogin();
		
		MyAccountPage mac=new MyAccountPage(driver);
		boolean targetPage=mac.isMyAccountPageExists();
		
		if(exp.equalsIgnoreCase("Valid"))
		{
			if(targetPage==true)
			{
				mac.clickLogout();
				Assert.assertTrue(true);
				
			}
			else
			{
				Assert.assertTrue(false);
			}
		}
		if(exp.equalsIgnoreCase("Invalid"))
		{
			if(targetPage==true)
			{
				mac.clickLogout();
				Assert.assertTrue(false);
				
			}
			else
			{
				Assert.assertTrue(true);
			}
		}
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		logger.info("******TC003_LoginDDT finished*****");
		
		
		
	}
	
	
	
}
