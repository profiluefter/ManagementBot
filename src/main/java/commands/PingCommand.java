package commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class PingCommand implements Command {
    public boolean called(String[] args, MessageReceivedEvent e) {
        return false;
    }

    public void actions(String[] args, MessageReceivedEvent e) {
        e.getTextChannel().sendMessage("Pong!").queue();
        e.getTextChannel().sendMessage(e.getAuthor().getJDA().getPing() + "").queue();
    }

    public void executed(boolean success, MessageReceivedEvent e) {

    }

    @Override
    public String getName() {
        return "ping";
    }

    public String help() {
        return null;
    }
}
