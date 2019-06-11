package com.malakhov.atontest.presenter;

import com.malakhov.atontest.view.MessageFragmentListener;
import androidx.fragment.app.FragmentActivity;

public class LoginPresenter {

    private MessageFragmentListener mListener;
    private FragmentActivity mView;

    public void attachView(FragmentActivity fragmentActivity) {
        mView = fragmentActivity;
        if (mView instanceof MessageFragmentListener){
            mListener = (MessageFragmentListener) mView;
        }
    }

    public void detachView() {
        mView = null;
        mListener = null;
    }

    public void onLickBtn(String tag) {
        mListener.onViewClicked(tag);
    }
}
