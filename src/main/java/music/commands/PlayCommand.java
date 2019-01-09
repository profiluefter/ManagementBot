package music.commands;

import core.Command;
import core.CommandDescription;
import core.Context;
import music.MusicManager;

@CommandDescription(
		name = {"play"}
)
public class PlayCommand extends Command {
	@Override
	public boolean execute(Context context) {
		MusicManager.interacted(context.getTextChannel(), context.getUserID());
		MusicManager.play(context.getArgsRaw(), context.getGuildID(), context.getUserID());
		return false;
	}
}
