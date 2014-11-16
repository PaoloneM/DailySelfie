package com.paolone.dailyselfie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;




/**
 * An activity representing a list of Selfies. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link SelfieDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link SelfieListFragment} and the item details
 * (if present) is a {@link SelfieDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link SelfieListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class DailySelfieMainActivity extends Activity
        implements SelfieListFragment.Callbacks {

	/*****************************************
	 *              CONSTANTS                *
	 *****************************************/
    // TAG for logging
	private static final String TAG = "Dailiy_Selfie";
	// Saved Instance key for the currently selected selfie item (UUID)
	private static final String SELECTED_SELFIE_KEY = "16f38740-6d99-11e4-9803-0800200c9a66";
	// Saved Instance key for the action bar home button back capability flag (UUID)
	private static final String ACTIONBAR_DISPLAY_OPTIONS = "4fdad5f0-6dd9-11e4-9803-0800200c9a66";

	/*****************************************
	 *           LOCAL VARIABLES             *
	 *****************************************/
	// layout type flag
    private boolean mTwoPane = false;
    // layout choice flag - for future uses
	private boolean mForceTwoPaneMode = false;
    // reference to Fragments
    private SelfieDetailFragment mSelfieDetailFragment = null;
    private SelfieListFragment mSelfieListFragment = null;
    // currently selected item pointer
    private String mLastSelectedPosition = null;
    

    /*****************************************
     *          ATIVITY LIFECYCLE            *
     *****************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         
        Log.i(TAG, "DailySelfieMainActivity.onCreate entered");
        
        // Choose which layout use and load it
        loadLayout(mForceTwoPaneMode);
        
        // Check layout style
        mTwoPane = isInTwoPaneMode();
        
        // Manage different layouts based on device size
        fragmentsInit(mTwoPane, savedInstanceState);

        // TODO: If exposing deep links into your app, handle intents here.
    }


    /**
     * Callback method from {@link SelfieListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {

    	Log.i(TAG, "DailySelfieMainActivity.onItemSelected entered");

        showSelfieDetailsNew(mTwoPane, id);
        
        if (!mTwoPane) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }
    
    // Menu selected item callback
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
        	getActionBar().setDisplayHomeAsUpEnabled(false);
        	getFragmentManager().popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Saved Instance Management
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        Log.i(TAG, "DailySelfieMainActivity.onSaveInstanceState entered");

		// save the current foreground feed
        savedInstanceState.putString(SELECTED_SELFIE_KEY, mLastSelectedPosition);
        savedInstanceState.putInt(ACTIONBAR_DISPLAY_OPTIONS, getActionBar().getDisplayOptions());
        Log.i(TAG, "Saved selfie index = " + mLastSelectedPosition);
        // as recommended by android basics training, always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);

    }
    
    /*****************************************
     *           SUPPORT METHODS             *
     *****************************************/

    // Simply load default main layout
    // TODO: improve managing user selection or Andorid choice override for mid-layer devices
    private void loadLayout(boolean forceTwoPaneMode) {
		// Load fragment container frame layout
        setContentView(R.layout.daily_selfie_main);
	}

	// Determines weather the layout must be composed of two panes:
    // if exists selfie_detail_container that means app is running on a large screen
	private boolean isInTwoPaneMode() {
		
        Log.i(TAG, "DailySelfieMainActivity.isInTwoPaneMode entered");

		return findViewById(R.id.selfie_list) != null;
	}

    // Manage different layouts based on device size
	private void fragmentsInit(boolean mIsInTwoPanesMode, Bundle savedInstanceBundle) {
		
        Log.i(TAG, "DailySelfieMainActivity.fragmentsInit entered");

		if (mIsInTwoPanesMode) {           

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((SelfieListFragment) getFragmentManager()
                    .findFragmentById(R.id.selfie_list))
                    .setActivateOnItemClick(true);
            
        } else {
        	
        	// Check if there is no saved instance before create a new list. If the state is 
        	// saved, the fragment is automatically re-created
        	if (savedInstanceBundle == null) {
        		
				// Create selfie list fragment
				mSelfieListFragment = new SelfieListFragment();
				// In single pane mode add the selfies' list Fragment to the container
				getFragmentManager()
						.beginTransaction()
						.add(R.id.selfie_fragment_container,
								mSelfieListFragment).commit();			
				
			} else {
				// Restore Action Bar View Options
				getActionBar().setDisplayOptions(savedInstanceBundle.getInt(ACTIONBAR_DISPLAY_OPTIONS));
			}
        	
        }
	}
	

	// depending on layout style, changes foreground fragment and updates its content 
	private void showSelfieDetailsNew(boolean mIsInTwoPanesMode, String selectedSelfieId) {
		
		Log.i(TAG, "DailySelfieMainActivity.showSelfieDetailsNew entered");

		// Create FeedFragment instance
		mSelfieDetailFragment = new SelfieDetailFragment();

        // Show the detail view in this activity by
        // adding or replacing the detail fragment using a fragment transaction.
		
		// Set arguments for the new fragment
        Bundle arguments = new Bundle();
        arguments.putString(SelfieDetailFragment.ARG_ITEM_ID, selectedSelfieId);
        mSelfieDetailFragment.setArguments(arguments);
        
        // Begin fragment transaction
        FragmentTransaction mFragmentTransaction = getFragmentManager().beginTransaction()
                .replace(R.id.selfie_fragment_container, mSelfieDetailFragment);
        
        // Is in single pane mode add transaction to backstack to allow back to list fragment
        if (!mIsInTwoPanesMode) {
        	
        	mFragmentTransaction.addToBackStack(null);
        	
        }
        
        // Commit changes to fragments
        mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        					.commit();

		// execute transaction now
	    getFragmentManager().executePendingTransactions();

        mLastSelectedPosition = selectedSelfieId;
     
	}
	
	// *** END OF CLASS ***
}
