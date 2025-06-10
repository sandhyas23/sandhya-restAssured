package runner;
import org.testng.annotations.DataProvider;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

	@CucumberOptions(plugin = { "pretty",
			"html:target/Report.html" }, monochrome = false, features = {
					"src/test/resources/features" }, glue = { "stepDefinitions", "hooks" })

	public class TestRunner extends AbstractTestNGCucumberTests {

		@Override
		//@Parameters({"browser"})
		@DataProvider(parallel = false)
		public Object[][] scenarios() {
			return super.scenarios();
		}



}
