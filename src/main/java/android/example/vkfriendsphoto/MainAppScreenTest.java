package android.example.vkfriendsphoto;

import android.app.Activity;
import android.content.Intent;
import android.example.vkfriendsphoto.utils.Authorization;
import android.example.vkfriendsphoto.utils.RecycleViewAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class MainAppScreenTest extends Activity {
    private String token = null;
    private ProgressBar loadingIndocator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_app_screen_test);
        loadingIndocator = findViewById(R.id.pb_loading_indicator);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        URL requestURL = Authorization.getRequestUrl(token);
        new VKAueryTask().execute(requestURL);
    }

    private class VKAueryTask extends AsyncTask <URL, Void, String>{

        @Override
        public void onPreExecute(){
        }

        @Override
        protected String doInBackground(URL ... urls) {
            String response = null;
            try {
                response = Authorization.getResponseFromUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            int i = 0;
            if (response != null && !response.equals("")) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONObject response1 = jsonResponse.getJSONObject("response");
                    int friendsNumber = response1.getInt("count");
                    JSONArray items = response1.getJSONArray("items");
                    String[] full_name = new String[friendsNumber];
                    String[] small_photo = new String[friendsNumber];
                    String [] large_photo = new String[friendsNumber];
                    Integer[] online_staus = new Integer[friendsNumber];
                    Boolean[] is_closed = new Boolean[friendsNumber];
                    Integer[] id = new Integer[friendsNumber];

                    while(i<friendsNumber){
                        full_name[i] = items.getJSONObject(i).getString("first_name") + " " + items.getJSONObject(i).getString("last_name");
                        small_photo[i] = items.getJSONObject(i).getString("photo_50");
                        large_photo[i]=items.getJSONObject(i).getString("photo_100");
                        online_staus[i] = items.getJSONObject(i).getInt("online");
                        if(items.getJSONObject(i).has("deactivated")){
                            is_closed[i] = true;
                        }
                        else is_closed[i] = items.getJSONObject(i).getBoolean("is_closed");
                        id[i] = items.getJSONObject(i).getInt("id");
                        i++;
                    }

                    RecyclerView testView = findViewById(R.id.rec_view);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MainAppScreenTest.this);
                    testView.setLayoutManager(layoutManager);

                    testView.setHasFixedSize(true);
                    RecycleViewAdapter recycleViewAdapter = new RecycleViewAdapter(Glide.with(MainAppScreenTest.this),full_name,small_photo, large_photo,online_staus, id, is_closed, MainAppScreenTest.this);
                    testView.setAdapter(recycleViewAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
