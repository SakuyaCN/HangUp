package com.sakuya.hangup.modules.player;

import com.google.gson.Gson;
import com.sakuya.hangup.Main;
import com.sakuya.hangup.entity.LvEntity;
import com.sakuya.hangup.entity.PlayerAttr;
import com.sakuya.hangup.entity.PlayerEntity;

import com.sakuya.hangup.modules.bag.BagModule;
import com.sakuya.hangup.modules.menu.IconMenu;
import com.sakuya.hangup.utils.BookUtils;
import com.sakuya.hangup.utils.FileUtil;
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
    private IconMenu equMenu;
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
        new Thread(()->{
            BagModule.getInstance().updateBagEntity(uuid);
            reLoadPlayerAttr(uuid,playerEntity);

        }).start();
        System.out.println("join");
    }

    public void playerOut(String uuid){
        onlinePlayer.remove(uuid);
        System.out.println("quit");
    }

    public void reLoadPlayerAttr(String uuid,PlayerEntity entity){
        PlayerAttr playerAttr;
        int[] other = entity.getOtherAttr();
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
        entity.setOtherAttr(other);
        entity.setPlayerAttr(playerAttr);
        SavePlayer(uuid,entity);
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

    public void SavePlayer(String uuid,PlayerEntity playerEntity){
        FileUtil.writeFile("PlayerData",uuid, new Gson().toJson(playerEntity));
        onlinePlayer.put(uuid,playerEntity);
    }

    public void openEquMenu(Player player){
        equMenu = new IconMenu("玩家背包", 45, event -> {
            if(!event.getName().equals("未解锁")){
            }
        }, Main.javaPlugin);
        if(equMenu!=null) {
            equMenu.setOption(12, new ItemStack(Material.DIAMOND_SWORD, 1), "武器", Arrays.asList("未解锁"));
            equMenu.setOption(14, new ItemStack(Material.SHIELD, 1), "副手", Arrays.asList("未解锁"));
            equMenu.setOption(21, new ItemStack(Material.DIAMOND_HELMET, 1), "头盔", Arrays.asList("未解锁"));
            equMenu.setOption(23, new ItemStack(Material.DIAMOND_CHESTPLATE, 1), "衣服", Arrays.asList("未解锁"));
            equMenu.setOption(30, new ItemStack(Material.DIAMOND_LEGGINGS, 1), "护腿", Arrays.asList("未解锁"));
            equMenu.setOption(32, new ItemStack(Material.DIAMOND_BOOTS, 1), "鞋子", Arrays.asList("未解锁"));
        }
        equMenu.open(player);
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
