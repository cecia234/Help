package it.units.ceschia.help.fragment;

import android.media.MediaPlayer;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import it.units.ceschia.help.R;
import it.units.ceschia.help.entity.GenericResult;
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
                case R.id.toolbar_action_user_info:
                    userViewModel.fetchSpecificUserInfos().observe(requireActivity(), (Observer<? super GenericResult>) res -> {
                        if (res.success) {
                            nc.navigate(R.id.action_homeFragment_to_userInfoFragment);
                        }else{
                            String s = "error fetching specific user infos";
                            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                        }
                    });
                    return true;
                default:
                    return false;
            }
        });

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        userViewModel.fetchUserInfos();

        userViewModel.getFirebaseUser().observe(getViewLifecycleOwner(), (Observer<FirebaseUser>) user -> {
            if (user==null){
                nc.navigate(R.id.action_homeFragment_to_loginFragment);
            }
        });

        int[] buttons = {R.id.button_emergency, R.id.button_relatives, R.id.button_noise, R.id.button_home_display_informations};
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
                nc.navigate(R.id.action_homeFragment_to_emergencyFragment);
                break;
            case R.id.button_relatives: nc.navigate(R.id.action_homeFragment_to_contactsFragment);
                break;
            case R.id.button_noise:
                makeNoise(view);
                break;
            case R.id.button_home_display_informations:
                userViewModel.fetchSpecificUserInfos().observe(requireActivity(), (Observer<? super GenericResult>) res -> {
                    if (res.success) {
                        nc.navigate(R.id.action_homeFragment_to_showInformationFragment);
                    }else{
                        String s = "error fetching specific user infos";
                        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
      }
    }

    private void makeNoise(View view){
        final MediaPlayer mp = MediaPlayer.create(this.getContext(), R.raw.ring);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                view.getRootView().findViewById(R.id.button_stop_sound).setVisibility(View.GONE);
            }

        });

        mp.start();
        view.getRootView().findViewById(R.id.button_stop_sound).setVisibility(View.VISIBLE);
        view.getRootView().findViewById(R.id.button_stop_sound).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                view.setVisibility(View.GONE);
            }
        });
    }

}