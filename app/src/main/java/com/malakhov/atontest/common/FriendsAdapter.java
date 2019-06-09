package com.malakhov.atontest.common;

import com.malakhov.atontest.R;
import com.malakhov.atontest.model.DownloadImageTask;
import com.malakhov.atontest.model.VKUser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.VHFriends> {

    private List<VKUser> mFriendList = new ArrayList<>();
    private final LayoutInflater mInflater;

    public FriendsAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setNewsItems(List<VKUser> list) {
        mFriendList.clear();
        mFriendList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VHFriends onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VHFriends(mInflater.inflate(R.layout.item_recycler, parent, false), mFriendList);
    }

    @Override
    public void onBindViewHolder(@NonNull VHFriends holder, int position) {
        holder.bind(mFriendList.get(position));
    }

    @Override
    public int getItemCount() {
        return mFriendList == null ? 0 : mFriendList.size();
    }

    static class VHFriends extends RecyclerView.ViewHolder {

        private TextView mFio;
        private ImageView mPhoto;
        private ProgressBar mProgressBar;
        private ClickFriendCallBack mClickFriendCallBack;

        public VHFriends(@NonNull View itemView, List<VKUser> friendList) {
            super(itemView);
            findViews(itemView);

            Context context = itemView.getContext();
            if (context instanceof ClickFriendCallBack){
                mClickFriendCallBack = (ClickFriendCallBack) context;
            }

            itemView.setOnClickListener((n)->{
                mClickFriendCallBack.clickOn(friendList.get(getAdapterPosition()));
            });
        }

        private void findViews(View itemView) {
            mFio = itemView.findViewById(R.id.fio);
            mPhoto = itemView.findViewById(R.id.photo);
            mProgressBar = itemView.findViewById(R.id.progress);
        }

        public void bind(VKUser vkUser) {
            mFio.setText(new StringBuilder().append(vkUser.getLastName()).append(" ").append(vkUser.getFirstName()));
            mProgressBar.setVisibility(View.VISIBLE);
            new DownloadImageTask(mPhoto, mProgressBar).execute(vkUser.getPhotoOrig());
        }

    }
}
