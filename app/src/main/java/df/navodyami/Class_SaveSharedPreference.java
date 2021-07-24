package df.navodyami;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Class_SaveSharedPreference
{
    static final String PREF_USER_NAME= "username";

    static final String PREF_TECHSUPPORTEMAIL= "email_techsuport";
    static final String PREF_TECHSUPPORTPHONE= "mobile_techsuport";
    static final String PREF_TECHSUPPORT_TITLE= "title_techsuport";

    static final String PREF_FLAG_USERMANUAL= "flag_usermanual";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }


    public static void setSupportEmail(Context ctx, String email)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_TECHSUPPORTEMAIL, email);
        editor.commit();
    }

    public static String getSupportEmail(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_TECHSUPPORTEMAIL, "");
    }


    public static void setSupportPhone(Context ctx, String email)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_TECHSUPPORTPHONE, email);
        editor.commit();
    }

    public static String getSupportPhone(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_TECHSUPPORTPHONE, "");
    }

    public static void setSupportTitle(Context ctx, String title)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_TECHSUPPORT_TITLE, title);
        editor.commit();
    }

    public static String getSupportTitle(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_TECHSUPPORT_TITLE, "");
    }

    public static void setPrefFlagUsermanual(Context ctx, String flag)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_FLAG_USERMANUAL, flag);
        editor.commit();
    }

    public static String getPrefFlagUsermanual(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_FLAG_USERMANUAL, "");
    }






}