package music.commands;

import core.Command;
import core.CommandDescription;
import core.Context;
import music.MusicManager;

@CommandDescription(
		name = {"skip", "next"}
)
public class SkipCommand extends Command {
	@Override
	public boolean execute(Context context) {
		MusicManager.interacted(event.getTextChannel(), event.getAuthor().getIdLong());
		MusicManager.skip(event.getGuild().getIdLong());
		return false;
	}
}
