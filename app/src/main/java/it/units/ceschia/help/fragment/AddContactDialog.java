package it.units.ceschia.help.fragment;

import static it.units.ceschia.help.utility.ViewsUtility.getTextFromEditText;

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
import it.units.ceschia.help.reciclerview.adapter.EditContactListAdapter;
import it.units.ceschia.help.reciclerview.adapter.PrioritiesAdapter;
import it.units.ceschia.help.utility.RecyclerViewDragAndDropHelper;
import it.units.ceschia.help.viewmodel.UserViewModel;


public class AddContactDialog extends DialogFragment {

    private static final String KEY_LAYOUT_MANAGER = "layoutManager";

    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView.LayoutManager mLayoutManager;


    public static final String TAG = "CIAO";
    UserViewModel userViewModel;
    protected EditContactListAdapter mAdapter;
    protected PrioritiesAdapter mAdapterPriorities;
    protected RecyclerView mRecyclerView;
    protected ArrayList<Contact> mDataset;
    private Toolbar toolbar;

    public AddContactDialog() {
    }

    public AddContactDialog(EditContactListAdapter mAdapter, ArrayList<Contact> mDataset) {
        // Required empty public constructor

        this.mAdapter = mAdapter;
        this.mDataset = mDataset;
    }

    public static AddContactDialog display(FragmentManager fragmentManager, EditContactListAdapter mAdapter, ArrayList<Contact> mDataset) {
        AddContactDialog dialog = new AddContactDialog(mAdapter, mDataset);
        dialog.show(fragmentManager, TAG);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        if (mAdapterPriorities == null) {
            mAdapterPriorities = new PrioritiesAdapter();
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
    public void onViewCreated(View viewa, Bundle savedInstanceState) {
        super.onViewCreated(viewa, savedInstanceState);
        mRecyclerView = (RecyclerView) viewa.findViewById(R.id.recycler_view_edit_priorities);
        mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        HashMap<String, Application> defaultPriorities = new HashMap<>();
        defaultPriorities.put("1", Application.WHATSAPP);
        defaultPriorities.put("2", Application.SMS);
        defaultPriorities.put("3", Application.TELEGRAM);
        defaultPriorities.put("4", Application.CALL);

        mAdapterPriorities.setPriorities(defaultPriorities);
        mAdapterPriorities.notifyDataSetChanged();

        mRecyclerView.setAdapter(mAdapterPriorities);
        ItemTouchHelper.Callback callback =
                new RecyclerViewDragAndDropHelper(mAdapterPriorities);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);


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

    private boolean sendChanges(View view) {
        String name = getTextFromEditText(view, R.id.edit_text_add_contact_name);
        String surname = getTextFromEditText(view, R.id.edit_text_add_contact_surname);
        String nick = getTextFromEditText(view, R.id.edit_text_add_contact_nick);
        String phone = getTextFromEditText(view, R.id.edit_text_add_contact_phone);
        String mail = getTextFromEditText(view, R.id.edit_text_add_contact_email);
        String message = getTextFromEditText(view, R.id.edit_text_add_contact_message);

        ArrayList<Application> prioritiesList = mAdapterPriorities.getPriorities();
        HashMap<String, Application> priorities = new HashMap<>();

        for (int i = 0; i < prioritiesList.size(); i++) {
            priorities.put(Integer.toString(i + 1), prioritiesList.get(i));
        }

        Contact contact = new Contact(name, surname, phone, mail, null, nick, message, priorities);

        userViewModel.addContact(contact).observe(requireActivity(), (Observer<GenericResult>) result -> {
            if (result.success) {
                Toast.makeText(getContext(), getString(R.string.result_add_success), Toast.LENGTH_SHORT).show();
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        userViewModel.fetchUserContacts().observe(requireActivity(), (Observer<? super GenericResult>) res -> {
            if (res.success) {
                mAdapter.setLocalDataSet(userViewModel.getUserContacts().getValue().getContacts());
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}