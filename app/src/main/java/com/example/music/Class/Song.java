package com.example.music.Class;

import java.io.Serializable;

public class Song implements Serializable {
    private int idBaiHat;
    private int idAlbum;
    private int idTheLoai;
    private int idPlaylist;
    private String tenBaiHat;
    private String hinhBaiHat;
    private String caSi;
    private String pathBaiHat;
    private int luotThich;

    public Song(int idBaiHat, int idAlbum, int idTheLoai, int idPlaylist, String tenBaiHat, String hinhBaiHat, String caSi, String pathBaiHat, int luotThich) {
        this.idBaiHat = idBaiHat;
        this.idAlbum = idAlbum;
        this.idTheLoai = idTheLoai;
        this.idPlaylist = idPlaylist;
        this.tenBaiHat = tenBaiHat;
        this.hinhBaiHat = hinhBaiHat;
        this.caSi = caSi;
        this.pathBaiHat = pathBaiHat;
        this.luotThich = luotThich;
    }

    public int getIdBaiHat() {
        return idBaiHat;
    }

    public void setIdBaiHat(int idBaiHat) {
        this.idBaiHat = idBaiHat;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    public int getIdTheLoai() {
        return idTheLoai;
    }

    public void setIdTheLoai(int idTheLoai) {
        this.idTheLoai = idTheLoai;
    }

    public int getIdPlaylist() {
        return idPlaylist;
    }

    public void setIdPlaylist(int idPlaylist) {
        this.idPlaylist = idPlaylist;
    }

    public String getTenBaiHat() {
        return tenBaiHat;
    }

    public void setTenBaiHat(String tenBaiHat) {
        this.tenBaiHat = tenBaiHat;
    }

    public String getHinhBaiHat() {
        return hinhBaiHat;
    }

    public void setHinhBaiHat(String hinhBaiHat) {
        this.hinhBaiHat = hinhBaiHat;
    }

    public String getCaSi() {
        return caSi;
    }

    public void setCaSi(String caSi) {
        this.caSi = caSi;
    }

    public String getPathBaiHat() {
        return pathBaiHat;
    }

    public void setPathBaiHat(String pathBaiHat) {
        this.pathBaiHat = pathBaiHat;
    }

    public int getLuotThich() {
        return luotThich;
    }

    public void setLuotThich(int luotThich) {
        this.luotThich = luotThich;
    }
}
