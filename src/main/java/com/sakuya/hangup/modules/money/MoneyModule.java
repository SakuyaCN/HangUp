package com.sakuya.hangup.modules.money;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MoneyModule {

    private static volatile MoneyModule sInst = null;
    private YamlConfiguration ymlCg = null;

    public static MoneyModule getInstance() {
        MoneyModule inst = sInst;
        if (inst == null) {
            synchronized (MoneyModule.class) {
                inst = sInst;
                if (inst == null) {
                    inst = new MoneyModule();
                    sInst = inst;
                }
            }
        }
        return inst;
    }

    public MoneyModule() {
        File file = new File("./plugins/HangUp/UserConfig/MoneyConfig.yml");
        ymlCg = YamlConfiguration.loadConfiguration(file);
    }

    public void fristLoad(){
        File file = new File("./plugins/HangUp/UserConfig/MoneyConfig.yml");
        ymlCg = YamlConfiguration.loadConfiguration(file);
        ymlCg.set("moneyType", new String[]{"Gold","Hgb"});
        ymlCg.set("moneyStart", 1000);
        ymlCg.set("isReplacement", true);
        ymlCg.set("Proportion", 100);
        try {
            ymlCg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
