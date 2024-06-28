package com.example.music.Class;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.music.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SearchSongsAdapter extends ArrayAdapter<Song> {
    private Context context;
    private List<Song> songList;

    public SearchSongsAdapter(@NonNull Context context, @NonNull List<Song> songList) {
        super(context, 0, songList);
        this.context = context;
        this.songList = songList;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listSong = convertView;
        if (listSong == null) {
            listSong = LayoutInflater.from(context).inflate(R.layout.item_search_song, parent, false);
        }
        if(songList != null) {
            Song currentSong = songList.get(position);
            TextView txtSearchNameSong = listSong.findViewById(R.id.txtSearchNameSong);
            TextView txtSearchSingerSong = listSong.findViewById(R.id.txtSearchSingerSong);
            ImageView imgSearchSong = listSong.findViewById(R.id.imgSearchSong);
            // Tải ảnh về local
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRefPathImage = storage.getReference().child(currentSong.getHinhBaiHat());
            try {
                File localFileImage = File.createTempFile("image", "jpg");
                storageRefPathImage.getFile(localFileImage)
                        .addOnSuccessListener(taskSnapshot -> {
                            // Load ảnh từ local file vào ImageView
                            Bitmap bitmap = BitmapFactory.decodeFile(localFileImage.getAbsolutePath());
                            imgSearchSong.setImageBitmap(bitmap);
                        })
                        .addOnFailureListener(e -> {
                            e.printStackTrace();
                            // Xử lý lỗi khi tải ảnh không thành công
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }

            txtSearchNameSong.setText(currentSong.getTenBaiHat());
            txtSearchSingerSong.setText(currentSong.getCaSi());
        }
        return listSong;
    }

    public void updateList(List<Song> newList) {
        songList.clear();
        songList.addAll(newList);
        notifyDataSetChanged();
    }
}
