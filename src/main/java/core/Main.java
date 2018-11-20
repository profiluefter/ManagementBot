package core;

import commands.Command;
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

    private static JDABuilder b = new JDABuilder(AccountType.BOT);

    public static void main(String[] args) {
        String token = System.getProperty("discord.token");
        if(token == null) {
            throw new IllegalArgumentException("Please provide the Discord token in the system property discord.token!");
        }

        b.setToken(token);
        b.setAutoReconnect(true);
        b.setStatus(OnlineStatus.ONLINE);

        addListeners();
        addCommands();

        try {
            JDA jda = b.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

    }

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

    private static void addListeners() {
        Reflections reflections = new Reflections("listeners");
        Set<Class<? extends ListenerAdapter>> listeners = reflections.getSubTypesOf(ListenerAdapter.class);
        for (Class<? extends ListenerAdapter> listener : listeners) {
            try {
                ListenerAdapter instance = listener.getConstructor().newInstance();
                b.addEventListener(instance);
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
