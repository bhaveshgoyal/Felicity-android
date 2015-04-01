package app.and.felicity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends FragmentActivity {
	public static Context context;
	private Animator mCurrentAnimator;
	public View currentExpandedView;
	// The system "short" animation time duration, in milliseconds. This
	// duration is ideal for subtle animations or animations that occur
	// very frequently.
	private int mShortAnimationDuration;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = HomeActivity.this;
		setContentView(R.layout.activity_home);
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		CircularImageView events = (CircularImageView)findViewById(R.id.cir_events);

		final CircularImageView about = (CircularImageView)findViewById(R.id.cir_about);


		final CircularImageView schedule = (CircularImageView)findViewById(R.id.cir_schedule);

		final CircularImageView nights = (CircularImageView)findViewById(R.id.cir_nights);

		final CircularImageView sponsors = (CircularImageView)findViewById(R.id.cir_sponsors);


		final CircularImageView contact = (CircularImageView)findViewById(R.id.cir_contact);
		//		
		//		events.setBorderColor(Color.WHITE);
		//		events.setBorderWidth(10);
		//		events.setSelectorColor(Color.CYAN);
		//		events.setSelectorStrokeColor(Color.BLUE);
		//		events.setSelectorStrokeWidth(10);
		//		events.setMinimumWidth(width/2);
		//		events.setMinimumHeight(width/2);

		events.getLayoutParams().height = width/3;
		events.getLayoutParams().width = width/3;
		about.getLayoutParams().height = width/3;
		about.getLayoutParams().width = width/3;
		schedule.getLayoutParams().height = width/3;
		schedule.getLayoutParams().width = width/3;
		nights.getLayoutParams().height = width/3;
		nights.getLayoutParams().width = width/3;
		sponsors.getLayoutParams().height = width/3;
		sponsors.getLayoutParams().width = width/3;
		contact.getLayoutParams().height = width/3;
		contact.getLayoutParams().width = width/3;



		//		FrameLayout home_main = (FrameLayout)findViewById(R.id.container);
		//		home_main.getBackground().setAlpha(225);
		ParallaxImageView mBackground = (ParallaxImageView) findViewById(R.id.backgroundimg);

		// Register a SensorManager to begin effect
		mBackground.registerSensorManager();

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		events.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent sub = new Intent(getApplicationContext(),EventsActivity.class);
				startActivity(sub);

			}
		});
		//		 final View thumb1View = findViewById(R.id.thumb_button_1);
		//	        thumb1View.setOnClickListener(new View.OnClickListener() {
		//	            @Override
		//	            public void onClick(View view) {
		//	            }
		//	        });

		about.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//				Bundle b = new Bundle();
				//				b.putString("name", "About");
				//				Intent sub = new Intent(getApplicationContext(),DetailsActivity.class);
				//				sub.putExtras(b);
				//				startActivity(sub);
				
				TextView info = (TextView)findViewById(R.id.info_content);
				info.setText("Felicity is the annual technical and cultural fest of IIIT-H.\nIt is a time of enjoyment, revelation, freedom, and a sense of celebration\n\nIncludes technical, cultural and literary events, Major nights, talks, workshops and performances.\n\nWe, at IIIT-H, believe in giving back to the society and use Felicity as a medium to serve this motive and pickup various social initiatives.\nFelicity 2015 is based upon the theme of INTO THE UNKNOWN, and we will try to showcase futuristic-space-revolutionizing-the-world.\nWe will encourage our audience to explore the unknown and add a whole new dimension to life. ");
				
zoomImageFromThumb(about, R.drawable.about_back, (LinearLayout)findViewById(R.id.inflated_info));

			}
		});
		mShortAnimationDuration = getResources().getInteger(
				android.R.integer.config_shortAnimTime);

		contact.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				Bundle b = new Bundle();
//				b.putString("name", "Contact");
//				Intent sub = new Intent(getApplicationContext(),DetailsActivity.class);
//				sub.putExtras(b);
//				startActivity(sub);

				zoomImageFromThumb(about, R.drawable.contact_back, (LinearLayout)findViewById(R.id.inflated_about));
			}
		});

		sponsors.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent sub = new Intent(getApplicationContext(),SubActivity.class);
				startActivity(sub);

				Toast.makeText(getApplicationContext(), "Coming Soon ...", Toast.LENGTH_SHORT).show();

			}
		});

		nights.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				Intent sub = new Intent(getApplicationContext(),SubActivity.class);
//				startActivity(sub);
				Toast.makeText(getApplicationContext(), "Coming Soon ...", Toast.LENGTH_SHORT).show();
			}
		});
		schedule.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				zoomImageFromThumb(schedule, R.drawable.schedule_back, (LinearLayout)findViewById(R.id.inflated_schedule));
				ScheduleFillerHelper.fillSchedule((ListView)findViewById(R.id.schedule_list_view));
				// ProgressDialog.show(getBaseContext(), "dialog title", "dialog message", true);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
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
		else if (id == R.id.action_facebook) {
			Intent fb = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(fb);
			return true;
		}


		return super.onOptionsItemSelected(item);
	}


	private void zoomImageFromThumb(final View thumbView, int imageResId, final LinearLayout expandedImageView) {
		// If there's an animation in progress, cancel it
		// immediately and proceed with this one.
		if (mCurrentAnimator != null) {
			mCurrentAnimator.cancel();
		}
		//     	Display display = getWindowManager().getDefaultDisplay();
		//		Point size = new Point();
		//		display.getSize(size);
		//		int width = size.x;
		//		GradientDrawable bgshape = (GradientDrawable)expandedImageView.getBackground();
		//		bgshape.setCornerRadius(10);
		// Load the high-resolution "zoomed-in" image.
		//	    final LinearLayout expandedImageView = (LinearLayout) findViewById(
		//	            R.id.expanded_image);
		//	    expandedImageView.setBackgroundColor(Color.WHITE);

		// Calculate the starting and ending bounds for the zoomed-in image.
		// This step involves lots of math. Yay, math.
		final Rect startBounds = new Rect();
		final Rect finalBounds = new Rect();
		final Point globalOffset = new Point();
		
		currentExpandedView = (View) expandedImageView;
		// The start bounds are the global visible rectangle of the thumbnail,
		// and the final bounds are the global visible rectangle of the container
		// view. Also set the container view's offset as the origin for the
		// bounds, since that's the origin for the positioning animation
		// properties (X, Y).
		thumbView.getGlobalVisibleRect(startBounds);
		findViewById(R.id.container)
		.getGlobalVisibleRect(finalBounds, globalOffset);
		startBounds.offset(-globalOffset.x, -globalOffset.y);
		finalBounds.offset(-globalOffset.x + 16, -globalOffset.y + 20);

		// Adjust the start bounds to be the same aspect ratio as the final
		// bounds using the "center crop" technique. This prevents undesirable
		// stretching during the animation. Also calculate the start scaling
		// factor (the end scaling factor is always 1.0).
		float startScale;
		if ((float) finalBounds.width() / finalBounds.height()
				> (float) startBounds.width() / startBounds.height()) {
			// Extend start bounds horizontally
			startScale = (float) startBounds.height() / finalBounds.height();
			float startWidth = startScale * finalBounds.width();
			float deltaWidth = (startWidth - startBounds.width()) / 2;
			startBounds.left -= deltaWidth;
			startBounds.right += deltaWidth;
		} else {
			// Extend start bounds vertically
			startScale = (float) startBounds.width() / finalBounds.width();
			float startHeight = startScale * finalBounds.height();
			float deltaHeight = (startHeight - startBounds.height()) / 2;
			startBounds.top -= deltaHeight;
			startBounds.bottom += deltaHeight;
		}

		// Hide the thumbnail and show the zoomed-in view. When the animation
		// begins, it will position the zoomed-in view in the place of the
		// thumbnail.
		thumbView.setAlpha(0f);
		expandedImageView.setVisibility(View.VISIBLE);

		// Set the pivot point for SCALE_X and SCALE_Y transformations
		// to the top-left corner of the zoomed-in view (the default
		// is the center of the view).
		expandedImageView.setPivotX(0f);
		expandedImageView.setPivotY(0f);

		// Construct and run the parallel animation of the four translation and
		// scale properties (X, Y, SCALE_X, and SCALE_Y).
		AnimatorSet set = new AnimatorSet();
		set
		.play(ObjectAnimator.ofFloat(expandedImageView, View.X,
				startBounds.left, finalBounds.left))
				.with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
						startBounds.top, finalBounds.top))
						.with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
								startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
										View.SCALE_Y, startScale, 1f));
		set.setDuration(mShortAnimationDuration);
		set.setInterpolator(new DecelerateInterpolator());
		set.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				mCurrentAnimator = null;
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				mCurrentAnimator = null;
			}
		});
		set.start();
		mCurrentAnimator = set;

		// Upon clicking the zoomed-in image, it should zoom back down
		// to the original bounds and show the thumbnail instead of
		// the expanded image.
		final float startScaleFinal = startScale;
		expandedImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mCurrentAnimator != null) {
					mCurrentAnimator.cancel();
				}

				// Animate the four positioning/sizing properties in parallel,
				// back to their original values.
				//	         	Display display = getWindowManager().getDefaultDisplay();
				//	    		Point size = new Point();
				//	    		display.getSize(size);
				//	    		int width = size.x;
				//	    		GradientDrawable bgshape = (GradientDrawable)expandedImageView.getBackground();
				//	    		bgshape.setCornerRadius(width/2);
				AnimatorSet set = new AnimatorSet();
				set.play(ObjectAnimator
						.ofFloat(expandedImageView, View.X, startBounds.left))
						.with(ObjectAnimator
								.ofFloat(expandedImageView, 
										View.Y,startBounds.top))
										.with(ObjectAnimator
												.ofFloat(expandedImageView, 
														View.SCALE_X, startScaleFinal))
														.with(ObjectAnimator
																.ofFloat(expandedImageView, 
																		View.SCALE_Y, startScaleFinal));
				set.setDuration(mShortAnimationDuration);

				set.setInterpolator(new AccelerateInterpolator());
				set.addListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						thumbView.setAlpha(1f);
						expandedImageView.setVisibility(View.GONE);
						mCurrentAnimator = null;
					}

					@Override
					public void onAnimationCancel(Animator animation) {
						thumbView.setAlpha(1f);
						expandedImageView.setVisibility(View.GONE);
						mCurrentAnimator = null;
					}
				});
				set.start();
				mCurrentAnimator = set;
			}
		});
	}
	@Override
	public void onBackPressed() {
		Log.d("mytag", "back pressed!");
		if(currentExpandedView!=null) {
			if(currentExpandedView.getVisibility() == View.VISIBLE){
				currentExpandedView.performClick();
				return;
			}
		}
		super.onBackPressed();
	}
}
