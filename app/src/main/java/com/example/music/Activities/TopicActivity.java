package com.example.music.Activities;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.music.Class.Topic;
import com.example.music.Class.MusicDatabaseHelper;
import com.example.music.Class.TopicAdapter;
import com.example.music.R;

import java.util.List;

public class TopicActivity extends AppCompatActivity {

    private ListView lvTopics;
    private MusicDatabaseHelper dbHelper;
    private List<Topic> topicList;
    private TopicAdapter topicAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_topic);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lvTopics = findViewById(R.id.lvTopics);
        dbHelper = new MusicDatabaseHelper(this);
        topicList = dbHelper.getAllChuDe();

        topicAdapter = new TopicAdapter(this, topicList);
        lvTopics.setAdapter(topicAdapter);


    }
}