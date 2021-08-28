package it.units.ceschia.help.viewmodel;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import it.units.ceschia.help.MainActivity;
import it.units.ceschia.help.entity.LoginResult;
import it.units.ceschia.help.entity.SignupResult;
import it.units.ceschia.help.entity.User;

public class UserViewModel extends ViewModel {
    private MutableLiveData<User> user = new MutableLiveData<User>();
    private MutableLiveData<FirebaseAuth> mAuth = new MutableLiveData<FirebaseAuth>();
    private MutableLiveData<FirebaseUser> firebaseUser = new MutableLiveData<FirebaseUser>();
    private MutableLiveData<FirebaseFirestore> firebaseFirestore = new MutableLiveData<FirebaseFirestore>();


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

    public void setFirebaseUser() {
        this.firebaseUser.setValue(mAuth.getValue().getCurrentUser());
    }

    public MutableLiveData<FirebaseFirestore> getFirebaseFirestore() {
        return firebaseFirestore;
    }

    public void setFirebaseFirestore(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore.setValue(firebaseFirestore);
    }

    public MutableLiveData<SignupResult> signUpUser(User user, String password) {
        MutableLiveData<SignupResult> resultMutableLiveData = new MutableLiveData<SignupResult>();
        String email = user.getEmail();
        String pw = password;
        FirebaseAuth auth = mAuth.getValue();
        auth.createUserWithEmailAndPassword(email, pw)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success
                            Log.d(TAG, "createUserWithEmail:success");
                            setFirebaseUser();
                            String uid = firebaseUser.getValue().getUid();
                            FirebaseFirestore db = firebaseFirestore.getValue();
                            //try to add user to db
                            db.collection("users").document(uid).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //if success, return true
                                    resultMutableLiveData.setValue(new SignupResult(true));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //if failure, delete currently created user and return false
                                    Log.w(TAG, "Error writing document", e);
                                    firebaseUser.getValue().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User account deleted.");
                                            }
                                        }
                                    });
                                    resultMutableLiveData.setValue(new SignupResult(false));
                                }
                            });
                        } else {
                            // If sign up fails, return false.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            resultMutableLiveData.setValue(new SignupResult(false));
                        }
                    }
                });
        return resultMutableLiveData;
    }

    public MutableLiveData<LoginResult> login(String email, String password) {
        FirebaseAuth auth = mAuth.getValue();
        MutableLiveData<LoginResult> resultMutableLiveData = new MutableLiveData<LoginResult>();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");

                            setFirebaseUser();
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


    public void fetchUserInfos(){
        if(firebaseUser.getValue()==null) return;
        DocumentReference docRef = firebaseFirestore.getValue().collection("users").document(firebaseUser.getValue().getUid());

        Source source = Source.DEFAULT;

        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    // Document found in the offline cache
                    DocumentSnapshot document = task.getResult();
                    setUser(document.toObject(User.class));
                }
            }
        });
        return;
    }


}
