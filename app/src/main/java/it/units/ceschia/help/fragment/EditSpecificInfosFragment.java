package it.units.ceschia.help.fragment;

import static it.units.ceschia.help.utility.ViewsUtility.getEditText;
import static it.units.ceschia.help.utility.ViewsUtility.getTextFromEditText;
import static it.units.ceschia.help.utility.ViewsUtility.setEditTextWithNullCheck;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import it.units.ceschia.help.R;
import it.units.ceschia.help.entity.GenericResult;
import it.units.ceschia.help.entity.UserInfoSpecific;
import it.units.ceschia.help.viewmodel.UserViewModel;

public class EditSpecificInfosFragment extends Fragment {

    UserViewModel userViewModel;

    public EditSpecificInfosFragment() {
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
        return inflater.inflate(R.layout.fragment_edit_specific_infos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setEditTextsValues();

        Button sendChangesButton = (Button) getView().findViewById(R.id.button_user_info_specific_save_changes);
        sendChangesButton.setOnClickListener(v->{
            sendChanges();
        });
    }

    private void setEditTextsValues(){
        EditText allergiesEditText= getEditText(getView(),R.id.edit_text_edit_spec_infos_allergies);
        EditText diseasesEditText= getEditText(getView(),R.id.edit_text_edit_spec_infos_diseases);
        EditText vaccinesEditText= getEditText(getView(),R.id.edit_text_edit_spec_infos_vaccines);
        EditText medicinesEditText= getEditText(getView(),R.id.edit_text_edit_spec_infos_medicines);
        EditText bloodTypeEditText= getEditText(getView(),R.id.edit_text_edit_spec_infos_blood_type);
        EditText rhEditText= getEditText(getView(),R.id.edit_text_edit_spec_infos_rh);

        if(userViewModel.getUserInfoSpecific().getValue()!=null){
            userViewModel.getUserInfoSpecific().observe(getViewLifecycleOwner(), (Observer<UserInfoSpecific>) infos -> {
                setEditTextWithNullCheck(allergiesEditText,infos.getAllergies());
                setEditTextWithNullCheck(diseasesEditText,infos.getDiseases());
                setEditTextWithNullCheck(vaccinesEditText,infos.getVaccines());
                setEditTextWithNullCheck(medicinesEditText,infos.getMedicines());
                setEditTextWithNullCheck(bloodTypeEditText,infos.getBloodType());
                setEditTextWithNullCheck(rhEditText,infos.getRh());
            });
        }
    }

    private void sendChanges(){
        View v = getView();
        String allergies = getTextFromEditText(v,R.id.edit_text_edit_spec_infos_allergies);
        String diseases = getTextFromEditText(v,R.id.edit_text_edit_spec_infos_diseases);
        String vaccines = getTextFromEditText(v,R.id.edit_text_edit_spec_infos_vaccines);
        String medicines = getTextFromEditText(v,R.id.edit_text_edit_spec_infos_medicines);
        String bloodType = getTextFromEditText(v,R.id.edit_text_edit_spec_infos_blood_type);
        String rh = getTextFromEditText(v,R.id.edit_text_edit_spec_infos_rh);

        UserInfoSpecific newInfoSpecific = new UserInfoSpecific(allergies,diseases,vaccines,medicines,bloodType,rh);

        userViewModel.editUserSpecificInfos(newInfoSpecific).observe(requireActivity(), (Observer<GenericResult>) result -> {
            if (result.success) {
                NavHostFragment.findNavController(this).popBackStack();
            } else {
                //showErrorMessage();
                Log.i("echo","Edit specific infos Failed");
            }
        });
    }
}