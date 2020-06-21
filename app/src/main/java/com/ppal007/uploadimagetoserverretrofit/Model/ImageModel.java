package com.ppal007.uploadimagetoserverretrofit.Model;

import com.google.gson.annotations.SerializedName;

public class ImageModel {

    @SerializedName("id")
    private String Id;

    @SerializedName("title")
    private String Title;

    @SerializedName("address")
    private String Address;

    @SerializedName("imagepath")
    private String ImagePath;

    @SerializedName("response")
    private String Response;


    public String getId() {
        return Id;
    }

    public String getTitle() {
        return Title;
    }

    public String getAddress() {
        return Address;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public String getResponse() {
        return Response;
    }
}
