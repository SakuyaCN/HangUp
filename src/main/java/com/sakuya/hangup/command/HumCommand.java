package com.sakuya.hangup.command;

import com.sakuya.hangup.Main;
import com.sakuya.hangup.entity.EquEntity;
import com.sakuya.hangup.modules.EquModule;
import com.sakuya.hangup.modules.create.PlayerCreate;
import com.sakuya.hangup.modules.menu.MenuConfig;
import com.sakuya.hangup.utils.UserCmdUtils;
import com.sakuya.hangup.utils.Util;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HumCommand implements CommandExecutor {


    public UserCmdUtils ucu = UserCmdUtils.getInstance();
    public PlayerCreate playerCreate = new PlayerCreate();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player)commandSender;
        switch (strings[0]){
            case "create" :{
                playerCreate.create(player,ucu,strings);
            }break;
            case "upEqu":{
                EquEntity equEntity = EquModule.getInstance().getEqu(Integer.parseInt(strings[1]));
                EquModule.getInstance().putEqu(player.getUniqueId().toString(),equEntity);
            }break;
            case "downEqu":{
                EquEntity equEntity = EquModule.getInstance().getEqu(Integer.parseInt(strings[1]));
                EquModule.getInstance().downEqu(player.getUniqueId().toString(),equEntity);
            }break;
            case "drapEqu":{

            }break;
            case "showEqu":{
                EquEntity equEntity = EquModule.getInstance().getEqu(Integer.parseInt(strings[1]));
                Main.javaPlugin.getServer().spigot().broadcast(Util.getHc(player.getName()+"展示了他的装备：   §n"+Util.colorText(equEntity.getPz(),equEntity.getName()), MenuConfig.getEquInfo(equEntity)));
            }break;
        }
        return false;
    }
}

