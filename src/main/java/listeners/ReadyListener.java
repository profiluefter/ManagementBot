package listeners;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ReadyListener extends ListenerAdapter {


    public void onReady(ReadyEvent e) {

        String startMSG = "HI :wave: ";

        for(Guild g : e.getJDA().getGuilds()) {
            g.getTextChannels().get(0).sendMessage(startMSG).queue();

        }

    }

}
