package Main.Commands;

import java.util.List;
import java.util.Objects;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Clear extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if (args[0].equalsIgnoreCase(Main.prefix + "clear") && (
                (Member)Objects.<Member>requireNonNull(event.getMember())).hasPermission(new Permission[] { Permission.MESSAGE_MANAGE }))
            if (args.length < 2) {
                EmbedBuilder usage = new EmbedBuilder();
                usage.setColor(16726307);
                usage.setTitle("Specify amount to delete");
                usage.setDescription("Usage: `" + Main.prefix + "clear [# of messages]`");
                event.getChannel().sendMessageEmbeds(usage.build(), new net.dv8tion.jda.api.entities.MessageEmbed[0]).queue();
            } else {
                try {
                    List<Message> messages = (List<Message>)event.getChannel().getHistory().retrievePast(Integer.parseInt(args[1])).complete();
                    event.getChannel().deleteMessages(messages).queue();
                    EmbedBuilder success = new EmbedBuilder();
                    success.setColor(2293546);
                    success.setTitle("Successfully deleted " + args[1] + " messages.");
                    event.getChannel().sendMessageEmbeds(success.build(), new net.dv8tion.jda.api.entities.MessageEmbed[0]).queue();
                } catch (IllegalArgumentException e) {
                    if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")) {
                        EmbedBuilder error = new EmbedBuilder();
                        error.setColor(16726307);
                        error.setTitle("Too many messages selected");
                        error.setDescription("Between 1-100 messages can be deleted at one time.");
                        event.getChannel().sendMessageEmbeds(error.build(), new net.dv8tion.jda.api.entities.MessageEmbed[0]).queue();
                    } else {
                        EmbedBuilder error = new EmbedBuilder();
                        error.setColor(16726307);
                        error.setTitle("Selected messages are older than 2 weeks");
                        error.setDescription("Messages older than 2 weeks cannot be deleted.");
                        event.getChannel().sendMessageEmbeds(error.build(), new net.dv8tion.jda.api.entities.MessageEmbed[0]).queue();
                    }
                }
            }
    }
}
