package listeners;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

public class ReadyListener extends ListenerAdapter {
    public void onReady(ReadyEvent e) {
        String startMSG = "HI :wave: ";

        for (Guild g : e.getJDA().getGuilds()) {
            if(false)
                g.getTextChannels().get(0).sendMessage(new EmbedBuilder().setColor(Color.GREEN).setDescription(startMSG).build()).queue();
        }
    }
}
