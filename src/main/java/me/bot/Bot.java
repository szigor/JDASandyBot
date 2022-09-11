package me.bot;

import me.bot.Commands.basic.HelpCommand;
import me.bot.Commands.basic.PingCommand;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class Bot {
    public static void main(String[] args) throws LoginException {
        JDABuilder.createDefault(
                System.getenv("TOKEN"),
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.GUILD_PRESENCES,
                GatewayIntent.GUILD_MEMBERS
        )
                .addEventListeners(
                        new Listener(),
                        new HelpCommand(),
                        new PingCommand())
                .setActivity(Activity.playing("s!help - komendy"))
                .build();
    }
}
