package it.units.ceschia.help.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import it.units.ceschia.help.R;
import it.units.ceschia.help.entity.HelpRequestType;
import it.units.ceschia.help.entity.Position;
import it.units.ceschia.help.entity.User;
import it.units.ceschia.help.viewmodel.UserViewModel;

public class EmergencyFragment extends Fragment implements View.OnClickListener{

    UserViewModel userViewModel;
    User currentUser;
    Position currentPosition;


    public EmergencyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        currentUser = userViewModel.getUser().getValue();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_emergency, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int[] buttons = {R.id.emergency_button_sickness, R.id.emergency_button_homeviolence, R.id.emergency_button_naturaldisaster,R.id.emergency_button_theft};
        for (int button : buttons) {
            Button b = view.findViewById(button);
            b.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(Intent.ACTION_DIAL);
        currentPosition = userViewModel.getPosition().getValue();
        switch (view.getId()) {
            case R.id.emergency_button_sickness:
                userViewModel.sendHelpRequest(currentUser,currentPosition, HelpRequestType.SICKNESS);
                i.setData(Uri.parse("tel:118"));
                break;
            case R.id.emergency_button_homeviolence:
                userViewModel.sendHelpRequest(currentUser,currentPosition, HelpRequestType.HOME_VIOLENCE);
                i.setData(Uri.parse("tel:112"));
                break;
            case R.id.emergency_button_naturaldisaster:
                userViewModel.sendHelpRequest(currentUser,currentPosition, HelpRequestType.NATURAL_DISASTER);
                i.setData(Uri.parse("tel:116"));
                break;
            case R.id.emergency_button_theft:
                userViewModel.sendHelpRequest(currentUser,currentPosition, HelpRequestType.THEFT);
                i.setData(Uri.parse("tel:112"));
                break;
        }
        startActivity(i);
    }
}