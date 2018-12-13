package commands;

import config.User;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.Strings;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

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
							Strings.getString("me.invalidArgument", event),
							Strings.getString("me.invalidArgumentDescription", event).replaceAll("\\[ARGUMENT]", Strings.getString("me.language", event)),
							event.getTextChannel());
				} else {
					User.loadUser(event).setLanguage(lang);
					sendEmbed(Color.GREEN,
							Strings.getString("success", event),
							Strings.getString("me.changed", event).replaceAll("\\[ARGUMENT]", Strings.getString("me.language", event)).replaceAll("\\[VALUE]", Strings.parseLang(lang)),
							event.getTextChannel());
				}
				return false;
			default:
				return true;
		}
	}

	private void printUserInfo(MessageReceivedEvent event) {
		User user = User.loadUser(event);
		EmbedBuilder builder = new EmbedBuilder();
		builder.setColor(Color.GREEN)
				.setTitle(event.getAuthor().getName())
				.addField(Strings.getString("me.discordid", event), String.valueOf(user.getDiscordID()), false)
				.addField(Strings.getString("me.language", event), Strings.parseLang(user.getLanguage()), false)
				.addField(Strings.getString("me.roles", event), event.getMember().getRoles().stream().map(Role::getName).collect(Collectors.joining(", ")), false);
		sendMessage(builder.build(), event.getTextChannel());
	}

	@Override
	public String getName() {
		return "me";
	}

	@Override
	public String getHelp(MessageReceivedEvent event) {
		return Strings.getString("me.help", event);
	}
}
