package me.bot.Commands.tips;

import me.bot.Commands.CommandContext;
import me.bot.Commands.ICommand;
import me.bot.Tips.TipsManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.List;

public class TipCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {

        TextChannel textChannel = ctx.getEvent().getChannel().asTextChannel();
        List<String> args = ctx.getArgs();

        if (args.size() > 2) {
            textChannel.sendMessage("Podaj prawidlowa nazwe postaci").queue();
        } else if (args.size() == 0) {
            textChannel.sendMessage("Podaj nazwe postaci").queue();
        } else {
            String champName = nameFixer(args);


            List<String> allyTips = new TipsManager(champName).getAllyTips();
            List<String> enemyTips = new TipsManager(champName).getEnemyTips();

            executeSpaces(allyTips);
            executeSpaces(enemyTips);

            String allyTipsString = String.join("@", allyTips).replaceAll("@", "\n\n- ");
            String enemyTipsString = String.join("@", enemyTips).replaceAll("@", "\n\n- ");

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder
                    .setColor(0x20B2AA)
                    .setTitle(champName + " tips")
                    .setThumbnail("http://ddragon.leagueoflegends.com/cdn/12.17.1/img/champion/" + champName + ".png")
                    .addField(champName + " as your enemy tips:", new StringBuilder(enemyTipsString).insert(0, "\n- ").toString(), true)
                    .addField(champName + " as your ally tips:", new StringBuilder(allyTipsString).insert(0, "\n- ").toString(), true)
//                    .setImage("http://ddragon.leagueoflegends.com/cdn/img/champion/splash/" + champName + "_0.jpg")
                    .build();

            textChannel.sendMessageEmbeds(embedBuilder.build()).queue();
        }

    }

    @Override
    public String getName() {
        return "tips";
    }

    private static String nameFixer(List<String> list) {
        List<String> listFixed = new ArrayList<>();
        if (list.size() > 1) {
            for (String s : list) {
                String toLower = s.toLowerCase();
                listFixed.add(toLower.substring(0, 1).toUpperCase() + toLower.substring(1));
            }
            return String.join("", listFixed);
        } else if (list.size() == 1) {
            String toLower = list.get(0).replaceAll("'", "").toLowerCase();
            listFixed.add(toLower.substring(0, 1).toUpperCase() + toLower.substring(1));
            return String.join("", listFixed);
        }
        return null;
    }

    private static void executeSpaces(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).startsWith(" ")) {
                list.set(i, list.get(i).replaceFirst(" ", ""));
            }
        }
    }
}
