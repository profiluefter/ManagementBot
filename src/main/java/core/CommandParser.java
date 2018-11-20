package core;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.STATIC;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandParser {
    public static CommandContainer parser(String raw, MessageReceivedEvent e) {
        String beheaded = raw.replaceFirst(STATIC.PREFIX, "");
        String[] splitBeheaded = beheaded.split(" ");
        String invoke = splitBeheaded[0];

        ArrayList<String> split = new ArrayList<String>(Arrays.asList(splitBeheaded));

        String[] args = new String[split.size() - 1];
        split.subList(1, split.size()).toArray(args);

        return new CommandContainer(raw, beheaded, splitBeheaded, invoke, args, e);
    }

    static class CommandContainer {
        final String raw;
        final String beheaded;
        final String[] splitBeheaded;
        final String invoke;
        final String[] args;
        final MessageReceivedEvent e;

        CommandContainer(String rw, String beheaded, String[] splitBeheaded, String invoke, String[] args, MessageReceivedEvent e) {
            this.raw = rw;
            this.beheaded = beheaded;
            this.splitBeheaded = splitBeheaded;
            this.invoke = invoke;
            this.args = args;
            this.e = e;
        }
    }
}
