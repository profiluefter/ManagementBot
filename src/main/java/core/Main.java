package core;

import commands.clearChat;
import commands.cmdPing;
import listeners.ReadyListener;
import listeners.ShutdownListener;
import listeners.commandListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;

import javax.security.auth.login.LoginException;

public class Main {

    public static JDABuilder b = new JDABuilder(AccountType.BOT);

    public static void main(String[] args) {



        b.setToken("NTE0MTA2NTI1MTA3NjE3ODE0.DtRu1A.2E7D8fe98Afqxxvl7ZWNvp8PFek");
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

    public static void addCommands() {

        commandHandler.commands.put("ping", new cmdPing());
        commandHandler.commands.put("clear", new clearChat());

    }

    public static void addListeners() {

        b.addEventListener(new ReadyListener());
        b.addEventListener(new ShutdownListener());
        b.addEventListener(new commandListener());

    }

}
