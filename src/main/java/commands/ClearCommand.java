package commands;

import config.User;
import core.Command;
import core.CommandDescription;
import core.Context;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;

import java.awt.*;
import java.util.List;

import static util.JDAUtil.sendEmbedWithLocalisation;

@SuppressWarnings("unused")
@CommandDescription(
		name = {"clear", "delete"},
		help = "clear.help"
)
public class ClearCommand extends Command {

	@Override
	public boolean execute(Context context) {
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