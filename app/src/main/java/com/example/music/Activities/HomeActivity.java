package com.example.music.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.music.Class.Topic;
import com.example.music.Class.MusicDatabaseHelper;
import com.example.music.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView searchButton,imgTopic1,imgTopic2,imgTopic3;
    private TextView item1,item2,item3;
    private MusicDatabaseHelper dbHelper;
    private FirebaseStorage storage;
    private Button btnXemThem;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        searchButton = findViewById(R.id.searchButtonHome);
        searchButton.setOnClickListener(this);

        //của khang (hiển thị chủ đề)
        item1 = findViewById(R.id.item1);
        item2 = findViewById(R.id.item2);
        item3 = findViewById(R.id.item3);

        imgTopic1 = findViewById(R.id.imgTopic1);
        imgTopic2 = findViewById(R.id.imgTopic2);
        imgTopic3 = findViewById(R.id.imgTopic3);

        btnXemThem = findViewById(R.id.btnXemThem);

        storage = FirebaseStorage.getInstance();
        dbHelper = new MusicDatabaseHelper(this);
        List<Topic> topList = dbHelper.getAllChuDe();

        item1.setText(String.valueOf(topList.get(0).getTenChuDe()));
        item2.setText(String.valueOf(topList.get(1).getTenChuDe()));
        item3.setText(String.valueOf(topList.get(2).getTenChuDe()));
        ImageView imgViews[] = {imgTopic1, imgTopic2, imgTopic3};
        List<Topic> topics = dbHelper.getAllChuDe();
        for (int i = 0; i < 3; i++) {
        Topic topic = topics.get(i);
        ImageView imageView = imgViews[i];
        loadImage(imageView, topic);
        }
    }

        private void loadImage(ImageView imageView, Topic topic) {
        StorageReference storageRefPathImage = storage.getReference().child(topic.getHinhChuDe());
        try {
            File localFileImage = File.createTempFile("image", "jpg");
            storageRefPathImage.getFile(localFileImage)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Load ảnh từ local file vào ImageView
                        Bitmap bitmap = BitmapFactory.decodeFile(localFileImage.getAbsolutePath());
                        imageView.setImageBitmap(bitmap);
                    })
                    .addOnFailureListener(e -> {
                        e.printStackTrace();
                        // Xử lý lỗi khi tải ảnh không thành công

                    });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void onClick (View v){
        if(v.getId() == R.id.searchButtonHome){
            Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.btnXemThem){
            Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
            startActivity(intent);
        }
    }

    //của khang

}