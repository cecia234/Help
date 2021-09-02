package it.units.ceschia.help.viewmodel;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.Date;

import it.units.ceschia.help.entity.Contact;
import it.units.ceschia.help.entity.GenericResult;
import it.units.ceschia.help.entity.HelpRequest;
import it.units.ceschia.help.entity.HelpRequestType;
import it.units.ceschia.help.entity.LoginResult;
import it.units.ceschia.help.entity.Position;
import it.units.ceschia.help.entity.SignupResult;
import it.units.ceschia.help.entity.User;
import it.units.ceschia.help.entity.UserContact;
import it.units.ceschia.help.entity.UserInfoSpecific;
import it.units.ceschia.help.reciclerview.adapter.ContactListAdapter;

public class UserViewModel extends ViewModel {
    private final MutableLiveData<User> user = new MutableLiveData<>();
    private final MutableLiveData<UserInfoSpecific> userInfoSpecific = new MutableLiveData<>();
    private final MutableLiveData<UserContact> userContacts = new MutableLiveData<>();
    private final MutableLiveData<FirebaseAuth> mAuth = new MutableLiveData<>();
    private final MutableLiveData<FirebaseUser> firebaseUser = new MutableLiveData<>();
    private final MutableLiveData<FirebaseFirestore> firebaseFirestore = new MutableLiveData<>();
    private final MutableLiveData<Position> position = new MutableLiveData<>();

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

    public MutableLiveData<UserInfoSpecific> getUserInfoSpecific() {
        return userInfoSpecific;
    }

    public void setUserInfoSpecific(UserInfoSpecific userInfoSpecific) {
        this.userInfoSpecific.setValue(userInfoSpecific);
    }

    public MutableLiveData<UserContact> getUserContacts() {
        return userContacts;
    }

    public void setUserContacts(UserContact userContacts) {
        this.userContacts.setValue(userContacts);
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

    public void setFirebaseFirestore(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore.setValue(firebaseFirestore);
    }

    public MutableLiveData<Position> getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position.setValue(position);
    }

    public MutableLiveData<SignupResult> signUpUser(User user, String password) {
        MutableLiveData<SignupResult> resultMutableLiveData = new MutableLiveData<>();
        String email = user.getEmail();
        FirebaseAuth auth = mAuth.getValue();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign up success
                        Log.d(TAG, "createUserWithEmail:success");
                        setFirebaseUser();
                        String uid = firebaseUser.getValue().getUid();
                        FirebaseFirestore db = firebaseFirestore.getValue();
                        //try to add user to db
                        db.collection("users").document(uid).set(user).addOnSuccessListener(aVoid -> {
                            //if success, return true
                            resultMutableLiveData.setValue(new SignupResult(true));
                        }).addOnFailureListener(e -> {
                            //if failure, delete currently created user and return false
                            Log.w(TAG, "Error writing document", e);
                            firebaseUser.getValue().delete().addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    Log.d(TAG, "User account deleted.");
                                }
                            });
                            resultMutableLiveData.setValue(new SignupResult(false));
                        });
                    } else {
                        // If sign up fails, return false.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        resultMutableLiveData.setValue(new SignupResult(false));
                    }
                });
        return resultMutableLiveData;
    }

    public MutableLiveData<LoginResult> login(String email, String password) {
        FirebaseAuth auth = mAuth.getValue();
        MutableLiveData<LoginResult> resultMutableLiveData = new MutableLiveData<>();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
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
                });
        return resultMutableLiveData;
    }

    public MutableLiveData<GenericResult> fetchUserInfos() {

        MutableLiveData<GenericResult> resultMutableLiveData = new MutableLiveData<>();
        if (firebaseUser.getValue() == null)
            resultMutableLiveData.setValue(new GenericResult(false));
        DocumentReference docRef = firebaseFirestore.getValue().collection("users").document(firebaseUser.getValue().getUid());

        Source source = Source.DEFAULT;

        docRef.get(source).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                setUser(document.toObject(User.class));
                resultMutableLiveData.setValue(new GenericResult(true));
            }
        });

        return resultMutableLiveData;
    }

    public MutableLiveData<GenericResult> fetchSpecificUserInfos() {

        MutableLiveData<GenericResult> resultMutableLiveData = new MutableLiveData<>();
        if (firebaseUser.getValue() == null)
            resultMutableLiveData.setValue(new GenericResult(false));
        DocumentReference docRef = firebaseFirestore.getValue().collection("specificInfos").document(firebaseUser.getValue().getUid());

        Source source = Source.DEFAULT;

        docRef.get(source).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Document found in the offline cache
                DocumentSnapshot document = task.getResult();
                setUserInfoSpecific(document.toObject(UserInfoSpecific.class));
                resultMutableLiveData.setValue(new GenericResult(true));

            } else {
                Log.d("echo", "Error getting documents: ", task.getException());
            }
        });

        return resultMutableLiveData;
    }

    public void fetchContactsContinue(final ContactListAdapter mAdapter, final RecyclerView mRecyclerView) {
        if (firebaseUser.getValue() == null) {
            return;
        }
        CollectionReference collectionReference = firebaseFirestore.getValue().collection("users/" + firebaseUser.getValue().getUid() + "/contact");
        ArrayList<Contact> contacts = new ArrayList<>();
        collectionReference.addSnapshotListener((value, e) -> {
            if (e != null) {
                Log.w(TAG, "Listen failed.", e);
                return;
            }

            for (QueryDocumentSnapshot document : value) {
                Contact contact = document.toObject(Contact.class);
                contacts.add(contact);

            }


            mAdapter.setLocalDataSet(contacts);
            mAdapter.setCurrentPos(position.getValue());
            mAdapter.notifyDataSetChanged();

            mRecyclerView.setAdapter(mAdapter);

        });
    }

    public MutableLiveData<GenericResult> fetchUserContacts() {
        MutableLiveData<GenericResult> resultMutableLiveData = new MutableLiveData<>();
        if (firebaseUser.getValue() == null)
            resultMutableLiveData.setValue(new GenericResult(false));

        CollectionReference collectionReference = firebaseFirestore.getValue().collection("users/" + firebaseUser.getValue().getUid() + "/contact");
        collectionReference.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<Contact> contacts = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Contact c = document.toObject(Contact.class);
                            c.setFbId(document.getId());
                            contacts.add(c);
                            Log.d("echo", c.toString());

                        }
                        setUserContacts(new UserContact(contacts));

                        resultMutableLiveData.setValue(new GenericResult(true));
                    } else {
                        Log.d("echo", "Error getting documents: ", task.getException());
                    }
                });
        return resultMutableLiveData;
    }

    public MutableLiveData<GenericResult> editUserInfos(User user) {
        MutableLiveData<GenericResult> resultMutableLiveData = new MutableLiveData<>();

        String uid = firebaseUser.getValue().getUid();

        FirebaseFirestore db = firebaseFirestore.getValue();

        db.collection("users").document(uid).set(user).addOnSuccessListener(aVoid -> {
            Log.i("echo", "Attempting write EDIT USER: " + user.toString());
            //if success, return true
            resultMutableLiveData.setValue(new GenericResult(true));
        }).addOnFailureListener(e -> {
            //if failure, return false
            Log.w(TAG, "Error writing document", e);
            resultMutableLiveData.setValue(new GenericResult(false));
        });

        return resultMutableLiveData;
    }

    public MutableLiveData<GenericResult> editUserSpecificInfos(UserInfoSpecific infos) {

        MutableLiveData<GenericResult> resultMutableLiveData = new MutableLiveData<>();

        String uid = firebaseUser.getValue().getUid();

        FirebaseFirestore db = firebaseFirestore.getValue();

        db.collection("specificInfos").document(uid).set(infos).addOnSuccessListener(aVoid -> {
            //if success, return true
            resultMutableLiveData.setValue(new GenericResult(true));
        }).addOnFailureListener(e -> {
            //if failure, return false
            Log.w(TAG, "Error writing document", e);
            resultMutableLiveData.setValue(new GenericResult(false));
        });

        return resultMutableLiveData;
    }

    public MutableLiveData<GenericResult> addContact(Contact contact) {

        MutableLiveData<GenericResult> resultMutableLiveData = new MutableLiveData<>();

        String uid = firebaseUser.getValue().getUid();

        FirebaseFirestore db = firebaseFirestore.getValue();

        db.collection("users/" + uid + "/contact").add(contact).addOnSuccessListener(documentReference -> resultMutableLiveData.setValue(new GenericResult(true)));

        return resultMutableLiveData;
    }

    public MutableLiveData<GenericResult> editContact(Contact contact) {
        MutableLiveData<GenericResult> resultMutableLiveData = new MutableLiveData<>();

        String uid = firebaseUser.getValue().getUid();

        FirebaseFirestore db = firebaseFirestore.getValue();

        db.collection("users/" + uid + "/contact").document(contact.getFbId()).update(
                "name", contact.getName(),
                "surname", contact.getSurname(),
                "nick", contact.getNick(),
                "mail", contact.getMail(),
                "phone", contact.getPhone(),
                "message", contact.getMessage(),
                "priorities", contact.getPriorities()
        ).addOnSuccessListener(aVoid -> {
            //if success, return true
            resultMutableLiveData.setValue(new GenericResult(true));
        }).addOnFailureListener(e -> {
            //if failure, return false
            Log.w(TAG, "Error writing document", e);
            resultMutableLiveData.setValue(new GenericResult(false));
        });

        return resultMutableLiveData;
    }

    public void sendHelpRequest(User user, Position position, HelpRequestType requestType) {
        MutableLiveData<GenericResult> resultMutableLiveData = new MutableLiveData<>();

        String uid = firebaseUser.getValue().getUid();

        FirebaseFirestore db = firebaseFirestore.getValue();

        Timestamp time = new Timestamp(new Date());

        HelpRequest request = new HelpRequest(uid, user, position, requestType, time, false);

        db.collection("helpRequests/").add(request).addOnSuccessListener(documentReference -> resultMutableLiveData.setValue(new GenericResult(true)));
    }


}
