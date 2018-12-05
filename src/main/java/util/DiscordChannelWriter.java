package util;

import net.dv8tion.jda.core.entities.TextChannel;
import org.jetbrains.annotations.NotNull;

import java.io.Writer;

public class DiscordChannelWriter extends Writer {
	private TextChannel textChannel;

	public DiscordChannelWriter(TextChannel channel) {
		textChannel = channel;
	}

	@Override
	public void write(@NotNull char[] buffer, int off, int len) {
		textChannel.sendMessage(new String(buffer,off,len)).queue();
	}

	@Override
	public void flush() {

	}

	@Override
	public void close() {

	}
}
