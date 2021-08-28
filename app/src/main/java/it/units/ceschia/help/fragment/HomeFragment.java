package it.units.ceschia.help.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

import it.units.ceschia.help.R;
import it.units.ceschia.help.entity.User;
import it.units.ceschia.help.viewmodel.UserViewModel;

public class HomeFragment extends Fragment implements View.OnClickListener {

    UserViewModel userViewModel;

    //NavController navController;

    public HomeFragment() {
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final NavController nc = Navigation.findNavController(getView());

        Toolbar myToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_home);
        myToolbar.setTitle(R.string.app_name);
        myToolbar.inflateMenu(R.menu.menu);
        myToolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.toolbar_action_settings:
                    nc.navigate(R.id.settingsFragment);
                    return true;
                case R.id.toolbar_action_user_info:
                    Log.i("echo", "parawea");
                    nc.navigate(R.id.action_homeFragment_to_userInfoFragment);
                    return true;
                default:
                    // If we got here, the user's action was not recognized.
                    // Invoke the superclass to handle it.
                    return false;
            }
        });

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        userViewModel.fetchUserInfos();

        userViewModel.getFirebaseUser().observe(getViewLifecycleOwner(), (Observer<FirebaseUser>) user -> {
            if (user != null) {
                Log.i("echo", "Logged!!!");
            } else {
                nc.navigate(R.id.action_homeFragment_to_loginFragment);
            }
        });

        userViewModel.getUser().observe(getViewLifecycleOwner(), (Observer<User>) user -> {
            String userInfos = user != null ? userViewModel.getUser().getValue().toString() : "no user infos";
            TextView userinfoTV = (TextView) getActivity().findViewById(R.id.text_view_user_info_home);
            userinfoTV.setText(userInfos);
        });

        int[] buttons = {R.id.button_emergency, R.id.button_relatives, R.id.button_noise};
        for (int button : buttons) {
            Button b = view.findViewById(button);
            b.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        final NavController nc = Navigation.findNavController(view);
        switch (view.getId()) {
            case R.id.button_emergency:
                Log.i("echo", "clickB");
                nc.navigate(R.id.emergencyFragment);
                break;
            case R.id.button_relatives:
                Log.i("echo", "clickC");
                nc.navigate(R.id.relativesFragment);
                break;
            case R.id.button_noise:
                Log.i("echo", "clickD");
                nc.navigate(R.id.noiseFragment);
                break;
        }
    }

}