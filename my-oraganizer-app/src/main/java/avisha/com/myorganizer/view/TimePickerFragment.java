package avisha.com.myorganizer.view;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by skulw on 1/28/17.
 */

public class TimePickerFragment extends DialogFragment {

    private TimePickerDialog.OnTimeSetListener listener;
    private TimePickerDialog timePickerDialog;
    private long date;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        if(date > 0) {
            c.setTimeInMillis(date);
        }
        int hourofDay = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(getActivity(), listener, hourofDay, minutes, false);
        // Create a new instance of DatePickerDialog and return it
        return timePickerDialog;
    }

    public void setOnTimeSetListener(TimePickerDialog.OnTimeSetListener listener) {
        this.listener = listener;
    }

}