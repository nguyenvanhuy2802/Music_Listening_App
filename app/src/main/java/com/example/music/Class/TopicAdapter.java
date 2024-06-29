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

public class TopicAdapter extends ArrayAdapter<Topic> {
    private Context context;
    private List<Topic> topicList;

    public TopicAdapter(@NonNull Context context, @NonNull List<Topic> topicList) {
        super(context, 0, topicList);
        this.context = context;
        this.topicList = topicList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listChuDe = convertView;
        if (listChuDe == null) {
            listChuDe = LayoutInflater.from(context).inflate(R.layout.item_topic, parent, false);
        }
        Topic currentTopic = topicList.get(position);
        TextView txtTitle = listChuDe.findViewById(R.id.tenChuDe);
        ImageView txtImage = listChuDe.findViewById(R.id.imageChuDe);

        txtTitle.setText(currentTopic.getTenChuDe());
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRefPathImage = storage.getReference().child(currentTopic.getHinhChuDe());
        try {
            File localFileImage = File.createTempFile("image", "jpg");
            storageRefPathImage.getFile(localFileImage)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Load ảnh từ local file vào ImageView
                        Bitmap bitmap = BitmapFactory.decodeFile(localFileImage.getAbsolutePath());
                        txtImage.setImageBitmap(bitmap);
                    })
                    .addOnFailureListener(e -> {
                        e.printStackTrace();
                        // Xử lý lỗi khi tải ảnh không thành công
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listChuDe;
    }
}
