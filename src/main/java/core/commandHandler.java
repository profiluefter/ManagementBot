package core;

import commands.Command;

import java.util.HashMap;

public class commandHandler {

    public static final commandParser p = new commandParser();
    public static HashMap<String, Command> commands = new HashMap<String, Command>();

    public static void handleCommands(commandParser.commandContainer cmd) {

        if(commands.containsKey(cmd.invoke)) {

            boolean safe = commands.get(cmd.invoke).called(cmd.args, cmd.e);

            if(!safe) {
                commands.get(cmd.invoke).acions(cmd.args, cmd.e);
                commands.get(cmd.invoke).executed(safe, cmd.e);

            } else {

                commands.get(cmd.invoke).executed(false, cmd.e);
            }

        }


    }


}
