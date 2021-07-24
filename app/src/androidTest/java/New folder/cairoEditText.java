package fontsmaterialuiux;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;


/**
 * Created by Mohammmed Alsudani on 26-Jan-19.
 * for more visit http://materialuiux.com
 */
public class cairoEditText extends AppCompatEditText
{


    public cairoEditText(Context context)
    {
        super(context);
        init();
    }

    public cairoEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public cairoEditText(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        if (!isInEditMode())
        {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Cairo-Regular.ttf");
            setTypeface(tf);
        }
    }

}
