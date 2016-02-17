package com.example.omiplekevin.customprogressdialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 *
 * Created by OMIPLEKEVIN on 02/10/2015.
 */
public class CustomProgressDialog extends Dialog {

    View mCustomView;

    TextView mDialogTitle, mFilename, mPercentPerItem, mPercentTotal, mOverall;
    ProgressBar mProgressPerItem, mProgressTotal;
    Context mContext;

    int TOTAL_MAX, ITEM_MAX;

    public CustomProgressDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        //text views
        mCustomView = View.inflate(this.mContext, layoutResID, null);
        mDialogTitle = (TextView) findViewById(R.id.dialogTitle);
        mFilename = (TextView) findViewById(R.id.filename);
        mPercentPerItem = (TextView) findViewById(R.id.percentPerItem);
        mPercentTotal = (TextView) findViewById(R.id.percentTotal);
        mOverall = (TextView) findViewById(R.id.nOutOfn);

        //progress bars
        mProgressPerItem = (ProgressBar) findViewById(R.id.progressPerItem);
        mProgressTotal = (ProgressBar) findViewById(R.id.progressTotal);
    }

    /**
     * Sets max progress of current item to the item's progress bar
     * @param maxValue max progress of the current item
     */
    public void setPrimaryProgressMax(int maxValue) {
        mProgressPerItem.setMax(maxValue);
        mProgressPerItem.invalidate();

        ITEM_MAX = maxValue;
    }

    /**
     * Sets current item's transfer progress to the item's progress bar
     * @param progressValue current item's transfer progress
     */
    public void setPrimaryProgress(int progressValue) {
        mProgressPerItem.setProgress(progressValue);
        mProgressPerItem.invalidate();

        float primaryPercentage = (float) progressValue / (float) ITEM_MAX;
        primaryPercentage = primaryPercentage * 100;
        mPercentPerItem.setText(String.format("%.1f",primaryPercentage) + "%");
        mPercentPerItem.invalidate();
    }

    /**
     * Sets max progress of the whole items to the total progress bar
     * @param maxValue max progress of the current transfer session
     */
    public void setSecondaryProgressMax(int maxValue) {
        mProgressTotal.setMax(maxValue);
        mProgressTotal.invalidate();

        TOTAL_MAX = maxValue;
    }

    /**
     * Sets current total transfer progress to the total progress bar
     * @param progressValue current total transfer progress
     */
    public void setSecondaryProgress(int progressValue) {
        mProgressTotal.setProgress(progressValue);
        mProgressTotal.invalidate();

        float totalPercentage = (float) progressValue / (float) TOTAL_MAX;
        totalPercentage = totalPercentage * 100;
        mPercentTotal.setText(String.format("%.1f",totalPercentage) + "%");
        mPercentTotal.invalidate();
    }

    /**
     * Sets title for the dialog
     * @param charSequence title of the dialog
     */
    public void setCustomTitle(final CharSequence charSequence) {
        mDialogTitle.setText(charSequence);
        mDialogTitle.invalidate();
    }

    /**
     * Sets current item's name
     * @param charSequence name of the item being transferred
     */
    public void setCurrentItem(CharSequence charSequence) {
        mFilename.setText(charSequence);
        mFilename.invalidate();
    }

    /**
     * Sets current progress CONTRA overall items
     * @param finished current progress
     * @param total overall items
     */
    public void setOverAllItems(int finished, int total){
        mOverall.setText(finished + "/" + total);
        mOverall.invalidate();
    }
}
