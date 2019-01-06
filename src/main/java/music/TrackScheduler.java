package music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.*;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.LinkedList;
import java.util.Queue;

class TrackScheduler implements AudioEventListener {
	private final Queue<AudioTrack> queue = new LinkedList<>();
	private final long guildID;
	private final AudioPlayer player;

	TrackScheduler(long guildID, AudioPlayer player) {
		this.guildID = guildID;
		this.player = player;
	}

	@Override
	public void onEvent(AudioEvent event) {
		TextChannel textChannel = MusicManager.getLastUsedVoiceChannel(guildID);
		long userID = MusicManager.getLastUserInteracted(guildID);
		if(event instanceof PlayerPauseEvent) {
			InfoPrinter.playerPauseEvent(textChannel, userID);
		} else if(event instanceof PlayerResumeEvent) {
			InfoPrinter.playerResumeEvent(textChannel, userID);
		} else if(event instanceof TrackEndEvent) {
			event.player.playTrack(queue.poll());
			InfoPrinter.trackEndEvent();
		} else if(event instanceof TrackExceptionEvent) {
			event.player.playTrack(queue.poll());
			InfoPrinter.trackExceptionEvent(((TrackExceptionEvent) event), textChannel, userID);
		} else if(event instanceof TrackStartEvent) {
			InfoPrinter.trackStartEvent();
		} else if(event instanceof TrackStuckEvent) {
			InfoPrinter.trackStuckEvent(textChannel, userID);
		}
	}

	void queue(AudioTrack track) {
		if(!player.startTrack(track, true))
			queue.offer(track);
	}

	void skip() {
		player.stopTrack();
	}

	void pause() {
		player.setPaused(true);
	}

	void resume() {
		player.setPaused(false);
	}

	AudioTrack getPlayingTrack() {
		return player.getPlayingTrack();
	}
}
