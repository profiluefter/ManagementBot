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
import util.JDAUtil;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MusicManager {
	private static Map<Long, TrackScheduler> players = new HashMap<>();
	private static Map<Long, TextChannel> textChannels = new HashMap<>();
	private static AudioPlayerManager playerManager;

	public static void init() {
		playerManager = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerRemoteSources(playerManager);
	}

	static void usedChannel(TextChannel channel) {
		textChannels.put(channel.getGuild().getIdLong(),channel);
	}

	static TextChannel getLastUsedVoiceChannel(long guildID) {
		return textChannels.get(guildID);
	}

	static void joinChannel(VoiceChannel channel) {
		AudioPlayer player = playerManager.createPlayer();
		TrackScheduler trackScheduler = new TrackScheduler(channel.getGuild().getIdLong(),player);
		player.addListener(trackScheduler);
		players.put(channel.getGuild().getIdLong(), trackScheduler);

		AudioManager audioManager = channel.getGuild().getAudioManager();
		audioManager.openAudioConnection(channel);
		audioManager.setSendingHandler(new AudioPlayerSendHandler(player));
	}

	static void play(long guildID, String searchTerm) {
		TrackScheduler scheduler = players.get(guildID);
		TextChannel textChannel = textChannels.get(guildID);

		playerManager.loadItem(searchTerm, new AudioLoadResultHandler() {
			@Override
			public void trackLoaded(AudioTrack track) {
				scheduler.queue(track);
				JDAUtil.sendEmbed(Color.GREEN, "Success!", "Playing " + track.getIdentifier(), textChannel);
			}

			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				JDAUtil.sendEmbed(Color.ORANGE, "Playlist loaded", "Playlist loaded: " + playlist.getName(), textChannel);
				for(AudioTrack track : playlist.getTracks()) {
					scheduler.queue(track);
				}
			}

			@Override
			public void noMatches() {
				JDAUtil.sendEmbed(Color.RED, "No matches!", "Nothing found!", textChannel);
			}

			@Override
			public void loadFailed(FriendlyException exception) {
				JDAUtil.sendEmbed(Color.RED, "Failed loading!", exception.getMessage(), textChannel);
			}
		});
	}

	static void skip(long idLong) {
		players.get(idLong).skip();
	}
}

