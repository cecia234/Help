package it.units.ceschia.help.fragment;

import static it.units.ceschia.help.utility.ViewsUtility.getEditText;
import static it.units.ceschia.help.utility.ViewsUtility.getTextView;
import static it.units.ceschia.help.utility.ViewsUtility.setEditTextWithNullCheck;
import static it.units.ceschia.help.utility.ViewsUtility.setTextViewWithNullCheck;

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
import it.units.ceschia.help.entity.UserInfoSpecific;
import it.units.ceschia.help.viewmodel.UserViewModel;

public class ShowInformationFragment extends Fragment {
    UserViewModel userViewModel;

    public ShowInformationFragment() {
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
        return inflater.inflate(R.layout.fragment_show_information, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        userViewModel.getUser().observe(getViewLifecycleOwner(), (Observer<User>) user -> {
            setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_name_surname),user.getName() + " " + user.getSurname());
            setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_email),user.getEmail());
            setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_phone),user.getTelephone());
            setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_address),user.getAddress() + ", " + user.getCity() + ", " + user.getCountry());
        });

        if (userViewModel.getUserInfoSpecificMutableLiveData().getValue() != null) {
            userViewModel.getUserInfoSpecificMutableLiveData().observe(getViewLifecycleOwner(), (Observer<UserInfoSpecific>) userSpec -> {
                setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_allergies),userSpec.getAllergies());
                setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_diseases),userSpec.getDiseases());
                setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_vaccines),userSpec.getVaccines());
                setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_medicines),userSpec.getMedicines());
                setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_blood_type),userSpec.getBloodType());
                setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_rh),userSpec.getRh());
            });

        } else {
            setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_allergies),"missing information");
            setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_diseases),"missing information");
            setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_vaccines),"missing information");
            setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_medicines),"missing information");
            setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_blood_type),"missing information");
            setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_rh),"missing information");
        }
    }
}