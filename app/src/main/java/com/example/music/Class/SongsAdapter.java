package com.example.music.Class;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.music.R;

import java.util.ArrayList;
import java.util.List;

public class SongsAdapter extends ArrayAdapter<Song> {
    private Context context;
    private List<Song> songList;

    public SongsAdapter(@NonNull Context context, @NonNull List<Song> songList) {
        super(context, 0, songList);
        this.context = context;
        this.songList = songList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listSong = convertView;
        if (listSong == null) {
            listSong = LayoutInflater.from(context).inflate(R.layout.item_song, parent, false);
        }
        Song currentSong = songList.get(position);
        TextView txtTitle = listSong.findViewById(R.id.txtTitle);
        TextView txtSinger = listSong.findViewById(R.id.txtSinger);

        txtTitle.setText(currentSong.getTenBaiHat());
        txtSinger.setText(currentSong.getCaSi());

        return listSong;
    }
}
