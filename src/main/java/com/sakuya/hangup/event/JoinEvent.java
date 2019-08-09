package com.sakuya.hangup.event;

import com.sakuya.hangup.modules.create.PlayerCreate;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    private PlayerCreate playerCreate;

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        playerCreate = new PlayerCreate();
        playerCreate.joinMsg(player);

    }
}
