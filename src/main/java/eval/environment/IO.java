package eval.environment;

import net.dv8tion.jda.core.entities.TextChannel;
import util.DiscordChannelWriter;

import java.io.PrintWriter;

public class IO {
    private static TextChannel textChannel;
    private PrintWriter writer;
    
    public static void setChannel(TextChannel channel) {
        textChannel = channel;
    }
    
    IO() {
        writer = new PrintWriter(new DiscordChannelWriter(textChannel));
    }
    
    public void println(Object object) {
        writer.println(object);
    }
    
    public void print(Object object) {
        writer.print(object);
    }
}