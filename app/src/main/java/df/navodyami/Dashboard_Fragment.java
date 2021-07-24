package df.navodyami;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

import df.navodyami.adapter.postAdapter;

public class Dashboard_Fragment extends Fragment {
    public static Dashboard_Fragment newInstance() {
        Dashboard_Fragment fragment = new Dashboard_Fragment();
        return fragment;
    }

    private RecyclerView _recyclerView;
    private postAdapter _mAdapter;
    private RecyclerView.LayoutManager _layoutManager;
    RelativeLayout r1;
    ImageView _pickImage, _changeCover;
    List<String> listcount = new ArrayList();
    TextView username;
   /* public static final String sharedpreferenc_username = "googlelogin_name";
    public static final String Key_username = "name_googlelogin";
    SharedPreferences sharedpref_userimage_Obj;
    public static final String sharedpreferenc_userimage = "googlelogin_img";
    public static final String key_userimage = "profileimg_googlelogin";
    SharedPreferences sharedpref_username_Obj;*/



    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";

    SharedPreferences sharedpreferencebook_usercredential_Obj;
    SharedPreferences.Editor editor_obj;
    public static final String Key_username = "name_googlelogin";
    public static final String key_userimage = "profileimg_googlelogin";

    SharedPreferences sharedpref_flag_Obj;
    public static final String sharedpreferenc_flag = "flag_sharedpreference";
    public static final String key_flag = "flag";

    String str_Googlelogin_Username, str_Googlelogin_UserImg, str_loginuserID;
    String str_flag;

    public static final String KeyValue_employeeid = "KeyValue_employeeid";
    String str_UserId;

    ArrayList<Class_DashboardTotalCount> dashboardcountList;
    TextView Application_Count_tv,Enquiry_Count_tv;

    Boolean isInternetPresent = false;
    Class_InternetDectector internetDectector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_newdashboard, container, false);

       /* sharedpref_username_Obj=getActivity().getSharedPreferences(sharedpreferenc_username, Context.MODE_PRIVATE);
        str_Googlelogin_Username = sharedpref_username_Obj.getString(Key_username, "").trim();

        sharedpref_userimage_Obj=getActivity().getSharedPreferences(sharedpreferenc_userimage, Context.MODE_PRIVATE);
        str_Googlelogin_UserImg = sharedpref_userimage_Obj.getString(key_userimage, "").trim();*/

//        sharedpref_loginuserid_Obj=getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
//        str_loginuserID = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();

        sharedpreferencebook_usercredential_Obj=getActivity().getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);

        str_Googlelogin_Username = sharedpreferencebook_usercredential_Obj.getString(Key_username, "").trim();
        str_Googlelogin_UserImg = sharedpreferencebook_usercredential_Obj.getString(key_userimage, "").trim();
        str_UserId = sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeid, "").trim();

        sharedpref_flag_Obj=getActivity().getSharedPreferences(sharedpreferenc_flag, Context.MODE_PRIVATE);
        str_flag = sharedpref_flag_Obj.getString(key_flag, "").trim();

        Application_Count_tv = (TextView) rootView.findViewById(R.id.Application_Count_tv);
        Enquiry_Count_tv = (TextView) rootView.findViewById(R.id.Enquiry_Count_tv);

        internetDectector = new Class_InternetDectector(getContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        dashboardcountList = new ArrayList<>();
        listcount.add("30");
        _recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        _layoutManager = new LinearLayoutManager(getActivity());
        _recyclerView.setFocusable(false);
        r1 = (RelativeLayout) rootView.findViewById(R.id.id_r1);
        //create dummy data to show in list



        if(isInternetPresent) {
            Get_Dashboard_Events_Count get_dashboard_events_count = new Get_Dashboard_Events_Count(getActivity());
            get_dashboard_events_count.execute();

            Get_Dashboard_Enquiry_Application_Count get_dashboard_enquiry_application_count = new Get_Dashboard_Enquiry_Application_Count(getActivity());
            get_dashboard_enquiry_application_count.execute();
        }else {
            Toast toast= Toast.makeText(getContext(), "  No Internet Connection  " , Toast.LENGTH_LONG);
            View view =toast.getView();
            view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
            toast.show();
            Class_DashboardTotalCount class_dashboardTotalCount = new Class_DashboardTotalCount("0", "0", "0","0");
            dashboardcountList.add(class_dashboardTotalCount);
            _mAdapter = new postAdapter(dashboardcountList,listcount);
            _recyclerView.setAdapter(_mAdapter);
        }
        if(!str_flag.equals("1")) {
            if (SaveSharedPreference.getUserName(getContext()).length() == 0) {
//                Intent i = new Intent(Activity_HomeScreen.this, NormalLogin.class);
//                startActivity(i);
//                finish();
//                // call Login Activity
            } else {
                // Stay at the current activity.
            }
        }else {

            if (SaveSharedPreference.getUserName(getContext()).length() == 0) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                // finish();
                // call Login Activity
            } else {
                // Stay at the current activity.
            }
        }

        _pickImage=(ImageView) rootView.findViewById(R.id.profile_img);
        username = (TextView) rootView.findViewById(R.id.username);
        if(str_flag.equals("1")){
            username.setText("");
        }else {
            username.setText(str_Googlelogin_Username);
            try {
                Glide.with(this).load(str_Googlelogin_UserImg).into(_pickImage);
            } catch (NullPointerException e) {
                // Toast.makeText(getApplicationContext(),"image not found",Toast.LENGTH_LONG).show();
            }
        }
        _recyclerView.setHasFixedSize(true);
        _recyclerView.setLayoutManager(_layoutManager);

        ///  add items to the adapter
        //   _mAdapter = new postAdapter(list);

        ///  set Adapter to RecyclerView


      /*  _pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ittFeesPaidToHome = new Intent(getContext() , EnquiryApplicantListActivity.class);
                startActivity(ittFeesPaidToHome);
            }
        });*/

        /////*      add Drawable      */////
        r1.setBackground(AppCompatResources.getDrawable(getActivity(), R.drawable.ic_shape_background));
        return rootView;
    }


  /*  public class Get_Dashboard_Events_Count extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;
        ProgressDialog progressDialog;
        //private ProgressBar progressBar;

        Get_Dashboard_Events_Count (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapObject response = Get_Dashboard_Events_Count();

            return response;
        }

        @Override
        protected void onPreExecute() {
      *//*      progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*//*
            progressDialog.setMessage("Loading");
            progressDialog.setProgressStyle(android.R.attr.progressBarStyleSmall);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(SoapObject result) {

            *//*---------------------------------Soap Response----------------------------
          <Get_Dashboard_Events_CountResult>
        <vmDashboard_Events_Count>
          <Event_Id>int</Event_Id>
          <Event_Name>string</Event_Name>
          <Total>int</Total>
          <Status>string</Status>
        </vmDashboard_Events_Count>
        <vmDashboard_Events_Count>
          <Event_Id>int</Event_Id>
          <Event_Name>string</Event_Name>
          <Total>int</Total>
          <Status>string</Status>
        </vmDashboard_Events_Count>
      </Get_Dashboard_Events_CountResult>

             *//*
            if(result != null){

                SoapPrimitive S_Event_Id,S_Event_Name,S_Total,S_Business_Name,S_Status;
                Object O_Event_Id,O_Event_Name,O_Total,O_Business_Name,O_Status;
                String str_Event_Id = null,str_Event_Name = null,str_Total = null,str_Business_Name = null,str_Status = null;

                //  deleteListApplicationDetailsTable_B4insertion();
                for (int i = 0; i < result.getPropertyCount(); i++) {
                    SoapObject list = (SoapObject) result.getProperty(i);

                    Log.d("list=", list.toString());

                    O_Status = list.getProperty("Status");
                    if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                        S_Status = (SoapPrimitive) list.getProperty("Status");
                        Log.d("Status:", S_Status.toString());
                        str_Status = S_Status.toString();
                    }

                    if (str_Status.equalsIgnoreCase("Success")) {

                        O_Event_Id = list.getProperty("Event_Id");
                        if (!O_Event_Id.toString().equals("anyType{}") && !O_Event_Id.toString().equals(null)) {
                            S_Event_Id = (SoapPrimitive) list.getProperty("Event_Id");
                            str_Event_Id = S_Event_Id.toString();
                            Log.d("Event_Id", str_Event_Id);
                        }


                        O_Event_Name = list.getProperty("Event_Name");
                        if (!O_Event_Name.toString().equals("anyType{}") && !O_Event_Name.toString().equals(null)) {
                            S_Event_Name = (SoapPrimitive) list.getProperty("Event_Name");
                            str_Event_Name = S_Event_Name.toString();
                            Log.d("Event_Name", str_Event_Name);
                        }

                        O_Total = list.getProperty("Total");
                        if (!O_Total.toString().equals("anyType{}") && !O_Total.toString().equals(null)) {
                            S_Total = (SoapPrimitive) list.getProperty("Total");
                            Log.d("Total", S_Total.toString());
                            str_Total = S_Total.toString();
                            Log.d("Total", str_Total);
                        }

                        Class_DashboardTotalCount class_dashboardTotalCount = new Class_DashboardTotalCount(str_Event_Id, str_Event_Name, str_Total,str_Status);
                        dashboardcountList.add(class_dashboardTotalCount);
                        _mAdapter = new postAdapter(dashboardcountList,listcount);
                        _recyclerView.setAdapter(_mAdapter);
                    }else{
                       *//* feesList.clear();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(),"There are no Enquiries", Toast.LENGTH_LONG).show();*//*
                        Class_DashboardTotalCount class_dashboardTotalCount = new Class_DashboardTotalCount("0", "0", "0","0");
                        dashboardcountList.add(class_dashboardTotalCount);
                        _mAdapter = new postAdapter(dashboardcountList,listcount);
                        _recyclerView.setAdapter(_mAdapter);
                    }

                }

            }
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }*/
  public class Get_Dashboard_Events_Count extends AsyncTask<Void, Void, SoapObject> {

      Context context;
      AlertDialog alertDialog;
      ProgressDialog progressDialog;
      //private ProgressBar progressBar;

      Get_Dashboard_Events_Count (Context ctx){
          context = ctx;
          progressDialog = new ProgressDialog(context);
      }


      @Override
      protected SoapObject doInBackground(Void... params) {
          //String versionCode = (String) params[2];

          SoapObject response = Get_Dashboard_Events_Count();

          return response;
      }

      @Override
      protected void onPreExecute() {
      /*      progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/
          progressDialog.setMessage("Loading");
          progressDialog.setProgressStyle(android.R.attr.progressBarStyleSmall);
          progressDialog.setCanceledOnTouchOutside(false);
          progressDialog.show();
      }

      @Override
      protected void onPostExecute(SoapObject result) {

            /*---------------------------------Soap Response----------------------------
          <Get_Dashboard_Events_CountResult>
        <vmDashboard_Events_Count>
          <Event_Id>int</Event_Id>
          <Event_Name>string</Event_Name>
          <Total>int</Total>
          <Status>string</Status>
        </vmDashboard_Events_Count>
        <vmDashboard_Events_Count>
          <Event_Id>int</Event_Id>
          <Event_Name>string</Event_Name>
          <Total>int</Total>
          <Status>string</Status>
        </vmDashboard_Events_Count>
      </Get_Dashboard_Events_CountResult>

             */
          if(result != null){

              SoapPrimitive S_Event_Id,S_Event_Name,S_Total,S_Business_Name,S_Status;
              Object O_Event_Id,O_Event_Name,O_Total,O_Business_Name,O_Status;
              String str_Event_Id = null,str_Event_Name = null,str_Total = null,str_Business_Name = null,str_Status = null;

              //  deleteListApplicationDetailsTable_B4insertion();
              for (int i = 0; i < result.getPropertyCount(); i++) {
                  SoapObject list = (SoapObject) result.getProperty(i);

                  Log.d("list=", list.toString());

                  O_Status = list.getProperty("Status");
                  if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                      S_Status = (SoapPrimitive) list.getProperty("Status");
                      Log.d("Status:", S_Status.toString());
                      str_Status = S_Status.toString();
                  }

                  if (str_Status.equalsIgnoreCase("Success")) {

                      O_Event_Id = list.getProperty("Event_Id");
                      if (!O_Event_Id.toString().equals("anyType{}") && !O_Event_Id.toString().equals(null)) {
                          S_Event_Id = (SoapPrimitive) list.getProperty("Event_Id");
                          str_Event_Id = S_Event_Id.toString();
                          Log.d("Event_Id", str_Event_Id);
                      }


                      O_Event_Name = list.getProperty("Event_Name");
                      if (!O_Event_Name.toString().equals("anyType{}") && !O_Event_Name.toString().equals(null)) {
                          S_Event_Name = (SoapPrimitive) list.getProperty("Event_Name");
                          str_Event_Name = S_Event_Name.toString();
                          Log.d("Event_Name", str_Event_Name);
                      }

                      O_Total = list.getProperty("Total");
                      if (!O_Total.toString().equals("anyType{}") && !O_Total.toString().equals(null)) {
                          S_Total = (SoapPrimitive) list.getProperty("Total");
                          Log.d("Total", S_Total.toString());
                          str_Total = S_Total.toString();
                          Log.d("Total", str_Total);
                      }


                  }else{
                       /* feesList.clear();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(),"There are no Enquiries", Toast.LENGTH_LONG).show();*/
                  }
                  Class_DashboardTotalCount class_dashboardTotalCount = new Class_DashboardTotalCount(str_Event_Id, str_Event_Name, str_Total,str_Status);
                  dashboardcountList.add(class_dashboardTotalCount);
                  _mAdapter = new postAdapter(dashboardcountList,listcount);
                  _recyclerView.setAdapter(_mAdapter);
              }

          }
          progressDialog.dismiss();
      }

      @Override
      protected void onProgressUpdate(Void... values) {
          super.onProgressUpdate(values);
      }
  }


    private SoapObject Get_Dashboard_Events_Count() {
        String METHOD_NAME = "Get_Dashboard_Events_Count";
        String SOAP_ACTION1 = "http://mis.navodyami.org/Get_Dashboard_Events_Count";
        String NAMESPACE = "http://mis.navodyami.org/";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Login.toString().trim());

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                //SoapPrimitive response1 = (SoapPrimitive) envelope.getResponse();
                Log.d("tag","soapresponse Get_Dashboard_Events_Count="+envelope.getResponse().toString());


                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("tag","soapresponse Get_Dashboard_Events_Count="+response.toString());

                //return null;

                return response;

            }
            catch(OutOfMemoryError ex){
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
                final String exceptionStr = t.getMessage().toString();
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        catch(OutOfMemoryError ex){
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());
            final String exceptionStr = t.getMessage().toString();
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(),"Web Service Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;

    }


    public class Get_Dashboard_Enquiry_Application_Count extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;
        ProgressDialog progressDialog;
        //private ProgressBar progressBar;

        Get_Dashboard_Enquiry_Application_Count (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapObject response = Get_Dashboard_Enquiry_Application_Count();

            return response;
        }

        @Override
        protected void onPreExecute() {
      /*      progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/
            progressDialog.setMessage("Loading");
            progressDialog.setProgressStyle(android.R.attr.progressBarStyleSmall);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(SoapObject result) {

            /*---------------------------------Soap Response----------------------------
          <vmDashboard_Enquiry_Application_Count>
<Enquiry_Count>11</Enquiry_Count>
<Application_Count>16</Application_Count>
<Status>Success</Status>
</vmDashboard_Enquiry_Application_Count>

             */
            if(result != null){

                SoapPrimitive S_Event_Id,S_Event_Name,S_Total,S_Business_Name,S_Status;
                Object O_Event_Id,O_Event_Name,O_Total,O_Business_Name,O_Status;
                String str_Event_Id = null,str_Event_Name = null,str_Total = null,str_Business_Name = null,str_Status = null;

                //  deleteListApplicationDetailsTable_B4insertion();
                for (int i = 0; i < result.getPropertyCount(); i++) {
                    SoapObject list = (SoapObject) result.getProperty(i);

                    Log.d("list=", list.toString());

                    O_Status = list.getProperty("Status");
                    if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                        S_Status = (SoapPrimitive) list.getProperty("Status");
                        Log.d("Status:", S_Status.toString());
                        str_Status = S_Status.toString();
                    }

                    if (str_Status.equalsIgnoreCase("Success")) {

                        O_Event_Id = list.getProperty("Enquiry_Count");
                        if (!O_Event_Id.toString().equals("anyType{}") && !O_Event_Id.toString().equals(null)) {
                            S_Event_Id = (SoapPrimitive) list.getProperty("Enquiry_Count");
                            str_Event_Id = S_Event_Id.toString();
                            Log.d("Enquiry_Count", str_Event_Id);
                            Enquiry_Count_tv.setText(str_Event_Id);
                        }


                        O_Event_Name = list.getProperty("Application_Count");
                        if (!O_Event_Name.toString().equals("anyType{}") && !O_Event_Name.toString().equals(null)) {
                            S_Event_Name = (SoapPrimitive) list.getProperty("Application_Count");
                            str_Event_Name = S_Event_Name.toString();
                            Log.d("Application_Count", str_Event_Name);
                            Application_Count_tv.setText(str_Event_Name);
                        }

                    /*    O_Total = list.getProperty("Total");
                        if (!O_Total.toString().equals("anyType{}") && !O_Total.toString().equals(null)) {
                            S_Total = (SoapPrimitive) list.getProperty("Total");
                            Log.d("Total", S_Total.toString());
                            str_Total = S_Total.toString();
                            Log.d("Total", str_Total);
                        }*/


                    }else{
                       /* feesList.clear();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(),"There are no Enquiries", Toast.LENGTH_LONG).show();*/
                    }
                   /* Class_DashboardTotalCount class_dashboardTotalCount = new Class_DashboardTotalCount(str_Event_Id, str_Event_Name,str_Status);
                    dashboardcountList.add(class_dashboardTotalCount);*/

                }

            }
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapObject Get_Dashboard_Enquiry_Application_Count() {
        String METHOD_NAME = "Get_Dashboard_Enquiry_Application_Count";
        String SOAP_ACTION1 = "http://mis.navodyami.org/Get_Dashboard_Enquiry_Application_Count";
        String NAMESPACE = "http://mis.navodyami.org/";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("User_Id",str_UserId);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Login.toString().trim());

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                //SoapPrimitive response1 = (SoapPrimitive) envelope.getResponse();
                Log.d("tag","soapresponse Get_Dashboard_Enquiry_Application_Count="+envelope.getResponse().toString());


                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("tag","soapresponse Get_Dashboard_Enquiry_Application_Count="+response.toString());

                //return null;

                return response;

            }
            catch(OutOfMemoryError ex){
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
                final String exceptionStr = t.getMessage().toString();
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        catch(OutOfMemoryError ex){
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());
            final String exceptionStr = t.getMessage().toString();
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(),"Web Service Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;

    }

}
