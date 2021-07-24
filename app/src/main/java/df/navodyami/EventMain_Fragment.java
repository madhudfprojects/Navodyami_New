package df.navodyami;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import df.navodyami.adapter.CalendarAdapter;
import df.navodyami.util.UserInfo;

public class EventMain_Fragment extends Fragment {
    public static EventMain_Fragment newInstance() {
        EventMain_Fragment fragment = new EventMain_Fragment();
        return fragment;
    }

    public CalendarAdapter cal_adapter1;
    public GregorianCalendar cal_month, cal_month_copy;

    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;

    int date_int;
    int month_int;
    int year_int;
    String id, date;
    UserInfo[] eventListModulessarr;
    ArrayList<UserInfo> arrayList = new ArrayList<UserInfo>();

    String monthYear;
    String finalMonth,finalYear;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_event, container, false);

        Calendar now = Calendar.getInstance();
        //
        System.out.println("Current Year is : " + now.get(Calendar.YEAR));
        // month start from 0 to 11
        System.out.println("Current Month is : " + (now.get(Calendar.MONTH) + 1));
        System.out.println("Current Date is : " + now.get(Calendar.DATE));
        date_int = now.get(Calendar.DATE);
        month_int = now.get(Calendar.MONTH) + 1;
        year_int = now.get(Calendar.YEAR);

        monthYear = String.valueOf(month_int) + "/" + String.valueOf(year_int);


        int finalmonth = 0;
//monthYear=String.valueOf(month_int)+"/"+String.valueOf(year_int);
        finalmonth = now.get(Calendar.MONTH) + 1;

        if (finalmonth == 0) {
            monthYear = "0" + finalmonth + "/" + String.valueOf(year_int);
            finalMonth ="0" + finalmonth;
            finalYear = String.valueOf(year_int);
        } else if (finalmonth <= 9) {
            monthYear = "0" + finalmonth + "/" + String.valueOf(year_int);
            finalMonth ="0" + finalmonth;
            finalYear = String.valueOf(year_int);
        } else {
            monthYear = finalmonth + "/" + String.valueOf(year_int);
            finalMonth = String.valueOf(finalmonth);
            finalYear = String.valueOf(year_int);
        }
        AsyncCallWS2 task = new AsyncCallWS2(getContext());
        task.execute();
        return rootView;
    }


    private class AsyncCallWS2 extends AsyncTask<String, Void, Void>
    {
        ProgressDialog dialog;

        Context context;

        @Override
        protected void onPreExecute()
        {
            Log.i("tag", "onPreExecute---tab2");
            dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            Log.i("tag", "onProgressUpdate---tab2");
        }

        public AsyncCallWS2(Context activity) {
            context =  activity;
            dialog = new ProgressDialog(activity);
        }
        @Override
        protected Void doInBackground(String... params)
        {
            Log.i("tag", "doInBackground");


            fetch_all_info();
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            dialog.dismiss();
            Log.i("tag", "onPostExecute");
            //Fragment fragment = null;
            //fragment=new CalanderFragment();

            // startActivity(i);
            Calendar now = Calendar.getInstance();
            //
            date_int=now.get(Calendar.DATE);
            month_int=now.get(Calendar.MONTH);
            year_int=now.get(Calendar.YEAR);
            Intent intent = new Intent(context, CalendarView.class);
            intent.putExtra("date_int", String.valueOf(date_int));
            intent.putExtra("month_int", String.valueOf(month_int));
            intent.putExtra("year_int", String.valueOf(year_int));
            startActivity(intent);

            cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
            cal_month_copy = (GregorianCalendar) cal_month.clone();
            cal_adapter1 = new CalendarAdapter(getContext(), cal_month, UserInfo.user_info_arr);


         /* Date date = new Date();
            Log.i("Tag_time","date1="+date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String PresentDayStr=sdf.format(date);
            Log.i("Tag_time","PresentDayStr="+PresentDayStr);

            cal_adapter.getPositionList(PresentDayStr,HomeActivity.this);*/

            //      finish();
        }
    }

    public void fetch_all_info() {
        String METHOD_NAME = "GetEventList";
        String SOAP_ACTION1 = "http://mis.navodyami.org/GetEventList";

        try {

            SoapObject request = new SoapObject("http://mis.navodyami.org/", METHOD_NAME);

            // Log.i("id=", Emp_id);


            //   Log.i("tag","monthyear="+monthYear);
            request.addProperty("p_Month", Integer.parseInt(finalMonth));
            request.addProperty("p_Year", Integer.parseInt(finalYear));

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Login);
            //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

            try {
                androidHttpTransport.call(SOAP_ACTION1, envelope);
                Log.d("soap response eventlist", envelope.getResponse().toString());
                SoapObject response = (SoapObject) envelope.getResponse();
                Log.d("soap response event", response.toString());
                Log.d("soap getPropertyCount()", String.valueOf(response.getPropertyCount()));
                // for (SoapObject cs : result1)
                if (response.getPropertyCount() > 0) {
                    int Count = response.getPropertyCount();

                    String schedule_id, event_Description, event_TypeSlno, event_Type, event_Id, event_Name, event_Fees, total_Stalls, fromDate, toDate;
                    String Participants_Count=null,Incharge_Id=null,eventStatus, isEventClosed, eventClosed_Date = null, isChecklistCompleted, isExpenseUpdated, isAccountantVerified, verified_Date, user_Id=null, username = null, email_id, status,isAttendance_Taken = null,Attendance_Date;

                    for (int i = 0; i < Count; i++) {
                        // sizearray=result1.size();
                        //  Log.i("Tag","sizearray="+sizearray);
                        SoapObject O_eventClosed_Date;
                        SoapPrimitive S_eventClosed_Date;
                        SoapObject list = (SoapObject) response.getProperty(i);
                        status = list.getProperty("Status").toString();

                        //   String a = list.getProperty("notes").toString().trim();
                        if(status.equalsIgnoreCase("Success")) {
                            schedule_id = list.getProperty("schedule_id").toString();
                            event_Description = list.getProperty("Event_Description").toString();
                            event_TypeSlno = list.getProperty("Event_TypeSlno").toString();
                            event_Type = list.getProperty("Event_Type").toString();
                            event_Id = list.getProperty("Event_Id").toString();
                            event_Name = list.getProperty("Event_Name").toString();
                            event_Fees = list.getProperty("Event_Fees").toString();
                            total_Stalls = list.getProperty("Total_Stalls").toString();
                            fromDate = list.getProperty("FromDate").toString();

                            toDate = list.getProperty("ToDate").toString();
                            eventStatus = list.getProperty("EventStatus").toString();
                            isEventClosed = list.getProperty("isEventClosed").toString();
                        /*if(isEventClosed.equalsIgnoreCase("1")){
                            {
                                eventClosed_Date = list.getProperty("eventClosed_Date").toString();
                            }*/
                            isChecklistCompleted = list.getProperty("isChecklistCompleted").toString();
                            isExpenseUpdated = list.getProperty("isExpenseUpdated").toString();
                            isAccountantVerified = list.getProperty("isAccountantVerified").toString();
                            verified_Date = list.getProperty("Verified_Date").toString();
                            user_Id = list.getProperty("User_Id").toString();
                            username = list.getProperty("username").toString();
                            email_id = list.getProperty("email_id").toString();
                            isAttendance_Taken = list.getProperty("isAttendance_Taken").toString();
                            Attendance_Date = list.getProperty("Attendance_Date").toString();
                            eventClosed_Date = list.getProperty("EventClosed_Date").toString();
                            Participants_Count = list.getProperty("Participants_Count").toString();
                            Incharge_Id = list.getProperty("Incharge_Id").toString();
                          /*  O_eventClosed_Date = (SoapObject) list.getProperty("EventClosed_Date");
                            if (!O_eventClosed_Date.toString().equals("anyType{}") && !O_eventClosed_Date.toString().equals(null)) {
                                S_eventClosed_Date = (SoapPrimitive) list.getProperty("EventClosed_Date");
                                eventClosed_Date = S_eventClosed_Date.toString();
                                Log.d("eventClosed_Date", eventClosed_Date);
                            }*/

                            String date1 = list.getProperty("FromDate").toString();

                            Date initDate = new SimpleDateFormat("dd-MM-yyyy").parse(date1);
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            date = formatter.format(initDate);
                            System.out.println(date);

                            // date=sdf.format(date1);
                            Log.i("Tag_time", "date=" + date);
                            //   State cat = new State(c.getString("state_id"),c.getString("state_name"));
                            UserInfo userInfo = new UserInfo(schedule_id, event_Description, event_TypeSlno, event_Type, event_Id, event_Name, event_Fees, total_Stalls, date, toDate, eventStatus, isEventClosed, eventClosed_Date, isChecklistCompleted, isExpenseUpdated, isAccountantVerified, verified_Date, user_Id, username, email_id, status,Attendance_Date,isAttendance_Taken,Participants_Count,Incharge_Id);
                            arrayList.add(userInfo);
                        }

                    }

                    final String[] items = new String[Count];
                    eventListModulessarr = new UserInfo[Count];
                    UserInfo obj = new UserInfo();

                    UserInfo.user_info_arr.clear();
                    for (int i = 0; i < response.getPropertyCount(); i++) {
                        schedule_id = arrayList.get(i).schedule_id;
                        event_Description = arrayList.get(i).Event_Description;
                        eventClosed_Date = arrayList.get(i).EventClosed_Date;
                        event_Fees = arrayList.get(i).Event_Fees;
                        event_Id = arrayList.get(i).Event_Id;
                        event_Name = arrayList.get(i).Event_Name;
                        event_Type = arrayList.get(i).Event_Type;
                        event_TypeSlno = arrayList.get(i).Event_TypeSlno;
                        eventStatus= arrayList.get(i).EventStatus;
                        fromDate = arrayList.get(i).FromDate;
                        total_Stalls = arrayList.get(i).Total_Stalls;
                        Log.e("tag","arrayList total_Stalls="+total_Stalls);
                        toDate = arrayList.get(i).ToDate;
                        verified_Date = arrayList.get(i).Verified_Date;
                        email_id = arrayList.get(i).email_id;
                        isEventClosed = arrayList.get(i).isEventClosed;
                        isAccountantVerified = arrayList.get(i).isAccountantVerified;
                        isChecklistCompleted = arrayList.get(i).isChecklistCompleted;
                        isExpenseUpdated = arrayList.get(i).isExpenseUpdated;
                        email_id = arrayList.get(i).email_id;

                        Attendance_Date =arrayList.get(i).Attendance_Date;
                        isAttendance_Taken= arrayList.get(i).isAttendance_Taken;
                        Participants_Count=arrayList.get(i).Participants_Count;
                        Incharge_Id=arrayList.get(i).Incharge_Id;

                        status = arrayList.get(i).Status;

                        obj.setSchedule_id(schedule_id);
                        obj.setEvent_Description(event_Description);
                        obj.setEventClosed_Date(eventClosed_Date);
                        obj.setEvent_Description(event_Description);
                        obj.setEvent_Fees(event_Fees);
                        obj.setEvent_TypeSlno(event_TypeSlno);
                        obj.setEvent_Type(event_Type);
                        obj.setEmail_id(email_id);
                        obj.setFromDate(fromDate);
                        obj.setTotal_Stalls(total_Stalls);
                        obj.setToDate(toDate);
                        obj.setIsAccountantVerified(isAccountantVerified);
                        obj.setIsChecklistCompleted(isChecklistCompleted);
                        obj.setIsExpenseUpdated(isExpenseUpdated);
                        obj.setIsEventClosed(isEventClosed);
                        obj.setVerified_Date(verified_Date);
                        obj.setEventStatus(eventStatus);
                        obj.setStatus(status);

                        obj.setAttendance_Date(Attendance_Date);
                        obj.setIsAttendance_Taken(isAttendance_Taken);
                        obj.setParticipants_Count(Participants_Count);
                        obj.setIncharge_Id(Incharge_Id);
                        eventListModulessarr[i] = obj;
                        //   UserInfo.user_info_arr =new ArrayList<UserInfo>() ;

                        UserInfo.user_info_arr.add(new UserInfo(schedule_id, event_Description, event_TypeSlno, event_Type, event_Id, event_Name, event_Fees, total_Stalls, fromDate, toDate, eventStatus, isEventClosed,eventClosed_Date, isChecklistCompleted, isExpenseUpdated, isAccountantVerified, verified_Date, user_Id, username, email_id, status,Attendance_Date,isAttendance_Taken,Participants_Count,Incharge_Id) );

                        Log.i("Tag", "items aa=" + arrayList.get(i).schedule_id);

                    }

                    Log.i("Tag", "items=" + items.length);
                }

            } catch (Throwable t) {
                //Toast.makeText(MainActivity.this, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Log.e("request fail 5", "> " + t.getMessage());
            }
        } catch (Throwable t) {
            Log.e("Tag", "UnRegister Receiver Error" + " > " + t.getMessage());

        }

    }
}
