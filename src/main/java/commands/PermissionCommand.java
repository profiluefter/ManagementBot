package commands;

import config.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class PermissionCommand implements Command{
	@Override
	public boolean execute(List<String> args, MessageReceivedEvent event) {
		User user = User.loadUser(event);
		user.addPermission("HEINRICH LOHL");
		user.getPermissions().forEach(System.out::println);
		return false;
	}

	@Override
	public String getName() {
		return "permission";
	}

	@Override
	public String getHelp(MessageReceivedEvent event) {
		return null;
	}
}
