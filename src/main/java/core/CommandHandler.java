package core;

import commands.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.STATIC;

import java.util.*;

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

		Command command = commands.get(invoke);
		if(command == null) {
			//TODO: Call help command
		} else {
			event.getMessage().delete().queue();
			command.execute(args,event);
		}
	}

	static void registerCommand(Command command) {
		commands.put(command.getName(),command);
	}
}
