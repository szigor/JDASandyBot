package me.bot.Commands.basic;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PingCommand extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {

        String messageSent = event.getMessage().getContentRaw();

        if (messageSent.equalsIgnoreCase("!ping")) {
            event.getChannel().sendMessage("Moj ping: **" + event.getJDA().getGatewayPing() + "ms**").queue();
        }

    }
}
