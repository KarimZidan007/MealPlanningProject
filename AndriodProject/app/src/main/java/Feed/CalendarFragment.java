package Feed;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sidechefproject.R;

public class CalendarFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        // Find the CalendarView in the layout
        CalendarView calendarView = view.findViewById(R.id.calendarView);

        // Set a listener to capture the selected date
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Display selected date
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                Toast.makeText(getContext(), "Selected date: " + selectedDate, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
