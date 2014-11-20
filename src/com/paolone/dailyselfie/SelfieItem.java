package com.paolone.dailyselfie;

import android.graphics.Bitmap;
import android.location.Location;

import java.io.File;
import java.util.Date;

/**
 * The class for Selfie Item
 */
public class SelfieItem {

    /*****************************************
     *              CONSTANTS                *
     *****************************************/


    /*****************************************
     *                FIELDS                 *
     *****************************************/

    private Date mDate;
    private Location mLocation;
    private Bitmap mThumb;
    private File mFile;

    /*****************************************
     *              CONSTRUCTOR              *
     *****************************************/

    public SelfieItem(Date date, File file){

        this(date, null, file);

    }

    public SelfieItem(Date date, Location location, File file){

        mDate = date;

        if (mLocation != null) {
            mLocation = location;
        }
        mFile = file;

    }

    /*****************************************
     *          EXPOSED  METHODS             *
     *****************************************/

    public Date getDate(){
        return mDate;
    }

    public Location getLocation(){
        return mLocation;
    }

    public File getFile(){
        return mFile;
    }

    public Bitmap getThumb(){
        return mThumb;
    }

    /*****************************************
     *           SUPPORT METHODS             *
     *****************************************/


}
