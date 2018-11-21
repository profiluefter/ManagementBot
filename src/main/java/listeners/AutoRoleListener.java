package listeners;

import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.concurrent.TimeUnit;

public class AutoRoleListener extends ListenerAdapter {
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		event.getGuild().getController().addRolesToMember(
				event.getMember(),
				event.getGuild().getRoleById(489495090214207489L)
		).queueAfter(10, TimeUnit.MINUTES);
	}
}
