package eval;

import commands.Command;
import config.Config;
import eval.environment.IO;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.DiscordChannelWriter;
import util.JDAUtil;
import util.Strings;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EvalCommand implements Command {
	@Override
	public boolean execute(List<String> args, MessageReceivedEvent event) {
		if (!event.getAuthor().getName().equals("Profilüfter"))
			return true;
		synchronized (this) {
			DiscordChannelWriter writer = null;
			try {
				String source = event.getMessage().getContentDisplay().replaceFirst(Config.get("prefix") + getName(), "");

				if (!source.startsWith("package eval.environment;"))
					source = "package eval.environment;" + source;

				String className;
				final Pattern pattern = Pattern.compile("class (.+?) \\{", Pattern.DOTALL);
				final Matcher matcher = pattern.matcher(source);
				if (matcher.find()) {
					className = matcher.group(1);
				} else {
					throw new IllegalArgumentException(Strings.getString("eval.detectClassName", event));
				}

				File root = Files.createTempDirectory("managementBot").toFile();
				File sourceFile = new File(root, "eval/environment/" + className + ".java");
				//noinspection ResultOfMethodCallIgnored
				sourceFile.getParentFile().mkdirs();
				Files.write(sourceFile.toPath(), source.getBytes(StandardCharsets.UTF_8));

				JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
				StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
				writer = new DiscordChannelWriter(event.getTextChannel()) {
					@Override
					public void sendAll() {
						buffer.forEach((channel, strings) -> JDAUtil.sendMessage(new MessageBuilder().appendCodeBlock(String.join("", strings).replaceAll(Pattern.quote(root.toString()),""),"").build(), channel));
					}
				};
				boolean success = compiler.getTask(writer, null, null, null, null, fileManager.getJavaFileObjectsFromFiles(Collections.singleton(sourceFile))).call();

				if(success) {
					RestrictingClassLoader classLoader = new RestrictingClassLoader(new URL[]{root.toURI().toURL()});
					Class<?> cls = Class.forName("eval.environment." + className, true, classLoader);

					IO.setChannel(event.getTextChannel());
					Method method = cls.getMethod("main", String[].class);
					method.setAccessible(true);
					method.invoke(null, new Object[]{new String[]{}});
				} else {
					throw new IllegalArgumentException(Strings.getString("eval.compileError",event));
				}
			} catch (InvocationTargetException e) {
				JDAUtil.sendMessage(Strings.getString("eval.classNotFound", event) + " " + e.getCause().getCause().getMessage(), event.getTextChannel());
			} catch (IllegalArgumentException e) {
				JDAUtil.sendMessage(e.getMessage(), event.getTextChannel());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
					writer.sendAll();
				}
			}
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
