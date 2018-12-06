package eval;

import java.net.URL;
import java.net.URLClassLoader;

class RestrictingClassLoader extends URLClassLoader {
	RestrictingClassLoader(URL[] urls) {
		super(urls);
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		if(name.startsWith("eval.environment"))
			return super.loadClass(name);
		else
			return findClass(name);
	}
}
