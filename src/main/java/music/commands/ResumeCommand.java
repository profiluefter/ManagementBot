package music.commands;

import core.Command;
import core.CommandDescription;
import core.Context;
import music.MusicManager;

@CommandDescription(
		name = {"resume", "unpause"}
)
public class ResumeCommand extends Command {
	@Override
	public boolean execute(Context context) {
		MusicManager.interacted(event.getTextChannel(), event.getAuthor().getIdLong());
		MusicManager.resume(event.getGuild().getIdLong());
		return false;
	}
}
