package util;

import config.User;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;

import java.awt.*;
import java.util.concurrent.TimeUnit;

//TODO: documentation
/**
 * A helper class for JDA related stuff
 */
public class JDAUtil {
	public static MessageEmbed generateEmbed(Color color, String title, String description) {
		return new EmbedBuilder().setColor(color).setTitle(title).setDescription(description).build();
	}

	public static void sendEmbedWithLocalisation(Color color, String titleKey, String descriptionKey, TextChannel channel, User user) {
		sendEmbed(color,Strings.getString(titleKey, user),Strings.getString(descriptionKey, user),channel);
	}

	public static void sendEmbed(Color color, String title, String description, TextChannel channel) {
		sendMessage(generateEmbed(color, title, description),channel);
	}

	public static void sendMessage(Message message, TextChannel channel) {
		channel.sendMessage(message).queue(message1 -> message1.delete().queueAfter(1,TimeUnit.MINUTES));
	}

	public static void sendMessage(MessageEmbed message, TextChannel channel) {
		channel.sendMessage(message).queue(message1 -> message1.delete().queueAfter(1,TimeUnit.MINUTES));
	}

	public static void sendMessage(CharSequence message, TextChannel channel) {
		channel.sendMessage(message).queue(message1 -> message1.delete().queueAfter(1,TimeUnit.MINUTES));
	}
}
