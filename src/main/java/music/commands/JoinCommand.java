package music.commands;

import core.Command;
import core.CommandDescription;
import music.MusicManager;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

@CommandDescription(
		name = "join"
)
public class JoinCommand extends Command {
	@Override
	public boolean execute(List<String> args, MessageReceivedEvent event) {
		MusicManager.interacted(event.getTextChannel(), event.getAuthor().getIdLong());
		MusicManager.joinChannel(event.getMember().getVoiceState().getChannel());
		return false;
	}
}
