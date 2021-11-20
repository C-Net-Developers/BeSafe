package com.example.besafe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;

public class EmergencyNumber extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_number);
        //buttons
        SwipeButton police=(SwipeButton) findViewById(R.id.Police);
        SwipeButton fire=(SwipeButton)findViewById(R.id.fire);
        SwipeButton ambulance=(SwipeButton)findViewById(R.id.ambulance);
        SwipeButton natEmg=(SwipeButton)findViewById(R.id.natEmg);
        SwipeButton LPG=(SwipeButton)findViewById(R.id.LPG);

        police.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                Intent intent=new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:100"));
                if(police.isActive())
                {
                    police.toggleState();
                    startActivity(intent);
                }
            }

        });

        fire.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                Intent intent=new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:101"));

                if(fire.isActive())
                {
                    fire.toggleState();
                    startActivity(intent);
                }

            }
        });
        ambulance.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                Intent intent=new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:102"));
                if(ambulance.isActive())
                {
                    ambulance.toggleState();
                    startActivity(intent);
                }
            }
        });
        natEmg.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                Intent intent=new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:112"));
                if(natEmg.isActive())
                {
                    natEmg.toggleState();
                    startActivity(intent);
                }
            }
        });
        LPG.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                Intent intent=new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:1906"));
                if(LPG.isActive())
                {
                    LPG.toggleState();
                    startActivity(intent);
                }
            }

        });

    }
    public void goBack(View view)
    {
        finish();
    }
}