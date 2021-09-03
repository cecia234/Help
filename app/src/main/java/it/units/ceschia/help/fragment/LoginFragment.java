package it.units.ceschia.help.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import it.units.ceschia.help.R;
import it.units.ceschia.help.viewmodel.UserViewModel;

public class LoginFragment extends Fragment implements View.OnClickListener{

    private UserViewModel userViewModel;

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
        Toolbar myToolbar = getActivity().findViewById(R.id.toolbar_login);
        myToolbar.setTitle(R.string.toolbar_sign_in);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

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
                EditText mailEditText = view.getRootView().findViewById(R.id.edit_text_prompt_email_signin);
                EditText pwEditText = view.getRootView().findViewById(R.id.edit_text_prompt_pw_signin);
                String email = mailEditText.getText().toString();
                String password = pwEditText.getText().toString();

                login(email, password);
                break;
            case R.id.signup_button:
                NavController nc = Navigation.findNavController(view);
                nc.navigate(R.id.signUpFragment);
                break;
        }
    }

    private void login(String email,String password){
        userViewModel.login(email, password).observe(requireActivity(), result -> {
            if (result.success) {
                NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_homeFragment);
                Toast.makeText(getContext(), getString(R.string.login_success), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
            }
        });
    }
}