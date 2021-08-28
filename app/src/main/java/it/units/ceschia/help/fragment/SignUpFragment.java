package it.units.ceschia.help.fragment;

import static it.units.ceschia.help.utility.ViewsUtility.getTextFromEditTextView;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import it.units.ceschia.help.R;
import it.units.ceschia.help.entity.LoginResult;
import it.units.ceschia.help.entity.SignupResult;
import it.units.ceschia.help.entity.User;
import it.units.ceschia.help.utility.ViewsUtility;
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
            String email = getTextFromEditTextView(view,R.id.edit_text_prompt_email_signup);
            String pw = getTextFromEditTextView(view,R.id.edit_text_prompt_pw_signup);
            String name = getTextFromEditTextView(view,R.id.edit_text_prompt_name);
            String surname = getTextFromEditTextView(view,R.id.edit_text_prompt_surname);
            String phoneNumber = getTextFromEditTextView(view,R.id.edit_text_prompt_phone);
            String country = getTextFromEditTextView(view,R.id.edit_text_prompt_country);
            String city = getTextFromEditTextView(view,R.id.edit_text_prompt_city);
            String address = getTextFromEditTextView(view,R.id.edit_text_prompt_address);

            User user = new User(name,surname,email,phoneNumber,country,city,address);

            Log.i("echo","Email: "+email+" passowrd: " + pw);
            Log.i("echo",user.toString());
            signup(user,pw);
        });

    }

    private void signup(User user,String password){
        userViewModel.signUpUser(user,password).observe(requireActivity(), (Observer<SignupResult>) result -> {
            if (result.success) {
                NavHostFragment.findNavController(this).navigate(R.id.action_signUpFragment_to_homeFragment);
            } else {
                //showErrorMessage();
                Log.i("echo","Login Failed");
            }
        });
    }

}