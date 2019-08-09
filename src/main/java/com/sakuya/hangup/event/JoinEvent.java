package com.sakuya.hangup.event;

import com.sakuya.hangup.modules.create.PlayerCreate;
import com.sakuya.hangup.utils.BookUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.md_5.bungee.api.chat.*;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutCustomPayload;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class JoinEvent implements Listener {

    private PlayerCreate playerCreate;

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event){
        TextComponent message = new TextComponent( "Click me" );
        message.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org" ) );
        message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "Visit the Spigot website!" ).create() ) );
        Player player = event.getPlayer();
        player.spigot().sendMessage(message);
        //player.spigot().sendMessage( new ComponentBuilder( "Hello " ).color( ChatColor.RED ).bold( true ).append( "world" ).color( ChatColor.DARK_RED ).append( "!" ).color( ChatColor.RED ).create() );
        playerCreate = new PlayerCreate();
        playerCreate.joinMsg(player);
    }
}
