package it.units.ceschia.help.viewmodel;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import it.units.ceschia.help.MainActivity;
import it.units.ceschia.help.entity.LoginResult;
import it.units.ceschia.help.entity.User;

public class UserViewModel extends ViewModel {
    private MutableLiveData<User> user = new MutableLiveData<User>();
    private MutableLiveData<FirebaseAuth> mAuth= new MutableLiveData<FirebaseAuth>();
    private MutableLiveData<FirebaseUser> firebaseUser= new MutableLiveData<FirebaseUser>();

    public UserViewModel() {
        this.user.setValue(null);
        this.mAuth.setValue(null);
        this.firebaseUser.setValue(null);
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user.setValue(user);
    }

    public MutableLiveData<FirebaseAuth> getmAuth() {
        return mAuth;
    }

    public void setmAuth(FirebaseAuth mAuth) {
        this.mAuth.setValue(mAuth);
    }

    public MutableLiveData<FirebaseUser> getFirebaseUser() {
        return firebaseUser;
    }

    public void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser.setValue(firebaseUser);
    }

    public void signUpUser(User user,String password){
        String email = user.getEmail();
        String pw = password;
        FirebaseAuth auth = mAuth.getValue();
        auth.createUserWithEmailAndPassword(email, pw)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            setFirebaseUser(auth.getCurrentUser());
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                            //updateUI(null);
                        }
                    }
                });
    }

    public MutableLiveData<LoginResult> login(String email,String password){
        FirebaseAuth auth = mAuth.getValue();
        MutableLiveData<LoginResult> resultMutableLiveData = new MutableLiveData<LoginResult>();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");

                            setFirebaseUser(auth.getCurrentUser());
                            resultMutableLiveData.setValue(new LoginResult(true));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            resultMutableLiveData.setValue(new LoginResult(false));
                        }
                    }
                });
        return resultMutableLiveData;
    }


}
