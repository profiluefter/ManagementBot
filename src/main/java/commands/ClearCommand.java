package commands;

import config.User;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.Strings;

import java.awt.*;
import java.util.List;

import static util.JDAUtil.sendEmbedWithLocalisation;

public class ClearCommand implements Command {

	@Override
	public boolean execute(List<String> args, MessageReceivedEvent e) {
		if (args.size() < 1) {
			sendEmbedWithLocalisation(Color.RED, "error", "clear.missingCount", e.getTextChannel(), User.loadUser(e.getAuthor().getIdLong()));
			return false;
		}

		int numb;
		try {
			numb = Integer.parseInt(args.get(0));
		} catch (NumberFormatException ex) {
			sendEmbedWithLocalisation(Color.RED, "error", "clear.countOutOfRange", e.getTextChannel(), User.loadUser(e.getAuthor().getIdLong()));
			return false;
		}

		if (numb > 1 && numb <= 100) {
			List<Message> mgs = new MessageHistory(e.getTextChannel()).retrievePast(numb).complete();
			e.getTextChannel().deleteMessages(mgs).queue();

			sendEmbedWithLocalisation(Color.GREEN, "success", "clear.success", e.getTextChannel(), User.loadUser(e.getAuthor().getIdLong()));
		} else {
			sendEmbedWithLocalisation(Color.RED, "error", "clear.countOutOfRange", e.getTextChannel(), User.loadUser(e.getAuthor().getIdLong()));
		}
		return false;
	}

	@Override
	public String getName() {
		return "clear";
	}

	public String getHelp(MessageReceivedEvent event) {
		return Strings.getString("clear.help", event);
	}
}