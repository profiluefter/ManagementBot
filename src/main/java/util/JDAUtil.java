package util;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.Color;

public class JDAUtil {
	public static MessageEmbed sendEmbed(Color color, String title, String description) {
		return new EmbedBuilder().setColor(color).setTitle(title).setDescription(description).build();
	}
}
