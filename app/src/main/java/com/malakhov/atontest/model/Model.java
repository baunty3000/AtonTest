package com.malakhov.atontest.model;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import org.json.JSONArray;
import org.json.JSONException;
import android.util.Log;
import java.util.ArrayList;
import static com.malakhov.atontest.view.WelcomeActivity.TAG;
import static com.vk.sdk.api.VKApiConst.FIELDS;

public class Model {

    private static final Model ourInstance = new Model();
    private LoadFriendsCallback mLoadFriendsCallback;

    public static Model getInstance() {
        return ourInstance;
    }

    private Model() {
    }

    public void setLoadFriendsCallback(LoadFriendsCallback loadFriendsCallback) {
        mLoadFriendsCallback = loadFriendsCallback;
    }

    public void loadFriends() {
        VKParameters vkParameters = new VKParameters();
        vkParameters.put(FIELDS, "photo_100, photo_max_orig");
        final VKRequest request = VKApi.friends().get(vkParameters);

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                Log.d(TAG, "Model loading friends onComplete");
                mLoadFriendsCallback.onLoad(getListFriends(getJSONArrayFriends(response)));
            }

            @Override
            public void onError(VKError error) {
                Log.d(TAG, "onError: " + error);
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                Log.d(TAG, "attemptFailed: " + attemptNumber);
            }
        });
    }

    private JSONArray getJSONArrayFriends(VKResponse response) {
        try {
            return response.json.getJSONObject("response").getJSONArray("items");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }

    private ArrayList<VKUser> getListFriends(JSONArray jsonArrayFriends) {
        ArrayList<VKUser> listFriends = new ArrayList<>();
        for (int i = 0; i < jsonArrayFriends.length(); i++) {
            VKUser vkUser = new VKUser();
            try {
                vkUser.parse(jsonArrayFriends.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listFriends.add(vkUser);
        }
        return listFriends;
    }
}
