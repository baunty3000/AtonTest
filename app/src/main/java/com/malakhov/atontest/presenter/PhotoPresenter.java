package com.malakhov.atontest.presenter;

import com.malakhov.atontest.model.LoadPhotoFriendCallback;
import com.malakhov.atontest.model.Model;
import com.malakhov.atontest.view.PhotoFragment;

public class PhotoPresenter {

    private PhotoFragment mView;

    public void attachView(PhotoFragment photoFragment) {
        mView = photoFragment;
    }

    public void detachView() {
        mView = null;
    }

    public void loadPhotoFriend(String url) {
        LoadPhotoFriendCallback loadPhotoFriendCallback = bitmap -> mView.showFriend(bitmap);
        Model.getInstance().getPhotoFriend(url, loadPhotoFriendCallback);
    }
}
