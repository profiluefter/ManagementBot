package commands;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.JDAUtil;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static localisation.Strings.Lang.EN;
import static localisation.Strings.getString;

public class ClearCommand implements Command {

	@Override
	public void execute(List<String> args, MessageReceivedEvent e) {
		if (args.size() < 1) {
			e.getTextChannel().sendMessage(JDAUtil.generateEmbed(Color.RED, getString("error", EN), getString(
					"clear.missingCount", EN))).complete().delete().queueAfter(5, TimeUnit.SECONDS);
		}

		int numb;
		try {
			numb = Integer.parseInt(args.get(0));
		} catch (NumberFormatException ex) {
			e.getTextChannel().sendMessage(JDAUtil.generateEmbed(Color.RED, getString("error", EN), getString(
					"clear.countOutOfRange", EN))).complete().delete().queueAfter(5, TimeUnit.SECONDS);
			return;
		}

		if (numb > 1 && numb <= 100) {
			List<Message> mgs = new MessageHistory(e.getTextChannel()).retrievePast(numb).complete();
			e.getTextChannel().deleteMessages(mgs).queue();

			e.getTextChannel().sendMessage(JDAUtil.generateEmbed(Color.GREEN, getString("success", EN), getString(
					"clear.success", EN))).complete().delete().queueAfter(5, TimeUnit.SECONDS);
		} else {
			e.getTextChannel().sendMessage(JDAUtil.generateEmbed(Color.GREEN, getString("error", EN), getString(
					"clear.countOutOfRange", EN))).complete().delete().queueAfter(5, TimeUnit.SECONDS);
		}
	}

	@Override
	public String getName() {
		return "clear";
	}

	public String help() {
		return null;
	}
}