package commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.Strings;

import java.util.List;

import static util.Strings.Lang.EN;

public class PingCommand implements Command {
	@Override
	public boolean execute(List<String> args, MessageReceivedEvent event) {
		event.getTextChannel().sendMessage(Strings.getString("ping.msg", Strings.Lang.EN).replaceAll("\\[VALUE]",Long.toString(event.getJDA().getPing()))).queue();
		return false;
	}

	@Override
	public String getName() {
		return "ping";
	}

	public String getHelp() {
		return Strings.getString("ping.help",EN);
	}
}
