package core;

import commands.Command;
import config.Config;
import config.Database;
import eval.EvalCommand;
import music.JoinCommand;
import music.MusicManager;
import music.PlayCommand;
import music.SkipCommand;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.reflections.Reflections;
import util.Strings;

import javax.security.auth.login.LoginException;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class Main {

	public static void main(String[] args) {
		String token;
		if((token = System.getenv("discord.token")) == null && (token = System.getenv("DISCORD_TOKEN")) == null) {
			throw new IllegalArgumentException("Please provide the Discord token in the system property discord.token or DISCORD_TOKEN!");
		}

		JDABuilder jdaBuilder = new JDABuilder(AccountType.BOT);
		jdaBuilder.setToken(token);

		Config.init();
		Database.init();
		Strings.init();

		MusicManager.init();

		addCommands();
		addListeners(jdaBuilder);

		try {
			jdaBuilder.build();
		} catch(LoginException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Searches the package commands for Commands
	 * and adds them to the CommandHandler
	 */
	private static void addCommands() {
		CommandHandler.registerCommand(new EvalCommand());

		CommandHandler.registerCommand(new JoinCommand());
		CommandHandler.registerCommand(new PlayCommand());
		CommandHandler.registerCommand(new SkipCommand());

		Reflections reflections = new Reflections("commands");
		Set<Class<? extends Command>> commands = reflections.getSubTypesOf(Command.class);
		for(Class<? extends Command> command : commands) {
			try {
				Command instance = command.getConstructor().newInstance();
				CommandHandler.registerCommand(instance);
			} catch(NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Searches the package listeners for Listeners
	 * and registers them to the provided JDABuilder
	 *
	 * @param jdaBuilder the instance to register the listeners to
	 */
	private static void addListeners(JDABuilder jdaBuilder) {
		Reflections reflections = new Reflections("listeners");
		Set<Class<? extends ListenerAdapter>> listeners = reflections.getSubTypesOf(ListenerAdapter.class);
		for(Class<? extends ListenerAdapter> listener : listeners) {
			try {
				ListenerAdapter instance = listener.getConstructor().newInstance();
				jdaBuilder.addEventListener(instance);
			} catch(NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
