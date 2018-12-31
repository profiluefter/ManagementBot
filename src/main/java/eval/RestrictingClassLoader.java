package eval;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;

/**
 * ClassLoader used to only allow some specified Classes.
 */
class RestrictingClassLoader extends URLClassLoader {
	/**
	 * The classes that are allowed when executing custom code.
	 */
	private List<String> allowedClasses = Arrays.asList(
			"eval.environment.IO",
			"java.lang.Object",
			"java.lang.String",
			"java.lang.invoke.StringConcatFactory"
	);

	RestrictingClassLoader(URL[] urls) {
		super(urls);
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		if(allowedClasses.contains(name))
			return super.loadClass(name);
		else
			return findClass(name);
	}
}
