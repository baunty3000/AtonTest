package com.malakhov.atontest.view;

import com.malakhov.atontest.R;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.malakhov.atontest.view.WelcomeActivity.TAG_LOGIN;


public class LoginFragment extends Fragment {

    private static final int LAYOUT = R.layout.fragment_login;
    private MessageFragmentListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MessageFragmentListener){
            mListener = (MessageFragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
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
        ((Button)view.findViewById(R.id.login)).setOnClickListener((v)-> mListener.onItemClicked(TAG_LOGIN));
    }
}
