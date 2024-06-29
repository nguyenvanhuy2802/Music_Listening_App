package com.example.music.Class;

public class Topic {
  private int idChuDe;
  private String tenChuDe;
  private String hinhChuDe;

    public Topic(int idChuDe, String tenChuDe, String hinhChuDe) {
        this.idChuDe = idChuDe;
        this.tenChuDe = tenChuDe;
        this.hinhChuDe = hinhChuDe;
    }

    public int getIdChuDe() {
        return idChuDe;
    }

    public void setIdChuDe(int idChuDe) {
        this.idChuDe = idChuDe;
    }

    public String getTenChuDe() {
        return tenChuDe;
    }

    public void setTenChuDe(String tenChuDe) {
        this.tenChuDe = tenChuDe;
    }

    public String getHinhChuDe() {
        return hinhChuDe;
    }

    public void setHinhChuDe(String hinhChuDe) {
        this.hinhChuDe = hinhChuDe;
    }

}
