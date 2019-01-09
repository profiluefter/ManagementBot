package commands;

import core.Command;
import core.CommandDescription;
import core.Context;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Role;
import util.Strings;

import java.awt.*;
import java.util.stream.Collectors;

import static util.JDAUtil.sendEmbed;
import static util.JDAUtil.sendMessage;

@CommandDescription(
		name = "me",
		help = "me.help"
)
public class MeCommand extends Command {
	@Override
	public boolean execute(Context context) {
		switch(context.getArgs().size()) {
			case 0:
				printUserInfo(context);
				return false;
			case 3:
				//noinspection SwitchStatementWithTooFewBranches
				switch(context.getArgs().get(0)) {
					case "set":
						return setProperty(context);
					default:
						return true;
				}
			default:
				return true;
		}
	}

	private boolean setProperty(Context context) {
		//noinspection SwitchStatementWithTooFewBranches
		switch(context.getArgs().get(1)) {
			case "lang":
				Strings.Lang lang = Strings.parseLang(context.getArgs().get(2));
				if(lang == null) {
					sendEmbed(Color.RED,
							Strings.getString("me.invalidArgument", context.getUser()),
							Strings.getString("me.invalidArgumentDescription", context.getUser()).replaceAll("\\[ARGUMENT]", Strings.getString("me.language", context.getUser())),
							context.getTextChannel());
				} else {
					context.getUser().setLanguage(lang);
					sendEmbed(Color.GREEN,
							Strings.getString("success", context.getUser()),
							Strings.getString("me.changed", context.getUser()).replaceAll("\\[ARGUMENT]", Strings.getString("me.language", context.getUser())).replaceAll("\\[VALUE]", Strings.parseLang(lang)),
							context.getTextChannel());
				}
				return false;
			default:
				return true;
		}
	}

	private void printUserInfo(Context context) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setColor(Color.GREEN)
				.setTitle(context.getJDAUser().getName())
				.addField(Strings.getString("me.discordid", context.getUser()), String.valueOf(context.getUser().getDiscordID()), false)
				.addField(Strings.getString("me.language", context.getUser()), Strings.parseLang(context.getUser().getLanguage()), false)
				.addField(Strings.getString("me.roles", context.getUser()), context.getMember().getRoles().stream().map(Role::getName).collect(Collectors.joining(", ")), false);
		sendMessage(builder.build(), context.getTextChannel());
	}
}
