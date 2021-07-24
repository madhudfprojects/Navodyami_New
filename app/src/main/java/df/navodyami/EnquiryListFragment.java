package df.navodyami;

/**
 * Created by Madhu.
 */

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;

public class EnquiryListFragment extends Fragment
{
    private ListView lview;
    private ArrayList<EnquiryListModel> feesList;
    private EnquiryListAdapter adapter;

    private LinkedHashSet<String> collegeNameLst;
    private ArrayList<String> collegeNameArrLst;
   // private AppCompatSpinner spin_college;
    private ArrayList<EnquiryListModel> originalList = null;
    private ProgressDialog progressDialog;
    private EditText etSearch;
    private String EnquiryID;
    private int counter=0;
    private Spinner spin_year;
    private HashMap<String,Integer> hashYearId = new HashMap<String,Integer>();
    int acadamicCode;

    Class_InternetDectector internetDectector, internetDectector2;
    Boolean isInternetPresent = false;

    Class_YearListDetails[] arrayObj_Class_yearDetails2;
    Class_YearListDetails Obj_Class_yearDetails;
    int sel_yearsp=0;
    String selected_year, sp_stryear_ID;

    SharedPreferences sharedpreferencebook_usercredential_Obj;
    SharedPreferences.Editor editor_obj;
    String str_UserId;
    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_employeeid = "KeyValue_employeeid";

    String TempId="0",dataSyncStatus;
    LinearLayout offline_count;
    TextView new_enquiryCount,edit_enquiryCount;

    public static final String sharedpreferencebook_OfflineEnquiryData = "sharedpreferencebook_OfflineEnquiryData";
    public static final String KeyValue_EnquiryEdited = "KeyValue_EnquiryEdited";
    String str_EnquiryEdited;
    ArrayList<String> EditedEnquiryId;
    ArrayList<String> EditedTempId;
    SharedPreferences sharedpreferencebook_OfflineEnquiryData_Obj;
    SharedPreferences.Editor editorEnquiry_obj;
    String status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.frag_enquiry_list, container, false);

        lview = (NonScrollListView) view.findViewById(R.id.listview_enquirylist);
        offline_count=(LinearLayout) view.findViewById(R.id.offline_count);
        new_enquiryCount =(TextView) view.findViewById(R.id.new_enquiryCount);
        edit_enquiryCount = (TextView) view.findViewById(R.id.edit_enquiryCount);

        internetDectector = new Class_InternetDectector(getActivity());
        isInternetPresent = internetDectector.isConnectingToInternet();

        sharedpreferencebook_usercredential_Obj = getActivity().getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_UserId = sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeid, "").trim();

        sharedpreferencebook_OfflineEnquiryData_Obj=getActivity().getSharedPreferences(sharedpreferencebook_OfflineEnquiryData, Context.MODE_PRIVATE);
        str_EnquiryEdited = sharedpreferencebook_OfflineEnquiryData_Obj.getString(KeyValue_EnquiryEdited, "").trim();

        Log.e("tag","str_EnquiryEdited="+str_EnquiryEdited);


        EditedEnquiryId= new ArrayList<>();
        EditedTempId = new ArrayList<>();
     //   if(isInternetPresent){
        offline_count.setVisibility(View.GONE);

        if(isInternetPresent){
            getEnquiryOfflineDataSync();
        }else{
            Add_Edit_offlineEnquiryCount();
        }
         /*   AddEnquiryDetails_Offline();
            if(str_EnquiryEdited.equals("1")) {
                EditEnquiryDetails_Count();
            }*/
      /*  }else{
            Toast.makeText(getActivity(),"There is no internet connection data will save in offline", Toast.LENGTH_SHORT).show();
        }*/
        spin_year= (Spinner) view.findViewById(R.id.spin_year);
        feesList = new ArrayList<EnquiryListModel>();


     /*   shardprefPM_obj = getActivity().getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);
        shardprefPM_obj.getString(PrefID_PMID, "").trim();
        str_MangerID = shardprefPM_obj.getString(PrefID_PMID, "").trim();
        Log.d("str_MangerID:",str_MangerID);*/

      /*  collegeNameLst = new LinkedHashSet<String>();
        collegeNameLst.add("Select Year");
        collegeNameArrLst = new ArrayList<String>();*/

    //    spin_college = (AppCompatSpinner) view.findViewById(R.id.spin_college);

       /* txt_studRegistered = (TextView) view.findViewById(R.id.txt_studRegistered);
        txt_feesPaidStudents = (TextView) view.findViewById(R.id.txt_feesPaidStudents);*/

      /* if(isInternetPresent) {
           deleteYearTable_B4insertion();
           populateAcademicYear();
       }else{*/
           uploadfromDB_Yearlist();
           //List_offlineEnquiryDetails();
      // }



        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent i = new Intent(getContext(), AddEnquiryActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });
//populateList();

        etSearch = (EditText) view.findViewById(R.id.etSearch);
     /*   etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
//                String selectedCollege = spin_college.getSelectedItem().toString();

               *//* if(!selectedCollege.equals("Select Year")) {
                    String text = etSearch.getText().toString();
                    adapter.filter(text, originalList, selectedCollege);
                }else{*//*
                    String text = etSearch.getText().toString();
                    adapter.filter(text, originalList, null);
               // }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //adapter.getFilter().filter(s.toString());
           *//*     String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text,originalList);*//*

               *//* String text2 = s.toString();
                if(text2.equals("")) {
                    String text = spin_college.getSelectedItem().toString();
                    if(text!="Select Year") {*//*

                        adapter.filter(text, originalList, null);
                   *//* }
                }*//*
            //}
        });*/

        spin_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_yearDetails = (Class_YearListDetails) spin_year.getSelectedItem();
                sp_stryear_ID = Obj_Class_yearDetails.getYearID().toString();
                selected_year = spin_year.getSelectedItem().toString();
                int sel_yearsp_new = spin_year.getSelectedItemPosition();
                //  farmerListViewAdapter.notifyDataSetChanged();

                acadamicCode=Integer.valueOf(sp_stryear_ID);
                if(sel_yearsp_new!=sel_yearsp) {
                    sel_yearsp=sel_yearsp_new;
                }
                if(isInternetPresent){
                    populateList();
                }else {
                    List_offlineEnquiryDetails();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }
            ;
            @Override
            public void afterTextChanged(Editable s) {
                //adapter.getFilter().filter(s.toString());
                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text,originalList,null);
            }
        });
        adapter = new EnquiryListAdapter(getActivity(),feesList);
        lview.setAdapter(adapter);
        /*spin_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                acadamicCode = hashYearId.get(spin_year.getSelectedItem());
                Log.e("tag","acadamicCode="+acadamicCode);

                populateList();
               // adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/


        // showActivity();
        return view;
    }

    private void populateAcademicYear() {
        if (isInternetPresent) {
            AcademicYear academicYear = new AcademicYear(getActivity());
            academicYear.execute();
        }

    }

    private void populateList() {

        GetEnquiryListing getEnquiryListing = new GetEnquiryListing(getActivity());
        getEnquiryListing.execute();

    }

    public class GetEnquiryListing extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        //private ProgressBar progressBar;

        GetEnquiryListing (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapObject response = getEnquiryListing();

            //Log.d("Soap response is",response.toString());

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
            <vmEnquiry_Listing>
<EnquiryId>10</EnquiryId>
<FirstName>offline five</FirstName>
<MiddleName>test</MiddleName>
<LastName>test</LastName>
<MobileNo>885522233</MobileNo>
<EmailId>test</EmailId>
<StateId>12</StateId>
<DistrictId>557</DistrictId>
<TalukaId>5500</TalukaId>
<VillageId>7748</VillageId>
<PanchayatId>0</PanchayatId>
<SectorId>9</SectorId>
<BusinessName>test</BusinessName>
<isApplicant>0</isApplicant>
<Status>Success</Status>
</vmEnquiry_Listing>

             */
            if(result != null){
                SoapPrimitive S_StateId,S_DistrictId,S_TalukaId,S_VillageId,S_SectorId,S_LeadId, S_StudentName, S_RegistrationDate, S_CollegeName, S_MobileNo, S_IsFeesPaid, S_Status,S_Gender,S_MiddleName,S_LastName,S_isApplicant;
                Object O_StateId,O_DistrictId,O_TalukaId,O_VillageId,O_SectorId,O_LeadId, O_StudentName, O_RegistrationDate, O_CollegeName, O_MobileNo, O_IsFeesPaid, O_Status,O_Gender,O_MiddleName,O_LastName,O_isApplicant;
                String str_StateId=null,str_DistrictId=null,str_TalukaId=null,str_VillageId=null,str_SectorId=null,str_leadid = null, str_studentName = null, str_registrationDate = null, str_collegeName = null, str_MobileNo = null, str_IsFeesPaid = null, str_Status = null,str_MiddleName=null,str_LastName=null,str_isApplicant=null,str_gender=null;

                deleteListEnquiryDetailsTable_B4insertion();
                feesList.clear();
                for (int i = 0; i < result.getPropertyCount(); i++) {
                    SoapObject list = (SoapObject) result.getProperty(i);

                    //Log.d("DistrictResult", list.toString());

                    O_Status = list.getProperty("Status");
                    if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                        S_Status = (SoapPrimitive) list.getProperty("Status");
                        Log.d("Status:", S_Status.toString());
                        str_Status = S_Status.toString();
                    }

                    if (str_Status.equalsIgnoreCase("Success")) {

                        O_StudentName = list.getProperty("FirstName");
                        if (!O_StudentName.toString().equals("anyType{}") && !O_StudentName.toString().equals(null)) {
                            S_StudentName = (SoapPrimitive) list.getProperty("FirstName");
                            str_studentName = S_StudentName.toString();
                            Log.d("FirstName", str_studentName);
                        }

                        O_LeadId = list.getProperty("EnquiryId");
                        if (!O_LeadId.toString().equals("anyType{}") && !O_LeadId.toString().equals(null)) {
                            S_LeadId = (SoapPrimitive) list.getProperty("EnquiryId");
                            str_leadid = S_LeadId.toString();
                            Log.d("str_EnquiryId", str_leadid);
                        }

                        O_RegistrationDate = list.getProperty("MobileNo");
                        if (!O_RegistrationDate.toString().equals("anyType{}") && !O_RegistrationDate.toString().equals(null)) {
                            S_RegistrationDate = (SoapPrimitive) list.getProperty("MobileNo");
                            Log.d("S_MobileNo", S_RegistrationDate.toString());
                            str_registrationDate = S_RegistrationDate.toString();
                            Log.d("str_MobileNo", str_registrationDate);
                        }

                        O_CollegeName = list.getProperty("BusinessName");
                        if (!O_CollegeName.toString().equals("anyType{}") && !O_CollegeName.toString().equals(null)) {
                            S_CollegeName = (SoapPrimitive) list.getProperty("BusinessName");
                            Log.d("S_BusinessName", S_CollegeName.toString());
                            str_collegeName = S_CollegeName.toString();
                            Log.d("str_BusinessName", str_collegeName);

                           // collegeNameLst.add(str_collegeName);

                        }
                        if (O_CollegeName.toString().equals("anyType{}") || O_CollegeName.toString().equals(null)) {
                            str_collegeName = "";
                            //collegeNameLst.add(str_collegeName);
                        }

                        O_MobileNo = list.getProperty("EmailId");
                        if (!O_MobileNo.toString().equals("anyType{}") && !O_MobileNo.toString().equals(null)) {
                            S_MobileNo = (SoapPrimitive) list.getProperty("EmailId");
                            Log.d("S_EmailId", S_MobileNo.toString());
                            str_MobileNo = S_MobileNo.toString();
                        }

                        O_MiddleName = list.getProperty("MiddleName");
                        if (!O_MiddleName.toString().equals("anyType{}") && !O_MiddleName.toString().equals(null)) {
                            S_MiddleName = (SoapPrimitive) list.getProperty("MiddleName");
                            str_MiddleName = S_MiddleName.toString();
                            Log.d("MiddleName", str_MiddleName);
                        }

                        O_LastName = list.getProperty("LastName");
                        if (!O_LastName.toString().equals("anyType{}") && !O_LastName.toString().equals(null)) {
                            S_LastName = (SoapPrimitive) list.getProperty("LastName");
                            str_LastName = S_LastName.toString();
                            Log.d("LastName", str_LastName);
                        }

                        O_isApplicant = list.getProperty("isApplicant");
                        if (!O_isApplicant.toString().equals("anyType{}") && !O_isApplicant.toString().equals(null)) {
                            S_isApplicant = (SoapPrimitive) list.getProperty("isApplicant");
                            Log.d("S_isApplicant", S_isApplicant.toString());
                            str_isApplicant = S_isApplicant.toString();
                        }

                        O_StateId = list.getProperty("StateId");
                        if (!O_StateId.toString().equals("anyType{}") && !O_StateId.toString().equals(null)) {
                            S_StateId = (SoapPrimitive) list.getProperty("StateId");
                            Log.d("S_StateId", S_StateId.toString());
                            str_StateId = S_StateId.toString();
                            //  sel_statesp = Integer.parseInt(S_StateId.toString());
                        }
                        O_DistrictId = list.getProperty("DistrictId");
                        if (!O_DistrictId.toString().equals("anyType{}") && !O_DistrictId.toString().equals(null)) {
                            S_DistrictId = (SoapPrimitive) list.getProperty("DistrictId");
                            Log.d("str_DistrictId", S_DistrictId.toString());
                            str_DistrictId = S_DistrictId.toString();
                            // sel_districtsp = Integer.parseInt(S_DistrictId.toString());
                            //   districtlist_SP.setSelection(sel_districtsp);

                        }
                        O_TalukaId = list.getProperty("TalukaId");
                        if (!O_TalukaId.toString().equals("anyType{}") && !O_TalukaId.toString().equals(null)) {
                            S_TalukaId = (SoapPrimitive) list.getProperty("TalukaId");
                            Log.d("Str_TalukaId", S_TalukaId.toString());
                            str_TalukaId = S_TalukaId.toString();
                            //     sel_taluksp = Integer.parseInt(S_TalukaId.toString());

                        }
                        O_VillageId = list.getProperty("VillageId");
                        if (!O_VillageId.toString().equals("anyType{}") && !O_VillageId.toString().equals(null)) {
                            S_VillageId = (SoapPrimitive) list.getProperty("VillageId");
                            Log.d("str_VillageId", S_VillageId.toString());
                            str_VillageId = S_VillageId.toString();
                            //   sel_villagesp = Integer.parseInt(S_VillageId.toString());
                        }
                        O_SectorId = list.getProperty("SectorId");
                        if (!O_SectorId.toString().equals("anyType{}") && !O_SectorId.toString().equals(null)) {
                            S_SectorId = (SoapPrimitive) list.getProperty("SectorId");
                            Log.d("Str_SectorId", S_SectorId.toString());
                            str_SectorId = S_SectorId.toString();
                        }

                        O_Gender = list.getProperty("Gender");
                        if (!O_Gender.toString().equals("anyType{}") && !O_Gender.toString().equals(null)) {
                            S_Gender = (SoapPrimitive) list.getProperty("Gender");
                            Log.d("Str_Gender", S_Gender.toString());
                            str_gender = S_Gender.toString();

                        }

                        EnquiryListModel item = null;
                        if (!str_collegeName.isEmpty()) {
                           // item = new EnquiryListModel(str_studentName, str_leadid, str_registrationDate, str_collegeName, str_MobileNo, str_Status,String.valueOf(acadamicCode));
                            if(str_isApplicant.equalsIgnoreCase("0")) {
                                item = new EnquiryListModel(str_studentName, str_leadid, str_registrationDate, str_collegeName, str_MobileNo, str_Status, String.valueOf(acadamicCode), str_MiddleName, str_LastName, str_StateId, str_DistrictId, str_TalukaId, str_VillageId, str_SectorId, str_gender, str_isApplicant);
                                feesList.add(item);
                            }
                        }


                    }else{
                        feesList.clear();
                      //  originalList.clear();
                   //     adapter.notifyDataSetChanged();
                    //    Toast.makeText(getActivity(),"There are no Enquiries", Toast.LENGTH_LONG).show();
                    }

                }


                originalList = new ArrayList<EnquiryListModel>();
                originalList.clear();
                originalList.addAll(feesList);

                DBCreate_ListEnquiryDetails_2SQLiteDB(originalList);
            //    adapter.notifyDataSetChanged();
               // initCollegeSpinner();
            }
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapObject getEnquiryListing() {
        String METHOD_NAME = "Enquiry_Listing";
        String SOAP_ACTION1 = "http://mis.navodyami.org/Enquiry_Listing";
        String NAMESPACE = "http://mis.navodyami.org/";

        try{

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");

            request.addProperty("AcademicCode",acadamicCode);
            request.addProperty("EnquiryId",0);
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
                Log.d("tag","soapresponse Enquiry List="+envelope.getResponse().toString());


                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("tag","soapresponse enquiry="+response.toString());

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
                       // Toast.makeText(getActivity(),"Web Service Error", Toast.LENGTH_LONG).show();
                        Toast toast= Toast.makeText(getActivity(), "  "+exceptionStr+"  " , Toast.LENGTH_LONG);
                        View view =toast.getView();
                        view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                        toast.show();

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
                 //   Toast.makeText(getActivity(),"Web Service Error", Toast.LENGTH_LONG).show();
                    Toast toast= Toast.makeText(getActivity(), "  "+exceptionStr+"  " , Toast.LENGTH_LONG);
                    View view =toast.getView();
                    view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                    toast.show();
                }
            });
        }
        return null;

    }


  /*  private void initCollegeSpinner() {
        collegeNameArrLst.addAll(collegeNameLst);
       *//* final ArrayList acadamicYear = new ArrayList();

        Set<String> yearList = hashYearId.keySet();


        for(String key: yearList){
            acadamicYear.add(key);
        }
        Log.e("tag","acadamicYear="+acadamicYear.get(1));*//*

        //int acadamicCode = hashYearId.get(spin_college.getSelectedItem().toString());

       //  Log.e("tag","acadamicCode="+acadamicCode);

        ArrayAdapter dataAdapter2 = new ArrayAdapter(getActivity(), R.layout.simple_spinner_items, collegeNameArrLst);
        dataAdapter2.setDropDownViewResource(R.layout.spinnercustomstyle);
        // attaching data adapter to spinner
        spin_college.setAdapter(dataAdapter2);




    }*/

    public class AcademicYear extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        private ProgressBar progressBar;
       // private ProgressDialog progressDialog;

        AcademicYear (Context ctx){
            context = ctx;
        //    progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params)
        {
            //String versionCode = (String) params[2];
            String METHOD_NAME = "GetAcademicYearList";
            String SOAP_ACTION1 = "http://mis.navodyami.org/GetAcademicYearList";
            String NAMESPACE = "http://mis.navodyami.org/";


            try{
                //mis.leadcampus.org

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

                envelope.dotNet = true;
                //Set output SOAP object
                envelope.setOutputSoapObject(request);
                //Create HTTP call object
                //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
                HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Login.toString().trim());
                //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

                try
                {
                    androidHttpTransport.call(SOAP_ACTION1, envelope);
                    SoapObject response = (SoapObject) envelope.getResponse();
                    Log.d("soap response year",response.toString());

                    return response;

                }catch(OutOfMemoryError ex){
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                catch (Exception t) {
                    Log.e("request fail", "> " + t.getMessage().toString());

                    final String exceptionStr = t.getMessage().toString();
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(context,exceptionStr, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            catch(OutOfMemoryError ex){
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.d("exception outside",t.getMessage().toString());
                final String exceptionStr = t.getMessage().toString();
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context,exceptionStr, Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;


            //Log.d("Soap response is",response.toString());

            //return response;
        }

        @Override
        protected void onPreExecute() {
    /*        progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/

        /*    progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();*/
        }

        @Override
        protected void onPostExecute(SoapObject result) {
            //progressBar.setVisibility(View.GONE);

         //   progressDialog.dismiss();


            String finalResult = result.toString();
            String finals = finalResult.replace("anyType","");
            Log.d("Finals is",finals);

            ArrayList<String> yearList = new ArrayList<String>();

            Log.d("finalResultsssss",finalResult);

            for(int i=0;i < result.getPropertyCount();i++)
            {
                SoapObject list = (SoapObject) result.getProperty(i);
                SoapPrimitive S_slno,S_AcademicCode,S_Status,S_Display_Year;
                Object O_slno,O_AcademicCode,O_Status,O_Display_Year;

                int int_slno=0;

                String str_slno=null,str_AcademicCode=null,str_status=null,str_Display_Year=null;

                O_slno = list.getProperty("slno");
                if(!O_slno.toString().equals("anyType") && !O_slno.toString().equals(null) && !O_slno.toString().equals("anyType{}")){
                    S_slno = (SoapPrimitive) list.getProperty("slno");
                    Log.d("slno",S_slno.toString());
                    str_slno = O_slno.toString();
                    int_slno = Integer.valueOf(str_slno);
                }

                O_AcademicCode = list.getProperty("AcademicCode");
                if(!O_AcademicCode.toString().equals("anyType") && !O_AcademicCode.toString().equals(null) && !O_AcademicCode.toString().equals("anyType{}"))
                {
                    S_AcademicCode = (SoapPrimitive) list.getProperty("AcademicCode");
                    Log.d("O_AcademicCode Name",S_AcademicCode.toString());
                    str_AcademicCode = O_AcademicCode.toString();

                    yearList.add(str_AcademicCode);

                    hashYearId.put(str_AcademicCode,int_slno);

                }
                O_Display_Year = list.getProperty("Display_Year");
                if(!O_Display_Year.toString().equals("anyType") && !O_Display_Year.toString().equals(null) && !O_Display_Year.toString().equals("anyType{}"))
                {
                    S_Display_Year = (SoapPrimitive) list.getProperty("Display_Year");
                    Log.d("Display_Year Name",S_Display_Year.toString());
                    str_Display_Year = O_Display_Year.toString();

                }
                DBCreate_Yeardetails_insert_2SQLiteDB(str_slno,str_AcademicCode,str_Display_Year);

            }

           /* ArrayAdapter dataAdapter3 = new ArrayAdapter(getActivity(), R.layout.simple_spinner_items, yearList);
            dataAdapter3.setDropDownViewResource(R.layout.spinnercustomstyle);
            // attaching data adapter to spinner
            spin_year.setAdapter(dataAdapter3);*/
            uploadfromDB_Yearlist();



            //setProjectTypeSpinner(projectTypeList);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public void uploadfromDB_Yearlist() {

        SQLiteDatabase db_year = getActivity().openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_year.execSQL("CREATE TABLE IF NOT EXISTS YearList(YearID VARCHAR,YearName VARCHAR,Display_Year VARCHAR);");
        Cursor cursor = db_year.rawQuery("SELECT DISTINCT * FROM YearList", null);
        int x = cursor.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_yearDetails2 = new Class_YearListDetails[x];
        if (cursor.moveToFirst()) {

            do {
                Class_YearListDetails innerObj_Class_yearList = new Class_YearListDetails();
                innerObj_Class_yearList.setYearID(cursor.getString(cursor.getColumnIndex("YearID")));
                innerObj_Class_yearList.setYear(cursor.getString(cursor.getColumnIndex("YearName")));


                arrayObj_Class_yearDetails2[i] = innerObj_Class_yearList;
                i++;
                //acadamicCode= Integer.parseInt(arrayObj_Class_yearDetails2[i].yearID);
            } while (cursor.moveToNext());


        }//if ends

        db_year.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getContext(), R.layout.spinnercustomstyle, arrayObj_Class_yearDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
            spin_year.setAdapter(dataAdapter);
            if(x>sel_yearsp) {
                spin_year.setSelection(sel_yearsp);
            }
        }

    }

    public void DBCreate_Yeardetails_insert_2SQLiteDB(String str_yearID, String str_yearname, String str_display_Year) {
        SQLiteDatabase db_yearlist = getActivity().openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_yearlist.execSQL("CREATE TABLE IF NOT EXISTS YearList(YearID VARCHAR,YearName VARCHAR,Display_Year VARCHAR);");

        Log.e("tag","YearName="+str_yearname+"Display_Year="+str_display_Year);

        String SQLiteQuery = "INSERT INTO YearList (YearID, YearName, Display_Year)" +
                " VALUES ('" + str_yearID + "','" + str_yearname + "','" + str_display_Year + "');";
        db_yearlist.execSQL(SQLiteQuery);


        //Log.e("str_yearID DB", str_yearID);
        // Log.e("str_yearname DB", str_yearname);
        db_yearlist.close();
    }

    public void deleteYearTable_B4insertion() {

        SQLiteDatabase db_yearlist_delete = getActivity().openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_yearlist_delete.execSQL("CREATE TABLE IF NOT EXISTS YearList(YearID VARCHAR,YearName VARCHAR,Display_Year VARCHAR);");
        Cursor cursor = db_yearlist_delete.rawQuery("SELECT * FROM YearList", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_yearlist_delete.delete("YearList", null, null);

        }
        db_yearlist_delete.close();
    }

    public void deleteListEnquiryDetailsTable_B4insertion() {

        SQLiteDatabase db_ListEnquiryDetails_delete = getActivity().openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_ListEnquiryDetails_delete.execSQL("CREATE TABLE IF NOT EXISTS ListEnquiryDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,FName VARCHAR,MobileNo VARCHAR,EmailId VARCHAR,BusinessName VARCHAR,EnquiryId VARCHAR,Status VARCHAR,YearCode VARCHAR,MName VARCHAR,LName VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,isApplicant VARCHAR,Gender VARCHAR,dataSyncStatus VARCHAR );");

        Cursor cursor = db_ListEnquiryDetails_delete.rawQuery("SELECT * FROM ListEnquiryDetails", null);
        int x = cursor.getCount();

        Log.e("tag","delete enquiry count="+x);
        if (x > 0) {
            db_ListEnquiryDetails_delete.delete("ListEnquiryDetails", null, null);

        }
        db_ListEnquiryDetails_delete.close();
    }
    /*
<EnquiryId>10</EnquiryId>
    <FirstName>offline five</FirstName>
<MiddleName>test</MiddleName>
<LastName>test</LastName>
<MobileNo>885522233</MobileNo>
<EmailId>test</EmailId>
<StateId>12</StateId>
<DistrictId>557</DistrictId>
<TalukaId>5500</TalukaId>
<VillageId>7748</VillageId>
<PanchayatId>0</PanchayatId>
<SectorId>9</SectorId>
<BusinessName>test</BusinessName>
<isApplicant>0</isApplicant>
<Status>Success</Status>*/

    public void DBCreate_ListEnquiryDetails_2SQLiteDB(ArrayList<EnquiryListModel> str_listenquirys) {
        SQLiteDatabase db_addEnquiry = getActivity().openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_addEnquiry.execSQL("CREATE TABLE IF NOT EXISTS ListEnquiryDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,FName VARCHAR,MobileNo VARCHAR,EmailId VARCHAR,BusinessName VARCHAR,EnquiryId VARCHAR,Status VARCHAR,YearCode VARCHAR,MName VARCHAR,LName VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,isApplicant VARCHAR,Gender VARCHAR,dataSyncStatus VARCHAR );");

        String FName,YearCode,LName,MName,MobileNo,EmailId,BusinessName,Status,gender,isApplicant,stateId,districtId,talukId,villegeId,sectorId,deviceType,userId,EnquiryId;

        for(int s=str_listenquirys.size()-1;s>-1;s--) {
        //for(int s=0;s<str_listenquirys.size();s++) {
            Log.e("tag","BusinessName="+str_listenquirys.get(s).getBussinessName()+"Fname="+str_listenquirys.get(s).getFname());

            FName=str_listenquirys.get(s).getFname();
            MobileNo=str_listenquirys.get(s).getMobileNo();
            EmailId=str_listenquirys.get(s).getEmailId();
            BusinessName=str_listenquirys.get(s).getBussinessName();
            EnquiryId=str_listenquirys.get(s).getEnquiry_id();
            Status = str_listenquirys.get(s).getStatus();
            YearCode = str_listenquirys.get(s).getYearCode();
            MName=str_listenquirys.get(s).getMName();
            LName=str_listenquirys.get(s).getLName();
            stateId=str_listenquirys.get(s).getStateId();
            districtId=str_listenquirys.get(s).getDistrictId();
            talukId=str_listenquirys.get(s).getTalukaI();
            villegeId=str_listenquirys.get(s).getVillageId();
            sectorId=str_listenquirys.get(s).getSectorId();
            gender=str_listenquirys.get(s).getGender();
            isApplicant=str_listenquirys.get(s).getIsApplicant();

            dataSyncStatus="online";

            String SQLiteQuery = "INSERT INTO ListEnquiryDetails (FName,MobileNo,EmailId,BusinessName,EnquiryId,Status,YearCode,MName,LName,StateId,DistrictId,TalukaId,VillageId,SectorId,isApplicant,Gender,dataSyncStatus)" +
                    " VALUES ('" + FName + "','" + MobileNo + "','" + EmailId +"','" + BusinessName +"','" + EnquiryId + "','" + Status +"','" + YearCode +"','" + MName +"','" + LName +"','" + stateId +"','" + districtId +"','" + talukId +"','" + villegeId +"','" + sectorId +"','" + isApplicant +"','" + gender +"','"+dataSyncStatus+"');";
            db_addEnquiry.execSQL(SQLiteQuery);
        }

        List_offlineEnquiryDetails();
        db_addEnquiry.close();
    }

    public void List_offlineEnquiryDetails() {

        SQLiteDatabase db1 = getActivity().openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS ListEnquiryDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,FName VARCHAR,MobileNo VARCHAR,EmailId VARCHAR,BusinessName VARCHAR,EnquiryId VARCHAR,Status VARCHAR,YearCode VARCHAR,MName VARCHAR,LName VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,isApplicant VARCHAR,Gender VARCHAR,dataSyncStatus VARCHAR );");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM ListEnquiryDetails ORDER BY tempId DESC", null);
        int x = cursor1.getCount();
        Log.d("cursor Enquiry Count", Integer.toString(x));

        int i = 0;
        // arrayObj_Class_GrampanchayatListDetails2 = new Class_GrampanchayatListDetails[x];

        feesList.clear();
            if (cursor1.moveToFirst()) {
                String YearCode = cursor1.getString(cursor1.getColumnIndex("YearCode"));
                if(YearCode.equalsIgnoreCase(String.valueOf(acadamicCode))||YearCode.equalsIgnoreCase("0")) {

                do {

                    String FName = cursor1.getString(cursor1.getColumnIndex("FName"));
                    String MobileNo = cursor1.getString(cursor1.getColumnIndex("MobileNo"));
                    String EmailId = cursor1.getString(cursor1.getColumnIndex("EmailId"));
                    String BusinessName = cursor1.getString(cursor1.getColumnIndex("BusinessName"));
                    String EnquiryId = cursor1.getString(cursor1.getColumnIndex("EnquiryId"));
                    String Status = cursor1.getString(cursor1.getColumnIndex("Status"));

                    String tempId = cursor1.getString(cursor1.getColumnIndex("tempId"));
                    String dataSyncStatus = cursor1.getString(cursor1.getColumnIndex("dataSyncStatus"));

                 /* String MName=cursor1.getString(cursor1.getColumnIndex("MName"));
                String LName=cursor1.getString(cursor1.getColumnIndex("LName"));*/
               /* String sp_strstate_ID=cursor1.getString(cursor1.getColumnIndex("StateId"));
                String sp_strdistrict_ID=cursor1.getString(cursor1.getColumnIndex("DistrictId"));
                String sp_strTaluk_ID=cursor1.getString(cursor1.getColumnIndex("TalukaId"));
                String sp_strVillage_ID=cursor1.getString(cursor1.getColumnIndex("VillageId"));
                String sp_strsector_ID=cursor1.getString(cursor1.getColumnIndex("SectorId"));*/
                    // cursor1.getString(cursor1.getColumnIndex("DeviceType"));
                    //  String str_UserId= cursor1.getString(cursor1.getColumnIndex("UserId"));


            /*    AddEnquiryDetails addEnquiryDetails = new AddEnquiryDetails(AddEnquiryActivity.this);
                addEnquiryDetails.execute(FName,MName,LName,MobileNo,EmailId,BusinessName,sp_strstate_ID,sp_strdistrict_ID,sp_strTaluk_ID,sp_strVillage_ID,sp_strsector_ID,str_UserId);
*/Log.e("tag","temp Id listenquiry=="+tempId);

                    if (Status.equalsIgnoreCase("Success")) {
                        EnquiryListModel item = null;

                        item = new EnquiryListModel(FName, EnquiryId, MobileNo, BusinessName, EmailId, Status, YearCode, tempId,dataSyncStatus);
                        feesList.add(item);

                    } else {
                        feesList.clear();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "There are no Enquiries", Toast.LENGTH_LONG).show();
                    }
                    i++;


                } while (cursor1.moveToNext());
                }else {
                    feesList.clear();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "There are no Enquiries", Toast.LENGTH_LONG).show();
                }


            }//if ends



        originalList = new ArrayList<EnquiryListModel>();
        originalList.clear();
        originalList.addAll(feesList);
        adapter.notifyDataSetChanged();
        cursor1.close();
        db1.close();

    }

  /*  public void AddEnquiryDetails_Offline(){
        SQLiteDatabase db1 = getActivity().openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS AddEnquiryDetails(FName VARCHAR,MName VARCHAR,LName VARCHAR,MobileNo VARCHAR,EmailId VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,BusinessName VARCHAR,DeviceType VARCHAR,UserId VARCHAR,EnquiryId VARCHAR,Gender VARCHAR,dataSyncStatus VARCHAR);");

        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM AddEnquiryDetails ", null);
        int x = cursor1.getCount();
        Log.d("tag","cursor Enquiry Add Count"+ Integer.toString(x));

        if(x>0){
            String x_str= String.valueOf(x);
            offline_count.setVisibility(View.VISIBLE);
            new_enquiryCount.setText(x_str);
            if(isInternetPresent) {
                Update_offlineAddEnquiryDetails();
            }
        }
       *//* else{
            offline_count.setVisibility(View.GONE);
        }*//*
    }
*/
  /*  public void Update_offlineAddEnquiryDetails() {

        SQLiteDatabase db1 = getActivity().openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS AddEnquiryDetails(FName VARCHAR,MName VARCHAR,LName VARCHAR,MobileNo VARCHAR,EmailId VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,BusinessName VARCHAR,DeviceType VARCHAR,UserId VARCHAR,EnquiryId VARCHAR,Gender VARCHAR,dataSyncStatus VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM AddEnquiryDetails ", null);
        int x = cursor1.getCount();
        Log.d("cursor Enquiry Count", Integer.toString(x));

        int i = 0;
        // arrayObj_Class_GrampanchayatListDetails2 = new Class_GrampanchayatListDetails[x];
        if (cursor1.moveToFirst()) {

            do {

                String FName=cursor1.getString(cursor1.getColumnIndex("FName"));
                String MName=cursor1.getString(cursor1.getColumnIndex("MName"));
                String LName=cursor1.getString(cursor1.getColumnIndex("LName"));
                String MobileNo=cursor1.getString(cursor1.getColumnIndex("MobileNo"));
                String EmailId=cursor1.getString(cursor1.getColumnIndex("EmailId"));
                String sp_strstate_ID=cursor1.getString(cursor1.getColumnIndex("StateId"));
                String sp_strdistrict_ID=cursor1.getString(cursor1.getColumnIndex("DistrictId"));
                String sp_strTaluk_ID=cursor1.getString(cursor1.getColumnIndex("TalukaId"));
                String sp_strVillage_ID=cursor1.getString(cursor1.getColumnIndex("VillageId"));
                String sp_strsector_ID=cursor1.getString(cursor1.getColumnIndex("SectorId"));
                String BusinessName=cursor1.getString(cursor1.getColumnIndex("BusinessName"));
                // cursor1.getString(cursor1.getColumnIndex("DeviceType"));
                String str_UserId= cursor1.getString(cursor1.getColumnIndex("UserId"));
                String str_Gender= cursor1.getString(cursor1.getColumnIndex("Gender"));
                //cursor1.getString(cursor1.getColumnIndex("EnquiryId"));

                AddEnquiryDetails addEnquiryDetails = new AddEnquiryDetails(getActivity());
                addEnquiryDetails.execute(FName,MName,LName,MobileNo,EmailId,BusinessName,sp_strstate_ID,sp_strdistrict_ID,sp_strTaluk_ID,sp_strVillage_ID,sp_strsector_ID,str_UserId,str_Gender);

                i++;
            } while (cursor1.moveToNext());
        }//if ends

        if (x > 0) {
            db1.delete("AddEnquiryDetails", null, null);

        }

        db1.close();

    }*/
 /*   public void EditEnquiryDetails_Count(){
        *//*SQLiteDatabase db2 = getActivity().openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS ListEnquiryDetails(FName VARCHAR,MobileNo VARCHAR,EmailId VARCHAR,BusinessName VARCHAR,EnquiryId VARCHAR,Status VARCHAR,YearCode VARCHAR,MName VARCHAR,LName VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,isApplicant VARCHAR,Gender VARCHAR);");
        Cursor cursor1 = db2.rawQuery("SELECT DISTINCT * FROM ListEnquiryDetails ", null);
        int x = cursor1.getCount();*//*

        EditedEnquiryId.clear();
        EditedTempId.clear();
        SQLiteDatabase db2 = getActivity().openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS ListEditEnquiryId(EnquiryId VARCHAR,tempId VARCHAR);");
        Cursor cursor1 = db2.rawQuery("SELECT DISTINCT * FROM ListEditEnquiryId ", null);
        int x = cursor1.getCount();
        Log.d("tag","cursor Enquiry Edit Count"+ Integer.toString(x));
        int i=0;
        String EnquiryId = null;
        String TempId=null;
        if(x>0){
            offline_count.setVisibility(View.VISIBLE);
            edit_enquiryCount.setText(String.valueOf(x));
            if (cursor1.moveToFirst()) {

                do {
                    EnquiryId = cursor1.getString(cursor1.getColumnIndex("EnquiryId"));
                    TempId = cursor1.getString(cursor1.getColumnIndex("tempId"));
                    i++;
                } while (cursor1.moveToNext());
            }
            EditedEnquiryId.add(EnquiryId);
            EditedTempId.add(TempId);
            if(isInternetPresent) {
                Edit_offlineEnquiryDetails(EditedEnquiryId, EditedTempId);
            }
        }
       *//* else{
            offline_count.setVisibility(View.GONE);
        }*//*

    }*/
    public void getEnquiryOfflineDataSync(){
        SQLiteDatabase db2 = getActivity().openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS ListEnquiryDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,FName VARCHAR,MobileNo VARCHAR,EmailId VARCHAR,BusinessName VARCHAR,EnquiryId VARCHAR,Status VARCHAR,YearCode VARCHAR,MName VARCHAR,LName VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,isApplicant VARCHAR,Gender VARCHAR,dataSyncStatus VARCHAR );");
        Cursor cursor1 = db2.rawQuery("SELECT DISTINCT * FROM ListEnquiryDetails WHERE dataSyncStatus='" +"offline"+"'", null);
        int x = cursor1.getCount();
        String EnquiryIdoff;
        if(x>0){
            if (cursor1.moveToFirst()) {

                do {
                    EnquiryIdoff = cursor1.getString(cursor1.getColumnIndex("EnquiryId"));

                    if(EnquiryIdoff.equalsIgnoreCase("0")){
                        String FName=cursor1.getString(cursor1.getColumnIndex("FName"));
                        String MName=cursor1.getString(cursor1.getColumnIndex("MName"));
                        String LName=cursor1.getString(cursor1.getColumnIndex("LName"));
                        String MobileNo=cursor1.getString(cursor1.getColumnIndex("MobileNo"));
                        String EmailId=cursor1.getString(cursor1.getColumnIndex("EmailId"));
                        String sp_strstate_ID=cursor1.getString(cursor1.getColumnIndex("StateId"));
                        String sp_strdistrict_ID=cursor1.getString(cursor1.getColumnIndex("DistrictId"));
                        String sp_strTaluk_ID=cursor1.getString(cursor1.getColumnIndex("TalukaId"));
                        String sp_strVillage_ID=cursor1.getString(cursor1.getColumnIndex("VillageId"));
                        String sp_strsector_ID=cursor1.getString(cursor1.getColumnIndex("SectorId"));
                        String BusinessName=cursor1.getString(cursor1.getColumnIndex("BusinessName"));
                        // cursor1.getString(cursor1.getColumnIndex("DeviceType"));
                        //   String str_UserId= cursor1.getString(cursor1.getColumnIndex("UserId"));
                        String str_Gender= cursor1.getString(cursor1.getColumnIndex("Gender"));
                        //cursor1.getString(cursor1.getColumnIndex("EnquiryId"));

                        AddEnquiryDetails addEnquiryDetails = new AddEnquiryDetails(getActivity());
                        addEnquiryDetails.execute(FName,MName,LName,MobileNo,EmailId,BusinessName,sp_strstate_ID,sp_strdistrict_ID,sp_strTaluk_ID,sp_strVillage_ID,sp_strsector_ID,str_UserId,str_Gender);


                    }else{
                        String FName = cursor1.getString(cursor1.getColumnIndex("FName"));
                        String MName = cursor1.getString(cursor1.getColumnIndex("MName"));
                        String LName = cursor1.getString(cursor1.getColumnIndex("LName"));
                        String MobileNo = cursor1.getString(cursor1.getColumnIndex("MobileNo"));
                        String EmailId = cursor1.getString(cursor1.getColumnIndex("EmailId"));
                        String BusinessName = cursor1.getString(cursor1.getColumnIndex("BusinessName"));
                        String EnquiryId = cursor1.getString(cursor1.getColumnIndex("EnquiryId"));
                        String Status = cursor1.getString(cursor1.getColumnIndex("Status"));
                        String YearCode = cursor1.getString(cursor1.getColumnIndex("YearCode"));
                        String sp_strstate_ID = cursor1.getString(cursor1.getColumnIndex("StateId"));
                        String sp_strdistrict_ID = cursor1.getString(cursor1.getColumnIndex("DistrictId"));
                        String sp_strTaluk_ID = cursor1.getString(cursor1.getColumnIndex("TalukaId"));
                        String sp_strVillage_ID = cursor1.getString(cursor1.getColumnIndex("VillageId"));
                        String sp_strsector_ID = cursor1.getString(cursor1.getColumnIndex("SectorId"));
                        // cursor1.getString(cursor1.getColumnIndex("DeviceType"));
//                String str_UserId= cursor1.getString(cursor1.getColumnIndex("UserId"));
                        String str_Gender = cursor1.getString(cursor1.getColumnIndex("Gender"));


                        UpdateEnquiryDetails updateEnquiryDetails = new UpdateEnquiryDetails(getContext());
                        updateEnquiryDetails.execute(FName, MName, LName, MobileNo, EmailId, BusinessName, sp_strstate_ID, sp_strdistrict_ID, sp_strTaluk_ID, sp_strVillage_ID, sp_strsector_ID, str_UserId, str_Gender, EnquiryId);

                    }
                } while (cursor1.moveToNext());
            }
        }

    }
    public void Add_Edit_offlineEnquiryCount() {

        SQLiteDatabase db2 = getActivity().openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS ListEnquiryDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,FName VARCHAR,MobileNo VARCHAR,EmailId VARCHAR,BusinessName VARCHAR,EnquiryId VARCHAR,Status VARCHAR,YearCode VARCHAR,MName VARCHAR,LName VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,isApplicant VARCHAR,Gender VARCHAR,dataSyncStatus VARCHAR );");

        Cursor cursor1 = db2.rawQuery("SELECT DISTINCT * FROM ListEnquiryDetails WHERE dataSyncStatus='" +"offline"+"'", null);
        int x = cursor1.getCount();
        Log.d("tag", "cursor Enquiry offline Count" + Integer.toString(x));
        String EnquiryIdCount = null;
        int newCount=0,editCount=0;
        if (x > 0) {
            if (cursor1.moveToFirst()) {

                do {
                    EnquiryIdCount = cursor1.getString(cursor1.getColumnIndex("EnquiryId"));

                    if(EnquiryIdCount.equalsIgnoreCase("0")){
                        newCount=newCount+1;
                    }else{
                        editCount=editCount+1;
                    }
                } while (cursor1.moveToNext());
            }

            Log.d("tag", "cursor offline Enquiry list Count newCount" + newCount);
            if (newCount != 0) {
                String x_str = String.valueOf(newCount);
                offline_count.setVisibility(View.VISIBLE);
                new_enquiryCount.setText(x_str);
            }
            Log.d("tag", "cursor offline Enquiry list Count editCount" + editCount);
            if (editCount != 0) {
                String x_str = String.valueOf(editCount);
                offline_count.setVisibility(View.VISIBLE);
                edit_enquiryCount.setText(x_str);
            }
        }
    }
     /*   public void Edit_offlineEnquiryDetails(ArrayList<String> EditedEnquiryId,ArrayList<String> EditedTempId) {

        SQLiteDatabase db2 = getActivity().openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS ListEnquiryDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,FName VARCHAR,MobileNo VARCHAR,EmailId VARCHAR,BusinessName VARCHAR,EnquiryId VARCHAR,Status VARCHAR,YearCode VARCHAR,MName VARCHAR,LName VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,isApplicant VARCHAR,Gender VARCHAR,dataSyncStatus VARCHAR );");

        for(int j=0;j<EditedEnquiryId.size();j++) {

            Cursor cursor1 = db2.rawQuery("SELECT DISTINCT * FROM ListEnquiryDetails WHERE EnquiryId='" + EditedEnquiryId.get(j) + "' AND tempId='"+EditedTempId.get(j)+"'", null);
            int x = cursor1.getCount();
            Log.d("tag", "cursor Enquiry Edit Count" + Integer.toString(x));

            int i = 0;

            if (cursor1.moveToFirst()) {

                // if(YearCode.equalsIgnoreCase(String.valueOf(acadamicCode))) {

                do {

                    String FName = cursor1.getString(cursor1.getColumnIndex("FName"));
                    String MName = cursor1.getString(cursor1.getColumnIndex("MName"));
                    String LName = cursor1.getString(cursor1.getColumnIndex("LName"));
                    String MobileNo = cursor1.getString(cursor1.getColumnIndex("MobileNo"));
                    String EmailId = cursor1.getString(cursor1.getColumnIndex("EmailId"));
                    String BusinessName = cursor1.getString(cursor1.getColumnIndex("BusinessName"));
                    String EnquiryId = cursor1.getString(cursor1.getColumnIndex("EnquiryId"));
                    String Status = cursor1.getString(cursor1.getColumnIndex("Status"));
                    String YearCode = cursor1.getString(cursor1.getColumnIndex("YearCode"));
                    String sp_strstate_ID = cursor1.getString(cursor1.getColumnIndex("StateId"));
                    String sp_strdistrict_ID = cursor1.getString(cursor1.getColumnIndex("DistrictId"));
                    String sp_strTaluk_ID = cursor1.getString(cursor1.getColumnIndex("TalukaId"));
                    String sp_strVillage_ID = cursor1.getString(cursor1.getColumnIndex("VillageId"));
                    String sp_strsector_ID = cursor1.getString(cursor1.getColumnIndex("SectorId"));
                    // cursor1.getString(cursor1.getColumnIndex("DeviceType"));
//                String str_UserId= cursor1.getString(cursor1.getColumnIndex("UserId"));
                    String str_Gender = cursor1.getString(cursor1.getColumnIndex("Gender"));


                    UpdateEnquiryDetails updateEnquiryDetails = new UpdateEnquiryDetails(getContext());
                    updateEnquiryDetails.execute(FName, MName, LName, MobileNo, EmailId, BusinessName, sp_strstate_ID, sp_strdistrict_ID, sp_strTaluk_ID, sp_strVillage_ID, sp_strsector_ID, str_UserId, str_Gender, EnquiryId);

                    i++;
                } while (cursor1.moveToNext());



            }//if ends
        }
        editorEnquiry_obj = sharedpreferencebook_OfflineEnquiryData_Obj.edit();
        editorEnquiry_obj.putString(KeyValue_EnquiryEdited, "0");
        editorEnquiry_obj.apply();

        SQLiteDatabase db_editEnquiryId = getActivity().openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS ListEditEnquiryId(EnquiryId VARCHAR,tempId VARCHAR);");
        Cursor cursor = db_editEnquiryId.rawQuery("SELECT * FROM ListEditEnquiryId", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_editEnquiryId.delete("ListEditEnquiryId", null, null);

        }
        db_editEnquiryId.close();
      *//*  if (x > 0) {
            db2.delete("ListEnquiryDetails", null, null);

        }*//*
    }
*/
    public class UpdateEnquiryDetails extends AsyncTask<String, Void, Void>
    {
        //   ProgressDialog dialog;

        Context context;

        //  public UpdateManagerDetails(GetManagerDetails getManagerDetails) {
        //  }

        @Override
        protected void onPreExecute()
        {
            Log.i("TAG", "onPreExecute---tab2");
          /*  dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
*/
        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            Log.i("TAG", "onProgressUpdate---tab2");
        }

        public UpdateEnquiryDetails(Context activity) {
            context = activity;
            //  dialog = new ProgressDialog(context);
        }
        @Override
        protected Void doInBackground(String... params)
        {
            Log.i("TAG", "doInBackground");
          //  Update( params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9] ,params[10],params[11],params[12],params[13]);
            String FName=(String) params[0];
            String MName=(String) params[1];
            String LName=(String) params[2];
            String MobileNo=(String) params[3];
            String EmailId=(String) params[4];
            String BusinessName=(String) params[5];
            String sp_strstate_ID=(String) params[6];
            String sp_strdistrict_ID=(String) params[7];
            String sp_strTaluk_ID=(String) params[8];
            String sp_strVillage_ID=(String) params[9];
            String sp_strsector_ID=(String) params[10];
            String str_UserId=(String) params[11];
            String Gender=(String) params[12];
            String EnquiryID=(String) params[13];

            String URL = Class_URL.URL_Login.toString().trim();
            String METHOD_NAME = "Enquiry_Adding";
            String Namespace = "http://mis.navodyami.org/", SOAPACTION = "http://mis.navodyami.org/Enquiry_Adding";

            try {
                SoapObject request = new SoapObject(Namespace, METHOD_NAME);
                request.addProperty("FirstName", FName);
                request.addProperty("MiddleName", MName);
                request.addProperty("LastName", LName);
                request.addProperty("MobileNo", MobileNo);
                request.addProperty("EmailId", EmailId);
                request.addProperty("StateId", sp_strstate_ID);
                request.addProperty("DistrictId", sp_strdistrict_ID);
                request.addProperty("TalukaId",sp_strTaluk_ID);
                request.addProperty("VillageId",sp_strVillage_ID);
                //   request.addProperty("panchayat_Id","0");
                request.addProperty("SectorId",sp_strsector_ID);
                request.addProperty("BusinessName",BusinessName);
                request.addProperty("DeviceType","MOB");
                request.addProperty("UserId",str_UserId);
                request.addProperty("EnquiryId",Long.parseLong(EnquiryID));
                request.addProperty("Gender",Gender);

                Log.e("tag","request=="+request.toString());

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                //Set output SOAP object
                envelope.setOutputSoapObject(request);
                //Create HTTP call object
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

                try {

                    androidHttpTransport.call(SOAPACTION, envelope);

                    SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                    //Log.e("tag","soap response Updateresponse"+ response.toString());

                    status = response.toString();
                    Log.e("tag","response =="+response.toString());
                    Log.e("tag","status =="+status);


                } catch (Throwable t) {

                    Log.e("request fail", "> " + t.getMessage());

                }
            } catch (Throwable t) {

                Log.e("UnRegister Error", "> " + t.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            Log.i("TAG", "onPostExecute");

            Log.d("Enquiry Responseisss: ",status.toString());

            //    dialog.dismiss();

            if(status.equalsIgnoreCase("success")) {
                Toast toast= Toast.makeText(context, " Enquiry Updated Successfully " , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor( Color.parseColor("#009900")); //any color your want
                toast.show();
                offline_count.setVisibility(View.GONE);
               /* FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, EventMain_Fragment.newInstance());
                transaction.commit();*/

            }
            else{
               // Toast.makeText(context, "Update Request Failed " , Toast.LENGTH_LONG).show();
                Toast toast= Toast.makeText(context, "  "+status.toString()+"  " , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                toast.show();
            }
        }
    }
    public class AddEnquiryDetails extends AsyncTask<String, Void, Void>
    {
        //   ProgressDialog dialog;

        Context context;

        //  public UpdateManagerDetails(GetManagerDetails getManagerDetails) {
        //  }

        @Override
        protected void onPreExecute()
        {
            Log.i("TAG", "onPreExecute---tab2");
         /*   dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();*/

        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            Log.i("TAG", "onProgressUpdate---tab2");
        }

        public AddEnquiryDetails(Context activity) {
            context = activity;
            // dialog = new ProgressDialog(context);
        }
        @Override
        protected Void doInBackground(String... params)
        {
            Log.i("TAG", "doInBackground");
            //Submit( params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9] ,params[10],params[11],params[12]);
            String FName=(String) params[0];
            String MName=(String) params[1];
            String LName=(String) params[2];
            String MobileNo=(String) params[3];
            String EmailId=(String) params[4];
            String BusinessName=(String) params[5];
            String sp_strstate_ID=(String) params[6];
            String sp_strdistrict_ID=(String) params[7];
            String sp_strTaluk_ID=(String) params[8];
            String sp_strVillage_ID=(String) params[9];
            String sp_strsector_ID=(String) params[10];
            String str_UserId=(String) params[11];
            String Gender=(String) params[12];
          //  String EnquiryID=(String) params[13];

            String URL = Class_URL.URL_Login.toString().trim();
            String METHOD_NAME = "Enquiry_Adding";
            String Namespace = "http://mis.navodyami.org/", SOAPACTION = "http://mis.navodyami.org/Enquiry_Adding";

            try {
                SoapObject request = new SoapObject(Namespace, METHOD_NAME);

                request.addProperty("FirstName", FName);
                request.addProperty("MiddleName", MName);
                request.addProperty("LastName", LName);
                request.addProperty("MobileNo", MobileNo);
                request.addProperty("EmailId", EmailId);
                request.addProperty("StateId", sp_strstate_ID);
                request.addProperty("DistrictId", sp_strdistrict_ID);
                request.addProperty("TalukaId",sp_strTaluk_ID);
                request.addProperty("VillageId",sp_strVillage_ID);
                //   request.addProperty("panchayat_Id","0");
                request.addProperty("SectorId",sp_strsector_ID);
                request.addProperty("BusinessName",BusinessName);
                request.addProperty("DeviceType","MOB");
                request.addProperty("UserId",str_UserId);
                request.addProperty("EnquiryId","0");
                request.addProperty("Gender",Gender);

                Log.e("tag","request=="+request.toString());

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                //Set output SOAP object
                envelope.setOutputSoapObject(request);
                //Create HTTP call object
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

                try {

                    androidHttpTransport.call(SOAPACTION, envelope);

                    SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                    //Log.e("tag","soap response Updateresponse"+ response.toString());

                    status = response.toString();
                    Log.e("tag","response =="+response.toString());
                    Log.e("tag","status =="+status);


                } catch (Throwable t) {

                    Log.e("request fail", "> " + t.getMessage());

                }
            } catch (Throwable t) {

                Log.e("UnRegister Error", "> " + t.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            Log.i("TAG", "onPostExecute");

            Log.d("ServiceResponseisss: ",status.toString());

            //   dialog.dismiss();

            if(status.equalsIgnoreCase("success")) {
                Toast toast= Toast.makeText(context, "  Enquiry inserted Successfully " , Toast.LENGTH_LONG);
                // Toast toast=   Toast.makeText(YOUR ACTIVITY NAME ,Toast.LENGTH_SHORT);
                offline_count.setVisibility(View.GONE);
                View view =toast.getView();
                view.setBackgroundColor( Color.parseColor("#009900")); //any color your want
                toast.show();
            }
            else{
               // Toast toast= Toast.makeText(context, "  Enquiry insertion Failed  " , Toast.LENGTH_LONG);
                Toast toast= Toast.makeText(context, "  "+status.toString()+"  " , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                toast.show();
            }
        }
    }
    public void Update(String FName,String MName,String LName,String MobileNo,String EmailId,String BusinessName,String sp_strstate_ID,String sp_strdistrict_ID,String sp_strTaluk_ID,String sp_strVillage_ID,String sp_strsector_ID,String str_UserId,String Gender,String EnquiryID){
        String URL = Class_URL.URL_Login.toString().trim();
        String METHOD_NAME = "Enquiry_Adding";
        String Namespace = "http://mis.navodyami.org/", SOAPACTION = "http://mis.navodyami.org/Enquiry_Adding";

        try {



            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

       /* ------   API Data  ---------
       <Enquiry_Adding xmlns="http://mis.navodyami.org/">
      <FirstName>string</FirstName>
      <MiddleName>string</MiddleName>
      <LastName>string</LastName>
      <MobileNo>string</MobileNo>
      <EmailId>string</EmailId>
      <StateId>long</StateId>
      <DistrictId>long</DistrictId>
      <TalukaId>long</TalukaId>
      <VillageId>long</VillageId>
      <panchayat_Id>long</panchayat_Id>
      <SectorId>long</SectorId>
      <BusinessName>string</BusinessName>
      <DeviceType>string</DeviceType>
      <UserId>long</UserId>
      <EnquiryId>long</EnquiryId>
    </Enquiry_Adding>*/
            request.addProperty("FirstName", FName);
            request.addProperty("MiddleName", MName);
            request.addProperty("LastName", LName);
            request.addProperty("MobileNo", MobileNo);
            request.addProperty("EmailId", EmailId);
            request.addProperty("StateId", sp_strstate_ID);
            request.addProperty("DistrictId", sp_strdistrict_ID);
            request.addProperty("TalukaId",sp_strTaluk_ID);
            request.addProperty("VillageId",sp_strVillage_ID);
            //   request.addProperty("panchayat_Id","0");
            request.addProperty("SectorId",sp_strsector_ID);
            request.addProperty("BusinessName",BusinessName);
            request.addProperty("DeviceType","MOB");
            request.addProperty("UserId",str_UserId);
            request.addProperty("EnquiryId",Long.parseLong(EnquiryID));
            request.addProperty("Gender",Gender);

            Log.e("tag","request=="+request.toString());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);

                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                //Log.e("tag","soap response Updateresponse"+ response.toString());

                status = response.toString();
                Log.e("tag","response =="+response.toString());
                Log.e("tag","status =="+status);


            } catch (Throwable t) {

                Log.e("request fail", "> " + t.getMessage());

            }
        } catch (Throwable t) {

            Log.e("UnRegister Error", "> " + t.getMessage());
        }
    }
    public void Submit(String FName,String MName,String LName,String MobileNo,String EmailId,String BusinessName,String sp_strstate_ID,String sp_strdistrict_ID,String sp_strTaluk_ID,String sp_strVillage_ID,String sp_strsector_ID,String str_UserId,String Gender){
        String URL = Class_URL.URL_Login.toString().trim();
        String METHOD_NAME = "Enquiry_Adding";
        String Namespace = "http://mis.navodyami.org/", SOAPACTION = "http://mis.navodyami.org/Enquiry_Adding";

        try {



            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

       /* ------   API Data  ---------
       <Enquiry_Adding xmlns="http://mis.navodyami.org/">
      <FirstName>string</FirstName>
      <MiddleName>string</MiddleName>
      <LastName>string</LastName>
      <MobileNo>string</MobileNo>
      <EmailId>string</EmailId>
      <StateId>long</StateId>
      <DistrictId>long</DistrictId>
      <TalukaId>long</TalukaId>
      <VillageId>long</VillageId>
      <panchayat_Id>long</panchayat_Id>
      <SectorId>long</SectorId>
      <BusinessName>string</BusinessName>
      <DeviceType>string</DeviceType>
      <UserId>long</UserId>
      <EnquiryId>long</EnquiryId>
    </Enquiry_Adding>*/
            request.addProperty("FirstName", FName);
            request.addProperty("MiddleName", MName);
            request.addProperty("LastName", LName);
            request.addProperty("MobileNo", MobileNo);
            request.addProperty("EmailId", EmailId);
            request.addProperty("StateId", sp_strstate_ID);
            request.addProperty("DistrictId", sp_strdistrict_ID);
            request.addProperty("TalukaId",sp_strTaluk_ID);
            request.addProperty("VillageId",sp_strVillage_ID);
            //   request.addProperty("panchayat_Id","0");
            request.addProperty("SectorId",sp_strsector_ID);
            request.addProperty("BusinessName",BusinessName);
            request.addProperty("DeviceType","MOB");
            request.addProperty("UserId",str_UserId);
            request.addProperty("EnquiryId","0");
            request.addProperty("Gender",Gender);

            Log.e("tag","request=="+request.toString());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);

                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                //Log.e("tag","soap response Updateresponse"+ response.toString());

                status = response.toString();
                Log.e("tag","response =="+response.toString());
                Log.e("tag","status =="+status);


            } catch (Throwable t) {

                Log.e("request fail", "> " + t.getMessage());

            }
        } catch (Throwable t) {

            Log.e("UnRegister Error", "> " + t.getMessage());
        }
    }

}//end of fragment classxx
