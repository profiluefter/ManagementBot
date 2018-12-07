package commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class PermissionCommand implements Command {
	@Override
	public boolean execute(List<String> args, MessageReceivedEvent event) {
		//TODO
		return false;
	}

	@Override
	public String getName() {
		return "permission";
	}

	@Override
	public String getHelp(MessageReceivedEvent event) {
		return null;
	}
}
