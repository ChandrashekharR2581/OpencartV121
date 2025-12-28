package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {
	
	@Test (groups= {"Regression","Master"})
	public void verify_Account_Registration() 
	{
		logger.info("********* Starting TC001_AccountRegistrationTest ********");
		
		try {
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickRegister();
		
		AccountRegistrationPage regpage=new AccountRegistrationPage(driver);
		regpage.setFirstName(randomeString().toUpperCase());
		regpage.setLastName(randomeString().toUpperCase());
		regpage.setEmail(randomeString()+"@gmail.com");
		regpage.setTelephone(randomeNum());
		
		String Pswd=randomeAlphaNumeric();
		regpage.setPassword(Pswd);
		regpage.setConfirmPassword(Pswd);
		
		regpage.setPrivacyPolicy();
		regpage.clickContinue();
		
		logger.info("Validating expected message");
		String confmsg=regpage.getConfirmationMessage();
		
		Assert.assertEquals(confmsg, "Your Account Has Been Created!");
		}
		catch(Exception e)
		{
			logger.debug(e);
			logger.error("Test failed");
			Assert.fail();
			
		}
	}
	
	
	

}
