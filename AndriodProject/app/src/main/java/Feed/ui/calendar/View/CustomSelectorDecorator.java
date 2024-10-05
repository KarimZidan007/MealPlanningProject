package Feed.ui.calendar.View;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public class CustomSelectorDecorator implements DayViewDecorator {

    private final CalendarDay selectedDay;
    private final ShapeDrawable circleDrawable;

    public CustomSelectorDecorator(Context context, CalendarDay selectedDay) {
        this.selectedDay = selectedDay;
        // Create a drawable for the circle with the desired color (orange)
        circleDrawable = new ShapeDrawable(new OvalShape());
        circleDrawable.getPaint().setColor(Color.parseColor("#FB8C00")); // Orange color
        circleDrawable.getPaint().setStyle(Paint.Style.FILL);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        // Only decorate the selected day
        return selectedDay != null && day.equals(selectedDay);
    }

    @Override
    public void decorate(DayViewFacade view) {
        // Set the circle background for the selected day
        view.setSelectionDrawable(circleDrawable);
    }
}
