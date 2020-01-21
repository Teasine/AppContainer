package com.example.container.appcontainer;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import com.xw.repo.BubbleSeekBar;


public class Ajustes extends Activity {

    private CardView ajustesCardView;
    private BubbleSeekBar seekBarDistancia;
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Button saveButton;


    public Ajustes(Context context_, CardView ajustesCardView_, BubbleSeekBar seekBarDistancia_, Button saveButton_) {
        this.context = context_;
        this.ajustesCardView = ajustesCardView_;
        this.seekBarDistancia = seekBarDistancia_;
        this.saveButton = saveButton_;


        //seekBarDistancia.setOnSeekBarChangeListener(this);
        preferences = context.getSharedPreferences("Ajustes", MODE_PRIVATE);
        editor = preferences.edit();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.commit();
                ajustesCardView.setVisibility(View.INVISIBLE);
            }
        });

        float radio = preferences.getFloat("Radio", 0.2f);

        if (radio == 0.2f) {
            seekBarDistancia.setProgress(200);
        } else if (radio == 0.3f) {
            seekBarDistancia.setProgress(300);
        } else if (radio == 0.4f) {
            seekBarDistancia.setProgress(400);
        } else if (radio == 0.5f) {
            seekBarDistancia.setProgress(500);
        } else if (radio == 0.6f) {
            seekBarDistancia.setProgress(600);
        } else if (radio == 0.7f) {
            seekBarDistancia.setProgress(700);
        } else if (radio == 0.8f) {
            seekBarDistancia.setProgress(800);
        }

        seekBarDistancia.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override

            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser){

                switch (progress) {
                    case 200:
                        editor.putFloat("Radio",0.2f);
                        break;
                    case 300:
                        editor.putFloat("Radio",0.3f);
                        break;
                    case 400:
                        editor.putFloat("Radio",0.4f);
                        break;
                    case 500:
                        editor.putFloat("Radio",0.5f);
                        break;
                    case 600:
                        editor.putFloat("Radio",0.6f);
                        break;
                    case 700:
                        editor.putFloat("Radio",0.7f);
                        break;
                    case 800:
                        editor.putFloat("Radio",0.8f);
                        break;
                    default:
                }
            }
        });

        // customize section texts
        seekBarDistancia.setCustomSectionTextArray(new BubbleSeekBar.CustomSectionTextArray() {
            @NonNull
            @Override
            public SparseArray<String> onCustomize(int sectionCount, @NonNull SparseArray<String> array) {
                array.clear();
                array.put(0, "200 m");
                array.put(2, "400 m");
                array.put(4, "600 m");
                array.put(6, "800 m");

                return array;
            }
        });

    }

}
