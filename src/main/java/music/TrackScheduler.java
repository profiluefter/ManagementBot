package music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.*;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import util.JDAUtil;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

class TrackScheduler implements AudioEventListener {
	private Queue<AudioTrack> queue = new LinkedList<>();
	private long guildID;
	private AudioPlayer player;

	TrackScheduler(long guildID, AudioPlayer player) {
		this.guildID = guildID;
		this.player = player;
	}

	@Override
	public void onEvent(AudioEvent event) {
		TextChannel textChannel = MusicManager.getLastUsedVoiceChannel(guildID);
		if(event instanceof PlayerPauseEvent) {
			JDAUtil.sendEmbed(null, "Pausing", "Player paused!", textChannel);
		} else if(event instanceof PlayerResumeEvent) {
			JDAUtil.sendEmbed(null, "Resuming", "Player resumed!", textChannel);
		} else if(event instanceof TrackEndEvent) {
			event.player.playTrack(queue.poll());
		} else if(event instanceof TrackExceptionEvent) {
			JDAUtil.sendEmbed(Color.RED, "Exception while playing track!", ((TrackExceptionEvent) event).exception.getMessage(), textChannel);
			event.player.playTrack(queue.poll());
		} else if(event instanceof TrackStartEvent) {
			AudioTrackInfo info = ((TrackStartEvent) event).track.getInfo();
			JDAUtil.sendMessage(new EmbedBuilder().setTitle("Playing next track")
					.addField("Title", info.title, true).addField("Creator", info.author, true)
					.addField("Duration", String.valueOf(TimeUnit.MILLISECONDS.toMinutes(info.length)), true).build(), textChannel);
		} else if(event instanceof TrackStuckEvent) {
			JDAUtil.sendEmbed(Color.ORANGE, "Track stuck", "Track \"" + ((TrackStuckEvent) event).track.getIdentifier() + "\" stopped loading! Playing next track...", textChannel);
		}
	}

	void queue(AudioTrack track) {
		if(!player.startTrack(track,true))
			queue.offer(track);
	}

	void skip() {
		player.stopTrack();
	}
}
