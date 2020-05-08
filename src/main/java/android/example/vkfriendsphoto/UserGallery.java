package android.example.vkfriendsphoto;

import android.app.Activity;
import android.content.Intent;
import android.example.vkfriendsphoto.Exeptions.NullTokenExeption;
import android.example.vkfriendsphoto.utils.Authorization;
import android.example.vkfriendsphoto.utils.GalleryInfo;
import android.example.vkfriendsphoto.utils.RecyclerViewGridAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;


public class UserGallery extends Activity {
    URL photosUrl = null;

    public void onCreate(Bundle savedInstantState){
        super.onCreate(savedInstantState);
        setContentView(R.layout.user_gallery);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);
        try {
            photosUrl = GalleryInfo.getUrl(id, Authorization.getToken());
        } catch (NullTokenExeption nullTokenExeption) {
            nullTokenExeption.printStackTrace();
        }
        new VKGettingPhotos().execute(photosUrl);
    }

    private class VKGettingPhotos extends AsyncTask<URL, Void, String>{

        @Override
        protected String doInBackground(URL... urls) {
            String response = null;
            try {
                response = Authorization.getResponseFromUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(String response){
            int i = 0;
            if (response != null && !response.equals("")) {
                String[] photos = null;
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONObject response1 = jsonResponse.getJSONObject("response");
                    int count = response1.getInt("count")-1;
                    JSONArray items = response1.getJSONArray("items");
                    if(count==-1){ ;
                        count = 0;
                    }
                    if(count>200){
                        count = 200;
                    }
                    photos = new String[count];
                    System.out.println(photos.length);

                    while(i<count){
                        photos[i] = items.getJSONObject(i).getJSONArray("sizes").getJSONObject(0).getString("url");
                        System.out.println("lol " + photos[i]);
                        i++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                finally {

                    if (!(photos.length==0)){
                        RecyclerView recyclerView = findViewById(R.id.rc_gallery);
                        GridLayoutManager layoutManager = new GridLayoutManager(UserGallery.this, 3);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setHasFixedSize(true);
                        RecyclerViewGridAdapter recyclerViewGridAdapter = new RecyclerViewGridAdapter(Glide.with(UserGallery.this),photos);
                        recyclerView.setAdapter(recyclerViewGridAdapter);
                    }
                    else Toast.makeText(UserGallery.this, "User hasn't added photos to his galleries yet", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}
