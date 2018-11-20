package commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface Command {
    boolean called(String[] args, MessageReceivedEvent e);

    void actions(String[] args, MessageReceivedEvent e);

    void executed(boolean success, MessageReceivedEvent e);

    String getName();

    String help();
}
