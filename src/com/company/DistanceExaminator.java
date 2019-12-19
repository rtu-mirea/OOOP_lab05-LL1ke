package com.company;
import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class DistanceExaminator implements Serializable {
    private Admin admin = new Admin();
    private ArrayList<Student> students = new ArrayList<Student>();
    private ArrayList<Question> questions = new ArrayList<Question>();
    private int curreantUser = -2;
    public int getCurreantUser(){
        return curreantUser;
    }
    public void addUser(String name, String login, String password, String repeatPassword) throws Exception{
        if (login.compareTo("") == 0 || password.compareTo("") == 0) throw new Exception("Fill in all the data!");
        if (password.compareTo(repeatPassword) != 0) throw new Exception("Passwords do not match!");
        if (login.compareTo(admin.getLogin()) == 0) throw new Exception("Bad news, login is already taken!");
        if (students.isEmpty()){
            Student buf = new Student(name, login, password);
            students.add(buf);
        }
        else {
            for (Student student : students) {
                if (student.getLogin().compareTo(login) == 0) throw new Exception("Bad news, login is already taken!");
            }
            Student buf = new Student(name, login, password);
            students.add(buf);
        }
    }
    public void changePassword(String login, String password, String newPassword, String repeatNewPassword) throws Exception {
        if (newPassword.compareTo(repeatNewPassword) != 0) throw new Exception("Passwords do not match!");
        if (admin.enter(login, password)) {
            admin.changePassword(newPassword);
            return;
        }
        if (students.isEmpty()) throw new Exception("There are no users in the system, except for the admin of course.");
        for (Student student: students){
            if (student.enter(login, password))
            {
                curreantUser = students.indexOf(student);
                break;
            }
            else curreantUser = -2;
        }
        if (curreantUser == -2) throw new Exception("The login or password you entered is incorrect. Try again.");
        students.get(curreantUser).changePassword(newPassword);
        curreantUser = -2;
    }
    public void logining(String login, String password)throws Exception{
        if (admin.enter(login, password)) {
            curreantUser = -1;
            return;
        }
        if (students.isEmpty()) throw new Exception("There are no users in the system, except for the admin of course.");
        for (Student student: students){
            if (student.enter(login, password))
            {
                curreantUser = students.indexOf(student);
                break;
            }
            else curreantUser = -2;
        }
        if (curreantUser == -2) throw new Exception("The login or password you entered is incorrect. Try again.");
    }
    public void exit(){
        curreantUser = -2;
    }
    public void addQuestion(String question, String answer) throws Exception{
        if (question.compareTo("") == 0 && answer.compareTo("") == 0)
            throw new Exception("Please, enter a question and answer.");
        if (curreantUser == -1) {
            questions.add(new Question(question, answer));
        }
        else throw new Exception("You cannot add questions.");
    }
    public void changeQuestion(int i, String question, String answer) throws Exception{
        if (question.compareTo("") == 0 || answer.compareTo("") == 0)
            throw new Exception("Please, enter a question and answer.");
        if (curreantUser == -1) {
            questions.set(i, new Question(question, answer));
        }
        else throw new Exception("You cannot add questions.");
    }
    public void deleteQuestion(int i){
        questions.remove(i);
    }
    public String getAllQuestions(){
        String res = "The list of questions\n";
        for (int i = 0; i < questions.size(); i++){
            res += "Question №" + i + " : " + questions.get(i).getQuestin() + "\nAnswer: " + questions.get(i).getAnswer() + '\n';
        }
        return res;
    }
    public void examination()throws Exception{
        int questionIndex, arr[] = {-1,-1,-1,-1,-1};
        boolean flag;
        Random random = new Random();
        String str;
        Scanner in = new Scanner(System.in);
        if (curreantUser == -1) throw new Exception("You cannot answer questions.");
        if (curreantUser < 0) throw new Exception("You are not log in.");
        if (questions.size() < 5) throw new Exception("The teacher has not yet added the right amount of questions.");
        if (students.get(curreantUser).getQuestionCount() != 0) throw new Exception("You already pass the exam.");
        JOptionPane.showMessageDialog(new JFrame(), "The start of the exam.");
        for (int i = 0; i < 5; i++){
            flag = true;
            questionIndex = random.nextInt(questions.size());
            for (int n = 0; n < i; n++){
                if (arr[n] == questionIndex){
                    flag = false;
                    break;
                }
            }
            if (flag) arr[i] = questionIndex;
            else i--;
        }
        for (int i = 1; i <= 5; i++){
            str = JOptionPane.showInputDialog("Question №" + i + " :\n" + questions.get(arr[i - 1]).getQuestin() + "\nAnswer: ");
            students.get(curreantUser).addQuestionCount();
            if (questions.get(arr[i - 1]).isCorrect(str))
                students.get(curreantUser).addRightAnswers();
        }
    }
    public int getMark()throws Exception{
        if (curreantUser < 0) throw new Exception("You are not logged in or do not have access to this menu.");
        if (students.get(curreantUser).getQuestionCount() == 0) throw new Exception("You have not pass the exam yet.");
        if (students.get(curreantUser).getRightAnswers() <= 2)
            return 2;
        else
            return students.get(curreantUser).getRightAnswers();
    }
}
