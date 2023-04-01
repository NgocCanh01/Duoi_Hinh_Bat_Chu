package com.example.duoihinhbatchu;

public class Question {
    private int id;//mã câu hỏi
    private String content;//nội dung của câu hỏi

    public Question(int id, String content) {
        this.id = id;
        this.content = content;
    }
    //Phương thức khỏi tạo một câu hỏi mới

    public int getId() {
        return id;
    }//lấy mã câu hỏi

    public String getContext() {
        return content;
    }//Lấy nội dung, đáp án
}
