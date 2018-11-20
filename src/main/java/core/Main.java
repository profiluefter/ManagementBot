package core;

import listeners.ReadyListener;
import listeners.ShutdownListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;

import javax.security.auth.login.LoginException;

public class Main {

    public static JDABuilder b = new JDABuilder(AccountType.BOT);

    public static void main(String[] args) {

//Sicker Token

        b.setToken("NTE0MTA2NTI1MTA3NjE3ODE0.DtRu1A.2E7D8fe98Afqxxvl7ZWNvp8PFek");
        b.setAutoReconnect(true);

        b.setStatus(OnlineStatus.ONLINE);

        addListeners();

        try {
            JDA jda = b.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

    }

    public static void addListeners() {

        b.addEventListener(new ReadyListener());
        b.addEventListener(new ShutdownListener());

    }

}
