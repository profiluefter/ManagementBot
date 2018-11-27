package commands;

import localisation.Strings;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import sql.User;

import java.awt.*;
import java.util.List;

import static util.JDAUtil.sendEmbed;
import static util.JDAUtil.sendMessage;

public class MeCommand implements Command {
	@Override
	public void execute(List<String> args, MessageReceivedEvent event) {
		switch (args.size()) {
			case 0:
				printUserInfo(event);
				break;
			case 3:
				switch (args.get(0)) {
					case "set":
						setProperty(args, event);
				}
				break;
		}
	}

	private void setProperty(List<String> args, MessageReceivedEvent event) {
		switch (args.get(1)) {
			case "lang":
				Strings.Lang lang = Strings.parseLang(args.get(2));
				if (lang == null) {
					sendEmbed(Color.RED,
							Strings.getString("me.invalidArgument", Strings.Lang.EN),
							Strings.getString("me.invalidArgumentDescription", Strings.Lang.EN).replaceAll("\\[ARGUMENT]", Strings.getString("me.language", Strings.Lang.EN)),
							event.getTextChannel());
				} else {
					User.loadUser(event.getAuthor().getIdLong()).setLanguage(lang);
					sendEmbed(Color.GREEN,
							Strings.getString("success", Strings.Lang.EN),
							Strings.getString("me.changed", Strings.Lang.EN).replaceAll("\\[ARGUMENT]", Strings.getString("me.language", Strings.Lang.EN)).replaceAll("\\[VALUE]", Strings.parseLang(lang)),
							event.getTextChannel());
				}
		}
	}

	private void printUserInfo(MessageReceivedEvent event) {
		User user = User.loadUser(event.getAuthor().getIdLong());
		EmbedBuilder builder = new EmbedBuilder();
		builder.setColor(Color.GREEN)
				.setTitle(event.getAuthor().getName())
				.addField(Strings.getString("me.discordid", Strings.Lang.EN), String.valueOf(user.getDiscordid()), false)
				.addField(Strings.getString("me.language", Strings.Lang.EN), Strings.parseLang(user.getLanguage()), false);
		sendMessage(builder.build(),event.getTextChannel());
	}

	@Override
	public String getName() {
		return "me";
	}

	@Override
	public String getHelp() {
		return Strings.getString("me.help", Strings.Lang.EN);
	}
}
