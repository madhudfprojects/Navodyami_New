package df.navodyami;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Random;

public class SplashActivity extends AppCompatActivity {


    WebView webview_splashscreen;
    Handler handler;



    public static final String PREFBook_LoginTrack= "prefbook_logintrack";  //sharedpreference Book
    public static final String PrefID_WhereToGo = "prefid_wheretogo"; //
    SharedPreferences shardprefLoginTrack_obj;
    SharedPreferences.Editor editor_LoginTrack;
    String str_wheretogo;
    Intent intent = null;

    String Str_status,o_versionresponse,o_versioncode;
    float soapprimitive_versionfloat,versionCodes;
    int soapprimitive_versionInteger,versionCodes_int;


    Class_InternetDectector internetDectector, internetDectector2;
    Boolean isInternetPresent = false;

    Thread splashTread;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
      //  webview_splashscreen = (WebView)findViewById(R.id.ac);

        imageView = (ImageView)findViewById(R.id.splash);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        shardprefLoginTrack_obj=getSharedPreferences(PREFBook_LoginTrack, Context.MODE_PRIVATE);
        str_wheretogo = shardprefLoginTrack_obj.getString(PrefID_WhereToGo, "").trim();

       // if(str_wheretogo!="" || str_wheretogo!=null || !(str_wheretogo.trim().length()==0)) {




        try {
            versionCodes = Float.parseFloat(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        try {
            versionCodes_int = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


       // versionCodes_int=555;
       // Toast.makeText(getApplicationContext(),"versioncode"+Float.toString(versionCodes),Toast.LENGTH_LONG).show();



        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

       /* if (isInternetPresent)
        {
            VersionCheckAsyncCallWS task = new VersionCheckAsyncCallWS(SplashActivity.this);
            task.execute();
        }
        else
        {*/
            if(SaveSharedPreference.getUserName(SplashActivity.this).length() == 0)
            {
                // call Login Activity
               /* Intent i=new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();*/
            }
            else
            {
                Intent i=new Intent(SplashActivity.this, BottomActivity.class);
                i.putExtra("frgToLoad", "1");
                startActivity(i);
                finish();

                // Stay at the current activity.
            }
          /*  Toast.makeText(getApplicationContext(),"No Internet Connected", Toast.LENGTH_LONG).show();
            finish();*/
      //  }




    /*    if(!(str_wheretogo.trim().length()==0))
        {
           // Toast.makeText(getApplicationContext(),"whereToGo:"+str_wheretogo.toString(),Toast.LENGTH_SHORT).show();
            webview_splashscreen.loadUrl("file:///android_asset/lead4_14mb.gif");
            webview_splashscreen.getSettings().setLoadWithOverviewMode(true);
            webview_splashscreen.getSettings().setUseWideViewPort(true);
            handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run()
                {
                    try {
                        Class<?> classname = Class.forName(str_wheretogo);
                        Intent intent = new Intent(SplashActivity.this, classname);
                        startActivity(intent);
                        finish();
                    } catch (ClassNotFoundException ignored) {
                    }
                }
            }, 4500);




        } else
        {
            webview_splashscreen.loadUrl("file:///android_asset/lead4_14mb.gif");
            webview_splashscreen.getSettings().setLoadWithOverviewMode(true);
            webview_splashscreen.getSettings().setUseWideViewPort(true);
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 4500);
            //},50);

        }*/
    }// end of Oncreate







    private class VersionCheckAsyncCallWS extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
            /*dialog.setMessage("Please wait,State Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
*/
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //Log.i(TAG, "onProgressUpdate---tab2");
        }


        @Override
        protected Void doInBackground(String... params) {
            Log.i("df", "doInBackground");

            GetAppReleasedDetails();  // get the App Release Details
            return null;
        }

        public VersionCheckAsyncCallWS(SplashActivity activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPostExecute(Void result) {

       /* if ((this.dialog != null) && this.dialog.isShowing()) {
            dialog.dismiss();

        }*/
            dialog.dismiss();

            //if(result!=null) {

                if (Str_status.equalsIgnoreCase("Success")) {

                    if (soapprimitive_versionInteger == 555) {
                        alerts_dialogMaintenance();
                    } else {


                        if (versionCodes_int >= soapprimitive_versionInteger) //9>=10
                        {

                            if (!(str_wheretogo.trim().length() == 0))
                            {
                               /* // Toast.makeText(getApplicationContext(),"whereToGo:"+str_wheretogo.toString(),Toast.LENGTH_SHORT).show();
                                webview_splashscreen.loadUrl("file:///android_asset/lead4_14mb.gif");
                                webview_splashscreen.getSettings().setLoadWithOverviewMode(true);
                                webview_splashscreen.getSettings().setUseWideViewPort(true);
                                handler = new Handler();

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Class<?> classname = Class.forName(str_wheretogo);
                                            Intent intent = new Intent(SplashActivity.this, classname);
                                            startActivity(intent);
                                            finish();
                                        } catch (ClassNotFoundException ignored) {
                                        }
                                    }
                                }, 4500);*/
                                setAnimations();
                                int[] ids = new int[]{R.drawable.navo_png,R.drawable.navo_png, R.drawable.navo_png};
                                Random randomGenerator = new Random();
                                int r= randomGenerator.nextInt(ids.length);
                                imageView.setImageDrawable(getResources().getDrawable(ids[r]));

                                splashTread = new Thread() {
                                    @Override
                                    public void run() {
                                        try {
                                            int waited = 0;
                                            // Splash screen pause time
                                            while (waited < 3500) {
                                                sleep(100);
                                                waited += 100;
                                            }
                                            Intent intent = new Intent(SplashActivity.this,
                                                    MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                            startActivity(intent);
                                            SplashActivity.this.finish();
                                        } catch (InterruptedException e) {
                                            // do nothing
                                        } finally {
                                            SplashActivity.this.finish();
                                        }

                                    }
                                };
                                splashTread.start();


                            } else {
                               /* webview_splashscreen.loadUrl("file:///android_asset/lead4_14mb.gif");
                                webview_splashscreen.getSettings().setLoadWithOverviewMode(true);
                                webview_splashscreen.getSettings().setUseWideViewPort(true);
                                handler = new Handler();

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, 4500);*/
                                //},50);
                            setAnimations();
                            int[] ids = new int[]{R.drawable.navo_png,R.drawable.navo_png, R.drawable.navo_png};
                            Random randomGenerator = new Random();
                            int r= randomGenerator.nextInt(ids.length);
                            imageView.setImageDrawable(getResources().getDrawable(ids[r]));

                            splashTread = new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        int waited = 0;
                                        // Splash screen pause time
                                        while (waited < 3500) {
                                            sleep(100);
                                            waited += 100;
                                        }
                                        Intent intent = new Intent(SplashActivity.this,
                                                MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        startActivity(intent);
                                        SplashActivity.this.finish();
                                    } catch (InterruptedException e) {
                                        // do nothing
                                    } finally {
                                        SplashActivity.this.finish();
                                    }

                                }
                            };
                            splashTread.start();

                            }


                        } else {
                            //alerts();
                            alerts_dialog();
                        }


                    }
                }else{
                    Toast toast= Toast.makeText(SplashActivity.this, "  Webservice Error"+Str_status+"  " , Toast.LENGTH_LONG);
                    View view =toast.getView();
                    view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                    toast.show();
                }

                //}




        }

    private void setAnimations() {

        Animation anim = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.alpha);
        anim.reset();
        LinearLayout l = (LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        Animation anim1 = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.translate);
        imageView.clearAnimation();
        imageView.startAnimation(anim1);
    }
        public void GetAppReleasedDetails()
        {
            String URL = Class_URL.URL_Login.toString().trim();
            String METHOD_NAME = "GetAppReleasedDetails";
            String Namespace = "http://mis.navodyami.org/", SOAPACTION = "http://mis.navodyami.org/GetAppReleasedDetails";

            try {

                SoapObject request = new SoapObject(Namespace, METHOD_NAME);
                // request.addProperty("stateid", "0");//<stateid>long</stateid>

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                //Set output SOAP object
                envelope.setOutputSoapObject(request);
                //Create HTTP call object
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);


                /*<AppVersion>string</AppVersion>
        <versionCode>string</versionCode>
        <Status>string</Status>*/

                try {

                    androidHttpTransport.call(SOAPACTION, envelope);
                    //  Log.i(TAG, "GetAllLoginDetails is running");
                    //    result1 = (Vector<SoapObject>) envelope.getResponse();

                    // response = (SoapPrimitive) envelope.getResponse();

                    // SoapPrimitive res =(SoapPrimitive)envelope.getResponse();

                    SoapObject response = (SoapObject) envelope.getResponse();
                    Log.e("response",response.toString());

                    o_versionresponse = response.getProperty("AppVersion").toString();
                    o_versioncode = response.getProperty("versionCode").toString();
                 //   str_misscallno=response.getProperty("MissCallNumber").toString();
                    Str_status = response.getProperty("Status").toString(); //Success

                    soapprimitive_versionfloat = Float.parseFloat(o_versionresponse);
                    soapprimitive_versionInteger = Integer.parseInt(o_versioncode);


                    /*editor_generalbook=shardprefgeneralbook_obj.edit();
                    editor_generalbook.putString(PREFBook_generalbook, str_misscallno);
                    editor_generalbook.commit();*/

//AppVersion
                    Log.e("version", o_versioncode.toString());


                } catch (Throwable t) {
                    //Toast.makeText(context, "Request failed: " + t.toString(),
                    //    Toast.LENGTH_LONG).show();
                    Log.e("request fail", "> " + t.getMessage());
                    //internet_issue = "slow internet";
                }
            } catch (Throwable t) {
                //Toast.makeText(context, "UnRegister Receiver Error " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Log.e("UnRegister Error", "> " + t.getMessage());
            }


        }
    }









    public  void alerts_dialog()
    {

        AlertDialog.Builder dialog = new AlertDialog.Builder(SplashActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("Alert");
       // dialog.setMessage("Kindly update from playstore");
        dialog.setMessage("Kindly contact Tech team for new version");
        dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
               /* Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.leadcampusapp"));
                startActivity(intent);*/
                finish();
            }
        });


        final AlertDialog alert = dialog.create();
        alert.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
            }
        });
        alert.show();

    }




    public  void alerts_dialogMaintenance()
    {

        AlertDialog.Builder dialog = new AlertDialog.Builder(SplashActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("Alert");
        dialog.setMessage("Sorry for inconvenience application is under maintenance!");
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                finish();
            }
        });


        final AlertDialog alert = dialog.create();
        alert.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
            }
        });
        alert.show();


    }















}//end of class
