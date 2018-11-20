package commands;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class clearChat implements Command {

    EmbedBuilder error = new EmbedBuilder().setColor(Color.RED);
    EmbedBuilder done = new EmbedBuilder().setColor(Color.GREEN);

    private int getInt(String s) {
        return Integer.parseInt(s);
    }

    public boolean called(String[] args, MessageReceivedEvent e) {
        return false;
    }

    public void acions(String[] args, MessageReceivedEvent e) {

        if(args.length < 1) {
            e.getTextChannel().sendMessage(error.setDescription("Bitte Zahl der zu löschenden Nachrichten angeben!").build()).queue();
        }

        int numb = getInt(args[0]);

        if(numb > 1 && numb <= 100) {

            MessageHistory h = new MessageHistory(e.getTextChannel());
            List<Message> mgs;

            e.getMessage().delete().queue();

            mgs = h.retrievePast(numb).complete();
            e.getTextChannel().deleteMessages(mgs).queue();

            Message msg = e.getTextChannel().sendMessage(done.setDescription("Chat cleared.").build()).complete();


        } else {

            e.getMessage().delete().queue();
            e.getTextChannel().sendMessage(error.setDescription("Bitte Zahl zwischen 2 und 100 der zu löschenden Nachrichten angeben!").build()).queue();
        }

    }

    public void executed(boolean success, MessageReceivedEvent e) {

    }

    public String help() {
        return null;
    }
}
