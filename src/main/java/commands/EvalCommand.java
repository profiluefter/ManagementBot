package commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.DiscordChannelWriter;

import javax.tools.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class EvalCommand implements Command {
	@Override
	public boolean execute(List<String> args, MessageReceivedEvent event) {
		try {
			String source = "package test; public class Test { public static void main(String[] args) {System.out.println(\"LOHL\");} }";

			File root;
			root = Files.createTempDirectory("managementBot").toFile();
			File sourceFile = new File(root, "test/Test.java");
			sourceFile.getParentFile().mkdirs();
			Files.write(sourceFile.toPath(), source.getBytes(StandardCharsets.UTF_8));

			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			compiler.run(null, null, null, sourceFile.getPath());

			URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{root.toURI().toURL()});
			Class<?> cls = Class.forName("test.Test", true, classLoader);
			cls.getMethod("main",String[].class).invoke(null,new Object[] {new String[]{}});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return false;
	}

	private void compile(MessageReceivedEvent event, File file) {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

		Writer out = new DiscordChannelWriter(event.getTextChannel());
		Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(Collections.singleton(file));
		DiagnosticListener<? super JavaFileObject> diagnosticListener = diagnostic -> {
			try {
				new BufferedWriter(out).write(diagnostic.getMessage(Locale.ENGLISH));
			} catch (IOException e) {
				e.printStackTrace();
			}
		};

		compiler.getTask(out, fileManager, diagnosticListener,
				null, null, compilationUnits).call();
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
