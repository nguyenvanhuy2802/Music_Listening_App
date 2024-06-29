package com.example.music.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.music.Class.MusicDatabaseHelper;
import com.example.music.Class.SearchSongsAdapter;
import com.example.music.Class.Song;
import com.example.music.R;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SearchActivity extends AppCompatActivity {
    private MusicDatabaseHelper dbHelper;
    private SearchSongsAdapter searchSongsAdapter;
    private ListView lvSearchSong;
    private List<Song> songSearchList;
    private EditText textSearch;
    private ImageView backButtonHome;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lvSearchSong = findViewById(R.id.lvSearchSong);
        textSearch = findViewById(R.id.textSearch);
        textSearch = findViewById(R.id.textSearch);
        backButtonHome = findViewById(R.id.backButtonHome);
        dbHelper = new MusicDatabaseHelper(this);
        songSearchList = dbHelper.getAllSongs();
        searchSongsAdapter = new SearchSongsAdapter(this, new ArrayList<>());
        lvSearchSong.setAdapter(searchSongsAdapter);
        lvSearchSong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song selectedSong = searchSongsAdapter.getItem(position);
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                intent.putExtra("selectedSong", selectedSong);
                intent.putExtra("songList", (ArrayList<Song>) songSearchList);
                startActivity(intent);
            }
        });

        // Xử lí sự kiện khi nhập vào tìm kiếm
        textSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                performSearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        backButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void performSearch(String query) {
        List<Song> filteredList = new ArrayList<>();
        String normalizedQuery = normalizeString(query);

        if (normalizedQuery.isEmpty()) {
            // Nếu từ khóa tìm kiếm rỗng, hiển thị lại danh sách ban đầu (rỗng)
            filteredList.clear(); // Xóa bất kỳ dữ liệu nào trong filteredList
        } else {
            // Nếu có từ khóa tìm kiếm, lọc danh sách theo từ khóa
            for (Song song : songSearchList) {
                String normalizedTenBaiHat = normalizeString(song.getTenBaiHat());
                String normalizedCaSi = normalizeString(song.getCaSi());

                // Kiểm tra nếu từ khóa tìm kiếm tồn tại trong tên bài hát hoặc tên ca sĩ
                if (normalizedTenBaiHat.contains(normalizedQuery) || normalizedCaSi.contains(normalizedQuery)) {
                    filteredList.add(song);
                }
            }
        }

        // Cập nhật danh sách bài hát hiển thị trên ListView
        searchSongsAdapter.updateList(filteredList);
    }

    private String normalizeString(String input) {
        // Loại bỏ dấu từ chuỗi
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("").toLowerCase();
    }
}