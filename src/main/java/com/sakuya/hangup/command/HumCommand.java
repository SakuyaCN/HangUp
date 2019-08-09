package com.sakuya.hangup.command;

import com.sakuya.hangup.modules.create.PlayerCreate;
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
        switch (strings[0]){
            case "create" :{
                Player player = (Player) commandSender;
                playerCreate.create(player,ucu,strings);
            }break;
        }
        return false;
    }
}

