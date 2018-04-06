package io.git.movies.popularmovies.pojos;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoList implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("results")
    private List<VideoList> videoList;

    protected VideoList(Parcel in) {
        id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VideoList> CREATOR = new Creator<VideoList>() {
        @Override
        public VideoList createFromParcel(Parcel in) {
            return new VideoList(in);
        }

        @Override
        public VideoList[] newArray(int size) {
            return new VideoList[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<VideoList> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoList> videoList) {
        this.videoList = videoList;
    }

}
