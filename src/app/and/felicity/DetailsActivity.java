package app.and.felicity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

public class DetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		Bundle extras = getIntent().getExtras();
		if (extras.getString("name").equals("About")){
			RelativeLayout detailsrel = (RelativeLayout)findViewById(R.id.main_details);
			detailsrel.setBackgroundResource(R.drawable.about_back);
		}
		else if (extras.getString("name").equals("Contact")){
			RelativeLayout detailsrel = (RelativeLayout)findViewById(R.id.main_details);
			detailsrel.setBackgroundResource(R.drawable.contact_back);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
