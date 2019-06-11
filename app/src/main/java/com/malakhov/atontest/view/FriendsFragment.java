package com.malakhov.atontest.view;

import com.malakhov.atontest.R;
import com.malakhov.atontest.common.ClickFriendCallBack;
import com.malakhov.atontest.common.FriendsAdapter;
import com.malakhov.atontest.model.VKUser;
import com.malakhov.atontest.presenter.FriendsPresenter;
import com.vk.sdk.VKSdk;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import static com.malakhov.atontest.view.WelcomeActivity.TAG_WELCOME;

public class FriendsFragment extends Fragment {

    private static final int LAYOUT = R.layout.fragment_users_recycler;
    private FriendsAdapter mAdapter;
    private MessageFragmentListener mListener;
    private FriendsPresenter mFriendsPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MessageFragmentListener){
            mListener = (MessageFragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mFriendsPresenter.detachView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        init(view);
        loadFriends();
        return view;
    }

    private void init(View view) {
        mFriendsPresenter = new FriendsPresenter();
        mFriendsPresenter.attachView(this);
        mAdapter = new FriendsAdapter(getActivity() instanceof ClickFriendCallBack ? (ClickFriendCallBack) getActivity() : null);
        RecyclerView recycler = view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(mAdapter);
        loadFriends();
        view.findViewById(R.id.logout).setOnClickListener((v)-> {
            VKSdk.logout();
            mListener.onViewClicked(TAG_WELCOME);
        });
    }

    private void loadFriends(){
        mFriendsPresenter.loadFriends();
    }

    public void showFriends(ArrayList<VKUser> friendList) {
        mAdapter.setNewsItems(friendList);
    }
}
