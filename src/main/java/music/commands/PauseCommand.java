package music.commands;

import core.Command;
import core.CommandDescription;
import core.Context;
import music.MusicManager;

@CommandDescription(
		name = {"pause"}
)
public class PauseCommand extends Command {
	@Override
	public boolean execute(Context context) {
		MusicManager.interacted(event.getTextChannel(), event.getAuthor().getIdLong());
		MusicManager.pause(event.getGuild().getIdLong());
		return false;
	}
}
