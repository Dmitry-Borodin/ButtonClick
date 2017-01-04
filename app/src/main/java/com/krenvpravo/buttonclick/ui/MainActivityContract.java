package com.krenvpravo.buttonclick.ui;

/**
 * @author Dmitry Borodin on 2017-01-04.
 */

public class MainActivityContract {

    interface View {
        void paintButtonRed();

        void paintButtonBlue();

        void showError(String errorMessage);
    }

    interface Presenter {
        void onViewStarted();

        void onViewStopped();

        void onViewDestroyed();

        void onButtonColorClicked();
    }
}