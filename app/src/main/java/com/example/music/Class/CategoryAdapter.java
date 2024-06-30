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

public class CategoryAdapter extends ArrayAdapter<Category> {
    private Context context;
    private List<Category> categoryList;

    public CategoryAdapter(@NonNull Context context, @NonNull List<Category> categoryList) {
        super(context, 0, categoryList);
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.item_topic, parent, false);
        }
        Category currentCategory = categoryList.get(position);
        TextView txtTitle = listItemView.findViewById(R.id.tenChuDe);
        ImageView txtImage = listItemView.findViewById(R.id.imageChuDe);

        txtTitle.setText(currentCategory.getTenTheLoai());
        FirebaseStorage storage = FirebaseStorage.getInstance();
        if(currentCategory.getHinhTheLoai() != null) {
            StorageReference storageRefPathImage = storage.getReference().child(currentCategory.getHinhTheLoai());
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
        }
        return listItemView;
    }
}
