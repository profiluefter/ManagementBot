package music.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import core.Command;
import core.CommandDescription;
import music.MusicManager;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

@CommandDescription(
		name = {"nowplaying", "np", "song"},
		help = "help.notAvailable"
)
public class NowPlayingCommand extends Command {
	@Override
	public boolean execute(List<String> args, MessageReceivedEvent event) {
		AudioTrack playingTrack = MusicManager.getPlayingTrack(event.getGuild().getIdLong());
		music.InfoPrinter.trackLoaded(playingTrack, event.getTextChannel(), event.getAuthor().getIdLong(), false);
		return false;
	}
}
