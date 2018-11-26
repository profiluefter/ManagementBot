package commands;

import localisation.Strings;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class PingCommand implements Command {
    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage(Strings.getString("ping.msg", Strings.Lang.EN).replaceAll("\\[VALUE]",Long.toString(event.getJDA().getPing()))).queue();
    }

    @Override
    public String getName() {
        return "ping";
    }

    public String help() {
        return null;
    }
}
