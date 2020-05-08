package android.example.vkfriendsphoto;

import android.content.Context;
import android.content.Intent;
import android.example.vkfriendsphoto.utils.Authorization;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void enterButton(View view) {
        Context context = this;
        Class destinationActivity = AuthorizationLayout.class;
        Intent childActivityIntent = new Intent(context, destinationActivity);
        startActivityForResult(childActivityIntent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(data == null) {return;}
        String error = data.getStringExtra("error");
        Toast.makeText(this,error, Toast.LENGTH_LONG).show();
    }

}
