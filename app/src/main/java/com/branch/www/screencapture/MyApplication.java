package com.branch.www.screencapture;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by Ryze on 2016-7-20.
 */
public class MyApplication extends Application {

  private static Context context;
  private Bitmap mScreenCaptureBitmap;

  @Override
  public void onCreate() {
    super.onCreate();
    context = getApplicationContext();
  }
  public Bitmap getmScreenCaptureBitmap() {
    return mScreenCaptureBitmap;
  }

  public void setmScreenCaptureBitmap(Bitmap mScreenCaptureBitmap) {
    this.mScreenCaptureBitmap = mScreenCaptureBitmap;
  }

  public static Context getContext() {
    return context;
  }
}
