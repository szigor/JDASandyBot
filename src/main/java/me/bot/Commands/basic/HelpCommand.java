package me.bot.Commands.basic;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class HelpCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        String messageSent = event.getMessage().getContentRaw();

        if (messageSent.equalsIgnoreCase("s!help")) {
            event.getChannel().sendMessage(
                    "__**Lista wszystkich moich komend:**__\n" +
                            "`!tips` - Pokazuje tipy na dana postac ***[!tips*** <***nazwa postaci***>***]***\n" +
                            "`!ping` - pokazuje swój ping"
            ).queue();
        }
    }
}
