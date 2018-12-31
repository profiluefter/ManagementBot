package util;

import config.User;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * A helper class for JDA related stuff
 */
public class JDAUtil {
	/**
	 * Generates an embed from the parameters.
	 *
	 * @param color       The color the embed will be displayed in.
	 * @param title       The title to display over the embed.
	 * @param description The description that will be displayed over the embed.
	 * @return The built embed.
	 * @see EmbedBuilder
	 */
	@SuppressWarnings("WeakerAccess") //Util method that could be useful
	public static MessageEmbed generateEmbed(Color color, String title, String description) {
		return new EmbedBuilder().setColor(color).setTitle(title).setDescription(description).build();
	}

	/**
	 * Sends an embed with the parameters loaded from the strings file. This also deletes the message after some time.
	 *
	 * @param color          The color the embed will be displayed in.
	 * @param titleKey       The key under which the title is saved in the strings file.
	 * @param descriptionKey The key under which the description is saved in the strings file.
	 * @param channel        The channel to send the embed in.
	 * @param user           The user object to load the language.
	 * @see Strings
	 * @see User
	 * @see EmbedBuilder
	 */
	public static void sendEmbedWithLocalisation(Color color, String titleKey, String descriptionKey, TextChannel channel, User user) {
		sendEmbed(color, Strings.getString(titleKey, user), Strings.getString(descriptionKey, user), channel);
	}

	/**
	 * Sends an embed to the specified channel. This also deletes the message after some time.
	 *
	 * @param color       The color the embed will be displayed in.
	 * @param title       The title to display over the embed.
	 * @param description The description that will be displayed over the embed.
	 * @param channel     The channel to send the embed in.
	 * @see EmbedBuilder
	 */
	public static void sendEmbed(Color color, String title, String description, TextChannel channel) {
		sendMessage(generateEmbed(color, title, description), channel);
	}

	/*
	 * Note for the future:
	 * List of hardcoded timestamps:
	 * music.InfoPrinter#playlistLoaded()
	 * */

	/**
	 * Sends a message to the specified channel. This also deletes the message after some time.
	 *
	 * @param message The message to send.
	 * @param channel The channel to send the message in.
	 */
	public static void sendMessage(Message message, @NotNull TextChannel channel) {
		channel.sendMessage(message).queue(message1 -> message1.delete().queueAfter(1, TimeUnit.MINUTES));
	}

	/**
	 * Sends a message to the specified channel. This also deletes the message after some time.
	 *
	 * @param message The message to send.
	 * @param channel The channel to send the message in.
	 */
	public static void sendMessage(MessageEmbed message, @NotNull TextChannel channel) {
		channel.sendMessage(message).queue(message1 -> message1.delete().queueAfter(1, TimeUnit.MINUTES));
	}

	/**
	 * Sends a message to the specified channel. This also deletes the message after some time.
	 *
	 * @param message The message to send.
	 * @param channel The channel to send the message in.
	 */
	public static void sendMessage(CharSequence message, @NotNull TextChannel channel) {
		channel.sendMessage(message).queue(message1 -> message1.delete().queueAfter(1, TimeUnit.MINUTES));
	}
}
