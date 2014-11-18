package com.paolone.dailyselfie;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;


import com.paolone.dailyselfie.dummy.DummyContent;

/**
 * A list fragment representing a list of Selfies. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link SelfieDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class SelfieListFragment extends Fragment {

	/*****************************************
	 *              CONSTANTS                *
	 *****************************************/
    // TAG for logging
	private static final String TAG = "Dailiy_Selfie";

    // The serialization (saved instance state) Bundle key representing the activated item position.
    // Only used on tablets.
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /*****************************************
     *                FIELDS                 *
     *****************************************/

    // The fragment's current callback object, which is notified of list item clicks.
    private Callbacks mCallbacks = sDummyCallbacks;

    // The current activated item position. Only used on tablets.
    private int mActivatedPosition = ListView.INVALID_POSITION;

    // A callback interface that all activities containing this fragment must implement.
    // This mechanism allows activities to be notified of item selections.
    public interface Callbacks {
        // Callback for when an item has been selected.
        public void onItemSelected(String id);
    }

    // A dummy implementation of the {@link Callbacks} interface that does nothing.
    // Used only when this fragment is not attached to an activity.
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };

    // Mandatory empty constructor for the fragment manager to instantiate the* fragment
    // (e.g. upon screen orientation changes).
    public SelfieListFragment() {
    	Log.i(TAG, "SelfieListFragment constructor entered");
    }

    private ExpandableListView selfiesExpandableList;
    private View rootView;

    /*****************************************
     *          FRAGMENT LIFECYCLE           *
     *****************************************/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        Log.i(TAG, "SelfieListFragment.onCreate entered");
        /*
        // TODO: replace with a real list adapter.
        selfiesExpandableList.setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                DummyContent.ITEMS));*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_selfies_list, container, false);

        return rootView;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i(TAG, "SelfieListFragment.onViewCreated entered");

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
        
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);

        selfiesExpandableList = (ExpandableListView) view.findViewById(R.id.SelfiesExpandableView);
        selfiesExpandableList.setAdapter(new ExpandableListAdapter(DummyContent.groupData, DummyContent.childData, view.getContext()));

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Log.i(TAG, "SelfieListFragment.onAttach entered");

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Log.i(TAG, "SelfieListFragment.onDetach entered");

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }
/*
    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
       // super.onListItemClick(listView, view, position, id);

        Log.i(TAG, "SelfieListFragment.onListItemClick entered");
        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mCallbacks.onItemSelected(DummyContent.ITEMS.get(position).id);
    }*/

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.i(TAG, "SelfieListFragment.onSaveInstanceState entered");

        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /*****************************************
     *           SUPPORT METHODS             *
     *****************************************/

    // Turns on activate-on-click mode. When this mode is on, list items will be given the
    // 'activated' state when touched.
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        selfiesExpandableList.setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            selfiesExpandableList.setItemChecked(mActivatedPosition, false);
        } else {
            selfiesExpandableList.setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    // *** END OF CLASS ***

}

