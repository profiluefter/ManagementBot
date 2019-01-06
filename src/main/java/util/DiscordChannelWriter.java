package util;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import org.jetbrains.annotations.NotNull;

import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An implementation of a {@link Writer} that writes into a specified {@link TextChannel}
 */
public class DiscordChannelWriter extends Writer implements AutoCloseable {
	protected static final Map<TextChannel, List<String>> buffer = new HashMap<>();
	private final TextChannel textChannel;

	/**
	 * Constructs an {@link DiscordChannelWriter} object.
	 *
	 * @param channel The {@link TextChannel} to send the messages in.
	 */
	public DiscordChannelWriter(TextChannel channel) {
		textChannel = channel;
		buffer.putIfAbsent(channel, new ArrayList<>());
	}

	/**
	 * Sends all buffered messages and deletes them from the buffer. Messages are buffered to reduce spam.
	 */
	public void sendAll() {
		buffer.forEach((channel, strings) -> JDAUtil.sendMessage(new MessageBuilder().appendCodeBlock(String.join("", strings), "").build(), channel));
		buffer.clear();
	}

	/**
	 * Writes a message to the buffer. Mainly used by {@link java.io.BufferedWriter} or similar.
	 *
	 * @param inputBuffer The chars to write.
	 * @param off         The initial offset to write the string.
	 * @param len         The length of the string.
	 * @see Writer#write(char[], int, int)
	 */
	@Override
	public void write(@NotNull char[] inputBuffer, int off, int len) {
		buffer.get(textChannel).add(new String(inputBuffer, off, len));
	}

	/**
	 * This method is empty because we don't want to send a message immediately, but rather concatenated as one message when {@link DiscordChannelWriter#sendAll()} is called.
	 */
	@Override
	public void flush() {
	}

	/**
	 * Calls {@link DiscordChannelWriter#sendAll()} when the Writer is closed. This sends all messages from the buffer.
	 */
	@Override
	public void close() {
		sendAll();
	}
}
