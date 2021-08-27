package it.units.ceschia.help.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import it.units.ceschia.help.R;
import it.units.ceschia.help.entity.LoginResult;
import it.units.ceschia.help.viewmodel.UserViewModel;

public class LoginFragment extends Fragment implements View.OnClickListener{
    public static String LOGIN_SUCCESSFUL = "LOGIN_SUCCESSFUL";

    private UserViewModel userViewModel;
    private SavedStateHandle savedStateHandle;

    public LoginFragment() {
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        savedStateHandle = Navigation.findNavController(view)
                .getPreviousBackStackEntry()
                .getSavedStateHandle();
        savedStateHandle.set(LOGIN_SUCCESSFUL, false);

        int[] buttons = {R.id.signin_button, R.id.signup_button};
        for (int button : buttons) {
            Button b = view.findViewById(button);
            b.setOnClickListener(this);
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signin_button:
                Log.i("echo", "clickSignIn");
                EditText mailEditText = (EditText) view.getRootView().findViewById(R.id.edit_text_prompt_email_signin);
                EditText pwEditText = (EditText) view.getRootView().findViewById(R.id.edit_text_prompt_pw_signin);
                String email = mailEditText.getText().toString();
                String password = pwEditText.getText().toString();

                login(email, password);
                break;
            case R.id.signup_button:
                Log.i("echo", "clickSignup");
                NavController nc = Navigation.findNavController(view);
                nc.navigate(R.id.signUpFragment);
                break;
        }
    }

    private void login(String email,String password){
        userViewModel.login(email, password).observe(requireActivity(), (Observer<LoginResult>) result -> {
            if (result.success) {
                savedStateHandle.set(LOGIN_SUCCESSFUL, true);
                NavHostFragment.findNavController(this).navigate(R.id.homeFragment);
            } else {
                //showErrorMessage();
                Log.i("echo","Login Failed");
            }
        });
    }
}