package it.units.ceschia.help.fragment;

import static it.units.ceschia.help.utility.ViewsUtility.getTextView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import it.units.ceschia.help.R;
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

        userViewModel.getFirebaseUser().observe(getViewLifecycleOwner(), user -> {
            if (user==null){
                NavController nc = Navigation.findNavController(view);
                nc.navigate(R.id.action_userInfoFragment_to_loginFragment);
            }
        });

        int[] buttons = {R.id.button_user_info_edit_user_info, R.id.button_user_info_add_specific_infos, R.id.button_user_info_contacts};
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

        userViewModel.getUser().observe(getViewLifecycleOwner(), user -> {

            String nameSurnameString =user.getName() + " " + user.getSurname();
            nameSurname.setText(nameSurnameString);
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
                userViewModel.fetchUserInfos().observe(requireActivity(),result -> {
                    if (result.success) {
                        nc.navigate(R.id.action_userInfoFragment_to_editInfosFragment);
                    }else{
                        String s = "error fetching contact infos";
                        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.button_user_info_add_specific_infos:
                userViewModel.fetchSpecificUserInfos().observe(requireActivity(),result -> {
                    if (result.success) {
                        nc.navigate(R.id.action_userInfoFragment_to_editSpecificInfosFragment);
                    }else{
                        String s = "error fetching specific contact infos";
                        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.button_user_info_contacts:
                userViewModel.fetchUserContacts().observe(requireActivity(), result->{
                    if (result.success) {
                        nc.navigate(R.id.action_userInfoFragment_to_contactsFragment);
                    }else{
                        String s = "error fetching specific contact infos";
                        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}