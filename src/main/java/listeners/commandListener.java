package listeners;

import core.commandHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import util.STATIC;

public class commandListener extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent e) {

        if(e.getMessage().getContentRaw().startsWith(STATIC.PREFIX) && e.getMessage().getAuthor().getId() != e.getJDA().getSelfUser().getId()) {

            commandHandler.handleCommands(commandHandler.p.parser(e.getMessage().getContentRaw(), e));
        }
    }


}
