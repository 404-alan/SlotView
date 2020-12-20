package com.example.justfortest;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SwitchCompat;

public class MainActivity extends AppCompatActivity {

    ImageView showView;
    Drawable showOverlaytDr;
    boolean isCircleShow = true;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView rectView = findViewById(R.id.rectView);
        final ImageView circleView = findViewById(R.id.circleView);
        SwitchCompat switchCompat = findViewById(R.id.overlaySwitch);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCircleShow = isChecked;
                if (isChecked) {
                    showView = circleView;
                    showOverlaytDr = getResources().getDrawable(R.drawable.circle);
                    circleView.setImageDrawable(showOverlaytDr);
                    rectView.setVisibility(View.GONE);
                    circleView.setVisibility(View.VISIBLE);
                } else {
                    showView = rectView;
                    showOverlaytDr = getResources().getDrawable(R.drawable.rect);
                    rectView.setImageDrawable(showOverlaytDr);
                    rectView.setVisibility(View.VISIBLE);
                    circleView.setVisibility(View.GONE);
                }
                showView.setTag(true);
            }
        });

        findViewById(R.id.hideSlotButton).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                boolean isShowingOverlay = (boolean) showView.getTag();
                if (isShowingOverlay) {
                    showView.setImageDrawable(null);
                } else {
                    showView.setImageDrawable(showOverlaytDr);
                }
                showView.setTag(!isShowingOverlay);
            }
        });
    }
}