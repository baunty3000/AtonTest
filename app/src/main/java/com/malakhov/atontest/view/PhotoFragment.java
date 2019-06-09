package com.malakhov.atontest.view;

import com.malakhov.atontest.R;
import com.malakhov.atontest.model.DownloadImageTask;

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

import static com.malakhov.atontest.presenter.Presenter.FIRST_NAME;
import static com.malakhov.atontest.presenter.Presenter.IMAGE_URL;

public class PhotoFragment extends Fragment {

    private static final int LAYOUT = R.layout.fragment_photo;
    private ImageView mPhoto;
    private TextView mText;
    private String mUrl;
    private String mFirstName;
    private ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        mUrl = getArguments().getString(IMAGE_URL);
        mFirstName = getArguments().getString(FIRST_NAME);
        init(view);
        return view;
    }

    private void init(View view) {
        mPhoto = view.findViewById(R.id.photo);
        mText = view.findViewById(R.id.tv_photo);
        mProgressBar = view.findViewById(R.id.progress);
        mText.setText(mFirstName);

        mProgressBar.setVisibility(View.VISIBLE);
        new DownloadImageTask(mPhoto, mProgressBar).execute(mUrl);
    }

}
