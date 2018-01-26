package com.branch.www.screencapture.utils;

import com.branch.www.screencapture.search.BaiDuSearch;
import com.branch.www.screencapture.search.Search;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by cyq7on on 2018/1/26.
 */

public class Pattern {
    private static final String QUESTION_FLAG = "?";
    private String questionAndAnswers;

    public Pattern(String questionAndAnswers) {
        this.questionAndAnswers = questionAndAnswers;
    }

    public void run() throws UnsupportedEncodingException {
        long endTime;
        long startTime = System.currentTimeMillis();
        if (questionAndAnswers == null || !questionAndAnswers.contains(QUESTION_FLAG)) {
            System.out.println("问题识别失败，输入回车继续运行");
            return;
        }
        //获取问题和答案
        System.out.println("检测到题目");
        Information information = Utils.getInformation(questionAndAnswers);
        String question = information.getQuestion();
        String[] answers = information.getAns();
        if (question == null) {
            System.err.println("问题不存在，输入回车继续运行");
            return;
        } else if (answers.length < 1) {
            System.err.println("检测不到答案，输入回车继续运行");
            return;
        }
        System.out.println("问题:" + question);
        System.out.println("答案：");
        for (String answer : answers) {
            System.out.println(answer);
        }
        long countQuestion = 1;
        int numOfAnswer = answers.length > 3 ? 4 : answers.length;
        long[] countQA = new long[numOfAnswer];
        long[] countAnswer = new long[numOfAnswer];

        int maxIndex = 0;

        Search[] searchQA = new Search[numOfAnswer];
        Search[] searchAnswers = new Search[numOfAnswer];
        FutureTask[] futureQA = new FutureTask[numOfAnswer];
        FutureTask[] futureAnswers = new FutureTask[numOfAnswer];
        FutureTask futureQuestion = new FutureTask<Long>(new BaiDuSearch(question));
        new Thread(futureQuestion).start();
        for (int i = 0; i < numOfAnswer; i++) {
            searchQA[i] = new BaiDuSearch((question + " " + answers[i]));
            searchAnswers[i] = new BaiDuSearch(answers[i]);

            futureQA[i] = new FutureTask<Long>(searchQA[i]);
            futureAnswers[i] = new FutureTask<Long>(searchAnswers[i]);
            new Thread(futureQA[i]).start();
            new Thread(futureAnswers[i]).start();
        }
        try {
            while (!futureQuestion.isDone()) {
            }
            countQuestion = (Long) futureQuestion.get();
            for (int i = 0; i < numOfAnswer; i++) {
                while (true) {
                    if (futureAnswers[i].isDone() && futureQA[i].isDone()) {
                        break;
                    }
                }
                countQA[i] = (Long) futureQA[i].get();
                countAnswer[i] = (Long) futureAnswers[i].get();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        float[] ans = new float[numOfAnswer];
        for (int i = 0; i < numOfAnswer; i++) {
            ans[i] = (float) countQA[i] / (float) (countQuestion * countAnswer[i]);
            maxIndex = (ans[i] > ans[maxIndex]) ? i : maxIndex;
        }
        //根据pmi值进行打印搜索结果
        int[] rank = Utils.rank(ans);
        for (int i : rank) {
            System.out.print(answers[i]);
            System.out.print(" countQA:" + countQA[i]);
            System.out.print(" countAnswer:" + countAnswer[i]);
            System.out.println(" ans:" + ans[i]);
        }

        System.out.println("--------最终结果-------");
        System.out.println(answers[maxIndex]);
        endTime = System.currentTimeMillis();
        float excTime = (float) (endTime - startTime) / 1000;

        System.out.println("执行时间：" + excTime + "s");
    }
}
