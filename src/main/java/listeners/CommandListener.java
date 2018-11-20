package listeners;

import core.CommandHandler;
import core.CommandParser;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import util.STATIC;

public class CommandListener extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getMessage().getContentRaw().startsWith(STATIC.PREFIX) && e.getMessage().getAuthor().getIdLong() != e.getJDA().getSelfUser().getIdLong()) {
            CommandHandler.handleCommands(CommandParser.parser(e.getMessage().getContentRaw(), e));
        }
    }
}
