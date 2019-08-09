package com.sakuya.hangup.utils;

import com.sakuya.hangup.entity.PlayerEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UserCmdUtils {

    private static volatile UserCmdUtils sInst = null;
    public HashMap<String,Integer> userCmdMap;
    public HashMap<String, PlayerEntity> userEntity;
    public static UserCmdUtils getInstance() {
        UserCmdUtils inst = sInst;
        if (inst == null) {
            synchronized (UserCmdUtils.class) {
                inst = sInst;
                if (inst == null) {
                    inst = new UserCmdUtils();
                    sInst = inst;
                }
            }
        }
        return inst;
    }

    public UserCmdUtils() {
        userCmdMap = new HashMap<>();
        userEntity = new HashMap<>();
    }

    public void startEsEnd(Player uuid){
        if(userCmdMap.get(uuid.getUniqueId().toString())== null) {
            userCmdMap.put(uuid.getUniqueId().toString(), 1);
            ScheduledExecutorService scheduledExecutorService =
                    Executors.newScheduledThreadPool(1);
            scheduledExecutorService.schedule(new Runnable() {
                @Override
                public void run() {
                    userCmdMap.remove(uuid.getUniqueId().toString());
                    userEntity.remove(uuid.getUniqueId().toString());
                    if (userCmdMap.get(uuid.getUniqueId().toString()) != 5) {
                        uuid.sendMessage(Util.getText("角色创建已过期，请重新创建"));
                    }
                }
            }, 120, TimeUnit.SECONDS);
        }
    }
}
