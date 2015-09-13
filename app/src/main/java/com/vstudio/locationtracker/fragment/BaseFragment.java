package com.vstudio.locationtracker.fragment;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vstudio.locationtracker.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.vstudio.locationtracker.utils.StringUtils.isValidString;
import static com.vstudio.locationtracker.utils.UIUtils.animateImageAddition;
import static com.vstudio.locationtracker.utils.UIUtils.hideKeyboard;

public class BaseFragment extends DialogFragment implements View.OnClickListener {

    public final static String LOG_TAG = "LOCATION_TRACKER";

    private String title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                handleUncaughtException(thread, e);
            }
        });
    }

    public void handleUncaughtException(Thread thread, Throwable e) {
        e.printStackTrace();

        getActivity().finish();
    }

    @Override
    public void onClick(View view) {
        return;
    }

    @Override
    public void onResume() {
        super.onResume();

        hideKeyboard(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().invalidateOptionsMenu();

        ((ActionBarActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onStop() {
        super.onStop();
        // Log.i("", "NetworkManager is null");
        // networkManager = null;
    }

    public static void addToBackStack(Context context, BaseFragment fragment) {
        FragmentManager fragmentManager = ((ActionBarActivity) context).getSupportFragmentManager();

        Log.i(BaseFragment.LOG_TAG, "FM SIZE IN ADD BEFORE: " + fragmentManager.getBackStackEntryCount());

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null).commit();

        Log.i(BaseFragment.LOG_TAG, "FM SIZE IN ADD AFTER: " + fragmentManager.getBackStackEntryCount());
    }

    public static void replaceStack(Context context, BaseFragment fragment) {
        FragmentManager fragmentManager = ((ActionBarActivity) context).getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack(fragmentManager.getBackStackEntryAt(0).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.replace(R.id.container, fragment).commit();
    }

    public static void openAniamtedDialog(Dialog dialog) {
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        dialog.show();
    }

    protected void hideToolbar() {
        if (getActivity() != null) {
            ((ActionBarActivity) getActivity()).getSupportActionBar().hide();
        }
    }

    protected String getTextFromView(int id) {
        return ((TextView) getView().findViewById(id)).getText().toString();
    }

    protected void addDataForValidString(String value, int viewId) {
        Log.i(BaseFragment.LOG_TAG, value);
        if (!isValidString(value)) {
            value = "-";
        }
        ((TextView) getView().findViewById(viewId)).setText(value);

    }

    public static Typeface customTypefaceFromBase(Context context) {
        return Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.custom_font_path));
    }

    public void addImageToImageView(final Activity activity, final String imageUrl, int imageViewId) {
        final ImageView imageView = (ImageView) getView().findViewById(imageViewId);
        //imageView.setImageBitmap(null);

        if (isValidString(imageUrl)) {
            new AsyncTask<String, Bitmap, Bitmap>() {
                @Override
                protected Bitmap doInBackground(String... strings) {
                    return getBitmapFromUrl(imageUrl);
                }

                @Override
                protected void onPostExecute(final Bitmap imageBitmap) {
                    if(imageBitmap != null) {
                        addBitmapToView(activity, imageBitmap, imageView);
                    } else {
                        imageView.setVisibility(View.GONE);
                    }
                }
            }.execute();
        }
    }

    private Bitmap getBitmapFromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void addBitmapToView(Activity activity, final Bitmap imageBitmap, final ImageView imageView) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                animateImageAddition(imageBitmap, imageView);
            }
        });
    }

    public static void startServiceWithRunCheck(Context context, Class<?> serviceClass) {
        if (!BaseFragment.isMyServiceRunning(context, serviceClass)) {
            /*****START UPDATE APP SERVICE******/
            Intent updateAppService = new Intent(context, serviceClass);
            context.startService(updateAppService);
            /*********************************************/
        }
    }

    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * *******************************
     */

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
