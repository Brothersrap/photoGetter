package android.example.vkfriendsphoto.utils;

import android.content.Context;
import android.content.Intent;
import android.example.vkfriendsphoto.R;
import android.example.vkfriendsphoto.UserGallery;
import android.support.annotation.NonNull;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.DataViewHolder>{
    private TextView textView;
    private ImageView imageView;
    private ImageView imageViewOnlineStatus;
    private static String[] userName;
    private static String[] photo50URL;
    private static String[] photo100URL;
    private static Boolean[] is_closed;
    private static Integer[] id;
    private final RequestManager glide;
    private Integer[] online_status;
    private Context context;

    public RecycleViewAdapter(RequestManager glide,String[]usersName, String[]photos50URL, String[]photos100URL, Integer[]online_statuses, Integer id[], Boolean[] is_closed, Context context){
        RecycleViewAdapter.userName = usersName;
        RecycleViewAdapter.photo50URL = photos50URL;
        RecycleViewAdapter.photo100URL = photos100URL;
        this.glide = glide;
        this.online_status = online_statuses;
        this.context = context;
        RecycleViewAdapter.id = id;
        RecycleViewAdapter.is_closed = is_closed;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutId = R.layout.vk_view_holder;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutId, viewGroup,false);
        DataViewHolder data = new DataViewHolder(view);
        return data;
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder dataViewHolder, int i) {
        textView.setText(userName[i]);
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(3f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        glide.load(photo50URL[i]).placeholder(circularProgressDrawable).into(imageView);
        if(online_status[i]==1){
            imageViewOnlineStatus.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return userName.length;
    }

    @Override
    public int getItemViewType(int position){
        return position;
    }

    class DataViewHolder extends RecyclerView.ViewHolder {
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_small_photo);
            textView = itemView.findViewById(R.id.tv_for_full_name);
            imageViewOnlineStatus = itemView.findViewById(R.id.iv_online_status);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(is_closed[position]){
                        Toast.makeText(context,"Profile's closed or deleted",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Intent intent = new Intent(context, UserGallery.class);
                        intent.putExtra("id", id[position]);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
