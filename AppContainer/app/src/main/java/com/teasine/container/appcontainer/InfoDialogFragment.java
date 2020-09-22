package com.teasine.container.appcontainer;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

public class InfoDialogFragment extends DialogFragment {

    DialogFragment dialogFragment;
    private Context context;
    private CardView plasticCard;
    private CardView paperCard;
    private CardView glassCard;
    private CardView organicCard;
    private CardView wasterCard;
    private ImageView closeButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dialogFragment = this;

        context = getContext();

        View view = inflater.inflate(R.layout.fragment_info,container,false);

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

        closeButton = view.findViewById(R.id.cancelButton);
        closeButton.setOnClickListener(closeAction);

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
            View linearLayout2 = ((ViewGroup) linearLayout).getChildAt(0);
            View text = ((ViewGroup) linearLayout2).getChildAt(1);

            // Obteniendo el icono para rotarlo
            View linearLayout3 = ((ViewGroup) linearLayout2).getChildAt(0);
            View dropDownButton = ((ViewGroup) linearLayout3).getChildAt(2);


            if (text.getVisibility() == View.GONE) {
                // Nice animation transition
                ObjectAnimator anim = ObjectAnimator.ofFloat(dropDownButton, "rotation",0, 180);
                anim.setDuration(300);
                anim.start();

                // Nice fade animation
                text.animate().translationY(0);
                Fade mFade = new Fade(Fade.IN);
                TransitionManager.beginDelayedTransition((ViewGroup) v, mFade);

                // it's collapsed - expand it
                text.setVisibility(View.VISIBLE);

            } else {
                // Nice animation transition
                ObjectAnimator anim = ObjectAnimator.ofFloat(dropDownButton, "rotation",180, 0);
                anim.setDuration(300);
                anim.start();

                // Nice fade animation
                text.animate().translationY(text.getHeight());
                Fade mFade = new Fade(Fade.OUT);
                TransitionManager.beginDelayedTransition((ViewGroup) v, mFade);

                // it's expanded - collapse it
                text.setVisibility(View.GONE);
            }
        }
    };


    public void onResume()
    {
        super.onResume();

        // Getting height and width of the phone screen
        DisplayMetrics displayMetrics = new DisplayMetrics();

        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        // Adding margin and centering to the dialog
        Window window = getDialog().getWindow();
        window.setLayout(width-30, height-120);
        window.setGravity(Gravity.CENTER);
    }

    @Override public void onStart() {
        super.onStart();

        // Ponemos el dimAmount a 0 para que no haya un fondo negro transparente detrás
        // del popup, ya que el fab ya nos crea una transparencia blanca que es la que
        // queremos
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.0f;

        window.setAttributes(windowParams);
    }

}

