package config;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConfigTest {

	@Before
	public void setUp() throws Exception {
		Config.init();
		Config.set("testKey","testValue");
	}

	@After
	public void tearDown() throws Exception {
		Config.remove("testKey");
		Config.remove("anotherTestKey");
		Config.remove("lastTestKey");
		Config.cleanUp();
	}

	@Test
	public void get() {
		Assert.assertEquals("testValue",Config.get("testKey"));
	}

	@Test
	public void set() {
		Config.set("anotherTestKey","anotherTestValue");
		Assert.assertEquals("anotherTestValue",Config.get("anotherTestKey"));
	}

	@Test
	public void remove() {
		Config.set("lastTestKey","lastTestValue");
		Config.remove("lastTestKey");
		Assert.assertNull(Config.get("lastTestKey"));
	}
}