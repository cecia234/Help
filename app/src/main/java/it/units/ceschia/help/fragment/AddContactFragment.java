package it.units.ceschia.help.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.units.ceschia.help.R;
import it.units.ceschia.help.viewmodel.UserViewModel;


public class AddContactFragment extends DialogFragment {


    public static final String TAG = "CIAO";

    public AddContactFragment() {
    }

    public static AddContactFragment newInstance(String title) {
        AddContactFragment frag = new AddContactFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_information, container);
    }





}