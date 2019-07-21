package com.malakhov.atontest.view;

import com.malakhov.atontest.R;
import com.malakhov.atontest.common.ClickFriendCallBack;
import com.malakhov.atontest.model.VKUser;
import com.malakhov.atontest.presenter.ActivityPresenter;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.espresso.idling.CountingIdlingResource;

public class WelcomeActivity extends AppCompatActivity implements MessageFragmentListener, ClickFriendCallBack {

    private static final int LAYOUT = R.layout.activity_welcome;
    private int mFrame = R.id.fragment;
    public static final String TAG = "info";
    public static final String TAG_WELCOME = "TAG_WELCOME";
    public static final String TAG_LOGIN = "TAG_LOGIN";
    public static final String TAG_LOGGED_IN = "TAG_LOGGED_IN";
    public static final String TAG_PHOTO = "TAG_PHOTO";
    private FragmentManager mFragmentManager;
    private ActivityPresenter mPresenter;
    private CountingIdlingResource mCountingIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        init();
        if (savedInstanceState == null) {
            if (VKSdk.isLoggedIn()) {
                mPresenter.loadFragmentData(TAG_LOGGED_IN, null);
            } else{
                mPresenter.loadFragmentData(TAG_WELCOME, null);
            }
        }
    }

    private void init() {
        mCountingIdlingResource = new CountingIdlingResource(TAG);
        mPresenter = new ActivityPresenter();
        mPresenter.attachView(this);
        mPresenter.setCountingIdlingResource(mCountingIdlingResource);
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    public void showFragment(Fragment fragment, String tag, boolean addToBS) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction()
                .replace(mFrame, fragment, tag);
        if (addToBS) fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    public void showToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewClicked(String fragmentTag) {
        mPresenter.loadFragmentData(fragmentTag, null);
    }

    @Override
    public void clickOnFriend(VKUser vkUser) {
        mPresenter.loadFragmentData(TAG_PHOTO, vkUser);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                mPresenter.loadFragmentData(TAG_LOGGED_IN, null); // Пользователь успешно авторизовался
                mCountingIdlingResource.decrement();
            }
            @Override
            public void onError(VKError error) {
                showToast(R.string.error_login_vk); // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
                finish();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public CountingIdlingResource getCountingIdlingResource() {
        return mCountingIdlingResource;
    }
}
