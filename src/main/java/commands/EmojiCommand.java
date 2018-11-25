package commands;

import localisation.Strings;
import net.dv8tion.jda.core.entities.Icon;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static util.JDAUtil.sendEmbed;

//TODO: Integrate in Command framework
public class EmojiCommand {
	public boolean execute(String[] args, MessageReceivedEvent event) {
		if(event.getMessage().getAttachments().size() == 0 && args.length == 2) {
			try {
				URL url = new URL(args[1]);
				event.getGuild().getController().createEmote(args[0], Icon.from(url.openStream())).queue(emote ->
						event.getChannel().sendMessage(sendEmbed(Color.GREEN,
								Strings.getString("success", Strings.Lang.EN),
								Strings.getString("emoji.success", Strings.Lang.EN)
								)).queue(message -> message.delete().queueAfter(5,TimeUnit.SECONDS)));
				return true;
			} catch (MalformedURLException e) {
				reportException(event, "emoji.malformedUrlTitle", "emoji.malformedUrlDescription");
				return false;
			} catch (IOException e) {
				reportException(event, "emoji.downloadErrorTitle", "emoji.downloadErrorDescription");
				return false;
			}
		} else if(event.getMessage().getAttachments().size() == 1 && args.length == 1) {
			try {
				Icon icon = event.getMessage().getAttachments().get(0).getAsIcon();
				event.getGuild().getController().createEmote(args[0], icon).queue(emote ->
						event.getChannel().sendMessage(sendEmbed(Color.GREEN,
								Strings.getString("success", Strings.Lang.EN),
								Strings.getString("emoji.success", Strings.Lang.EN)
						)).queue(message -> message.delete().queueAfter(5,TimeUnit.SECONDS)));
				return true;
			} catch (IOException e) {
				reportException(event, "emoji.downloadErrorTitle", "emoji.downloadErrorDescription");
				return false;
			}
		} else {
			//TODO: Print usage
			return false;
		}
	}

	private void reportException(MessageReceivedEvent event, String s, String s2) {
		event.getChannel().sendMessage(sendEmbed(Color.RED,
				Strings.getString(s, Strings.Lang.EN),
				Strings.getString(s2, Strings.Lang.EN)))
				.queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
	}
}
