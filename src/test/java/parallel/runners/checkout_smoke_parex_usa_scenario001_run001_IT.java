package parallel.runners;

import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;
import com.stpl.automation.step_definitions_test.RunCucumberTest;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@CucumberOptions(
    monochrome = false,
    features = {"classpath:parallel/features/checkout_smoke_parex_usa_scenario001_run001_IT.feature"},
    format = {"json:target/cucumber-report/checkout_smoke_parex_usa_scenario001_run001_IT.json"},
    glue="com.stpl.automation.step_definitions_test",
    strict = false,
    dryRun = false,
    tags = {"~@ignore"}
)
public class checkout_smoke_parex_usa_scenario001_run001_IT {
}
// Generated by Cucable, Wed Sep 06 17:41:19 IST 2017

