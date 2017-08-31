package com.stpl.automation.step_definitions_test;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * @author jdoriya 12-Jun-2017
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/checkout_smoke_parex_usa.feature", plugin = { "pretty",
		"html:target/cucumber-html-report","json:target/cucumber-report.json" }, tags = {})
public class RunCucumberTest {

}
