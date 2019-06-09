package com.malakhov.atontest.view;

import com.malakhov.atontest.R;
import com.malakhov.atontest.common.FriendsAdapter;
import com.malakhov.atontest.model.VKUser;
import com.vk.sdk.VKSdk;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mAdapter = new FriendsAdapter(getContext());
        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.recycler);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(mAdapter);

        ((Button)view.findViewById(R.id.logout)).setOnClickListener((v)-> {
            VKSdk.logout();
            mListener.onItemClicked(TAG_WELCOME);
        });
    }

    public void setFriends(ArrayList<VKUser> friendList){
        mAdapter.setNewsItems(friendList);
    }
}
