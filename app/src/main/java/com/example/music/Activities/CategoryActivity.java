package com.example.music.Activities;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private ListView lvCategorys;
    private MusicDatabaseHelper dbHelper;
    private List<Category> categoryList;
    private CategoryAdapter categoryAdapter;
    private ImageView btnBackCategory;
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


        Bundle bundle = getIntent().getExtras();
        int idSelect = 0;
        if (bundle != null) {
            int idTopic1 = bundle.getInt("idTopic1",-1);
            int idTopic2 = bundle.getInt("idTopic2",-1);
            int idTopic3 = bundle.getInt("idTopic3",-1);
            if (idTopic1 != -1){
                idSelect=idTopic1;
            }else if(idTopic2 != -1){
                idSelect=idTopic2;
            } else {
                idSelect = idTopic3;
            }

        }
        btnBackCategory = findViewById(R.id.btnBackCategory);
        lvCategorys = findViewById(R.id.lvCategorys);
        dbHelper = new MusicDatabaseHelper(this);
        categoryList = dbHelper.getAllTheLoai();
        List<Category> categoryByIdList = getCategoryById(idSelect);

        categoryAdapter = new CategoryAdapter(this, categoryByIdList);
        lvCategorys.setAdapter(categoryAdapter);

        btnBackCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lvCategorys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category category = categoryByIdList.get(position);
                Intent intent = new Intent(CategoryActivity.this, ListMusicActivity.class);
                int idCategory = category.getIdTheLoai();
                Bundle bundle = new Bundle();
                bundle.putInt("idCategory", idCategory);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }

    public List<Category> getCategoryById(int idTopic){
        List<Category> categoryByIdList = new ArrayList<>();
        for (Category category: categoryList){
            if(category.getIdChuDe()==idTopic){
                categoryByIdList.add(category);
            }
        }
        return categoryByIdList;
    }
}