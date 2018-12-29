package music;

import commands.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class PauseCommand implements Command {
	@Override
	public boolean execute(List<String> args, MessageReceivedEvent event) {
		MusicManager.interacted(event.getTextChannel(), event.getAuthor().getIdLong());
		MusicManager.pause(event.getGuild().getIdLong());
		return false;
	}

	@Override
	public String getName() {
		return "pause";
	}

	@Override
	public String getHelp(MessageReceivedEvent event) {
		return null;
	}
}
