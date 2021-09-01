package it.units.ceschia.help.fragment;

import static it.units.ceschia.help.utility.ViewsUtility.getTextFromEditText;

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

import java.util.ArrayList;

import it.units.ceschia.help.R;
import it.units.ceschia.help.entity.Contact;
import it.units.ceschia.help.entity.GenericResult;
import it.units.ceschia.help.reciclerview.adapter.EditContactListAdapter;
import it.units.ceschia.help.viewmodel.UserViewModel;


public class AddContactDialog extends DialogFragment {


    public static final String TAG = "CIAO";
    UserViewModel userViewModel;
    protected EditContactListAdapter mAdapter;
    protected ArrayList<Contact> mDataset;
    private Toolbar toolbar;

    public AddContactDialog() {
    }

    public AddContactDialog(EditContactListAdapter mAdapter,ArrayList<Contact> mDataset) {
        // Required empty public constructor
        this.mAdapter = mAdapter;
        this.mDataset = mDataset;
    }

    public static AddContactDialog display(FragmentManager fragmentManager, EditContactListAdapter mAdapter, ArrayList<Contact> mDataset) {
        AddContactDialog dialog = new AddContactDialog(mAdapter,mDataset);
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
    public void onViewCreated(View viewa, Bundle savedInstanceState) {
        super.onViewCreated(viewa, savedInstanceState);

        Button saveInfo = viewa.findViewById(R.id.button_add_contact);
        saveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendChanges(viewa);
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

    private boolean sendChanges(View view){
        String name = getTextFromEditText(view,R.id.edit_text_add_contact_name);
        String surname = getTextFromEditText(view,R.id.edit_text_add_contact_surname);
        String nick = getTextFromEditText(view,R.id.edit_text_add_contact_nick);
        String phone = getTextFromEditText(view,R.id.edit_text_add_contact_phone);
        String mail = getTextFromEditText(view,R.id.edit_text_add_contact_email);
        String message = getTextFromEditText(view,R.id.edit_text_add_contact_message);

        Contact contact = new Contact(name,surname,phone,mail,null,nick,message);

        userViewModel.addContact(contact).observe(requireActivity(), (Observer<GenericResult>) result -> {
            if (result.success) {
                Log.i("echo","Edit succeded");

            } else {
                //showErrorMessage();
                Log.i("echo","Edit Failed");
            }
        });




        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        userViewModel.fetchUserContacts().observe(requireActivity(),(Observer<? super GenericResult>) res->{
            if (res.success){
                Log.i("echo","updating UUI");
                mAdapter.setLocalDataSet(userViewModel.getUserContacts().getValue().getContacts());
                mAdapter.notifyDataSetChanged();}
        });
    }
}