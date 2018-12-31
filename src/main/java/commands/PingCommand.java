package commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.JDAUtil;
import util.Strings;

import java.util.List;

public class PingCommand implements Command {
	@Override
	public boolean execute(List<String> args, MessageReceivedEvent event) {
		JDAUtil.sendMessage(Strings.getString("ping.msg", event).replaceAll("\\[VALUE]", Long.toString(event.getJDA().getPing())), event.getTextChannel());
		return false;
	}

	@Override
	public String[] getName() {
		return new String[]{"ping"};
	}

	public String getHelp(MessageReceivedEvent event) {
		return Strings.getString("ping.help", event);
	}
}
