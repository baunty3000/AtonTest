package com.malakhov.atontest.view;

import com.malakhov.atontest.R;
import com.malakhov.atontest.presenter.LoginPresenter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import static com.malakhov.atontest.view.WelcomeActivity.TAG_LOGIN;

public class LoginFragment extends Fragment {

    private static final int LAYOUT = R.layout.fragment_login;
    private LoginPresenter mLoginPresenter;

    @Override
    public void onDetach() {
        super.onDetach();
        mLoginPresenter.detachView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        mLoginPresenter = new LoginPresenter();
        mLoginPresenter.attachView(getActivity());
        view.findViewById(R.id.login).setOnClickListener((v) -> mLoginPresenter.onLickBtn(TAG_LOGIN));
    }
}
