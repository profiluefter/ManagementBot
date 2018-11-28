package commands;

import core.CommandHandler;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.Strings;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static util.JDAUtil.sendMessage;

public class HelpCommand implements Command {
	@Override
	public boolean execute(List<String> args, MessageReceivedEvent event) {
		List<MessageEmbed.Field> fields = new ArrayList<>();
		for(Command command : CommandHandler.getCommands().values()) {
			fields.add(new MessageEmbed.Field(command.getName(),
					command.getHelp(event) == null ?
							Strings.getString("help.notAvailable", event.getAuthor().getIdLong()) :
							command.getHelp(event), false));
		}
		EmbedBuilder embedBuilder =
				new EmbedBuilder().setTitle(Strings.getString("help.title", event.getAuthor().getIdLong())).setColor(Color.BLUE);
		fields.forEach(embedBuilder::addField);
		sendMessage(embedBuilder.build(), event.getTextChannel());
		return false;
	}

	@Override
	public String getName() {
		return "help";
	}

	@Override
	public String getHelp(MessageReceivedEvent event) {
		return Strings.getString("help.help", event.getAuthor().getIdLong());
	}
}
