package commands;

import config.User;
import core.Command;
import core.CommandDescription;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;

import static util.JDAUtil.sendEmbedWithLocalisation;

@CommandDescription(
		name = {"clear", "delete"},
		help = "clear.help"
)
public class ClearCommand extends Command {

	@Override
	public boolean execute(List<String> args, MessageReceivedEvent e) {
		if(args.size() < 1) {
			sendEmbedWithLocalisation(Color.RED, "error", "clear.missingCount", e.getTextChannel(), User.loadUser(e.getAuthor().getIdLong()));
			return false;
		}

		int numb;
		try {
			numb = Integer.parseInt(args.get(0));
		} catch(NumberFormatException ex) {
			sendEmbedWithLocalisation(Color.RED, "error", "clear.countOutOfRange", e.getTextChannel(), User.loadUser(e.getAuthor().getIdLong()));
			return false;
		}

		if(numb > 1 && numb <= 100) {
			List<Message> mgs = new MessageHistory(e.getTextChannel()).retrievePast(numb).complete();
			e.getTextChannel().deleteMessages(mgs).queue();

			sendEmbedWithLocalisation(Color.GREEN, "success", "clear.success", e.getTextChannel(), User.loadUser(e.getAuthor().getIdLong()));
		} else {
			sendEmbedWithLocalisation(Color.RED, "error", "clear.countOutOfRange", e.getTextChannel(), User.loadUser(e.getAuthor().getIdLong()));
		}
		return false;
	}
}