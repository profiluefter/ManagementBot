/* TODO
package commands;

import config.User;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.utils.MiscUtil;
import util.JDAUtil;
import util.Strings;
import util.Touple;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class PermissionCommand implements Command {
	@Override
	public boolean execute(List<String> args, MessageReceivedEvent event) {
		if(args.size() == 0) {
			listPermissions(event);
			return false;
		}
		if(args.size() == 1)
			return true;

		switch(args.get(0)) {
			case "add":
			case "permit":
			case "grant":
				args.remove(0);
				Touple<List<Long>, List<String>> touple = splitMessage(args);
				return addPermissions(touple.a, touple.b, event);
			case "remove":
			case "revoke":
			case "deny":
				args.remove(0);
				Touple<List<Long>, List<String>> touple2 = splitMessage(args);
				return removePermissions(touple2.a, touple2.b, event);
		}
		return true;
	}

	private Touple<List<Long>, List<String>> splitMessage(List<String> message) {
		List<Long> ids = new ArrayList<>();
		List<String> permissions;

		String wholeMessage = String.join(" ", message);

		Matcher matcher = Message.MentionType.USER.getPattern().matcher(wholeMessage);

		while(matcher.find()) {
			try {
				String match = matcher.group(1);
				long id = MiscUtil.parseSnowflake(match);
				if(!ids.contains(id))
					ids.add(id);
				wholeMessage = wholeMessage.replace("<@!" + match + ">", "");
			} catch(NumberFormatException ignored) {
			}
		}

		permissions = Arrays.stream(wholeMessage.split(" ")).filter(s -> !s.trim().isEmpty()).collect(Collectors.toList());

		return new Touple<>(ids, permissions);
	}

	private boolean addPermissions(List<Long> ids, List<String> permissions, MessageReceivedEvent event) {
		if(permissions.size() == 0)
			return true;
		if(ids.size() == 0)
			ids.add(event.getAuthor().getIdLong());

		for(long id : ids) {
			User user = User.loadUser(id);
			for(String permission : permissions) {
				user.addPermission(permission);
			}
		}

		JDAUtil.sendEmbed(Color.GREEN, Strings.getString("success",event),
				Strings.getString("permissions.addPermissions",event)
						.replaceAll("\\[PERMISSIONS]", String.join(", ", permissions))
						.replaceAll("\\[USERS]", ids.stream().map(aLong -> event.getJDA().getUserById(aLong).getAsMention()).collect(Collectors.joining(", ")))
				,event.getTextChannel());
		return false;
	}

	private boolean removePermissions(List<Long> ids, List<String> permissions, MessageReceivedEvent event) {
		if(permissions.size() == 0)
			return true;
		if(ids.size() == 0)
			ids.add(event.getAuthor().getIdLong());

		for(long id : ids) {
			User user = User.loadUser(id);
			for(String permission : permissions) {
				user.removePermission(permission);
			}
		}

		JDAUtil.sendEmbed(Color.GREEN, Strings.getString("success",event),
				Strings.getString("permissions.removePermissions",event)
						.replaceAll("\\[PERMISSIONS]", String.join(", ", permissions))
						.replaceAll("\\[USERS]", ids.stream().map(aLong -> event.getJDA().getUserById(aLong).getAsMention()).collect(Collectors.joining(", ")))
				,event.getTextChannel());
		return false;
	}

	private void listPermissions(MessageReceivedEvent event) {
		User user = config.User.loadUser(event.getAuthor().getIdLong());
		JDAUtil.sendEmbed(Color.BLUE, "Permissions for " + event.getAuthor().getAsMention(), String.join(",\n", user.getPermissions()), event.getTextChannel());
	}

	@Override
	public String[] getName() {
		return new String[]{"permissions", "permission", "perms"};
	}

	@Override
	public String getHelp(MessageReceivedEvent event) {
		return null;
	}
}
*/
