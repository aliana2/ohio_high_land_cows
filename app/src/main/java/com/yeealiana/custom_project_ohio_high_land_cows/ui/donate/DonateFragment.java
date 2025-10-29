package com.yeealiana.custom_project_ohio_high_land_cows.ui.donate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.yeealiana.custom_project_ohio_high_land_cows.MainActivity;
import com.yeealiana.custom_project_ohio_high_land_cows.R;
import com.yeealiana.custom_project_ohio_high_land_cows.databinding.FragmentDonateBinding;

public class DonateFragment extends Fragment {

    private FragmentDonateBinding binding;
    SharedPreferences sp;
    SharedPreferences.Editor e;
    String pastSubmissionsString;
    TextView pastSubmissions;
    String rememberPastCardInfoPrompt;
    EditText cardNum, cvv, expDate, nameOnCard;
    Button submit;
    TextView donateTotal;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DonateViewModel donateViewModel =
                new ViewModelProvider(this).get(DonateViewModel.class);

        binding = FragmentDonateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDonate;
        donateViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        donateTotal = root.findViewById(R.id.donate_total);

        sp = getActivity().getSharedPreferences("Values", Context.MODE_PRIVATE);
        e = sp.edit();

        pastSubmissions = root.findViewById(R.id.past_submissions);
        cardNum = root.findViewById(R.id.card_num);
        cvv = root.findViewById(R.id.cvv);
        expDate = root.findViewById(R.id.exp_date);
        nameOnCard = root.findViewById(R.id.name);
        submit = root.findViewById(R.id.submit);
        rememberPastCardInfoPrompt = getString(R.string.remember_card_info_prompt);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitCardInfo(v);
            }
        });


        setInitialValues();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void clearSP() {
        e.putString("pastSubmissions", "");
        e.apply();
    }

    private void setInitialValues() {

        pastSubmissionsString   = sp.getString("pastSubmissions", "");
        if (pastSubmissionsString.isEmpty()) {
            pastSubmissions.setText(rememberPastCardInfoPrompt + "no submissions yet :( be the first! <3");
        } else {
            pastSubmissions.setText(rememberPastCardInfoPrompt + pastSubmissionsString);
        }

        int total = sp.getInt("total", 0);
        donateTotal.setText(String.format("in your cart in the sweet treat tab, you have a total of $%d, which is the amount you will be donating by pressing submit!", total));

    }

    public void onSubmitCardInfo(View view) {
        // toast pop up "fill out the form >:(" if there are empty fields
        if ( cardNum.getText().toString().isEmpty() || cvv.getText().toString().isEmpty() || expDate.getText().toString().isEmpty() || nameOnCard.getText().toString().isEmpty() ) {
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(requireContext(), "make sure to fill out all fields in the form... \uD83D\uDE12", duration);
            toast.show();
        } else {

            // put info into the shared preferences, display in past submissions textview
            String currentSubmission = "card number: " + cardNum.getText() + "\ncvv: " + cvv.getText() + "\nexpiration date: " + expDate.getText() + "\nname on card: " + nameOnCard.getText() + "\n\n";
            pastSubmissionsString += currentSubmission;
            e.putString("pastSubmissions", pastSubmissionsString);
            e.apply();
            pastSubmissions.setText(rememberPastCardInfoPrompt + pastSubmissionsString);

            // toast message "thank you", vibration buzz
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(requireContext(), "thank you ! your donation is appreciated by the cattle... and me!", duration);
            toast.show();

            // clear edittext fields
            for (EditText v : new EditText[]{cardNum, cvv, expDate, nameOnCard}) {
                v.setText("");
            }

            // reset totals & money
            donateTotal.setText("in your cart in the sweet treat tab, you have a total of $0, which is the amount you will be donating by pressing submit!");
            e.putInt("total", 0);
            e.putInt("numDonut", 0);
            e.putInt("numCake", 0);
            e.putInt("numCorn", 0);
            e.apply();

        }

    }

}