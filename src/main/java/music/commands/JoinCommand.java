package music.commands;

import core.Command;
import core.CommandDescription;
import core.Context;
import music.MusicManager;

@CommandDescription(
		name = "join"
)
public class JoinCommand extends Command {
	@Override
	public boolean execute(Context context) {
		MusicManager.interacted(event.getTextChannel(), event.getAuthor().getIdLong());
		MusicManager.joinChannel(event.getMember().getVoiceState().getChannel());
		return false;
	}
}
