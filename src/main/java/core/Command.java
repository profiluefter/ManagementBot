package core;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public abstract class Command {
	private CommandDescription description;

	/**
	 * @param args  The arguments provided when called
	 * @param event The event of the message
	 * @return If the help should be printed. False if not.
	 */
	public abstract boolean execute(List<String> args, MessageReceivedEvent event);

	public CommandDescription getDescription() {
		return description;
	}

	public Command() {
		description = getClass().getAnnotation(CommandDescription.class);
	}
}
