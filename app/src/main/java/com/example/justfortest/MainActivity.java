package com.example.justfortest;

import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {

    ImageView showView;
    Drawable showOverlaytDr;
    boolean isCircleShow = true;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                TextView textView = findViewById(R.id.text);
                    int length = dpToPx(200);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(length,length);
                    textView.setLayoutParams(params);
                    textView.setPivotX(textView.getX() / 2);
                    textView.setPivotY(0);
                    textView.setScaleX(0.5f);
                    textView.setScaleY(0.5f);
            }
        });
    }

    private int dpToPx(int px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,px,getResources().getDisplayMetrics());
    }
}