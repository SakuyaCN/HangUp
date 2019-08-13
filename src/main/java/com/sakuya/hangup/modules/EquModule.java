package com.sakuya.hangup.modules;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sakuya.hangup.entity.*;
import com.sakuya.hangup.modules.bag.BagModule;
import com.sakuya.hangup.modules.player.PlayerModule;
import com.sakuya.hangup.utils.FileUtil;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

public class EquModule {

    String path = "./plugins/HangUp/UserConfig/EquConfig.json";
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
        entities.add(new EquEntity(1,"毁灭之刃",1,"传说","武器",false,10000,""));
        entities.add(new EquEntity(2,"血棘",1,"传说","衣服",false,3000,""));
        FileUtil.writeFile("UserConfig","EquConfig",new Gson().toJson(entities));
    }

    public EquEntity getEqu(int id){
        AtomicReference<EquEntity> tempEqu = new AtomicReference<>();
        try {
            String json = new String(readAllBytes(get(path)));
            List<EquEntity> list = new Gson().fromJson(json, new TypeToken<List<EquEntity>>() {}.getType());
            if(list!= null){
                list.forEach(equEntity -> {
                    if(equEntity.getId() == id){
                        tempEqu.set(equEntity);
                    }
                });
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return tempEqu.get();
    }

    public boolean putEqu(String uuid,EquEntity equEntity){
        PlayerEntity playerEntity = PlayerModule.getInstance().onlinePlayer.get(uuid);
        if(playerEntity!=null){
            switch (equEntity.getType()){
                case "武器":putEquOrBag(1,playerEntity,uuid,equEntity);break;
                case "副手":putEquOrBag(2,playerEntity,uuid,equEntity);break;
                case "头盔":putEquOrBag(3,playerEntity,uuid,equEntity);break;
                case "衣服":putEquOrBag(4,playerEntity,uuid,equEntity);break;
                case "护腿":putEquOrBag(5,playerEntity,uuid,equEntity);break;
                case "鞋子":putEquOrBag(6,playerEntity,uuid,equEntity);break;
            }
        }else {
         return false;
        }
        return false;
    }

    public void putEquOrBag(int index,PlayerEntity playerEntity,String uuid,EquEntity equEntity){
        if(playerEntity.getPlayerEqu()[index] != 0){
            BagEntity bagEntity = BagModule.getInstance().onlineBag.get(uuid);
            bagEntity.getGoods().forEach(item->{
                if(item.getGoodsEntity().getType_id() == playerEntity.getPlayerEqu()[index]){
                    item.getGoodsEntity().setShow(true);
                }if(item.getGoodsEntity().getType_id() == equEntity.getId()){
                    item.getGoodsEntity().setShow(false);
                }
            });
            playerEntity.getPlayerEqu()[index] = equEntity.getId();
            PlayerModule.getInstance().reLoadPlayerAttr(uuid,playerEntity);
        }else{
            playerEntity.getPlayerEqu()[index] = equEntity.getId();
            PlayerModule.getInstance().reLoadPlayerAttr(uuid,playerEntity);
        }
    }

    public void SaveEqu(String uuid,BagEntity bagEntity){
        FileUtil.writeFile("BagData",uuid, new Gson().toJson(bagEntity));
    }
}
