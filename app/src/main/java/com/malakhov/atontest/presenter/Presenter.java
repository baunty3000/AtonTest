package com.malakhov.atontest.presenter;

import com.malakhov.atontest.model.Model;
import com.malakhov.atontest.model.VKUser;
import com.malakhov.atontest.view.FriendsFragment;
import com.malakhov.atontest.view.LoginFragment;
import com.malakhov.atontest.view.PhotoFragment;
import com.malakhov.atontest.view.WelcomeActivity;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

import android.os.Bundle;
import android.util.Log;

import static com.malakhov.atontest.view.WelcomeActivity.TAG_LOGGED_IN;
import static com.malakhov.atontest.view.WelcomeActivity.TAG_LOGIN;
import static com.malakhov.atontest.view.WelcomeActivity.TAG_PHOTO;
import static com.malakhov.atontest.view.WelcomeActivity.TAG_WELCOME;

public class Presenter {
    private WelcomeActivity mView;
    private Model mModel;
    private String TAG = "info";
    public static final String FIRST_NAME = "FIRST_NAME";
    public static final String IMAGE_URL = "IMAGE_URL";

    public Presenter(Model model) {
        mModel = model;
    }

    public void attachView(WelcomeActivity usersActivity) {
        mView = usersActivity;
    }

    public void detachView() {
        mView = null;
    }

    public void loadFragmentData(String key, Object object) {
        switch (key){
            case TAG_WELCOME:
                mView.showFragment(new LoginFragment(), TAG_WELCOME, false);
                break;
            case TAG_LOGGED_IN:
                mView.showFragment(new FriendsFragment(), TAG_LOGIN,false);
                loadFriends();
                break;
            case TAG_LOGIN:
                VKSdk.login(mView, VKScope.PHOTOS);
                mView.showFragment(new FriendsFragment(), TAG_LOGIN,false);
                break;
            case TAG_PHOTO:
                VKUser friend = (VKUser) object;
                PhotoFragment photoFragment = new PhotoFragment();
                Bundle bundle = new Bundle();
                bundle.putString(FIRST_NAME, friend.getFirstName());
                bundle.putString(IMAGE_URL, friend.getPhotoOrig());
                photoFragment.setArguments(bundle);
                mView.showFragment(photoFragment, TAG_PHOTO, true);
                break;
        }
    }

    public void loadFriends() {
        mModel.loadFriends();
        mModel.setLoadFriendsCallback(friendList -> mView.showFriends(friendList));
    }

}
