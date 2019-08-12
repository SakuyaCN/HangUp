package com.sakuya.hangup.modules.quest;

import com.google.gson.Gson;
import com.sakuya.hangup.Main;
import com.sakuya.hangup.entity.*;
import com.sakuya.hangup.modules.menu.IconMenu;
import com.sakuya.hangup.modules.menu.MenuConfig;
import com.sakuya.hangup.utils.BookUtils;
import com.sakuya.hangup.utils.FileUtil;
import com.sakuya.hangup.utils.Util;
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

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

public class QuestModule {

    String path = "./plugins/HangUp/UserConfig/QuestConfig.json";
    private static volatile QuestModule sInst = null;
    private List<QuestEntity> entities;

    public static QuestModule getInstance() {
        QuestModule inst = sInst;
        if (inst == null) {
            synchronized (QuestModule.class) {
                inst = sInst;
                if (inst == null) {
                    inst = new QuestModule();
                    sInst = inst;
                }
            }
        }
        return inst;
    }

    public QuestModule() {
        entities = new ArrayList<>();
    }


    public void firstLoad(){
        entities.add(new QuestEntity(1,"新手任务，挖矿！",1,0,"这是一个新手任务，需要你去挖10个铁矿！",
                "游戏","挖矿",10,true,true,null,false));
        entities.add(new QuestEntity(1,"升级，升级，升级！",1,0,"将HangUp等级升级到10级！",
                "HangUp","升级",10,true,true,null,false));
        FileUtil.writeFile("UserConfig","QuestConfig",new Gson().toJson(entities));
    }

    public void showQuestBook(Player player){
        BookUtils.openBook(getExampleBook(),player);
    }

    public void SaveBag(String uuid,BagEntity bagEntity){
        FileUtil.writeFile("BagData",uuid, new Gson().toJson(bagEntity));
    }

    public static ItemStack getExampleBook() {
        try {
            ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta meta = (BookMeta) book.getItemMeta();
            List<IChatBaseComponent> pages = (List<IChatBaseComponent>) CraftMetaBook.class.getDeclaredField("pages").get(meta); //This
            TextComponent hi = new TextComponent(new ComponentBuilder("Hello!").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("HI There!").create())).create()); //This
            IChatBaseComponent page = IChatBaseComponent.ChatSerializer.a(ComponentSerializer.toString(new BaseComponent[]{hi,hi,hi})); //This
            pages.add(page);
            pages.add(page);
            meta.setTitle("Example");
            meta.setAuthor("Optics Server");
            book.setItemMeta(meta);
            return book;
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {

        }
        return null;
    }
}
