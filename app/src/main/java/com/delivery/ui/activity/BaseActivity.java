package com.delivery.ui.activity;


import android.widget.ImageView;
import com.bumptech.glide.Glide;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;

public abstract class BaseActivity extends AppCompatActivity {

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext()).load(imageUrl).into(view);
    }
}
