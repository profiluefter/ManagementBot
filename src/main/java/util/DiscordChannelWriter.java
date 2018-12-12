package util;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import org.jetbrains.annotations.NotNull;

import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscordChannelWriter extends Writer {
	protected static Map<TextChannel, List<String>> buffer = new HashMap<>();
	private TextChannel textChannel;

	public DiscordChannelWriter(TextChannel channel) {
		textChannel = channel;
		buffer.putIfAbsent(channel, new ArrayList<>());
	}

	public void sendAll() {
		buffer.forEach((channel, strings) -> JDAUtil.sendMessage(new MessageBuilder().appendCodeBlock(String.join("", strings), "").build(), channel));
	}

	@Override
	public void write(@NotNull char[] inputBuffer, int off, int len) {
		buffer.get(textChannel).add(new String(inputBuffer, off, len));
	}

	@Override
	public void flush() {

	}

	@Override
	public void close() {
		sendAll();
	}
}
