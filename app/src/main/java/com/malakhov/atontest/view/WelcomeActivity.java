package com.malakhov.atontest.view;

import com.malakhov.atontest.R;
import com.malakhov.atontest.common.ClickFriendCallBack;
import com.malakhov.atontest.model.Model;
import com.malakhov.atontest.model.VKUser;
import com.malakhov.atontest.presenter.Presenter;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class WelcomeActivity extends AppCompatActivity implements MessageFragmentListener,
        ClickFriendCallBack {

    private static final int LAYOUT = R.layout.activity_welcome;
    private int mFrame = R.id.fragment;
    public static final String TAG_WELCOME = "TAG_WELCOME";
    public static final String TAG_LOGIN = "TAG_LOGIN";
    public static final String TAG_LOGGED_IN = "TAG_LOGGED_IN";
    public static final String TAG_PHOTO = "TAG_PHOTO";
    private FragmentManager mFragmentManager;
    private Presenter mPresenter;
    private String TAG = "info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        init();
    }

    private void init() {
        Model model = new Model();
        mPresenter = new Presenter(model);
        mPresenter.attachView(this);
        mFragmentManager = getSupportFragmentManager();

        if (VKSdk.isLoggedIn()) {
            mPresenter.loadFragmentData(TAG_LOGGED_IN, null);
            return;
        }
        mPresenter.loadFragmentData(TAG_WELCOME, null);
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onItemClicked(String fragmentTag) {
        mPresenter.loadFragmentData(fragmentTag, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                // Пользователь успешно авторизовался
                mPresenter.loadFriends();
            }

            @Override
            public void onError(VKError error) {
                // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
                showToast(R.string.error_login_vk);
                finish();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void showFriends(ArrayList<VKUser> friendList) {
        ((FriendsFragment)getSupportFragmentManager().findFragmentByTag(TAG_LOGIN)).setFriends(friendList);
    }

    @Override
    public void clickOn(VKUser vkUser) {
        mPresenter.loadFragmentData(TAG_PHOTO, vkUser);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mPresenter.loadFriends();
    }
}
