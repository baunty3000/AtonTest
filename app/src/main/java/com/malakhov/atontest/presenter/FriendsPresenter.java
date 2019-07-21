package com.malakhov.atontest.presenter;

import com.malakhov.atontest.model.Model;
import com.malakhov.atontest.view.FriendsFragment;

public class FriendsPresenter{

    private FriendsFragment mView;

    public void attachView(FriendsFragment friendsFragment) {
        mView = friendsFragment;
    }

    public void detachView() {
        mView = null;
    }

    public void loadFriends() {
        Model.getInstance().loadFriends(friendList -> mView.showFriends(friendList), () -> mView.showError());
    }
}
