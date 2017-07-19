package com.sarvika.automation.test;

import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * @author jdoriya 05-06-2017
 */
// @ContextConfiguration(locations = {
// "classpath:stpl/spring/store_context.xml",
// })
// @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class TestBase extends AbstractTestNGSpringContextTests {

	public ApplicationContext applicationContext;

}
