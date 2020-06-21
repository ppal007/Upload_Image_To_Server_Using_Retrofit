package com.ppal007.uploadimagetoserverretrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.ppal007.uploadimagetoserverretrofit.Adapter.RVAdapter;
import com.ppal007.uploadimagetoserverretrofit.Model.ImageModel;
import com.ppal007.uploadimagetoserverretrofit.Retrofit.ApiClient;
import com.ppal007.uploadimagetoserverretrofit.Retrofit.ApiInterFace;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        recyclerView = findViewById(R.id.recyclerViewId);

//        initialize recycler view...................................................................
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        initialize api............................................................................
        ApiInterFace apiInterFace = ApiClient.getApiClient().create(ApiInterFace.class);
        Call<List<ImageModel>> call = apiInterFace.getAllData();
        call.enqueue(new Callback<List<ImageModel>>() {
            @Override
            public void onResponse(Call<List<ImageModel>> call, Response<List<ImageModel>> response) {
                if (response.body() != null){
                    List<ImageModel> imageModelList = response.body();

                    adapter = new RVAdapter(getApplicationContext(),imageModelList);
                    recyclerView.setAdapter(adapter);

                }else {
                    Toast.makeText(DetailsActivity.this, "Something Wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ImageModel>> call, Throwable t) {
                Toast.makeText(DetailsActivity.this, ""+t.toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}