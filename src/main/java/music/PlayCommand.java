package music;

import commands.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class PlayCommand implements Command {
	@Override
	public boolean execute(List<String> args, MessageReceivedEvent event) {
		MusicManager.interacted(event.getTextChannel(), event.getAuthor().getIdLong());
		MusicManager.play(String.join(" ", args), event.getGuild().getIdLong(), event.getAuthor().getIdLong());
		return false;
	}

	@Override
	public String getName() {
		return "play";
	}

	@Override
	public String getHelp(MessageReceivedEvent event) {
		return null;
	}
}
