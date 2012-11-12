package cz.uhk.hidoor;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;


public class ListDoorLoader extends ListActivity {
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.listdoor);
        ListAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, getDoors());
        setListAdapter(adapter);
       //getListView().setTextFilterEnabled(true);
	}
	
	private List<String> getDoors() {
		List<String> doors = new ArrayList<String>();
		DatabaseHandler db = new DatabaseHandler(this);
		List<Door> listDoor = db.getAll();
		  for (int i=0; i < listDoor.size(); i++) {
			  doors.add(listDoor.get(i).getName().toString());
		  }
		return doors;
	}
 
}