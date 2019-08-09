package com.sakuya.hangup.command;

import com.sakuya.hangup.Main;
import com.sakuya.hangup.entity.GoodsEntity;
import com.sakuya.hangup.modules.bag.BagModule;
import com.sakuya.hangup.modules.create.PlayerCreate;
import com.sakuya.hangup.modules.goods.GoodsModule;
import com.sakuya.hangup.modules.menu.IconMenu;
import com.sakuya.hangup.modules.menu.MenuConfig;
import com.sakuya.hangup.modules.player.PlayerModule;
import com.sakuya.hangup.modules.skill.SkillModule;
import com.sakuya.hangup.utils.BookUtils;
import com.sakuya.hangup.utils.Util;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class PlayerCommand implements CommandExecutor {
    private IconMenu menu;
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        menu = new IconMenu("HangUp主菜单", 9, event -> {
            switch (event.getName()){
                case "个人属性" :event.setWillClose(true); break;
                case "技能" :{
                    SkillModule.getInstance().SkillClick(player,event);
                }break;
                case "背包":{
                    BagModule.getInstance().openBagMenu(player);
                    // BagModule.getInstance().addGoods(player.getUniqueId().toString(), GoodsModule.getInstance().getGoodsformJson().get(0),10);
                }break;
                default:
                    event.setWillClose(true);
            }
        }, Main.javaPlugin);
        if(strings.length==0){
            PlayerCreate playerCreate = new PlayerCreate();
            playerCreate.joinMsg(player);
        }else{
            if(PlayerModule.getInstance().getPlayerJson(player.getUniqueId().toString())== null){
                PlayerCreate playerCreate = new PlayerCreate();
                playerCreate.joinMsg(player);
                return false;
            }
            switch (strings[0]) {
                case "menu" : {
                    menu.setOption(0, new ItemStack(Material.DIAMOND_SWORD, 1), "个人属性",
                                    MenuConfig.getPlayerEntity(player.getUniqueId().toString()));
                    menu.setOption(1, new ItemStack(Material.WRITTEN_BOOK, 1), "技能",
                            MenuConfig.getPlayerSkill(player.getUniqueId().toString()));
                    menu.setOption(2, new ItemStack(Material.CHEST, 1), "背包",
                            MenuConfig.getBagList(player.getUniqueId().toString()));
                    menu.open(player);
                }break;
                case "book" :{

                }
            }
        }
        return false;
    }
}
