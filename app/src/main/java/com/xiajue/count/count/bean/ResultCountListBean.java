package com.xiajue.count.count.bean;

/**
 * Created by Moing_Admin on 2017/12/25.
 */

public class ResultCountListBean {
    private String subject;
    private String answer;
    private int answerColor;

    public ResultCountListBean() {
    }

    public ResultCountListBean(String subject, String answer, int answerColor) {
        this.subject = subject;
        this.answer = answer;
        this.answerColor = answerColor;
    }

    public String getSubject() {
        return subject;
    }

    public String getAnswer() {
        return answer;
    }

    public int getAnswerColor() {
        return answerColor;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setAnswerColor(int answerColor) {
        this.answerColor = answerColor;
    }
}
