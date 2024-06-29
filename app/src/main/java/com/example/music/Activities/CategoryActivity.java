package com.example.music.Activities;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.music.Class.Category;
import com.example.music.Class.CategoryAdapter;
import com.example.music.Class.MusicDatabaseHelper;
import com.example.music.Class.Topic;
import com.example.music.Class.TopicAdapter;
import com.example.music.R;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private ListView lvCategorys;
    private MusicDatabaseHelper dbHelper;
    private List<Category> categoryList;
    private CategoryAdapter categoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        lvCategorys = findViewById(R.id.lvCategorys);
        dbHelper = new MusicDatabaseHelper(this);
        categoryList = dbHelper.getAllTheLoai();

        categoryAdapter = new CategoryAdapter(this,categoryList);
        lvCategorys.setAdapter(categoryAdapter);
    }
}