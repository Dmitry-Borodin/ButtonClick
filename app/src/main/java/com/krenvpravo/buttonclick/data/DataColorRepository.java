package com.krenvpravo.buttonclick.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.krenvpravo.buttonclick.utils.Constants;

/**
 * @author Dmitry Borodin on 2017-01-04.
 */

public class DataColorRepository {

    private static DataColorRepository instance;

    private final DatabaseReference refButtonColor;
    @Nullable
    private Callback callback;   //instance of presenter listening for changes in DB
    private ValueEventListener eventListener; //local class that is listens for DB changes

    private DataColorRepository() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        refButtonColor = database.getReference(Constants.FIREBASE_KEY_BUTTON_IS_RED);
    }

    public static DataColorRepository getInstance() {
        if (instance == null) {
            instance = new DataColorRepository();
        }
        return instance;
    }

    public void setButtonColorRed() {
        refButtonColor.setValue(true);
    }

    public void setButtonColorBlue() {
        refButtonColor.setValue(false);
    }

    public void registerListener(final Callback callback) {
        this.callback = callback;
        prepareEventListener();
        //reattach every time we add callback - we will get last value callback immediately
        refButtonColor.addValueEventListener(eventListener);
    }

    private void prepareEventListener() {
        if (eventListener != null) {
            refButtonColor.removeEventListener(eventListener);
        }else {
            eventListener = getValueEventListener();
        }
    }

    @NonNull
    private ValueEventListener getValueEventListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean newValue = dataSnapshot.getValue(Boolean.class);
                if (callback != null) {
                    callback.onButtinColorChanged(newValue);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (callback != null) {
                    callback.onButtonColorResolveError(databaseError);
                }
            }
        };
    }

    public void unregisterCallback(Callback callback) {
        if (this.callback == callback) {
            this.callback = null;
        }
    }

    public interface Callback {
        void onButtinColorChanged(@Nullable Boolean isRed);
        void onButtonColorResolveError(DatabaseError error);
    }

}
