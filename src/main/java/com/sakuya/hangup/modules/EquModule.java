package com.sakuya.hangup.modules;

import com.google.gson.Gson;
import com.sakuya.hangup.entity.BagEntity;
import com.sakuya.hangup.entity.EquEntity;
import com.sakuya.hangup.entity.QuestEntity;
import com.sakuya.hangup.utils.BookUtils;
import com.sakuya.hangup.utils.FileUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftMetaBook;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;

public class EquModule {

    String path = "./plugins/HangUp/UserConfig/QuestConfig.json";
    private static volatile EquModule sInst = null;
    private List<EquEntity> entities;

    public static EquModule getInstance() {
        EquModule inst = sInst;
        if (inst == null) {
            synchronized (EquModule.class) {
                inst = sInst;
                if (inst == null) {
                    inst = new EquModule();
                    sInst = inst;
                }
            }
        }
        return inst;
    }

    public EquModule() {
        entities = new ArrayList<>();
    }


    public void firstLoad(){
        entities.add(new EquEntity("1","毁灭之刃",1,"传说","武器",false,10000,""));
        entities.add(new EquEntity("2","血棘",1,"传说","衣服",false,3000,""));
        FileUtil.writeFile("UserConfig","QuestConfig",new Gson().toJson(entities));
    }

    public void SaveEqu(String uuid,BagEntity bagEntity){
        FileUtil.writeFile("BagData",uuid, new Gson().toJson(bagEntity));
    }
}
