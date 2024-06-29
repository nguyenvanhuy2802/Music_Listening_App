package com.example.music.Class;

public class Category {
    private int idTheLoai;
    private int idChuDe;
    private String tenTheLoai;
    private String hinhTheLoai;

    public Category(int idTheLoai, int idChuDe, String tenTheLoai, String hinhTheLoai) {
        this.idTheLoai = idTheLoai;
        this.idChuDe = idChuDe;
        this.tenTheLoai = tenTheLoai;
        this.hinhTheLoai = hinhTheLoai;
    }

    public int getIdTheLoai() {
        return idTheLoai;
    }

    public void setIdTheLoai(int idTheLoai) {
        this.idTheLoai = idTheLoai;
    }

    public int getIdChuDe() {
        return idChuDe;
    }

    public void setIdChuDe(int idChuDe) {
        this.idChuDe = idChuDe;
    }

    public String getTenTheLoai() {
        return tenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }

    public String getHinhTheLoai() {
        return hinhTheLoai;
    }

    public void setHinhTheLoai(String hinhTheLoai) {
        this.hinhTheLoai = hinhTheLoai;
    }
}
