package com.sakuya.hangup.modules.menu;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sakuya.hangup.entity.*;
import com.sakuya.hangup.modules.EquModule;
import com.sakuya.hangup.modules.bag.BagModule;
import com.sakuya.hangup.modules.player.PlayerModule;
import com.sakuya.hangup.modules.skill.SkillModule;
import com.sakuya.hangup.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
            strings.add("§9§l——————————————————");
            strings.add("§b§l点击进入玩家详情");

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
            AtomicInteger num = new AtomicInteger();
            if (bagEntity.getGoods() != null){
                bagEntity.getGoods().forEach(item->{
                    if(item.getGoodsEntity().isShow())
                        num.addAndGet(1);
                });
            }
            strings.add("§9§l——————————————————");
            strings.add("§b背包容量:"+bagEntity.getBagMax());
            strings.add("");
            strings.add("§a已用容量:"+num.get());
            strings.add("§9§l——————————————————");
            strings.add("§6点击进入背包");
        }
        return strings;
    }

    public static List<String> getBagListInfo(BagGoods goods){
        List<String> strings = new ArrayList<>();
        if(goods!=null){
            if(goods.getGoodsEntity().getGoods_type().equals("装备")){
                EquEntity equEntity = EquModule.getInstance().getEqu(goods.getGoodsEntity().getType_id());
                strings.addAll(getEquInfo(equEntity));
                strings.add("§9§l—————————————————————");
                strings.add("§b§l点击进行更多操作");

            }else {
                strings.add("§9§l——————————————————");
                strings.add("§b - 名称: " + Util.colorText(goods.getGoodsEntity().getGoods_pz(), goods.getGoodsEntity().getGoods_name()));
                strings.add("§b - 品质: " + Util.colorText(goods.getGoodsEntity().getGoods_pz(), goods.getGoodsEntity().getGoods_pz()));
                strings.add("§b - 等级: Lv." + goods.getGoodsEntity().getGoods_lv());
                strings.add("§b - 类型: " + goods.getGoodsEntity().getGoods_type());
                strings.add("§b - 说明: " + goods.getGoodsEntity().getGoods_ctx());
                strings.add("§9§l——————————————————");
                strings.add("§6数    量:           " + goods.getCount());
            }
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

    public static List<String> getQuestList(){
        List<String> strings = new ArrayList<>();
        strings.add("§9§l—————————————————————");
        strings.add(" - §e§l【左键】点击进入任务列表");
        strings.add("");
        strings.add(" - §b§l【右键】点击进入我的任务");
        return strings;
    }

    public static List<String> getEquInfo(EquEntity equEntity){
        List<String> strings = new ArrayList<>();
        strings.add(Util.colorText(equEntity.getPz(),"§l"+equEntity.getName()));
        strings.add("§9§l—————————————————————");
        strings.add(" - §b等级： Lv."+equEntity.getLv());
        strings.add(" - §b品质： "+Util.colorText(equEntity.getPz(),equEntity.getPz()));
        strings.add(" - §b部位： "+equEntity.getType());
        strings.add("§9§l—————————————————————");
        strings.add("§7基础属性：");
        switch (equEntity.getType()){
            case "武器": strings.add("   §a基础攻击+ "+equEntity.getBaseAttr()); break;
            case "副手": strings.add("   §a基础法防+ "+equEntity.getBaseAttr()); break;
            case "头盔":
            case "护腿":
            case "鞋子":
                strings.add("   §a基础物防+ "+equEntity.getBaseAttr()); break;
            case "衣服": strings.add("   §a基础生命+ "+equEntity.getBaseAttr()); break;
        }
        if(equEntity.getEntry_attr()!=null && !equEntity.getEntry_attr().isEmpty()){
            strings.add("§9§l—————————————————————");
            strings.add("§7额外属性：");
            JsonObject jsonObject = null;
            try {
                jsonObject = new Gson().fromJson(equEntity.getEntry_attr(), JsonObject.class);
            }catch (Exception e){
                System.out.println("请检查配置文件格式是否正确！\n配置出错装备ID："+equEntity.getId());
            }if(jsonObject!=null) {
                setEntryAttr(jsonObject,strings);
            }
        }
        return strings;
    }

    public static void setEntryAttr(JsonObject jsonObject,List<String> strings){
        if(jsonObject.get("atk")!=null){
            strings.add("   §d攻击+ "+jsonObject.get("atk").getAsInt());
        }if(jsonObject.get("def")!=null){
            strings.add("   §d防御+ "+jsonObject.get("def").getAsInt());
        }if(jsonObject.get("hp")!=null){
            strings.add("   §d生命+ "+jsonObject.get("hp").getAsInt());
        }if(jsonObject.get("mv")!=null){
            strings.add("   §d魔力+ "+jsonObject.get("mv").getAsInt());
        }if(jsonObject.get("mdef")!=null){
            strings.add("   §d法防+ "+jsonObject.get("mdef").getAsInt());
        }if(jsonObject.get("res")!=null){
            strings.add("   §d抵抗+ "+jsonObject.get("res").getAsInt());
        }if(jsonObject.get("crit")!=null){
            strings.add("   §d暴击+ "+jsonObject.get("crit").getAsInt());
        }if(jsonObject.get("ct")!=null){
            strings.add("   §d破甲+ "+jsonObject.get("ct").getAsInt());
        }if(jsonObject.get("speed")!=null){
            strings.add("   §d速度+ "+jsonObject.get("speed").getAsInt());
        }if(jsonObject.get("power")!=null){
            strings.add("   §d力量+ "+jsonObject.get("power").getAsInt());
        }if(jsonObject.get("intelligence")!=null){
            strings.add("   §d智力+ "+jsonObject.get("intelligence").getAsInt());
        }if(jsonObject.get("agile")!=null){
            strings.add("   §d敏捷+ "+jsonObject.get("agile").getAsInt());
        }if(jsonObject.get("spirit")!=null){
            strings.add("   §d精神+ "+jsonObject.get("spirit").getAsInt());
        }
    }

}
