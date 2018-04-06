package io.git.movies.popularmovies.pojos;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoList implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("results")
    private List<VideoDetails> videoList;

    protected VideoList(Parcel in) {
        id = in.readString();
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<VideoDetails> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoDetails> videoList) {
        this.videoList = videoList;
    }

    @Override
    public String toString() {
        return "VideoList{" +
                "id='" + id + '\'' +
                ", videoList=" + videoList +
                '}';
    }
}
