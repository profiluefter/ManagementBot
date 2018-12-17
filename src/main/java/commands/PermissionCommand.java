package commands;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.JDAUtil;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class PermissionCommand implements Command {
	@Override
	public boolean execute(List<String> args, MessageReceivedEvent event) {
		if(args.size() == 0) {
			listPermissions(event);
			return false;
		}
		switch(args.get(0)) {
			case "add":
			case "permit":
			case "grant":
				 return addPermission(args, event);
		}
		return true;
	}

	private boolean addPermission(List<String> args, MessageReceivedEvent event) {
		return false;
	}

	private void listPermissions(MessageReceivedEvent event) {
		User jdaUser = null;
		config.User user = config.User.loadUser(jdaUser.getIdLong());
		JDAUtil.sendEmbed(Color.BLUE,"Permissions for " + jdaUser.getAsMention(),user.getPermissions().stream().collect(Collectors.joining(",\n")), event.getTextChannel());
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
