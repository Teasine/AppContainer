package com.example.container.appcontainer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import com.xw.repo.BubbleSeekBar;

import static android.content.Context.MODE_PRIVATE;

public class AjustesDialogFragment extends DialogFragment {

    DialogFragment dialogFragment;
    private CardView ajustesCardView;
    private Context context;

    private BubbleSeekBar seekBarDistancia;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Button saveButton;
    private Button cancelButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dialogFragment = this;
        context = getContext();

        View view = inflater.inflate(R.layout.fragment_ajustes,container,false);
        BubbleSeekBar seekBarDistancia = view.findViewById(R.id.seekBarRadio);
        Button saveButton = view.findViewById(R.id.saveButton);
        Button cancelButton = view.findViewById(R.id.cancelButton);

        preferences = context.getSharedPreferences("Ajustes", MODE_PRIVATE);
        editor = preferences.edit();

        saveButton.setOnClickListener(doneAction);

        cancelButton.setOnClickListener(closeAction);

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

        return view;
    }

    // Cuando es pulsado el botón entendido
    View.OnClickListener doneAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Guardamos
            editor.commit();

            // Cierra el popup
            dialogFragment.dismiss();
            dialogFragment.dismissAllowingStateLoss();
            getFragmentManager().executePendingTransactions();
        }
    };

    // Cuando es pulsado el botón cerrar
    View.OnClickListener closeAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Cierra el popup
            dialogFragment.dismiss();
            dialogFragment.dismissAllowingStateLoss();
            getFragmentManager().executePendingTransactions();
        }
    };

}

