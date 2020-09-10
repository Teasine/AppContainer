package com.example.container.appcontainer;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.card.MaterialCardView;
import com.xw.repo.BubbleSeekBar;

import static android.content.Context.MODE_PRIVATE;

public class AjustesDialogFragment extends DialogFragment {

    DialogFragment dialogFragment;
    private Context context;
    private CardView plasticCard;
    private CardView paperCard;
    private CardView glassCard;
    private CardView organicCard;
    private CardView wasterCard;
    private Button cancelButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dialogFragment = this;
        context = getContext();

        View view = inflater.inflate(R.layout.fragment_ajustes,container,false);


        plasticCard = view.findViewById(R.id.Plastic);
        plasticCard.setOnClickListener(collapseExpandTextView);

        paperCard = view.findViewById(R.id.Paper);
        paperCard.setOnClickListener(collapseExpandTextView);

        glassCard = view.findViewById(R.id.Glass);
        glassCard.setOnClickListener(collapseExpandTextView);

        organicCard = view.findViewById(R.id.Organic);
        organicCard.setOnClickListener(collapseExpandTextView);

        wasterCard = view.findViewById(R.id.Waste);
        wasterCard.setOnClickListener(collapseExpandTextView);

        cancelButton = view.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(closeAction);

        return view;
    }

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


    // Cuando es pulsado el cardView
    View.OnClickListener collapseExpandTextView = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // Obteniendo el texto que se va a desplegar y contraer desde el parent
            View parent = ((ViewGroup) v).getChildAt(0);
            View linearLayout = ((ViewGroup) parent).getChildAt(0);
            View text = ((ViewGroup) linearLayout).getChildAt(1);

            if (text.getVisibility() == View.GONE) {
                // it's collapsed - expand it
                text.setVisibility(View.VISIBLE);
            } else {
                // it's expanded - collapse it
                text.setVisibility(View.GONE);
            }
        }
    };

    public void onResume()
    {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(1070, 2000);
        window.setGravity(Gravity.CENTER);
    }

}

