package commands;

import core.CommandHandler;
import localisation.Strings;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import static util.JDAUtil.sendMessage;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class HelpCommand implements Command {
	@Override
	public void execute(List<String> args, MessageReceivedEvent event) {
		List<MessageEmbed.Field> fields = new ArrayList<>();
		for(Command command : CommandHandler.getCommands().values()) {
			fields.add(new MessageEmbed.Field(command.getName(),
					command.getHelp() == null ?
							Strings.getString("help.notAvailable", Strings.Lang.EN) :
							command.getHelp(), false));
		}
		EmbedBuilder embedBuilder =
				new EmbedBuilder().setTitle(Strings.getString("help.title", Strings.Lang.EN)).setColor(Color.BLUE);
		fields.forEach(embedBuilder::addField);
		sendMessage(embedBuilder.build(), event.getTextChannel());
	}

	@Override
	public String getName() {
		return "help";
	}

	@Override
	public String getHelp() {
		return Strings.getString("help.help", Strings.Lang.EN);
	}
}
