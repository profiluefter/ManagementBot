package config;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import util.Strings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DatabaseTest {

	@BeforeClass
	public static void setUp() throws Exception {
		Database.init();
		User user = User.loadUser(123456789L);
		user.setLanguage(Strings.Lang.DE);
		user.addPermission("testPermission");
	}

	@AfterClass
	public static void tearDown() throws Exception {
		Database.deleteUser(User.loadUser(123456789L));
		Database.cleanUp();
	}

	@Test
	public void loadUser() throws SQLException {
		ResultSet resultSet = Database.loadUser(123456789L);
		String language = resultSet.getString("language");
		Assert.assertEquals("DE", language);
	}

	@Test
	public void loadPermissions() {
		List<String> strings = Database.loadPermissions(123456789L);
		Assert.assertNotNull(strings);
		Assert.assertEquals("testPermission", strings.get(0));
	}

	@Test
	public void saveUser() throws SQLException {
		User user = User.loadUser(123456789L);
		user.setLanguage(Strings.Lang.EN);
		Database.saveUser(user);
		Assert.assertEquals(Database.loadUser(123456789L).getString(2), "EN");
		user.setLanguage(Strings.Lang.DE);
	}
}