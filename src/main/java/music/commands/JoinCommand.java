package music.commands;

import commands.Command;
import music.MusicManager;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class JoinCommand implements Command {
	@Override
	public boolean execute(List<String> args, MessageReceivedEvent event) {
		MusicManager.interacted(event.getTextChannel(), event.getAuthor().getIdLong());
		MusicManager.joinChannel(event.getMember().getVoiceState().getChannel());
		return false;
	}

	@Override
	public String[] getName() {
		return new String[]{"join"};
	}

	@Override
	public String getHelp(MessageReceivedEvent event) {
		return null;
	}
}
