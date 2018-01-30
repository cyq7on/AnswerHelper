package com.branch.www.screencapture;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;

public class MainActivity extends AppCompatActivity {


    public static final int REQUEST_MEDIA_PROJECTION = 18;
    private static boolean isNeed = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        requestCapturePermission();
    }


    public void requestCapturePermission() {

        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager)
                getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        startActivityForResult(
                mediaProjectionManager.createScreenCaptureIntent(),
                REQUEST_MEDIA_PROJECTION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_MEDIA_PROJECTION:

                if (resultCode == RESULT_OK && data != null) {
                    if(isNeed){
                        FloatWindowsService.setResultData(data);
                        startService(new Intent(getApplicationContext(), FloatWindowsService.class));
                        initAccessTokenWithAkSk();
                        isNeed = false;
                    }else {
                        finish();
                    }
                }
                break;
        }

    }

    private void initAccessTokenWithAkSk() {
        OCR.getInstance().initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                String token = result.getAccessToken();
                Log.d("test", "验证成功");
                finish();
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "AK，SK方式获取token失败", Toast.LENGTH_SHORT).show();
                    }
                });

                Log.d("test", error.getMessage());
                finish();
            }
        }, getApplicationContext(), "B8OMZNpahuc1EyraCx0yI8TS", "lftpgyLRdSspzWIy8ajVxiwjFy0yICPI");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放内存资源
        //OCR.getInstance().release();
    }

}
