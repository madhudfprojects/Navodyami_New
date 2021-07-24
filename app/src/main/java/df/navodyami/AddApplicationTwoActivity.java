package df.navodyami;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddApplicationTwoActivity extends AppCompatActivity {

    RadioGroup manufacturer_radiogroup,license_radiogroup,labour_radiogroup,outsidefamily_radiogroup,machine_radiogroup;
    String str_manufacture,int_manufacture="1",int_licence="0",str_ownership,int_Labour="1",int_outsidefamily="0",int_machine="0",str_female_year1,str_male_year1,str_male_year2,str_male_year3,str_female_year2,str_female_year3;

    EditText edt_license,edt_product1,edt_product2,edt_product3,edt_BusinessYear,edt_Total_Employee,edt_male_year1,edt_female_year1,edt_male_year2,edt_female_year2,edt_male_year3,edt_female_year3;
    EditText edt_trunover_year1,edt_trunover_year2,edt_trunover_year3,edt_Profit_year1,edt_Profit_year2,edt_Profit_year3,edt_machine,edt_other,edt_otherLast;
    Spinner Ownership_sp;
    ArrayAdapter dataAdapter_Ownership,dataAdapter_catgary;

    Class_YearListDetails[] arrayObj_Class_yearDetails2;
    Class_YearListDetails Obj_Class_yearDetails;
    int sel_yearsp=0;
    String selected_year, sp_stryear_ID;

    TextView yearOne,yearTwo,yearThree,yearOne_trunover,yearTwo_trunover,yearThree_trunover,yearOne_Profit,yearTwo_Profit,yearThree_Profit;

    CheckBox checkbox_marketsanthe,checkbox_consumer,checkbox_retail,checkbox_wholesale,checkbox_marketsantheLast,checkbox_consumerLast,checkbox_retailLast,checkbox_wholesaleLast;
    ImageView Prev_iv,Next_iv;
    String c1 = " ",c2Last= " ",EnquiryId,ApplicationSlnoNew,isApplicant,tempId;
    ArrayList<String> consumer_array;
    ArrayList<String> consumerLast_array;
    List<String> Earn_Most_Channel= new ArrayList<String>();// = Arrays.asList(str.split(","));
    List<String> Where_Sell_Products= new ArrayList<String>();
    ArrayList<Class_AddApplicationTwoDetails> addApplicationTwoList;
    Class_AddApplicationTwoDetails[] arrayObj_Class_ApplicationDetailsTwo;

    String status;
    String str_licence,str_productOne,str_productTwo,str_productThree,str_businessYear,str_yearOne,str_yearTwo,str_yearThree;
    String str_femaleYear1,str_femaleYear2,str_femaleYear3,str_maleYear1,str_maleYear2,str_maleYear3,str_trunover_year1,str_trunover_year2,str_trunover_year3;
    String str_Profit_year1,str_Profit_year2,str_Profit_year3,str_Total_Employee,str_whichmachine;

    SharedPreferences sharedpreferencebook_usercredential_Obj;
    SharedPreferences.Editor editor_obj;
    String str_UserId;
    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_employeeid = "KeyValue_employeeid";

    ArrayList<String> listYears = new ArrayList<String>();
    ArrayList<String> listPermentEmplyee = new ArrayList<String>();
    ArrayList<String> listFemalePermentEmplyee = new ArrayList<String>();
    ArrayList<String> listMalePermentEmplyee = new ArrayList<String>();
    ArrayList<String> listTurnOver = new ArrayList<String>();
    ArrayList<String> listProfit = new ArrayList<String>();

    Boolean isInternetPresent = false;
    Class_InternetDectector internetDectector;

    Class_OwnershipListDetails[] arrayObj_Class_OwnershipDetails2;
    final ArrayList listOwner = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_application_two);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_n_actionbar);
        setSupportActionBar(toolbar);
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView title= (TextView) toolbar.findViewById(R.id.title_name);
        title.setText("Application Form");
        getSupportActionBar().setTitle("");

        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        sharedpreferencebook_usercredential_Obj=getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_UserId = sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeid, "").trim();


        manufacturer_radiogroup =(RadioGroup)findViewById(R.id.manufacturer_radiogroup);
        license_radiogroup = (RadioGroup) findViewById(R.id.license_radiogroup);
        labour_radiogroup= (RadioGroup) findViewById(R.id.labour_radiogroup);
        outsidefamily_radiogroup=(RadioGroup) findViewById(R.id.outsidefamily_radiogroup);
        machine_radiogroup=(RadioGroup) findViewById(R.id.machine_radiogroup);

        edt_license = (EditText) findViewById(R.id.edt_license);
        edt_product1 = (EditText) findViewById(R.id.edt_product1);
        edt_product2 = (EditText) findViewById(R.id.edt_product2);
        edt_product3 = (EditText) findViewById(R.id.edt_product3);
        edt_BusinessYear = (EditText) findViewById(R.id.edt_BusinessYear);
        edt_Total_Employee = (EditText) findViewById(R.id.edt_Total_Employee);

        edt_female_year1 = (EditText) findViewById(R.id.female_year1);
        edt_female_year2 = (EditText) findViewById(R.id.female_year2);
        edt_female_year3 = (EditText) findViewById(R.id.female_year3);
        edt_male_year1 = (EditText) findViewById(R.id.male_year1);
        edt_male_year2 = (EditText) findViewById(R.id.male_year2);
        edt_male_year3 = (EditText) findViewById(R.id.male_year3);

        edt_trunover_year1 = (EditText) findViewById(R.id.trunover_year1);
        edt_trunover_year2 = (EditText) findViewById(R.id.trunover_year2);
        edt_trunover_year3 = (EditText) findViewById(R.id.trunover_year3);

        edt_Profit_year1 = (EditText) findViewById(R.id.Profit_year1);
        edt_Profit_year2 = (EditText) findViewById(R.id.Profit_year2);
        edt_Profit_year3 = (EditText) findViewById(R.id.Profit_year3);

        edt_machine = (EditText) findViewById(R.id.edt_machine);
        edt_other = (EditText) findViewById(R.id.edt_other);
        edt_otherLast = (EditText) findViewById(R.id.edt_otherLast);

        yearOne = (TextView) findViewById(R.id.yearOne);
        yearTwo = (TextView) findViewById(R.id.yearTwo);
        yearThree = (TextView) findViewById(R.id.yearThree);
        yearOne_trunover = (TextView) findViewById(R.id.yearOne_trunover);
        yearTwo_trunover = (TextView) findViewById(R.id.yearTwo_trunover);
        yearThree_trunover = (TextView) findViewById(R.id.yearThree_trunover);
        yearOne_Profit = (TextView) findViewById(R.id.yearOne_Profit);
        yearTwo_Profit = (TextView) findViewById(R.id.yearTwo_Profit);
        yearThree_Profit = (TextView) findViewById(R.id.yearThree_Profit);

        checkbox_marketsanthe = (CheckBox) findViewById(R.id.checkbox_marketsanthe);
        checkbox_consumer = (CheckBox) findViewById(R.id.checkbox_consumer);
        checkbox_retail = (CheckBox) findViewById(R.id.checkbox_retail);
        checkbox_wholesale = (CheckBox) findViewById(R.id.checkbox_wholesale);
        checkbox_marketsantheLast = (CheckBox) findViewById(R.id.checkbox_marketsantheLast);
        checkbox_consumerLast = (CheckBox) findViewById(R.id.checkbox_consumerLast);
        checkbox_retailLast = (CheckBox) findViewById(R.id.checkbox_retailLast);
        checkbox_wholesaleLast = (CheckBox) findViewById(R.id.checkbox_wholesaleLast);

        addApplicationTwoList = new ArrayList<>();
        consumer_array = new ArrayList<>();
        consumerLast_array = new ArrayList<>();

        Prev_iv = (ImageView) findViewById(R.id.Prev_iv);
        Next_iv = (ImageView) findViewById(R.id.Next_iv);

        Ownership_sp = (Spinner) findViewById(R.id.Ownership_sp);
        //clear_fields();

       /* checkbox_consumerLast.setChecked(true);
        checkbox_marketsantheLast.setChecked(true);*/

        /*Intent intent = getIntent();
        EnquiryId=intent.getStringExtra("EnquiryId");*/
        Intent intent = getIntent();
        ApplicationSlnoNew = intent.getStringExtra("Application_SlnoNew");
        EnquiryId=intent.getStringExtra("EnquiryId");
        isApplicant=intent.getStringExtra("isApplicant");
        tempId=intent.getStringExtra("tempId");

        Log.e("tag","ApplicationSlnoNew two="+ApplicationSlnoNew);
        Log.e("tag","EnquiryId two="+EnquiryId);
        Log.e("tag","tempId two="+tempId);

        uploadfromDB_Yearlist();
        uploadfromDB_OwnershipList();
        setOwnershipSpinner();

        Ownership_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                str_ownership = Ownership_sp.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //  uploadfromDB_AddApplicationTwoDetails();
        uploadfromDB_CompliteApplicationTwoDetails();

        Next_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                listYears.clear();
                listProfit.clear();
                listTurnOver.clear();
                listFemalePermentEmplyee.clear();
                listMalePermentEmplyee.clear();

                str_licence=edt_license.getText().toString();

                if(edt_license.getText().length()==0){
                    str_licence="";
                }
                Log.e("tag","whichLicence str_licence1="+str_licence);
                str_productOne=edt_product1.getText().toString();
                str_productTwo=edt_product2.getText().toString();
                str_productThree=edt_product3.getText().toString();
                str_businessYear=edt_BusinessYear.getText().toString();
                str_Total_Employee=edt_Total_Employee.getText().toString();
                str_yearOne=yearOne.getText().toString();
                str_yearTwo=yearTwo.getText().toString();
                str_yearThree=yearThree.getText().toString();
                str_femaleYear1=edt_female_year1.getText().toString();
                str_femaleYear2=edt_female_year2.getText().toString();
                str_femaleYear3=edt_female_year3.getText().toString();
                str_maleYear1=edt_male_year1.getText().toString();
                str_maleYear2=edt_male_year2.getText().toString();
                str_maleYear3=edt_male_year3.getText().toString();
                str_trunover_year1=edt_trunover_year1.getText().toString();
                str_trunover_year2=edt_trunover_year2.getText().toString();
                str_trunover_year3=edt_trunover_year3.getText().toString();
                str_Profit_year1=edt_Profit_year1.getText().toString();
                str_Profit_year2=edt_Profit_year2.getText().toString();
                str_Profit_year3=edt_Profit_year3.getText().toString();
                str_whichmachine=edt_machine.getText().toString();
                c1="";
                c2Last="";
                consumer_array.clear();
                consumerLast_array.clear();

                checkboxvalues();
                checkboxvaluesLast();
               // int size=consumer_array.size();
               /* if(size>1) {
                    c1 = consumer_array.get(size - 1);
                    c2Last = consumerLast_array.get(size - 1);
                }*/
                for(int i=0;i<consumer_array.size();i++) {

                    c1 += consumer_array.get(i);
                    c1 += ",";
                }
                for(int i=0;i<consumerLast_array.size();i++) {
                    c2Last+=consumerLast_array.get(i);
                    c2Last+=",";
                }
                Log.e("tag","sell product=="+c1);
                Log.e("tag","last C2=="+c1);
                String year1=yearOne.getText().toString();
                String year2=yearTwo.getText().toString();
                String year3=yearThree.getText().toString();

                listYears.add(year1);
                listYears.add(year2);
                listYears.add(year3);

                if(edt_female_year1.getText().length()==0){
                    str_femaleYear1="0";
                }
                if(edt_female_year2.getText().length()==0){
                    str_femaleYear2="0";
                }
                if(edt_female_year3.getText().length()==0){
                    str_femaleYear3="0";
                }
                if(edt_male_year1.getText().length()==0){
                    str_maleYear1="0";
                }
                if(edt_male_year2.getText().length()==0){
                    str_maleYear2="0";
                }
                if(edt_male_year3.getText().length()==0){
                    str_maleYear3="0";
                }
                if(edt_trunover_year1.getText().length()==0){
                    str_trunover_year1="0";
                }
                if(edt_trunover_year2.getText().length()==0){
                    str_trunover_year2="0";
                }
                if(edt_trunover_year3.getText().length()==0){
                    str_trunover_year3="0";
                }
                if(edt_Profit_year1.getText().length()==0){
                    str_Profit_year1="0";
                }
                if(edt_Profit_year2.getText().length()==0){
                    str_Profit_year2="0";
                }
                if(edt_Profit_year3.getText().length()==0){
                    str_Profit_year3="0";
                }
                if(edt_Total_Employee.getText().length()==0){
                    str_Total_Employee="0";
                }
                if(edt_BusinessYear.getText().length()==0){
                    str_businessYear="0";
                }
                listFemalePermentEmplyee.add(str_femaleYear1);
                listFemalePermentEmplyee.add(str_femaleYear2);
                listFemalePermentEmplyee.add(str_femaleYear3);

                listMalePermentEmplyee.add(str_maleYear1);
                listMalePermentEmplyee.add(str_maleYear2);
                listMalePermentEmplyee.add(str_maleYear3);

                listTurnOver.add(str_trunover_year1);
                listTurnOver.add(str_trunover_year2);
                listTurnOver.add(str_trunover_year3);

                listProfit.add(str_Profit_year1);
                listProfit.add(str_Profit_year2);
                listProfit.add(str_Profit_year3);

                if(validation()) {
                    internetDectector = new Class_InternetDectector(getApplicationContext());
                    isInternetPresent = internetDectector.isConnectingToInternet();

                    if (isInternetPresent) {
                        if(EnquiryId.equalsIgnoreCase("0")) {
                            Class_AddApplicationTwoDetails class_addApplicationTwoDetails = new Class_AddApplicationTwoDetails(int_manufacture, int_licence, str_licence, str_productOne,
                                    str_productTwo, str_productThree, str_businessYear, str_ownership, str_yearOne, str_femaleYear1, str_maleYear1, str_yearTwo, str_femaleYear2, str_maleYear2,
                                    str_yearThree, str_femaleYear3, str_maleYear3, int_Labour, int_outsidefamily, str_trunover_year1, str_trunover_year2, str_trunover_year3, str_Profit_year1,
                                    str_Profit_year2, str_Profit_year3, str_whichmachine, int_machine, c1, c2Last, EnquiryId, str_Total_Employee, ApplicationSlnoNew);
                            addApplicationTwoList.add(class_addApplicationTwoDetails);

                            //DBCreate_AddApplicationTwoDetails_insert_2SQLiteDB(addApplicationTwoList);
                            DBCreate_AddCompliteApplicationTwoDetails_insert_2SQLiteDB(addApplicationTwoList);
                            Intent intent1 = new Intent(AddApplicationTwoActivity.this, AddApplicationThreeActivity.class);
                            intent1.putExtra("EnquiryId",EnquiryId);
                            intent1.putExtra("Application_SlnoNew",ApplicationSlnoNew);
                            intent1.putExtra("isApplicant",isApplicant);
                            intent1.putExtra("tempId",tempId);
                            startActivity(intent1);
                        }
                        else{
                            AddApplicationTwoDetails addApplicationTwoDetails = new AddApplicationTwoDetails(AddApplicationTwoActivity.this);
                            addApplicationTwoDetails.execute();
                            Class_AddApplicationTwoDetails class_addApplicationTwoDetails = new Class_AddApplicationTwoDetails(int_manufacture, int_licence, str_licence, str_productOne,
                                    str_productTwo, str_productThree, str_businessYear, str_ownership, str_yearOne, str_femaleYear1, str_maleYear1, str_yearTwo, str_femaleYear2, str_maleYear2,
                                    str_yearThree, str_femaleYear3, str_maleYear3, int_Labour, int_outsidefamily, str_trunover_year1, str_trunover_year2, str_trunover_year3, str_Profit_year1,
                                    str_Profit_year2, str_Profit_year3, str_whichmachine, int_machine, c1, c2Last, EnquiryId, str_Total_Employee, ApplicationSlnoNew);
                            addApplicationTwoList.add(class_addApplicationTwoDetails);
                            DBCreate_AddCompliteApplicationTwoDetails_insert_2SQLiteDB(addApplicationTwoList);
                            // DBCreate_AddApplicationTwoDetails_insert_2SQLiteDB(addApplicationTwoList);
                        }
                    } else {
                        Class_AddApplicationTwoDetails class_addApplicationTwoDetails = new Class_AddApplicationTwoDetails(int_manufacture, int_licence, str_licence, str_productOne,
                                str_productTwo, str_productThree, str_businessYear, str_ownership, str_yearOne, str_femaleYear1, str_maleYear1, str_yearTwo, str_femaleYear2, str_maleYear2,
                                str_yearThree, str_femaleYear3, str_maleYear3, int_Labour, int_outsidefamily, str_trunover_year1, str_trunover_year2, str_trunover_year3, str_Profit_year1,
                                str_Profit_year2, str_Profit_year3, str_whichmachine, int_machine, c1, c2Last, EnquiryId, str_Total_Employee, ApplicationSlnoNew);
                        addApplicationTwoList.add(class_addApplicationTwoDetails);
                        DBCreate_AddCompliteApplicationTwoDetails_insert_2SQLiteDB(addApplicationTwoList);
                        //DBCreate_AddApplicationTwoDetails_insert_2SQLiteDB(addApplicationTwoList);

                        Intent intent1 = new Intent(AddApplicationTwoActivity.this, AddApplicationThreeActivity.class);
                        intent1.putExtra("EnquiryId",EnquiryId);
                        intent1.putExtra("Application_SlnoNew",ApplicationSlnoNew);
                        intent1.putExtra("isApplicant",isApplicant);
                        intent1.putExtra("tempId",tempId);
                        startActivity(intent1);
                    }
                }




                /*Intent intent1 = new Intent(AddApplicationTwoActivity.this, AddApplicationThreeActivity.class);
                  intent1.putExtra("EnquiryId",EnquiryId);
                startActivity(intent1);*/
            }
        });
        Prev_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(AddApplicationTwoActivity.this, AddApplicationOneActivity.class);
                intent1.putExtra("EnquiryId",EnquiryId);
                intent1.putExtra("ApplicationSlno",ApplicationSlnoNew);
                intent1.putExtra("isApplicant",isApplicant);
                intent1.putExtra("tempId",tempId);
                startActivity(intent1);


            }
        });
    }

    public void initialize_fields(){

    }

    public void clear_fields(){
        Ownership_sp.setSelection(0);
        edt_license.setText("");
        edt_product1.setText("");
        edt_product2.setText("");
        edt_product3.setText("");
        edt_machine.setText("");
        edt_trunover_year3.setText("");
        edt_trunover_year2.setText("");
        edt_trunover_year1.setText("");
        edt_female_year3.setText("");
        edt_female_year2.setText("");
        edt_female_year1.setText("");
        edt_BusinessYear.setText("");
        edt_male_year3.setText("");
        edt_male_year2.setText("");
        edt_male_year1.setText("");
        edt_other.setText("");
        edt_otherLast.setText("");
        edt_Total_Employee.setText("");

        if (checkbox_consumer.isChecked()) {
            checkbox_consumer.setChecked(false);
        }
        if (checkbox_wholesale.isChecked()) {
            checkbox_wholesale.setChecked(false);
        }
        if (checkbox_retail.isChecked()) {
            checkbox_retail.setChecked(false);

        }
        if (checkbox_marketsanthe.isChecked()) {
            checkbox_marketsanthe.setChecked(false);
        }

        if (checkbox_consumerLast.isChecked()) {
            checkbox_consumerLast.setChecked(false);
        }
        if (checkbox_wholesaleLast.isChecked()) {
            checkbox_wholesaleLast.setChecked(false);
        }
        if (checkbox_retailLast.isChecked()) {
            checkbox_retailLast.setChecked(false);
        }
        if (checkbox_marketsantheLast.isChecked()) {
            checkbox_marketsantheLast.setChecked(false);
        }

    }
    public boolean validation(){
        Boolean bownership=true;
        String str_Ownership = Ownership_sp.getSelectedItem().toString();

        if(str_Ownership.contains("Select Ownership")){

            TextView errorText = (TextView)Ownership_sp.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Please select the Ownership");
            // Toast.makeText(AddEnquiryActivity.this,"Please select the Sector",Toast.LENGTH_LONG).show();
            bownership=false;
            // return false;
        }
       /* if(str_Ownership.contains("Select Ownership")){

            TextView errorText = (TextView)Ownership_sp.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Please select the Ownership");
            // Toast.makeText(AddEnquiryActivity.this,"Please select the Sector",Toast.LENGTH_LONG).show();
            bownership=false;
            // return false;
        }*/
        if(bownership) {
            return true;
        }else{
            Toast toast= Toast.makeText(getApplicationContext(), "  Please fill mandatory fields" , Toast.LENGTH_LONG);
            View view =toast.getView();
            view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
            toast.show();
            return false;}

    }
    public void onRadioButtonManufactureClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.rdb_manufacture:
                if (checked) {
                    str_manufacture="Manufacturer";
                    int_manufacture = "1";
                }
                break;
            case R.id.rdb_trader:
                if (checked) {
                    str_manufacture="Trader";
                    int_manufacture = "0";
                }
                break;
        }
    }

    public void onRadioButtonLicenceClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.rdb_licenseyes:
                if (checked) {
                    // str_manufacture="Manufacturer";
                    int_licence = "1";
                    edt_license.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rdb_licenseno:
                if (checked) {
                    //str_manufacture="Trader";
                    int_licence="0";
                    edt_license.setVisibility(View.GONE);
                }
                break;
        }
    }
    public void onRadioButtonLabourClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.rdb_Skilled:
                if (checked) {
                    // str_manufacture="Manufacturer";
                    int_Labour = "1";
                }
                break;
            case R.id.rdb_Unskilled:
                if (checked) {
                    //str_manufacture="Trader";
                    int_Labour="0";
                }
                break;
        }
    }
    public void onRadioButtonOutsidefamilyClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.rdb_outsidefamilyYes:
                if (checked) {
                    // str_manufacture="Manufacturer";
                    int_outsidefamily = "1";

                }
                break;
            case R.id.rdb_outsidefamilyNo:
                if (checked) {
                    //str_manufacture="Trader";
                    int_outsidefamily="0";
                }
                break;
        }
    }

    public void onRadioButtonMachineClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.rdb_ownmachine:
                if (checked) {
                    // str_manufacture="Manufacturer";
                    int_machine = "1";

                }
                break;
            case R.id.rdb_rentmachine:
                if (checked) {
                    //str_manufacture="Trader";
                    int_machine="0";
                }
                break;
        }
    }
    public void setcheckboxvalues() {


        for (int i = 0; i < Where_Sell_Products.size(); i++)
        {
            Log.e("tag","Where_Sell_Products in loop="+Where_Sell_Products.get(i));
            if (Where_Sell_Products.get(i).equalsIgnoreCase("Direct Consumer")) {
                checkbox_consumer.setChecked(true);
                Log.e("tag","Where_Sell_Products check="+Where_Sell_Products.get(i));
            }/*else {
                checkbox_consumer.setChecked(false);
                Log.e("tag","Where_Sell_Products not check="+Where_Sell_Products.get(i));
            }*/
            if (Where_Sell_Products.get(i).equalsIgnoreCase("Local")) {
                checkbox_marketsanthe.setChecked(true);
            }/*else {
                checkbox_marketsanthe.setChecked(false);
            }*/
            if (Where_Sell_Products.get(i).equalsIgnoreCase("Retail Stores")) {
                checkbox_retail.setChecked(true);
            }/*else {
                checkbox_retail.setChecked(false);
            }*/
            if (Where_Sell_Products.get(i).equalsIgnoreCase("Wholesale")) {
                checkbox_wholesale.setChecked(true);
            }/*else {
                checkbox_wholesale.setChecked(false);
            }
*/
        }
    }



    public void setcheckboxvaluesLast() {

        for(int i=0;i<Earn_Most_Channel.size();i++){
            if(Earn_Most_Channel.get(i).equalsIgnoreCase("Direct Consumer")){
                checkbox_consumerLast.setChecked(true);
            }/*else {
                    checkbox_consumerLast.setChecked(false);
                }*/
            if(Earn_Most_Channel.get(i).equalsIgnoreCase("Local")){
                checkbox_marketsantheLast.setChecked(true);
            }/*else {
                    checkbox_marketsantheLast.setChecked(false);
                }*/
            if(Earn_Most_Channel.get(i).equalsIgnoreCase("Retail Stores")){
                checkbox_retailLast.setChecked(true);
            }/*else {
                    checkbox_retailLast.setChecked(false);
                }*/
            if(Earn_Most_Channel.get(i).equalsIgnoreCase("Wholesale")){
                checkbox_wholesaleLast.setChecked(true);
            }/*else {
                    checkbox_wholesaleLast.setChecked(false);
                }*/

        }

    }
    public void checkboxvalues() {

        if (checkbox_consumer.isChecked()) {
            // c1 += "Direct Consumer" + ",";
            consumer_array.add("Direct Consumer");
        }
        if (checkbox_wholesale.isChecked()) {
            // c1 += "Wholesale" + ",";
            consumer_array.add("Wholesale");
        }
        if (checkbox_retail.isChecked()) {
            // c1 += "Retail Stores" + ",";
            //// Log.e("tag","C1=="+c1);
            consumer_array.add("Retail Stores");

        }
        if (checkbox_marketsanthe.isChecked()) {
            // c1 += "Local" + ",";
            consumer_array.add("Local");
        }

       /* if(edt_other.getText().toString().length() == 0){
            c1 += edt_other.getText().toString();
            Log.e("tag","C11=="+c1);

        }*/

    }
    public void checkboxvaluesLast() {

        if (checkbox_consumerLast.isChecked()) {

            // flag2 = true;
            //  c2Last += "Direct Consumer" + ",";
            consumerLast_array.add("Direct Consumer");
        }
        if (checkbox_wholesaleLast.isChecked()) {
            // flag3 = true;
            // c2Last += "Wholesale" + ",";
            consumerLast_array.add("Wholesale");
        }
        if (checkbox_retailLast.isChecked()) {
            //  flag4 = true;
            ///  c2Last += "Retail Stores" + ",";
            //  Log.e("tag","c2Last=="+c2Last);
            consumerLast_array.add("Retail Stores");
        }
        if (checkbox_marketsantheLast.isChecked()) {
            //checkbox.setBackgroundColor(Color.BLACK);
            // flag1 = true;
            // c2Last += "Local" + ",";
            consumerLast_array.add("Local");
        }
       /* if(edt_otherLast.getText().toString().length() == 0){
            c2Last += edt_otherLast.getText().toString();
            Log.e("tag","c2Last=="+c2Last);

        }*/

    }
    private void setOwnershipSpinner() {

      /*  listOwner.add("Select Ownership");
        listOwner.add("Individually");
        listOwner.add("Family");
        listOwner.add("Partnership");
        listOwner.add("Cooperative");*/
        // listOwner.add("Other");
        /*listBg.add("AB-");
        listBg.add("O+");
        listBg.add("O-");
        listBg.add("Bombay");*/

        //ArrayAdapter dataAdapterSem = new ArrayAdapter(context, R.layout.simple_spinner_semester, listsemester);
        dataAdapter_Ownership = new ArrayAdapter(AddApplicationTwoActivity.this, R.layout.simple_spinner_items, listOwner);

        // Drop down layout style - list view with radio button
        dataAdapter_Ownership.setDropDownViewResource(R.layout.spinnercustomstyle);

        // attaching data adapter to spinner
        Ownership_sp.setAdapter(dataAdapter_Ownership);
        // sp_Education.setSupportBackgroundTintList(ContextCompat.getColorStateList(AddApplicationOneActivity.this, R.color.colorBlack));

    }

    public void uploadfromDB_Yearlist() {

        SQLiteDatabase db_year = openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
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
                innerObj_Class_yearList.setDisplay_Year(cursor.getString(cursor.getColumnIndex("Display_Year")));


                arrayObj_Class_yearDetails2[i] = innerObj_Class_yearList;
                i++;

            } while (cursor.moveToNext());


        }//if ends

        db_year.close();
        if (x > 0) {
            // Class_YearListDetails Obj_Class_yearList= new Class_YearListDetails();

            for(int j=0;j<x;j++){
                Log.e("tag","yearOne="+ arrayObj_Class_yearDetails2[j].getDisplay_Year());
                Log.e("tag","yearOne1="+ arrayObj_Class_yearDetails2[0].getDisplay_Year());

                yearOne.setText( arrayObj_Class_yearDetails2[0].getDisplay_Year());
                yearOne_trunover.setText( arrayObj_Class_yearDetails2[0].getDisplay_Year());
                yearOne_Profit.setText( arrayObj_Class_yearDetails2[0].getDisplay_Year());

                Log.e("tag","yearOne2="+ arrayObj_Class_yearDetails2[1].getDisplay_Year());

                yearTwo.setText(arrayObj_Class_yearDetails2[1].getDisplay_Year());
                yearTwo_trunover.setText(arrayObj_Class_yearDetails2[1].getDisplay_Year());
                yearTwo_Profit.setText(arrayObj_Class_yearDetails2[1].getDisplay_Year());

                Log.e("tag","yearOne3="+ arrayObj_Class_yearDetails2[2].getDisplay_Year());

                yearThree.setText(arrayObj_Class_yearDetails2[2].getDisplay_Year());
                yearThree_trunover.setText(arrayObj_Class_yearDetails2[2].getDisplay_Year());
                yearThree_Profit.setText(arrayObj_Class_yearDetails2[2].getDisplay_Year());

            }
          /*  ArrayAdapter dataAdapter = new ArrayAdapter(getContext(),R.layout.spinnercustomstyle, arrayObj_Class_yearDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
            spin_year.setAdapter(dataAdapter);
            if(x>sel_yearsp) {
                spin_year.setSelection(sel_yearsp);
            }*/
        }

    }

    public void DBCreate_AddApplicationTwoDetails_insert_2SQLiteDB(ArrayList<Class_AddApplicationTwoDetails> str_addenquirys)
    {
        SQLiteDatabase db_addApplicationtwo = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

        db_addApplicationtwo.execSQL("CREATE TABLE IF NOT EXISTS AddApplicationDetailsTwo(Manufacture VARCHAR,Licence VARCHAR,WhichLicence VARCHAR,ProductOne VARCHAR," +
                "ProductTwo VARCHAR,ProductThree VARCHAR,BusinessYear VARCHAR,Ownership VARCHAR,YearOne VARCHAR,Female_year1 VARCHAR,Male_year1 VARCHAR," +
                "YearTwo VARCHAR,Female_year2 VARCHAR,Male_year2 VARCHAR,YearThree VARCHAR,Female_year3 VARCHAR,Male_year3 VARCHAR,Labour VARCHAR,OutsidefamilyLabour VARCHAR, " +
                "Trunover_year1 VARCHAR,Trunover_year2 VARCHAR,Trunover_year3 VARCHAR,Profit_year1 VARCHAR,Profit_year2 VARCHAR,Profit_year3 VARCHAR,Which_Machine VARCHAR,Machine VARCHAR,Sell_products VARCHAR,Last_quarter VARCHAR,EnquiryId VARCHAR,Total_Employee VARCHAR,Application_Slno VARCHAR);");

     /*   String FName,LName,MName,MobileNo,EmailId,BusinessName,stateId,districtId,talukId,villegeId,sectorId,deviceType,userId,EnquiryId,Gender;

        String Economic,whatsappNo,DOB,education, socialCatgary, businessAddress,yearInBusiness,yearInCurrentBusiness,sectorBusiness,gottoknow,UNDP,yearUNDP,navodyami,yearNavodyami;
*/
        String manufacture, licence, whichLicence, productOne, productTwo, productThree, businessYear, ownership;
        String yearOne, female_year1, male_year1, yearTwo, female_year2, male_year2, yearThree, female_year3;
        String male_year3, labour, outsidefamilyLabour, trunover_year1, trunover_year2, trunover_year3, profit_year1;
        String profit_year2, profit_year3, machine, sell_products, last_quarter,enquiryId,total_Employee,Application_Slno,Which_Machine;

        for(int s=0;s<str_addenquirys.size();s++) {
            //    Log.e("tag","BusinessName="+str_addenquirys.get(s).getBusinessName()+"Fname="+str_addenquirys.get(s).getFName());

            manufacture=str_addenquirys.get(s).getManufacture();
            licence=str_addenquirys.get(s).getLicence();
            whichLicence=str_addenquirys.get(s).getWhichLicence();
            productOne=str_addenquirys.get(s).getProductOne();
            productTwo=str_addenquirys.get(s).getProductTwo();
            productThree=str_addenquirys.get(s).getProductThree();
            businessYear=str_addenquirys.get(s).getBusinessYear();
            total_Employee=str_addenquirys.get(s).getTotal_Employee();
            ownership=str_addenquirys.get(s).getOwnership();
            yearOne=str_addenquirys.get(s).getYearOne();
            female_year1=str_addenquirys.get(s).getFemale_year1();
            male_year1=str_addenquirys.get(s).getMale_year1();
            yearTwo=str_addenquirys.get(s).getYearTwo();
            female_year2=str_addenquirys.get(s).getFemale_year2();
            male_year2=str_addenquirys.get(s).getMale_year2();
            yearThree = str_addenquirys.get(s).getYearThree();
            female_year3 = str_addenquirys.get(s).getFemale_year3();
            male_year3 = str_addenquirys.get(s).getMale_year3();
            labour = str_addenquirys.get(s).getLabour();
            outsidefamilyLabour = str_addenquirys.get(s).getOutsidefamilyLabour();
            trunover_year1 = str_addenquirys.get(s).getTrunover_year1();
            trunover_year2 = str_addenquirys.get(s).getTrunover_year2();
            trunover_year3 = str_addenquirys.get(s).getTrunover_year3();
            profit_year1 = str_addenquirys.get(s).getProfit_year1();
            profit_year2 = str_addenquirys.get(s).getProfit_year2();
            profit_year3 = str_addenquirys.get(s).getProfit_year3();
            machine = str_addenquirys.get(s).getMachine();
            sell_products =str_addenquirys.get(s).getSell_products();
            last_quarter=str_addenquirys.get(s).getLast_quarter();
            enquiryId = str_addenquirys.get(s).getEnquiryId();
            Application_Slno = str_addenquirys.get(s).getApplication_Slno();
            Which_Machine=str_addenquirys.get(s).getWhichmachine();
          /*  Log.e("tag","Enqury No="+EnquiryId);
            Log.e("tag","talukId="+talukId);*/

           /* SQLiteDatabase db1 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

            db1.execSQL("CREATE TABLE IF NOT EXISTS AddApplicationDetails(FName VARCHAR,MName VARCHAR,LName VARCHAR,MobileNo VARCHAR," +
                    "EmailId VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,BusinessName VARCHAR," +
                    "DeviceType VARCHAR,UserId VARCHAR,EnquiryId VARCHAR,Gender VARCHAR,Economic VARCHAR,whatsappNo VARCHAR,DOB VARCHAR,education VARCHAR, " +
                    "socialCatgary VARCHAR, businessAddress VARCHAR,yearInBusiness VARCHAR,yearInCurrentBusiness VARCHAR,sectorBusiness VARCHAR," +
                    "gottoknow VARCHAR,UNDP VARCHAR,yearUNDP VARCHAR,navodyami VARCHAR,yearNavodyami VARCHAR);");*/

            Cursor cursor1 = db_addApplicationtwo.rawQuery("SELECT DISTINCT * FROM AddApplicationDetailsTwo WHERE EnquiryId ='"+enquiryId+"'", null);
            int x = cursor1.getCount();
            Log.d("tag","cursor Application Count"+ Integer.toString(x));
            if(x==0) {
                String SQLiteQuery = "INSERT INTO AddApplicationDetailsTwo (Manufacture, Licence,WhichLicence,ProductOne,ProductTwo,ProductThree,BusinessYear,Ownership," +
                        "YearOne,Female_year1,Male_year1,YearTwo,Female_year2,Male_year2,YearThree,Female_year3,Male_year3,Labour,OutsidefamilyLabour, Trunover_year1, Trunover_year2," +
                        "Trunover_year3,Profit_year1,Profit_year2,Profit_year3,Which_Machine,Machine,Sell_products,Last_quarter,EnquiryId,Total_Employee,Application_Slno)" +
                        " VALUES ('" + manufacture + "','" + licence + "','" + whichLicence + "','" + productOne + "','" + productTwo + "','" + productThree + "','" +
                        businessYear + "','" + ownership + "','" + yearOne + "','" + female_year1 + "','" + male_year1 + "','" + yearTwo + "','" +
                        female_year2 + "','" + male_year2 + "','" + yearThree + "','" + female_year3 + "','" + male_year3 + "','" + labour + "','" + outsidefamilyLabour + "','" +
                        trunover_year1 + "','" + trunover_year2 + "','" + trunover_year3 + "','" + profit_year1 + "','" +
                        profit_year2 + "','" + profit_year3 +"','" +Which_Machine+ "','" + machine + "','" + sell_products + "','" + last_quarter+ "','" + enquiryId + "','"+ total_Employee+"','"+ Application_Slno+"');";
                db_addApplicationtwo.execSQL(SQLiteQuery);
                //   db_addEnquiry.close();

            }else{
               /* String strSQL = "UPDATE AddApplicationDetails SET FName = FName,MName=MName,LName=LName,MobileNo=MobileNo,EmailId=EmailId,StateId=stateId,DistrictId=districtId,TalukaId=talukId,VillageId=villegeId,SectorId=sectorId,BusinessName=BusinessName,DeviceType=deviceType,UserId=userId,EnquiryId=EnquiryId,Gender=Gender,Economic=Economic,whatsappNo=whatsappNo,DOB=DOB,education=education, socialCatgary=socialCatgary, businessAddress=businessAddress,yearInBusiness=yearInBusiness,yearInCurrentBusiness=yearInCurrentBusiness,sectorBusiness=sectorBusiness,gottoknow=gottoknow,UNDP=UNDP,yearUNDP=yearUNDP,navodyami=navodyami,yearNavodyami=yearNavodyami WHERE EnquiryId = "+ EnquiryId;
                db_addEnquiry.execSQL(strSQL);*/

              /*  String fname=FName;
                String str_talukid=talukId;
                String str_villageId="570686";*/
                /*String strSQL = "UPDATE AddApplicationDetailsNew SET FName=fname, MName=MName,LName=LName,MobileNo=MobileNo,EmailId=EmailId,StateId=stateId," +
                        "DistrictId=districtId,TalukaId='4357',VillageId=str_villageId,SectorId=sectorId,BusinessName=BusinessName,DeviceType=deviceType," +
                        "UserId=userId,EnquiryId=EnquiryId,Gender=Gender,Economic=Economic,whatsappNo=whatsappNo,DOB=DOB,education=education, " +
                        "socialCatgary=socialCatgary, businessAddress=businessAddress,yearInBusiness=yearInBusiness," +
                        "yearInCurrentBusiness=yearInCurrentBusiness,sectorBusiness=sectorBusiness,gottoknow=gottoknow,UNDP=UNDP,yearUNDP=yearUNDP," +
                        "navodyami=navodyami,yearNavodyami = yearNavodyami WHERE EnquiryId = "+ EnquiryId;

                db_addEnquiry.execSQL(strSQL); */

                ContentValues cv = new ContentValues();
                cv.put("Manufacture",manufacture);
                cv.put("Licence",licence);
                cv.put("WhichLicence",whichLicence);
                cv.put("ProductOne",productOne);
                cv.put("ProductTwo",productTwo);
                cv.put("ProductThree",productThree);
                cv.put("BusinessYear",businessYear);
                cv.put("Ownership",ownership);
                cv.put("YearOne",yearOne);
                cv.put("Female_year1",female_year1);
                cv.put("Male_year1",male_year1);
                cv.put("YearTwo",yearTwo);
                cv.put("Female_year2",female_year2);
                cv.put("Male_year2",male_year2);
                cv.put("YearThree",yearThree);
                cv.put("Female_year3",female_year3);
                cv.put("Male_year3",male_year3);
                cv.put("Labour",labour);
                cv.put("OutsidefamilyLabour",outsidefamilyLabour);
                cv.put("Trunover_year1",trunover_year1);
                cv.put("Trunover_year2",trunover_year2);
                cv.put("Trunover_year3",trunover_year3);
                cv.put("Profit_year1",profit_year1);
                cv.put("Profit_year2",profit_year2);
                cv.put("Profit_year3",profit_year3);
                cv.put("Which_Machine",Which_Machine);
                cv.put("Machine",machine);
                cv.put("Sell_products",sell_products);
                cv.put("Last_quarter",last_quarter);
                cv.put("EnquiryId",enquiryId);
                cv.put("Total_Employee",total_Employee);
                cv.put("Application_Slno",Application_Slno);

                db_addApplicationtwo.update("AddApplicationDetailsTwo", cv, "EnquiryId = ?", new String[]{EnquiryId});
                //     db_addEnquiry.close();

            }
        }

        // ApplicationCount();


        db_addApplicationtwo.close();
    }
    public void DBCreate_AddCompliteApplicationTwoDetails_insert_2SQLiteDB(ArrayList<Class_AddApplicationTwoDetails> str_addenquirys)
    {
        SQLiteDatabase db_addApplicationtwo = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

        db_addApplicationtwo.execSQL("CREATE TABLE IF NOT EXISTS CompliteApplicationDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,EnquiryId VARCHAR,Application_Slno VARCHAR,Academic_Year VARCHAR,dataSyncStatus VARCHAR,FName VARCHAR,MName VARCHAR,LName VARCHAR,MobileNo VARCHAR," +
                "EmailId VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,BusinessName VARCHAR," +
                "DeviceType VARCHAR,UserId VARCHAR,Gender VARCHAR,Economic VARCHAR,whatsappNo VARCHAR,DOB VARCHAR,education VARCHAR, " +
                "socialCatgary VARCHAR, businessAddress VARCHAR,yearInBusiness VARCHAR,yearInCurrentBusiness VARCHAR,sectorBusiness VARCHAR," +
                "gottoknow VARCHAR,UNDP VARCHAR,yearUNDP VARCHAR,navodyami VARCHAR,yearNavodyami VARCHAR,aadhar VARCHAR,street VARCHAR,Application_Date VARCHAR," +
                "Manufacture VARCHAR,Licence VARCHAR,WhichLicence VARCHAR,ProductOne VARCHAR," +
                "ProductTwo VARCHAR,ProductThree VARCHAR,BusinessYear VARCHAR,Ownership VARCHAR,YearOne VARCHAR,Female_year1 VARCHAR,Male_year1 VARCHAR," +
                "YearTwo VARCHAR,Female_year2 VARCHAR,Male_year2 VARCHAR,YearThree VARCHAR,Female_year3 VARCHAR,Male_year3 VARCHAR,Labour VARCHAR,OutsidefamilyLabour VARCHAR," +
                "Trunover_year1 VARCHAR,Trunover_year2 VARCHAR,Trunover_year3 VARCHAR,Profit_year1 VARCHAR,Profit_year2 VARCHAR,Profit_year3 VARCHAR,Which_Machine VARCHAR,Machine VARCHAR," +
                "Sell_products VARCHAR,Last_quarter VARCHAR,Total_Employee VARCHAR," +
                "BusinessSource VARCHAR,LastLoan VARCHAR,Investment VARCHAR,Knowledge VARCHAR,AppliedAmt VARCHAR,SanctionedAmt VARCHAR,InterestRate VARCHAR," +
                "AppliedAt VARCHAR,Repaymentperiod VARCHAR,ApplicationFees VARCHAR,VerifiedDate VARCHAR,Remark VARCHAR,Manual_Receipt_No VARCHAR,Payment_Mode VARCHAR,IsAccountVerified VARCHAR);");

        String manufacture, licence, whichLicence, productOne, productTwo, productThree, businessYear, ownership;
        String yearOne, female_year1, male_year1, yearTwo, female_year2, male_year2, yearThree, female_year3;
        String male_year3, labour, outsidefamilyLabour, trunover_year1, trunover_year2, trunover_year3, profit_year1;
        String profit_year2, profit_year3, machine, sell_products, last_quarter,enquiryId,total_Employee,Application_Slno,Which_Machine;

        for(int s=0;s<str_addenquirys.size();s++) {
            //    Log.e("tag","BusinessName="+str_addenquirys.get(s).getBusinessName()+"Fname="+str_addenquirys.get(s).getFName());

            manufacture=str_addenquirys.get(s).getManufacture();
            licence=str_addenquirys.get(s).getLicence();
            whichLicence=str_addenquirys.get(s).getWhichLicence();
            productOne=str_addenquirys.get(s).getProductOne();
            productTwo=str_addenquirys.get(s).getProductTwo();
            productThree=str_addenquirys.get(s).getProductThree();
            businessYear=str_addenquirys.get(s).getBusinessYear();
            total_Employee=str_addenquirys.get(s).getTotal_Employee();
            ownership=str_addenquirys.get(s).getOwnership();
            yearOne=str_addenquirys.get(s).getYearOne();
            female_year1=str_addenquirys.get(s).getFemale_year1();
            male_year1=str_addenquirys.get(s).getMale_year1();
            yearTwo=str_addenquirys.get(s).getYearTwo();
            female_year2=str_addenquirys.get(s).getFemale_year2();
            male_year2=str_addenquirys.get(s).getMale_year2();
            yearThree = str_addenquirys.get(s).getYearThree();
            female_year3 = str_addenquirys.get(s).getFemale_year3();
            male_year3 = str_addenquirys.get(s).getMale_year3();
            labour = str_addenquirys.get(s).getLabour();
            outsidefamilyLabour = str_addenquirys.get(s).getOutsidefamilyLabour();
            trunover_year1 = str_addenquirys.get(s).getTrunover_year1();
            trunover_year2 = str_addenquirys.get(s).getTrunover_year2();
            trunover_year3 = str_addenquirys.get(s).getTrunover_year3();
            profit_year1 = str_addenquirys.get(s).getProfit_year1();
            profit_year2 = str_addenquirys.get(s).getProfit_year2();
            profit_year3 = str_addenquirys.get(s).getProfit_year3();
            machine = str_addenquirys.get(s).getMachine();
            sell_products =str_addenquirys.get(s).getSell_products();
            last_quarter=str_addenquirys.get(s).getLast_quarter();
            enquiryId = str_addenquirys.get(s).getEnquiryId();
            Application_Slno = str_addenquirys.get(s).getApplication_Slno();
            Which_Machine=str_addenquirys.get(s).getWhichmachine();

            Cursor cursor = db_addApplicationtwo.rawQuery("SELECT DISTINCT * FROM CompliteApplicationDetails WHERE tempId='"+tempId+"'", null);
            int x = cursor.getCount();
            Log.d("tag","cursor Applicationtwo Count"+ Integer.toString(x));
            if(x!=0) {

                ContentValues cv = new ContentValues();
                cv.put("Manufacture",manufacture);
                cv.put("Licence",licence);
                cv.put("WhichLicence",whichLicence);
                cv.put("ProductOne",productOne);
                cv.put("ProductTwo",productTwo);
                cv.put("ProductThree",productThree);
                cv.put("BusinessYear",businessYear);
                cv.put("Ownership",ownership);
                cv.put("YearOne",yearOne);
                cv.put("Female_year1",female_year1);
                cv.put("Male_year1",male_year1);
                cv.put("YearTwo",yearTwo);
                cv.put("Female_year2",female_year2);
                cv.put("Male_year2",male_year2);
                cv.put("YearThree",yearThree);
                cv.put("Female_year3",female_year3);
                cv.put("Male_year3",male_year3);
                cv.put("Labour",labour);
                cv.put("OutsidefamilyLabour",outsidefamilyLabour);
                cv.put("Trunover_year1",trunover_year1);
                cv.put("Trunover_year2",trunover_year2);
                cv.put("Trunover_year3",trunover_year3);
                cv.put("Profit_year1",profit_year1);
                cv.put("Profit_year2",profit_year2);
                cv.put("Profit_year3",profit_year3);
                cv.put("Which_Machine",Which_Machine);
                cv.put("Machine",machine);
                cv.put("Sell_products",sell_products);
                cv.put("Last_quarter",last_quarter);
                cv.put("EnquiryId",enquiryId);
                cv.put("Total_Employee",total_Employee);
                cv.put("Application_Slno",Application_Slno);

                db_addApplicationtwo.update("CompliteApplicationDetails", cv, "tempId = ?", new String[]{tempId});
                //     db_addEnquiry.close();

            }
        }

        // ApplicationCount();


        db_addApplicationtwo.close();
    }
    public String removeLastChar(String s) {
        if (s == null || s.length() == 0) { return s; } return s.substring(0, s.length()-1); }

  /*  public void uploadfromDB_AddApplicationTwoDetails() {

        SQLiteDatabase db_addApplicationtwo = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

        db_addApplicationtwo.execSQL("CREATE TABLE IF NOT EXISTS AddApplicationDetailsTwo(Manufacture VARCHAR,Licence VARCHAR,WhichLicence VARCHAR,ProductOne VARCHAR," +
                "ProductTwo VARCHAR,ProductThree VARCHAR,BusinessYear VARCHAR,Ownership VARCHAR,YearOne VARCHAR,Female_year1 VARCHAR,Male_year1 VARCHAR," +
                "YearTwo VARCHAR,Female_year2 VARCHAR,Male_year2 VARCHAR,YearThree VARCHAR,Female_year3 VARCHAR,Male_year3 VARCHAR,Labour VARCHAR,OutsidefamilyLabour VARCHAR, " +
                "Trunover_year1 VARCHAR,Trunover_year2 VARCHAR,Trunover_year3 VARCHAR,Profit_year1 VARCHAR,Profit_year2 VARCHAR,Profit_year3 VARCHAR,Which_Machine VARCHAR,Machine VARCHAR,Sell_products VARCHAR," +
                "Last_quarter VARCHAR,EnquiryId VARCHAR,Total_Employee VARCHAR,Application_Slno VARCHAR);");

        Cursor cursor = db_addApplicationtwo.rawQuery("SELECT DISTINCT * FROM AddApplicationDetailsTwo WHERE EnquiryId ='"+EnquiryId+ "' AND Application_Slno='"+ApplicationSlnoNew+"'", null);
        int x = cursor.getCount();
        Log.d("tag","cursor count AddApplicationDetailsTwo"+ Integer.toString(x));
        // clear_fields();
        int i = 0;
        arrayObj_Class_ApplicationDetailsTwo = new Class_AddApplicationTwoDetails[x];
        String manufacture, licence, whichLicence, productOne, productTwo, productThree, businessYear, ownership;
        String str_yearOne = null, female_year1, male_year1, str_yearTwo = null, female_year2, male_year2, str_yearThree=null, female_year3;
        String male_year3, labour, outsidefamilyLabour, trunover_year1, trunover_year2, trunover_year3, profit_year1;
        String profit_year2, profit_year3, machine, sell_products, last_quarter,enquiryId,total_Employee,Application_Slno,Which_Machine;
        if (cursor.moveToFirst()) {

            do {
                manufacture= cursor.getString(cursor.getColumnIndex("Manufacture"));
                Log.e("tag","manufacture get in two="+manufacture);
                if(manufacture.equals("1"))
                {
                    manufacturer_radiogroup.check(R.id.rdb_manufacture);
                    int_manufacture="1";
                }
                else
                { manufacturer_radiogroup.check(R.id.rdb_trader);
                    int_manufacture="0";
                }
                licence=cursor.getString(cursor.getColumnIndex("Licence"));
                if(licence.equals("1"))
                {
                    license_radiogroup.check(R.id.rdb_licenseyes);
                    int_licence="1";
                    edt_license.setVisibility(View.VISIBLE);
                }
                else
                { license_radiogroup.check(R.id.rdb_licenseno);
                    int_licence="0";
                    edt_license.setVisibility(View.GONE);
                }
                whichLicence=cursor.getString(cursor.getColumnIndex("WhichLicence"));
                Log.e("tag","whichLicence="+whichLicence);
                edt_license.setText(whichLicence);
                productOne=cursor.getString(cursor.getColumnIndex("ProductOne"));
                if(productOne!=null) {
                    edt_product1.setText(productOne);
                }
                productTwo=cursor.getString(cursor.getColumnIndex("ProductTwo"));
                if(productTwo!=null) {
                    edt_product2.setText(productTwo);
                }
                productThree=cursor.getString(cursor.getColumnIndex("ProductThree"));
                if(productThree!=null) {
                    edt_product3.setText(productThree);
                }
                businessYear=cursor.getString(cursor.getColumnIndex("BusinessYear"));
                edt_BusinessYear.setText(businessYear);
                ownership=cursor.getString(cursor.getColumnIndex("Ownership"));
                //   Ownership_sp.setSelection(Integer.parseInt(ownership.toString()));
                if (ownership != null) {
                    int spinnerPosition = dataAdapter_Ownership.getPosition(ownership);
                    Ownership_sp.setSelection(spinnerPosition);
                }

                str_yearOne=cursor.getString(cursor.getColumnIndex("YearOne"));
                yearOne.setText(str_yearOne);
                female_year1=cursor.getString(cursor.getColumnIndex("Female_year1"));
                edt_female_year1.setText(female_year1);
                male_year1=cursor.getString(cursor.getColumnIndex("Male_year1"));
                edt_male_year1.setText(male_year1);
                str_yearTwo=cursor.getString(cursor.getColumnIndex("YearTwo"));
                yearTwo.setText(str_yearTwo);
                female_year2=cursor.getString(cursor.getColumnIndex("Female_year2"));
                edt_female_year2.setText(female_year2);
                male_year2=cursor.getString(cursor.getColumnIndex("Male_year2"));
                edt_male_year2.setText(male_year2);
                str_yearThree=cursor.getString(cursor.getColumnIndex("YearThree"));
                yearThree.setText(str_yearThree);
                male_year3=cursor.getString(cursor.getColumnIndex("Male_year3"));
                edt_male_year3.setText(male_year3);
                female_year3=cursor.getString(cursor.getColumnIndex("Female_year3"));
                edt_female_year3.setText(female_year3);
                labour=cursor.getString(cursor.getColumnIndex("Labour"));
                *//*if(str_yearOne==null||str_yearTwo==null||str_yearThree==null){
                    //  if(str_yearOne.equalsIgnoreCase(null)||str_yearTwo.equalsIgnoreCase("null")||str_yearThree.equalsIgnoreCase("null")) {
                    uploadfromDB_Yearlist();
                    //  }
                }*//*
                if(labour.equals("1"))
                {
                    labour_radiogroup.check(R.id.rdb_Skilled);
                    int_Labour="1";
                }
                else
                { labour_radiogroup.check(R.id.rdb_Unskilled);
                    int_Labour="0";
                }
                outsidefamilyLabour=cursor.getString(cursor.getColumnIndex("OutsidefamilyLabour"));
                if(outsidefamilyLabour.equals("1"))
                {
                    outsidefamily_radiogroup.check(R.id.rdb_outsidefamilyYes);
                    int_outsidefamily="1";
                }
                else
                { outsidefamily_radiogroup.check(R.id.rdb_outsidefamilyNo);
                    int_outsidefamily="0";
                }
                trunover_year1=cursor.getString(cursor.getColumnIndex("Trunover_year1"));
                edt_trunover_year1.setText(trunover_year1);
                yearOne_trunover.setText(str_yearOne);
                trunover_year2=cursor.getString(cursor.getColumnIndex("Trunover_year2"));
                yearTwo_trunover.setText(str_yearTwo);
                edt_trunover_year2.setText(trunover_year2);
                trunover_year3=cursor.getString(cursor.getColumnIndex("Trunover_year3"));
                yearThree_trunover.setText(str_yearThree);
                edt_trunover_year3.setText(trunover_year3);
                profit_year1=cursor.getString(cursor.getColumnIndex("Profit_year1"));
                edt_Profit_year1.setText(profit_year1);
                yearOne_Profit.setText(str_yearOne);
                profit_year2=cursor.getString(cursor.getColumnIndex("Profit_year2"));
                edt_Profit_year2.setText(profit_year2);
                yearTwo_Profit.setText(str_yearTwo);
                profit_year3=cursor.getString(cursor.getColumnIndex("Profit_year3"));
                edt_Profit_year3.setText(profit_year3);
                yearThree_Profit.setText(str_yearThree);
                Which_Machine = cursor.getString(cursor.getColumnIndex("Which_Machine"));
                edt_machine.setText(Which_Machine);
                machine=cursor.getString(cursor.getColumnIndex("Machine"));
                if(machine.equals("1"))
                {
                    machine_radiogroup.check(R.id.rdb_ownmachine);
                    int_machine="1";
                }
                else
                { machine_radiogroup.check(R.id.rdb_rentmachine);
                    int_machine="0";
                }
                sell_products=cursor.getString(cursor.getColumnIndex("Sell_products"));
                Log.e("tag","Sell_products="+sell_products);
                //c1=sell_products;
                Where_Sell_Products.clear();
                if(sell_products!=null) {
                    Where_Sell_Products = Arrays.asList(sell_products.split(","));
                    Log.e("tag", "Where_Sell_Products=" + Where_Sell_Products);
                    Log.e("tag", "Where_Sell_Products=" + Where_Sell_Products.get(0));
                    setcheckboxvalues();
                }
                last_quarter=cursor.getString(cursor.getColumnIndex("Last_quarter"));
                Log.e("tag","last_quarter="+last_quarter);
                Earn_Most_Channel.clear();
                if(last_quarter!=null) {
                    Earn_Most_Channel = Arrays.asList(last_quarter.split(","));
                    Log.e("tag", "Earn_Most_Channel=" + Earn_Most_Channel);
                    setcheckboxvaluesLast();
                }
                enquiryId=cursor.getString(cursor.getColumnIndex("EnquiryId"));
                total_Employee=cursor.getString(cursor.getColumnIndex("Total_Employee"));
                edt_Total_Employee.setText(total_Employee);
                Application_Slno=cursor.getString(cursor.getColumnIndex("Application_Slno"));
                if(str_yearOne==null||str_yearTwo==null||str_yearThree==null){
                    //  if(str_yearOne.equalsIgnoreCase(null)||str_yearTwo.equalsIgnoreCase("null")||str_yearThree.equalsIgnoreCase("null")) {
                    uploadfromDB_Yearlist();
                    //  }
                }

            } while (cursor.moveToNext());


        }//if ends

        db_addApplicationtwo.close();
    }*/
  public void uploadfromDB_OwnershipList() {

      SQLiteDatabase db_Ownership = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
      db_Ownership.execSQL("CREATE TABLE IF NOT EXISTS OwnershipList(OwnershipId VARCHAR,OwnershipName VARCHAR);");
      Cursor cursor = db_Ownership.rawQuery("SELECT DISTINCT * FROM OwnershipList", null);
      int x = cursor.getCount();
      Log.d("tag","cursor count Ownership"+ Integer.toString(x));

      int i = 0;
      arrayObj_Class_OwnershipDetails2 = new Class_OwnershipListDetails[x];
      if (cursor.moveToFirst()) {

          do {
              Class_OwnershipListDetails innerObj = new Class_OwnershipListDetails();
              innerObj.setId(cursor.getString(cursor.getColumnIndex("OwnershipId")));
              innerObj.setName(cursor.getString(cursor.getColumnIndex("OwnershipName")));

              arrayObj_Class_OwnershipDetails2[i] = innerObj;
              Log.e("tag","arrayObj_Class_OwnershipDetails2[]="+arrayObj_Class_OwnershipDetails2[i].toString());
              listOwner.add(cursor.getString(cursor.getColumnIndex("OwnershipName")));
              i++;

          } while (cursor.moveToNext());


      }//if ends

      db_Ownership.close();
      //setKnowSpinner();
      /*     if (x > 0) {

       *//*  for(int i1=0;i1<=arrayObj_Class_NavodyamiDetails2.length;i1++){
              listKnow.add(arrayObj_Class_NavodyamiDetails2[i1].getName());
           }*//*
            Log.e("tag","arrayObj_Class_NavodyamiDetails2="+arrayObj_Class_NavodyamiDetails2.toString());
           Log.e("tag","listKnow="+listKnow.toString());
            dataAdapter_gotknow = new ArrayAdapter(this, R.layout.spinnercustomstyle, arrayObj_Class_NavodyamiDetails2);
            dataAdapter_gotknow.setDropDownViewResource(R.layout.spinnercustomstyle);
            // attaching data adapter to spinner
            gotknow_sp.setAdapter(dataAdapter_gotknow);

          //  sel_gottoknow=gotknow_sp.getSelectedItemPosition();

            if(x>sel_gottoknow) {
                gotknow_sp.setSelection(sel_gottoknow);
            }
        }*/

  }

    public void uploadfromDB_CompliteApplicationTwoDetails(){
        SQLiteDatabase db_addApplicationtwo = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

        db_addApplicationtwo.execSQL("CREATE TABLE IF NOT EXISTS CompliteApplicationDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,EnquiryId VARCHAR,Application_Slno VARCHAR,Academic_Year VARCHAR,dataSyncStatus VARCHAR,FName VARCHAR,MName VARCHAR,LName VARCHAR,MobileNo VARCHAR," +
                "EmailId VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,BusinessName VARCHAR," +
                "DeviceType VARCHAR,UserId VARCHAR,Gender VARCHAR,Economic VARCHAR,whatsappNo VARCHAR,DOB VARCHAR,education VARCHAR, " +
                "socialCatgary VARCHAR, businessAddress VARCHAR,yearInBusiness VARCHAR,yearInCurrentBusiness VARCHAR,sectorBusiness VARCHAR," +
                "gottoknow VARCHAR,UNDP VARCHAR,yearUNDP VARCHAR,navodyami VARCHAR,yearNavodyami VARCHAR,aadhar VARCHAR,street VARCHAR,Application_Date VARCHAR," +
                "Manufacture VARCHAR,Licence VARCHAR,WhichLicence VARCHAR,ProductOne VARCHAR," +
                "ProductTwo VARCHAR,ProductThree VARCHAR,BusinessYear VARCHAR,Ownership VARCHAR,YearOne VARCHAR,Female_year1 VARCHAR,Male_year1 VARCHAR," +
                "YearTwo VARCHAR,Female_year2 VARCHAR,Male_year2 VARCHAR,YearThree VARCHAR,Female_year3 VARCHAR,Male_year3 VARCHAR,Labour VARCHAR,OutsidefamilyLabour VARCHAR," +
                "Trunover_year1 VARCHAR,Trunover_year2 VARCHAR,Trunover_year3 VARCHAR,Profit_year1 VARCHAR,Profit_year2 VARCHAR,Profit_year3 VARCHAR,Which_Machine VARCHAR,Machine VARCHAR," +
                "Sell_products VARCHAR,Last_quarter VARCHAR,Total_Employee VARCHAR," +
                "BusinessSource VARCHAR,LastLoan VARCHAR,Investment VARCHAR,Knowledge VARCHAR,AppliedAmt VARCHAR,SanctionedAmt VARCHAR,InterestRate VARCHAR," +
                "AppliedAt VARCHAR,Repaymentperiod VARCHAR,ApplicationFees VARCHAR,VerifiedDate VARCHAR,Remark VARCHAR,Manual_Receipt_No VARCHAR,Payment_Mode VARCHAR,IsAccountVerified VARCHAR);");

        Cursor cursor = db_addApplicationtwo.rawQuery("SELECT DISTINCT * FROM CompliteApplicationDetails WHERE EnquiryId ='"+EnquiryId+"' AND tempId='"+tempId+"'", null);
        int x = cursor.getCount();
        Log.e("tag","Business get details count="+x);
        String manufacture, licence, whichLicence, productOne, productTwo, productThree, businessYear, ownership;
        String str_yearOne = null, female_year1, male_year1, str_yearTwo = null, female_year2, male_year2, str_yearThree=null, female_year3;
        String male_year3, labour, outsidefamilyLabour, trunover_year1, trunover_year2, trunover_year3, profit_year1;
        String profit_year2, profit_year3, machine, sell_products, last_quarter,enquiryId,total_Employee,Application_Slno,Which_Machine;

        if (cursor.moveToFirst()) {

            do {
                manufacture = cursor.getString(cursor.getColumnIndex("Manufacture"));
                if (manufacture.equals("1")) {
                    manufacturer_radiogroup.check(R.id.rdb_manufacture);
                    int_manufacture = "1";
                } else {
                    manufacturer_radiogroup.check(R.id.rdb_trader);
                    int_manufacture = "0";
                }
                licence = cursor.getString(cursor.getColumnIndex("Licence"));
                if (licence.equals("1")) {
                    license_radiogroup.check(R.id.rdb_licenseyes);
                    int_licence = "1";
                    edt_license.setVisibility(View.VISIBLE);
                } else {
                    license_radiogroup.check(R.id.rdb_licenseno);
                    int_licence = "0";
                    edt_license.setVisibility(View.GONE);
                }
                whichLicence = cursor.getString(cursor.getColumnIndex("WhichLicence"));
                Log.e("tag","WhichLicence=="+whichLicence);
                if(whichLicence==null||whichLicence.equalsIgnoreCase("")){
                    edt_license.setText("");
                }else{
                    edt_license.setText(whichLicence);
                }

                productOne = cursor.getString(cursor.getColumnIndex("ProductOne"));
                if (productOne != null) {
                    edt_product1.setText(productOne);
                }
                productTwo = cursor.getString(cursor.getColumnIndex("ProductTwo"));
                if (productTwo != null) {
                    edt_product2.setText(productTwo);
                }
                productThree = cursor.getString(cursor.getColumnIndex("ProductThree"));
                if (productThree != null) {
                    edt_product3.setText(productThree);
                }
                businessYear = cursor.getString(cursor.getColumnIndex("BusinessYear"));
                edt_BusinessYear.setText(businessYear);
                ownership = cursor.getString(cursor.getColumnIndex("Ownership"));
                //   Ownership_sp.setSelection(Integer.parseInt(ownership.toString()));
                if (ownership != null) {
                    int spinnerPosition = dataAdapter_Ownership.getPosition(ownership);
                    Ownership_sp.setSelection(spinnerPosition);
                }

                str_yearOne = cursor.getString(cursor.getColumnIndex("YearOne"));
                yearOne.setText(str_yearOne);
                female_year1 = cursor.getString(cursor.getColumnIndex("Female_year1"));
                edt_female_year1.setText(female_year1);
                male_year1 = cursor.getString(cursor.getColumnIndex("Male_year1"));
                edt_male_year1.setText(male_year1);
                str_yearTwo = cursor.getString(cursor.getColumnIndex("YearTwo"));
                yearTwo.setText(str_yearTwo);
                female_year2 = cursor.getString(cursor.getColumnIndex("Female_year2"));
                edt_female_year2.setText(female_year2);
                male_year2 = cursor.getString(cursor.getColumnIndex("Male_year2"));
                edt_male_year2.setText(male_year2);
                str_yearThree = cursor.getString(cursor.getColumnIndex("YearThree"));
                yearThree.setText(str_yearThree);
                male_year3 = cursor.getString(cursor.getColumnIndex("Male_year3"));
                edt_male_year3.setText(male_year3);
                female_year3 = cursor.getString(cursor.getColumnIndex("Female_year3"));
                edt_female_year3.setText(female_year3);
                labour = cursor.getString(cursor.getColumnIndex("Labour"));
                /*if(str_yearOne==null||str_yearTwo==null||str_yearThree==null){
                    //  if(str_yearOne.equalsIgnoreCase(null)||str_yearTwo.equalsIgnoreCase("null")||str_yearThree.equalsIgnoreCase("null")) {
                    uploadfromDB_Yearlist();
                    //  }
                }*/
                if (labour.equals("1")) {
                    labour_radiogroup.check(R.id.rdb_Skilled);
                    int_Labour = "1";
                } else {
                    labour_radiogroup.check(R.id.rdb_Unskilled);
                    int_Labour = "0";
                }
                outsidefamilyLabour = cursor.getString(cursor.getColumnIndex("OutsidefamilyLabour"));
                if (outsidefamilyLabour.equals("1")) {
                    outsidefamily_radiogroup.check(R.id.rdb_outsidefamilyYes);
                    int_outsidefamily = "1";
                } else {
                    outsidefamily_radiogroup.check(R.id.rdb_outsidefamilyNo);
                    int_outsidefamily = "0";
                }
                trunover_year1 = cursor.getString(cursor.getColumnIndex("Trunover_year1"));
                edt_trunover_year1.setText(trunover_year1);
                yearOne_trunover.setText(str_yearOne);
                trunover_year2 = cursor.getString(cursor.getColumnIndex("Trunover_year2"));
                yearTwo_trunover.setText(str_yearTwo);
                edt_trunover_year2.setText(trunover_year2);
                trunover_year3 = cursor.getString(cursor.getColumnIndex("Trunover_year3"));
                yearThree_trunover.setText(str_yearThree);
                edt_trunover_year3.setText(trunover_year3);
                profit_year1 = cursor.getString(cursor.getColumnIndex("Profit_year1"));
                edt_Profit_year1.setText(profit_year1);
                yearOne_Profit.setText(str_yearOne);
                profit_year2 = cursor.getString(cursor.getColumnIndex("Profit_year2"));
                edt_Profit_year2.setText(profit_year2);
                yearTwo_Profit.setText(str_yearTwo);
                profit_year3 = cursor.getString(cursor.getColumnIndex("Profit_year3"));
                edt_Profit_year3.setText(profit_year3);
                yearThree_Profit.setText(str_yearThree);
                Which_Machine = cursor.getString(cursor.getColumnIndex("Which_Machine"));
               // edt_machine.setText(Which_Machine);
                if(Which_Machine==null||Which_Machine.equalsIgnoreCase("")){
                    edt_machine.setText("");
                }else{
                    edt_machine.setText(Which_Machine);
                }
                machine = cursor.getString(cursor.getColumnIndex("Machine"));
                if (machine.equals("1")) {
                    machine_radiogroup.check(R.id.rdb_ownmachine);
                    int_machine = "1";
                } else {
                    machine_radiogroup.check(R.id.rdb_rentmachine);
                    int_machine = "0";
                }
                sell_products = cursor.getString(cursor.getColumnIndex("Sell_products"));
                Log.e("tag", "Sell_products=" + sell_products);
                //c1=sell_products;
                Where_Sell_Products.clear();
                if (sell_products != null) {
                    Where_Sell_Products = Arrays.asList(sell_products.split(","));
                    Log.e("tag", "Where_Sell_Products=" + Where_Sell_Products);
                    Log.e("tag", "Where_Sell_Products=" + Where_Sell_Products.get(0));
                    setcheckboxvalues();
                }
                last_quarter = cursor.getString(cursor.getColumnIndex("Last_quarter"));
                Log.e("tag", "last_quarter=" + last_quarter);
                Earn_Most_Channel.clear();
                if (last_quarter != null) {
                    Earn_Most_Channel = Arrays.asList(last_quarter.split(","));
                    Log.e("tag", "Earn_Most_Channel=" + Earn_Most_Channel);
                    setcheckboxvaluesLast();
                }
                enquiryId = cursor.getString(cursor.getColumnIndex("EnquiryId"));
                total_Employee = cursor.getString(cursor.getColumnIndex("Total_Employee"));
                edt_Total_Employee.setText(total_Employee);
                Application_Slno = cursor.getString(cursor.getColumnIndex("Application_Slno"));
                if (str_yearOne == null || str_yearTwo == null || str_yearThree == null ||str_yearOne.equalsIgnoreCase("") || str_yearTwo.equalsIgnoreCase("") || str_yearThree.equalsIgnoreCase("")) {
                    //  if(str_yearOne.equalsIgnoreCase(null)||str_yearTwo.equalsIgnoreCase("null")||str_yearThree.equalsIgnoreCase("null")) {
                    uploadfromDB_Yearlist();
                    //  }
                }

            } while (cursor.moveToNext());
        }
    }
    private class AddApplicationTwoDetails extends AsyncTask<String, Void, Void>
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

        public AddApplicationTwoDetails(AddApplicationTwoActivity activity) {
            context = activity;
            dialog = new ProgressDialog(context);
        }
        @Override
        protected Void doInBackground(String... params)
        {
            Log.i("TAG", "doInBackground");
            Submit();
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            Log.i("TAG", "onPostExecute");

            Log.d("ServiceResponseisss: ",status.toString());

            dialog.dismiss();


            if(status.equalsIgnoreCase("success")) {
                Toast toast= Toast.makeText(AddApplicationTwoActivity.this, " Application Updated Successfully " , Toast.LENGTH_LONG);
                // Toast toast=   Toast.makeText(YOUR ACTIVITY NAME ,Toast.LENGTH_SHORT);
                View view =toast.getView();
                view.setBackgroundColor( Color.parseColor("#009900")); //any color your want
                toast.show();
                Intent intent1 = new Intent(AddApplicationTwoActivity.this, AddApplicationThreeActivity.class);
                intent1.putExtra("EnquiryId",EnquiryId);
                intent1.putExtra("Application_SlnoNew",ApplicationSlnoNew);
                intent1.putExtra("isApplicant",isApplicant);
                intent1.putExtra("tempId",tempId);
                startActivity(intent1);
            }
            else{
               // Toast toast= Toast.makeText(AddApplicationTwoActivity.this, "  Updated Failed  " , Toast.LENGTH_LONG);
                Toast toast= Toast.makeText(context, "  "+status.toString()+"  " , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                toast.show();
            }
        }
    }

    public void Submit(){
        String URL = Class_URL.URL_Login.toString().trim();
        String METHOD_NAME = "Save_Application_Business_Details";
        String Namespace = "http://mis.navodyami.org/", SOAPACTION = "http://mis.navodyami.org/Save_Application_Business_Details";

        try {



            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

       /* ------   API Data  ---------
      <Save_Application_Business_Details xmlns="http://mis.navodyami.org/">
      <Enquiry_Id>string</Enquiry_Id>
      <Application_Slno>string</Application_Slno>
      <isManufacture>string</isManufacture>
      <isHave_License>string</isHave_License>
      <Which_License>string</Which_License>
      <Product1>string</Product1>
      <Product2>string</Product2>
      <Product3>string</Product3>
      <Business_Started_Year>string</Business_Started_Year>
      <Ownership>string</Ownership>
      <Which_Labour_Hire>string</Which_Labour_Hire>
      <IsHave_Labour_Outside_Family>string</IsHave_Labour_Outside_Family>
      <Which_Machine_Use>string</Which_Machine_Use>
      <Which_Machine_Have>string</Which_Machine_Have>
      <Where_Sell_Products>string</Where_Sell_Products>
      <Earn_Most_Channel>string</Earn_Most_Channel>
      <User_Id>string</User_Id>
      <Permanent_Employees>string</Permanent_Employees>
      <TurnOver>string</TurnOver>
      <Profit>string</Profit>
    </Save_Application_Business_Details>*/
       Log.e("tag","str_licence="+str_licence);

            request.addProperty("Enquiry_Id",Integer.valueOf(EnquiryId));
            request.addProperty("Application_Slno", Integer.valueOf(ApplicationSlnoNew));
            request.addProperty("isManufacture", Integer.valueOf(int_manufacture));
            request.addProperty("isHave_License", Integer.valueOf(int_licence));
            request.addProperty("Which_License", str_licence);
            request.addProperty("Product1", str_productOne);
            request.addProperty("Product2", str_productTwo);
            request.addProperty("Product3",str_productThree);
            request.addProperty("Business_Started_Year",Integer.valueOf(str_businessYear));
            request.addProperty("Ownership",str_ownership);
            request.addProperty("Which_Labour_Hire",Integer.valueOf(int_Labour));
            request.addProperty("IsHave_Labour_Outside_Family",Integer.valueOf(int_outsidefamily));
            request.addProperty("Which_Machine_Use",str_whichmachine);
            request.addProperty("Which_Machine_Have",Integer.valueOf(int_machine));
            request.addProperty("Where_Sell_Products",c1);
            request.addProperty("Earn_Most_Channel",c2Last);
            request.addProperty("User_Id",Integer.valueOf(str_UserId));
            request.addProperty("Total_Employees",Integer.valueOf(str_Total_Employee));

            JSONArray array=new JSONArray();
            ArrayList<String> json_arrayList=new ArrayList<>();
            String str_json = null,str_jsonfinal;
            str_jsonfinal="[";
            for(int i=0;i<listYears.size();i++){
                JSONObject obj=new JSONObject();
                try {
                    obj.put("Year",listYears.get(i));
                    obj.put("TotalMale",listMalePermentEmplyee.get(i));
                    obj.put("TotalFemale",listFemalePermentEmplyee.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                array.put(obj);
                if(str_json==null){
                    str_json =(obj.toString() + ",");
                }else {
                    str_json = str_json + (obj.toString() + ",");
                }
               /* if(i==2) {
                    str_json += obj.toString();
                }else{
                    str_json += obj.toString() + ",";
                }*/
                //json_arrayList.add(str_json.trim);
                // str_json += str_json+obj.toString()+",";
            }
            int lastCommaIndex = str_json.lastIndexOf(",");
            str_jsonfinal += str_json.substring(0, lastCommaIndex -0);

            // str_json.trim();
            // String str_jsonFinal=removeLastChar(str_json);
            str_jsonfinal+="]";


            Log.e("tag","PermentEmplyee json_arrayList=="+json_arrayList);
            Log.e("tag","PermentEmplyee array=="+array);
            Log.e("tag","PermentEmplyee str_jsonFinal=="+str_jsonfinal);
            request.addProperty("Permanent_Employees",str_jsonfinal);
            JSONObject jsonObjectTrunover = new JSONObject();
            for(int k=0;k<listYears.size() && k<listTurnOver.size();k++)
            {
                jsonObjectTrunover.put(listYears.get(k),listTurnOver.get(k));
            }
            Log.e("tag","jsonObjectTrunover="+jsonObjectTrunover);
            request.addProperty("TurnOver",jsonObjectTrunover.toString());

            JSONObject jsonObjectProfit = new JSONObject();
            for(int k=0;k<listYears.size() && k<listProfit.size();k++)
            {
                jsonObjectProfit.put(listYears.get(k),listProfit.get(k));
            }
            Log.e("tag","jsonObjectProfit="+jsonObjectProfit);
            request.addProperty("Profit",jsonObjectProfit.toString());


          /*  request.addProperty("Enquiry_Id",1);
            request.addProperty("Application_Slno", 1);
            request.addProperty("isManufacture", 1);
            request.addProperty("isHave_License", 1);
            request.addProperty("Which_License", "xcsdd");
            request.addProperty("Product1", "www1");
            request.addProperty("Product2", "rrr2");
            request.addProperty("Product3","test3");
            request.addProperty("Business_Started_Year",2018);
            request.addProperty("Ownership","Cooperative");
            request.addProperty("Which_Labour_Hire",1);
            request.addProperty("IsHave_Labour_Outside_Family",1);
            request.addProperty("Which_Machine_Use","test");
            request.addProperty("Which_Machine_Have",1);
            request.addProperty("Where_Sell_Products","Local,Retail Stores");
            request.addProperty("Earn_Most_Channel","Local,Retail Stores");
            request.addProperty("User_Id",1);
            request.addProperty("Total_Employees",40);*/
            /*request.addProperty("Permanent_Employees",array);
            request.addProperty("TurnOver",jsonObjectTrunover);
            request.addProperty("Profit",jsonObjectProfit);*/
            Log.e("tag","request two=="+request.toString());

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
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent1 = new Intent(AddApplicationTwoActivity.this, AddApplicationOneActivity.class);
        intent1.putExtra("EnquiryId",EnquiryId);
        intent1.putExtra("ApplicationSlno",ApplicationSlnoNew);
        intent1.putExtra("isApplicant",isApplicant);
        intent1.putExtra("tempId",tempId);
        startActivity(intent1);
    }
}
