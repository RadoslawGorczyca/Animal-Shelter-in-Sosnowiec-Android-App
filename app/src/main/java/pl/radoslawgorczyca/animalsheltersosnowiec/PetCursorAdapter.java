package pl.radoslawgorczyca.animalsheltersosnowiec;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import pl.radoslawgorczyca.animalsheltersosnowiec.data.PetContract.PetEntry;

/**
 * Created by Radek on 09-Jan-18.
 */

public class PetCursorAdapter extends CursorAdapter {


    public PetCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.grid_view_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView nameTextView = view.findViewById(R.id.animal_name);
        TextView statusTextView = view.findViewById(R.id.animal_status);
        ImageView statusIconView = view.findViewById(R.id.status_icon);

        int nameColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_NAME);
        int statusColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_STATUS);

        String petName = cursor.getString(nameColumnIndex);
        int petStatus = cursor.getInt(statusColumnIndex);

        nameTextView.setText(petName);

        if (petStatus == PetEntry.STATUS_ADOPTABLE){
            statusTextView.setText(R.string.status_adoptable);
            statusIconView.setImageResource(R.mipmap.green_circle);
        } else if (petStatus == PetEntry.STATUS_QUARANTINE) {
            statusTextView.setText(R.string.status_quarantine);
            statusIconView.setImageResource(R.mipmap.orange_circle);
        } else {
            statusTextView.setText(R.string.status_booked);
            statusIconView.setImageResource(R.mipmap.red_circle);
        }

    }
}
