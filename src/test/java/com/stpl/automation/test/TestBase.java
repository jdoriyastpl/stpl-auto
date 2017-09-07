package com.stpl.automation.test;

import java.io.Closeable;

import org.junit.AfterClass;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeSuite;

import com.stpl.automation.exceptions.AutomationException;
import com.stpl.automation.stplstores.storepages.StoreLandingPage;
import com.stpl.automation.webdriver.page.BasePage;

/**
 * @author jdoriya 05-06-2017
 */
// @ContextConfiguration(locations = {"classpath:auto_context.xml"})
// @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class TestBase extends AbstractTestNGSpringContextTests {
	
	public ApplicationContext applicationContext;

	protected void initializeSystemProperties() {

		applicationContext = new ClassPathXmlApplicationContext("ApplicationContext.xml");
		applicationContext.getAutowireCapableBeanFactory().autowireBean(this);
		
	}
	
}
