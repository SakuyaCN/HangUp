package com.sakuya.hangup.event;

import com.sakuya.hangup.modules.player.PlayerModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OutEvent implements Listener {

    @EventHandler
    public void onJoinEvent(PlayerQuitEvent event){
        PlayerModule.getInstance().playerOut(event.getPlayer().getUniqueId().toString());
    }
}
