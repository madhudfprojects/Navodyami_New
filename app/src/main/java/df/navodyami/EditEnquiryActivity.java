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

public class EditEnquiryActivity extends AppCompatActivity {

    private EditText edt_BusinessName,edt_EmailId,edt_MobileNo,edt_LastName,edt_MiddleName,edt_FirstName;
    Button btn_update;
    String FName,LName,MName,MobileNo,EmailId,BusinessName,str_gender;

    Spinner yearlist_SP, statelist_SP, districtlist_SP, taluklist_SP, villagelist_SP,spin_Sector;
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

    String selected_year, sp_stryear_ID, sp_strstate_ID, selected_district, selected_stateName, sp_strdistrict_ID, sp_strdistrict_state_ID, sp_strTaluk_ID, selected_taluk, sp_strVillage_ID, selected_village, sp_strgrampanchayat_ID, selected_grampanchayat, sp_strsector_ID, selected_sector;
    int sel_sector=0,sel_yearsp=0,sel_statesp=0,sel_districtsp=0,sel_taluksp=0,sel_villagesp=0,sel_grampanchayatsp=0;

    ArrayList<EnquiryListModel> EditEnquiryList;

    String str_StateId,str_DistrictId,str_TalukaId,str_VillageId,str_SectorId,str_isApplicant;
    String status;
    String EnquiryId,YearCode;
    Boolean isInternetPresent = false;
    Class_InternetDectector internetDectector;

    SharedPreferences sharedpreferencebook_usercredential_Obj;
    SharedPreferences.Editor editor_obj;
    String str_UserId;
    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_employeeid = "KeyValue_employeeid";

    SharedPreferences sharedpreferencebook_OfflineEnquiryData_Obj;
    SharedPreferences.Editor editorEnquiry_obj;

    public static final String sharedpreferencebook_OfflineEnquiryData = "sharedpreferencebook_OfflineEnquiryData";
    public static final String KeyValue_EnquiryEdited = "KeyValue_EnquiryEdited";
    String str_EnquiryEdited;
    RadioGroup gender_radiogroup;
    private ProgressDialog progressDialog;

    String TempId,dataSyncStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_enquiry);

        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_n_actionbar);
        setSupportActionBar(toolbar);
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView title= (TextView) toolbar.findViewById(R.id.title_name);
        title.setText("Edit Enquiry Form");
        getSupportActionBar().setTitle("");

        EditEnquiryList = new ArrayList<>();
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
        btn_update = (Button) findViewById(R.id.btn_update);
        gender_radiogroup =(RadioGroup)findViewById(R.id. gender_radiogroup);

        sharedpreferencebook_usercredential_Obj=getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_UserId = sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeid, "").trim();

        sharedpreferencebook_OfflineEnquiryData_Obj=getSharedPreferences(sharedpreferencebook_OfflineEnquiryData, Context.MODE_PRIVATE);

        Intent intent = getIntent();
        EnquiryId=intent.getStringExtra("EnquiryId");
        YearCode = intent.getStringExtra("YearCode");
        TempId = intent.getStringExtra("TempId");

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

        if(isInternetPresent) {
            GetEnquiryDetails getEnquiryDetails = new GetEnquiryDetails(EditEnquiryActivity.this);
            getEnquiryDetails.execute();
        }else{
            List_offlineEnquiryDetails();
        }
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

                    districtlist_SP.setSelection(0);
                    taluklist_SP.setSelection(0);
                    villagelist_SP.setSelection(0);
                }
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

                Update_TalukId_spinner(sp_strdistrict_ID);

                if(sel_districtsp_new!=sel_districtsp) {
                    sel_districtsp=sel_districtsp_new;
                    taluklist_SP.setSelection(0);
                    villagelist_SP.setSelection(0);
                }
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

                Update_VillageId_spinner(sp_strTaluk_ID);

                if(sel_taluksp_new!=sel_taluksp) {
                    sel_taluksp=sel_taluksp_new;
                    /*ViewFarmerList_arraylist.clear();
                    farmerListViewAdapter.notifyDataSetChanged();
*/
                    villagelist_SP.setSelection(0);
                }
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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin_Sector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_ClasssectorListDetails = (Class_SectorListDetails) spin_Sector.getSelectedItem();
                sp_strsector_ID = Obj_ClasssectorListDetails.getSectorId().toString();
                selected_sector = Obj_ClasssectorListDetails.getSectorName().toString();
                int sel_sectorsp_new = spin_Sector.getSelectedItemPosition();
                if(sel_sectorsp_new!=sel_sector) {
                    sel_sector=sel_sectorsp_new;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_update.setVisibility(View.VISIBLE);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FName = edt_FirstName.getText().toString();
                MName = edt_MiddleName.getText().toString();
                LName = edt_LastName.getText().toString();
                MobileNo = edt_MobileNo.getText().toString();
                EmailId = edt_EmailId.getText().toString();
                BusinessName = edt_BusinessName.getText().toString();
                btn_update.setVisibility(View.GONE);
                if(validation()) {
                    if (isInternetPresent) {
                        UpdateEnquiryDetails updateEnquiryDetails = new UpdateEnquiryDetails(EditEnquiryActivity.this);
                        updateEnquiryDetails.execute(FName, MName, LName, MobileNo, EmailId, BusinessName, sp_strstate_ID, sp_strdistrict_ID, sp_strTaluk_ID, sp_strVillage_ID, sp_strsector_ID, str_UserId,str_gender,EnquiryId);
                    }
                    else{
                        EnquiryListModel editEnquiry = null;
                        EditEnquiryList.clear();
                        editEnquiry = new EnquiryListModel(FName,EnquiryId,MobileNo,BusinessName,EmailId,"Success",YearCode,MName,LName,sp_strstate_ID,sp_strdistrict_ID,sp_strTaluk_ID,sp_strVillage_ID,sp_strsector_ID,str_gender,str_isApplicant);
                        EditEnquiryList.add(editEnquiry);
                        DBUpdate_EditEnquiryDetails_2SQLiteDB(EditEnquiryList);

                    }
                }
            }
        });

    }

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

    public void Update_districtid_spinner(String str_stateid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS DistrictList(DistrictID VARCHAR,DistrictName VARCHAR,Distr_Stateid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM DistrictList WHERE Distr_Stateid='" + str_stateid + "'", null);
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
        for(int i1=0; i1 < arrayObj_Class_DistrictListDetails2.length; i1++) {
            if (arrayObj_Class_DistrictListDetails2[i1].district_id.equalsIgnoreCase(str_DistrictId))
                sel_districtsp = i1;
        }
        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_DistrictListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
            districtlist_SP.setAdapter(dataAdapter);
           if(x>sel_districtsp) {
                districtlist_SP.setSelection(sel_districtsp);
            }
        }

    }
    public void Update_TalukId_spinner(String str_distid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS TalukList(TalukID VARCHAR,TalukName VARCHAR,Taluk_districtid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM TalukList WHERE Taluk_districtid='" + str_distid + "'", null);
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
        for(int i1=0; i1 < arrayObj_Class_TalukListDetails2.length; i1++) {
            if (arrayObj_Class_TalukListDetails2[i1].taluk_id.equalsIgnoreCase(str_TalukaId))
                sel_taluksp = i1;
        }
        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_TalukListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
            taluklist_SP.setAdapter(dataAdapter);
            if(x>sel_taluksp) {
                taluklist_SP.setSelection(sel_taluksp);
            }
        }

    }
    public void Update_VillageId_spinner(String str_talukid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR,SyncSlno VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM VillageList WHERE TalukID='" + str_talukid + "'", null);
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
                innerObj_Class_villageList.setTaluk_id(cursor1.getString(cursor1.getColumnIndex("SyncSlno")));


                arrayObj_Class_VillageListDetails2[i] = innerObj_Class_villageList;
                // Log.e("village_name", cursor1.getString(cursor1.getColumnIndex("TalukName")));
                i++;
            } while (cursor1.moveToNext());
        }//if ends


        db1.close();

        for(int i1=0; i1 < arrayObj_Class_VillageListDetails2.length; i1++) {
            if (arrayObj_Class_VillageListDetails2[i1].village_id.equalsIgnoreCase(str_VillageId)) {
                sel_villagesp = i1;
                Log.e("tag", "sel_village pos==" + sel_villagesp);
                Log.e("tag", "sel_villageID==" + arrayObj_Class_VillageListDetails2[i1].village_id);
                Log.e("tag", "sel_villageName==" + arrayObj_Class_VillageListDetails2[i1].village_name);
            }
        }

        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_VillageListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
            villagelist_SP.setAdapter(dataAdapter);
            if(x>sel_villagesp) {
                villagelist_SP.setSelection(sel_villagesp);
            }

        }

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
    public void uploadfromDB_Statelist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS StateList(StateID VARCHAR,StateName VARCHAR,state_status VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StateList", null);
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
        for(int i1=0; i1 < arrayObj_Class_stateDetails2.length; i1++) {
            if (arrayObj_Class_stateDetails2[i1].state_id.equalsIgnoreCase(str_StateId))
                sel_statesp = i1;
        }
        db1.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_stateDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
            statelist_SP.setAdapter(dataAdapter);

          //  sel_statesp = dataAdapter.getPosition(Integer.parseInt(str_StateId));

            if(x>sel_statesp) {
                statelist_SP.setSelection(sel_statesp);
            }
        }

    }
   /* public void uploadfromDB_Districtlist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS DistrictList(DistrictID VARCHAR,DistrictName VARCHAR,Distr_Stateid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM DistrictList", null);
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

       // sel_districtsp = new ArrayList<Class_DistrictListDetails>(Arrays.asList(arrayObj_Class_DistrictListDetails2)).indexOf(str_DistrictId);
        for(int i1=0; i1 < arrayObj_Class_DistrictListDetails2.length; i1++) {
            if (arrayObj_Class_DistrictListDetails2[i1].district_id.equalsIgnoreCase(str_DistrictId))
                sel_districtsp = i1;
        }
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(),R.layout.spinnercustomstyle, arrayObj_Class_DistrictListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
            districtlist_SP.setAdapter(dataAdapter);


           // sel_districtsp = dataAdapter.getPosition(Integer.parseInt(str_DistrictId));

            if(x>sel_districtsp) {
                districtlist_SP.setSelection(sel_districtsp);
            }
        }

    }
    public void uploadfromDB_Taluklist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS TalukList(TalukID VARCHAR,TalukName VARCHAR,Taluk_districtid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM TalukList", null);

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
        for(int i1=0; i1 < arrayObj_Class_TalukListDetails2.length; i1++) {
            if (arrayObj_Class_TalukListDetails2[i1].taluk_id.equalsIgnoreCase(str_TalukaId))
                sel_taluksp = i1;
        }
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_TalukListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
            taluklist_SP.setAdapter(dataAdapter);
            sel_taluksp = dataAdapter.getPosition(str_TalukaId);
            if(x>sel_taluksp) {
                taluklist_SP.setSelection(sel_taluksp);
            }
        }

    }
    public void uploadfromDB_Villagelist() {

        SQLiteDatabase db_village = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_village.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR,SyncSlno VARCHAR);");
        Cursor cursor1 = db_village.rawQuery("SELECT DISTINCT * FROM VillageList", null);
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
                innerObj_Class_villageList.setTaluk_id(cursor1.getString(cursor1.getColumnIndex("SyncSlno")));


                arrayObj_Class_VillageListDetails2[i] = innerObj_Class_villageList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db_village.close();
      *//*  for(int i1=0; i1 < arrayObj_Class_VillageListDetails2.length; i1++) {
            if (arrayObj_Class_VillageListDetails2[i1].village_id.equalsIgnoreCase(str_VillageId)) {
                sel_villagesp = i1;
                Log.e("tag", "sel_village pos==" + sel_villagesp);
                Log.e("tag", "sel_villageID==" + arrayObj_Class_VillageListDetails2[i1].village_id);
                Log.e("tag", "sel_villageName==" + arrayObj_Class_VillageListDetails2[i1].village_name);
            }
        }*//*
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_VillageListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
            villagelist_SP.setAdapter(dataAdapter);

           // sel_villagesp = dataAdapter.getPosition(str_VillageId);

            *//*if(x>sel_villagesp) {
                villagelist_SP.setSelection(sel_villagesp);
            }*//*
        }

    }*/

    public void List_offlineEnquiryDetails() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS ListEnquiryDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,FName VARCHAR,MobileNo VARCHAR,EmailId VARCHAR,BusinessName VARCHAR,EnquiryId VARCHAR,Status VARCHAR,YearCode VARCHAR,MName VARCHAR,LName VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,isApplicant VARCHAR,Gender VARCHAR,dataSyncStatus VARCHAR );");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM ListEnquiryDetails WHERE EnquiryId='" + EnquiryId + "'AND tempId='"+TempId+"'", null);
        int x = cursor1.getCount();
        Log.d("cursor Enquiry Count", Integer.toString(x));

        int i = 0;
        // arrayObj_Class_GrampanchayatListDetails2 = new Class_GrampanchayatListDetails[x];

        if (cursor1.moveToFirst()) {

           // if(YearCode.equalsIgnoreCase(String.valueOf(acadamicCode))) {

                do {

                    String FName = cursor1.getString(cursor1.getColumnIndex("FName"));
                    edt_FirstName.setText(FName);
                    String MName=cursor1.getString(cursor1.getColumnIndex("MName"));
                    if(MName.equals("")||MName==null){
                        edt_MiddleName.setText("");
                    }else{
                        edt_MiddleName.setText(MName);
                    }
                    String LName=cursor1.getString(cursor1.getColumnIndex("LName"));
                    if(LName.equals("")||LName==null){
                        edt_LastName.setText("");
                    }else{
                        edt_LastName.setText(LName);
                    }

                    String MobileNo = cursor1.getString(cursor1.getColumnIndex("MobileNo"));
                    edt_MobileNo.setText(MobileNo);
                    String EmailId = cursor1.getString(cursor1.getColumnIndex("EmailId"));
                    if(EmailId.equals("")||EmailId==null){
                        edt_EmailId.setText("");
                    }else{
                        edt_EmailId.setText(EmailId);
                    }
                    String BusinessName = cursor1.getString(cursor1.getColumnIndex("BusinessName"));
                    edt_BusinessName.setText(BusinessName);
                    String EnquiryId = cursor1.getString(cursor1.getColumnIndex("EnquiryId"));
                    String Status = cursor1.getString(cursor1.getColumnIndex("Status"));
                    String YearCode = cursor1.getString(cursor1.getColumnIndex("YearCode"));
                    str_StateId=cursor1.getString(cursor1.getColumnIndex("StateId"));
                    str_DistrictId=cursor1.getString(cursor1.getColumnIndex("DistrictId"));
                    str_TalukaId=cursor1.getString(cursor1.getColumnIndex("TalukaId"));
                    str_VillageId=cursor1.getString(cursor1.getColumnIndex("VillageId"));
                    str_SectorId=cursor1.getString(cursor1.getColumnIndex("SectorId"));
                    sel_sector =  Integer.parseInt(str_SectorId.toString());
                    spin_Sector.setSelection(sel_sector);
                    // cursor1.getString(cursor1.getColumnIndex("DeviceType"));
                 //   str_UserId= cursor1.getString(cursor1.getColumnIndex("UserId"));
                    str_gender= cursor1.getString(cursor1.getColumnIndex("Gender"));
                    TempId=cursor1.getString(cursor1.getColumnIndex("tempId"));
                    if(str_gender.equals("M"))
                    {
                        gender_radiogroup.check(R.id.rdb_male); }
                    else
                    { gender_radiogroup.check(R.id.rdb_female); }

                    //UpdateEnquiryDetails updateEnquiryDetails = new UpdateEnquiryDetails(EditEnquiryActivity.this);
                  //  updateEnquiryDetails.execute(FName, MName, LName, MobileNo, EmailId, BusinessName, sp_strstate_ID, sp_strdistrict_ID, sp_strTaluk_ID, sp_strVillage_ID, sp_strsector_ID, str_UserId,str_Gender,EnquiryId);

                    i++;


                } while (cursor1.moveToNext());

        }//if ends
        uploadfromDB_Statelist();
        uploadfromDB_Sectorlist();
    }

    public void DBUpdate_EditEnquiryDetails_2SQLiteDB(ArrayList<EnquiryListModel> str_editenquirys) {
        SQLiteDatabase db_editEnquiry = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_editEnquiry.execSQL("CREATE TABLE IF NOT EXISTS ListEnquiryDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,FName VARCHAR,MobileNo VARCHAR,EmailId VARCHAR,BusinessName VARCHAR,EnquiryId VARCHAR,Status VARCHAR,YearCode VARCHAR,MName VARCHAR,LName VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,isApplicant VARCHAR,Gender VARCHAR,dataSyncStatus VARCHAR );");

        String FName,LName,MName,MobileNo,EmailId,BusinessName,stateId,districtId,talukId,villegeId,sectorId,deviceType,userId,EnquiryId,Gender;

        for(int s=0;s<str_editenquirys.size();s++) {
            Log.e("tag","BusinessName="+str_editenquirys.get(s).getBussinessName()+"Fname="+str_editenquirys.get(s).getFname());

            FName=str_editenquirys.get(s).getFname();
            MName=str_editenquirys.get(s).getMName();
            LName=str_editenquirys.get(s).getLName();
            MobileNo=str_editenquirys.get(s).getMobileNo();
            EmailId=str_editenquirys.get(s).getEmailId();
            BusinessName=str_editenquirys.get(s).getBussinessName();
            stateId=str_editenquirys.get(s).getStateId();
            districtId=str_editenquirys.get(s).getDistrictId();
            talukId=str_editenquirys.get(s).getTalukaI();
            villegeId=str_editenquirys.get(s).getVillageId();
            sectorId=str_editenquirys.get(s).getSectorId();
            EnquiryId=str_editenquirys.get(s).getEnquiry_id();
            Gender = str_editenquirys.get(s).getGender();

            dataSyncStatus="offline";
            ContentValues cv = new ContentValues();
            cv.put("FName",FName);
            cv.put("MName",MName);
            cv.put("LName",LName);
            cv.put("MobileNo",MobileNo);
            cv.put("EmailId",EmailId);
            cv.put("StateId",stateId);
            cv.put("DistrictId",districtId);
            cv.put("TalukaId",talukId);
            cv.put("VillageId",villegeId);
            cv.put("SectorId",sectorId);
            cv.put("BusinessName",BusinessName);
            cv.put("Gender",Gender);
            cv.put("dataSyncStatus",dataSyncStatus);


            long result =  db_editEnquiry.update("ListEnquiryDetails", cv, "EnquiryId = ? AND tempId = ?", new String[]{EnquiryId,TempId});

            if(result != -1){
                editorEnquiry_obj = sharedpreferencebook_OfflineEnquiryData_Obj.edit();
                editorEnquiry_obj.putString(KeyValue_EnquiryEdited, "1");
                editorEnquiry_obj.apply();

                Toast toast= Toast.makeText(EditEnquiryActivity.this, "Enquiry Updated Successfully" , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor( Color.parseColor("#009900")); //any color your want
                toast.show();
               /* FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, EventMain_Fragment.newInstance());
                transaction.commit();*/
                Intent i = new Intent(EditEnquiryActivity.this, BottomActivity.class);
                i.putExtra("frgToLoad", "0");
                startActivity(i);
              /*  FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, EventMain_Fragment.newInstance());
                transaction.commit();*/
                finish();

                SQLiteDatabase db_editEnquiryId = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
                db_editEnquiryId.execSQL("CREATE TABLE IF NOT EXISTS ListEditEnquiryId(EnquiryId VARCHAR,tempId VARCHAR);");

                String SQLiteQuery = "INSERT INTO ListEditEnquiryId (EnquiryId,tempId)" +
                        " VALUES ('" + EnquiryId + "','" + TempId + "');";
                db_editEnquiryId.execSQL(SQLiteQuery);
                db_editEnquiryId.close();

            }
            else{

                //  Toast.makeText(context, "Update Request Failed " , Toast.LENGTH_LONG).show();
                Toast toast= Toast.makeText(EditEnquiryActivity.this, "Update Request Failed" , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                toast.show();
            }

        }

        db_editEnquiry.close();
    }

/*
    public void DBUpdate_EditEnquiryDetailsoffline_2SQLiteDB(ArrayList<EnquiryListModel> str_editenquirys){
        SQLiteDatabase db_editEnquiry = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_editEnquiry.execSQL("CREATE TABLE IF NOT EXISTS ListEnquiryDetails(FName VARCHAR,MobileNo VARCHAR,EmailId VARCHAR,BusinessName VARCHAR,EnquiryId VARCHAR,Status VARCHAR,YearCode VARCHAR,MName VARCHAR,LName VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,isApplicant VARCHAR,Gender VARCHAR);");

        String FName,LName,MName,MobileNo,EmailId,BusinessName,stateId,districtId,talukId,villegeId,sectorId,deviceType,userId,EnquiryId,Gender;

        for(int s=0;s<str_editenquirys.size();s++) {
            Log.e("tag", "BusinessName=" + str_editenquirys.get(s).getBussinessName() + "Fname=" + str_editenquirys.get(s).getFname());

            FName = str_editenquirys.get(s).getFname();
            MName = str_editenquirys.get(s).getMName();
            LName = str_editenquirys.get(s).getLName();
            MobileNo = str_editenquirys.get(s).getMobileNo();
            EmailId = str_editenquirys.get(s).getEmailId();
            BusinessName = str_editenquirys.get(s).getBussinessName();
            stateId = str_editenquirys.get(s).getStateId();
            districtId = str_editenquirys.get(s).getDistrictId();
            talukId = str_editenquirys.get(s).getTalukaI();
            villegeId = str_editenquirys.get(s).getVillageId();
            sectorId = str_editenquirys.get(s).getSectorId();
            EnquiryId = str_editenquirys.get(s).getEnquiry_id();
            Gender = str_editenquirys.get(s).getGender();

            ContentValues cv = new ContentValues();
            cv.put("FName", FName);
            cv.put("MName", MName);
            cv.put("LName", LName);
            cv.put("MobileNo", MobileNo);
            cv.put("EmailId", EmailId);
            cv.put("StateId", stateId);
            cv.put("DistrictId", districtId);
            cv.put("TalukaId", talukId);
            cv.put("VillageId", villegeId);
            cv.put("SectorId", sectorId);
            cv.put("BusinessName", BusinessName);
            cv.put("Gender", Gender);


            long result = db_editEnquiry.update("ListEnquiryDetails", cv, "EnquiryId = ?", new String[]{EnquiryId});

            if (result != -1) {
                Toast toast= Toast.makeText(EditEnquiryActivity.this, " Enquiry Updated Offline Successfully " , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor( Color.parseColor("#009900")); //any color your want
                toast.show();
                Intent i = new Intent(EditEnquiryActivity.this, BottomActivity.class);
                i.putExtra("frgToLoad", "0");
                startActivity(i);

                finish();

            }
            else{
                //  Toast.makeText(context, "Update Request Failed " , Toast.LENGTH_LONG).show();
                Toast toast= Toast.makeText(EditEnquiryActivity.this, "  Update Request Failed  " , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                toast.show();
            }
        }
    }
*/
    public class GetEnquiryDetails extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        //private ProgressBar progressBar;

        GetEnquiryDetails (Context ctx){
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
                SoapPrimitive S_StateId,S_DistrictId,S_TalukaId,S_VillageId,S_SectorId,S_LeadId, S_FirstName,S_MiddleName,S_LastName, S_EmailId, S_BusinessName, S_MobileNo, S_isApplicant, S_Status, S_Gender;
                Object O_StateId,O_DistrictId,O_TalukaId,O_VillageId,O_SectorId,O_LeadId, O_FirstName,O_MiddleName,O_LastName, O_EmailId, O_BusinessName, O_MobileNo, O_isApplicant, O_Status, O_Gender;
                String str_leadid = null, str_FirstName = null,str_MiddleName = null,str_LastName=null, str_EmailId = null, str_BusinessName = null, str_MobileNo = null, str_Status = null;

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

                        O_LeadId = list.getProperty("EnquiryId");
                        if (!O_LeadId.toString().equals("anyType{}") && !O_LeadId.toString().equals(null)) {
                            S_LeadId = (SoapPrimitive) list.getProperty("EnquiryId");
                            str_leadid = S_LeadId.toString();
                            Log.d("str_EnquiryId", str_leadid);
                        }

                        O_FirstName = list.getProperty("FirstName");
                        if (!O_FirstName.toString().equals("anyType{}") && !O_FirstName.toString().equals(null)) {
                            S_FirstName = (SoapPrimitive) list.getProperty("FirstName");
                            str_FirstName = S_FirstName.toString();
                            edt_FirstName.setText(str_FirstName);
                            Log.d("FirstName", str_FirstName);
                        }

                        O_MiddleName = list.getProperty("MiddleName");
                        if (!O_MiddleName.toString().equals("anyType{}") && !O_MiddleName.toString().equals(null)) {
                            S_MiddleName = (SoapPrimitive) list.getProperty("MiddleName");
                            str_MiddleName = S_MiddleName.toString();
                            edt_MiddleName.setText(str_MiddleName);
                            Log.d("MiddleName", str_MiddleName);
                        }

                        O_LastName = list.getProperty("LastName");
                        if (!O_LastName.toString().equals("anyType{}") && !O_LastName.toString().equals(null)) {
                            S_LastName = (SoapPrimitive) list.getProperty("LastName");
                            str_LastName = S_LastName.toString();
                            edt_LastName.setText(str_LastName);
                            Log.d("LastName", str_LastName);
                        }

                        O_MobileNo = list.getProperty("MobileNo");
                        if (!O_MobileNo.toString().equals("anyType{}") && !O_MobileNo.toString().equals(null)) {
                            S_MobileNo = (SoapPrimitive) list.getProperty("MobileNo");
                            Log.d("S_MobileNo", S_MobileNo.toString());
                            str_MobileNo = S_MobileNo.toString();
                            edt_MobileNo.setText(str_MobileNo);
                        }

                        O_EmailId = list.getProperty("EmailId");
                        if (!O_EmailId.toString().equals("anyType{}") && !O_EmailId.toString().equals(null)) {
                            S_EmailId = (SoapPrimitive) list.getProperty("EmailId");
                            Log.d("S_EmailId", S_EmailId.toString());
                            str_EmailId = S_EmailId.toString();
                            Log.d("Str_EmailId", str_EmailId);
                            edt_EmailId.setText(str_EmailId);
                        }

                        O_BusinessName = list.getProperty("BusinessName");
                        if (!O_BusinessName.toString().equals("anyType{}") && !O_BusinessName.toString().equals(null)) {
                            S_BusinessName = (SoapPrimitive) list.getProperty("BusinessName");
                            Log.d("S_BusinessName", S_BusinessName.toString());
                            str_BusinessName = S_BusinessName.toString();
                            Log.d("str_BusinessName", str_BusinessName);
                            edt_BusinessName.setText(str_BusinessName);
                           /// collegeNameLst.add(str_collegeName);

                        }
                        if (O_BusinessName.toString().equals("anyType{}") || O_BusinessName.toString().equals(null)) {
                            str_BusinessName = "";
                            edt_BusinessName.setText("");

                            //collegeNameLst.add(str_collegeName);
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
                            sel_sector =  Integer.parseInt(S_SectorId.toString());
                            spin_Sector.setSelection(sel_sector);
                        }

                        O_Gender = list.getProperty("Gender");
                        if (!O_Gender.toString().equals("anyType{}") && !O_Gender.toString().equals(null)) {
                            S_Gender = (SoapPrimitive) list.getProperty("Gender");
                            Log.d("Str_Gender", S_Gender.toString());
                            str_gender = S_Gender.toString();
                            if(str_gender.equals("M"))
                            {
                                gender_radiogroup.check(R.id.rdb_male); }
                            else
                            { gender_radiogroup.check(R.id.rdb_female); }

                        }


                        /*EnquiryListModel item = null;
                        if (!str_collegeName.isEmpty()) {
                            item = new EnquiryListModel(str_studentName, str_leadid, str_registrationDate, str_collegeName, str_MobileNo, str_IsFeesPaid);
                            feesList.add(item);
                        }*/


                    }

                }

                /*originalList = new ArrayList<EnquiryListModel>();
                originalList.addAll(feesList);

                adapter.notifyDataSetChanged();
                initCollegeSpinner();*/
            }
            progressDialog.dismiss();
            uploadfromDB_Statelist();
           /* uploadfromDB_Districtlist();
            uploadfromDB_Taluklist();
            uploadfromDB_Villagelist();*/
            uploadfromDB_Sectorlist();


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

            request.addProperty("AcademicCode",0);
            request.addProperty("EnquiryId",Long.parseLong(EnquiryId));

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
              runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(EditEnquiryActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(EditEnquiryActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        catch(OutOfMemoryError ex){
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(EditEnquiryActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());
            final String exceptionStr = t.getMessage().toString();
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(EditEnquiryActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;

    }

    public class UpdateEnquiryDetails extends AsyncTask<String, Void, Void>
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

        public UpdateEnquiryDetails(Context activity) {
            context = activity;
            dialog = new ProgressDialog(context);
        }
        @Override
        protected Void doInBackground(String... params)
        {
            Log.i("TAG", "doInBackground");
            Update( params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9] ,params[10],params[11],params[12],params[13]);
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            Log.i("TAG", "onPostExecute");

            Log.d("Enquiry Responseisss: ",status.toString());

            dialog.dismiss();

            if(status.equalsIgnoreCase("success")) {
              //  Toast.makeText(context, "Enquiry Updated Successfully " , Toast.LENGTH_LONG).show();
                Toast toast= Toast.makeText(EditEnquiryActivity.this, " Enquiry Updated Successfully " , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor( Color.parseColor("#009900")); //any color your want
                toast.show();
               /* FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, EventMain_Fragment.newInstance());
                transaction.commit();*/
                Intent i = new Intent(EditEnquiryActivity.this, BottomActivity.class);
                i.putExtra("frgToLoad", "0");
                startActivity(i);

              /*  FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, EventMain_Fragment.newInstance());
                transaction.commit();*/
                finish();

            }
            else{
              //  Toast.makeText(context, "Update Request Failed " , Toast.LENGTH_LONG).show();
              //  Toast toast= Toast.makeText(EditEnquiryActivity.this, "  Update Request Failed  " , Toast.LENGTH_LONG);
                Toast toast= Toast.makeText(context, "  "+status.toString()+"  " , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                toast.show();
                btn_update.setVisibility(View.VISIBLE);
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
        /*if( edt_MobileNo.getText().toString().length() == 0 ){
            edt_MobileNo.setError( "Mobile No required!" );bmobileno=false;}
*/
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
        String email = edt_EmailId.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(edt_EmailId.getText().toString().length() != 0) {
        if (!email.matches(emailPattern))
        {
            edt_EmailId.setError("InValid Email Id");
            bfname=false;
        }}else {
            bfname=true;
        }
        if(bfname&&bmobileno&&bBusinessname&&bsector) {
            return true;
        }else{
            btn_update.setVisibility(View.VISIBLE);
            Toast toast= Toast.makeText(getApplicationContext(), "  Please fill mandatory fields" , Toast.LENGTH_LONG);
            View view =toast.getView();
            view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
            toast.show();
            return false;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(EditEnquiryActivity.this, BottomActivity.class);
        i.putExtra("frgToLoad", "0");
        startActivity(i);
        finish();

    }
}
