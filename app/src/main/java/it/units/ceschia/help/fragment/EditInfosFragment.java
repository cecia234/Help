package it.units.ceschia.help.fragment;

import static it.units.ceschia.help.utility.ViewsUtility.getEditText;
import static it.units.ceschia.help.utility.ViewsUtility.getTextFromEditText;
import static it.units.ceschia.help.utility.ViewsUtility.setEditTextWithNullCheck;

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
import androidx.navigation.fragment.NavHostFragment;

import it.units.ceschia.help.R;
import it.units.ceschia.help.entity.User;
import it.units.ceschia.help.viewmodel.UserViewModel;


public class EditInfosFragment extends Fragment {

    UserViewModel userViewModel;

    public EditInfosFragment() {
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
        return inflater.inflate(R.layout.fragment_edit_infos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar myToolbar = getActivity().findViewById(R.id.toolbar_sign_up);
        myToolbar.setTitle(R.string.toolbar_edit_user_info);

        userViewModel.fetchUserInfos();

        setEditTextsValues();

        Button sendChangesButton = getView().findViewById(R.id.button_edit_user_info_confirm_changes);
        sendChangesButton.setOnClickListener(v-> sendChanges());
    }

    private void setEditTextsValues(){
        EditText nameEditText= getEditText(getView(),R.id.edit_text_edit_user_info_name);
        EditText surnameEditText= getEditText(getView(),R.id.edit_text_edit_user_info_surname);
        EditText telephoneEditText= getEditText(getView(),R.id.edit_text_edit_user_info_telephone);
        EditText countryEditText= getEditText(getView(),R.id.edit_text_edit_user_info_country);
        EditText cityEditText= getEditText(getView(),R.id.edit_text_edit_user_info_city);
        EditText addressEditText= getEditText(getView(),R.id.edit_text_edit_user_info_address);

        if(userViewModel.getUserInfoSpecific().getValue()!=null){
            userViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
                setEditTextWithNullCheck(nameEditText, user.getName());
                setEditTextWithNullCheck(surnameEditText, user.getSurname());
                setEditTextWithNullCheck(telephoneEditText, user.getTelephone());
                setEditTextWithNullCheck(countryEditText, user.getCountry());
                setEditTextWithNullCheck(cityEditText, user.getCity());
                setEditTextWithNullCheck(addressEditText, user.getAddress());

            });
        }
    }

    private void sendChanges(){
        View v = getView();
        String name = getTextFromEditText(v,R.id.edit_text_edit_user_info_name);
        String surname = getTextFromEditText(v,R.id.edit_text_edit_user_info_surname);
        String telephone = getTextFromEditText(v,R.id.edit_text_edit_user_info_telephone);
        String country = getTextFromEditText(v,R.id.edit_text_edit_user_info_country);
        String city = getTextFromEditText(v,R.id.edit_text_edit_user_info_city);
        String address = getTextFromEditText(v,R.id.edit_text_edit_user_info_address);

        User newUser = new User(name,surname,userViewModel.getUser().getValue().getEmail(),telephone,country,city,address);

        userViewModel.editUserInfos(newUser).observe(requireActivity(), result -> {
            if (result.success) {
                NavHostFragment.findNavController(this).popBackStack();
                Toast.makeText(getContext(), getString(R.string.result_edit_success), Toast.LENGTH_SHORT).show();
            } else {Toast.makeText(getContext(), getString(R.string.result_edit_failed), Toast.LENGTH_SHORT).show();
            }
        });
    }
}