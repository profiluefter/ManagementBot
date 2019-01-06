package eval.environment;

import net.dv8tion.jda.core.entities.TextChannel;
import util.DiscordChannelWriter;

import java.io.PrintWriter;

/**
 * Used to interact with the user of the code-snippet.
 */
//TODO: reader methods
@SuppressWarnings("unused") //Used for eval
public class IO {
	private static TextChannel textChannel;
	private final PrintWriter writer;

	/**
	 * Constructs an new instance of the object and copies the active discord channel.
	 * This is done, because else there could be a race condition, where the messages are written in the wrong channel.
	 * Technically this could still happen, but only if the instance isn't created before any blocking calls,
	 * but that shouldn't happen, because there are no allowed blocking methods.
	 */
	public IO() {
		writer = new PrintWriter(new DiscordChannelWriter(textChannel));
	}

	/**
	 * Sets the channel that should be interacted with.
	 *
	 * @param channel The channel.
	 */
	public static void setChannel(TextChannel channel) {
		textChannel = channel;
	}

	/**
	 * Writes to the channel and appends a newline.
	 *
	 * @param object The content to write.
	 */
	public void println(Object object) {
		writer.println(object);
	}

	/**
	 * Writes to the channel.
	 *
	 * @param object The content to write.
	 */
	public void print(Object object) {
		writer.print(object);
	}
}