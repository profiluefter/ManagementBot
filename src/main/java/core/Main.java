package core;

import commands.ClearCommand;
import commands.PingCommand;
import listeners.CommandListener;
import listeners.ReadyListener;
import listeners.ShutdownListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;

import javax.security.auth.login.LoginException;

public class Main {

    private static JDABuilder b = new JDABuilder(AccountType.BOT);

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

    private static void addCommands() {
        CommandHandler.commands.put("ping", new PingCommand());
        CommandHandler.commands.put("clear", new ClearCommand());
    }

    private static void addListeners() {
        b.addEventListener(new ReadyListener());
        b.addEventListener(new ShutdownListener());
        b.addEventListener(new CommandListener());
    }

}
