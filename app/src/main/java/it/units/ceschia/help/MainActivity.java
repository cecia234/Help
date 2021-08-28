package it.units.ceschia.help;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import it.units.ceschia.help.databinding.ActivityMainBinding;
import it.units.ceschia.help.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private UserViewModel userViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.setFirebaseFirestore(db);
        userViewModel.setmAuth(mAuth);
        userViewModel.setFirebaseUser();

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_action_settings:
                navController.navigate(R.id.settingsFragment);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void logOut(View view){
        mAuth.signOut();
        userViewModel.setFirebaseUser();
    }
}