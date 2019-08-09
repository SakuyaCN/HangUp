package com.sakuya.hangup;

import com.google.gson.Gson;
import com.sakuya.hangup.entity.PlayerEntity;
import com.sakuya.hangup.utils.FileUtil;



public class test {
    public static void main(String[] args) {
        for (int i = 0;i<100;i++){
            int index = (int) (Math.random()* 6);
            System.out.println(index);
        }
    }
}
