package testRunners;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import utilities.SendMail;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = { "src/test/resources/Features/" }, 
		glue = { "stepDefinitions","hooks"},
//		tags = "" ,
		stepNotifications = true,
		monochrome = false,
		dryRun=false,
		plugin = {
				"pretty",
				"json:target/json-report/cucumber.json",
				"html:target/cucumberreport/cucumber.html",
		})


public class TestRunner {
	@AfterClass
	public static void sendmail()
	{
		 String message="Hello , Dear, this is message for security check . ";
	        String subject ="Automation Script Report";
	        String to="";    // mail id to whom mail will be delevered.x
	        String from= ""; // mail id of from whom mail will be send.
	        SendMail.sendEmail(message,subject,to,from);
	}

}