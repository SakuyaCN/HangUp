package com.sakuya.hangup.utils;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Util {
    public static String getText(String text){
        return ChatColor.AQUA + "【HangUp】" +text;
    }

    public static TextComponent getTc(String text,String cmd){
        TextComponent tcMessage = new TextComponent(Util.getText(text));
        tcMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,cmd));
        return tcMessage;
    }

    public static String getJobName(String job){
        switch (job){
            case "shooter" : return "射手";
            case "warrior" : return "战士";
            case "mage" : return "法师";
        }
        return "";
    }
    public static List<Integer> random(int n, int L){
        List<Integer> list = new ArrayList<>();
        Random rand = new Random();
        int temp = L;
        for(int i = 1, j; i < n; i++){
            j = rand.nextInt(temp-1) + 1;
            if(j > temp-(n-i)){//保证每个随机数最小为1，那么j就不能大于L-
                j = temp-(n-i);
            }
            else if(j<= 0){
                j = 1;
            }
            temp -= j;
            list.add(j);
        }
        list.add(temp);
        Collections.shuffle(list);
        return list;
    }

    public static String colorText(String type,String text){
        switch (type){
            case "普通" : return "§f"+text;
            case "优秀" : return "§b"+text;
            case "精良" : return "§a"+text;
            case "史诗" : return "§5"+text;
            case "传说" : return "§4"+text;
            default: return "§f"+text;
        }
    }

}
