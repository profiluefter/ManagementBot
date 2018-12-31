package music.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import commands.Command;
import music.MusicManager;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class NowPlayingCommand implements Command {
	@Override
	public boolean execute(List<String> args, MessageReceivedEvent event) {
		AudioTrack playingTrack = MusicManager.getPlayingTrack(event.getGuild().getIdLong());
		music.InfoPrinter.trackLoaded(playingTrack, event.getTextChannel(), event.getAuthor().getIdLong(), false);
		return false;
	}

	@Override
	public String[] getName() {
		return new String[]{"nowplaying", "np", "song"};
	}

	@Override
	public String getHelp(MessageReceivedEvent event) {
		return null;
	}
}
