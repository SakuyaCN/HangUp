package com.sakuya.hangup;

import com.sakuya.hangup.command.HumCommand;
import com.sakuya.hangup.command.PlayerCommand;
import com.sakuya.hangup.event.JoinEvent;
import com.sakuya.hangup.event.OutEvent;
import com.sakuya.hangup.modules.goods.GoodsModule;
import com.sakuya.hangup.modules.money.MoneyModule;
import com.sakuya.hangup.modules.player.PlayerModule;
import com.sakuya.hangup.modules.skill.SkillModule;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {
    public static JavaPlugin javaPlugin;
    @Override
    public void onEnable() {
        super.onEnable();
        javaPlugin = this;
        saveDefaultConfig();
        Bukkit.getLogger().info("开冲！");
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
            getConfig().set("isfrist",false);
            saveConfig();
        }
    }
}
