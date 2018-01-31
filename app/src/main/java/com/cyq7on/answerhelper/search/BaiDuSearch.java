package com.cyq7on.answerhelper.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by 618 on 2018/1/12.
 *
 * @author lingfengsan
 */
public class BaiDuSearch implements Search {
    private String question;
    private String path;

    public BaiDuSearch(String question) throws UnsupportedEncodingException {
        this.question = question;
        this.path = "http://www.baidu.com/s?tn=ichuner&lm=-1&word=" +
                URLEncoder.encode(question, "gb2312") + "&rn=1";
    }
    @Override
    public Long search() throws IOException {
        boolean findIt = false;
        String line = null;
        while (!findIt) {
            URL url = new URL(path);
            BufferedReader breaded = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
            while ((line = breaded.readLine()) != null) {
                if (line.contains("百度为您找到相关结果约")) {
                    findIt = true;
                    int start = line.indexOf("百度为您找到相关结果约") + 11;

                    line = line.substring(start);
                    int end = line.indexOf("个");
                    line = line.substring(0, end);
                    break;
                }

            }
        }
        line = line.replace(",", "");
        return Long.valueOf(line);
    }

    @Override
    public Long call() throws IOException {
        return search();
    }


}
