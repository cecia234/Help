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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import it.units.ceschia.help.R;
import it.units.ceschia.help.entity.Application;
import it.units.ceschia.help.entity.Contact;
import it.units.ceschia.help.entity.GenericResult;
import it.units.ceschia.help.reciclerview.adapter.ContactListAdapter;
import it.units.ceschia.help.reciclerview.adapter.PrioritiesAdapter;
import it.units.ceschia.help.utility.ItemTouchHelperAdapter;
import it.units.ceschia.help.utility.RecyclerViewDragAndDropHelper;
import it.units.ceschia.help.viewmodel.UserViewModel;

public class EditContactDialog extends DialogFragment {


    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    public static final String TAG = "CIAO";
    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }


    UserViewModel userViewModel;
    protected RecyclerView mRecyclerView;
    protected PrioritiesAdapter mAdapter;
    private Toolbar toolbar;
    private Contact contact;


    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView.LayoutManager mLayoutManager;





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
        if(mAdapter==null){
            mAdapter = new PrioritiesAdapter();
        }
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

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_edit_priorities);
         mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
        mAdapter.setPriorities(contact.getPriorities());
        mAdapter.notifyDataSetChanged();

        mRecyclerView.setAdapter(mAdapter);
        ItemTouchHelper.Callback callback =
                new RecyclerViewDragAndDropHelper(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);



        setItemInfos(view);

        Button saveInfo = view.findViewById(R.id.button_add_contact);
        saveInfo.setText(R.string.button_save_changes_generic);
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

        ArrayList<Application> prioritiesList = mAdapter.getPriorities();
        HashMap<String,Application> priorities = new HashMap<>();

        for (int i=0;i< prioritiesList.size();i++) {
            priorities.put(Integer.toString(i+1),prioritiesList.get(i));
        }


        Contact newContact = new Contact(name,surname,phone,mail,null,nick,message,priorities);
        newContact.setFbId(contact.getFbId());

        userViewModel.editContact(newContact).observe(requireActivity(), (Observer<GenericResult>) result -> {
            if (result.success) {
                Toast.makeText(getContext(), getString(R.string.result_edit_success), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), getString(R.string.result_edit_failed), Toast.LENGTH_SHORT).show();
            }
        });




        return false;
    }


    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;


        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }


}