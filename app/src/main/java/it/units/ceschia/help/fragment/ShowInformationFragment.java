package it.units.ceschia.help.fragment;

import static it.units.ceschia.help.utility.ViewsUtility.getTextView;
import static it.units.ceschia.help.utility.ViewsUtility.setTextViewWithNullCheck;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import it.units.ceschia.help.R;
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


        userViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_name_surname),user.getName() + " " + user.getSurname());
            setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_email),user.getEmail());
            setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_phone),user.getTelephone());
            setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_address),user.getAddress() + ", " + user.getCity() + ", " + user.getCountry());
        });

        if (userViewModel.getUserInfoSpecific().getValue() != null) {
            userViewModel.getUserInfoSpecific().observe(getViewLifecycleOwner(), userSpec -> {
                setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_allergies),userSpec.getAllergies());
                setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_diseases),userSpec.getDiseases());
                setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_vaccines),userSpec.getVaccines());
                setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_medicines),userSpec.getMedicines());
                setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_blood_type),userSpec.getBloodType());
                setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_rh),userSpec.getRh());
            });

        } else {
            String missingInfoText = getString(R.string.missing_info);
            setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_allergies),missingInfoText);
            setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_diseases),missingInfoText);
            setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_vaccines),missingInfoText);
            setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_medicines),missingInfoText);
            setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_blood_type),missingInfoText);
            setTextViewWithNullCheck(getTextView(view, R.id.text_view_show_info_rh),missingInfoText);
        }
    }
}