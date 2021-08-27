package it.units.ceschia.help.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import it.units.ceschia.help.R;
import it.units.ceschia.help.entity.User;
import it.units.ceschia.help.viewmodel.UserViewModel;

public class SignUpFragment extends Fragment {

    UserViewModel userViewModel;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
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
        int[] signUpFieldIds = {R.id.edit_text_prompt_name,R.id.edit_text_prompt_surname,R.id.edit_text_prompt_email_signin,R.id.edit_text_prompt_pw_signin,R.id.edit_text_prompt_phone,R.id.edit_text_prompt_country,R.id.edit_text_prompt_city,R.id.edit_text_prompt_address};

        //Ricordo controllo pw



        Button signUpButton = (Button) view.findViewById(R.id.signup_button_send);
        signUpButton.setOnClickListener(v->{
            TextView emailtv = (TextView) view.findViewById(R.id.edit_text_prompt_email_signup);
            TextView pwtv = (TextView) view.findViewById(R.id.edit_text_prompt_pw_signup);
            String email = emailtv.getText().toString();
            String pw = pwtv.getText().toString();
            User user = new User();
            user.setEmail(email);

            Log.i("echo","Email: "+email+" passowrd: " + pw);
            Log.i("echo",user.toString());
            userViewModel.signUpUser(user,pw);
        });

    }

    private String getTextFromEditTextView(View view,int id){
        TextView t = (TextView) view.findViewById(id);
        return t.getText().toString();
    }
}