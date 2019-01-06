package music;

import com.sedmelluq.discord.lavaplayer.player.event.*;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import org.jetbrains.annotations.NotNull;
import util.JDAUtil;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static util.Strings.getString;

public class InfoPrinter {
	@NotNull
	private static String calculateDuration(long millis, long userID) {
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(millis - TimeUnit.MINUTES.toMillis(minutes));
		return minutes + " " + getString("minutes", userID) + " " + seconds + " " + getString("seconds", userID);
	}

	//------------------------------------------Track-Loader------------------------------------------------------------
	public static void trackLoaded(AudioTrack track, TextChannel textChannel, long userID, boolean event) {
		String time = calculateDuration(track.getDuration(), userID);

		JDAUtil.sendMessage(new EmbedBuilder()
				.setColor(Color.GREEN)
				.setTitle(getString(event ? "music.trackLoaded" : "music.playing", userID))
				.addField(getString("music.title", userID), track.getInfo().title, true)
				.addField(getString("music.artist", userID), track.getInfo().author, true)
				.addField(getString("music.duration", userID), time, true)
				.build(), textChannel);
	}

	static void playlistLoaded(AudioPlaylist playlist, TextChannel textChannel, long userID) {
		EmbedBuilder builder = new EmbedBuilder()
				.setColor(Color.GREEN)
				.setTitle(getString("music.playlistLoaded", userID))
				.setDescription(getString("music.title", userID) + ": " + playlist.getName());

		playlist.getTracks().forEach(
				audioTrack -> builder.addField(audioTrack.getInfo().title, calculateDuration(audioTrack.getDuration(), userID), false)
		);

		try {
			textChannel.sendMessage(builder.build()).complete().delete().queueAfter(1, TimeUnit.MINUTES);
		} catch(IllegalArgumentException e) { //Embed to long
			textChannel.sendMessage(new EmbedBuilder()
					.setColor(Color.GREEN)
					.setTitle(getString("music.playlistLoaded", userID))
					.setDescription(getString("music.title", userID) + ": " + playlist.getName())
					.build()).complete().delete().queueAfter(1, TimeUnit.MINUTES);
		}
	}

	static void noMatches(TextChannel textChannel, long userID) {
		JDAUtil.sendMessage(new EmbedBuilder()
				.setColor(Color.RED)
				.setTitle(getString("music.noMatchesTitle", userID))
				.setDescription(getString("music.noMatchesDescription", userID))
				.build(), textChannel);
	}

	static void loadFailed(FriendlyException exception, TextChannel textChannel, long userID) {
		JDAUtil.sendMessage(new EmbedBuilder()
				.setColor(Color.RED)
				.setTitle(getString("music.loadingFailed", userID))
				.setDescription(exception.getMessage())
				.build(), textChannel);
	}

	//----------------------------------------Player-Events-------------------------------------------------------------
	static void playerPauseEvent(TextChannel textChannel, long userID) {
		JDAUtil.sendMessage(new EmbedBuilder()
				.setColor(Color.BLUE)
				.setTitle(getString("music.pausedTitle", userID))
				.setDescription(getString("music.pausedDescription", userID))
				.build(), textChannel);
	}

	static void playerResumeEvent(TextChannel textChannel, long userID) {
		JDAUtil.sendMessage(new EmbedBuilder()
				.setColor(Color.BLUE)
				.setTitle(getString("music.resumedTitle", userID))
				.setDescription(getString("music.resumedDescription", userID))
				.build(), textChannel);
	}

	@SuppressWarnings("EmptyMethod")
	static void trackEndEvent() {
		//Ignored
	}

	static void trackExceptionEvent(TrackExceptionEvent event, TextChannel textChannel, long userID) {
		JDAUtil.sendMessage(new EmbedBuilder()
				.setColor(Color.RED)
				.setTitle(getString("music.loadingFailed", userID))
				.setDescription(event.exception.getMessage())
				.build(), textChannel);
	}

	@SuppressWarnings("EmptyMethod")
	static void trackStartEvent() {
		//Ignored
	}

	static void trackStuckEvent(TextChannel textChannel, long userID) {
		JDAUtil.sendMessage(new EmbedBuilder()
				.setColor(Color.RED)
				.setTitle(getString("music.trackStuckTitle", userID))
				.setDescription(getString("music.trackStuckDescription", userID))
				.build(), textChannel);
	}
}
