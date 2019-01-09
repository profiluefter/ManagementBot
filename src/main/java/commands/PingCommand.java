package commands;

import core.Command;
import core.CommandDescription;
import core.Context;
import util.JDAUtil;
import util.Strings;

@CommandDescription(
		name = "ping",
		help = "ping.help"
)
public class PingCommand extends Command {
	@Override
	public boolean execute(Context context) {
		JDAUtil.sendMessage(Strings.getString("ping.msg", event).replaceAll("\\[VALUE]", Long.toString(event.getJDA().getPing())), event.getTextChannel());
		return false;
	}
}
