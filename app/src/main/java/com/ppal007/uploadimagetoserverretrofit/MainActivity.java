package com.ppal007.uploadimagetoserverretrofit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.ppal007.uploadimagetoserverretrofit.Model.ImageModel;
import com.ppal007.uploadimagetoserverretrofit.Retrofit.ApiClient;
import com.ppal007.uploadimagetoserverretrofit.Retrofit.ApiInterFace;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private EditText editTextTitle,editTextAddress;
    private Button buttonUpload,buttonShowDetails;
    private ImageButton imageButton;

    private static final int IMAGE_REQUEST = 1111;
    private Bitmap _bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        finding xml............................................................................
        imageView = findViewById(R.id.imageViewId);
        editTextTitle = findViewById(R.id.editTextTitleId);
        editTextAddress = findViewById(R.id.editTextAddressId);
        buttonUpload = findViewById(R.id.buttonUploadImageId);
        buttonShowDetails = findViewById(R.id.buttonShowDetailsId);
        imageButton = findViewById(R.id.imageButtonId);


//        upload image click listener.............................................................
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

//        upload button click listener.............................................................
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

//        button show details click listener.........................................................
        buttonShowDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DetailsActivity.class);
                startActivity(intent);
            }
        });
    }

//    for image select.............................................................................
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent,IMAGE_REQUEST);
        startActivityForResult(Intent.createChooser(intent,"Select Image"),IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null){

            Uri path = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
//                set image to image view....................................................
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bitmap);
//                set image button visibility false.................................................
                imageButton.setVisibility(View.GONE);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

//    for image upload to server...................................................................
    private void uploadImage() {

        String _title = editTextTitle.getText().toString();
        String _address = editTextAddress.getText().toString();

        if (imageView.getDrawable()==null){
            Toast.makeText(this, "please select an image", Toast.LENGTH_SHORT).show();
        }else if (_title.isEmpty()){
            editTextTitle.setError("Enter image title");
            editTextTitle.requestFocus();
        }else if (_address.isEmpty()){
            editTextAddress.setError("Enter Address");
            editTextAddress.requestFocus();
        }else {
            String _image = imageToString();

            ApiInterFace apiInterFace = ApiClient.getApiClient().create(ApiInterFace.class);
            Call<ImageModel> call = apiInterFace.uploadImage(_title,_address,_image);
            call.enqueue(new Callback<ImageModel>() {
                @Override
                public void onResponse(Call<ImageModel> call, Response<ImageModel> response) {
                    if (response.body()!=null){
                        ImageModel model = response.body();
                        Toast.makeText(MainActivity.this, ""+model.getResponse(), Toast.LENGTH_SHORT).show();

                        imageView.setImageBitmap(null);
                        imageView.setImageDrawable(null);
                        editTextTitle.setText("");
                        editTextAddress.setText("");
//                        set image button visibility true.......................................
                        imageButton.setVisibility(View.VISIBLE);
//                        set image view visibility false...........................................
                        imageView.setVisibility(View.GONE);
                    }else {
                        Toast.makeText(MainActivity.this, "Can't Upload", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ImageModel> call, Throwable t) {
                    Toast.makeText(MainActivity.this, ""+t.toString(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
//    for convert image to string..................................................................
    private String imageToString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        get image from image view..................................................................
        _bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        _bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        byte[] imageByte = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imageByte,Base64.DEFAULT);
    }
}