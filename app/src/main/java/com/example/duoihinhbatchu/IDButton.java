package com.example.duoihinhbatchu;

public class IDButton {
    private int idAnwser, idPick;

    public IDButton(int idPick, int idAnwser) {
        this.idAnwser = idAnwser;//id trả lời
        this.idPick = idPick;//id của nút chon
    }
    //Phương thức khỏi tạo cho một nút mới

    public int getIdPick() {
        return idPick;
    }//lấy id của nút chọn

    public int getIdAnwser() {
        return idAnwser;
    }//lấy id của nút trả lời
}
