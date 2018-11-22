package commands;

import static localisation.Strings.Lang.EN;
import static localisation.Strings.getString;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.JDAUtil;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ClearCommand implements Command {

	public boolean called(String[] args, MessageReceivedEvent e) {
		return false;
	}

	public void actions(String[] args, MessageReceivedEvent e) {
		e.getMessage().delete().queue();

		if(args.length < 1) {
			e.getTextChannel().sendMessage(JDAUtil.sendEmbed(Color.RED, getString("error", EN), getString("clear" +
					".missingCount", EN))).complete().delete().queueAfter(5, TimeUnit.SECONDS);
		}

		int numb;
		try {
			numb = Integer.parseInt(args[0]);
		}catch(NumberFormatException ex) {
			e.getTextChannel().sendMessage(JDAUtil.sendEmbed(Color.RED, getString("error", EN), getString("clear" +
					".countOutOfRange", EN))).complete().delete().queueAfter(5, TimeUnit.SECONDS);
			return;
		}

		if(numb > 1 && numb <= 100) {
			List<Message> mgs = new MessageHistory(e.getTextChannel()).retrievePast(numb).complete();
			e.getTextChannel().deleteMessages(mgs).queue();

			e.getTextChannel().sendMessage(JDAUtil.sendEmbed(Color.GREEN, getString("success", EN), getString("clear" +
					".success", EN))).complete().delete().queueAfter(5, TimeUnit.SECONDS);
		} else {
			e.getTextChannel().sendMessage(JDAUtil.sendEmbed(Color.GREEN, getString("error", EN), getString("clear" +
					".countOutOfRange", EN))).complete().delete().queueAfter(5, TimeUnit.SECONDS);
		}
	}

	public void executed(boolean success, MessageReceivedEvent e) {

	}

	@Override
	public String getName() {
		return "clear";
	}

	public String help() {
		return null;
	}
}
