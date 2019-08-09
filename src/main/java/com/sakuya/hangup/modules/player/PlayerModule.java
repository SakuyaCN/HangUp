package com.sakuya.hangup.modules.player;

import com.google.gson.Gson;
import com.sakuya.hangup.entity.LvEntity;
import com.sakuya.hangup.entity.PlayerAttr;
import com.sakuya.hangup.entity.PlayerEntity;

import com.sakuya.hangup.modules.bag.BagModule;
import com.sakuya.hangup.utils.FileUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
            System.out.println(json);
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
}
