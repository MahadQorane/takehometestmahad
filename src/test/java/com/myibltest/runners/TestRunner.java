package com.myibltest.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * Cucumber test runner
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.myibltest.stepdef"},
        plugin = {
                "pretty",
                "json:target/cucumber-reports/cucumber.json",
                "html:target/cucumber-reports/cucumber.html",
                "junit:target/cucumber-reports/cucumber.xml"
        },
        monochrome = true,
        // tags = "@smoke" // Uncomment to run only smoke tests
        dryRun = false
)
public class TestRunner {
    // This class is intentionally empty. It's used only as a holder for runner annotations
}
