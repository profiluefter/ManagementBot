package music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.HashMap;
import java.util.Map;

public class MusicManager {
	private static Map<Long, TrackScheduler> players = new HashMap<>();
	private static Map<Long, TextChannel> textChannels = new HashMap<>();
	private static Map<Long, Long> users = new HashMap<>();
	private static AudioPlayerManager playerManager;

	public static void init() {
		playerManager = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerRemoteSources(playerManager);
	}

	static void interacted(TextChannel channel, long userID) {
		textChannels.put(channel.getGuild().getIdLong(), channel);
		users.put(channel.getGuild().getIdLong(), userID);
	}

	static TextChannel getLastUsedVoiceChannel(long guildID) {
		return textChannels.get(guildID);
	}

	static long getLastUserInteracted(long guildID) {
		return users.get(guildID);
	}

	static void joinChannel(VoiceChannel channel) {
		AudioPlayer player = playerManager.createPlayer();
		TrackScheduler trackScheduler = new TrackScheduler(channel.getGuild().getIdLong(), player);
		player.addListener(trackScheduler);
		players.put(channel.getGuild().getIdLong(), trackScheduler);

		AudioManager audioManager = channel.getGuild().getAudioManager();
		audioManager.openAudioConnection(channel);
		audioManager.setSendingHandler(new AudioPlayerSendHandler(player));
	}

	static void play(String searchTerm, long guildID, long userID) {
		TrackScheduler scheduler = players.get(guildID);
		TextChannel textChannel = textChannels.get(guildID);

		playerManager.loadItem(searchTerm, new AudioLoadResultHandler() {
			@Override
			public void trackLoaded(AudioTrack track) {
				scheduler.queue(track);
				InfoPrinter.trackLoaded(track, textChannel, userID);
			}

			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				for(AudioTrack track : playlist.getTracks()) {
					scheduler.queue(track);
				}
				InfoPrinter.playlistLoaded(playlist, textChannel, userID);
			}

			@Override
			public void noMatches() {
				InfoPrinter.noMatches(textChannel, userID);
			}

			@Override
			public void loadFailed(FriendlyException exception) {
				InfoPrinter.loadFailed(exception, textChannel, userID);
			}
		});
	}

	static void skip(long guildID) {
		players.get(guildID).skip();
	}

	static void pause(long guildID) {
		players.get(guildID).pause();
	}

	static void resume(long guildID) {
		players.get(guildID).resume();
	}
}

