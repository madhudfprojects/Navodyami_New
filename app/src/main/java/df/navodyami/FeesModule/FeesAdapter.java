package df.navodyami.FeesModule;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import df.navodyami.R;


/**
 * @author Madhu on 29/01/2020.
 */

public class FeesAdapter extends ArrayAdapter<FeesListModel> {
    private Context context1;
    String Slno,Schedule_Id,Entreprenuer_Slno,Entreprenuer_Id,Mobile_No,Email_Id,Sector_Name,Allocated_Fees,Collected_Fees,Discount_Amount,Discount_Remark,Discount_Date,Balance,Stall_No, Name, UserName,Payment_Status,Submit_Count,Verified_Count;

    String UserId,EventDate,EventName,IsLoc,IsAdmin,isInchage;
    public  ArrayList<FeesListModel> listview_info_arr=new ArrayList<>();

    int dmin,dHour,dsec;

    public FeesAdapter(Context context, ArrayList<FeesListModel> listview_info_arr) {
        super(context, R.layout.fees_list);
        this.context1=context;
        this.listview_info_arr=listview_info_arr;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
            convertView = inflater.inflate(R.layout.fees_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        final FeesListModel model = getItem(position);
        int mm=getCount();

        Log.i("TAG","mm="+mm+" getItemId(position)="+ getItemId(position)+"getItem="+getItem(position));

        Schedule_Id=model.getSchedule_Id();
        Slno=model.getSlno();
        Name=model.getName();
        Sector_Name=model.getSector_Name();
        Mobile_No=model.getMobile_No();
        Allocated_Fees=model.getAllocated_Fees();
        Collected_Fees=model.getCollected_Fees();
        Discount_Amount=model.getDiscount_Amount();
        Discount_Remark=model.getDiscount_Remark();
        Discount_Date=model.getDiscount_Date();
        Balance=model.getBalance();
        UserName=model.getUserName();
        Entreprenuer_Slno=model.getEntreprenuer_Slno();
        IsAdmin=model.getIsAdmin();
        isInchage=model.getIsInchage();
        IsLoc=model.getIsLocation();
        EventDate=model.getEvent_Date();
        EventName=model.getEvent_Name();
        UserId=model.getUserId();
//        listview_info_arr.get(position);

        holder.name_tv.setText(Name);
        holder.mobileNo_tv.setText(Mobile_No);
        holder.sector_tv.setText(Sector_Name);
        holder.createdBy_tv.setText(UserName);
        holder.collecteAmt_tv.setText(Collected_Fees);
        holder.balanceAmt_tv.setText(Balance);
        holder.discountAmt_tv.setText(Discount_Amount);
        holder.tv_discountRemark.setText(Discount_Remark);
        holder.tv_discountDate.setText(Discount_Date);
        holder.scheduleId_tv.setText(Schedule_Id);
        holder.entreprenuerSlno_tv.setText(Entreprenuer_Slno);
        holder.Slno_tv.setText(Slno);

        holder.Event_Name.setText(EventName);
        holder.Event_Date.setText(EventDate);
        holder.IsLocation.setText(IsLoc);
        holder.IsAdmin.setText(IsAdmin);
        holder.isInchage.setText(isInchage);
        holder.userId.setText(UserId);

        if(model.getMobile_No()!=null) {
            holder.mobileNo_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + holder.mobileNo_tv.getText().toString()));
                    v.getContext().startActivity(intent);
                }
            });
        }
        if(holder.balanceAmt_tv.getText().equals("0")){
            holder.payment_bt.setVisibility(View.GONE);
            holder.discount_bt.setVisibility(View.GONE);
        }
        if(model.getIsAdmin()!=null) {
            holder.IsAdmin.setText(model.getIsAdmin().toString());
        }
        if(model.getIsLocation()!=null) {
            holder.IsLocation.setText(model.getIsLocation().toString());
        }
        if(model.getEvent_Date()!=null) {
            holder.Event_Date.setText(model.getEvent_Date().toString());
        } if(model.getEvent_Name()!=null) {
            holder.Event_Name.setText(model.getEvent_Name().toString());
        } if(model.getIsInchage()!=null) {
            holder.isInchage.setText(model.getIsInchage().toString());
        }



        holder.payment_bt.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        Intent i = new Intent(getContext(), FeesPaymentActivity.class);
                                                        i.putExtra("Name",holder.name_tv.getText().toString());
                                                        i.putExtra("BalanceAmt",holder.balanceAmt_tv.getText().toString());
                                                        i.putExtra("CollectAmt",holder.collecteAmt_tv.getText().toString());
                                                        i.putExtra("DiscountAmt",holder.discountAmt_tv.getText().toString());
                                                        i.putExtra("Schedule_Id",holder.scheduleId_tv.getText().toString());
                                                        i.putExtra("Entreprenuer_Slno",holder.entreprenuerSlno_tv.getText().toString());
                                                        i.putExtra("IsLocation",holder.IsLocation.getText().toString());
                                                        i.putExtra("IsAdmin",holder.IsAdmin.getText().toString());
                                                        i.putExtra("isInchage",holder.isInchage.getText().toString());
                                                        i.putExtra("UserId",holder.userId.getText().toString());
                                                        i.putExtra("Event_Date",holder.Event_Date.getText().toString());
                                                        i.putExtra("Event_Name",holder.Event_Name.getText().toString());
                                                        context1.startActivity(i);
                                                    }
                                                });

        holder.discount_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!holder.discountAmt_tv.getText().equals("0")){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context1);
                    dialog.setCancelable(false);
                    dialog.setTitle("Navodyami");
                    dialog.setMessage(" Discount already given. \n Are you sure want to update it?");

                    dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Intent i = new Intent(getContext(), FeesDiscountActivity.class);
                            i.putExtra("Name", holder.name_tv.getText().toString());
                            i.putExtra("BalanceAmt", holder.balanceAmt_tv.getText().toString());
                            i.putExtra("CollectAmt", holder.collecteAmt_tv.getText().toString());
                            i.putExtra("DiscountAmt", holder.discountAmt_tv.getText().toString());
                            i.putExtra("Schedule_Id", holder.scheduleId_tv.getText().toString());
                            i.putExtra("Entreprenuer_Slno", holder.Slno_tv.getText().toString());
                            i.putExtra("DiscountRemark", holder.tv_discountRemark.getText().toString());
                            i.putExtra("DiscountDate", holder.tv_discountDate.getText().toString());
                            i.putExtra("IsLocation",holder.IsLocation.getText().toString());
                            i.putExtra("IsAdmin",holder.IsAdmin.getText().toString());
                            i.putExtra("isInchage",holder.isInchage.getText().toString());
                            i.putExtra("UserId",holder.userId.getText().toString());
                            i.putExtra("Event_Date",holder.Event_Date.getText().toString());
                            i.putExtra("Event_Name",holder.Event_Name.getText().toString());
                            context1.startActivity(i);
                            // finish();
                        }
                    })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Action for "Cancel".
                                    dialog.dismiss();
                                }
                            });

                    final AlertDialog alert = dialog.create();
                    alert.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface arg0) {
                            alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
                        }
                    });
                    alert.show();
                }
                else {
                    Intent i = new Intent(getContext(), FeesDiscountActivity.class);
                   /* i.putExtra("Name", holder.name_tv.getText().toString());
                    i.putExtra("BalanceAmt", holder.balanceAmt_tv.getText().toString());
                    i.putExtra("CollectAmt", holder.collecteAmt_tv.getText().toString());
                    i.putExtra("DiscountAmt", holder.discountAmt_tv.getText().toString());
                    i.putExtra("Schedule_Id", holder.scheduleId_tv.getText().toString());
                    i.putExtra("Entreprenuer_Slno", holder.Slno_tv.getText().toString());*/
                    i.putExtra("Name", holder.name_tv.getText().toString());
                    i.putExtra("BalanceAmt", holder.balanceAmt_tv.getText().toString());
                    i.putExtra("CollectAmt", holder.collecteAmt_tv.getText().toString());
                    i.putExtra("DiscountAmt", holder.discountAmt_tv.getText().toString());
                    i.putExtra("Schedule_Id", holder.scheduleId_tv.getText().toString());
                    i.putExtra("Entreprenuer_Slno", holder.Slno_tv.getText().toString());
                    i.putExtra("DiscountRemark", holder.tv_discountRemark.getText().toString());
                    i.putExtra("DiscountDate", holder.tv_discountDate.getText().toString());
                    i.putExtra("IsLocation",holder.IsLocation.getText().toString());
                    i.putExtra("IsAdmin",holder.IsAdmin.getText().toString());
                    i.putExtra("isInchage",holder.isInchage.getText().toString());
                    i.putExtra("UserId",holder.userId.getText().toString());
                    i.putExtra("Event_Date",holder.Event_Date.getText().toString());
                    i.putExtra("Event_Name",holder.Event_Name.getText().toString());
                    context1.startActivity(i);
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
     //   ImageView imageView;
        TextView name_tv;
        TextView mobileNo_tv;
        TextView sector_tv;
        TextView createdBy_tv;
        TextView collecteAmt_tv;
        TextView balanceAmt_tv;
        TextView discountAmt_tv;
        TextView scheduleId_tv;
        TextView entreprenuerSlno_tv;
        TextView Slno_tv;
        TextView tv_discountRemark;
        TextView tv_discountDate;
        TextView Event_Name;
        TextView Event_Date;
        TextView IsAdmin;
        TextView isInchage;
        TextView IsLocation;
        TextView userId;

        ImageButton discount_bt;
        ImageButton payment_bt;

        ViewHolder(View view) {

            name_tv = (TextView) view.findViewById(R.id.name_tv);
            mobileNo_tv = (TextView) view.findViewById(R.id.mobileNo_tv);
            sector_tv =(TextView) view.findViewById(R.id.sector_tv);
            createdBy_tv = (TextView) view.findViewById(R.id.createdBy_tv);
            collecteAmt_tv = (TextView) view.findViewById(R.id.collecteAmt_tv);
            balanceAmt_tv =(TextView) view.findViewById(R.id.balanceAmt_tv);
            discountAmt_tv = (TextView) view.findViewById(R.id.discountAmt_tv);
            scheduleId_tv = (TextView) view.findViewById(R.id.scheduleId_tv);
            entreprenuerSlno_tv = (TextView) view.findViewById(R.id.entreprenuerSlno_tv);
            Slno_tv = (TextView) view.findViewById(R.id.Slno_tv);
            tv_discountDate = (TextView) view.findViewById(R.id.tv_discountDate);
            tv_discountRemark = (TextView) view.findViewById(R.id.tv_discountRemark);
            discount_bt =(ImageButton)view.findViewById(R.id.discount_bt);
            payment_bt = (ImageButton) view.findViewById(R.id.payment_bt);

            Event_Name = (TextView) view.findViewById(R.id.tv_Event_Name);
            Event_Date = (TextView) view.findViewById(R.id.tv_Event_Date);
            IsAdmin = (TextView) view.findViewById(R.id.tv_IsAdmin);
            isInchage = (TextView) view.findViewById(R.id.tv_isInchage);
            IsLocation = (TextView) view.findViewById(R.id.tv_IsLocation);
            userId = (TextView) view.findViewById(R.id.tv_userId);
        }
    }
}
