package milan.common.widget.recyclerview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;

public class AutoMeasureDisabledLinearLayoutManager extends LinearLayoutManager {

    public AutoMeasureDisabledLinearLayoutManager(Context context) {
        super(context);
    }

    public AutoMeasureDisabledLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public AutoMeasureDisabledLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isAutoMeasureEnabled() {
        return false;
    }
}
