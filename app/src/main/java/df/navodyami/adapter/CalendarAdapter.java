package df.navodyami.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import df.navodyami.CalendarView;
import df.navodyami.CalenderActivity;
import df.navodyami.EventListActivity;
import df.navodyami.R;
import df.navodyami.util.EventChoosedList;
import df.navodyami.util.UserInfo;


public class CalendarAdapter extends BaseAdapter {
	private Context context;

	private java.util.Calendar month;
	public GregorianCalendar pmonth;
	Intent intent;
	/**
	 * calendar instance for previous month for getting complete view
	 */
	public GregorianCalendar pmonthmaxset;
	private GregorianCalendar selectedDate;
	int firstDay;
	int maxWeeknumber;
	int maxP;
	int calMaxP;
	int lastWeekDay;
	int leftDays;
	int mnthlength;
	String itemvalue, curentDateString;
	DateFormat df;

	List<UserInfo> UserInfolist = new ArrayList<UserInfo>();

	private ListView lv_android;
	private AndroidListAdapter list_adapter;

	View v;
	View GlobalView;
	private ArrayList<String> items;
	public static List<String> day_string;
	private View previousView;
	//public ArrayList<CalendarCollection> date_collection_arr;
	//public ArrayList<EventModule> event_module_arr;
	public ArrayList<UserInfo> userInfo_arr;
	ArrayList<UserInfo> arrayList= new ArrayList<UserInfo>();
	Date Selected_Date,Current_date;
	final EventChoosedList eventChoosedList = new EventChoosedList();

//	EventChoosedList eventChoosedList=new EventChoosedList();

	public CalendarAdapter(Context context, GregorianCalendar monthCalendar, ArrayList<UserInfo> userInfo_arr) {
		this.userInfo_arr=userInfo_arr;
		CalendarAdapter.day_string = new ArrayList<String>();
		Locale.setDefault(Locale.US);
		month = monthCalendar;
		selectedDate = (GregorianCalendar) monthCalendar.clone();
		this.context = context;
		month.set(GregorianCalendar.DAY_OF_MONTH, 1);

		this.items = new ArrayList<String>();
		df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		curentDateString = df.format(selectedDate.getTime());
		refreshDays();

	}



	public void setItems(ArrayList<String> items) {
		for (int i = 0; i != items.size(); i++) {
			if (items.get(i).length() == 1) {
				items.set(i, "0" + items.get(i));
			}
		}
		this.items = items;
	}

	public int getCount() {
		return day_string.size();
	}

	public Object getItem(int position) {
		return day_string.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new view for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		v=convertView;
		View v = convertView;
		TextView dayView;
		if (convertView == null) { // if it's not recycled, initialize some
			// attributes
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.cal_item, null);

		}


		dayView = (TextView) v.findViewById(R.id.date);
		String[] separatedTime = day_string.get(position).split("-");


		String gridvalue = separatedTime[2].replaceFirst("^0*", "");
		if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
			dayView.setTextColor(Color.GRAY);
			dayView.setClickable(false);
			dayView.setFocusable(false);
		} else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
			dayView.setTextColor(Color.GRAY);
			dayView.setClickable(false);
			dayView.setFocusable(false);
		} else {
			// setting curent month's days in blue color.
			dayView.setTextColor(Color.BLACK);
		}


		if (day_string.get(position).equals(curentDateString)) {

			//v.setBackgroundColor(Color.CYAN);
			v.setBackgroundResource(R.drawable.rounded_calender_wo);
			dayView.setTextColor(Color.WHITE);
		} else {
			v.setBackgroundColor(Color.parseColor("#ffffff"));
		}


		dayView.setText(gridvalue);

		// create date string for comparison
		String date = day_string.get(position);

		if (date.length() == 1) {
			date = "0" + date;
		}
		String monthStr = "" + (month.get(GregorianCalendar.MONTH) + 1);
		if (monthStr.length() == 1) {
			monthStr = "0" + monthStr;
		}

		// show icon if date is not empty and it exists in the items array
		/*ImageView iw = (ImageView) v.findViewById(R.id.date_icon);
		if (date.length() > 0 && items != null && items.contains(date)) {
			iw.setVisibility(View.VISIBLE);
		} else {
			iw.setVisibility(View.GONE);
		}
		*/

		setEventView(v, position,dayView);

		return v;
	}

	public View setSelected(View view, int pos) {

		TextView dayView = (TextView) view.findViewById(R.id.date);
		//if (previousView != null) {
		// previousView.setBackgroundColor(Color.parseColor("#ffffff"));
			/*view.setBackgroundColor(Color.parseColor("#343434"));
			view.setBackgroundResource(R.drawable.rounded_selecteditem_false);
			dayView.setTextColor(Color.WHITE);*/
		//}

		//	view.setBackgroundColor(Color.MAGENTA);
		//view.setBackgroundColor(Color.parseColor("#343434"));
		//	int len1=EventModule.event_module_arr.size();
		int size1= UserInfo.user_info_arr.size();
		arrayList.size();
		//UserInfolist.size();
		Log.e("Tag","size="+size1);
		for (int i = 0; i < size1; i++) {
			//EventModule cal_obj = EventModule.event_module_arr.get(i);
			UserInfo cal_obj = UserInfo.user_info_arr.get(i);
			String date = cal_obj.FromDate;
            /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(date1);*/
			SimpleDateFormat daySDF = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Selected_Date = daySDF.parse(date);
				Current_date = new Date();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//	boolean event_TF = cal_obj.eventUpdate;

			String event_status = cal_obj.isEventClosed;
			String attendence_status = cal_obj.isAttendance_Taken;

			//	Log.i("Tag","event_status="+event_status);
			if (day_string.get(pos).equals(date) && event_status.equals("1")){
				view.setBackgroundResource(R.drawable.rounded_calender_persent);
				dayView.setTextColor(Color.WHITE);
			/*		if (previousView != null) {
					previousView.setBackgroundResource(R.drawable.rounded_calender_item);
					}*/
			}
			if (day_string.get(pos).equals(date) && event_status.equals("1")){
				view.setBackgroundResource(R.drawable.rounded_calender_persent);
				dayView.setTextColor(Color.WHITE);
			/*		if (previousView != null) {
					previousView.setBackgroundResource(R.drawable.rounded_calender_item);
					}*/
			}
			if(day_string.get(pos).equals(date)&&event_status.equals("0")) {
				Log.i("Tag","Selected_Date="+Selected_Date+"Current_date="+Current_date);
				if(Selected_Date.compareTo(Current_date) > 0){
					view.setBackgroundResource(R.drawable.rounded_calender_od);
					dayView.setTextColor(Color.WHITE);
					Log.i("Tag","rounded_calender_todaydate=");
					//	txt.setTextColor(Color.WHITE);
				}else {
					/*if (day_string.get(pos).equals(curentDateString)) {
						view.setBackgroundColor(Color.parseColor("#ffffff"));
						view.setBackgroundResource(R.drawable.rounded_calender_todaydate);
						dayView.setTextColor(Color.WHITE);
					}else {*/
					if(day_string.get(pos).equals(date)&&attendence_status.equals("0")){
                        view.setBackgroundResource(R.drawable.rounded_calender_absent);
                        dayView.setTextColor(Color.WHITE);
                        Log.i("Tag", "rounded_calender_notupdated=");
                    }else{
                        view.setBackgroundResource(R.drawable.rounded_calender_late);
                        dayView.setTextColor(Color.WHITE);
                        Log.i("Tag", "rounded_calender_updated yellow=");

                    }

					//}
					//	txt.setTextColor(Color.WHITE);
				}
			}
		/*	if (day_string.get(pos).equals(date) && event_status.equals("0")) {
				view.setBackgroundResource(R.drawable.rounded_calender_notupdated);
				dayView.setTextColor(Color.WHITE);
				/*	if (previousView != null) {
					previousView.setBackgroundResource(R.drawable.rounded_calender_notupdated);
					}*/
			//	}

			Log.i("Tag","day_string.get(pos).equals(date)1="+day_string.get(pos).equals(date));
			Log.i("Tag","day_string.get(pos)="+day_string.get(pos));

			/*else {
				view.setBackgroundColor(Color.parseColor("#343434"));
				dayView.setTextColor(Color.BLACK);
				if (previousView != null) {
					previousView.setBackgroundColor(Color.parseColor("#ffff34"));
				}
			}*/
		}
		int len=day_string.size();
		if (len>pos) {
			if (day_string.get(pos).equals(curentDateString)) {
				view.setBackgroundColor(Color.parseColor("#ffffff"));
				view.setBackgroundResource(R.drawable.rounded_calender_wo);
				dayView.setTextColor(Color.WHITE);
				//view.setBackgroundColor(Color.CYAN);
			}else{
				//	previousView.setBackgroundColor(Color.parseColor("#00FFFF"));
				previousView = view;
			}
		}

		return view;
	}

	public void refreshDays() {
		// clear items
		items.clear();
		day_string.clear();
		Locale.setDefault(Locale.US);
		pmonth = (GregorianCalendar) month.clone();
		// month start day. ie; sun, mon, etc
		firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
		// finding number of weeks in current month.
		maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
		//maxWeeknumber = 6;
		// allocating maximum row number for the gridview.
		mnthlength = maxWeeknumber * 7;
		maxP = getMaxP(); // previous month maximum day 31,30....
		calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
		/**
		 * Calendar instance for getting a complete gridview including the three
		 * month's (previous,current,next) dates.
		 */
		pmonthmaxset = (GregorianCalendar) pmonth.clone();
		/**
		 * setting the start date as previous month's required date.
		 */
		pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);

		/**
		 * filling calendar gridview.
		 */
		for (int n = 0; n < mnthlength; n++) {

			itemvalue = df.format(pmonthmaxset.getTime());
			pmonthmaxset.add(GregorianCalendar.DATE, 1);
			day_string.add(itemvalue);
		}
	}

	private int getMaxP() {
		int maxP;
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMinimum(GregorianCalendar.MONTH)) {
			pmonth.set((month.get(GregorianCalendar.YEAR) - 1),
					month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			pmonth.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) - 1);
		}
		maxP = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

		return maxP;
	}



	public void setEventView(View v, int pos, TextView txt){

		//int len=EventModule.event_module_arr.size();
		int size_new= UserInfo.user_info_arr.size();
		Log.e("tag","size calendarAdapter=="+size_new);
		for (int i = 0; i < size_new; i++) {
			//EventModule cal_obj=EventModule.event_module_arr.get(i);
			UserInfo cal_obj= UserInfo.user_info_arr.get(i);
			String date= cal_obj.FromDate; //cal_obj.strDate;
			//boolean event_TF= cal_obj.eventUpdate;
			SimpleDateFormat daySDF = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Selected_Date = daySDF.parse(date);
				Current_date = new Date();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String event_status =cal_obj.isEventClosed;
            String attendence_status =cal_obj.isAttendance_Taken;
			int len1=day_string.size();
			//Log.i("Tag","len1="+len1);
			if (len1>pos) {
				//Log.i("Tag","event_status1="+event_status);
				if (day_string.get(pos).equals(date) && event_status.equalsIgnoreCase("1")) {
					Log.e("event_TF","day_string="+day_string.get(pos));
					Log.e("event_TF","date="+date);

					v.setBackgroundResource(R.drawable.rounded_calender_persent);
					txt.setTextColor(Color.WHITE);
				}
				if (day_string.get(pos).equals(date) && event_status.equalsIgnoreCase("1")) {
					Log.e("event_TF","day_string="+day_string.get(pos));
					Log.e("event_TF","date="+date);

					v.setBackgroundResource(R.drawable.rounded_calender_persent);
					txt.setTextColor(Color.WHITE);
				}
				if(day_string.get(pos).equals(date)&&event_status.equals("0")) {
					Log.i("Tag","Selected_Date="+Selected_Date+"Current_date="+Current_date);
					if(Selected_Date.compareTo(Current_date) > 0){
						v.setBackgroundResource(R.drawable.rounded_calender_od);
						txt.setTextColor(Color.WHITE);
					}else {

						Log.i("Tag","day_string.get(pos).equals(date)="+day_string.get(pos).equals(date));


						if(day_string.get(pos).equals(date)&& attendence_status.equals("0")) {
                            v.setBackgroundResource(R.drawable.rounded_calender_absent);
                            txt.setTextColor(Color.WHITE);
                            Log.i("Tag", "rounded_calender_not updatedtt=");
                        }else{
                            v.setBackgroundResource(R.drawable.rounded_calender_late);
                            txt.setTextColor(Color.WHITE);
                            Log.i("Tag", "rounded_calender_updated yellowtt=");

                        }
						//}
					}
				}

			}
		}

	}

	public void getPositionList( String date, final Activity act){
		String event_date=null;
		String event_date1=null;
		ArrayList<String> arr_Fdate=new ArrayList<>();
		ArrayList<String> arr_Edate=new ArrayList<>();
		ArrayList<String> arr_eventDiscription=new ArrayList<>();
		ArrayList<String> arr_eventType=new ArrayList<>();
		ArrayList<String> arr_eventName=new ArrayList<>();
		ArrayList<String> arr_eventFees=new ArrayList<>();
		ArrayList<String> arr_eventStatus=new ArrayList<>();
		ArrayList<String> arr_scheduleId=new ArrayList<>();
		ArrayList<String> arr_totalStalls=new ArrayList<>();
		ArrayList<String> arr_Attendance_Taken=new ArrayList<>();
		ArrayList<String> arr_Attendance_Date=new ArrayList<>();
		ArrayList<String> arr_Participants_Count=new ArrayList<>();
		ArrayList<String> arr_Incharge_Id=new ArrayList<>();
		ArrayList<String> arr_IsEventClosed=new ArrayList<>();

		int size= UserInfo.user_info_arr.size();
		Log.e("TAG_TIME","size=="+size);

        arr_Fdate.clear();
        arr_Edate.clear();
        arr_eventDiscription.clear();
        arr_eventType.clear();
        arr_eventName.clear();
        arr_eventFees.clear();
        arr_eventStatus.clear();
        arr_scheduleId.clear();
        arr_totalStalls.clear();
        arr_Attendance_Date.clear();
        arr_Attendance_Taken.clear();
        arr_Participants_Count.clear();
        arr_Incharge_Id.clear();
		arr_IsEventClosed.clear();

		for (int i = 0; i < size; i++) {

			//	EventModule cal_collection=EventModule.event_module_arr.get(i);

			//ArrayList<String> event_date=new ArrayList<String>();
		/*	event_date=cal_collection.strDate;
			String event_starttime=cal_collection.strStartTime;
			String event_endtime=cal_collection.strEndTime;
			String event_faculty=cal_collection.strFacultyName;
			String event_cohort=cal_collection.strCohort;
			String event_classroom=cal_collection.strClassroom;
			String event_module=cal_collection.strModule;
			String event_fellowship=cal_collection.strFellowship;
			Boolean event_update=cal_collection.eventUpdate;
*/
			UserInfo cal_collection= UserInfo.user_info_arr.get(i);
			event_date=cal_collection.FromDate;
			String event_Fdate=cal_collection.FromDate;
			String event_Edate=cal_collection.ToDate;
			String eventDiscription=cal_collection.Event_Description;
			String event_type=cal_collection.Event_Type;
			String event_fees=cal_collection.Event_Fees;
			String event_status=cal_collection.EventStatus;
			String event_Name=cal_collection.Event_Name;
			String scheduleId=cal_collection.schedule_id;
			String totalStalls = cal_collection.Total_Stalls;
			String attendenceTaken = cal_collection.isAttendance_Taken;
			String participants_Count=cal_collection.Participants_Count;
			String attendance_Date=cal_collection.Attendance_Date;
			String incharge_Id=cal_collection.Incharge_Id;
			String isEventClosed=cal_collection.isEventClosed;

			if (date.equals(event_date)) {


                event_date1=event_date;
                arr_scheduleId.add(scheduleId);
				arr_Fdate.add(event_Fdate);
				arr_Edate.add(event_Edate);
				arr_eventDiscription.add(eventDiscription);
				arr_eventName.add(event_Name);
				arr_eventStatus.add(event_status);
				arr_eventFees.add(event_fees);
                arr_eventType.add(event_type);
                arr_totalStalls.add(totalStalls);
                Log.e("tag","totalStalls="+totalStalls);
                arr_Attendance_Date.add(attendance_Date);
                arr_Attendance_Taken.add(attendenceTaken);
                arr_Participants_Count.add(participants_Count);
                arr_Incharge_Id.add(incharge_Id);
				arr_IsEventClosed.add(isEventClosed);
				//arr_eventUpdate[i]=eventChoosedList.getEventUpdate();


				intent = new Intent(context, EventListActivity.class);
				intent.putExtra("arr_scheduleId",arr_scheduleId);
				intent.putExtra("arr_Fdate",arr_Fdate);
				intent.putExtra("arr_Edate",arr_Edate);
				intent.putExtra("arr_eventName",arr_eventName);
				intent.putExtra("arr_eventFees",arr_eventFees);
                intent.putExtra("arr_eventStatus",arr_eventStatus);
				intent.putExtra("arr_eventDiscription",arr_eventDiscription);
				intent.putExtra("arr_eventType",arr_eventType);
				intent.putExtra("arr_totalStalls",arr_totalStalls);
				intent.putExtra("arr_Attendance_Date",arr_Attendance_Date);
				intent.putExtra("arr_Attendance_Taken",arr_Attendance_Taken);
				intent.putExtra("arr_Participants_Count",arr_Participants_Count);
				intent.putExtra("arr_Incharge_Id",arr_Incharge_Id);
				intent.putExtra("arr_IsEventClosed",arr_IsEventClosed);

				//	Log.i("CalenderActivity","arr_eventUpdate"+arr_eventUpdate);
				//Log.i("CalenderActivity","arr_stime="+arr_stime);
				//	CardsAdapter adapter = new CardsAdapter(context);
				//	ListView lv_eventlist=(ListView) view.findViewById(R.id.lv_android);

				// date_tv.setText(date_str);
				//   event_tv.setText(event_msg_str);

				//		lv_eventlist.setAdapter(adapter);
				//GlobalView=context;

				/*lv_android = (ListView) v.findViewById(R.id.lv_android);
				list_adapter=new AndroidListAdapter(v.getContext(),R.layout.list_item, CalendarCollection.date_collection_arr);
				lv_android.setAdapter(list_adapter);*/
				//	getWidget();

				//	Toast.makeText(context, "Selected Date: "+event_date, Toast.LENGTH_SHORT).show();
		/*	 new AlertDialog.Builder(context)
	          .setIcon(android.R.drawable.ic_dialog_alert)
	          .setTitle("Date: "+event_date)
	           .setMessage("Event: "+event_message)
	            .setPositiveButton("OK",new android.content.DialogInterface.OnClickListener(){
	            public void onClick(DialogInterface dialog, int which)
	            {
	            	act.finish();
	            }
	            }).show();
			break;	*/
			}else{
				/*Intent intent1  = new Intent (context, CalenderActivity.class);
				context.startActivity(intent1);*/
			}

		}
		Log.i("Tag","event_date=="+event_date1);

		if (date.equals(event_date1)) {
			context.startActivity(intent);
		}
		else{
			/*Intent intent1  = new Intent (context, CalenderActivity.class);
			context.startActivity(intent1);*/
		}

	}

}