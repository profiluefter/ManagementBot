package music.commands;

import commands.Command;
import music.MusicManager;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class ResumeCommand implements Command {
	@Override
	public boolean execute(List<String> args, MessageReceivedEvent event) {
		MusicManager.interacted(event.getTextChannel(), event.getAuthor().getIdLong());
		MusicManager.resume(event.getGuild().getIdLong());
		return false;
	}

	@Override
	public String[] getName() {
		return new String[]{"resume","unpause"};
	}

	@Override
	public String getHelp(MessageReceivedEvent event) {
		return null;
	}
}
