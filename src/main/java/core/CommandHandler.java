package core;

import config.Config;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.JDAUtil;
import util.Strings;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class CommandHandler {
	private static final Map<String, Command> commands = new HashMap<>();

	/**
	 * Parses the message and calls the corresponding command.
	 *
	 * @param event The event of the message.
	 */
	public static void handle(MessageReceivedEvent event) {
		String rawMessage = event.getMessage().getContentRaw();
		List<String> args = Arrays.asList(rawMessage.split(" "));
		String invoke = args.get(0).replaceFirst(Config.get("prefix"), "");

		Command command = commands.getOrDefault(invoke, commands.get("help"));
		event.getMessage().delete().queueAfter(1, TimeUnit.MINUTES);
		boolean printHelp = command.execute(new Context(event, args));
		if(printHelp)
			JDAUtil.sendEmbed(Color.RED, Strings.getString("syntaxError", event), Strings.getString(command.getDescription().help(), event), event.getTextChannel());
	}

	/**
	 * Registers a command. It reads the name from the object and inserts it into the {@link Map}.
	 *
	 * @param command The command to register.
	 */
	static void registerCommand(Command command) {
		String[] names = command.getDescription().name();
		for(String name : names) {
			commands.put(name, command);
		}
	}

	/**
	 * Returns all commands registered so far.
	 *
	 * @return All commands.
	 */
	public static Map<String, Command> getCommands() {
		return commands;
	}
}
