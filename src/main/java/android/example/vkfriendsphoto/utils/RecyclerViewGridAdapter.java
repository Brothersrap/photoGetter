package android.example.vkfriendsphoto.utils;

import android.content.Context;
import android.example.vkfriendsphoto.R;
import android.support.annotation.NonNull;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.RequestManager;

public class RecyclerViewGridAdapter extends RecyclerView.Adapter<RecyclerViewGridAdapter.DataViewHolder>{
    private ImageView photo;
    private String[] photos;
    private RequestManager glide;
    private Context context;
    public RecyclerViewGridAdapter(RequestManager glide, String[] photos){
        this.photos = photos;
        this.glide = glide;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        return new DataViewHolder(LayoutInflater.from(context).inflate(R.layout.photo_holder,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder dataViewHolder, int i) {
       CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        glide.load(photos[i]).placeholder(circularProgressDrawable).into(photo);
        System.out.println("");
    }

    @Override
    public int getItemCount() {
        return photos.length;
    }

    @Override

    public int getItemViewType(int position){
        return position;
    }

    public class DataViewHolder extends RecyclerView.ViewHolder{

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.image_view_for_grid_photo);
        }
    }
}