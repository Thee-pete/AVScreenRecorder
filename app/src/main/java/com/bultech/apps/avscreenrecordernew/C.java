package com.bultech.apps.avscreenrecordernew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.CamcorderProfile;
import android.media.MediaMetadataRetriever;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codemybrainsout.ratingdialog.RatingDialog;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static android.app.Notification.GROUP_ALERT_SUMMARY;

public class C extends AppCompatActivity {
    private static final String TAG = "C";
    private static final int REQUEST_CODE = 1000;
    private int cms;
    private MediaProjectionManager cp;
    private static int cw = 720;
    private static int ch = 1280;
    private MediaProjection cme;
    private VirtualDisplay cmv;
    private MediaProjectionCallback cmx;
    private MediaRecorder cmr;
    private static final SparseIntArray cor = new SparseIntArray();
    private static final int crp = 10;

    String cf;
    List<A> cl;
    ListView clv;
    String cxy = "";
    FloatingActionButton cxv;
    public static List<String> cmy;
    File file;
    File clt;
    long cld;
    private static final String YES_ACTION = "YES_ACTION";
    private NotificationManager notificationManager;


    private AdView adView;
    private InterstitialAd interstitialAd;



    static {
        cor.append(Surface.ROTATION_0, 90);
        cor.append(Surface.ROTATION_90, 0);
        cor.append(Surface.ROTATION_180, 270);
        cor.append(Surface.ROTATION_270, 180);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.titlemain, null);
        ((TextView)v.findViewById(R.id.titlemain)).setText(R.string.app_name);


        this.getSupportActionBar().setCustomView(v);


        AudienceNetworkAds.initialize(this);



        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown


        adView = new AdView(this, "191236043006931_191246996339169", AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.ad_container);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        // Request an ad
        adView.loadAd();


        cl = new ArrayList<>();
        clv = (ListView) findViewById(R.id.cmy1);
        cxv = (FloatingActionButton) findViewById(R.id.fav);
        cxy = "s";
        //creating the adapter
        B adapter = new B(this, R.layout.custom_listview, cl);
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "Screen Recording");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
            }
        }
        jqjdd();
        Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        cw = display.getWidth();
        ch = display.getHeight();
        cxv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cxy.equals("s")){
                    ctgh();
                } else if (cxy.equals("t")){
                    notificationManager.cancel(1);
                    cxy = "s";
                    cxv.setImageResource(R.drawable.ic_recode);
                    ctgdd();
                    fhjbv();
                    cfhgw();
                }
            }
        });
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        cms = metrics.densityDpi;
        cmr = new MediaRecorder();
        cp = (MediaProjectionManager) getSystemService
                (Context.MEDIA_PROJECTION_SERVICE);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        jfcfdfh(getIntent());


        final RatingDialog ratingDialog = new RatingDialog.Builder(this)
                .threshold(0)
                .session(3)
                .onRatingBarFormSumbit(new RatingDialog.Builder.RatingDialogFormListener() {
                    @Override
                    public void onFormSubmitted(String feedback) {

                    }
                }).build();

        ratingDialog.show();



    }

    private void jqjdd(){
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO)
                .check();
    }
    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            fhjbv();
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(C.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }
    };
    public void cf(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
        Date now = new Date();
        cf = formatter.format(now);
    }
    @SuppressLint("DefaultLocale")
    public static String fdfdfbg(long seconds) {
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(seconds),
                TimeUnit.MILLISECONDS.toMinutes(seconds) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(seconds)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(seconds) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(seconds)));
    }
    public static String fdfhcvq(long size) {
        if (size <= 0)
            return "0";
        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
    public void fhjbv(){
        cl.clear();
        cmy = new ArrayList<String>();
        File directory = Environment.getExternalStorageDirectory();
        file = new File(directory + "/Screen Recording");
        File list[] = file.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String cf) {
                // TODO Auto-generated method stub
                if (cf.contains(".mp4")) {
                    return true;
                }
                return false;
            }
        });

        for (int i = 0; i < list.length; i++) {
            cmy.add(list[i].getName());
            String filepath = Environment.getExternalStorageDirectory() + "/Screen Recording/"+list[i].getName();
            File file = new File(filepath);
            long length = file.length();
            if (length < 1024){
                clt = new File(Environment.getExternalStorageDirectory() + "/Screen Recording/"+list[i].getName());
                clt.delete();
            } else {
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                retriever.setDataSource(getApplicationContext(), Uri.parse(Environment.getExternalStorageDirectory() + "/Screen Recording/"+list[i].getName()));
                String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                cld = Long.parseLong(time );
            }
            cl.add(new A("video.png", list[i].getName(), ""+fdfdfbg(cld),"Size : "+ fdfhcvq(length)));

            B adapter = new B(this, R.layout.custom_listview, cl);
            //attaching adapter to the listview


            clv.setAdapter(adapter);



        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, cmy);
        clv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(Environment.getExternalStorageDirectory() + "/Screen Recording/"+cmy.get(position));
                intent.setDataAndType(uri, "video/*");
                startActivity(intent);



            }
        });

       if (adapter.getCount()==0){
           LinearLayout emptylayout= findViewById(R.id.textempty);
           emptylayout.setVisibility(View.VISIBLE);
       }else{
           LinearLayout emptylayout= findViewById(R.id.textempty);
           emptylayout.setVisibility(View.INVISIBLE);
       }


    }
    private Intent fflvv() {
        Intent intent = new Intent(this, C.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return intent;
    }

    public void fflnke() {
        Intent yesIntent = fflvv();
        yesIntent.setAction(YES_ACTION);
        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("AV Screen Recorder")
                .setContentText("Recording")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(false)
                .setOngoing(true)
                .setUsesChronometer(true)
                .setVibrate(null)
                .setGroupAlertBehavior(GROUP_ALERT_SUMMARY)
                .setGroup("My group")
                .setGroupSummary(false)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .addAction(new NotificationCompat.Action(
                        R.drawable.ic_close,
                        "STOP",
                        PendingIntent.getActivity(this, 0, yesIntent, PendingIntent.FLAG_UPDATE_CURRENT)));
        notificationManager.notify(notificationId, mBuilder.build());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        jfcfdfh(intent);
        super.onNewIntent(intent);
    }
    private void jfcfdfh(Intent intent) {
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case YES_ACTION:
                    notificationManager.cancel(1);
                    cxy = "s";
                    cxv.setImageResource(R.drawable.ic_recode);
                    ctgdd();
                    fhjbv();
                    cfhgw();
                    break;
            }
        }
    }

    public void ctgh(){
        if (ContextCompat.checkSelfPermission(C.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) + ContextCompat
                .checkSelfPermission(C.this,
                        Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (C.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (C.this, Manifest.permission.RECORD_AUDIO)) {
            } else {
                ActivityCompat.requestPermissions(C.this,
                        new String[]{Manifest.permission
                                .WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO},
                        crp);
            }
        } else {
            jfhft();
            ceendf();
        }
    }

    public void ctgdd(){
        cmr.stop();
        cmr.reset();
        Log.v(TAG, "Stopping Recording");
        fffhqeo();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_CODE) {
            Log.e(TAG, "Unknown request code: " + requestCode);
            return;
        }
        if (resultCode != RESULT_OK) {
            Toast.makeText(this, "Screen Cast Permission Denied", Toast.LENGTH_SHORT).show();
            notificationManager.cancel(1);
            fhjbv();
            cxy = "s";
            cxv.setImageResource(R.drawable.ic_recode);

            return;
        } else {
            cxy = "t";
            cxv.setImageResource(R.drawable.ic_clear);
            fflnke();
            cddvn();
            cfdft();
        }
        cmx = new MediaProjectionCallback();
        cme = cp.getMediaProjection(resultCode, data);
        cme.registerCallback(cmx, null);
        cmv = createVirtualDisplay();
        cmr.start();
    }
    public void cddvn() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
    public void cfdft(){
        interstitialAd = new InterstitialAd(this, "191236043006931_191246553005880");
        // Create listeners for the Interstitial Ad
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        };
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());

    }
    public void cfhgw(){

    }
    private void ceendf() {
        if (cme == null) {
            startActivityForResult(cp.createScreenCaptureIntent(), REQUEST_CODE);
            return;
        }
        cmv = createVirtualDisplay();
        cmr.start();
    }

    private VirtualDisplay createVirtualDisplay() {
        return cme.createVirtualDisplay("C",
                cw, ch, cms,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                cmr.getSurface(), null /*Callbacks*/, null
                /*Handler*/);
    }

    private void jfhft() {

        cf();

        try {
            CamcorderProfile profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
            cmr.setAudioSource(MediaRecorder.AudioSource.MIC);
            cmr.setVideoSource(MediaRecorder.VideoSource.SURFACE);
            cmr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            cmr.setOutputFile(Environment.getExternalStorageDirectory()+"/Screen Recording" + "/Screen Recording "+cf+".mp4");
            cmr.setVideoSize(cw, ch);
            cmr.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            cmr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            cmr.setVideoEncodingBitRate(profile.videoBitRate);
            cmr.setCaptureRate(20);
            cmr.setVideoFrameRate(20);
            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            int orientation = cor.get(rotation + 90);
            cmr.setOrientationHint(orientation);
            cmr.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private class MediaProjectionCallback extends MediaProjection.Callback {
        @Override
        public void onStop() {
            cmr.stop();
            cmr.reset();
            Log.v(TAG, "Recording Stopped");

            cme = null;
            fffhqeo();
        }
    }

    private void fffhqeo() {
        if (cmv == null) {
            return;
        }
        cmv.release();

        jfcdv();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        jfcdv();
    }

    private void jfcdv() {
        if (cme != null) {
            cme.unregisterCallback(cmx);
            cme.stop();
            cme = null;
        }
        Log.i(TAG, "MediaProjection Stopped");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case crp: {
                if ((grantResults.length > 0) && (grantResults[0] +
                        grantResults[1]) == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share ){

            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                String sAux = "\n "+getString(R.string.app_name)+" - Download Now\n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id="+getPackageName()+" \n\n";
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "choose one"));
            } catch(Exception e) {

            }
        }

        switch (item.getItemId())
        {
            case R.id.menu_rate:

                Intent intent1 = new Intent(Intent.ACTION_VIEW);
                intent1.setData(Uri.parse(
                        "https://play.google.com/store/apps/details?id=com.bultech.apps.avscreenrecorder"));
                intent1.setPackage("com.android.vending");
                startActivity(intent1);

                return true;

            case R.id.menu_more:

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id="+getString(R.string.Devloper_ID))));
                } catch (android.content.ActivityNotFoundException anfe) {
                }

                return true;

            case R.id.menu_privacy:

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.privacy_policy_url))));
                } catch (android.content.ActivityNotFoundException anfe) {
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}