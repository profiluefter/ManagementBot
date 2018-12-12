package config;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConfigTest {

	@BeforeClass
	public static void setUp() throws Exception {
		Config.init();
		Config.set("testKey", "testValue");
	}

	@AfterClass
	public static void tearDown() throws Exception {
		Config.remove("testKey");
		Config.remove("anotherTestKey");
		Config.remove("lastTestKey");
		Config.cleanUp();
	}

	@Test
	public void get() {
		Assert.assertEquals("testValue", Config.get("testKey"));
	}

	@Test
	public void set() {
		Config.set("anotherTestKey", "anotherTestValue");
		Assert.assertEquals("anotherTestValue", Config.get("anotherTestKey"));
	}

	@Test
	public void remove() {
		Config.set("lastTestKey", "lastTestValue");
		Config.remove("lastTestKey");
		Assert.assertNull(Config.get("lastTestKey"));
	}
}