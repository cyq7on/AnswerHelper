package com.branch.www.screencapture;

import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;

public class MainActivity extends AppCompatActivity {


  public static final int REQUEST_MEDIA_PROJECTION = 18;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    requestCapturePermission();
  }


  public void requestCapturePermission() {

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
      //5.0 之后才允许使用屏幕截图

      return;
    }

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
          FloatWindowsService.setResultData(data);
          startService(new Intent(getApplicationContext(), FloatWindowsService.class));
          initAccessTokenWithAkSk();
        }
        break;
    }

  }

  private void initAccessTokenWithAkSk() {
    OCR.getInstance().initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
      @Override
      public void onResult(AccessToken result) {
        String token = result.getAccessToken();
        Log.d("test","验证成功");
        finish();
      }

      @Override
      public void onError(OCRError error) {
        error.printStackTrace();
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            Toast.makeText(getApplicationContext(),"AK，SK方式获取token失败",Toast.LENGTH_SHORT).show();
          }
        });

        Log.d("test",error.getMessage());
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
