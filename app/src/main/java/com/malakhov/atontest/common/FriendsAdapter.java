package com.malakhov.atontest.common;

import com.malakhov.atontest.R;
import com.malakhov.atontest.model.VKUser;
import android.graphics.Bitmap;
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
    private ClickFriendCallBack mClickFriendCallBack;

    public FriendsAdapter(ClickFriendCallBack clickFriendCallBack) {
        mClickFriendCallBack = clickFriendCallBack;
    }

    public void setNewsItems(List<VKUser> list) {
        mFriendList.clear();
        mFriendList.addAll(list);
        notifyDataSetChanged();
        DownloaderImages.getInstance().init(list.size());
    }

    @NonNull
    @Override
    public VHFriends onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VHFriends(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VHFriends holder, int position) {
        VKUser vkUser = mFriendList.get(position);
        holder.bind(vkUser);
        holder.itemView.setOnClickListener(v -> {
            if (mClickFriendCallBack != null) mClickFriendCallBack.clickOnFriend(vkUser);
        });
        if (DownloaderImages.getInstance().getPhoto(vkUser.getPhotoOrig()) != null){
            holder.setPhoto(DownloaderImages.getInstance().getPhoto(vkUser.getPhotoOrig()));
            holder.mProgressBar.setVisibility(View.GONE);
        } else { DownloaderImages.getInstance().downloadPhoto(mFriendList.get(position).getPhotoOrig(),
                result -> {
                    holder.setPhoto(result);
                    DownloaderImages.getInstance().addToCash(vkUser.getPhotoOrig(), result);
                    holder.mProgressBar.setVisibility(View.GONE);
                });
        }
    }

    @Override
    public int getItemCount()  {
        return mFriendList == null ? 0 : mFriendList.size();
    }

    static class VHFriends extends RecyclerView.ViewHolder {

        private TextView mFio;
        private ImageView mPhoto;
        private ProgressBar mProgressBar;

        private VHFriends(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);
        }

        private void findViews(View itemView) {
            mFio = itemView.findViewById(R.id.fio);
            mPhoto = itemView.findViewById(R.id.photo);
            mProgressBar = itemView.findViewById(R.id.progress);
        }

        private void bind(VKUser vkUser) {
            mFio.setText(new StringBuilder().append(vkUser.getLastName()).append(" ").append(vkUser.getFirstName()));
            mProgressBar.setVisibility(View.VISIBLE);
        }

        private void setPhoto(Bitmap result) {
            mPhoto.setImageBitmap(result);
        }
    }
}
