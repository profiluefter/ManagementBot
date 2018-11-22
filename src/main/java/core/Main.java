package core;

import commands.Command;
import localisation.Strings;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.reflections.Reflections;

import javax.security.auth.login.LoginException;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        String token = System.getenv("discord.token");
        if(token == null) {
            throw new IllegalArgumentException("Please provide the Discord token in the system property discord.token!");
        }

	    JDABuilder jdaBuilder = new JDABuilder(AccountType.BOT);
        jdaBuilder.setToken(token);
        jdaBuilder.setAutoReconnect(true);
        jdaBuilder.setStatus(OnlineStatus.ONLINE);

        Strings.init();
	    addCommands();
	    addListeners(jdaBuilder);

        try {
            JDA jda = jdaBuilder.build().awaitReady();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }

    }

	/**
	 * Searches the package commands for Commands
	 * and adds them to the CommandHandler
	 */
    private static void addCommands() {
        Reflections reflections = new Reflections("commands");
        Set<Class<? extends Command>> commands = reflections.getSubTypesOf(Command.class);
        for (Class<? extends Command> command : commands) {
            try {
                Command instance = command.getConstructor().newInstance();
                CommandHandler.commands.put(instance.getName(),instance);
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

	/**
	 * Searches the package listeners for Listeners
	 * and registers them to the provided JDABuilder
	 * @param jdaBuilder the instance to register the listeners to
	 */
    private static void addListeners(JDABuilder jdaBuilder) {
        Reflections reflections = new Reflections("listeners");
        Set<Class<? extends ListenerAdapter>> listeners = reflections.getSubTypesOf(ListenerAdapter.class);
        for (Class<? extends ListenerAdapter> listener : listeners) {
            try {
                ListenerAdapter instance = listener.getConstructor().newInstance();
                jdaBuilder.addEventListener(instance);
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
