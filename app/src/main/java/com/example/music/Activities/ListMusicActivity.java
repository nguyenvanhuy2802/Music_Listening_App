package com.example.music.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.music.Class.MusicDatabaseHelper;
import com.example.music.Class.Song;
import com.example.music.Class.SongsAdapter;
import com.example.music.R;

import java.util.ArrayList;
import java.util.List;

public class ListMusicActivity extends AppCompatActivity {
    private ListView lvSongs;
    private SongsAdapter songsAdapter;
    private List<Song> songList;
    private MusicDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_music);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.d("MusicDatabaseHelper", "lisview called");


        lvSongs = findViewById(R.id.lvSongs);
        dbHelper = new MusicDatabaseHelper(this);
//        dbHelper.deleteAllData();
        songList = dbHelper.getAllSongs();


        // Bỏ danh sách bài bát vào adapter
        songsAdapter = new SongsAdapter(this, songList);
        // Găn adapter vào listview
        lvSongs.setAdapter(songsAdapter);
        // Xử lí sự kiện khi chọn 1 bai hát từ danh sách
        lvSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song selectedSong = songList.get(position);
                Intent intent = new Intent(ListMusicActivity.this, MainActivity.class);
                // Đưa bài hát được chọn vào intent
                intent.putExtra("selectedSong", selectedSong);
                // Đưa danh sách bài hát được chọn vào intent
                intent.putExtra("songList", (ArrayList<Song>) songList);
                startActivity(intent);
            }
        });
    }

}
