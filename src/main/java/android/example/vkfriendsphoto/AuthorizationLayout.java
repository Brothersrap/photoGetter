package android.example.vkfriendsphoto;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.example.vkfriendsphoto.utils.Authorization;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.sql.SQLOutput;


public class AuthorizationLayout extends Activity {
    private static final String redirectUriTitle = "OAuth Blank";
    ProgressBar loadingProgressBar;
    private String token = "";
    WebView authorizationWebView;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization_layout);
        loadingProgressBar = findViewById(R.id.LPB_authorization);
        authorizationWebView = findViewById(R.id.authorizationWebView);

        final Intent intent = new Intent(AuthorizationLayout.this, MainActivity.class);

        final String[] tokens = new String[1];
        authorizationWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                intent.putExtra("error", "connection error");
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onPageFinished(WebView view, String urlNewString){
                loadingProgressBar.setVisibility(View.INVISIBLE);
                if(view.getTitle().equals(redirectUriTitle)) {
                    String answer = view.getUrl();
                    String[] answerMas = answer.split("=|&");
                    tokens[0] = answerMas[1];
                    token = tokens[0];
                    if(!token.equals("access_denied")){
                        Intent intent = new Intent(AuthorizationLayout.this, MainAppScreenTest.class);
                        Authorization.setToken(token);
                        intent.putExtra("token", token);
                        startActivity(intent);
                        finish();
                    }
                    else if(token.equals("access_denied")){
                        intent.putExtra("error", "access_denied");
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            }
            });
        authorizationWebView.loadUrl(String.valueOf(Authorization.getTokenURL()));


        }
    }
