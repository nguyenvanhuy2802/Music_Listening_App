package com.example.music.Class;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MusicDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "music.db";
    private static final int DATABASE_VERSION = 1;

    public MusicDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getReadableDatabase();
        onUpgrade(db, db.getVersion(),DATABASE_VERSION );
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo các bảng cho cho cơ sở dữ liệu nhạc
        Log.d("MusicDatabaseHelper", "onCreate called");

        db.execSQL("CREATE TABLE baihat (" +
                "idBaiHat INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idAlbum INTEGER, " +
                "idTheLoai INTEGER, " +
                "idPlaylist INTEGER, " +
                "tenBaiHat TEXT, " +
                "hinhBaiHat TEXT, " +
                "caSi TEXT, " +
                "pathBaiHat TEXT, " +
                "luotThich INTEGER, " +
                "FOREIGN KEY (idAlbum) REFERENCES album(idAlbum), " +
                "FOREIGN KEY (idTheLoai) REFERENCES theloai(idTheLoai), " +
                "FOREIGN KEY (idPlaylist) REFERENCES playlist(idPlaylist))");

        // Tạo bảng 'chude'
        db.execSQL("CREATE TABLE chude (" +
                "idChuDe INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenChuDe TEXT, " +
                "hinhChuDe TEXT)");

        // Tạo bảng 'theloai'
        db.execSQL("CREATE TABLE theloai (" +
                "idTheLoai INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idChuDe INTEGER, " +
                "tenTheLoai TEXT, " +
                "hinhTheLoai TEXT, " +
                "FOREIGN KEY (idChuDe) REFERENCES chude(idChuDe))");

        // Tạo bảng 'playlist'
        db.execSQL("CREATE TABLE playlist (" +
                "idPlaylist INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenPlaylist TEXT, " +
                "hinhnen TEXT, " +
                "hinhIcon TEXT)");

        // Tạo bảng 'quangcao'
        db.execSQL("CREATE TABLE quangcao (" +
                "idQC INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "hinhQC TEXT, " +
                "noidungQC TEXT, " +
                "idBaiHat INTEGER, " +
                "FOREIGN KEY (idBaiHat) REFERENCES baihat(idBaiHat))");

        // Tạo bảng 'album'
        db.execSQL("CREATE TABLE album (" +
                "idAlbum INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenAlbum TEXT, " +
                "tenCaSiAlbum TEXT, " +
                "hinhAlbum TEXT)");

        insertMusicData();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("MusicDatabaseHelper", "onUpgrade called");

        // Xóa bảng cũ nếu tồn tại
        db.execSQL("DROP TABLE IF EXISTS baihat");
        db.execSQL("DROP TABLE IF EXISTS chude");
        db.execSQL("DROP TABLE IF EXISTS theloai");
        db.execSQL("DROP TABLE IF EXISTS playlist");
        db.execSQL("DROP TABLE IF EXISTS quangcao");
        db.execSQL("DROP TABLE IF EXISTS album");

        // Tạo lại các bảng
        onCreate(db);
    }

    public void insertMusicData() {
        insertTopic();
        insertCategory();
        insertAlbum();
        insertPlaylist();
        insertSongs();
        insertAdvertisement();

    }

    private void insertSongs() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO baihat ( idAlbum, idTheLoai, idPlaylist, tenBaiHat , hinhBaiHat ,caSi , pathBaiHat , luotThich) VALUES (1, 5, 1, 'Đừng làm trái tim anh đau', 'img/baihat/dunglamtraitimanhdau.jpg', 'Sơn Tùng-MTP', 'mp3/dunglamtraitimanhdau.mp3', 1000)");
        db.execSQL("INSERT INTO baihat ( idAlbum, idTheLoai, idPlaylist, tenBaiHat , hinhBaiHat ,caSi , pathBaiHat , luotThich) VALUES (9, 1, 1,'Nhân duyên tiền định', 'img/baihat/nhanduyentiendinh.jpg', 'DJ AM, SS Remix', 'mp3/nhanduyentiendinh.mp3', 1000)");
        db.execSQL("INSERT INTO baihat ( idAlbum, idTheLoai, idPlaylist, tenBaiHat , hinhBaiHat ,caSi , pathBaiHat , luotThich) VALUES (2, 4, 1,'Cẩm tú cầu', 'img/baihat/camtucau.jpg', 'Rayo Huỳnh Văn', 'mp3/camtucau.mp3', 1000)");
        db.execSQL("INSERT INTO baihat ( idAlbum, idTheLoai, idPlaylist, tenBaiHat , hinhBaiHat ,caSi , pathBaiHat , luotThich) VALUES (3, 4, 2,'Anh thôi nhân nhượng', 'img/baihat/anhthoinhannhuong.jpg', 'Kiều Chi', 'mp3/anhthoinhannhuong.mp3', 1000)");
        db.execSQL("INSERT INTO baihat ( idAlbum, idTheLoai, idPlaylist, tenBaiHat , hinhBaiHat ,caSi , pathBaiHat , luotThich) VALUES (4, 4, 2,'Ngày em đẹp nhất', 'img/baihat/ngayemdepnhat.jpg', 'Tama', 'mp3/ngayemdepnhat.mp3', 1000)");
        db.execSQL("INSERT INTO baihat ( idAlbum, idTheLoai, idPlaylist, tenBaiHat , hinhBaiHat ,caSi , pathBaiHat , luotThich) VALUES (5, 2, 1,'Thủy triều', 'img/baihat/thuytrieu.jpg', 'Q.Hùng MasterD', 'mp3/thuytrieu.mp3', 1000)");
        db.execSQL("INSERT INTO baihat ( idAlbum, idTheLoai, idPlaylist, tenBaiHat , hinhBaiHat ,caSi , pathBaiHat , luotThich) VALUES (6, 2, 2,'Lệ lưu ly', 'img/baihat/leluuly.jpg', 'Vũ Phụng Tiên', 'mp3/leluuly.mp3', 1000)");
        db.execSQL("INSERT INTO baihat ( idAlbum, idTheLoai, idPlaylist, tenBaiHat , hinhBaiHat ,caSi , pathBaiHat , luotThich) VALUES (7, 2, 2,'Từng quen', 'img/baihat/tungquen.jpg', 'Wren Evans, ltsnk', 'mp3/tungquen.mp3', 1000)");
        db.execSQL("INSERT INTO baihat ( idAlbum, idTheLoai, idPlaylist, tenBaiHat , hinhBaiHat ,caSi , pathBaiHat , luotThich) VALUES (8, 2, 2,'Lưu luyến sau chia tay', 'img/baihat/luuluyensauchiatay.jpg', 'Try92, Kai06', 'mp3/luuluyensauchiatay.mp3', 1000)");
        db.execSQL("INSERT INTO baihat ( idAlbum, idTheLoai, idPlaylist, tenBaiHat , hinhBaiHat ,caSi , pathBaiHat , luotThich) VALUES (10, 3, 1,'Hey Daddy', 'img/baihat/heydaddy.jpg', 'Usher', 'mp3/heydaddy.mp3', 1000)");
        db.close();
    }

    private void insertCategory() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO theloai (idChuDe, tenTheLoai, hinhTheLoai) VALUES (1,'EDM', 'image_theloai_a.png')");
        db.execSQL("INSERT INTO theloai (idChuDe , tenTheLoai, hinhTheLoai) VALUES (3,'Pop', 'image_theloai_b.png')");
        db.execSQL("INSERT INTO theloai (idChuDe , tenTheLoai, hinhTheLoai) VALUES (1,'KPop', 'image_theloai_b.png')");
        db.execSQL("INSERT INTO theloai (idChuDe , tenTheLoai, hinhTheLoai) VALUES (1,'Acoustic', 'image_theloai_b.png')");
        db.execSQL("INSERT INTO theloai (idChuDe , tenTheLoai, hinhTheLoai) VALUES (2,'Ballad', 'image_theloai_b.png')");
        db.close();
    }

    private void insertTopic() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO chude (tenChuDe, hinhChuDe) VALUES ('Nhạc chill', 'image_chude_a.png')");
        db.execSQL("INSERT INTO chude (tenChuDe, hinhChuDe) VALUES ('Nhạc tâm trạng', 'image_chude_b.png')");
        db.execSQL("INSERT INTO chude (tenChuDe, hinhChuDe) VALUES ('Nhạc ÂU Mỹ', 'image_chude_a.png')");
        db.close();
    }

    private void insertAlbum() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO album (tenAlbum, tenCaSiAlbum, hinhAlbum) VALUES ('Sky Tour', 'Sơn Tùng MTP', 'image_a.png')");
        db.execSQL("INSERT INTO album (tenAlbum, tenCaSiAlbum, hinhAlbum) VALUES ('Cẩm tú cầu (lofi)', 'Rayo, Huỳnh Văn', 'image_b.png')");
        db.execSQL("INSERT INTO album (tenAlbum, tenCaSiAlbum, hinhAlbum) VALUES ('Anh thôi nhân nhường (Cover)', 'Kiều Chi', 'image_a.png')");
        db.execSQL("INSERT INTO album (tenAlbum, tenCaSiAlbum, hinhAlbum) VALUES ('Ngày em đẹp nhất (Single)', 'Tama', 'image_b.png')");
        db.execSQL("INSERT INTO album (tenAlbum, tenCaSiAlbum, hinhAlbum) VALUES ('Thủy Triều', 'Q.Hung MasterD', 'image_a.png')");
        db.execSQL("INSERT INTO album (tenAlbum, tenCaSiAlbum, hinhAlbum) VALUES ('Lệ lưu ly', 'Vũ Phụng Tiên, DT Tập Rap', 'image_b.png')");
        db.execSQL("INSERT INTO album (tenAlbum, tenCaSiAlbum, hinhAlbum) VALUES ('Từng quen', 'Wren Evans, ltsnk', 'image_a.png')");
        db.execSQL("INSERT INTO album (tenAlbum, tenCaSiAlbum, hinhAlbum) VALUES ('Lưu luyến sau chia tay', 'Try92, Kai06', 'image_b.png')");
        db.execSQL("INSERT INTO album (tenAlbum, tenCaSiAlbum, hinhAlbum) VALUES ('Gói gọn hồi ức trao anh', 'DJ AM, SS Remix', 'image_a.png')");
        db.execSQL("INSERT INTO album (tenAlbum, tenCaSiAlbum, hinhAlbum) VALUES ('Dadys Home', 'Usher', 'image_b.png')");
        db.close();

    }

    private void insertAdvertisement() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO quangcao (hinhQC, noidungQC, idBaiHat) VALUES ('image_quangcao_1.png', 'Nhạc hit mới', 1)");
        db.execSQL("INSERT INTO quangcao (hinhQC, noidungQC, idBaiHat) VALUES ('image_quangcao_2.png', 'Nhạc Tiktok ', 2)");
        db.close();
    }

    private void insertPlaylist() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO playlist (tenPlaylist, hinhnen, hinhIcon) VALUES ('Playlist được nghe nhiều nhất', 'image_nen_a.png', 'icon_a.png')");
        db.execSQL("INSERT INTO playlist (tenPlaylist, hinhnen, hinhIcon) VALUES ('Playlist này chill phết', 'image_nen_b.png', 'icon_b.png')");
        db.close();
    }

    // Phương thức lấy danh sách các bài hát
    public List<Song> getAllSongs() {
        List<Song> songList = new ArrayList<>();
        String selectQuery = "SELECT * FROM baihat";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Song song = new Song(0, 0, 0, 0, null, null, null, null, 0);
                song.setIdBaiHat(cursor.getInt(0));
                song.setIdAlbum(cursor.getInt(1));
                song.setIdTheLoai(cursor.getInt(2));
                song.setIdPlaylist(cursor.getInt(3));
                song.setTenBaiHat(cursor.getString(4));
                song.setHinhBaiHat(cursor.getString(5));
                song.setCaSi(cursor.getString(6));
                song.setPathBaiHat(cursor.getString(7));
                song.setLuotThich(cursor.getInt(8));

                // Thêm bài hát vào danh sách
                songList.add(song);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return songList;
    }

    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM baihat");
        db.execSQL("DELETE FROM chude");
        db.execSQL("DELETE FROM theloai");
        db.execSQL("DELETE FROM playlist");
        db.execSQL("DELETE FROM quangcao");
        db.execSQL("DELETE FROM album");
        db.close();
    }
    public Song getSongById(int idBaiHat) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM baihat WHERE idBaiHat = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(idBaiHat)});
        Song song = null;

        if (cursor.moveToFirst()) {
            song = new Song(0, 0, 0, 0, null, null, null, null , 0);
            song.setIdBaiHat(cursor.getInt(0));
            song.setIdAlbum(cursor.getInt(1));
            song.setIdTheLoai(cursor.getInt(2));
            song.setIdPlaylist(cursor.getInt(3));
            song.setTenBaiHat(cursor.getString(4));
            song.setHinhBaiHat(cursor.getString(5));
            song.setCaSi(cursor.getString(6));
            song.setPathBaiHat(cursor.getString(7));
            song.setLuotThich(cursor.getInt(8));
        }

        cursor.close();
        db.close();
        return song;
    }


}
