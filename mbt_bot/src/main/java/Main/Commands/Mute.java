package Main.Commands;

import java.util.Objects;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Mute extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if (args[0].equalsIgnoreCase(Main.prefix + "mute") &&
                args.length == 2) {
            Role role = (Role) event.getGuild().getRoleById("875128720967499866");
            Member member = event.getMessage().getMentionedMembers().get(0);
            if (Objects.<Member>requireNonNull(event.getMember()).hasPermission(Permission.MESSAGE_MANAGE)) {
                if (!member.getRoles().contains(role)) {
                    event.getGuild().addRoleToMember(member, role).queue();
                    event.getChannel().sendMessage("Muted member.").queue();
                }
            } else {
                event.getChannel().sendMessage("Incorrect Syntax. Use `!mute` [user mention] [time (optional)]").queue();
            }
        }
    }
}