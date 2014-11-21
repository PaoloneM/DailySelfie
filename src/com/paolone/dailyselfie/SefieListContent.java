package com.paolone.dailyselfie;

import android.provider.MediaStore;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * The content of selfies' list
 */

public class SefieListContent {

    /*****************************************
     *              CONSTANTS                *
     *****************************************/
    // TAG for logging
    private static final String TAG = "Dailiy_Selfie";
    // Groups
    private static final int RECENT_GROUP = 0;
    private static final int MONTH_GROUP = 1;
    private static final int OLDER_GROUP = 2;
    // File paths
    private static final String DIR = MediaStore.Images.Media.DATA;
    private static final String FILE_RADIX = "Selfie_";

    /*****************************************
     *                FIELDS                 *
     *****************************************/

    private static int[] itemMap;
    private static ArrayList<SelfieItem> childList;


    /*****************************************
     *              CONSTRUCTOR              *
     *****************************************/

    public SefieListContent() {

        childList = new ArrayList<SelfieItem>();

        createDummyData(childList);

        for (SelfieItem mSelfie : childList){

            case mSelfie.getDate().compareTo(Calendar.getInstance().getTime()) > 7
                    itemMap

        }

    }


    /*****************************************
     *          EXPOSED  METHODS             *
     *****************************************/

    public int getSelfiesCount(){

        return childList.size();

    }

    /*****************************************
     *           SUPPORT METHODS             *
     *****************************************/

    private void createDummyData(ArrayList<SelfieItem> childList) {

        SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        Date mDate = null;

        try {
            mDate = format.parse("2014-01-01T01:01:02:03Z");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        File mFile = new File(DIR, (FILE_RADIX + mDate.toString()));

        childList.add(new SelfieItem(mDate, mFile));

        try {
            mDate = format.parse("2014-11-01T01:01:02:03Z");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        File mFile = new File(DIR, (FILE_RADIX + mDate.toString()));

        childList.add(new SelfieItem(mDate, mFile));

        try {
            mDate = format.parse("2014-11-21T01:01:02:03Z");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        File mFile = new File(DIR, (FILE_RADIX + mDate.toString()));

        childList.add(new SelfieItem(mDate, mFile));

    }

}
