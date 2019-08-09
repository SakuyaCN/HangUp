package com.sakuya.hangup.modules.skill;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sakuya.hangup.Main;
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

public class SkillModule {

    File file =new File("./plugins/HangUp/UserConfig/SkillConfig.json");

    private static volatile SkillModule sInst = null;
    public YamlConfiguration skillCg = null;
    public List<SkillEntity> skillEntities;
    private IconMenu skillMenu;

    public static SkillModule getInstance() {
        SkillModule inst = sInst;
        if (inst == null) {
            synchronized (SkillModule.class) {
                inst = sInst;
                if (inst == null) {
                    inst = new SkillModule();
                    sInst = inst;
                }
            }
        }
        return inst;
    }

    public SkillModule() {
        skillEntities = new ArrayList<>();
    }

    public void fristLoad(){
        skillEntities.add(new SkillEntity(1,"幸运金币","初始技能之一，每次打怪可以额外获得5%的金币加成！",1,"优秀","被动",false));
        skillEntities.add(new SkillEntity(2,"幸运经验","初始技能之一，每次打怪可以额外获得5%的经验加成！",1,"优秀","被动",false));
        skillEntities.add(new SkillEntity(3,"力量强化","初始技能之一，永久提高玩家等级*10点力量！",1,"优秀","被动",false));
        skillEntities.add(new SkillEntity(4,"智力强化","初始技能之一，永久提高玩家等级*10点智力！",1,"优秀","被动",false));
        skillEntities.add(new SkillEntity(5,"敏捷强化","初始技能之一，永久提高玩家等级*10点敏捷！",1,"优秀","被动",false));
        skillEntities.add(new SkillEntity(6,"精神强化","初始技能之一，永久提高玩家等级*10点精神！",1,"优秀","被动",false));
        skillEntities.add(new SkillEntity(7,"高级力量强化","进阶技能之一，永久提高玩家等级*100点力量！",10,"精良","被动",false));
        skillEntities.add(new SkillEntity(8,"特技力量强化","高等技能之一，永久提高玩家等级*300点力量！",30,"史诗","被动",false));
        skillEntities.add(new SkillEntity(9,"神级力量强化","神级技能之一，永久提高玩家等级*500点力量！",50,"传说","被动",false));
        FileUtil.writeSkill(new Gson().toJson(skillEntities));
    }

    public List<SkillEntity> getSkillEntitiesForJson(){
        try {
            String json = new String(readAllBytes(get("./plugins/HangUp/UserConfig/SkillConfig.json")));
            return new Gson().fromJson(json, new TypeToken<List<SkillEntity>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SkillEntity> getAllSKill(){
        if(skillEntities==null || skillEntities.size()==0){
            skillEntities = getSkillEntitiesForJson();
        }
        return skillEntities;
    }

    public void SkillClick(Player player,IconMenu.OptionClickEvent event){
        PlayerEntity playerEntity = PlayerModule.getInstance().onlinePlayer.get(player.getUniqueId().toString());
        if(playerEntity!=null){
            if(playerEntity.getSkill()== null){
                int index = (int) (Math.random()* 6);
                int[] skills = new int[]{getAllSKill().get(index).getSkill_id()};
                playerEntity.setSkill(skills);
                player.sendTitle(Util.getText("已学习技能： "+getAllSKill().get(index).getSkill_name()),"",10,50,10);
                PlayerModule.getInstance().reLoadPlayerAttr(player.getUniqueId().toString(),playerEntity);
            }else{
                openSkillMenu(player);
            }
        }else{
            player.sendMessage("无法读取玩家属性!");
        }
    }

    public void openSkillMenu(Player player){
        skillMenu = new IconMenu("HangUp技能菜单", 45, event -> {
            switch (event.getName()){

            }
            event.setWillClose(true);
        }, Main.javaPlugin);
        AtomicInteger code = new AtomicInteger(18);
        skillMenu.setOption(0, new ItemStack(Material.BOOK_AND_QUILL, 1), "已学习技能",
                MenuConfig.getPlayerSkill(player.getUniqueId().toString()));
        getAllSKill().forEach((skillEntity -> {
            skillMenu.setOption(code.get(), new ItemStack(Material.WRITTEN_BOOK, 1), Util.colorText(skillEntity.getSkill_pz(),"【图鉴】"+skillEntity.getSkill_name()),
                    MenuConfig.getAllSkillInfo(skillEntity));
            code.addAndGet(1);
        }));
        skillMenu.open(player);
    }
}
