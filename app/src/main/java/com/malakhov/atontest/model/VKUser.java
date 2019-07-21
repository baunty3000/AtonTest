package com.malakhov.atontest.model;

import org.json.JSONObject;
import java.util.Objects;

public class VKUser {

    private String mLastName;
    private String mFirstName;
    private String mPhotoMini;
    private String mPhotoOrig;

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getPhotoOrig() {
        return mPhotoOrig;
    }

    public void setPhotoOrig(String photoOrig) {
        mPhotoOrig = photoOrig;
    }

    public void parse(JSONObject json) {
        mFirstName = json.optString("first_name", "");
        mLastName = json.optString("last_name", "");
        mPhotoMini = json.optString("photo_100", "");
        mPhotoOrig = json.optString("photo_max_orig", "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VKUser vkUser = (VKUser) o;
        return Objects.equals(mLastName, vkUser.mLastName) && Objects
                .equals(mFirstName, vkUser.mFirstName) && Objects
                .equals(mPhotoMini, vkUser.mPhotoMini) && Objects
                .equals(mPhotoOrig, vkUser.mPhotoOrig);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mLastName, mFirstName, mPhotoMini, mPhotoOrig);
    }
}


