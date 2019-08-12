package com.sakuya.hangup.modules.bag;

import com.google.gson.Gson;
import com.sakuya.hangup.Main;
import com.sakuya.hangup.entity.*;
import com.sakuya.hangup.modules.menu.IconMenu;
import com.sakuya.hangup.modules.menu.MenuConfig;
import com.sakuya.hangup.utils.FileUtil;
import com.sakuya.hangup.utils.Util;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

public class BagModule {

    public HashMap<String, BagEntity> onlineBag;
    private static volatile BagModule sInst = null;
    private IconMenu bagMenu;

    public static BagModule getInstance() {
        BagModule inst = sInst;
        if (inst == null) {
            synchronized (BagModule.class) {
                inst = sInst;
                if (inst == null) {
                    inst = new BagModule();
                    sInst = inst;
                }
            }
        }
        return inst;
    }

    public BagModule() {
        onlineBag = new HashMap<>();
    }

    public File getBagFile(String uuid){
        File file = new File("./plugins/HangUp/BagData/"+ uuid +".json");
        if(file.length()==0){
            return null;
        }
        return file;
    }

    public PlayerEntity getBagEntity(String uuid){
        try {
            String json = new String(readAllBytes(get("./plugins/HangUp/BagData/"+uuid+".json")));
            return new Gson().fromJson(json,PlayerEntity.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void openBagMenu(Player player){
        bagMenu = new IconMenu("玩家背包", 45, event -> {
            if(!event.getName().equals("未解锁")){
                String uuid = event.getPlayer().getUniqueId().toString();
                //removeGoodes(uuid,onlineBag.get(uuid).getGoods().get(event.getPosition()).getGoodsEntity(),1);
            }
        }, Main.javaPlugin);
        BagEntity entity = onlineBag.get(player.getUniqueId().toString());
        if(bagMenu!=null) {
            if(entity.getGoods() != null){
                AtomicInteger code = new AtomicInteger(0);
                if(entity.getGoods().size()>0){
                    entity.getGoods().forEach((bagGoods -> {
                        bagMenu.setOption(code.get(), new ItemStack(bagGoods.getGoodsEntity().getImg(), 1), Util.colorText(bagGoods.getGoodsEntity().getGoods_pz(),bagGoods.getGoodsEntity().getGoods_name()),
                                MenuConfig.getBagListInfo(bagGoods));
                        code.addAndGet(1);
                    }));
                }
            }
            AtomicInteger code = new AtomicInteger(entity.getBagMax());
            while (code.get()<45){
                bagMenu.setOption(code.get(), new ItemStack(Material.THIN_GLASS, 1), "未解锁", Arrays.asList("未解锁"));
                code.addAndGet(1);
            }
            bagMenu.open(player);
        }
    }

    public boolean addGoods(String uuid,GoodsEntity entity,int num){
        AtomicBoolean isNull = new AtomicBoolean(true);
        BagEntity bagEntity = onlineBag.get(uuid);
        if(bagEntity.getGoods()!= null) {
            if(bagEntity.getGoods().size()>=bagEntity.getBagMax()){
                return false;
            }
            bagEntity.getGoods().forEach((goods) -> {
                if (entity.getGoods_id() == goods.getGoodsEntity().getGoods_id()) {
                    goods.setCount(goods.getCount() + num);
                    isNull.set(false);
                    return;
                }
            });
        }else{
            List<BagGoods> list = new ArrayList<>();
            list.add(new BagGoods(0,entity,num));
            bagEntity.setGoods(list);
            isNull.set(false);
        }
        if(isNull.get()){
            bagEntity.getGoods().add(new BagGoods(0,entity,num));
        }
        SaveBag(uuid,bagEntity);
        return true;
    }

    public boolean removeGoodes(String uuid,GoodsEntity entity,int num){
        AtomicBoolean isOk = new AtomicBoolean(false);
        BagEntity bagEntity = onlineBag.get(uuid);
        if(bagEntity.getGoods() != null){
            Iterator<BagGoods> list = bagEntity.getGoods().iterator();
            while (list.hasNext()){
                BagGoods entity1 = list.next();
                if (entity.getGoods_id() == entity1.getGoodsEntity().getGoods_id()) {
                    if(entity1.getCount() - num < 0){
                        break;
                    }else if(entity1.getCount() - num == 0){
                        isOk.set(true);
                        bagEntity.getGoods().remove(entity1);
                        System.out.println("size "+ bagEntity.getGoods().size());
                        break;
                    }else{
                        isOk.set(true);
                        entity1.setCount(entity1.getCount() - num);
                        break;
                    }
                }
            }
        }else{
            return isOk.get();
        }
        if(isOk.get()){
            SaveBag(uuid,bagEntity);
        }
        System.out.println("size "+ bagEntity.getGoods().size());
        return isOk.get();
    }

    public void updateBagEntity(String uuid){
        try {
            String json = new String(readAllBytes(get("./plugins/HangUp/BagData/"+uuid+".json")));
            onlineBag.put(uuid,new Gson().fromJson(json,BagEntity.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SaveBag(String uuid,BagEntity bagEntity){
        FileUtil.writeFile("BagData",uuid, new Gson().toJson(bagEntity));
        onlineBag.put(uuid,bagEntity);
    }
}
