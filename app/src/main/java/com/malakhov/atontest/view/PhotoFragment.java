package com.malakhov.atontest.view;

import com.malakhov.atontest.R;
import com.malakhov.atontest.presenter.PhotoPresenter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import static com.malakhov.atontest.presenter.ActivityPresenter.KEY_FIO;
import static com.malakhov.atontest.presenter.ActivityPresenter.KEY_IMAGE_URL;

public class PhotoFragment extends Fragment {

    private static final int LAYOUT = R.layout.fragment_photo;
    private ImageView mPhoto;
    private TextView mText;
    private String mUrl;
    private String mFirstName;
    private ProgressBar mProgressBar;
    private PhotoPresenter mPhotoPresenter;

    @Override
    public void onDetach() {
        super.onDetach();
        mPhotoPresenter.detachView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        mUrl = getArguments().getString(KEY_IMAGE_URL);
        mFirstName = getArguments().getString(KEY_FIO);
        init(view);
        return view;
    }

    private void init(View view) {
        mPhotoPresenter = new PhotoPresenter();
        mPhotoPresenter.attachView(this);
        findViews(view);
        loadPhotoFriend();
    }

    private void findViews (View view){
        mPhoto = view.findViewById(R.id.photo);
        mText = view.findViewById(R.id.tv_photo);
        mProgressBar = view.findViewById(R.id.progress);
    }

    private void loadPhotoFriend(){
        mPhotoPresenter.loadPhotoFriend(mUrl);
    }

    public void showFriend(Bitmap bitmap){
        mText.setText(mFirstName);
        mProgressBar.setVisibility(View.VISIBLE);
        mPhoto.setImageBitmap(bitmap);
        mProgressBar.setVisibility(View.GONE);
    }
}
