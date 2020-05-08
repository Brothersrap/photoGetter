package android.example.vkfriendsphoto.utils;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;


public class GalleryInfo {

    String[] arguments = {
            "lol",
            "LOL"
    };

    private static final String VK_API_REQUEST_URL = "https://api.vk.com/";
    private static final String METHOD_REQUEST = "method/photos.getAll";

    private static final String PARAM_ACCESS_TOKEN = "access_token";

    private static final String PARAM_OWNER_ID = "owner_id";
    private static final String PARAM_EXTENDED = "extended";
    private static final String PARAM_COUNT = "count";
    private static final String PARAM_VERSION = "v";
    private static final String PARAM_NO_SERVICE_ALBUM = "no_service_albums";

   //https://api.vk.com/method/photos.getAll?no_service_albums=0&photo_sizes=0&owner_id=8777325&extended=0&count=200&access_token=ced6cddb0843fdb0f38af6683ecb73a83262850f0c8b360108279e01de3a405c12263701fa8fd7e45f8e0&v=5.92

    private GalleryInfo(){}

    public static URL getUrl(int id, String token){

        Uri builtUri = Uri.parse(VK_API_REQUEST_URL+METHOD_REQUEST)
                .buildUpon()
                .appendQueryParameter(PARAM_OWNER_ID, String.valueOf(id))
                .appendQueryParameter(PARAM_EXTENDED,"0")
                .appendQueryParameter(PARAM_COUNT,"200")
                .appendQueryParameter(PARAM_ACCESS_TOKEN,token).
                appendQueryParameter(PARAM_NO_SERVICE_ALBUM,"0")
                .appendQueryParameter(PARAM_VERSION,"5.92")
                .build();

        URL authorizationURL = null;
        try {
            authorizationURL = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return authorizationURL;
    }

}
