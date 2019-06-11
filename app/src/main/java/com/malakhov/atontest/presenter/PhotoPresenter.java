package com.malakhov.atontest.presenter;

import com.malakhov.atontest.view.PhotoFragment;

public class PhotoPresenter {

    private PhotoFragment mView;

    public void attachView(PhotoFragment photoFragment) {
        mView = photoFragment;
    }

    public void detachView() {
        mView = null;
    }

    public void loadFriend() {
        mView.showFriend();
    }
}
