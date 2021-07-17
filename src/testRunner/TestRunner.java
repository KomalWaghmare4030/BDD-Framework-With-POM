package testRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions
		(
			features = ".//Features//ReadJson.feature",
			glue = "stepDefinitions",
			dryRun = false,
			plugin = { "pretty", "html:target/cucumber-reports.html" },
			monochrome = true,
			tags = "@Test1"
		)

public class TestRunner {

}
