package com.example.sohan.currencyconvertor.utils;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.WindowManager;

import com.example.sohan.currencyconvertor.R;

import java.lang.ref.WeakReference;


public class DialogUtils {

    private static DialogUtils sDialogUtil;
    private static ProgressDialog sProgressDialog;

    private WeakReference<Dialog> mDialogWeakReference = null;

    private DialogUtils() {

    }

    public static DialogUtils getInstance() {
        if (sDialogUtil == null) {
            sDialogUtil = new DialogUtils();
        }
        return sDialogUtil;
    }



    /**
     * Dialog to handle default alert messages.
     *
     * @param context                  Calling context.
     * @param title                    Title of dialog.
     * @param message                  Message to display in dialog.
     * @param positiveBtnText          Positive button text. Can be null for hiding this button in dialog.
     * @param positiveBtnClickListener Listener for handling positive button click.
     * @param negativeBtnText          Negative button text. Can be null for hiding this button in dialog.
     * @param negativeBtnClickListener Listener for handling negative button click.
     * @param cancelable               Boolean for blocking or non-blocking dialog.
     */
    public void showDefaultAlertDialog(Context context, String title, String message,
                                       String positiveBtnText, DialogInterface.OnClickListener positiveBtnClickListener,
                                       String negativeBtnText, DialogInterface.OnClickListener negativeBtnClickListener,
                                       boolean cancelable) {

        Dialog alertDialog = null;
        if (mDialogWeakReference != null) {
            alertDialog = mDialogWeakReference.get();
        }
        if (alertDialog == null || !alertDialog.isShowing()) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            if (!TextUtils.isEmpty(title)) {
                alertDialogBuilder.setTitle(title);
            }
            if (!TextUtils.isEmpty(message)) {
                alertDialogBuilder.setMessage(message);
            }
            alertDialogBuilder
                    .setCancelable(cancelable)
                    .setPositiveButton(positiveBtnText, positiveBtnClickListener)
                    .setNegativeButton(negativeBtnText, negativeBtnClickListener);
            alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    /**
     * For cancelling dialog associated with mDefaultAlertDialog instance.
     */
    public void cancelDefaultAlertDialog() {
        Dialog alertDialog = null;
        if (mDialogWeakReference != null) {
            alertDialog = mDialogWeakReference.get();
        }
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
        mDialogWeakReference = null;
    }

    public void showNetworkErrorDialog(Context context) {
        showDefaultAlertDialog(context, context.getString(R.string.no_network)
                , context.getString(R.string.please_check_internet_connection), context.getString(R.string.ok)
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }, null, null, false);
    }

    public void showNetworkErrorDialog(Context context, DialogInterface.OnClickListener onClickListener) {
        showDefaultAlertDialog(context, context.getString(R.string.no_network)
                , context.getString(R.string.please_check_internet_connection), context.getString(R.string.ok)
                , onClickListener, null, null, false);
    }

    /**
     * Creates and shows progress dialog with given message and sets your
     * OnCancelListener
     *
     * @param msg
     */
    public  void displayProgressDailog(Context context, String msg) {
        if (sProgressDialog != null && sProgressDialog.isShowing())
            return;

        if (context != null) {
            sProgressDialog = new ProgressDialog(context);
            sProgressDialog.setMessage(msg);
            sProgressDialog.setCancelable(false);
            try {
                sProgressDialog.show();
            } catch (WindowManager.BadTokenException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Dismisses progress dialog.
     */
    public  void hideProgressDialog() {
        if (sProgressDialog != null && sProgressDialog.isShowing()) {
            try {
                sProgressDialog.dismiss();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

            sProgressDialog = null;
        }
    }
}
