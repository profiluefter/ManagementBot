package commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import config.Config;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.lang.reflect.Method;

public class EvalCommand implements Command {
	@Override
	public boolean execute(List<String> args, MessageReceivedEvent event) {
		try {
			String source = event.getMessage().getContentStripped().replaceFirst(Config.get("prefix") + getName(),"");
			
			File root = Files.createTempDirectory("managementBot").toFile();
			File sourceFile = new File(root, "eval/Test.java");
			sourceFile.getParentFile().mkdirs();
			Files.write(sourceFile.toPath(), source.getBytes(StandardCharsets.UTF_8));

			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			StandardJavaFileManager fileManager = compiler.getStandardFileManager(null,null,null);
			compiler.getTask(null,null,null,null,null,fileManager.getJavaFileObjectsFromFiles(Collections.singleton(sourceFile))).call();

			URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{root.toURI().toURL()});
			Class<?> cls = Class.forName("eval.Test", true, classLoader);
			Method method = cls.getMethod("main",String[].class);
			method.setAccessible(true);
			method.invoke(null,new Object[] {new String[]{}});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return false;
	}

	@Override
	public String getName() {
		return "eval";
	}

	@Override
	public String getHelp(MessageReceivedEvent event) {
		return null;
	}
}
