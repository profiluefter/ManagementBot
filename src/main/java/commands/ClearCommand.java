package commands;

import localisation.Strings;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ClearCommand implements Command {
    private EmbedBuilder error = new EmbedBuilder().setColor(Color.RED);
    private EmbedBuilder done = new EmbedBuilder().setColor(Color.GREEN);

    public boolean called(String[] args, MessageReceivedEvent e) {
        return false;
    }

    public void actions(String[] args, MessageReceivedEvent e) {
        e.getMessage().delete().queue();

        if (args.length < 1) {
            e.getTextChannel().sendMessage(error.setDescription(Strings.getString("clear.missingCount", Strings.Lang.EN)).build()).complete().delete().queueAfter(5, TimeUnit.SECONDS);
        }

        int numb;
        try {
            numb = Integer.parseInt(args[0]);
        } catch (NumberFormatException ex) {
            e.getTextChannel().sendMessage(error.setDescription(Strings.getString("clear.countOutOfRange", Strings.Lang.EN)).build()).complete().delete().queueAfter(5,TimeUnit.SECONDS);
            return;
        }

        if (numb > 1 && numb <= 100) {
            List<Message> mgs = new MessageHistory(e.getTextChannel()).retrievePast(numb).complete();
            e.getTextChannel().deleteMessages(mgs).queue();

            e.getTextChannel().sendMessage(done.setDescription(Strings.getString("clear.success", Strings.Lang.EN)).build()).complete().delete().queueAfter(5,TimeUnit.SECONDS);
        } else {
            e.getTextChannel().sendMessage(error.setDescription(Strings.getString("clear.countOutOfRange", Strings.Lang.EN)).build()).complete().delete().queueAfter(5,TimeUnit.SECONDS);
        }
    }

    public void executed(boolean success, MessageReceivedEvent e) {

    }

    @Override
    public String getName() {
        return "clear";
    }

    public String help() {
        return null;
    }
}
