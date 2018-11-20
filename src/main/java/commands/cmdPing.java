package commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class cmdPing implements Command {

    public boolean called(String[] args, MessageReceivedEvent e) {

        return false;
    }

    public void acions(String[] args, MessageReceivedEvent e) {
        e.getTextChannel().sendMessage("Pong!").queue();
    }


    public void executed(boolean success, MessageReceivedEvent e) {


    }

    public String help() {
        return null;
    }

}
