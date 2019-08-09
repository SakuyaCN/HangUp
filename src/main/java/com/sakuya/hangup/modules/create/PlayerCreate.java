package com.sakuya.hangup.modules.create;
;
import com.google.gson.Gson;
import com.sakuya.hangup.entity.BagEntity;
import com.sakuya.hangup.entity.PlayerEntity;
import com.sakuya.hangup.modules.bag.BagModule;
import com.sakuya.hangup.modules.player.PlayerModule;
import com.sakuya.hangup.utils.FileUtil;
import com.sakuya.hangup.utils.UserCmdUtils;
import com.sakuya.hangup.utils.Util;
import org.bukkit.entity.Player;

import java.util.List;

import static com.sakuya.hangup.utils.Util.random;

public class PlayerCreate {

    public PlayerCreate() {
    }

    public void joinMsg(Player player){
        String uuid = player.getUniqueId().toString();
        String[] msg = {Util.getText("HangUp放置类玩法插件"),Util.getText("按下T键打开聊天框"),Util.getText("点击下划线文字操作")};
        player.sendMessage(msg);
        if(PlayerModule.getInstance().getPlayerJson(uuid)== null){
            player.sendMessage(Util.getText("未检测到您的角色，点击下方文字创建角色↓"));
            player.spigot().sendMessage(Util.getTc("§n[创建角色]","/humcmd create"));
        }else{
            player.sendMessage(Util.getText("输入 /hum menu 打开菜单"));
            PlayerModule.getInstance().playerJoin(uuid);
        }
    }

    public void create(Player player,UserCmdUtils ucu,String[] strings){
        String uuid = player.getUniqueId().toString();
        switch (strings[0]){
            case "create":{
                if (PlayerModule.getInstance().getPlayerJson(uuid) == null) {
                    if(ucu.userCmdMap.get(uuid) == null){
                        ucu.startEsEnd(player);
                    }
                    switch (ucu.userCmdMap.get(uuid)){
                        case 1 : {
                            player.sendMessage(Util.getText("请选择您的职业(请在120秒内完成)"));
                            player.spigot().sendMessage(Util.getTc("§c§n[射手]", "/humcmd create shooter"), Util.getTc("§c§n[法师]", "/humcmd create mage"), Util.getTc("§c§n[战士]", "/humcmd create warrior"));
                            ucu.userEntity.put(uuid,new PlayerEntity());
                            ucu.userCmdMap.put(uuid,2);
                        }break;
                        case 2:{
                            ucu.userEntity.get(uuid).setUuid(uuid);
                            ucu.userEntity.get(uuid).setLv(1);
                            ucu.userEntity.get(uuid).setExp(0);
                            ucu.userEntity.get(uuid).setuName(player.getName());
                            ucu.userEntity.get(uuid).setJob(strings[1]);
                            List<Integer> list = random(4,20);
                            ucu.userEntity.get(uuid).setAttr(new int[]{list.get(0),list.get(1),list.get(2),list.get(3)});
                            ucu.userEntity.get(uuid).setOtherAttr(new int[]{0,0,0,0});
                            player.sendMessage(Util.getText("已选择职业：§c【"+ Util.getJobName(strings[1]) +"】"));
                            player.sendMessage(Util.getText("初始天赋属性如下，是否更换？"));
                            player.spigot().sendMessage(Util.getTc("§c§n[保存]","/humcmd create save"),Util.getTc("§c§n[更换]","/humcmd create change"));
                            player.sendMessage(new String[]{Util.getText("§e力量（生命 | 攻击）： "+list.get(0)),
                                    Util.getText("§e智力（法力 | 破甲）： "+list.get(1)),
                                    Util.getText("§e敏捷（攻速 | 物防）： "+list.get(2)),
                                    Util.getText("§e精神（法防 | 抗性）： "+list.get(3))});
                            ucu.userCmdMap.put(uuid,3);
                            player.sendMessage("————————————————————————");
                        }break;
                        case 3:{
                            if(strings[1].equals("save")){
                                BagModule.getInstance().SaveBag(uuid,new BagEntity(uuid,16,null));
                                PlayerModule.getInstance().reLoadPlayerAttr(uuid,ucu.userEntity.get(uuid));
                                player.sendMessage(Util.getText("角色创建成功！"));
                                player.sendMessage(Util.getText("输入 /hum menu 打开功能菜单！"));
                                ucu.userCmdMap.put(uuid,4);
                            }else if(strings[1].equals("change")){
                                player.spigot().sendMessage(Util.getTc("§c§n[保存]","/humcmd create save"),Util.getTc("§c§n[更换]","/humcmd create change"));
                                List<Integer> list = random(4,20);
                                player.sendMessage(new String[]{Util.getText("§e力量（生命 | 攻击）： "+list.get(0)),
                                        Util.getText("§e智力（法力 | 破甲）： "+list.get(1)),
                                        Util.getText("§e敏捷（攻速 | 物防）： "+list.get(2)),
                                        Util.getText("§e精神（法防 | 抗性）： "+list.get(3))});
                                ucu.userEntity.get(uuid).setAttr(new int[]{list.get(0),list.get(1),list.get(2),list.get(3)});
                            }
                            player.sendMessage("————————————————————————");
                        }break;
                        case 4: {

                        }break;
                    }
                } else {
                    player.sendMessage(Util.getText("您已经创建过角色！"));
                }
            }
            break;
        }
    }
}
