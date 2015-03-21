package awdal.com.hackath2on.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by zenbook on 21/03/15.
 */
public class CustomTextViewRegular extends TextView {

    public CustomTextViewRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    public CustomTextViewRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public CustomTextViewRegular(Context context) {
        super(context);
        init();
    }


    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "Roboto-Regular.ttf");
        setTypeface(tf);
    }

}
