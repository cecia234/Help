package it.units.ceschia.help.fragment;

import static it.units.ceschia.help.utility.ViewsUtility.getTextView;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.units.ceschia.help.R;
import it.units.ceschia.help.entity.User;
import it.units.ceschia.help.viewmodel.UserViewModel;

public class UserInfoFragment extends Fragment {

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


    }

    private void updateInfos(){
        userViewModel.getUser().observe(getViewLifecycleOwner(), (Observer<User>) user -> {
            TextView nameSurname = getTextView(getView(),R.id.text_view_userinfo_namesurname);
            TextView email = getTextView(getView(),R.id.text_view_userinfo_email);
            TextView telephone = getTextView(getView(),R.id.text_view_userinfo_phone);
            TextView country = getTextView(getView(),R.id.text_view_userinfo_country);
            TextView city = getTextView(getView(),R.id.text_view_userinfo_city);
            TextView address = getTextView(getView(),R.id.text_view_userinfo_address);

            nameSurname.setText(user.getName() + " " + user.getSurname());
            email.setText(user.getEmail());
            telephone.setText(user.getTelephone());
            country.setText(user.getCountry());
            city.setText(user.getCity());
            address.setText(user.getAddress());

        });
    }
}