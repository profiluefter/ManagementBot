package music;

import commands.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class JoinCommand implements Command {
	@Override
	public boolean execute(List<String> args, MessageReceivedEvent event) {
		MusicManager.usedChannel(event.getTextChannel());
		MusicManager.joinChannel(event.getMember().getVoiceState().getChannel());
		return false;
	}

	@Override
	public String getName() {
		return "join";
	}

	@Override
	public String getHelp(MessageReceivedEvent event) {
		return null;
	}
}
