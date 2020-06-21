package com.ppal007.uploadimagetoserverretrofit.Retrofit;



import com.ppal007.uploadimagetoserverretrofit.Model.ImageModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterFace {

//    insert image to server...........................................................................
    @FormUrlEncoded
    @POST("insert_userinfo.php")
    Call<ImageModel> uploadImage(
            @Field("title") String title,
            @Field("address") String address,
            @Field("imagepath") String imagepath
    );

    //    retrieve image from server..............................................................
    @GET("get_user_information.php")
    Call<List<ImageModel>> getAllData();

}
