package com.sakuya.hangup;

import com.mojang.authlib.GameProfile;
import com.sakuya.hangup.command.HumCommand;
import com.sakuya.hangup.command.PlayerCommand;
import com.sakuya.hangup.entity.QuestEntity;
import com.sakuya.hangup.event.JoinEvent;
import com.sakuya.hangup.event.OutEvent;
import com.sakuya.hangup.modules.EquModule;
import com.sakuya.hangup.modules.goods.GoodsModule;
import com.sakuya.hangup.modules.money.MoneyModule;
import com.sakuya.hangup.modules.player.PlayerModule;
import com.sakuya.hangup.modules.quest.QuestModule;
import com.sakuya.hangup.modules.skill.SkillModule;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;

public class Main extends JavaPlugin {
    public static JavaPlugin javaPlugin;
    @Override
    public void onEnable() {
        super.onEnable();
        javaPlugin = this;
        saveDefaultConfig();
        Bukkit.getLogger().info("[HangUp]开冲！");
        Bukkit.getLogger().info("[HangUp]更多功能定制联系！");
        Bukkit.getLogger().info("[HangUp]QQ:347764670");
        new File("./plugins/HangUp", "\\UserConfig").mkdirs();
        new File("./plugins/HangUp", "\\PlayerData").mkdirs();
        new File("./plugins/HangUp", "\\BagData").mkdirs();
        configStart();
        Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OutEvent(), this);
        Bukkit.getPluginCommand("hum").setExecutor(new PlayerCommand());
        Bukkit.getPluginCommand("humcmd").setExecutor(new HumCommand());
    }

    private void configStart(){
        if(getConfig().getBoolean("isfrist")){
            GoodsModule.getInstance().fristLoad();
            MoneyModule.getInstance().fristLoad();
            PlayerModule.getInstance().fristLoad();
            SkillModule.getInstance().fristLoad();
            QuestModule.getInstance().firstLoad();
            EquModule.getInstance().firstLoad();
            getConfig().set("isfrist",false);
            saveConfig();
        }
    }

//    public void spawnFakePlayer(Player player, String displayname){
//        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
//        WorldServer world = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();
//
//        Player target = Bukkit.getServer().getPlayer(displayname);
//        EntityPlayer npc;
//        if (target != null) {
//            npc = new EntityPlayer(server, world, new GameProfile(target.getUniqueId(), target.getName()), new PlayerInteractManager(world));
//        } else {
//            OfflinePlayer op = Bukkit.getServer().getOfflinePlayer(displayname);
//            npc = new EntityPlayer(server, world, new GameProfile(op.getUniqueId(), displayname), new PlayerInteractManager(world));
//        }
//        npc.setLocation(0, 0, 0, 0, 0);
//
//        for(Player all : Bukkit.getOnlinePlayers()){
//            PlayerConnection connection = ((CraftPlayer)all).getHandle().playerConnection;
//            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
//            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
//        }
//    }
}
