package com.krenvpravo.buttonclick.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DatabaseError;
import com.krenvpravo.buttonclick.data.DataColorRepository;

/**
 * @author Dmitry Borodin on 2017-01-04.
 */

public class MainActivityPresenter implements MainActivityContract.Presenter {

    @Nullable
    private MainActivityContract.View view;
    private DataColorRepository.Callback repositoryCallback;
    private DataColorRepository repository;
    private boolean isRed;


    public MainActivityPresenter(final MainActivityContract.View view) {
        this.view = view;
        repositoryCallback = getRepositoryCallback();
        repository = DataColorRepository.getInstance();
    }

    @Override
    public void onViewStarted() {
        repository.registerListener(repositoryCallback);
    }

    @Override
    public void onViewStopped() {
        repository.unregisterCallback(repositoryCallback);
    }

    @Override
    public void onViewDestroyed() {
        view = null;
    }

    @Override
    public void onButtonColorClicked() {
        if (isRed) {
            repository.setButtonColorBlue();
        } else {
            repository.setButtonColorRed();
        }
    }


    @NonNull
    private DataColorRepository.Callback getRepositoryCallback() {
        return new DataColorRepository.Callback() {
            @Override
            public void onButtinColorChanged(@Nullable Boolean isRedNow) {
                isRed = isRedNow == null ? true : isRedNow;
                if (view != null) {
                    if (isRed) {
                        view.paintButtonRed();
                    } else {
                        view.paintButtonBlue();
                    }
                }
            }

            @Override
            public void onButtonColorResolveError(DatabaseError error) {
                if (view != null) {
                    view.showError(error.getMessage());
                }
            }
        };
    }
}
