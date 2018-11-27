package core;

import commands.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.STATIC;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class CommandHandler {
	private static Map<String, Command> commands = new HashMap<>();

	public static void handle(MessageReceivedEvent event) {
		String rawMessage = event.getMessage().getContentRaw();
		List<String> split = Arrays.asList(rawMessage.split(" "));
		String invoke = split.get(0).replaceFirst(STATIC.PREFIX,"");

		List<String> args;
		if(split.size() > 1)
			args = split.subList(1,split.size());
		else
			args = new ArrayList<>();

		Command command = commands.getOrDefault(invoke,commands.get("help"));
		event.getMessage().delete().queueAfter(1, TimeUnit.MINUTES);
		command.execute(args,event);
	}

	static void registerCommand(Command command) {
		commands.put(command.getName(),command);
	}

	public static Map<String, Command> getCommands() {
		return commands;
	}
}
