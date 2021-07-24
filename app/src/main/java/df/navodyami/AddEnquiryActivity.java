package df.navodyami;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.List;

public class AddEnquiryActivity extends AppCompatActivity {


    List<Class_StateListDetails> stateList;
    List<Class_DistrictListDetails> districtList;
    List<Class_TalukListDetails> talukList;
    List<Class_VillageListDetails> villageList;
    List<Class_GrampanchayatListDetails> panchayatList;
    List<Class_YearListDetails> yearList;
    ArrayList<Class_AddEnquiryDetailsDetails> addEnquiryList;

    String TempId="0",dataSyncStatus;

    String selected_year, sp_stryear_ID, sp_strstate_ID, selected_district, selected_stateName, sp_strdistrict_ID, sp_strdistrict_state_ID, sp_strTaluk_ID, selected_taluk, sp_strVillage_ID, selected_village, sp_strgrampanchayat_ID, selected_grampanchayat, sp_strsector_ID, selected_sector;

    String[] StrArray_farmerimage,StrArray_farmerlist_panchayatid, StrArray_farmerlist_villageid, StrArray_farmerlist_talukid, StrArray_farmerlist_distid, StrArray_farmerlist_stateid, StrArray_farmerlist_yearid, StrArray_farmerlist_farmerid, StrArray_year_ID, StrArray_year, strArray_farmername, StrArray_farmercode, strArray_stateid, strArray_statename, strArray_districtid, strArray_districtname, strArray_selecteddistname, strArray_Dist_stateid, strArray_taluk_distid, strArray_talukid, strArray_talukname, strArray_village_talukid, strArray_villageid, strArray_villagename, strArray_panchayat_distid, strArray_panchayatid, strArray_panchayatname;

    int sel_sector=0,sel_yearsp=0,sel_statesp=0,sel_districtsp=0,sel_taluksp=0,sel_villagesp=0,sel_grampanchayatsp=0;
    Spinner yearlist_SP, statelist_SP, districtlist_SP, taluklist_SP, villagelist_SP, grampanchayatlist_SP;
    LinearLayout spinnerlayout_ll;
    ImageButton search_ib, downarrow_ib, uparrow_ib;
    TextView viewspinner_tv;

    Class_SectorListDetails[] arrayObj_Class_sectorDetails2;
    Class_SectorListDetails Obj_ClasssectorListDetails;
    Class_YearListDetails[] arrayObj_Class_yearDetails2;
    Class_YearListDetails Obj_Class_yearDetails;
    Class_StateListDetails[] arrayObj_Class_stateDetails2;
    Class_StateListDetails Obj_Class_stateDetails;
    Class_DistrictListDetails[] arrayObj_Class_DistrictListDetails2;
    Class_DistrictListDetails Obj_Class_DistrictDetails;
    Class_TalukListDetails[] arrayObj_Class_TalukListDetails2;
    Class_TalukListDetails Obj_Class_TalukDetails;
    Class_VillageListDetails[] arrayObj_Class_VillageListDetails2;
    Class_VillageListDetails Obj_Class_VillageListDetails;
    Class_GrampanchayatListDetails[] arrayObj_Class_GrampanchayatListDetails2;
    Class_GrampanchayatListDetails Obj_Class_GramanchayatDetails;

    Class_AddEnquiryDetailsDetails class_addEnquiryDetailsDetails = null;

    Boolean isInternetPresent = false;
    Class_InternetDectector internetDectector;

    public static final String sharedpreferenc_selectedspinner = "sharedpreferenc_selectedspinner";
    public static final String Key_sel_yearsp = "sel_yearsp";
    public static final String Key_sel_statesp = "sel_statesp";
    public static final String Key_sel_districtsp = "sel_districtsp";
    public static final String Key_sel_taluksp = "sel_taluksp";
    public static final String Key_sel_villagesp = "sel_villagesp";
    public static final String Key_sel_grampanchayatsp = "sel_grampanchayatsp";
    SharedPreferences sharedpref_spinner_Obj;

    private EditText edt_BusinessName,edt_EmailId,edt_MobileNo,edt_LastName,edt_MiddleName,edt_FirstName;
    private HashMap<String,Integer> hashProjTypeId = new HashMap<String,Integer>();
    Spinner spin_Sector;

    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_employeeid = "KeyValue_employeeid";
    public static final String KeyValue_employeename = "KeyValue_employeename";
    public static final String KeyValue_employee_mailid = "KeyValue_employee_mailid";
    public static final String KeyValue_employeemobileno = "KeyValue_employeemobileno";
    public static final String Key_username = "name_googlelogin";
    public static final String key_userimage = "profileimg_googlelogin";

    SharedPreferences sharedpreferencebook_usercredential_Obj;
    SharedPreferences.Editor editor_obj;
    String str_UserId;

    static String status;
    Button btn_submit;
    String FName,LName,MName,MobileNo,EmailId,BusinessName,str_gender;
    String SyncSlno="0";
    Object VillageStatus;
    RadioGroup gender_radiogroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_enquiry);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_n_actionbar);
        setSupportActionBar(toolbar);
          getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView title= (TextView) toolbar.findViewById(R.id.title_name);
        title.setText("Add Enquiry");
        getSupportActionBar().setTitle("");
        //   toolbar.setBackgroundResource(R.drawable.gradiant);

        stateList = new ArrayList<>();
        districtList = new ArrayList<>();
        talukList = new ArrayList<>();
        villageList = new ArrayList<>();
      //  panchayatList = new ArrayList<>();
        yearList = new ArrayList<>();
        addEnquiryList = new ArrayList<>();

        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

       /* sharedpref_spinner_Obj = getSharedPreferences(sharedpreferenc_selectedspinner, Context.MODE_PRIVATE);
        sel_yearsp = Integer.parseInt(sharedpref_spinner_Obj.getString(Key_sel_yearsp, "").trim());
        sel_statesp = Integer.parseInt(sharedpref_spinner_Obj.getString(Key_sel_statesp, "").trim());
        sel_districtsp = Integer.parseInt(sharedpref_spinner_Obj.getString(Key_sel_districtsp, "").trim());
        sel_taluksp = Integer.parseInt(sharedpref_spinner_Obj.getString(Key_sel_taluksp, "").trim());
        sel_villagesp = Integer.parseInt(sharedpref_spinner_Obj.getString(Key_sel_villagesp, "").trim());
        sel_grampanchayatsp = Integer.parseInt(sharedpref_spinner_Obj.getString(Key_sel_grampanchayatsp, "").trim());
*/
        yearlist_SP = (Spinner) findViewById(R.id.yearlist_farmer_SP);
        statelist_SP = (Spinner) findViewById(R.id.statelist_farmer_SP);

        districtlist_SP = (Spinner) findViewById(R.id.districtlist_farmer_SP);
        taluklist_SP = (Spinner) findViewById(R.id.taluklist_farmer_SP);
        villagelist_SP = (Spinner) findViewById(R.id.villagelist_farmer_SP);
      //  grampanchayatlist_SP = (Spinner) findViewById(R.id.grampanchayatlist_farmer_SP);
        spinnerlayout_ll = (LinearLayout) findViewById(R.id.spinnerlayout_LL);
        viewspinner_tv = (TextView) findViewById(R.id.viewspinner_TV);

        downarrow_ib = (ImageButton) findViewById(R.id.downarrow_IB);
        uparrow_ib = (ImageButton) findViewById(R.id.uparrow_IB);

        edt_FirstName = (EditText) findViewById(R.id.edt_FirstName);
        edt_LastName = (EditText) findViewById(R.id.edt_LastName);
        edt_MiddleName = (EditText) findViewById(R.id.edt_MiddleName);
        edt_BusinessName = (EditText) findViewById(R.id.edt_BusinessName);
        edt_EmailId = (EditText) findViewById(R.id.edt_EmailId);
        edt_MobileNo = (EditText) findViewById(R.id.edt_MobileNo);
        spin_Sector = (Spinner) findViewById(R.id.Sector_sp);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        gender_radiogroup = (RadioGroup) findViewById(R.id.gender_radiogroup);


        sharedpreferencebook_usercredential_Obj=getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_UserId = sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeid, "").trim();



        uparrow_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewspinner_tv.setText("Hide");
                spinnerlayout_ll.setVisibility(View.VISIBLE);
                downarrow_ib.setVisibility(View.VISIBLE);
                uparrow_ib.setVisibility(View.GONE);

            }
        });

        downarrow_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewspinner_tv.setText("Show");
                spinnerlayout_ll.setVisibility(View.GONE);
                downarrow_ib.setVisibility(View.GONE);
                uparrow_ib.setVisibility(View.VISIBLE);

            }
        });

      /*  if(isInternetPresent){
            SQLiteDatabase db1 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
            db1.execSQL("CREATE TABLE IF NOT EXISTS AddEnquiryDetails(FName VARCHAR,MName VARCHAR,LName VARCHAR,MobileNo VARCHAR,EmailId VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,BusinessName VARCHAR,DeviceType VARCHAR,UserId VARCHAR,EnquiryId VARCHAR,Gender VARCHAR);");
            Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM AddEnquiryDetails ", null);
            int x = cursor1.getCount();
            Log.d("cursor Enquiry Count", Integer.toString(x));
            if(x>0) {
                Update_offlineAddEnquiryDetails();
            }
        }else{
            Toast.makeText(getApplicationContext(),"There is no internet connection data will save in offline", Toast.LENGTH_SHORT).show();
        }*/

        btn_submit.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              btn_submit.setVisibility(View.GONE);
                                               FName = edt_FirstName.getText().toString();
                                               MName = edt_MiddleName.getText().toString();
                                               LName = edt_LastName.getText().toString();
                                               MobileNo = edt_MobileNo.getText().toString();
                                               EmailId = edt_EmailId.getText().toString();
                                               BusinessName = edt_BusinessName.getText().toString();

                                               if(validation()) {
                                                   if (isInternetPresent) {

                                                       AddEnquiryDetails addEnquiryDetails = new AddEnquiryDetails(AddEnquiryActivity.this);
                                                       addEnquiryDetails.execute(FName, MName, LName, MobileNo, EmailId, BusinessName, sp_strstate_ID, sp_strdistrict_ID, sp_strTaluk_ID, sp_strVillage_ID, sp_strsector_ID, str_UserId,str_gender);
                                                       //DBCreate_ListEnquiryDetails_2SQLiteDB(addEnquiryList);
                                                       ClearEditTextAfterDoneTask();
                                                   } else {
                                                       addEnquiryList.clear();
                                                       Class_AddEnquiryDetailsDetails class_addEnquiryDetailsDetails1 = new Class_AddEnquiryDetailsDetails(FName, MName, LName, MobileNo, EmailId, sp_strstate_ID, sp_strdistrict_ID, sp_strTaluk_ID, sp_strVillage_ID, sp_strsector_ID, BusinessName, "MOB", str_UserId, "0",str_gender);
                                                       addEnquiryList.add(class_addEnquiryDetailsDetails1);
                                                     //  TempId="1";
                                                    //   DBCreate_AddEnquiryDetails_insert_2SQLiteDB(addEnquiryList);
                                                       DBCreate_ListEnquiryDetails_2SQLiteDB(addEnquiryList);
                                                       ClearEditTextAfterDoneTask();
                                                       Log.e("tag", "offline data=" + addEnquiryList.get(0).getFName());
                                                   }
                                               }
                                          }
                                      });

            /*    yearlist_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_yearDetails = (Class_YearListDetails) yearlist_SP.getSelectedItem();
                sp_stryear_ID = Obj_Class_yearDetails.getYearID().toString();
                selected_year = yearlist_SP.getSelectedItem().toString();
                int sel_yearsp_new = yearlist_SP.getSelectedItemPosition();
                //  farmerListViewAdapter.notifyDataSetChanged();

                if(sel_yearsp_new!=sel_yearsp) {
                    sel_yearsp=sel_yearsp_new;
                    *//*ViewFarmerList_arraylist.clear();
                    farmerListViewAdapter.notifyDataSetChanged();*//*
                    statelist_SP.setSelection(0);
                    districtlist_SP.setSelection(0);
                    taluklist_SP.setSelection(0);
                    villagelist_SP.setSelection(0);
                    grampanchayatlist_SP.setSelection(0);
                }
              *//*  originalViewFarmerList.clear();
                ViewFarmerList_arraylist.clear();
                farmerListViewAdapter.notifyDataSetChanged();
                farmer_listview.setAdapter(farmerListViewAdapter);*//*

               *//* statelist_SP.setSelection(0);
                districtlist_SP.setSelection(0);
                taluklist_SP.setSelection(0);
                villagelist_SP.setSelection(0);
                grampanchayatlist_SP.setSelection(0);*//*
                // Update_stateid_spinner(sp_stryear_ID);
                //  Log.e("sp_stryear_ID", " : " + sp_stryear_ID);

                //Update_ids_farmerlist_listview(sp_stryear_ID,"","","","","");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/
        statelist_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                Obj_Class_stateDetails = (Class_StateListDetails) statelist_SP.getSelectedItem();
                sp_strstate_ID = Obj_Class_stateDetails.getState_id().toString();
                selected_stateName = statelist_SP.getSelectedItem().toString();
                int sel_statesp_new = statelist_SP.getSelectedItemPosition();

                Update_districtid_spinner(sp_strstate_ID);
                if(sel_statesp_new!=sel_statesp) {
                    sel_statesp=sel_statesp_new;
                  /*  ViewFarmerList_arraylist.clear();
                    farmerListViewAdapter.notifyDataSetChanged();*/
                    districtlist_SP.setSelection(0);
                    taluklist_SP.setSelection(0);
                    villagelist_SP.setSelection(0);
                  //  grampanchayatlist_SP.setSelection(0);
                }
     /*           originalViewFarmerList.clear();
                ViewFarmerList_arraylist.clear();
                farmerListViewAdapter.notifyDataSetChanged();
                farmer_listview.setAdapter(farmerListViewAdapter);
*/
              /*  districtlist_SP.setSelection(0);
                taluklist_SP.setSelection(0);
                villagelist_SP.setSelection(0);
                grampanchayatlist_SP.setSelection(0);*/
                //  Log.e("selected_stateName", " : " + selected_stateName);
                // Log.e("sp_strstate_ID", " : " + sp_strstate_ID);
                // Update_ids_farmerlist_listview(sp_stryear_ID,sp_strstate_ID,"","","","");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        districtlist_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_DistrictDetails = (Class_DistrictListDetails) districtlist_SP.getSelectedItem();
                sp_strdistrict_ID = Obj_Class_DistrictDetails.getDistrict_id();
                sp_strdistrict_state_ID = Obj_Class_DistrictDetails.getState_id();
                selected_district = districtlist_SP.getSelectedItem().toString();
                int sel_districtsp_new = districtlist_SP.getSelectedItemPosition();
//                Log.i("selected_district", " : " + selected_district);
//                Log.i("sp_strdistrict_state_ID", " : " + sp_strdistrict_state_ID);
                //Log.e("sp_strdistrict_ID", " : " + sp_strdistrict_ID);
                // Log.i("sp_strstate_ID", " : " + sp_strstate_ID);

                Update_TalukId_spinner(sp_strdistrict_ID);
                // Update_TalukId_spinner("5623");
                //Update_GramPanchayatID_spinner(sp_strdistrict_ID);

                if(sel_districtsp_new!=sel_districtsp) {
                    sel_districtsp=sel_districtsp_new;
                   /* ViewFarmerList_arraylist.clear();
                    farmerListViewAdapter.notifyDataSetChanged();*/
                    taluklist_SP.setSelection(0);
                    villagelist_SP.setSelection(0);
                 //   grampanchayatlist_SP.setSelection(0);
                }
           /*     originalViewFarmerList.clear();
                ViewFarmerList_arraylist.clear();
                farmerListViewAdapter.notifyDataSetChanged();
                farmer_listview.setAdapter(farmerListViewAdapter);
*/
                /*taluklist_SP.setSelection(0);
                villagelist_SP.setSelection(0);
                grampanchayatlist_SP.setSelection(0);*/
                // Update_ids_farmerlist_listview(sp_stryear_ID,sp_strstate_ID,sp_strdistrict_ID,"","","");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        taluklist_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_TalukDetails = (Class_TalukListDetails) taluklist_SP.getSelectedItem();
                sp_strTaluk_ID = Obj_Class_TalukDetails.getTaluk_id();
                selected_taluk = taluklist_SP.getSelectedItem().toString();
                int sel_taluksp_new = taluklist_SP.getSelectedItemPosition();
                // Update_VillageId_spinner("5433");//5516,sp_strTaluk_ID
//                Log.i("selected_taluk", " : " + selected_taluk);
//
//                Log.e("sp_stryear_ID..", sp_stryear_ID);
//                Log.e("sp_strstate_ID..", sp_strstate_ID);
//                Log.e("sp_strdistrict_ID..", sp_strdistrict_ID);
                //Log.e("sp_strTaluk_ID..", sp_strTaluk_ID);
                Update_VillageId_spinner(sp_strTaluk_ID);

                if(sel_taluksp_new!=sel_taluksp) {
                    sel_taluksp=sel_taluksp_new;
                    /*ViewFarmerList_arraylist.clear();
                    farmerListViewAdapter.notifyDataSetChanged();
*/
                    villagelist_SP.setSelection(0);
                 //   grampanchayatlist_SP.setSelection(0);
                }
              /*  originalViewFarmerList.clear();
                ViewFarmerList_arraylist.clear();
                farmerListViewAdapter.notifyDataSetChanged();
                farmer_listview.setAdapter(farmerListViewAdapter);*/
/*
                villagelist_SP.setSelection(0);
                grampanchayatlist_SP.setSelection(0);*/
                // Update_ids_farmerlist_listview(sp_stryear_ID,sp_strstate_ID,sp_strdistrict_ID,sp_strTaluk_ID,"","");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        villagelist_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_VillageListDetails = (Class_VillageListDetails) villagelist_SP.getSelectedItem();
                sp_strVillage_ID = Obj_Class_VillageListDetails.getVillage_id();
                selected_village = villagelist_SP.getSelectedItem().toString();

                int sel_villagesp_new = villagelist_SP.getSelectedItemPosition();

                if(sel_villagesp_new!=sel_villagesp) {
                    sel_villagesp=sel_villagesp_new;
                   /* ViewFarmerList_arraylist.clear();
                    farmerListViewAdapter.notifyDataSetChanged();*/

                  //  grampanchayatlist_SP.setSelection(0);
                }
                // Log.i("selected_village", " : " + selected_village);

                //  Update_ids_farmerlist_listview(sp_stryear_ID,sp_strstate_ID,sp_strdistrict_ID,sp_strTaluk_ID,sp_strVillage_ID,"");

                // Update_ids_farmerlist_listview("3","1","539","5700","626790","60");
              /*  originalViewFarmerList.clear();
                ViewFarmerList_arraylist.clear();
                farmerListViewAdapter.notifyDataSetChanged();
                farmer_listview.setAdapter(farmerListViewAdapter);*/

                /*grampanchayatlist_SP.setSelection(0);*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       /* grampanchayatlist_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_GramanchayatDetails = (Class_GrampanchayatListDetails) grampanchayatlist_SP.getSelectedItem();
                sp_strgrampanchayat_ID = Obj_Class_GramanchayatDetails.getGramanchayat_id().toString();
                selected_grampanchayat = Obj_Class_GramanchayatDetails.getGramanchayat_name().toString();
                int sel_grampanchayatsp_new = grampanchayatlist_SP.getSelectedItemPosition();

                if(sel_grampanchayatsp_new!=sel_grampanchayatsp) {
                    sel_grampanchayatsp=sel_grampanchayatsp_new;
                  *//*  ViewFarmerList_arraylist.clear();
                    farmerListViewAdapter.notifyDataSetChanged();*//*
                }
               *//* Log.e("sp_stryear_ID..", sp_stryear_ID);
                Log.e("sp_strstate_ID..", sp_strstate_ID);
                Log.e("sp_strdistrict_ID..", sp_strdistrict_ID);
                Log.e("sp_strTaluk_ID..", sp_strTaluk_ID);
                Log.e("sp_strVillage_ID..", sp_strVillage_ID);
                Log.e("sp_strpachayat_ID..", sp_strgrampanchayat_ID);*//*
//                Log.e("selected_grampanchayat", selected_grampanchayat);

                //Update_ViewFarmerListID_spinner(sp_stryear_ID, sp_strstate_ID, sp_strdistrict_ID, sp_strTaluk_ID,sp_strVillage_ID);


                //Update_ids_farmerlist_listview("1","25","635","5663","626707","18");

            //    Update_ids_farmerlist_listview(sp_stryear_ID, sp_strstate_ID, sp_strdistrict_ID, sp_strTaluk_ID, sp_strVillage_ID, sp_strgrampanchayat_ID);
                // Update_ids_farmerlist_listview("2019","1","539","5700","626790","60");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/
        spin_Sector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_ClasssectorListDetails = (Class_SectorListDetails) spin_Sector.getSelectedItem();
                sp_strsector_ID = Obj_ClasssectorListDetails.getSectorId().toString();
                selected_sector = Obj_ClasssectorListDetails.getSectorName().toString();
                int sel_sectorsp_new = spin_Sector.getSelectedItemPosition();

               /* if(sel_sectorsp_new!=sel_grampanchayatsp) {
                    sel_grampanchayatsp=sel_sectorsp_new;
                  *//*  ViewFarmerList_arraylist.clear();
                    farmerListViewAdapter.notifyDataSetChanged();*//*
                }*/
               /* Log.e("sp_stryear_ID..", sp_stryear_ID);
                Log.e("sp_strstate_ID..", sp_strstate_ID);
                Log.e("sp_strdistrict_ID..", sp_strdistrict_ID);
                Log.e("sp_strTaluk_ID..", sp_strTaluk_ID);
                Log.e("sp_strVillage_ID..", sp_strVillage_ID);
                Log.e("sp_strpachayat_ID..", sp_strgrampanchayat_ID);*/
//                Log.e("selected_grampanchayat", selected_grampanchayat);

                //Update_ViewFarmerListID_spinner(sp_stryear_ID, sp_strstate_ID, sp_strdistrict_ID, sp_strTaluk_ID,sp_strVillage_ID);


                //Update_ids_farmerlist_listview("1","25","635","5663","626707","18");

                //    Update_ids_farmerlist_listview(sp_stryear_ID, sp_strstate_ID, sp_strdistrict_ID, sp_strTaluk_ID, sp_strVillage_ID, sp_strgrampanchayat_ID);
                // Update_ids_farmerlist_listview("2019","1","539","5700","626790","60");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //uploadfromDB_Yearlist();
        uploadfromDB_Statelist();
        uploadfromDB_Districtlist();
        uploadfromDB_Taluklist();
        uploadfromDB_Villagelist();
      //  uploadfromDB_Grampanchayatlist();
        uploadfromDB_Sectorlist();
        GetSyncSlno_VillageTable_B4insertion();

    }

    public boolean validation()
    {

        Boolean bfname=true,bmobileno=true,bBusinessname=true,bsector=true;
        String str_Sector = spin_Sector.getSelectedItem().toString();


        if( edt_FirstName.getText().toString().length() == 0 ){
            edt_FirstName.setError( "First name is required!" );bfname=false;}

        if (edt_MobileNo.getText().toString().length() == 0 || edt_MobileNo.getText().toString().length() < 10) {
            edt_MobileNo.setError("InValid Mobile Number");
            bmobileno=false;
        }

        if( edt_BusinessName.getText().toString().length() == 0 ){
            edt_BusinessName.setError( "Business Name is required!" );bBusinessname=false;}

        if(str_Sector.contains("Select Sector")){

            TextView errorText = (TextView)spin_Sector.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Please select the Sector");
           // Toast.makeText(AddEnquiryActivity.this,"Please select the Sector",Toast.LENGTH_LONG).show();
            bsector=false;
            // return false;
        }
        if (gender_radiogroup.getCheckedRadioButtonId() == -1)
        {
            // no radio buttons are checked
            //  Toast.makeText(getApplicationContext(), "Please select Gender", Toast.LENGTH_SHORT).show();
            Toast toast= Toast.makeText(getApplicationContext(), "  Please select Gender  " , Toast.LENGTH_LONG);
            View view =toast.getView();
            view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
            toast.show();
            bfname=false;
        }
        String email = edt_EmailId.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(edt_EmailId.getText().toString().length() != 0) {
            if (!email.matches(emailPattern)) {

                edt_EmailId.setError("InValid Email Id");
                bfname = false;
            }
        }else{
            bfname = true;
        }

        if(bfname&&bmobileno&&bBusinessname&&bsector) {
            return true;
        }
        else{
            btn_submit.setVisibility(View.VISIBLE);
            Toast toast= Toast.makeText(getApplicationContext(), "  Please fill mandatory fields" , Toast.LENGTH_LONG);
            View view =toast.getView();
            view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
            toast.show();
            return false;
        }
    }

    public void ClearEditTextAfterDoneTask() {
        edt_BusinessName.getText().clear();
        edt_MobileNo.getText().clear();
        edt_MiddleName.getText().clear();
        edt_FirstName.getText().clear();
        edt_EmailId.getText().clear();
        edt_LastName.getText().clear();
        spin_Sector.setSelection(0);
        btn_submit.setVisibility(View.VISIBLE);
    }


    public void uploadfromDB_Yearlist() {

        SQLiteDatabase db_year = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_year.execSQL("CREATE TABLE IF NOT EXISTS YearList(YearID VARCHAR,YearName VARCHAR);");
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

            } while (cursor.moveToNext());


        }//if ends

        db_year.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_yearDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
            yearlist_SP.setAdapter(dataAdapter);
            if(x>sel_yearsp) {
                yearlist_SP.setSelection(sel_yearsp);
            }
        }

    }

    public void DBCreate_Yeardetails_insert_2SQLiteDB(String str_yearID, String str_yearname) {
        SQLiteDatabase db_yearlist = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_yearlist.execSQL("CREATE TABLE IF NOT EXISTS YearList(YearID VARCHAR,YearName VARCHAR);");


        String SQLiteQuery = "INSERT INTO YearList (YearID, YearName)" +
                " VALUES ('" + str_yearID + "','" + str_yearname + "');";
        db_yearlist.execSQL(SQLiteQuery);


        //Log.e("str_yearID DB", str_yearID);
        // Log.e("str_yearname DB", str_yearname);
        db_yearlist.close();
    }

    public void deleteYearTable_B4insertion() {

        SQLiteDatabase db_yearlist_delete = openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_yearlist_delete.execSQL("CREATE TABLE IF NOT EXISTS YearList(YearID VARCHAR,YearName VARCHAR);");
        Cursor cursor = db_yearlist_delete.rawQuery("SELECT * FROM YearList", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_yearlist_delete.delete("YearList", null, null);

        }
        db_yearlist_delete.close();
    }

    public void uploadfromDB_Sectorlist() {

        SQLiteDatabase db_sector = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_sector.execSQL("CREATE TABLE IF NOT EXISTS SectorList(SectorId VARCHAR,SectorName VARCHAR);");
        Cursor cursor = db_sector.rawQuery("SELECT DISTINCT * FROM SectorList", null);
        int x = cursor.getCount();
        Log.d("cursor count sector", Integer.toString(x));

        int i = 0;
        arrayObj_Class_sectorDetails2 = new Class_SectorListDetails[x];
        if (cursor.moveToFirst()) {

            do {
                Class_SectorListDetails innerObj_Class_sectorList = new Class_SectorListDetails();
                innerObj_Class_sectorList.setSectorId(cursor.getString(cursor.getColumnIndex("SectorId")));
                innerObj_Class_sectorList.setSectorName(cursor.getString(cursor.getColumnIndex("SectorName")));

                arrayObj_Class_sectorDetails2[i] = innerObj_Class_sectorList;
                i++;

            } while (cursor.moveToNext());


        }//if ends

        db_sector.close();
        if (x > 0) {

            ArrayAdapter dataAdapter2 = new ArrayAdapter(this, R.layout.spinnercustomstyle, arrayObj_Class_sectorDetails2);
            dataAdapter2.setDropDownViewResource(R.layout.spinnercustomstyle);
            // attaching data adapter to spinner
            spin_Sector.setAdapter(dataAdapter2);

            if(x>sel_sector) {
                spin_Sector.setSelection(sel_sector);
            }
        }

    }

    public void DBCreate_Sector_insert_2SQLiteDB(ArrayList<String> str_SectorId, ArrayList<String> str_SectorName) {
        SQLiteDatabase db_sectorlist = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_sectorlist.execSQL("CREATE TABLE IF NOT EXISTS SectorList(SectorId VARCHAR,SectorName VARCHAR);");


        for(int s=0;s<str_SectorId.size();s++) {
            Log.e("tag","str_SectorId="+str_SectorId.get(s)+"str_SectorName="+str_SectorName.get(s));
            String SQLiteQuery = "INSERT INTO SectorList (SectorId, SectorName)" +
                    " VALUES ('" + str_SectorId.get(s) + "','" + str_SectorName.get(s) + "');";
            db_sectorlist.execSQL(SQLiteQuery);
        }


        //Log.e("str_yearID DB", str_yearID);
        // Log.e("str_yearname DB", str_yearname);
        db_sectorlist.close();
    }

    public void deleteSectorTable_B4insertion() {

        SQLiteDatabase db_sectorlist_delete = openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_sectorlist_delete.execSQL("CREATE TABLE IF NOT EXISTS SectorList(SectorId VARCHAR,SectorName VARCHAR);");
        Cursor cursor = db_sectorlist_delete.rawQuery("SELECT * FROM SectorList", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_sectorlist_delete.delete("SectorList", null, null);

        }
        db_sectorlist_delete.close();
    }

    public void DBCreate_Statedetails_insert_2SQLiteDB(String str_stateID, String str_statename, String state_status) {
        SQLiteDatabase db_statelist = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_statelist.execSQL("CREATE TABLE IF NOT EXISTS StateList(StateID VARCHAR,StateName VARCHAR,state_status VARCHAR);");

      /*  String Query = "Select * from StateList where StateID = " + "'" + str_stateID + "'";
        Cursor cursor = db_statelist.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            //return false;
        }else {
            String SQLiteQuery = "INSERT INTO StateList (StateID,StateName,state_status)" +
                    " VALUES ('" + str_stateID + "','" + str_statename + "','" + state_status + "');";
            db_statelist.execSQL(SQLiteQuery);
        }
*/
        String SQLiteQuery = "INSERT INTO StateList (StateID, StateName, state_status)"+
        "SELECT * FROM (SELECT '"+str_stateID+"', '"+str_statename+"', '"+state_status+"') AS tmp "+
        "WHERE NOT EXISTS ("+
                "SELECT StateID FROM StateList WHERE StateID = '"+str_stateID+"'"+
        ")";
        db_statelist.execSQL(SQLiteQuery);
        /*insert into tablename (code)
        Select '1448523' Where not exists(select * from tablename where code='1448523')*/

       /* String SQLiteQuery = "INSERT INTO StateList (StateID,StateName,state_status) Select StateID Where not exists(select * from StateList where StateID='"+str_stateID+"')";
        db_statelist.execSQL(SQLiteQuery);*/

       /* String SQLiteQuery = "INSERT INTO StateList (StateID,StateName,state_status)" +
                " VALUES ('" + str_stateID + "','" + str_statename + "','" + state_status + "');";
        db_statelist.execSQL(SQLiteQuery);*/

//        Log.e("str_stateID DB", str_stateID);
//        Log.e("str_statename DB", str_statename);
//        Log.e("str_stateyearid DB", str_yearid);
        db_statelist.close();
    }

    public void deleteStateTable_B4insertion() {

        SQLiteDatabase db_statelist_delete = openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_statelist_delete.execSQL("CREATE TABLE IF NOT EXISTS StateList(StateID VARCHAR,StateName VARCHAR,state_status VARCHAR);");
        Cursor cursor = db_statelist_delete.rawQuery("SELECT * FROM StateList", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_statelist_delete.delete("StateList", null, null);

        }
        db_statelist_delete.close();
    }

    public void DBCreate_Districtdetails_insert_2SQLiteDB(String str_districtID, String str_districtname, String str_stateid) {
        SQLiteDatabase db_districtlist = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_districtlist.execSQL("CREATE TABLE IF NOT EXISTS DistrictList(DistrictID VARCHAR,DistrictName VARCHAR,Distr_Stateid VARCHAR);");


       /* String SQLiteQuery = "INSERT INTO DistrictList (DistrictID, DistrictName,Distr_Stateid)" +
                " VALUES ('" + str_districtID + "','" + str_districtname + "','" + str_stateid + "');";
        db_districtlist.execSQL(SQLiteQuery);*/
        /*Cursor cursor1 = db_districtlist.rawQuery("SELECT DistrictID FROM DistrictList WHERE DistrictID = '"+str_districtID+"'", null);
        int x = cursor1.getCount();
        Log.e("Select statment count=", String.valueOf(x));*/

        String SQLiteQuery = "INSERT INTO DistrictList (DistrictID, DistrictName, Distr_Stateid)"+
                "SELECT * FROM (SELECT '"+str_districtID+"', '"+str_districtname+"', '"+str_stateid+"') AS tmp "+
                "WHERE NOT EXISTS ("+
                "SELECT DistrictID FROM DistrictList WHERE DistrictID = '"+str_districtID+"'"+
                ")";
        db_districtlist.execSQL(SQLiteQuery);



//        Log.e("str_districtID DB", str_districtID);
//        Log.e("str_districtname DB", str_districtname);
//        Log.e("str_yearid DB", str_yearid);
//        Log.e("str_stateid DB", str_stateid);
        db_districtlist.close();
    }

    public void deleteDistrictTable_B4insertion() {

        SQLiteDatabase db_districtlist_delete = openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_districtlist_delete.execSQL("CREATE TABLE IF NOT EXISTS DistrictList(DistrictID VARCHAR,DistrictName VARCHAR,Distr_Stateid VARCHAR);");
        Cursor cursor1 = db_districtlist_delete.rawQuery("SELECT * FROM DistrictList", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db_districtlist_delete.delete("DistrictList", null, null);

        }
        db_districtlist_delete.close();
    }

    public void DBCreate_Talukdetails_insert_2SQLiteDB(String str_talukID, String str_talukname, String str_districtid) {

        SQLiteDatabase db_taluklist = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_taluklist.execSQL("CREATE TABLE IF NOT EXISTS TalukList(TalukID VARCHAR,TalukName VARCHAR,Taluk_districtid VARCHAR);");


   /*     String SQLiteQuery = "INSERT INTO TalukList (TalukID, TalukName,Taluk_districtid)" +
                " VALUES ('" + str_talukID + "','" + str_talukname + "','" + str_districtid + "');";
        db_taluklist.execSQL(SQLiteQuery);*/

        String SQLiteQuery = "INSERT INTO TalukList (TalukID, TalukName, Taluk_districtid)"+
                "SELECT * FROM (SELECT '"+str_talukID+"', '"+str_talukname+"', '"+str_districtid+"') AS tmp "+
                "WHERE NOT EXISTS ("+
                "SELECT TalukID FROM TalukList WHERE TalukID = '"+str_talukID+"'"+
                ")";
        db_taluklist.execSQL(SQLiteQuery);
//        Log.e("str_talukID DB", str_talukID);
//        Log.e("str_talukname DB", str_talukname);
//        Log.e("str_districtid DB", str_districtid);
        db_taluklist.close();
    }

    public void deleteTalukTable_B4insertion() {

        SQLiteDatabase db_taluklist_delete = openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_taluklist_delete.execSQL("CREATE TABLE IF NOT EXISTS TalukList(TalukID VARCHAR,TalukName VARCHAR,Taluk_districtid VARCHAR);");
        Cursor cursor1 = db_taluklist_delete.rawQuery("SELECT * FROM TalukList", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db_taluklist_delete.delete("TalukList", null, null);

        }
        db_taluklist_delete.close();
    }

    public void DBCreate_Villagedetails_insert_2SQLiteDB(String str_villageID, String str_village, String str_talukid,String str_SyncSlno) {
        SQLiteDatabase db_villagelist = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_villagelist.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR,SyncSlno VARCHAR);");


        /*String SQLiteQuery = "INSERT INTO VillageList (VillageID, Village,TalukID)" +
                " VALUES ('" + str_villageID + "','" + str_village + "','" + str_talukid + "');";
        db_villagelist.execSQL(SQLiteQuery);*/

        String SQLiteQuery = "INSERT INTO VillageList (VillageID, Village,TalukID,SyncSlno)"+
                "SELECT * FROM (SELECT '"+ str_villageID + "','" + str_village + "','" + str_talukid +"','" + str_SyncSlno +"') AS tmp "+
                "WHERE NOT EXISTS ("+
                "SELECT VillageID FROM VillageList WHERE VillageID = '"+str_villageID+"'"+
                ")";
        db_villagelist.execSQL(SQLiteQuery);
//        Log.e("str_villageID DB", str_villageID);
//        Log.e("str_village DB", str_village);
//        Log.e("str_talukid DB", str_talukid);
        db_villagelist.close();
    }

    public void deleteVillageTable_B4insertion() {

        SQLiteDatabase db_villagelist_delete = openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_villagelist_delete.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR,SyncSlno VARCHAR);");
        Cursor cursor1 = db_villagelist_delete.rawQuery("SELECT * FROM VillageList", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db_villagelist_delete.delete("VillageList", null, null);

        }
        db_villagelist_delete.close();
    }

    public void GetSyncSlno_VillageTable_B4insertion() {

        SQLiteDatabase db_villagelist_syncslno = openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_villagelist_syncslno.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR,SyncSlno VARCHAR);");
        Cursor cursor1 = db_villagelist_syncslno.rawQuery("SELECT SyncSlno FROM VillageList WHERE VillageID=570686", null);
        int x = cursor1.getCount();

        if (cursor1.moveToFirst()) {

            do {

                SyncSlno = cursor1.getString(cursor1.getColumnIndex("SyncSlno"));

                Log.e("tag","SyncSlno=="+SyncSlno);
            } while (cursor1.moveToNext());
        }
            db_villagelist_syncslno.close();

    }
    public void DBCreate_Grampanchayatdetails_insert_2SQLiteDB(String str_grampanchayatID, String str_grampanchayat, String str_distid) {

        SQLiteDatabase db_panchayatlist = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_panchayatlist.execSQL("CREATE TABLE IF NOT EXISTS GrampanchayatList(GramanchayatID VARCHAR,Gramanchayat VARCHAR,Panchayat_DistID VARCHAR);");


        String SQLiteQuery = "INSERT INTO GrampanchayatList (GramanchayatID, Gramanchayat,Panchayat_DistID)" +
                " VALUES ('" + str_grampanchayatID + "','" + str_grampanchayat + "','" + str_distid + "');";
        db_panchayatlist.execSQL(SQLiteQuery);
        // }
//        Log.e("str_panchayatID DB", str_grampanchayatID);
//        Log.e("str_panchayat DB", str_grampanchayat);
//        Log.e("str_distid DB", str_distid);
        db_panchayatlist.close();
    }

    public void deleteGrampanchayatTable_B4insertion() {

        SQLiteDatabase db_grampanchayatlist_delete = openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_grampanchayatlist_delete.execSQL("CREATE TABLE IF NOT EXISTS GrampanchayatList(GramanchayatID VARCHAR,Gramanchayat VARCHAR,Panchayat_DistID VARCHAR);");
        Cursor cursor1 = db_grampanchayatlist_delete.rawQuery("SELECT * FROM GrampanchayatList", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db_grampanchayatlist_delete.delete("GrampanchayatList", null, null);

        }
        db_grampanchayatlist_delete.close();
    }

    //////////////////////////////////////////////////////////////////////////////////

    public void uploadfromDB_Statelist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS StateList(StateID VARCHAR,StateName VARCHAR,state_status VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StateList ORDER BY StateName", null);
        int x = cursor1.getCount();
        Log.d("cursor Scount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_stateDetails2 = new Class_StateListDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_StateListDetails innerObj_Class_SandboxList = new Class_StateListDetails();
                innerObj_Class_SandboxList.setState_id(cursor1.getString(cursor1.getColumnIndex("StateID")));
                innerObj_Class_SandboxList.setState_name(cursor1.getString(cursor1.getColumnIndex("StateName")));
                innerObj_Class_SandboxList.setState_staus(cursor1.getString(cursor1.getColumnIndex("state_status")));


                arrayObj_Class_stateDetails2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db1.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_stateDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
            statelist_SP.setAdapter(dataAdapter);
            if(x>sel_statesp) {
                statelist_SP.setSelection(sel_statesp);
            }
        }

    }

   /* public void Update_stateid_spinner(String str_yearids) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS StateList(StateID VARCHAR,StateName VARCHAR,state_status VARCHAR);");
//        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StateList WHERE YearId='" + str_yearids + "'", null);
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StateList WHERE state_yearid='" + str_yearids + "'", null);


        int x = cursor1.getCount();
        Log.d("cursor Scount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_stateDetails2 = new Class_StateListDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_StateListDetails innerObj_Class_AcademicList = new Class_StateListDetails();
                innerObj_Class_AcademicList.setState_id(cursor1.getString(cursor1.getColumnIndex("StateID")));
                innerObj_Class_AcademicList.setState_name(cursor1.getString(cursor1.getColumnIndex("StateName")));
                innerObj_Class_AcademicList.setYear_id(cursor1.getString(cursor1.getColumnIndex("state_yearid")));


                arrayObj_Class_stateDetails2[i] = innerObj_Class_AcademicList;
                i++;
            } while (cursor1.moveToNext());
        }//if ends


        db1.close();
        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_stateDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
            statelist_SP.setAdapter(dataAdapter);
            if(x>sel_statesp) {
                statelist_SP.setSelection(sel_statesp);
            }
        }

    }
*/

    public void uploadfromDB_Districtlist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS DistrictList(DistrictID VARCHAR,DistrictName VARCHAR,Distr_Stateid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM DistrictList ORDER BY DistrictName", null);
        int x = cursor1.getCount();
        Log.d("cursor Dcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_DistrictListDetails2 = new Class_DistrictListDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_DistrictListDetails innerObj_Class_SandboxList = new Class_DistrictListDetails();
                innerObj_Class_SandboxList.setDistrict_id(cursor1.getString(cursor1.getColumnIndex("DistrictID")));
                innerObj_Class_SandboxList.setDistrict_name(cursor1.getString(cursor1.getColumnIndex("DistrictName")));
                //innerObj_Class_SandboxList.setYear_id(cursor1.getString(cursor1.getColumnIndex("Distr_yearid")));
                innerObj_Class_SandboxList.setState_id(cursor1.getString(cursor1.getColumnIndex("Distr_Stateid")));


                arrayObj_Class_DistrictListDetails2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db1.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_DistrictListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
            districtlist_SP.setAdapter(dataAdapter);
            if(x>sel_districtsp) {
                districtlist_SP.setSelection(sel_districtsp);
            }
        }

    }

    public void Update_districtid_spinner(String str_stateid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS DistrictList(DistrictID VARCHAR,DistrictName VARCHAR,Distr_Stateid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM DistrictList WHERE Distr_Stateid='" + str_stateid + "' ORDER BY DistrictName", null);
        //Cursor cursor1 = db1.rawQuery("select * from DistrictList D, TalukList T where D.DistrictID=T.Taluk_districtid AND D.Distr_Stateid='" + str_stateid + "'",null);

        int x = cursor1.getCount();
        Log.d("cursor Dcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_DistrictListDetails2 = new Class_DistrictListDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_DistrictListDetails innerObj_Class_AcademicList = new Class_DistrictListDetails();
                innerObj_Class_AcademicList.setDistrict_id(cursor1.getString(cursor1.getColumnIndex("DistrictID")));
                innerObj_Class_AcademicList.setDistrict_name(cursor1.getString(cursor1.getColumnIndex("DistrictName")));
                //innerObj_Class_AcademicList.setYear_id(cursor1.getString(cursor1.getColumnIndex("Distr_yearid")));
                innerObj_Class_AcademicList.setState_id(cursor1.getString(cursor1.getColumnIndex("Distr_Stateid")));


                arrayObj_Class_DistrictListDetails2[i] = innerObj_Class_AcademicList;
                i++;
            } while (cursor1.moveToNext());
        }//if ends


        db1.close();
        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_DistrictListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
            districtlist_SP.setAdapter(dataAdapter);
            if(x>sel_districtsp) {
                districtlist_SP.setSelection(sel_districtsp);
            }
        }

    }

    public void uploadfromDB_Taluklist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS TalukList(TalukID VARCHAR,TalukName VARCHAR,Taluk_districtid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM TalukList ORDER BY TalukName", null);

        // Cursor cursor2 = db1.rawQuery("select T.taluka_id,V.village_id,V.village_name from master_state S, master_district D, master_taluka T, master_village V, master_panchayat P where S.state_id=D.state_id AND D.district_id=T.district_id AND T.taluka_id=V.taluk_id AND T.district_id=P.district_id AND (S.state_id in (1,12,25))",null);
        //  Cursor cursor1 = db1.rawQuery("select T.TalukID,T.TalukName,T.Taluk_districtid from DistrictList D, TalukList T where D.DistrictID=T.Taluk_districtid",null);

        int x = cursor1.getCount();
        Log.d("cursor Tcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_TalukListDetails2 = new Class_TalukListDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_TalukListDetails innerObj_Class_SandboxList = new Class_TalukListDetails();
                innerObj_Class_SandboxList.setTaluk_id(cursor1.getString(cursor1.getColumnIndex("TalukID")));
                if(cursor1.getString(cursor1.getColumnIndex("TalukName")).isEmpty())
                {
                    innerObj_Class_SandboxList.setTaluk_name("Empty In DB");
                }else{
                    innerObj_Class_SandboxList.setTaluk_name(cursor1.getString(cursor1.getColumnIndex("TalukName")));
                }
                //innerObj_Class_SandboxList.setTaluk_name(cursor1.getString(cursor1.getColumnIndex("TalukName")));
                innerObj_Class_SandboxList.setDistrict_id(cursor1.getString(cursor1.getColumnIndex("Taluk_districtid")));


                arrayObj_Class_TalukListDetails2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db1.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_TalukListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
            taluklist_SP.setAdapter(dataAdapter);
            if(x>sel_taluksp) {
                taluklist_SP.setSelection(sel_taluksp);
            }
        }

    }

    public void Update_TalukId_spinner(String str_distid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS TalukList(TalukID VARCHAR,TalukName VARCHAR,Taluk_districtid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM TalukList WHERE Taluk_districtid='" + str_distid + "' ORDER BY TalukName", null);
        int x = cursor1.getCount();
        Log.d("cursor Tcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_TalukListDetails2 = new Class_TalukListDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_TalukListDetails innerObj_Class_talukList = new Class_TalukListDetails();
                innerObj_Class_talukList.setTaluk_id(cursor1.getString(cursor1.getColumnIndex("TalukID")));
                innerObj_Class_talukList.setTaluk_name(cursor1.getString(cursor1.getColumnIndex("TalukName")));
                innerObj_Class_talukList.setDistrict_id(cursor1.getString(cursor1.getColumnIndex("Taluk_districtid")));


                arrayObj_Class_TalukListDetails2[i] = innerObj_Class_talukList;
                //Log.e("taluk_name",cursor1.getString(cursor1.getColumnIndex("TalukName")));
                i++;
            } while (cursor1.moveToNext());
        }//if ends


        db1.close();
        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_TalukListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
            taluklist_SP.setAdapter(dataAdapter);
            if(x>sel_taluksp) {
                taluklist_SP.setSelection(sel_taluksp);
            }
        }

    }

    public void uploadfromDB_Villagelist() {

        SQLiteDatabase db_village = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_village.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR,SyncSlno VARCHAR);");
        Cursor cursor1 = db_village.rawQuery("SELECT DISTINCT * FROM VillageList ORDER BY Village", null);
        int x = cursor1.getCount();
        Log.d("cursor Vcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_VillageListDetails2 = new Class_VillageListDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_VillageListDetails innerObj_Class_villageList = new Class_VillageListDetails();
                innerObj_Class_villageList.setVillage_id(cursor1.getString(cursor1.getColumnIndex("VillageID")));
                innerObj_Class_villageList.setVillage_name(cursor1.getString(cursor1.getColumnIndex("Village")));
                innerObj_Class_villageList.setTaluk_id(cursor1.getString(cursor1.getColumnIndex("TalukID")));
                innerObj_Class_villageList.setSyncSlno(cursor1.getString(cursor1.getColumnIndex("SyncSlno")));


                arrayObj_Class_VillageListDetails2[i] = innerObj_Class_villageList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db_village.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_VillageListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
            villagelist_SP.setAdapter(dataAdapter);
            if(x>sel_villagesp) {
                villagelist_SP.setSelection(sel_villagesp);
            }
        }

    }

    public void Update_VillageId_spinner(String str_talukid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR,SyncSlno VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM VillageList WHERE TalukID='" + str_talukid + "' ORDER BY Village", null);
        int x = cursor1.getCount();
        Log.d("cursor Vcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_VillageListDetails2 = new Class_VillageListDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_VillageListDetails innerObj_Class_villageList = new Class_VillageListDetails();
                innerObj_Class_villageList.setVillage_id(cursor1.getString(cursor1.getColumnIndex("VillageID")));
                innerObj_Class_villageList.setVillage_name(cursor1.getString(cursor1.getColumnIndex("Village")));
                innerObj_Class_villageList.setTaluk_id(cursor1.getString(cursor1.getColumnIndex("TalukID")));
                innerObj_Class_villageList.setSyncSlno(cursor1.getString(cursor1.getColumnIndex("SyncSlno")));


                arrayObj_Class_VillageListDetails2[i] = innerObj_Class_villageList;
                // Log.e("village_name", cursor1.getString(cursor1.getColumnIndex("TalukName")));
                i++;
            } while (cursor1.moveToNext());
        }//if ends


        db1.close();
        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_VillageListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
            villagelist_SP.setAdapter(dataAdapter);
            if(x>sel_villagesp) {
                villagelist_SP.setSelection(sel_villagesp);
            }

        }

    }

   /* public void uploadfromDB_Grampanchayatlist() {

        SQLiteDatabase db_grampanchayat = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_grampanchayat.execSQL("CREATE TABLE IF NOT EXISTS GrampanchayatList(GramanchayatID VARCHAR,Gramanchayat VARCHAR,Panchayat_DistID VARCHAR);");
        Cursor cursor1 = db_grampanchayat.rawQuery("SELECT DISTINCT * FROM GrampanchayatList", null);
        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_GrampanchayatListDetails2 = new Class_GrampanchayatListDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_GrampanchayatListDetails innerObj_Class_panchayatList = new Class_GrampanchayatListDetails();
                innerObj_Class_panchayatList.setGramanchayat_id(cursor1.getString(cursor1.getColumnIndex("GramanchayatID")));
                innerObj_Class_panchayatList.setGramanchayat_name(cursor1.getString(cursor1.getColumnIndex("Gramanchayat")));
                innerObj_Class_panchayatList.setDistrictid(cursor1.getString(cursor1.getColumnIndex("Panchayat_DistID")));


                arrayObj_Class_GrampanchayatListDetails2[i] = innerObj_Class_panchayatList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db_grampanchayat.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_GrampanchayatListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
            grampanchayatlist_SP.setAdapter(dataAdapter);
            if(x>sel_grampanchayatsp) {
                grampanchayatlist_SP.setSelection(sel_grampanchayatsp);
            }

        }

    }


    public void Update_GramPanchayatID_spinner(String str_ditrictid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS GrampanchayatList(GramanchayatID VARCHAR,Gramanchayat VARCHAR,Panchayat_DistID VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM GrampanchayatList WHERE Panchayat_DistID='" + str_ditrictid + "'", null);
        int x = cursor1.getCount();
        Log.d("cursor Gcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_GrampanchayatListDetails2 = new Class_GrampanchayatListDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_GrampanchayatListDetails innerObj_Class_panchayatList = new Class_GrampanchayatListDetails();
                innerObj_Class_panchayatList.setGramanchayat_id(cursor1.getString(cursor1.getColumnIndex("GramanchayatID")));
                innerObj_Class_panchayatList.setGramanchayat_name(cursor1.getString(cursor1.getColumnIndex("Gramanchayat")));
                innerObj_Class_panchayatList.setDistrictid(cursor1.getString(cursor1.getColumnIndex("Panchayat_DistID")));


                arrayObj_Class_GrampanchayatListDetails2[i] = innerObj_Class_panchayatList;
                i++;
            } while (cursor1.moveToNext());
        }//if ends


        db1.close();
        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_GrampanchayatListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
            grampanchayatlist_SP.setAdapter(dataAdapter);
            if(x>sel_grampanchayatsp) {
                grampanchayatlist_SP.setSelection(sel_grampanchayatsp);
            }
        }

    }
*/

    ////////////////////////////////////////////////////////
    public void DBCreate_AddEnquiryDetails_insert_2SQLiteDB(ArrayList<Class_AddEnquiryDetailsDetails> str_addenquirys) {
        SQLiteDatabase db_addEnquiry = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_addEnquiry.execSQL("CREATE TABLE IF NOT EXISTS AddEnquiryDetails(FName VARCHAR,MName VARCHAR,LName VARCHAR,MobileNo VARCHAR,EmailId VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,BusinessName VARCHAR,DeviceType VARCHAR,UserId VARCHAR,EnquiryId VARCHAR,Gender VARCHAR,dataSyncStatus VARCHAR);");

        String FName,LName,MName,MobileNo,EmailId,BusinessName,stateId,districtId,talukId,villegeId,sectorId,deviceType,userId,EnquiryId,Gender;

        for(int s=0;s<str_addenquirys.size();s++) {
            Log.e("tag","BusinessName="+str_addenquirys.get(s).getBusinessName()+"Fname="+str_addenquirys.get(s).getFName());

            FName=str_addenquirys.get(s).getFName();
            MName=str_addenquirys.get(s).getMName();
            LName=str_addenquirys.get(s).getLName();
            MobileNo=str_addenquirys.get(s).getMobileNo();
            EmailId=str_addenquirys.get(s).getEmailId();
            BusinessName=str_addenquirys.get(s).getBusinessName();
            stateId=str_addenquirys.get(s).getStateId();
            districtId=str_addenquirys.get(s).getDistrictId();
            talukId=str_addenquirys.get(s).getTalukaI();
            villegeId=str_addenquirys.get(s).getVillageId();
            sectorId=str_addenquirys.get(s).getSectorId();
            deviceType=str_addenquirys.get(s).getDeviceType();
            userId=str_addenquirys.get(s).getUserId();
            EnquiryId=str_addenquirys.get(s).getEnquiryId();
            Gender = str_addenquirys.get(s).getGender();

            dataSyncStatus="offline";
          //  Log.e("tag","tempid addenqury=="+TempId);
            /*String SQLiteQuery = "INSERT INTO AddEnquiryDetails (FName, MName,LName,MobileNo,EmailId,StateId,DistrictId,TalukaId,VillageId,SectorId,BusinessName,DeviceType,UserId,EnquiryId,Gender)" +
                    " VALUES ('" + FName + "','" + MName + "','" + LName + "','" + MobileNo + "','" + EmailId + "','" + stateId + "','" + districtId +"','" + talukId + "','" + villegeId +"','" + sectorId +"','" + BusinessName +"','" + deviceType +"','" + userId +"','" + EnquiryId +"','" + Gender +"');";
            db_addEnquiry.execSQL(SQLiteQuery);*/

            ContentValues contentValues = new ContentValues();
            contentValues.put("FName", FName);
            contentValues.put("MName", MName);
            contentValues.put("LName", LName);
            contentValues.put("MobileNo", MobileNo);
            contentValues.put("EmailId", EmailId);
            contentValues.put("StateId", stateId);
            contentValues.put("DistrictId", districtId);
            contentValues.put("TalukaId", talukId);
            contentValues.put("VillageId", villegeId);
            contentValues.put("SectorId", sectorId);
            contentValues.put("BusinessName", BusinessName);
            contentValues.put("DeviceType", deviceType);
            contentValues.put("UserId", userId);
            contentValues.put("EnquiryId", EnquiryId);
            contentValues.put("Gender", Gender);
            contentValues.put("dataSyncStatus", dataSyncStatus);
            long result=db_addEnquiry.insert("AddEnquiryDetails",null,contentValues);
            // Log.e("tag","SQLiteQuery insert=="+SQLiteQuery.toString());
            Log.e("tag","SQLiteQuery insert result="+result);

            if(result>0){
                Toast toast= Toast.makeText(getApplicationContext(), "  Enquiry inserted offline Successfully " , Toast.LENGTH_LONG);
                // Toast toast=   Toast.makeText(YOUR ACTIVITY NAME ,Toast.LENGTH_SHORT);
                View view =toast.getView();
                view.setBackgroundColor( Color.parseColor("#009900")); //any color your want
                toast.show();
            }
            else{
                Toast toast= Toast.makeText(getApplicationContext(), "  Enquiry insertion Failed  " , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                toast.show();
            }
        }

        db_addEnquiry.close();
    }
    public void DBCreate_ListEnquiryDetails_2SQLiteDB(ArrayList<Class_AddEnquiryDetailsDetails> str_listenquirys) {
        SQLiteDatabase db_addEnquiry = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_addEnquiry.execSQL("CREATE TABLE IF NOT EXISTS ListEnquiryDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,FName VARCHAR,MobileNo VARCHAR,EmailId VARCHAR,BusinessName VARCHAR,EnquiryId VARCHAR,Status VARCHAR,YearCode VARCHAR,MName VARCHAR,LName VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,isApplicant VARCHAR,Gender VARCHAR,dataSyncStatus VARCHAR );");

        String FName,YearCode,LName,MName,MobileNo,EmailId,BusinessName,Status,gender,isApplicant,stateId,districtId,talukId,villegeId,sectorId,deviceType,userId,EnquiryId;

        for(int s=0;s<str_listenquirys.size();s++) {
            Log.e("tag","BusinessName="+str_listenquirys.get(s).getBusinessName()+"Fname="+str_listenquirys.get(s).getFName());

            FName=str_listenquirys.get(s).getFName();
            MobileNo=str_listenquirys.get(s).getMobileNo();
            EmailId=str_listenquirys.get(s).getEmailId();
            BusinessName=str_listenquirys.get(s).getBusinessName();
            EnquiryId=str_listenquirys.get(s).getEnquiryId();
           // Status = str_listenquirys.get(s).gets();
           // YearCode = str_listenquirys.get(s).get;
            MName=str_listenquirys.get(s).getMName();
            LName=str_listenquirys.get(s).getLName();
            stateId=str_listenquirys.get(s).getStateId();
            districtId=str_listenquirys.get(s).getDistrictId();
            talukId=str_listenquirys.get(s).getTalukaI();
            villegeId=str_listenquirys.get(s).getVillageId();
            sectorId=str_listenquirys.get(s).getSectorId();
            gender=str_listenquirys.get(s).getGender();
            dataSyncStatus="offline";
            isApplicant="0";


            Log.e("tag","EnquiryId Id listenquiry=="+EnquiryId);
        /*    String SQLiteQuery = "INSERT INTO ListEnquiryDetails (FName,MobileNo,EmailId,BusinessName,EnquiryId,Status,YearCode,MName,LName,StateId,DistrictId,TalukaId,VillageId,SectorId,isApplicant,Gender,dataSyncStatus)" +
                    " VALUES ('" + FName + "','" + MobileNo + "','" + EmailId +"','" + BusinessName +"','" + EnquiryId + "','" + "Success" +"','" + "0" +"','" + MName +"','" + LName +"','" + stateId +"','" + districtId +"','" + talukId +"','" + villegeId +"','" + sectorId +"','" + isApplicant +"','" + gender +"','"+dataSyncStatus+"');";
            db_addEnquiry.execSQL(SQLiteQuery);*/
            dataSyncStatus="offline";
            //  Log.e("tag","tempid addenqury=="+TempId);
            /*String SQLiteQuery = "INSERT INTO AddEnquiryDetails (FName, MName,LName,MobileNo,EmailId,StateId,DistrictId,TalukaId,VillageId,SectorId,BusinessName,DeviceType,UserId,EnquiryId,Gender)" +
                    " VALUES ('" + FName + "','" + MName + "','" + LName + "','" + MobileNo + "','" + EmailId + "','" + stateId + "','" + districtId +"','" + talukId + "','" + villegeId +"','" + sectorId +"','" + BusinessName +"','" + deviceType +"','" + userId +"','" + EnquiryId +"','" + Gender +"');";
            db_addEnquiry.execSQL(SQLiteQuery);*/

            ContentValues contentValues = new ContentValues();
            contentValues.put("FName", FName);
            contentValues.put("MName", MName);
            contentValues.put("LName", LName);
            contentValues.put("MobileNo", MobileNo);
            contentValues.put("EmailId", EmailId);
            contentValues.put("StateId", stateId);
            contentValues.put("DistrictId", districtId);
            contentValues.put("TalukaId", talukId);
            contentValues.put("VillageId", villegeId);
            contentValues.put("SectorId", sectorId);
            contentValues.put("BusinessName", BusinessName);
            contentValues.put("YearCode", "0");
            contentValues.put("Status", "Success");
        //    contentValues.put("DeviceType", deviceType);
        //    contentValues.put("UserId", userId);
            contentValues.put("EnquiryId", EnquiryId);
            contentValues.put("Gender", gender);
            contentValues.put("dataSyncStatus", dataSyncStatus);
            long result=db_addEnquiry.insert("ListEnquiryDetails",null,contentValues);
            // Log.e("tag","SQLiteQuery insert=="+SQLiteQuery.toString());
            Log.e("tag","SQLiteQuery insert result="+result);

            if(result>0){
                Toast toast= Toast.makeText(getApplicationContext(), "  Enquiry inserted offline Successfully " , Toast.LENGTH_LONG);
                // Toast toast=   Toast.makeText(YOUR ACTIVITY NAME ,Toast.LENGTH_SHORT);
                View view =toast.getView();
                view.setBackgroundColor( Color.parseColor("#009900")); //any color your want
                toast.show();
            }
            else{
                Toast toast= Toast.makeText(getApplicationContext(), "  Enquiry insertion Failed  " , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                toast.show();
            }
        }

        db_addEnquiry.close();
    }

  /*  public static void Update_offlineAddEnquiryDetails() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS AddEnquiryDetails(FName VARCHAR,MName VARCHAR,LName VARCHAR,MobileNo VARCHAR,EmailId VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,BusinessName VARCHAR,DeviceType VARCHAR,UserId VARCHAR,EnquiryId VARCHAR,Gender VARCHAR);");
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

                AddEnquiryDetails addEnquiryDetails = new AddEnquiryDetails(AddEnquiryActivity.this);
                addEnquiryDetails.execute(FName,MName,LName,MobileNo,EmailId,BusinessName,sp_strstate_ID,sp_strdistrict_ID,sp_strTaluk_ID,sp_strVillage_ID,sp_strsector_ID,str_UserId,str_Gender);

                i++;
            } while (cursor1.moveToNext());
        }//if ends

        if (x > 0) {
            db1.delete("AddEnquiryDetails", null, null);

        }

        db1.close();

    }
*/
   /* public void deleteAddEnquiryDetails_AfterInsertion() {

        SQLiteDatabase db_addEnquiry_delete = openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_addEnquiry_delete.execSQL("CREATE TABLE IF NOT EXISTS AddEnquiryDetails(FName VARCHAR,MName VARCHAR,LName VARCHAR,MobileNo VARCHAR,EmailId VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,BusinessName VARCHAR,DeviceType VARCHAR,UserId VARCHAR,EnquiryId VARCHAR);");
        Cursor cursor1 = db_addEnquiry_delete.rawQuery("SELECT * FROM AddEnquiryDetails", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db_addEnquiry_delete.delete("AddEnquiryDetails", null, null);

        }
        db_addEnquiry_delete.close();
    }*/

   /* private void GetDropdownValues()
    {
        final ProgressDialog dialog = new ProgressDialog(AddEnquiryActivity.this);


        //getting the progressbar
        // final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //making the progressbar visible
        //  progressBar.setVisibility(View.VISIBLE);
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        String str_JSON_LocationURL = Class_URL.JSON_LocationURL.toString().trim();

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.POST, str_JSON_LocationURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        // progressBar.setVisibility(View.INVISIBLE);
//                        dialog.dismiss();

                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray stateArray = obj.getJSONArray("States");
                            // Log.e("state", String.valueOf(stateArray));

                            JSONArray districtArray = obj.getJSONArray("District");
                            JSONArray talukArray = obj.getJSONArray("Taluks");
                            JSONArray villageArray = obj.getJSONArray("Village");
                            JSONArray panchayatArray = obj.getJSONArray("Panchayat");
                            JSONArray yearArray = obj.getJSONArray("Year");
                            // Log.e("talukArray",Arrays.toString(new JSONArray[]{talukArray}));
                            // Log.e("stateArray",Arrays.toString(new JSONArray[]{stateArray}));
                            //Log.e("districtArray",Arrays.toString(new JSONArray[]{districtArray}));
                            // Log.e("villageArray",Arrays.toString(new JSONArray[]{villageArray}));
                            // Log.e("panchayatArray",Arrays.toString(new JSONArray[]{panchayatArray}));
                            // Log.e("yearArray",Arrays.toString(new JSONArray[]{yearArray}));

                            //Log.e("villageArray", String.valueOf(villageArray.length()));
                            //now looping through all the elements of the json array,29811
                            strArray_statename = new String[stateArray.length()];
                            strArray_stateid = new String[stateArray.length()];
                            strArray_districtname = new String[districtArray.length()];
                            strArray_districtid = new String[districtArray.length()];
                            strArray_Dist_stateid = new String[districtArray.length()];
                            strArray_selecteddistname = new String[districtArray.length()];
                            strArray_talukid = new String[talukArray.length()];
                            strArray_talukname = new String[talukArray.length()];
                            strArray_taluk_distid = new String[talukArray.length()];
                            strArray_villageid = new String[villageArray.length()];
                            strArray_village_talukid = new String[villageArray.length()];
                            strArray_villagename = new String[villageArray.length()];
                            strArray_panchayatid = new String[panchayatArray.length()];
                            strArray_panchayatname = new String[panchayatArray.length()];
                            strArray_panchayat_distid = new String[panchayatArray.length()];
                            StrArray_year = new String[yearArray.length()];
                            StrArray_year_ID = new String[yearArray.length()];

                            arrayObj_Class_stateDetails2 = new Class_StateListDetails[stateArray.length()];
                            arrayObj_Class_DistrictListDetails2 = new Class_DistrictListDetails[districtArray.length()];
                            arrayObj_Class_TalukListDetails2 = new Class_TalukListDetails[talukArray.length()];
                            arrayObj_Class_VillageListDetails2 = new Class_VillageListDetails[villageArray.length()];
                            arrayObj_Class_GrampanchayatListDetails2 = new Class_GrampanchayatListDetails[panchayatArray.length()];
                            arrayObj_Class_yearDetails2 = new Class_YearListDetails[yearArray.length()];


                            for (int i = 0; i < yearArray.length(); i++) {

                                //getting the json object of the particular index inside the array
                                JSONObject yearObject = yearArray.getJSONObject(i);

                                //creating a hero object and giving them the values from json object

                                Class_YearListDetails class_yearListDetails = new Class_YearListDetails(yearObject.getString("year_id"), yearObject.getString("year"));
                                StrArray_year[i] = yearObject.getString("year");
                                StrArray_year_ID[i] = yearObject.getString("year_id");
                                class_yearListDetails.setYear(StrArray_year[i]);
                                class_yearListDetails.setYearID(StrArray_year_ID[i]);
                                arrayObj_Class_yearDetails2[i] = class_yearListDetails;
                                // Log.e("StrArray_year",StrArray_year[i]);

                                yearList.add(class_yearListDetails);

                                DBCreate_Yeardetails_insert_2SQLiteDB(StrArray_year_ID[i], StrArray_year[i]);

                            }

                            for (int i = 0; i < stateArray.length(); i++) {

                                //getting the json object of the particular index inside the array
                                JSONObject StateObject = stateArray.getJSONObject(i);
                                //JSONObject yearObject = yearArray.getJSONObject(i);
                                //creating a hero object and giving them the values from json object
                                Class_StateListDetails class_stateListDetails = new Class_StateListDetails(StateObject.getString("state_id"), StateObject.getString("state_name"));
                                strArray_statename[i] = StateObject.getString("state_name");
                                strArray_stateid[i] = StateObject.getString("state_id");

                                //StrArray_year_ID[i] = yearObject.getString("year_id");

                                class_stateListDetails.setState_name(strArray_statename[i]);
                                class_stateListDetails.setState_id(strArray_stateid[i]);
                                //class_stateListDetails.setYear_id(StrArray_year_ID[i]);
                                arrayObj_Class_stateDetails2[i] = class_stateListDetails;
                                //adding the hero to herolist
                                // Log.e("strArray_statename..",strArray_statename[i]);
                                stateList.add(class_stateListDetails);
                                DBCreate_Statedetails_insert_2SQLiteDB(strArray_stateid[i], strArray_statename[i], strArray_stateid[i]);

                            }
                            for (int i = 0; i < districtArray.length(); i++) {

                                //getting the json object of the particular index inside the array
                                JSONObject districtObject = districtArray.getJSONObject(i);

                                //creating a hero object and giving them the values from json object
                                Class_DistrictListDetails class_districtListDetails = new Class_DistrictListDetails(districtObject.getString("district_id"), districtObject.getString("district_name"), districtObject.getString("st_id"));
                                strArray_districtname[i] = districtObject.getString("district_name");
                                strArray_districtid[i] = districtObject.getString("district_id");
                                strArray_Dist_stateid[i] = districtObject.getString("st_id");
                                class_districtListDetails.setDistrict_id(strArray_districtid[i]);
                                class_districtListDetails.setDistrict_name(strArray_districtname[i]);
                                class_districtListDetails.setState_id(strArray_Dist_stateid[i]);
                                arrayObj_Class_DistrictListDetails2[i] = class_districtListDetails;

                                //adding the hero to herolist
                                // Log.e("strArray_districtname..",strArray_districtname[i]);
                                districtList.add(class_districtListDetails);

                                DBCreate_Districtdetails_insert_2SQLiteDB(strArray_districtid[i], strArray_districtname[i], "Y1", strArray_Dist_stateid[i]);

                            }

                            for (int i = 0; i < talukArray.length(); i++) {

                                //getting the json object of the particular index inside the array
                                JSONObject talukObject = talukArray.getJSONObject(i);

                                //creating a hero object and giving them the values from json object
                                Class_TalukListDetails class_talukListDetails = new Class_TalukListDetails(talukObject.getString("taluka_id"), talukObject.getString("taluk_name"), talukObject.getString("district_id"));
                                strArray_talukname[i] = talukObject.getString("taluk_name");
                                strArray_talukid[i] = talukObject.getString("taluka_id");//taluka_id,taluk_id
                                strArray_taluk_distid[i] = talukObject.getString("district_id");//district_id,dist_id
                                arrayObj_Class_TalukListDetails2[i] = class_talukListDetails;

                                //adding the hero to herolist
                                //Log.e("strArray_talukid..",strArray_talukid[i]);
                                //Log.e("strArray_taluk_distid..",strArray_taluk_distid[i]);
                                //Log.e("strArray_talukid..",strArray_talukid[i]);
                                talukList.add(class_talukListDetails);

                                DBCreate_Talukdetails_insert_2SQLiteDB(strArray_talukid[i], strArray_talukname[i], strArray_taluk_distid[i]);

                            }

                            //  for (int i = 0; i < 70; i++) {
                            for (int i = 0; i < villageArray.length(); i++) {

                                //getting the json object of the particular index inside the array
                                JSONObject villageObject = villageArray.getJSONObject(i);

                                //creating a hero object and giving them the values from json object
                                Class_VillageListDetails class_villageListDetails = new Class_VillageListDetails(villageObject.getString("village_id"), villageObject.getString("village_name"), villageObject.getString("taluk_id"));
                                strArray_villagename[i] = villageObject.getString("village_name");
                                strArray_villageid[i] = villageObject.getString("village_id");
                                strArray_village_talukid[i] = villageObject.getString("taluk_id");
                                arrayObj_Class_VillageListDetails2[i] = class_villageListDetails;

                                //adding the hero to herolist
                                //  Log.e("str_villae_talukid..",strArray_village_talukid[i]);
                                villageList.add(class_villageListDetails);
                                DBCreate_Villagedetails_insert_2SQLiteDB(strArray_villageid[i], strArray_villagename[i], strArray_village_talukid[i]);


                            }

                            for (int i = 0; i < panchayatArray.length(); i++) {

                                //getting the json object of the particular index inside the array
                                JSONObject panchayatObject = panchayatArray.getJSONObject(i);

                                //creating a hero object and giving them the values from json object
                                Class_GrampanchayatListDetails class_grampanchayatListDetails = new Class_GrampanchayatListDetails(panchayatObject.getString("panchayat_id"), panchayatObject.getString("panchayat_name"), panchayatObject.getString("district_id"));
                                strArray_panchayatname[i] = panchayatObject.getString("panchayat_name");
                                strArray_panchayatid[i] = panchayatObject.getString("panchayat_id");
                                strArray_panchayat_distid[i] = panchayatObject.getString("district_id");
                                arrayObj_Class_GrampanchayatListDetails2[i] = class_grampanchayatListDetails;

                                //adding the hero to herolist
                                // Log.e("strArrhayatname",strArray_panchayatname[i]);
                                panchayatList.add(class_grampanchayatListDetails);

                                DBCreate_Grampanchayatdetails_insert_2SQLiteDB(strArray_panchayatid[i], strArray_panchayatname[i], strArray_panchayat_distid[i]);

                            }

                            uploadfromDB_Yearlist();
                            uploadfromDB_Statelist();
                            uploadfromDB_Districtlist();
                            uploadfromDB_Taluklist();
                            uploadfromDB_Villagelist();
                            uploadfromDB_Grampanchayatlist();

//                            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_stateDetails2);
//                            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
//                            statelist_SP.setAdapter(dataAdapter);
//                            ArrayAdapter dataAdapter_dist = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_DistrictListDetails2);
//                            dataAdapter_dist.setDropDownViewResource(R.layout.spinnercenterstyle);
//                            districtlist_SP.setAdapter(dataAdapter_dist);
//                            ArrayAdapter dataAdapter_taluk= new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_TalukListDetails2);
//                            dataAdapter_taluk.setDropDownViewResource(R.layout.spinnercenterstyle);
//                            taluklist_SP.setAdapter(dataAdapter_taluk);
//                            ArrayAdapter dataAdapter_village= new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_VillageListDetails2);
//                            dataAdapter_village.setDropDownViewResource(R.layout.spinnercenterstyle);
//                            villagelist_SP.setAdapter(dataAdapter_village);
//                            ArrayAdapter dataAdapter_panchayat= new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_GrampanchayatListDetails2);
//                            dataAdapter_panchayat.setDropDownViewResource(R.layout.spinnercenterstyle);
//                            grampanchayatlist_SP.setAdapter(dataAdapter_panchayat);


//                            for(int i=0;i<strArray_Dist_stateid.length;i++) {
//                                if (sp_strstate_ID.equals(strArray_Dist_stateid[i])) {
//
//                                    strArray_selecteddistname[i]=strArray_districtname[i];
////                                    ArrayAdapter dataAdapter_dist = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, strArray_districtname);
////                                    dataAdapter_dist.setDropDownViewResource(R.layout.spinnercenterstyle);
////                                    districtlist_SP.setAdapter(dataAdapter_dist);
//                                }
//
//                                   ArrayAdapter dataAdapter_dist = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, strArray_selecteddistname);
//                                   dataAdapter_dist.setDropDownViewResource(R.layout.spinnercenterstyle);
//                                   districtlist_SP.setAdapter(dataAdapter_dist);
//
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                            Log.e("errormsg_catch", e.getMessage());
                        }

                        dialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        // error.printStackTrace();
                        Log.e("errormsg", error.getMessage().toString());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }*/

    public void onRadioButtonGenderClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.rdb_male:
                if (checked) {
                    str_gender="M";
                }
                break;
            case R.id.rdb_female:
                if (checked) {
                    str_gender="F";
                }
                break;
        }
    }


    public class GetMasterDetails extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;
        ProgressDialog progressDialog;

        //private ProgressBar progressBar;

        GetMasterDetails (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];
            String METHOD_NAME = "Sync_Village_SyncSlno";
            String SOAP_ACTION1 = "http://mis.navodyami.org/Sync_Village_SyncSlno";
            String NAMESPACE = "http://mis.navodyami.org/";

            try{

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                // String collgId="302",AcadId="5";

                Log.e("tag","req SyncSlno=="+SyncSlno);
                request.addProperty("Sync_Slno",SyncSlno);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

                envelope.dotNet = true;
                //Set output SOAP object
                Log.e("tag","request Master data="+request);
                envelope.setOutputSoapObject(request);
                //Create HTTP call object
                //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
                HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Login.toString().trim());
                //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

                try {
                    androidHttpTransport.call(SOAP_ACTION1, envelope);
                    SoapObject response = (SoapObject) envelope.getResponse();
                    Log.d("Master list:", response.toString());

                    return response;

                   // }
                //    else {
                       // Toast.makeText(AddEnquiryActivity.this, "There are no syncing data", Toast.LENGTH_LONG).show();
                  //  }
                        //   adapter_collg.notifyDataSetChanged();

               //     Log.e("tag","collegeList="+collegeList);

                    //return response;

                }

                catch(OutOfMemoryError ex){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(AddEnquiryActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                catch (Exception t) {
                    Log.e("request fail", "> " + t.getMessage().toString());

                    t.printStackTrace();
                    final String exceptionStr = t.getMessage().toString();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(AddEnquiryActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            catch(OutOfMemoryError ex){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(AddEnquiryActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }

            catch (Exception t) {
                Log.d("exception outside",t.getMessage().toString());

                t.printStackTrace();
                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(AddEnquiryActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;


            //Log.d("Soap response is",response.toString());

            //return response;
        }

        @Override
        protected void onPreExecute() {
           /* progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/

            progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(SoapObject result) {
            /* progressBar.setVisibility(View.GONE);*/
            progressDialog.dismiss();

            SoapObject soapObj_Village1 = (SoapObject) result.getProperty("Village");


            SoapObject soapObj_VillageList = (SoapObject) soapObj_Village1.getProperty(0);

            Log.e("tag", "VillageStatus=" + soapObj_VillageList.getProperty("VillageStatus"));

            VillageStatus= soapObj_VillageList.getProperty("VillageStatus");
            Log.e("tag", "VillageStatus=" + VillageStatus);

            if (soapObj_VillageList.getProperty("VillageStatus").toString().equals("Success")) {

                  //  if(soapObj_VillageList.getProperty("VillageStatus").equals("Success")){
                  SoapObject soapObj = (SoapObject) result.getProperty("State");

                  if (!soapObj.toString().equals("anyType{}") && !soapObj.toString().equals(null)) {

                      SoapPrimitive S_State_Id, S_StateName, S_StateStatus;
                      Object O_State_Id, O_StateName, O_StateStatus;
                      String str_State_Id = null, str_StateName = null, str_StateStatus = null;
                      //count2 = soapObj.getPropertyCount();
                      //  collegeList.clear();
                      Log.e("tag", "soapObj.getPropertyCount()==" + soapObj.getPropertyCount());
                      // Log.e("tag","soapObj.getPropertyCount()-1=="+soapObj.getPropertyCount()-1);

                      for (int count1 = 0; count1 < soapObj.getPropertyCount(); count1++) {
                          SoapObject ProjectList = (SoapObject) soapObj.getProperty(count1);
                          Log.d("statelist", ProjectList.toString());
                          Log.d("statelist count", String.valueOf(soapObj.getPropertyCount()));


                          //   if (count1 != 0) {
                          O_State_Id = ProjectList.getProperty("State_Id");
                          Log.e("tag", "State_Id==" + O_State_Id);
                          if (!O_State_Id.toString().equals("anyType{}") && !O_State_Id.toString().equals(null)) {
                              S_State_Id = (SoapPrimitive) ProjectList.getProperty("State_Id");
                              str_State_Id = S_State_Id.toString();
                              Log.e("tag", "str_State_Id==" + str_State_Id);
                          }
                          O_StateName = ProjectList.getProperty("StateName");
                          Log.e("tag", "StateName==" + O_StateName);
                          if (!O_StateName.toString().equals("anyType{}") && !O_StateName.toString().equals(null)) {
                              S_StateName = (SoapPrimitive) ProjectList.getProperty("StateName");
                              str_StateName = S_StateName.toString();
                              Log.e("tag", "str_StateName==" + str_StateName);
                          }
                          O_StateStatus = ProjectList.getProperty("StateStatus");
                          Log.e("tag", "StateStatus==" + O_StateStatus);
                          if (!O_StateStatus.toString().equals("anyType{}") && !O_StateStatus.toString().equals(null)) {
                              S_StateStatus = (SoapPrimitive) ProjectList.getProperty("StateStatus");
                              str_StateStatus = S_StateStatus.toString();
                              Log.e("tag", "str_StateStatus==" + str_StateStatus);
                          }

                          Class_StateListDetails class_stateListDetails = new Class_StateListDetails(str_State_Id, str_StateName, str_StateStatus);
                          stateList.add(class_stateListDetails);
                          DBCreate_Statedetails_insert_2SQLiteDB(str_State_Id, str_StateName, str_StateStatus);
                          //  }
                      }
                  }

                  SoapObject soapObj_district = (SoapObject) result.getProperty("District");

                  if (!soapObj_district.toString().equals("anyType{}") && !soapObj_district.toString().equals(null)) {

                      SoapPrimitive S_DistrictId, S_DistrictName, S_Stateid;
                      Object O_DistrictId, O_DistrictName, O_Stateid;
                      String str_DistrictId = null, str_DistrictName = null, str_Stateid = null;
                      //count2 = soapObj.getPropertyCount();
                      //  collegeList.clear();
                      Log.e("tag", "soapObj_district.getPropertyCount()==" + soapObj_district.getPropertyCount());
                      // Log.e("tag","soapObj.getPropertyCount()-1=="+soapObj.getPropertyCount()-1);

                      for (int count1 = 0; count1 < soapObj_district.getPropertyCount(); count1++) {
                          SoapObject DistrictList = (SoapObject) soapObj_district.getProperty(count1);
                          Log.d("DistrictList", DistrictList.toString());
                          Log.d("DistrictList count", String.valueOf(soapObj_district.getPropertyCount()));


                          //    if (count1 != 0) {
                          O_DistrictId = DistrictList.getProperty("DistrictId");
                          Log.e("tag", "DistrictId==" + O_DistrictId);
                          if (!O_DistrictId.toString().equals("anyType{}") && !O_DistrictId.toString().equals(null)) {
                              S_DistrictId = (SoapPrimitive) DistrictList.getProperty("DistrictId");
                              str_DistrictId = S_DistrictId.toString();
                              Log.e("tag", "str_DistrictId==" + str_DistrictId);
                          }
                          O_DistrictName = DistrictList.getProperty("DistrictName");
                          Log.e("tag", "DistrictName==" + O_DistrictName);
                          if (!O_DistrictName.toString().equals("anyType{}") && !O_DistrictName.toString().equals(null)) {
                              S_DistrictName = (SoapPrimitive) DistrictList.getProperty("DistrictName");
                              str_DistrictName = S_DistrictName.toString();
                              Log.e("tag", "str_DistrictName==" + str_DistrictName);
                          }
                          O_Stateid = DistrictList.getProperty("Stateid");
                          Log.e("tag", "Stateid==" + O_Stateid);
                          if (!O_Stateid.toString().equals("anyType{}") && !O_Stateid.toString().equals(null)) {
                              S_Stateid = (SoapPrimitive) DistrictList.getProperty("Stateid");
                              str_Stateid = S_Stateid.toString();
                              Log.e("tag", "str_Stateid==" + str_Stateid);
                          }

                          Class_DistrictListDetails class_districtListDetails = new Class_DistrictListDetails(str_DistrictId, str_DistrictName, str_Stateid);
                          districtList.add(class_districtListDetails);
                          DBCreate_Districtdetails_insert_2SQLiteDB(str_DistrictId, str_DistrictName, str_Stateid);
                          //   }
                      }
                  }
                  SoapObject soapObj_Taluka = (SoapObject) result.getProperty("Taluka");

                  if (!soapObj_Taluka.toString().equals("anyType{}") && !soapObj_Taluka.toString().equals(null)) {

                      SoapPrimitive S_Taluka_Id, S_Taluk_Name, S_District_Id;
                      Object O_Taluka_Id, O_Taluk_Name, O_District_Id;
                      String str_Taluka_Id = null, str_Taluk_Name = null, str_District_Id = null;
                      //count2 = soapObj.getPropertyCount();
                      //  collegeList.clear();
                      Log.e("tag", "soapObj_Taluka.getPropertyCount()==" + soapObj_Taluka.getPropertyCount());
                      // Log.e("tag","soapObj.getPropertyCount()-1=="+soapObj.getPropertyCount()-1);

                      for (int count1 = 0; count1 < soapObj_Taluka.getPropertyCount(); count1++) {
                          SoapObject TalukaList = (SoapObject) soapObj_Taluka.getProperty(count1);
                          Log.d("TalukaList", TalukaList.toString());
                          Log.d("TalukaList count", String.valueOf(soapObj_Taluka.getPropertyCount()));


                          //    if (count1 != 0) {
                          O_Taluka_Id = TalukaList.getProperty("Taluka_Id");
                          Log.e("tag", "Taluka_Id==" + O_Taluka_Id);
                          if (!O_Taluka_Id.toString().equals("anyType{}") && !O_Taluka_Id.toString().equals(null)) {
                              S_Taluka_Id = (SoapPrimitive) TalukaList.getProperty("Taluka_Id");
                              str_Taluka_Id = S_Taluka_Id.toString();
                              Log.e("tag", "str_Taluka_Id==" + str_Taluka_Id);
                          }
                          O_Taluk_Name = TalukaList.getProperty("Taluk_Name");
                          Log.e("tag", "Taluk_Name==" + O_Taluk_Name);
                          if (!O_Taluk_Name.toString().equals("anyType{}") && !O_Taluk_Name.toString().equals(null)) {
                              S_Taluk_Name = (SoapPrimitive) TalukaList.getProperty("Taluk_Name");
                              str_Taluk_Name = S_Taluk_Name.toString();
                              Log.e("tag", "str_Taluk_Name==" + str_Taluk_Name);
                          }
                          O_District_Id = TalukaList.getProperty("District_Id");
                          Log.e("tag", "District_Id==" + O_District_Id);
                          if (!O_District_Id.toString().equals("anyType{}") && !O_District_Id.toString().equals(null)) {
                              S_District_Id = (SoapPrimitive) TalukaList.getProperty("District_Id");
                              str_District_Id = S_District_Id.toString();
                              Log.e("tag", "str_District_Id==" + str_District_Id);
                          }

                          Class_TalukListDetails class_talukListDetails = new Class_TalukListDetails(str_Taluka_Id, str_Taluk_Name, str_District_Id);
                          talukList.add(class_talukListDetails);
                          DBCreate_Talukdetails_insert_2SQLiteDB(str_Taluka_Id, str_Taluk_Name, str_District_Id);
                          //    }
                      }
                  }

                  SoapObject soapObj_Village = (SoapObject) result.getProperty("Village");
                  if (!soapObj_Village.toString().equals("anyType{}") && !soapObj_Village.toString().equals(null)) {

                      SoapPrimitive S_Village_Id, S_Village_Name, S_Taluka_Id, S_SyncSlno;
                      Object O_Village_Id, O_Village_Name, O_Taluka_Id, O_SyncSlno;
                      String str_Village_Id = null, str_Village_Name = null, str_Taluka_Id = null, str_SyncSlno = null;
                      //count2 = soapObj.getPropertyCount();
                      //  collegeList.clear();
                      Log.e("tag", "soapObj_Village.getPropertyCount()==" + soapObj_Village.getPropertyCount());
                      // Log.e("tag","soapObj.getPropertyCount()-1=="+soapObj.getPropertyCount()-1);

                      for (int count1 = 0; count1 < soapObj_Village.getPropertyCount(); count1++) {
                          SoapObject VillageList = (SoapObject) soapObj_Village.getProperty(count1);
                          Log.d("VillageList", VillageList.toString());
                          Log.d("VillageList count", String.valueOf(soapObj_Village.getPropertyCount()));


                          //   if (count1 != 0) {
                          O_Village_Id = VillageList.getProperty("Village_Id");
                          Log.e("tag", "Village_Id==" + O_Village_Id);
                          if (!O_Village_Id.toString().equals("anyType{}") && !O_Village_Id.toString().equals(null)) {
                              S_Village_Id = (SoapPrimitive) VillageList.getProperty("Village_Id");
                              str_Village_Id = S_Village_Id.toString();
                              Log.e("tag", "str_Village_Id==" + str_Village_Id);
                          }
                          O_Village_Name = VillageList.getProperty("Village_Name");
                          Log.e("tag", "Village_Name==" + O_Village_Name);
                          if (!O_Village_Name.toString().equals("anyType{}") && !O_Village_Name.toString().equals(null)) {
                              S_Village_Name = (SoapPrimitive) VillageList.getProperty("Village_Name");
                              str_Village_Name = S_Village_Name.toString();
                              Log.e("tag", "str_Village_Name==" + str_Village_Name);
                          }
                          O_Taluka_Id = VillageList.getProperty("Taluka_Id");
                          Log.e("tag", "Taluka_Id==" + O_Taluka_Id);
                          if (!O_Taluka_Id.toString().equals("anyType{}") && !O_Taluka_Id.toString().equals(null)) {
                              S_Taluka_Id = (SoapPrimitive) VillageList.getProperty("Taluka_Id");
                              str_Taluka_Id = S_Taluka_Id.toString();
                              Log.e("tag", "str_Taluka_Id==" + str_Taluka_Id);
                          }
                          O_SyncSlno = VillageList.getProperty("SyncSlno");
                          Log.e("tag", "SyncSlno==" + O_SyncSlno);
                          if (!O_SyncSlno.toString().equals("anyType{}") && !O_SyncSlno.toString().equals(null)) {
                              S_SyncSlno = (SoapPrimitive) VillageList.getProperty("SyncSlno");
                              str_SyncSlno = S_SyncSlno.toString();
                              Log.e("tag", "str_SyncSlno==" + str_SyncSlno);
                          }

                          Class_VillageListDetails class_villageListDetails = new Class_VillageListDetails(str_Village_Id, str_Village_Name, str_Taluka_Id, str_SyncSlno);
                          villageList.add(class_villageListDetails);
                          DBCreate_Villagedetails_insert_2SQLiteDB(str_Village_Id, str_Village_Name, str_Taluka_Id, str_SyncSlno);
                          //    }
                      }
                  }
              }else{
                  Toast.makeText(AddEnquiryActivity.this, "There are no syncing data", Toast.LENGTH_LONG).show();
              }
        /*    if (VillageStatus.equals("Success")) {
               // if(result!=null) {
                    String finalResult = result.toString();
                    uploadfromDB_Statelist();
                    uploadfromDB_Districtlist();
                    uploadfromDB_Taluklist();
                    uploadfromDB_Villagelist();
               // }

            }
            else {
                Toast.makeText(AddEnquiryActivity.this, "There are no syncing data", Toast.LENGTH_LONG).show();
            }*/
            uploadfromDB_Statelist();
            uploadfromDB_Districtlist();
            uploadfromDB_Taluklist();
            uploadfromDB_Villagelist();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


    public class GetSectorDetails extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;
        ProgressDialog progressDialog;

      //  private ProgressBar progressBar;

        GetSectorDetails (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];
            String METHOD_NAME = "GetSector";
            String SOAP_ACTION1 = "http://mis.navodyami.org/GetSector";
            String NAMESPACE = "http://mis.navodyami.org/";

            try{

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
                    Log.d("GetThemeList:",response.toString());

                    return response;

                }

                catch(OutOfMemoryError ex){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(AddEnquiryActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                catch (Exception t) {
                    Log.e("request fail", "> " + t.getMessage().toString());

                    t.printStackTrace();
                    final String exceptionStr = t.getMessage().toString();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(AddEnquiryActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            catch(OutOfMemoryError ex){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(AddEnquiryActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }

            catch (Exception t) {
                Log.d("exception outside",t.getMessage().toString());

                t.printStackTrace();
                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(AddEnquiryActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;


            //Log.d("Soap response is",response.toString());

            //return response;
        }

        @Override
        protected void onPreExecute() {
           /* progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/

            progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(SoapObject result) {
            /* progressBar.setVisibility(View.GONE);*/
            progressDialog.dismiss();

            if(result!=null) {
                String finalResult = result.toString();
                String finals = finalResult.replace("anyType", "");
                Log.d("Finals is", finals);

                ArrayList<String> sectorNameList = new ArrayList<String>();
                ArrayList<String> sectorIdList = new ArrayList<String>();
               // sectorList.add("Select Sector");

                Log.d("finalResultsssss", finalResult);

                for (int i = 0; i < result.getPropertyCount(); i++) {
                    SoapObject list = (SoapObject) result.getProperty(i);
                    SoapPrimitive S_ThemeId, S_ThemeName, S_Status;
                    Object O_ThemeId, O_ThemeName, O_Status;

                    int projectTypeId = 0;

                    String str_ProjectTypeId = null, str_ProjectType = null, str_status = null;

                    O_ThemeId = list.getProperty("SectorId");
                    if (!O_ThemeId.toString().equals("anyType") && !O_ThemeId.toString().equals(null) && !O_ThemeId.toString().equals("anyType{}")) {
                        S_ThemeId = (SoapPrimitive) list.getProperty("SectorId");
                        Log.d("SectorId", S_ThemeId.toString());
                        str_ProjectTypeId = O_ThemeId.toString();
                        projectTypeId = Integer.valueOf(str_ProjectTypeId);
                        sectorIdList.add(str_ProjectTypeId);
                    }

                    O_ThemeName = list.getProperty("SectorName");
                    if (!O_ThemeName.toString().equals("anyType") && !O_ThemeName.toString().equals(null) && !O_ThemeName.toString().equals("anyType{}")) {
                        S_ThemeName = (SoapPrimitive) list.getProperty("SectorName");
                        Log.d("SectorName", S_ThemeName.toString());
                        str_ProjectType = O_ThemeName.toString();

                        sectorNameList.add(str_ProjectType);

                        hashProjTypeId.put(str_ProjectType, projectTypeId);
                    }


                }

                DBCreate_Sector_insert_2SQLiteDB(sectorIdList,sectorNameList);
                uploadfromDB_Sectorlist();
               // setSectorSpinner(sectorList);

            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private void setSectorSpinner(ArrayList<String> sectorList) {

        ArrayAdapter dataAdapter2 = new ArrayAdapter(this, R.layout.simple_spinner_items, sectorList);

        dataAdapter2.setDropDownViewResource(R.layout.spinnercustomstyle);

        // attaching data adapter to spinner
        spin_Sector.setAdapter(dataAdapter2);
        //spin_projectType.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorWhite));

    }

    public static class AddEnquiryDetails extends AsyncTask<String, Void, Void>
    {
         ProgressDialog dialog;

        Context context;

        //  public UpdateManagerDetails(GetManagerDetails getManagerDetails) {
        //  }

        @Override
        protected void onPreExecute()
        {
            Log.i("TAG", "onPreExecute---tab2");
            dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            Log.i("TAG", "onProgressUpdate---tab2");
        }

        public AddEnquiryDetails(Context activity) {
           context = activity;
            dialog = new ProgressDialog(context);
        }
        @Override
        protected Void doInBackground(String... params)
        {
            Log.i("TAG", "doInBackground");
            Submit( params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9] ,params[10],params[11],params[12]);
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            Log.i("TAG", "onPostExecute");

            Log.d("ServiceResponseisss: ",status.toString());

            dialog.dismiss();

            if(status.equalsIgnoreCase("success")) {
                Toast toast= Toast.makeText(context, "  Enquiry inserted Successfully " , Toast.LENGTH_SHORT);
               // Toast toast=   Toast.makeText(YOUR ACTIVITY NAME ,Toast.LENGTH_SHORT);
                View view =toast.getView();
                view.setBackgroundColor( Color.parseColor("#009900")); //any color your want
                toast.show();
            }
            else{
                Toast toast= Toast.makeText(context, " "+status.toString()+" " , Toast.LENGTH_SHORT);
               // Toast toast= Toast.makeText(context, "  Enquiry insertion Failed  " , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor(Color.parseColor("#DC143C")); //any color your want
                toast.show();
            }
        }
    }

    public static void Submit(String FName,String MName,String LName,String MobileNo,String EmailId,String BusinessName,String sp_strstate_ID,String sp_strdistrict_ID,String sp_strTaluk_ID,String sp_strVillage_ID,String sp_strsector_ID,String str_UserId,String Gender){
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        MenuItem action_Sync = menu.findItem(R.id.Sync);
        action_Sync.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            // Toast.makeText(CalenderActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            internetDectector = new Class_InternetDectector(getApplicationContext());
            isInternetPresent = internetDectector.isConnectingToInternet();

            if (isInternetPresent) {

                SaveSharedPreference.setUserName(getApplicationContext(),"");

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("Key_Logout", "yes");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();

            }
            else{
                Toast.makeText(getApplicationContext(),"No Internet", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        if(id== android.R.id.home) {
            //  Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(AddEnquiryActivity.this, BottomActivity.class);
            i.putExtra("frgToLoad", "0");
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(AddEnquiryActivity.this, BottomActivity.class);
        i.putExtra("frgToLoad", "0");
        startActivity(i);
        finish();

    }
}
