package commands;

import core.Command;
import core.CommandDescription;
import core.CommandHandler;
import core.Context;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import util.Strings;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static util.JDAUtil.sendMessage;

@CommandDescription(
		name = {"help", "commands"},
		help = "help.help"
)
public class HelpCommand extends Command {
	@Override
	public boolean execute(Context context) {
		List<MessageEmbed.Field> fields = new ArrayList<>();
		for(Command command : CommandHandler.getCommands().values()) { //TODO: Order
			MessageEmbed.Field field = new MessageEmbed.Field(command.getDescription().name()[0],
					Strings.getString(command.getDescription().help(), event), false);
			if(!fields.contains(field))
				fields.add(field);

		}
		EmbedBuilder embedBuilder =
				new EmbedBuilder().setTitle(Strings.getString("help.title", event)).setColor(Color.BLUE);
		fields.forEach(embedBuilder::addField);
		sendMessage(embedBuilder.build(), event.getTextChannel());
		return false;
	}
}
