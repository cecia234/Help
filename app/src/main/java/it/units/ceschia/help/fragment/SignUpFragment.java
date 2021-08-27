package it.units.ceschia.help.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.units.ceschia.help.R;

public class SignUpFragment extends Fragment {

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int[] signUpFieldIds = {R.id.edit_text_prompt_name,R.id.edit_text_prompt_surname,R.id.edit_text_prompt_email,R.id.edit_text_prompt_pw,R.id.edit_text_prompt_phone,R.id.edit_text_prompt_country,R.id.edit_text_prompt_city,R.id.edit_text_prompt_address};

        //Ricordo controllo pw


    }
}