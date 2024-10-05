package Feed.ui.calendar.View;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.HashSet;
import java.util.Set;

public class MealIndicatorDecorator implements DayViewDecorator {
    private final Set<CalendarDay> dates;

    public MealIndicatorDecorator(Set<CalendarDay> dates) {
        this.dates = dates;
    }

    @Override
    public boolean shouldDecorate(@NonNull CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(@NonNull DayViewFacade view) {
        view.addSpan(new DotSpan(10, Color.BLACK));
    }
}
