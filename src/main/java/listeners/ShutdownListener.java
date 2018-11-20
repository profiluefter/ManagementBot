package listeners;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ShutdownEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ShutdownListener extends ListenerAdapter {

    public void onShutdown(ShutdownEvent e) {

        for(Guild g : e.getJDA().getGuilds()) {
            g.getTextChannels().get(0).sendMessage("Bye :wave:").queue();

        }

    }


}
