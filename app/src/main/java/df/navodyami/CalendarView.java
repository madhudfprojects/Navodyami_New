package df.navodyami;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Locale;

import df.navodyami.adapter.AndroidListAdapter;
import df.navodyami.adapter.CalendarAdapter;
import df.navodyami.util.UserInfo;

public class CalendarView extends AppCompatActivity {

	public GregorianCalendar month, itemmonth,cal_month_copy;// calendar instances.
	private CalendarAdapter cal_adapter;

	public CalendarAdapter adapter;// adapter instance
	public Handler handler;// for grabbing some event values for showing the dot
							// marker.
	public ArrayList<String> items; // container to store calendar items which
									// needs showing the event marker

	private Toolbar mTopToolbar;
	String u1,p1;

	private ListView lv_android;
	private AndroidListAdapter list_adapter;

	// ArrayList<EventModule> eventModules=new ArrayList<>();

	String bookid,cohartName,fellowshipName,eventdate,start_time,userId,status,end_time,module_name,attendance;
	Boolean eventUpdate;
	ArrayList<String> bookId1;
	ArrayList<String> eventdate1;
	int sizearray;
	private SwipeRefreshLayout swipeLayout;
	Class_InternetDectector internetDectector;
	Boolean isInternetPresent = false;
	//Button logout_bt;
	String Schedule_Status,Schedule_ID,Lavel_ID,Schedule_Date,End_Time,Start_Time,Schedule_Session,Subject_Name,Leason_Name,Lavel_Name,Cluster_Name,Institute_Name;
	ArrayList<UserInfo> arrayList = new ArrayList<UserInfo>();
	UserInfo[] userInfosarr;
	String str_loginuserID;
	String monthtype2="null";
	int date_int;
	int month_int;
	int year_int;
	String monthYear;
	String finalMonth,finalYear;
	private Context context;
	RelativeLayout next;
	String date;
	UserInfo[] eventListModulessarr;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar);
		/*mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
		setSupportActionBar(mTopToolbar);
*/
//		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//getSupportActionBar().setTitle("Schedules");

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_n_actionbar);
		setSupportActionBar(toolbar);
		//  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		TextView title1= (TextView) toolbar.findViewById(R.id.title_name);
		title1.setText("View Schedules");
		getSupportActionBar().setTitle("");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		 Locale.setDefault( Locale.US );
	/*	month = (GregorianCalendar) GregorianCalendar.getInstance();
		cal_month_copy = (GregorianCalendar) month.clone();*/

		//itemmonth = (GregorianCalendar) month.clone();

		SharedPreferences myprefs = this.getSharedPreferences("user", Context.MODE_PRIVATE);
		str_loginuserID = myprefs.getString("login_userid", "nothing");

		items = new ArrayList<String>();
		context = CalendarView.this;
		Intent intent = getIntent();
		String date_int=intent.getStringExtra("date_int");
		String month_int=intent.getStringExtra("month_int");
		String year_int=intent.getStringExtra("year_int");

		SharedPreferences myprefs1= getSharedPreferences("month", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = myprefs1.edit();
		editor.clear();
		editor.apply();

		monthtype2 = myprefs1.getString("month", "null");

		if(monthtype2.equals("false")) {
			String monthtype = intent.getStringExtra("month_type");

			if (monthtype.equals("true_pre")) {
				setPreviousMonth();
				//   refreshCalendar();
				monthtype2="true";
			}else if (monthtype.equals("true_next")) {
				setNextMonth();
				//   refreshCalendar();
				monthtype2="true";
			}
		}else {

			monthtype2="true";
			//  cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
			month = (GregorianCalendar) new GregorianCalendar(Integer.valueOf(year_int),Integer.valueOf(month_int),Integer.valueOf(date_int));
			cal_month_copy = (GregorianCalendar) month.clone();

			int month2 = 0, year = 0, finalmonth = 0;

			month2 = month.get(GregorianCalendar.MONTH);
			year = month.get(GregorianCalendar.YEAR);

			finalmonth=month2;

			if(finalmonth<=9){
				monthYear = "0"+finalmonth + "/" + year;
				finalMonth="0"+finalmonth;
				finalYear= String.valueOf(year);
			} else{
				monthYear = finalmonth + "/" + year;
				finalMonth= String.valueOf(finalmonth);
				finalYear= String.valueOf(year);
			}
			// monthYear = finalmonth + "/" + year;
			Log.i("tag_initial", "finalmonth=" + finalmonth + "monthYear=" + monthYear);

		}
		adapter = new CalendarAdapter(this, month, UserInfo.user_info_arr);

		GridView gridview = (GridView) findViewById(R.id.gv_calendar);
		gridview.setAdapter(adapter);

		/*handler = new Handler();
		handler.post(calendarUpdater);*/

		TextView title = (TextView) findViewById(R.id.tv_month);
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

		RelativeLayout previous = (RelativeLayout) findViewById(R.id.previous);

		/*previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setPreviousMonth();
				refreshCalendar();
			}
		});*/
		previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setPreviousMonth();
				//   refreshCalendar();
			}
		});

		next = (RelativeLayout) findViewById(R.id.next);
		/*next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setNextMonth();
				refreshCalendar();

			}
		});*/
	//	next.setVisibility(View.INVISIBLE);
		Calendar calendar = Calendar.getInstance();
		int month1 = calendar.get(Calendar.MONTH);
		int month2=month.get(GregorianCalendar.MONTH);

		int year1= calendar.get(Calendar.YEAR);
		int year2= month.get(GregorianCalendar.YEAR);
		if(month2<month1 || year2<year1){
			//next.setVisibility(View.VISIBLE);
			next.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					setNextMonth();
					//       refreshCalendar();

				}
			});
		}else if(month1==month2 || year1==year2){
			//next.setVisibility(View.INVISIBLE);
			next.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					setNextMonth();
					//       refreshCalendar();

				}
			});
		}

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				((CalendarAdapter) parent.getAdapter()).setSelected(v,position);
				String selectedGridDate = CalendarAdapter.day_string
						.get(position);
				String[] separatedTime = selectedGridDate.split("-");
				String gridvalueString = separatedTime[2].replaceFirst("^0*",
						"");// taking last part of date. ie; 2 from 2012-12-02.
				int gridvalue = Integer.parseInt(gridvalueString);
				// navigate to next or previous month on clicking offdays.
				if ((gridvalue > 10) && (position < 8)) {
					setPreviousMonth();
					refreshCalendar();
				} else if ((gridvalue < 7) && (position > 28)) {
					setNextMonth();
					refreshCalendar();
				}
				((CalendarAdapter) parent.getAdapter()).setSelected(v,position);
				((CalendarAdapter) parent.getAdapter()).getPositionList(selectedGridDate, CalendarView.this);

			//	showToast(selectedGridDate);

			}
		});
	}

	/*protected void setNextMonth() {
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMaximum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) + 1),
					month.getActualMinimum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) + 1);
		}

	}

	protected void setPreviousMonth() {
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMinimum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) - 1),
					month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) - 1);
		}

	}*/
	protected void setNextMonth() {
		int month2 = 0, year=0, finalmonth=0;
		Log.e("tag","cal_month0="+month);
		//  monthYear="12/2018";

		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMaximum(GregorianCalendar.MONTH)) {
			Log.e("tag","cal_month2="+month);
			month.set((month.get(GregorianCalendar.YEAR) + 1),
					month.getActualMinimum(GregorianCalendar.MONTH), 1);
			month2=month.get(GregorianCalendar.MONTH);
			year = month.get(GregorianCalendar.YEAR);
		} else {
			Log.e("tag","cal_month1 ="+month);
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) + 1);
			month2=month.get(GregorianCalendar.MONTH);
			year = month.get(GregorianCalendar.YEAR);
		}

		finalmonth=month2+1;

		if(finalmonth<=9){
			monthYear = "0"+finalmonth + "/" + year;
			finalMonth="0"+finalmonth;
			finalYear= String.valueOf(year);
		}else {
			monthYear = finalmonth + "/" + year;
			finalMonth= String.valueOf(finalmonth);
			finalYear= String.valueOf(year);
		}
		Log.i("tag_next","finalmonth="+finalmonth+"monthYear="+monthYear);

	//	next.setVisibility(View.INVISIBLE);
		Calendar calendar = Calendar.getInstance();
		int month1 = calendar.get(Calendar.MONTH);
		// int month2=GregorianCalendar.getInstance().get(GregorianCalendar.MONTH);
		Log.i("tag","month1="+month1+"month2="+month2);

		if(month2<month1){
		//	next.setVisibility(View.VISIBLE);
			next.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					setNextMonth();
					//       refreshCalendar();

				}
			});
		}else if(month1==month2){
		//	next.setVisibility(View.INVISIBLE);
			next.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					setNextMonth();
					//       refreshCalendar();

				}
			});
		}
		if (monthtype2.equals("true")) {
			monthtype2="false";

			date_int=1;
			month_int=month2;
			year_int=year;

			AsyncCallWS2 task=new AsyncCallWS2(CalendarView.this);
			task.execute();

		}


	}

	protected void setPreviousMonth() {
		int month2 = 0, year=0, finalmonth=0;
		// monthYear="11/2018";

		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMinimum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) - 1),
					month.getActualMaximum(GregorianCalendar.MONTH), 1);
			month2=month.get(GregorianCalendar.MONTH);
			year = month.get(GregorianCalendar.YEAR);
		} else {
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) - 1);
			month2=month.get(GregorianCalendar.MONTH);
			year = month.get(GregorianCalendar.YEAR);

		}

		//next.setVisibility(View.INVISIBLE);
		Calendar calendar = Calendar.getInstance();
		int month1 = calendar.get(Calendar.MONTH);
		// int month2=GregorianCalendar.getInstance().get(GregorianCalendar.MONTH);
		Log.i("tag","month1="+month1+"month2="+month2+"year="+year);

		finalmonth=month2+1;

		if(finalmonth<=9){
			monthYear = "0"+finalmonth + "/" + year;
			finalMonth="0"+finalmonth;
			finalYear= String.valueOf(year);
		}else {
			monthYear = finalmonth + "/" + year;
			finalMonth= String.valueOf(finalmonth);
			finalYear= String.valueOf(year);
		}
		Log.i("tag","finalmonth="+finalmonth+"monthYear="+monthYear);



		if(month2<month1){
		//	next.setVisibility(View.VISIBLE);
			next.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					setNextMonth();
					//    refreshCalendar();

				}
			});
		}else if(month1==month2){
		//	next.setVisibility(View.INVISIBLE);
			next.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					setNextMonth();
					//       refreshCalendar();

				}
			});
		}
		if (monthtype2.equals("true")) {
			monthtype2="false";
            /*Intent intent = new Intent(context, CalenderActivity.class);
            intent.putExtra("month_type", "true_pre");
            intent.putExtra("monthYear", monthYear);
            SharedPreferences myprefs = getSharedPreferences("month", MODE_PRIVATE);
            myprefs.edit().putString("month", monthtype2).commit();
            startActivity(intent);*/

            /*Intent intent = new Intent(context, CalenderActivity.class);
            intent.putExtra("date_int",String.valueOf(1));
            intent.putExtra("month_int",String.valueOf(month2));
            intent.putExtra("year_int",String.valueOf(year));
            startActivity(intent);
*/
			date_int=1;
			month_int=month2;
			year_int=year;
			AsyncCallWS2 task=new AsyncCallWS2(CalendarView.this);
			task.execute();
		}

	}

	protected void showToast(String string) {
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();

	}

	public void refreshCalendar() {
		TextView title = (TextView) findViewById(R.id.tv_month);

		adapter.refreshDays();
		adapter.notifyDataSetChanged();
		//handler.post(calendarUpdater); // generate some calendar items

		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
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
			/*Calendar now = Calendar.getInstance();
			//
			date_int=now.get(Calendar.DATE);
			month_int=now.get(Calendar.MONTH);
			year_int=now.get(Calendar.YEAR);
			Intent intent = new Intent(context, CalendarView.class);
			intent.putExtra("date_int", String.valueOf(date_int));
			intent.putExtra("month_int", String.valueOf(month_int));
			intent.putExtra("year_int", String.valueOf(year_int));
			startActivity(intent);*/

			Intent intent = new Intent(context, CalendarView.class);
			intent.putExtra("date_int",String.valueOf(date_int));
			intent.putExtra("month_int",String.valueOf(month_int));
			intent.putExtra("year_int",String.valueOf(year_int));
			startActivity(intent);

			month = (GregorianCalendar) GregorianCalendar.getInstance();
			cal_month_copy = (GregorianCalendar) month.clone();
			cal_adapter = new CalendarAdapter(getApplicationContext(), month, UserInfo.user_info_arr);


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
							Log.e("tag","Total_Stalls web="+total_Stalls);
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
						obj.setToDate(toDate);
						obj.setIsAccountantVerified(isAccountantVerified);
						obj.setIsChecklistCompleted(isChecklistCompleted);
						obj.setIsExpenseUpdated(isExpenseUpdated);
						obj.setIsEventClosed(isEventClosed);
						obj.setVerified_Date(verified_Date);
						obj.setEventStatus(eventStatus);
						obj.setStatus(status);
						obj.setTotal_Stalls(total_Stalls);
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
	/*public Runnable calendarUpdater = new Runnable() {

		@Override
		public void run() {
			items.clear();

			// Print dates of the current week
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
			String itemvalue;
			for (int i = 0; i < 7; i++) {
				itemvalue = df.format(itemmonth.getTime());
				itemmonth.add(GregorianCalendar.DATE, 1);
				items.add("2019-09-12");
				items.add("2019-10-07");
				items.add("2019-10-15");
				items.add("2019-10-20");
				items.add("2019-11-30");
				items.add("2019-11-28");
			}

			adapter.setItems(items);
			adapter.notifyDataSetChanged();
		}
	};*/

	@Override
	public void onBackPressed() {


    /*    Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }*/

		Intent i = new Intent(CalendarView.this, BottomActivity.class);
		i.putExtra("frgToLoad", "1");
		startActivity(i);

		finish();
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
			Intent i = new Intent(CalendarView.this, BottomActivity.class);
			i.putExtra("frgToLoad", "1");
			startActivity(i);
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
