package com.yeealiana.custom_project_ohio_high_land_cows.ui.sweettreat;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.yeealiana.custom_project_ohio_high_land_cows.R;
import com.yeealiana.custom_project_ohio_high_land_cows.databinding.FragmentSweettreatBinding;

import java.util.Objects;

public class SweettreatFragment extends Fragment {

    private FragmentSweettreatBinding binding;
    int cakeState = 1;
    int cornState = 1;
    int donutState = 1;

    ImageButton cakeButton;
    ImageButton cornButton;
    ImageButton donutButton;
    SharedPreferences sp;
    SharedPreferences.Editor e;
    int numCake = 0;
    int numCorn = 0;
    int numDonut = 0;
    TextView cart;
    TextView totalView;
    int total = 0;
    Button clearCart;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SweettreatViewModel sweettreatViewModel =
                new ViewModelProvider(this).get(SweettreatViewModel.class);

        binding = FragmentSweettreatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sp = getActivity().getSharedPreferences("Values", Context.MODE_PRIVATE);
        e = sp.edit();
        cart = root.findViewById(R.id.cart);
        totalView = root.findViewById(R.id.cart_total);
        setInitialValues();

        clearCart = root.findViewById(R.id.clear_cart);
        clearCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCarts();
            }
        });

        cakeButton = root.findViewById(R.id.cake_img);
        cakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchImg(v, cakeButton, 1);
            }
        });

        cornButton = root.findViewById(R.id.corn_img);
        cornButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchImg(v, cornButton, 2);
            }
        });

        donutButton = root.findViewById(R.id.donut_img);
        donutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchImg(v, donutButton, 3);
            }
        });

        final TextView textView = binding.textSweettreat;
        sweettreatViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private void setInitialValues() {

        numCake = sp.getInt("numCake", 0);
        numCorn = sp.getInt("numCorn", 0);
        numDonut = sp.getInt("numDonut", 0);

        total = numCake*45 + numCorn*38 + numDonut*86;
        e.putInt("total", total);
        e.apply();

        cart.setText(String.format("black sesame mini cakes: %d\nblue corn slabs: %d\n apple cider donuts: %d", numCake, numCorn, numDonut));
        totalView.setText(String.format("total: $%d (see sweet treat tab to pay)", total));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void switchImg(View v, ImageButton button, int buttonIndicator) {

        // buzzing
        Vibrator vibration = (Vibrator) requireContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibration.vibrate(500);

        // sound
        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.audio);
        mediaPlayer.start();

        //image switching
        if (buttonIndicator == 1) { // cake button
            if (cakeState == 1) button.setImageResource(R.mipmap.cake_2_foreground);
            else button.setImageResource(R.mipmap.cake_1);
            cakeState = (cakeState+1)%2;
            numCake++;
            total += 45;
            e.putInt("numCake", numCake);
        } else if (buttonIndicator == 2) { // corn button
            if (cornState == 1) button.setImageResource(R.mipmap.corn_2_foreground);
            else button.setImageResource(R.mipmap.corn_1);
            cornState = (cornState+1)%2;
            numCorn++;
            total += 38;
            e.putInt("numCorn", numCorn);
        } else { // donut button
            if (donutState == 1) button.setImageResource(R.mipmap.donut_2_foreground);
            else button.setImageResource(R.mipmap.donut_1);
            donutState = (donutState+1)%2;
            numDonut++;
            total += 86;
            e.putInt("numDonut", numDonut);
        }

        e.putInt("total", total);
        cart.setText(String.format("black sesame mini cakes: %d\nblue corn slabs: %d\n apple cider donuts: %d", numCake, numCorn, numDonut));
        totalView.setText(String.format("total: $%d (see sweet treat tab to pay)", total));
        e.apply();
    }

    public void clearCarts() {

        for (String s : new String[]{"cakeNum", "cornNum", "donutNum", "total"}) {
            e.putInt(s, 0);
        }
        e.apply();

        numCake = 0;
        numCorn = 0;
        numDonut = 0;
        total = 0;
        cart.setText(String.format("black sesame mini cakes: %d\nblue corn slabs: %d\n apple cider donuts: %d", numCake, numCorn, numDonut));
        totalView.setText(String.format("total: $%d (see sweet treat tab to pay)", total));

    }

}