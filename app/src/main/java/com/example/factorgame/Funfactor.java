package com.example.factorgame;

import android.app.Application;

public class Funfactor extends Application {
    public static boolean isGo=false,isAnswer=false,isCorrect=false;
    static int op1=0,op2=0,op3=0,number=0;
    // public static void setInitial(boolean Initial){
    //   isInitial=Initial;
    //}
    public static void setGo(boolean go){
        isGo=go;
    }
    public static void setAnswer(boolean answer){
        isAnswer=answer;
    }
    /* public static boolean getInitial(){
         return isInitial;
     }*/
    public static boolean getGo(){
        return isGo;
    }
    public static boolean getAnswer(){
        return isAnswer;
    }
    public static void setop1(int option){
        op1=option;
    }
    public static void setop2(int option){
        op2=option;
    }
    public static void setop3(int option){
        op3=option;
    }
    public static int getop1(){
        return op1;
    }
    public static int getop2(){
        return op2;
    }
    public static int getop3(){
        return op3;
    }


    public static void setisCorrect(boolean b) {
        isCorrect=b;
    }
    public static boolean getisCorrect(){
        return isCorrect;
    }

    public static void setNumber(int no) {
        number = no;
    }
    public static int getNumber(){
        return number;
    }
}
