package commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public interface Command {
	/**
	 * @param args The arguments provided when called
	 * @param event The event of the message
	 */
	void execute(List<String> args, MessageReceivedEvent event);

	/**
	 * @return The name of the Command as it would be called
	 */
	String getName();

	/**
	 * @return A short description of the command and its usage
	 */
	String getHelp();
}
