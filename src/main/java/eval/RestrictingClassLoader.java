package eval;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;

class RestrictingClassLoader extends URLClassLoader {
	private List<String> allowedClasses = Arrays.asList(
			"eval.environment.IO",
			"java.lang.Object",
			"java.lang.String"
	);

	RestrictingClassLoader(URL[] urls) {
		super(urls);
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		if (allowedClasses.contains(name))
			return super.loadClass(name);
		else
			return findClass(name);
	}
}
