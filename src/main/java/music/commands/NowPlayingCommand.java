package music.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import core.Command;
import core.CommandDescription;
import core.Context;
import music.MusicManager;

@CommandDescription(
		name = {"nowplaying", "np", "song"}
)
public class NowPlayingCommand extends Command {
	@Override
	public boolean execute(Context context) {
		AudioTrack playingTrack = MusicManager.getPlayingTrack(event.getGuild().getIdLong());
		music.InfoPrinter.trackLoaded(playingTrack, event.getTextChannel(), event.getAuthor().getIdLong(), false);
		return false;
	}
}
