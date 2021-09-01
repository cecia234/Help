package it.units.ceschia.help.fragment;

import static it.units.ceschia.help.utility.ViewsUtility.getEditText;
import static it.units.ceschia.help.utility.ViewsUtility.getTextFromEditText;
import static it.units.ceschia.help.utility.ViewsUtility.setEditTextWithNullCheck;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import it.units.ceschia.help.R;
import it.units.ceschia.help.entity.Contact;
import it.units.ceschia.help.entity.GenericResult;
import it.units.ceschia.help.viewmodel.UserViewModel;

public class EditContactDialog extends DialogFragment {


    public static final String TAG = "CIAO";
    UserViewModel userViewModel;
    private Toolbar toolbar;
    private Contact contact;


    public EditContactDialog() {
        // Required empty public constructor
    }
    public EditContactDialog( Contact contact) {
        // Required empty public constructor
        this.contact = contact;
    }

    public static EditContactDialog display(FragmentManager fragmentManager, Contact c) {
        EditContactDialog dialog = new EditContactDialog(c);
        dialog.show(fragmentManager, TAG);
        return dialog;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_add_contact, container, false);

        toolbar = view.findViewById(R.id.toolbar_edit_contacts);
        return view;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setItemInfos(view);

        Button saveInfo = view.findViewById(R.id.button_add_contact);
        saveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendChanges(view);
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    private void setItemInfos(View v){
        int[] ids = {R.id.edit_text_add_contact_name,R.id.edit_text_add_contact_surname,R.id.edit_text_add_contact_nick,R.id.edit_text_add_contact_phone,R.id.edit_text_add_contact_email,R.id.edit_text_add_contact_message};
        String[] textToSet = {contact.getName(),contact.getSurname(),contact.getNick(),contact.getPhone(),contact.getMail(),contact.getMessage()};
        for(int i = 0;i< ids.length;i++){
            setEditTextWithNullCheck(getEditText(v,ids[i]),textToSet[i]);
        }
    }

    private boolean sendChanges(View view){
        String name = getTextFromEditText(view,R.id.edit_text_add_contact_name);
        String surname = getTextFromEditText(view,R.id.edit_text_add_contact_surname);
        String nick = getTextFromEditText(view,R.id.edit_text_add_contact_nick);
        String phone = getTextFromEditText(view,R.id.edit_text_add_contact_phone);
        String mail = getTextFromEditText(view,R.id.edit_text_add_contact_email);
        String message = getTextFromEditText(view,R.id.edit_text_add_contact_message);

        Contact newContact = new Contact(name,surname,phone,mail,null,nick,message);
        newContact.setFbId(contact.getFbId());

        userViewModel.editContact(newContact).observe(requireActivity(), (Observer<GenericResult>) result -> {
            if (result.success) {
                Log.i("echo","Edit succeded");
                Toast.makeText(getContext(),"Edit Succeded",Toast.LENGTH_LONG).show();
            } else {
                //showErrorMessage();
                Log.i("echo","Edit Failed");
                Toast.makeText(getContext(),"Edit Failed",Toast.LENGTH_LONG).show();
            }
        });




        return false;
    }


}