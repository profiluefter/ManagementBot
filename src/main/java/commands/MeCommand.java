package commands;

import config.User;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.Strings;

import java.awt.*;
import java.util.List;

import static util.JDAUtil.sendEmbed;
import static util.JDAUtil.sendMessage;

public class MeCommand implements Command {
	@Override
	public boolean execute(List<String> args, MessageReceivedEvent event) {
		switch (args.size()) {
			case 0:
				printUserInfo(event);
				return false;
			case 3:
				//noinspection SwitchStatementWithTooFewBranches
				switch (args.get(0)) {
					case "set":
						return setProperty(args, event);
					default:
						return true;
				}
			default:
				return true;
		}
	}

	private boolean setProperty(List<String> args, MessageReceivedEvent event) {
		//noinspection SwitchStatementWithTooFewBranches
		switch (args.get(1)) {
			case "lang":
				Strings.Lang lang = Strings.parseLang(args.get(2));
				if (lang == null) {
					sendEmbed(Color.RED,
							Strings.getString("me.invalidArgument", event.getAuthor().getIdLong()),
							Strings.getString("me.invalidArgumentDescription", event.getAuthor().getIdLong()).replaceAll("\\[ARGUMENT]", Strings.getString("me.language", event.getAuthor().getIdLong())),
							event.getTextChannel());
				} else {
					User.loadUser(event.getAuthor().getIdLong()).setLanguage(lang);
					sendEmbed(Color.GREEN,
							Strings.getString("success", event.getAuthor().getIdLong()),
							Strings.getString("me.changed", event.getAuthor().getIdLong()).replaceAll("\\[ARGUMENT]", Strings.getString("me.language", event.getAuthor().getIdLong())).replaceAll("\\[VALUE]", Strings.parseLang(lang)),
							event.getTextChannel());
				}
				return false;
				default:
					return true;
		}
	}

	private void printUserInfo(MessageReceivedEvent event) {
		User user = User.loadUser(event.getAuthor().getIdLong());
		EmbedBuilder builder = new EmbedBuilder();
		builder.setColor(Color.GREEN)
				.setTitle(event.getAuthor().getName())
				.addField(Strings.getString("me.discordid", event.getAuthor().getIdLong()), String.valueOf(user.getDiscordId()), false)
				.addField(Strings.getString("me.language", event.getAuthor().getIdLong()), Strings.parseLang(user.getLanguage()), false);
		sendMessage(builder.build(), event.getTextChannel());
	}

	@Override
	public String getName() {
		return "me";
	}

	@Override
	public String getHelp(MessageReceivedEvent event) {
		return Strings.getString("me.help", event.getAuthor().getIdLong());
	}
}
