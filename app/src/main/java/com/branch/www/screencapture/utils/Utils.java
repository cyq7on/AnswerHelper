package com.branch.www.screencapture.utils;

import java.util.Arrays;

/**
 * Created by lingfengsan on 2018/1/12.
 *
 * @author lingfengsan
 */
/**
 * @Description: fit android
 * @author: cyq7on
 * @date: 2018/1/26 11:15
 * @version: V2.0
 */
public class Utils {

    public static Information getInformation(String str) {
        //先去除空行
        str = str.replaceAll("((\r\n)|\n)[\\s\t ]*(\\1)+", "$1").
                replaceAll("^((\r\n)|\n)", "");
        str=str.replace('.',' ').replace(" ","");
        //问号统一替换为英文问号防止报错
        str=str.replace("？","?");
        int begin=(str.charAt(1)>='0'&& str.charAt(1)<='9')?2:1;
        String question = str.trim().substring(begin, str.indexOf('?') + 1);
        question = question.replaceAll("((\r\n)|\n)", "");
        System.out.println(question);
        String remain = str.substring(str.indexOf("?") + 1);
        String[] ans = remain.trim().split("\n");
        return new Information(question,ans);
    }



    /**
     * 对rank值进行排序
     *
     * @param floats pmi值
     * @return 返回排序的rank
     */
    public static int[] rank(float[] floats) {
        int[] rank = new int[floats.length];
        float[] f = Arrays.copyOf(floats, floats.length);
        Arrays.sort(f);
        for (int i = 0; i < floats.length; i++) {
            for (int j = 0; j < floats.length; j++) {
                if (f[i] == floats[j]) {
                    rank[i] = j;
                }
            }
        }
        return rank;
    }

}
