package commands;

import core.Command;
import core.CommandDescription;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.JDAUtil;
import util.Strings;

import java.util.List;

@CommandDescription(
		name = "ping",
		help = "ping.help"
)
public class PingCommand extends Command {
	@Override
	public boolean execute(List<String> args, MessageReceivedEvent event) {
		JDAUtil.sendMessage(Strings.getString("ping.msg", event).replaceAll("\\[VALUE]", Long.toString(event.getJDA().getPing())), event.getTextChannel());
		return false;
	}
}
