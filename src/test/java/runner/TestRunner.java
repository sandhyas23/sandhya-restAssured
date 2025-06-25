package runner;
import org.testng.annotations.DataProvider;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

	@CucumberOptions(plugin = { "pretty",
			"html:target/Report.html","html:target/cucumber-reports/cucumber.html",
    		"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" }, 
			tags = "@patch or @put",
			monochrome = false, features = {
					"src/test/resources/features" }, glue = { "stepDefinitions", "hooks" })

	public class TestRunner extends AbstractTestNGCucumberTests {

		@Override
		//@Parameters({"browser"})
		@DataProvider(parallel = false)
		public Object[][] scenarios() {
			return super.scenarios();
		}



}
