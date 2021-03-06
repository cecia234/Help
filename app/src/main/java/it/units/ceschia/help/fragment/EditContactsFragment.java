package it.units.ceschia.help.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import it.units.ceschia.help.R;
import it.units.ceschia.help.entity.Contact;
import it.units.ceschia.help.entity.UserContact;
import it.units.ceschia.help.reciclerview.adapter.EditContactListAdapter;
import it.units.ceschia.help.viewmodel.UserViewModel;

public class EditContactsFragment extends Fragment {

    private static final String KEY_LAYOUT_MANAGER = "layoutManager";

    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }

    private UserViewModel userViewModel;

    protected LayoutManagerType mCurrentLayoutManagerType;

    protected RecyclerView mRecyclerView;
    protected EditContactListAdapter mAdapter;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_contacts, container, false);

        initDataset();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar myToolbar = getActivity().findViewById(R.id.toolbar_edit_contacts);
        myToolbar.setTitle(R.string.toolbar_edit_contacts);
        FloatingActionButton fab = view.findViewById(R.id.float_action_button);
        fab.setOnClickListener(view1 -> {
            FragmentManager fm = requireActivity().getSupportFragmentManager();
            AddContactDialog.display(fm,mAdapter,mDataset);
        });

        // BEGIN_INCLUDE(initializeRecyclerView)
        mRecyclerView = view.findViewById(R.id.recycler_view_edit_contacts);

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
        setRecyclerViewLayoutManager();



        if (mAdapter != null) // it works second time and later
            mAdapter.notifyDataSetChanged(); //TODO: improve cases
        else { // it works first time
            mAdapter = new EditContactListAdapter(mDataset);
            mRecyclerView.setAdapter(mAdapter);
        }



    }

    /**
     * Set RecyclerView's LayoutManager to the one given.
     *
     */
    public void setRecyclerViewLayoutManager() {
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
            Toast.makeText(getContext(),"error", Toast.LENGTH_SHORT).show();
        }
    }
}