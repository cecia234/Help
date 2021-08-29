package it.units.ceschia.help.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import it.units.ceschia.help.R;
import it.units.ceschia.help.entity.Contact;
import it.units.ceschia.help.entity.UserContact;
import it.units.ceschia.help.reciclerview.adapter.ContactListAdapter;
import it.units.ceschia.help.viewmodel.UserViewModel;

public class EditContactsFragment extends Fragment {

    private static final String KEY_LAYOUT_MANAGER = "layoutManager";

    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }

    private UserViewModel userViewModel;

    protected LayoutManagerType mCurrentLayoutManagerType;

    protected RecyclerView mRecyclerView;
    protected ContactListAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected ArrayList<Contact> mDataset;

    public EditContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        initDataset();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar myToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_edit_contacts);
        myToolbar.setTitle(R.string.toolbar_edit_contacts);
        myToolbar.inflateMenu(R.menu.menu_edit_contact);
        myToolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.toolbar_action_add_contact:
                    //show id to add contact
                    FragmentManager fm = requireActivity().getSupportFragmentManager();
                    AddContactFragment addContactDialogFragment = AddContactFragment.newInstance("Some Title");
                    addContactDialogFragment.show(fm, "fragment_edit_name");
                    return true;
                default:
                    // If we got here, the user's action was not recognized.
                    // Invoke the superclass to handle it.
                    return false;
            }
        });

        // BEGIN_INCLUDE(initializeRecyclerView)
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_edit_contacts);

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);


        mAdapter = new ContactListAdapter(mDataset);
        // Set ContactListAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // END_INCLUDE(initializeRecyclerView)


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_contacts, container, false);


        return view;
    }

    /**
     * Set RecyclerView's LayoutManager to the one given.
     *
     * @param layoutManagerType Type of layout manager to switch to.
     */
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

    /**
     * Generates Strings for RecyclerView's adapter. This data would usually come
     * from a local content provider or remote server.
     */
    private void initDataset() {
        // Initialize dataset
        UserContact userContact = userViewModel.getUserContacts().getValue();
        if (userContact != null) {
            mDataset = userContact.getContacts();
        } else {
            Log.i("echo", "userContact null!!!");
        }
    }
}