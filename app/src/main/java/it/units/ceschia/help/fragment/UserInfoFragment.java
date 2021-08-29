package it.units.ceschia.help.fragment;

import static it.units.ceschia.help.utility.ViewsUtility.getTextView;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

import it.units.ceschia.help.R;
import it.units.ceschia.help.entity.User;
import it.units.ceschia.help.viewmodel.UserViewModel;

public class UserInfoFragment extends Fragment implements View.OnClickListener {

    UserViewModel userViewModel;
    public UserInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateInfos();

        userViewModel.getFirebaseUser().observe(getViewLifecycleOwner(), (Observer<FirebaseUser>) user -> {
            if (user != null) {
                Log.i("echo", "Logged!!!");
            } else {
                NavController nc = Navigation.findNavController(view);
                nc.navigate(R.id.action_userInfoFragment_to_loginFragment);
            }
        });

        int[] buttons = {R.id.button_user_info_edit_user_info, R.id.button_user_info_add_specific_infos, R.id.button_user_info_contacts,R.id.button_user_info_message_preferences,R.id.button_user_info_priorities};
        for (int button : buttons) {
            Button b = view.findViewById(button);
            b.setOnClickListener(this);
        }

    }

    private void updateInfos(){
        TextView nameSurname = getTextView(getView(),R.id.text_view_userinfo_namesurname);
        TextView email = getTextView(getView(),R.id.text_view_userinfo_email);
        TextView telephone = getTextView(getView(),R.id.text_view_userinfo_phone);
        TextView country = getTextView(getView(),R.id.text_view_userinfo_country);
        TextView city = getTextView(getView(),R.id.text_view_userinfo_city);
        TextView address = getTextView(getView(),R.id.text_view_userinfo_address);

        userViewModel.getUser().observe(getViewLifecycleOwner(), (Observer<User>) user -> {

            nameSurname.setText(user.getName() + " " + user.getSurname());
            email.setText(user.getEmail());
            telephone.setText(user.getTelephone());
            country.setText(user.getCountry());
            city.setText(user.getCity());
            address.setText(user.getAddress());

        });
    }
    @Override
    public void onClick(View view) {
        final NavController nc = Navigation.findNavController(view);
        switch (view.getId()) {
            case R.id.button_user_info_edit_user_info:
                Log.i("echo", "clickB");
                nc.navigate(R.id.action_userInfoFragment_to_editInfosFragment);
                break;
            case R.id.button_user_info_add_specific_infos:
                Log.i("echo", "clickC");
                nc.navigate(R.id.action_userInfoFragment_to_editSpecificInfosFragment);
                break;
            case R.id.button_user_info_contacts:
                Log.i("echo", "clickD");
                nc.navigate(R.id.action_userInfoFragment_to_contactsFragment);
                break;
            case R.id.button_user_info_message_preferences:
                Log.i("echo", "clickD");
                //nc.navigate(R.id.noiseFragment);
                break;
            case R.id.button_user_info_priorities:
                Log.i("echo", "clickD");
               // nc.navigate(R.id.noiseFragment);
                break;
        }
    }
}