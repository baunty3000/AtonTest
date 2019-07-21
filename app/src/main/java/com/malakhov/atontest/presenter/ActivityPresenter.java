package com.malakhov.atontest.presenter;

import com.malakhov.atontest.R;
import com.malakhov.atontest.model.VKUser;
import com.malakhov.atontest.view.FriendsFragment;
import com.malakhov.atontest.view.LoginFragment;
import com.malakhov.atontest.view.PhotoFragment;
import com.malakhov.atontest.view.WelcomeActivity;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import android.os.Bundle;
import androidx.test.espresso.idling.CountingIdlingResource;
import static com.malakhov.atontest.view.WelcomeActivity.TAG_LOGGED_IN;
import static com.malakhov.atontest.view.WelcomeActivity.TAG_LOGIN;
import static com.malakhov.atontest.view.WelcomeActivity.TAG_PHOTO;
import static com.malakhov.atontest.view.WelcomeActivity.TAG_WELCOME;

public class ActivityPresenter {

    public static final String KEY_FIO = "KEY_FIO";
    public static final String KEY_IMAGE_URL = "KEY_IMAGE_URL";
    private WelcomeActivity mView;
    private CountingIdlingResource mCountingIdlingResource;

    public void attachView(WelcomeActivity usersActivity) {
        mView = usersActivity;
    }

    public void detachView() {
        mView = null;
    }

    public void setCountingIdlingResource(CountingIdlingResource countingIdlingResource) {
        mCountingIdlingResource = countingIdlingResource;
    }

    public void loadFragmentData(String key, Object object) {
        switch (key){
            case TAG_WELCOME:
                mView.showFragment(new LoginFragment(), TAG_WELCOME, false);
                break;
            case TAG_LOGIN:
                mCountingIdlingResource.increment();
                VKSdk.login(mView, VKScope.PHOTOS);
                break;
            case TAG_LOGGED_IN:
                mView.showFragment(new FriendsFragment(), TAG_LOGIN,false);
                break;
            case TAG_PHOTO:
                VKUser friend = (VKUser) object;
                PhotoFragment photoFragment = new PhotoFragment();
                Bundle bundle = new Bundle();
                bundle.putString(KEY_FIO, friend.getLastName() + " " + friend.getFirstName());
                bundle.putString(KEY_IMAGE_URL, friend.getPhotoOrig());
                photoFragment.setArguments(bundle);
                mView.showFragment(photoFragment, TAG_PHOTO, true);
                break;
            default: throw new IllegalArgumentException(mView.getString(R.string.error_no_id)+": "+key);
        }
    }
}
