package com.example.kp2101.journey0100;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;


import org.json.JSONObject;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    AccessToken accessToken;
    Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        accessToken=null;


        //初始化FacebookSdk，記得要放第一行，不然setContentView會出錯
        FacebookSdk.sdkInitialize(getApplicationContext());


        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //final Intent intent = this.getIntent();
        //bundle = intent.getExtras();


        //宣告callback Manager
        callbackManager = CallbackManager.Factory.create();

        boolean loggedIn=false;
        loggedIn = isFacebookLoggedIn();
        if(loggedIn){
            Log.d("login", "true");
            profile=null;
            profile = Profile.getCurrentProfile();
            if(profile==null){
                Log.d("profile", "null");
            }else{
                Log.d("profile", "not null");
                if(profile.getId()==null){
                    Log.d("profile getid", "null");
                }else{
                    Log.d("profile getid", profile.getId());
                }

            }
            nextIntent(profile.getId(),profile.getName());
        }else{
            Log.d("login", "false");

            //找到button

            LinearLayout loginButton = (LinearLayout) findViewById(R.id.fb_login);

            loginButton.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile", "user_friends","email"));
                }
            });


            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

                //登入成功
                @Override
                public void onSuccess(LoginResult loginResult) {

                    //accessToken之後或許還會用到 先存起來

                    accessToken = loginResult.getAccessToken();

                    Log.d("FB", "access token got.");

                    //send request and call graph api

                    GraphRequest request = GraphRequest.newMeRequest(
                            accessToken,
                            new GraphRequest.GraphJSONObjectCallback() {

                                //當RESPONSE回來的時候

                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {

//                                    Log.d("FB", object.optString("id"));
//                                    Log.d("FB", object.optString("name"));
//                                    Log.d("FB", object.optString("link"));
                                    nextIntent(object.optString("id"),object.optString("name"));


                                }
                            });
                    //包入你想要得到的資料 送出request

                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,link");
                    request.setParameters(parameters);
                    request.executeAsync();
                    Log.d("request", request.getCallback().toString());



                }

                //登入取消

                @Override
                public void onCancel() {
                    // App code

                    Log.d("FB", "CANCEL");
                }

                //登入失敗

                @Override
                public void onError(FacebookException exception) {
                    // App code

                    Log.d("FB", exception.toString());
                }
             });

        }



    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_settings:
                return true;
            case R.id.action_logout:
                LoginManager.getInstance().logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
    public boolean isFacebookLoggedIn(){
        if(AccessToken.getCurrentAccessToken()==null){
            Log.d("Access","null");
        }else{
            Log.d("Access",AccessToken.getCurrentAccessToken().toString());
        }

        return AccessToken.getCurrentAccessToken() != null;
    }
    public void nextIntent(String fbid,String fbname){
        Intent intent = new Intent(this, JourneyActivity.class);
        intent.putExtra("fbid", fbid);
        intent.putExtra("fbname", fbname);
        startActivity(intent);
        finish();
    }

}
