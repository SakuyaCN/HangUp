package com.sakuya.hangup.modules.player;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sakuya.hangup.Main;
import com.sakuya.hangup.entity.EquEntity;
import com.sakuya.hangup.entity.LvEntity;
import com.sakuya.hangup.entity.PlayerAttr;
import com.sakuya.hangup.entity.PlayerEntity;

import com.sakuya.hangup.modules.EquModule;
import com.sakuya.hangup.modules.bag.BagModule;
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
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftMetaBook;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

public class PlayerModule {

    public HashMap<String,PlayerEntity> onlinePlayer;
    File file =new File("./plugins/HangUp/UserConfig/PlayerConfig.yml");
    private static volatile PlayerModule sInst = null;
    public YamlConfiguration playerCg = null;
    public static PlayerModule getInstance() {
        PlayerModule inst = sInst;
        if (inst == null) {
            synchronized (PlayerModule.class) {
                inst = sInst;
                if (inst == null) {
                    inst = new PlayerModule();
                    sInst = inst;
                }
            }
        }
        return inst;
    }

    public PlayerModule() {
        playerCg = YamlConfiguration.loadConfiguration(file);
        onlinePlayer = new HashMap<>();
    }

    public void fristLoad(){
        playerCg = YamlConfiguration.loadConfiguration(file);
        playerCg.set("maxLv",100);
        List<LvEntity> list = new ArrayList<>();
        for (int i = 1;i<=100;i++){
            playerCg.set("lvExp."+i,i+1);
            playerCg.set("lvExp.exp",(((i+1)*(i+5))*(99*(i+1))/10));
        }
        playerCg.set("lvExp",list);
        try {
            playerCg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public File getPlayerJson(String uuid){
        File file = new File("./plugins/HangUp/PlayerData/"+ uuid +".json");
        if(file.length()==0){
            return null;
        }
        return file;
    }

    public PlayerEntity getPlayerEntity(String uuid){
        try {
            String json = new String(readAllBytes(get("./plugins/HangUp/PlayerData/"+uuid+".json")));
            return new Gson().fromJson(json,PlayerEntity.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void playerJoin(String uuid){
        PlayerEntity playerEntity = getPlayerEntity(uuid);
        onlinePlayer.put(uuid,playerEntity);
        new BukkitRunnable() {
            @Override
            public void run() {
                BagModule.getInstance().updateBagEntity(uuid);
                reLoadPlayerAttr(uuid,playerEntity);
            }
        }.run();
        System.out.println("join");
    }

    public void playerOut(String uuid){
        onlinePlayer.remove(uuid);
        BagModule.getInstance().onlineBag.remove(uuid);
        System.out.println("quit");
    }

    public void reLoadPlayerAttr(String uuid,PlayerEntity entity){
        long m = System.currentTimeMillis();
        System.out.println("耗时："+m);
        PlayerAttr playerAttr;
        int[] other = new int[4];
        playerAttr = setAttr(entity);
        if(entity.getSkill()!=null){
            for (int i : entity.getSkill()){
                switch (i){
                    case 3 :{
                        int tmp = 10 * entity.getLv();
                        other[0] = tmp;
                        playerAttr.setHp(playerAttr.getHp()+ tmp* 10);
                        playerAttr.setAtk(playerAttr.getAtk()+ tmp * 5);
                    }break;
                    case 4 :{
                        int tmp = 10 * entity.getLv();
                        other[1] = tmp;
                        playerAttr.setMv(playerAttr.getMv()+ tmp* 10);
                        playerAttr.setAtk(playerAttr.getAtk()+ tmp * 5);
                    }break;
                    case 5 :{
                        int tmp = 10 * entity.getLv();
                        other[2] = tmp;
                        playerAttr.setDef(playerAttr.getDef()+ tmp* 4);
                        playerAttr.setSpeed(playerAttr.getSpeed()+ tmp);
                    }break;
                    case 6 :{
                        int tmp = 10 * entity.getLv();
                        other[3] = tmp;
                        playerAttr.setRes(playerAttr.getRes()+ tmp* 3);
                        playerAttr.setmDef(playerAttr.getmDef()+ tmp*2);
                    }break;
                }
            }
        }
        if(entity.getPlayerEqu()!=null){
            for(int index = 0;index < entity.getPlayerEqu().length;index++){
                if(entity.getPlayerEqu()[index]!=0){
                    for(int i=0;i<entity.getPlayerEqu().length;i++){
                        if(entity.getPlayerEqu()[i] != 0){
                            EquEntity equEntity = EquModule.getInstance().getEqu(entity.getPlayerEqu()[i]);
                            System.out.println(equEntity.toString());
                            switch (index){
                                case 0:playerAttr.setAtk(playerAttr.getAtk()+equEntity.getBaseAttr());break;
                                case 1:playerAttr.setmDef(playerAttr.getmDef()+equEntity.getBaseAttr());break;
                                case 2:
                                case 4:
                                case 5:
                                    playerAttr.setDef(playerAttr.getDef()+equEntity.getBaseAttr());break;
                                case 3:playerAttr.setHp(playerAttr.getHp()+equEntity.getBaseAttr());break;
                            }
                            if(equEntity.getEntry_attr()!=null && !equEntity.getEntry_attr().isEmpty()){
                                JsonObject jsonObject = null;
                                try {
                                    jsonObject = new Gson().fromJson(equEntity.getEntry_attr(), JsonObject.class);
                                }catch (Exception e){
                                    System.out.println("请检查配置文件格式是否正确！\n配置出错装备ID："+equEntity.getId());
                                    return;
                                }if(jsonObject==null) return;
                                setEntryAttr(playerAttr,other,jsonObject);
                            }
                        }
                    }
                }
            }
        }

        entity.setOtherAttr(other);
        entity.setPlayerAttr(playerAttr);
        SavePlayer(uuid,entity);
        System.out.println("耗时："+(System.currentTimeMillis()-m));
    }

    public void setEntryAttr(PlayerAttr playerAttr,int[] other,JsonObject jsonObject){
        if(jsonObject.get("atk")!=null){
            playerAttr.setAtk(playerAttr.getAtk()+jsonObject.get("atk").getAsInt());
        }if(jsonObject.get("def")!=null){
            playerAttr.setDef(playerAttr.getDef()+jsonObject.get("def").getAsInt());
        }if(jsonObject.get("hp")!=null){
            playerAttr.setHp(playerAttr.getHp()+jsonObject.get("hp").getAsInt());
        }if(jsonObject.get("mv")!=null){
            playerAttr.setMv(playerAttr.getMv()+jsonObject.get("mv").getAsInt());
        }if(jsonObject.get("mdef")!=null){
            playerAttr.setmDef(playerAttr.getmDef()+jsonObject.get("mdef").getAsInt());
        }if(jsonObject.get("res")!=null){
            playerAttr.setRes(playerAttr.getRes()+jsonObject.get("res").getAsInt());
        }if(jsonObject.get("crit")!=null){
            playerAttr.setCrit(playerAttr.getCrit()+jsonObject.get("crit").getAsInt());
        }if(jsonObject.get("ct")!=null){
            playerAttr.setCt(playerAttr.getCt()+jsonObject.get("ct").getAsInt());
        }if(jsonObject.get("speed")!=null){
            playerAttr.setSpeed(playerAttr.getSpeed()+jsonObject.get("speed").getAsInt());
        }if(jsonObject.get("power")!=null){
            other[0] = other[0] + jsonObject.get("power").getAsInt();
        }if(jsonObject.get("intelligence")!=null){
            other[1] = other[1] + jsonObject.get("intelligence").getAsInt();
        }if(jsonObject.get("agile")!=null){
            other[2] = other[2] + jsonObject.get("agile").getAsInt();
        }if(jsonObject.get("spirit")!=null){
            other[3] = other[3] + jsonObject.get("spirit").getAsInt();
        }
        setOther(playerAttr,other);
    }

    public PlayerAttr setAttr(PlayerEntity entity){
        PlayerAttr playerAttr = new PlayerAttr();
        playerAttr.setHp(100+entity.getAttr()[0] * 10);
        playerAttr.setAtk(30+entity.getAttr()[0] * 5);
        playerAttr.setDef(45+entity.getAttr()[2] * 4);
        playerAttr.setmDef(10+entity.getAttr()[3]*2);
        playerAttr.setMv(10+entity.getAttr()[1] * 10);
        playerAttr.setCrit(0);
        playerAttr.setRes(1+entity.getAttr()[3]);
        playerAttr.setCt(0+entity.getAttr()[1]);
        playerAttr.setSpeed(100+entity.getAttr()[2]);
        return playerAttr;
    }

    public void setOther(PlayerAttr playerAttr,int[] other){
        playerAttr.setHp(playerAttr.getHp()+other[0] * 10);
        playerAttr.setAtk(playerAttr.getAtk()+other[0] * 5);
        playerAttr.setDef(playerAttr.getDef()+other[2] * 4);
        playerAttr.setmDef(playerAttr.getmDef()+other[3]*2);
        playerAttr.setMv(playerAttr.getMv()+other[1] * 10);
        playerAttr.setCrit(0);
        playerAttr.setRes(playerAttr.getRes()+other[3]);
        playerAttr.setCt(playerAttr.getCt()+other[1]);
        playerAttr.setSpeed(playerAttr.getSpeed()+other[2]);
    }

    public void SavePlayer(String uuid,PlayerEntity playerEntity){
        new BukkitRunnable() {
            @Override
            public void run() {
                FileUtil.writeFile("PlayerData",uuid, new Gson().toJson(playerEntity));
                onlinePlayer.put(uuid,playerEntity);
            }
        }.run();
    }

    public void openEquMenu(Player player){
        PlayerEntity playerEntity = onlinePlayer.get(player.getUniqueId().toString());
        IconMenu equMenu = new IconMenu("玩家装备", 45, event -> {
            if(event.getName()!=null){
                switch (event.getPosition()){
                    case 12:sendPlayer(player,playerEntity.getPlayerEqu()[0]);break;
                    case 14:sendPlayer(player,playerEntity.getPlayerEqu()[1]);break;
                    case 21:sendPlayer(player,playerEntity.getPlayerEqu()[2]);break;
                    case 23:sendPlayer(player,playerEntity.getPlayerEqu()[3]);break;
                    case 30:sendPlayer(player,playerEntity.getPlayerEqu()[4]);break;
                    case 32:sendPlayer(player,playerEntity.getPlayerEqu()[5]);break;
                }
                event.setWillClose(true);
            }
        }, Main.javaPlugin);
        if(equMenu!=null) {
            equMenu.setOption(12, new ItemStack(Material.DIAMOND_SWORD, 1), "武器", getEquInfo(EquModule.getInstance().getEqu(playerEntity.getPlayerEqu()[0])));
            equMenu.setOption(14, new ItemStack(Material.SHIELD, 1), "副手", getEquInfo(EquModule.getInstance().getEqu(playerEntity.getPlayerEqu()[1])));
            equMenu.setOption(21, new ItemStack(Material.DIAMOND_HELMET, 1), "头盔", getEquInfo(EquModule.getInstance().getEqu(playerEntity.getPlayerEqu()[2])));
            equMenu.setOption(23, new ItemStack(Material.DIAMOND_CHESTPLATE, 1), "衣服", getEquInfo(EquModule.getInstance().getEqu(playerEntity.getPlayerEqu()[3])));
            equMenu.setOption(30, new ItemStack(Material.DIAMOND_LEGGINGS, 1), "护腿", getEquInfo(EquModule.getInstance().getEqu(playerEntity.getPlayerEqu()[4])));
            equMenu.setOption(32, new ItemStack(Material.DIAMOND_BOOTS, 1), "鞋子", getEquInfo(EquModule.getInstance().getEqu(playerEntity.getPlayerEqu()[5])));
        }
        equMenu.open(player);
    }

    private void sendPlayer(Player player,int id){
        if(id==0){
            return;
        }
        EquEntity equEntity = EquModule.getInstance().getEqu(id);
        player.spigot().sendMessage(Util.getHc("§b鼠标移至名称查看：§n"+Util.colorText(equEntity.getPz(),equEntity.getName()),MenuConfig.getEquInfo(equEntity)));
        player.spigot().sendMessage(Util.getTc("§b操作该装备：",""),Util.getTc("§a§n[卸下]","/humcmd downEqu "+equEntity.getId()),Util.getTc("§a§n[展示]","/humcmd showEqu "+equEntity.getId()));
    }

    public List<String> getEquInfo(EquEntity equEntity){
        if(equEntity!=null){
            return MenuConfig.getEquInfo(equEntity);
        }
        return Arrays.asList("未装备");
    }

    public ItemStack getPlayerBook(String uuid) {
        PlayerEntity player = onlinePlayer.get(uuid);
        if(player==null){
            return null;
        }
        try {
            ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta meta = (BookMeta) book.getItemMeta();
            List<IChatBaseComponent> pages = (List<IChatBaseComponent>) CraftMetaBook.class.getDeclaredField("pages").get(meta); //This
            TextComponent hi = new TextComponent(new ComponentBuilder("§2AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAB\n").create());
            TextComponent hi2 = new TextComponent(new ComponentBuilder("§2111111111111111111111111111111112\n").create());
            TextComponent hi3 = new TextComponent(new ComponentBuilder("§2啊沙发沙发沙发沙发沙发沙发沙发沙发阿三\n").create());
            IChatBaseComponent page = IChatBaseComponent.ChatSerializer.a(ComponentSerializer.toString(new BaseComponent[]{hi,hi2,hi3})); //This
            pages.add(page);
            meta.setTitle("玩家界面");
            meta.setAuthor("Optics Server");
            book.setItemMeta(meta);
            return book;
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {

        }
        return null;
    }
}
