package Main.Commands;

import java.util.Objects;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Ban extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if (args.length <= 2 && (
                (Member)Objects.<Member>requireNonNull(event.getMember())).hasPermission(new Permission[] { Permission.BAN_MEMBERS }) && args[0].equalsIgnoreCase(Main.prefix + "ban")) {
            Guild guild = event.getGuild();
            Member member = event.getMessage().getMentionedMembers().get(0);
            member.ban(0, "ban command").queue();

            EmbedBuilder banned = new EmbedBuilder();
            banned.setTitle("Successfully Banned User");
            event.getChannel().sendMessageEmbeds(banned.build(), new net.dv8tion.jda.api.entities.MessageEmbed[0]).queue();
        }
    }
}