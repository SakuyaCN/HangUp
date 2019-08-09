package com.sakuya.hangup.modules.goods;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sakuya.hangup.Main;
import com.sakuya.hangup.entity.GoodsEntity;
import com.sakuya.hangup.entity.PlayerEntity;
import com.sakuya.hangup.entity.SkillEntity;
import com.sakuya.hangup.modules.menu.IconMenu;
import com.sakuya.hangup.modules.menu.MenuConfig;
import com.sakuya.hangup.modules.player.PlayerModule;
import com.sakuya.hangup.utils.FileUtil;
import com.sakuya.hangup.utils.Util;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

public class GoodsModule {

    String path = "./plugins/HangUp/UserConfig/GoodsConfig.json";

    private static volatile GoodsModule sInst = null;
    public List<GoodsEntity> goodsEntities;
    private IconMenu skillMenu;

    public static GoodsModule getInstance() {
        GoodsModule inst = sInst;
        if (inst == null) {
            synchronized (GoodsModule.class) {
                inst = sInst;
                if (inst == null) {
                    inst = new GoodsModule();
                    sInst = inst;
                }
            }
        }
        return inst;
    }

    public GoodsModule() {
        goodsEntities = new ArrayList<>();
    }

    public void fristLoad(){
        goodsEntities.add(new GoodsEntity(1001,"硬质木材",1,"普通","材料",0,"一块普普通通的硬质木材,可用于装备的打造,任务等需求.",Material.WOOD));
        goodsEntities.add(new GoodsEntity(1002,"稀有铁锭",1,"优秀","材料",0,"一块较为稀有的铁矿石,可用于装备的打造,任务等需求.",Material.IRON_INGOT));
        goodsEntities.add(new GoodsEntity(1003,"稀有青铜",1,"优秀","材料",0,"一块较为稀有的青铜块,可用于装备的打造,任务等需求.",Material.EMERALD_BLOCK));
        FileUtil.writeFile("UserConfig","GoodsConfig",new Gson().toJson(goodsEntities));
    }

    public List<GoodsEntity> getGoodsformJson(){
        try {
            String json = new String(readAllBytes(get(path)));
            return new Gson().fromJson(json, new TypeToken<List<GoodsEntity>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
