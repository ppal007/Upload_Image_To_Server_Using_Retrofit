package com.ppal007.uploadimagetoserverretrofit.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ppal007.uploadimagetoserverretrofit.Model.ImageModel;
import com.ppal007.uploadimagetoserverretrofit.R;
import com.ppal007.uploadimagetoserverretrofit.Utils.Common;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyViewHolder> {

    private Context context;
    private List<ImageModel> imageModelList;

    public RVAdapter(Context context, List<ImageModel> imageModelList) {
        this.context = context;
        this.imageModelList = imageModelList;
    }

    @NonNull
    @Override
    public RVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_sample,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapter.MyViewHolder holder, int position) {

//        get image path.......................................................................
        String img_path = Common.BASE_URL+imageModelList.get(position).getImagePath();
//        convert String(image path) to Uri...................................................
        Uri img_uri = Uri.parse(img_path);
//        set image to image view................................................................
        Glide.with(context).load(img_uri).circleCrop().into(holder.imageView);
//        set title and address to textview......................................................
        holder.textViewTitle.setText(imageModelList.get(position).getTitle());
        holder.textViewAddress.setText(imageModelList.get(position).getAddress());

    }

    @Override
    public int getItemCount() {
        return imageModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textViewTitle,textViewAddress;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewSampleId);
            textViewTitle = itemView.findViewById(R.id.textViewTitleSampleId);
            textViewAddress = itemView.findViewById(R.id.textViewAddressSampleId);

        }
    }
}
