package com.company;
import java.io.Serializable;

public class Question  implements Serializable {
    private String questin = "";
    private String questionAnswers = "";
    public Question(String str, String answer){
        questin = str;
        questionAnswers = answer;
    }
    public boolean isCorrect(String answer){
        return questionAnswers.compareTo(answer) == 0;
    }
    public String getQuestin() {
        return questin;
    }
    public String getAnswer(){return questionAnswers;}
}
