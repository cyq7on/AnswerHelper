package com.cyq7on.answerhelper.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cyq7on.answerhelper.R;


/**
 * Created by 赵晨璞 on 2016/8/11.
 */
public class ToastUtil {

    private Toast toast;
    private LinearLayout toastView;

    /**
     * 修改原布局的Toast
     */
    public ToastUtil() {

    }

    /**
     * 完全自定义布局Toast
     *
     * @param context
     * @param view
     */
    public ToastUtil(Context context, View view, int duration) {
        toast = new Toast(context);
        toast.setView(view);
        toast.setDuration(duration);
    }

    /**
     * 向Toast中添加自定义view
     *
     * @param view
     * @param postion
     * @return
     */
    public ToastUtil addView(View view, int postion) {
        toastView = (LinearLayout) toast.getView();
        toastView.addView(view, postion);

        return this;
    }

    /**
     * 设置Toast字体及背景颜色
     *
     * @param messageColor
     * @param backgroundColor
     * @return
     */
    public ToastUtil setToastColor(int messageColor, int backgroundColor) {
        View view = toast.getView();
        view.setBackgroundColor(backgroundColor);
        if (view != null) {
            TextView message = view.findViewById(android.R.id.message);
            message.setBackgroundColor(backgroundColor);
            message.setTextColor(messageColor);
        }
        return this;
    }

    /**
     * 设置Toast字体及背景
     *
     * @param messageColor
     * @param background
     * @return
     */
    public ToastUtil setToastBackground(int messageColor, int background) {
        View view = toast.getView();
        view.setBackgroundColor(background);
        if (view != null) {
            TextView message = view.findViewById(android.R.id.message);
            LinearLayout.LayoutParams params = new LinearLayout.
                    LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            message.setLayoutParams(params);
            message.setBackgroundResource(background);
            message.setTextColor(messageColor);
        }
        return this;
    }

    /**
     * 短时间显示Toast
     */
    public ToastUtil Short(Context context, CharSequence message) {
        if (toast == null || (toastView != null && toastView.getChildCount() > 1)) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toastView = null;
        } else {
            toast.setText(message);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        return this;
    }

    /**
     * 短时间显示Toast
     */
    public ToastUtil Short(Context context, int message) {
        if (toast == null || (toastView != null && toastView.getChildCount() > 1)) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toastView = null;
        } else {
            toast.setText(message);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        return this;
    }

    /**
     * 长时间显示Toast
     */
    public ToastUtil Long(Context context, CharSequence message) {
        if (toast == null || (toastView != null && toastView.getChildCount() > 1)) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toastView = null;
        } else {
            toast.setText(message);
            toast.setDuration(Toast.LENGTH_LONG);
        }
        return this;
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public ToastUtil Long(Context context, int message) {
        if (toast == null || (toastView != null && toastView.getChildCount() > 1)) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toastView = null;
        } else {
            toast.setText(message);
            toast.setDuration(Toast.LENGTH_LONG);
        }
        return this;
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public ToastUtil Indefinite(Context context, CharSequence message, int duration) {
        if (toast == null || (toastView != null && toastView.getChildCount() > 1)) {
            toast = Toast.makeText(context, message, duration);
            toastView = null;
        } else {
            toast.setText(message);
            toast.setDuration(duration);
        }
        return this;
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public ToastUtil Indefinite(Context context, int message, int duration) {
        if (toast == null || (toastView != null && toastView.getChildCount() > 1)) {
            toast = Toast.makeText(context, message, duration);
            toastView = null;
        } else {
            toast.setText(message);
            toast.setDuration(duration);
        }
        return this;
    }

    /**
     * 显示Toast
     *
     * @return
     */
    public ToastUtil show() {
        toast.show();

        return this;
    }

    /**
     * 获取Toast
     *
     * @return
     */
    public Toast getToast() {
        return toast;
    }

    /**
     * @Description: 默认显示方式
     * @author: cyq7on
     * @date: 2018/1/31 16:54
     * @version: V1.0
     */

    public static void showShort(Context context, String msg) {
        ToastUtil toastUtil = new ToastUtil();
        toastUtil.Short(context, msg).setToastColor(Color.WHITE,
                context.getResources().getColor(R.color.colorAccent)).show();
    }

    public static void showLong(Context context, String msg) {
        ToastUtil toastUtil = new ToastUtil();
        toastUtil.Long(context, msg).setToastColor(Color.WHITE,
                context.getResources().getColor(R.color.colorAccent)).show();
    }

    public static void showShort(Context context, int msgRes) {
        ToastUtil toastUtil = new ToastUtil();
        toastUtil.Short(context, msgRes).setToastColor(Color.WHITE,
                context.getResources().getColor(R.color.colorAccent)).show();
    }

    public static void showLong(Context context, int msgRes) {
        ToastUtil toastUtil = new ToastUtil();
        toastUtil.Short(context, msgRes).setToastColor(Color.WHITE,
                context.getResources().getColor(R.color.colorAccent)).show();
    }
}

