package com.stpl.automation.step_definitions_test;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * @author jdoriya 12-Jun-2017
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features", plugin = { "pretty",
		"html:target/cucumber-html-report" }, tags = {})
public class RunCucumberTest {

}
