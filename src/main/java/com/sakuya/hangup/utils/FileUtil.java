package com.sakuya.hangup.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {


    public static void readFileByChars(String uuid) {
        File file = new File("./plugins/HangUp/PlayerData/"+uuid+".json");
        Reader reader = null;
        try {
            // 一次读一个字符
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            while ((tempchar = reader.read()) != -1) {
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
                if (((char) tempchar) != '\r') {
                    System.out.print((char) tempchar);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            // 一次读多个字符
            char[] tempchars = new char[30];
            int charread = 0;
            reader = new InputStreamReader(new FileInputStream("./plugins/HangUp/PlayerData/"+uuid+".json"));
            // 读入多个字符到字符数组中，charread为一次读取字符数
            while ((charread = reader.read(tempchars)) != -1) {
                // 同样屏蔽掉\r不显示
                if ((charread == tempchars.length)
                        && (tempchars[tempchars.length - 1] != '\r')) {
                    System.out.print(tempchars);
                } else {
                    for (int i = 0; i < charread; i++) {
                        if (tempchars[i] == '\r') {
                            continue;
                        } else {
                            System.out.print(tempchars[i]);
                        }
                    }
                }
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public static String readFile(String uuid){
        BufferedReader reader = null;
        String laststr = "";
        try{
            FileInputStream fileInputStream = new FileInputStream("./plugins/HangUp/PlayerData/"+uuid+".json");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while((tempString = reader.readLine()) != null){
                laststr += tempString;
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }

    public static void writeFile(String path,String uuid, String sets) {
        Path fpath=Paths.get("./plugins/HangUp/"+path+"/"+uuid+".json");
        try {
            if(!Files.exists(fpath)) {
                try {
                    Files.createFile(fpath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Files.write(fpath, sets.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeSkill(String sets) {
        new Thread(() -> {
            FileWriter fw = null;
            PrintWriter out = null;
            try {
                fw = new FileWriter("./plugins/HangUp/UserConfig/SkillConfig.json");
                out = new PrintWriter(fw);
                out.write(sets);
                out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                out.close();
            }
        }).start();
    }
}
