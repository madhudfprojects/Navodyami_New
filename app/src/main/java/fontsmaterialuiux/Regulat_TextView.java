package fontsmaterialuiux;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
/**
 * for more visit http://materialuiux.com
 */
public class Regulat_TextView extends AppCompatTextView {

    public Regulat_TextView(Context context) {
        super(context);
        init();
    }

    public Regulat_TextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Regulat_TextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLineSpacing(0, 0.9f);

        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Cairo-Regular.ttf");
            setTypeface(tf);
        }
    }
}
