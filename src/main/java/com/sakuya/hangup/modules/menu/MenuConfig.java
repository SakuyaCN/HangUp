package com.sakuya.hangup.modules.menu;

import com.sakuya.hangup.entity.BagEntity;
import com.sakuya.hangup.entity.BagGoods;
import com.sakuya.hangup.entity.PlayerEntity;
import com.sakuya.hangup.entity.SkillEntity;
import com.sakuya.hangup.modules.bag.BagModule;
import com.sakuya.hangup.modules.player.PlayerModule;
import com.sakuya.hangup.modules.skill.SkillModule;
import com.sakuya.hangup.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class MenuConfig {

    public static List<String> getPlayerEntity(String uuid){
        PlayerEntity playerEntity = PlayerModule.getInstance().onlinePlayer.get(uuid);
        List<String> strings = new ArrayList<>();
        if(playerEntity!=null){
            strings.add("§e角色名称: "+playerEntity.getuName());
            strings.add("§e当前等级: Lv."+playerEntity.getLv());
            strings.add("§e当前经验: "+playerEntity.getExp());
            strings.add("§e当前职业: "+ Util.getJobName(playerEntity.getJob()));
            strings.add("§9§l——————————————————");
            strings.add("§7基础属性：");
            strings.add("   §7力量： "+playerEntity.getAttr()[0] +" §9+"+ playerEntity.getOtherAttr()[0]);
            strings.add("   §7智力： "+playerEntity.getAttr()[1] +" §9+"+ playerEntity.getOtherAttr()[1]);
            strings.add("   §7敏捷： "+playerEntity.getAttr()[2] +" §9+"+ playerEntity.getOtherAttr()[2]);
            strings.add("   §7精神： "+playerEntity.getAttr()[3] +" §9+"+ playerEntity.getOtherAttr()[3]);
            strings.add("§9§l——————————————————");
            strings.add("§a战斗属性：");
            strings.add("   §a生 命 值： "+playerEntity.getPlayerAttr().getHp());
            strings.add("   §a魔 法 值： "+playerEntity.getPlayerAttr().getMv());
            strings.add("   §a攻 击 力： "+playerEntity.getPlayerAttr().getAtk());
            strings.add("   §a防 御 力： "+playerEntity.getPlayerAttr().getDef());
            strings.add("   §a法 防 值： "+playerEntity.getPlayerAttr().getmDef());
            strings.add("   §a攻 速 值： "+playerEntity.getPlayerAttr().getSpeed());
            strings.add("   §a抵 抗 性： "+playerEntity.getPlayerAttr().getRes());
            strings.add("   §a暴 击 率： "+playerEntity.getPlayerAttr().getCrit());
            strings.add("   §a破 甲 值： "+playerEntity.getPlayerAttr().getCt());

        }
        return strings;
    }

    public static List<String> getPlayerSkill(String uuid){
        PlayerEntity playerEntity = PlayerModule.getInstance().onlinePlayer.get(uuid);
        List<String> strings = new ArrayList<>();
        if(playerEntity!=null){
            if(playerEntity.getSkill()== null){
                strings.add("§9§l——————————————————");
                strings.add("§b当前还未学习技能，点击领取随机初始技能！");
            }else{
                strings.add("§9§l——————————————————");
                strings.add("§b当前已学习技能：");
                for(int i : playerEntity.getSkill()){
                    for(SkillEntity skillEntity : SkillModule.getInstance().getAllSKill()){
                        if(skillEntity.getSkill_id() == i){
                            strings.add(" - §b："+Util.colorText(skillEntity.getSkill_pz(),skillEntity.getSkill_name()));
                        }
                    }
                }
            }
        }
        return strings;
    }

    public static List<String> getBagList(String uuid){
        BagEntity bagEntity = BagModule.getInstance().onlineBag.get(uuid);
        List<String> strings = new ArrayList<>();
        if(bagEntity!=null){
            int num = 0;
            if (bagEntity.getGoods() != null){
                num = bagEntity.getGoods().size();
            }
            strings.add("§9§l——————————————————");
            strings.add("§b背包容量:"+bagEntity.getBagMax());
            strings.add("");
            strings.add("§a已用容量:"+num);
            strings.add("§9§l——————————————————");
            strings.add("§6点击进入背包");
        }
        return strings;
    }

    public static List<String> getBagListInfo(BagGoods goods){
        List<String> strings = new ArrayList<>();
        if(goods!=null){
            strings.add("§9§l——————————————————");
            strings.add("§b物品名称: "+Util.colorText(goods.getGoodsEntity().getGoods_pz(),goods.getGoodsEntity().getGoods_name()));
            strings.add("§b物品品质: "+Util.colorText(goods.getGoodsEntity().getGoods_pz(),goods.getGoodsEntity().getGoods_pz()));
            strings.add("§b物品等级: Lv."+goods.getGoodsEntity().getGoods_lv());
            strings.add("§b物品类型: "+goods.getGoodsEntity().getGoods_type());
            strings.add("§b物品说明: "+goods.getGoodsEntity().getGoods_ctx());
            strings.add("§9§l——————————————————");
            strings.add("§6数    量:           "+goods.getCount());
        }
        return strings;
    }

    public static List<String> getAllSkillInfo(SkillEntity entity){
        List<String> strings = new ArrayList<>();
        strings.add("§9§l——————————————————");
        strings.add("§7§l技能说明：");
        strings.add(" - §e技能等级： Lv."+entity.getSkill_lv());
        strings.add(" - §e技能品质： "+Util.colorText(entity.getSkill_pz(),entity.getSkill_pz()));
        strings.add(" - §e技能类型： "+entity.getSkill_type());
        strings.add(" - §e可否遗忘： "+entity.isDelete());
        strings.add(" - §e技能说明： "+entity.getSkill_ctx());
        return strings;
    }
}
