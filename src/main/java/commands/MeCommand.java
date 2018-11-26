package commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class MeCommand implements Command {
	@Override
	public void execute(List<String> args, MessageReceivedEvent event) {

	}

	@Override
	public String getName() {
		return "me";
	}

	@Override
	public String help() {
		return null;
	}
}
