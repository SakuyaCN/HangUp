package com.sakuya.hangup;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sakuya.hangup.entity.PlayerAttr;
import com.sakuya.hangup.entity.PlayerEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;


public class test {
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException, IOException {
//        long m = System.currentTimeMillis();
//        CyclicBarrier cyclicBarrier = new CyclicBarrier(4);
//        for (int i = 0;i<5;i++){
//            int finalI = i;
//            new Thread(new Runnable() {
//              @Override
//              public void run() {
//                  String json = null;
//                  try {
//                      json = new String(readAllBytes(get("E:\\HangUp\\src\\main\\java\\com\\sakuya\\hangup/aaa.json")));
//                  } catch (IOException e) {
//                      e.printStackTrace();
//                  }
//                  System.out.println(finalI +"length"+json.length());
//                  try {
//                      cyclicBarrier.await();
//                  } catch (InterruptedException e) {
//                      e.printStackTrace();
//                  } catch (BrokenBarrierException e) {
//                      e.printStackTrace();
//                  }
//              }
//          }).start();
//        }
//        cyclicBarrier.await();
//        System.out.println("结束"+(System.currentTimeMillis()-m));
//        PlayerAttr playerAttr = new PlayerAttr();
//        playerAttr.setAtk(100);
//        System.out.println(playerAttr.getAtk());
//        da(playerAttr);
//        System.out.println(playerAttr.getAtk());
//        String json = "{ \"name\": \"Baeldung\" \"java\": true }";
//        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
//        if(jsonObject.get("123")!=null){
//            System.out.println(jsonObject.get("123"));
//        }else{
//            System.out.println("bbb");
//        }

//        List<Object> list = new ArrayList<>();
//        list.add(new PlayerAttr());
//        list.add(null);
//        list.add(new PlayerAttr());
//        System.out.println(list.size());
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<5;i++){
                    final int[] a = {0};
                    Timer timer = new Timer();
                    int finalI = i;
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            System.out.println(finalI +"asdasdasd");
                            a[0]++;
                            if(a[0] == 10)
                                timer.cancel();
                        }
                    },0,1000L+(i*1000));
                }
            }
        }).run();
    }

    public static void da(PlayerAttr playerAttr){
        PlayerAttr playerAttr1 = playerAttr;
        playerAttr1.setAtk(0);
    }

    public static void da(int[] playerAttr){
        playerAttr[1] = 10;
    }
}
