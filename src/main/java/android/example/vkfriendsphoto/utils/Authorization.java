package android.example.vkfriendsphoto.utils;

import android.content.Intent;
import android.example.vkfriendsphoto.Exeptions.NullTokenExeption;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

public final class Authorization{

    private Authorization(){}

    //lol

    //authorizationPart

    private static String token = null;

    private static final String VK_API_AUTH_URL = "https://oauth.vk.com/";

    private static final String PARAM_CLIENT_ID = "client_id";
    private static final String clientVkId = "6883786";

    private static final String PARAM_DISPLAY = "display";
    private static final String display = "page";

    private static final String PARAM_REDIRECT_URI = "redirect_uri";
    private static final String redirectUri = "oauth.vk.com";

    private static final String PARAM_SCOPE = "scope";
    private static final String scope = "friends, photos";
    private static final String PARAM_RESPONSE_TYPE = "response_type";
    private static final String responseType = "token";

    private static final String PARAM_VERSION = "v";

    private static final String METHOD_AUTHORIZE = "authorize";

    //oauth.vk.com/authorize?client_id=6883786&display=page&redirect_uri=https://oauth.vk.com/blank.html&scope=friends&response_type=token&v=5.92&state=123456

    //requestPart

    private static final String VK_API_REQUEST_URL = "https://api.vk.com/";
    private static final String METHOD_REQUEST = "method/friends.get";

    private static final String PARAM_ACCESS_TOKEN = "access_token";

    private static final String PARAM_FIELDS = "fields";
    private static final String fields = "nickname,photo_50,photo_100,online";


    //https://api.vk.com/method/friends.get?access_token=token&fields=nickname,photo_50,photo_100,online&v=5.95

    public static void setToken(String token){
        Authorization.token = token;
    }

    public static String getToken() throws NullTokenExeption {
        if (token.equals(null)){
            throw new NullTokenExeption();
        }
        else return token;
    }

    public static URL getTokenURL(){
        Uri builtUri = Uri.parse(VK_API_AUTH_URL+METHOD_AUTHORIZE)
                .buildUpon()
                .appendQueryParameter(PARAM_CLIENT_ID, clientVkId)
                .appendQueryParameter(PARAM_DISPLAY, display)
                .appendQueryParameter(PARAM_REDIRECT_URI,redirectUri)
                .appendQueryParameter(PARAM_SCOPE,scope)
                .appendQueryParameter(PARAM_RESPONSE_TYPE,responseType)
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

    public static URL getRequestUrl(String token){
        Uri builtUri = Uri.parse(VK_API_REQUEST_URL+METHOD_REQUEST)
                .buildUpon()
                .appendQueryParameter(PARAM_ACCESS_TOKEN, token)
                .appendQueryParameter(PARAM_FIELDS,fields)
                .appendQueryParameter(PARAM_VERSION,"5.92")
                .build();
        URL requestURL = null;
        try {
            requestURL = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return requestURL;
    }

    public static String getResponseFromUrl(URL url) throws IOException{

        HttpURLConnection urlConnection =(HttpURLConnection) url.openConnection();

        try{
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasNext = scanner.hasNext();
            if (hasNext){
                return scanner.next();
            }
            else return null;}
        catch (UnknownHostException e){
            return null;
        }
        finally {
            urlConnection.disconnect();
        }
    }
}
