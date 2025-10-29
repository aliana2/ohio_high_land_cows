package com.yeealiana.custom_project_ohio_high_land_cows.ui.about;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.yeealiana.custom_project_ohio_high_land_cows.R;
import com.yeealiana.custom_project_ohio_high_land_cows.databinding.FragmentAboutBinding;

public class AboutFragment extends Fragment {

    private FragmentAboutBinding binding;
    ImageButton cow;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AboutViewModel aboutViewModel =
                new ViewModelProvider(this).get(AboutViewModel.class);

        binding = FragmentAboutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        cow = root.findViewById(R.id.cow_picture);
        cow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMoo(root);
            }
        });

        final TextView textView = binding.textAbout;
        aboutViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void goMoo(View view) {
        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.moosound);
        mediaPlayer.start();

    }


}