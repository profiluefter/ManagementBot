package listeners;

import config.Config;
import core.CommandHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {
	public void onMessageReceived(MessageReceivedEvent e) {
		//Test if the message starts with the prefix of the bot or if the author of the message is a bot
		if (e.getMessage().getContentRaw().startsWith(Config.get("prefix")) && !e.getMessage().getAuthor().isBot()) {
			CommandHandler.handle(e);
		}
	}
}
