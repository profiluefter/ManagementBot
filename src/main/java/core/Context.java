package core;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.JDAUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Context {
	private final MessageReceivedEvent event;
	private final List<String> args;

	public JDA getJDA() {
		return event.getJDA();
	}

	public TextChannel getTextChannel() {
		return event.getTextChannel();
	}

	public User getJDAUser() {
		return event.getAuthor();
	}

	public long getUserID() {
		return event.getAuthor().getIdLong();
	}

	public config.User getUser() {
		return config.User.loadUser(event);
	}

	public long getGuildID() {
		return event.getGuild().getIdLong();
	}

	public Message getMessage() {
		return event.getMessage();
	}

	public List<String> getArgs() {
		ArrayList<String> arrayList = new ArrayList<>(args);
		arrayList.remove(0);
		return arrayList;
	}

	public String getArgsRaw() {
		return args.stream().skip(1).collect(Collectors.joining(" "));
	}

	public void sendMessage(String message) {
		JDAUtil.sendMessage(message, event.getTextChannel());
	}

	public void sendEmbed(MessageEmbed embed) {
		JDAUtil.sendMessage(embed, event.getTextChannel());
	}

	public void sendEmbed(Color color, String title, String description) {
		JDAUtil.sendEmbed(color,title,description,event.getTextChannel());
	}

	public Context(MessageReceivedEvent event, List<String> args) {
		assert args.size() > 0;
		this.event = event;
		this.args = args;
	}

	public Member getMember() {
		return event.getMember();
	}
}
