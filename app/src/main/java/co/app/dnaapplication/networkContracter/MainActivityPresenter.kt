package co.app.dnaapplication.networkContracter

import android.app.Dialog
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import android.widget.TextView
import co.app.dnaapplication.R
import co.app.dnaapplication.appBase.MainActivity
import co.app.dnaapplication.callbacks.OkCancelCallback
import co.app.dnaapplication.constants.ApiConstants
import co.app.dnaapplication.utilities.AppDetails
import co.app.dnaapplication.utilities.AppDetails.Companion.context
import co.app.dnaapplication.utilities.AppSharedPreferences


import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityPresenter(val mView: MainContractor.View) : MainContractor.Presenter {
    private var progressDialog: ProgressDialog? = null
    //private var progressDialog: Dialog? = null
    private var apiCallingCount=0
    override fun onClick(
        caseConstants: ApiConstants,
        parameters: Array<String>,
        context: Context,
        showProgressBar: Boolean?
    ) {
        if (showProgressBar!!) {

        }
        val retrofit = ApiClient.client
        val requestInterface = retrofit.create(ApiInterface::class.java)
        val accessTokenCall: Call<JsonObject>

        when (caseConstants) {
            ApiConstants.dna -> {
                accessTokenCall = requestInterface.searchDnaList(parameters[0])
                callApi(accessTokenCall, context, ApiConstants.dna)
            }
        }
    }

    private fun callApi(
        accessTokenCall: Call<JsonObject>,
        context: Context,
        view: ApiConstants
    ) {
       // if (isNetworkAvailable(context)) {
            accessTokenCall.enqueue(object : Callback<JsonObject> {
                override fun onResponse(
                    call: Call<JsonObject>,
                    response: Response<JsonObject>
                ) {
                    apiCallingCount --
                    if (progressDialog != null && progressDialog!!.isShowing)
                        try {
                            if(apiCallingCount == 0) {
                                progressDialog!!.dismiss()
                            }
                        } catch (e: Exception) {
                            Log.e(ContentValues.TAG, "onResponse: $e")
                        }

                    if (response.code() != null) {

                        when (response.code()) {
                            200 -> {
                                mView.setViewData((response.body()!!.toString()), view)
                            }
                            403 -> {
                                showErrorDialog(
                                    response.body()!!.get("message").asString,
                                    context
                                )
                            }
                            500 -> {
                                showErrorDialog(
                                    context!!.getString(R.string.server_not_responding),
                                    context
                                )
                            }
                            else -> {
                                mView.setViewData((response.body()!!.toString()), view)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    try {
                        apiCallingCount --
                        Log.e("count",apiCallingCount.toString())
                        if (progressDialog != null && progressDialog!!.isShowing)
                            if(apiCallingCount == 0) {
                                progressDialog!!.dismiss()
                            }
                        showErrorDialog(
                            context!!.getString(R.string.server_not_responding),
                            context
                        )
                    } catch (e: Exception) {
                        Log.e(ContentValues.TAG, "onFailure: $e")
                    }
                    t.printStackTrace()
                }
            })
        /*} else {
            //apiCallingCount --
            if (progressDialog!!.isShowing)
                try {
                    progressDialog!!.dismiss()
                } catch (e: Exception) {
                    Log.e(ContentValues.TAG, "onResponse: $e")

                }

        }*/
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).state == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(
            ConnectivityManager.TYPE_MOBILE
        ).state == NetworkInfo.State.CONNECTING || connectivityManager.getNetworkInfo(
            ConnectivityManager.TYPE_WIFI
        ).state == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(
            ConnectivityManager.TYPE_WIFI
        ).state == NetworkInfo.State.CONNECTING
    }

    private fun showErrorDialog(msg: String, context: Context) {
        okCancelDialog(msg,
            "Ok",
            "",
            object : OkCancelCallback {
                override fun onOkClick() {
                    AppSharedPreferences.clearUserData()
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                }

                override fun onCancelClick() {
                }
            })
    }

    private fun okCancelDialog(
        message: String,
        okText: String,
        cancelText: String,
        okCancel: OkCancelCallback
    ) {
        val alertDialog = android.app.AlertDialog.Builder(AppDetails.activity).create()
        val view = View.inflate(context, R.layout.dialog_ok_cancel_newtwork, null)
        alertDialog.setView(view)
        alertDialog.setCancelable(false)
        val msg = view.findViewById<TextView>(R.id.msg)
        msg.text = message
        val sureBtn = view.findViewById<TextView>(R.id.sureBtn)
        sureBtn.text = okText
        val cancelBtn = view.findViewById<TextView>(R.id.cancelBtn)
        cancelBtn.text = cancelText
        val title = view.findViewById<TextView>(R.id.title)
        title.visibility = View.GONE

        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        sureBtn.setOnClickListener { v ->
            okCancel.onOkClick()
            alertDialog.dismiss()
        }
        cancelBtn.setOnClickListener { v ->
            okCancel.onCancelClick()
            alertDialog.dismiss()
        }
        alertDialog.show()
    }



    private fun initAndShowProgressBar(context: Context) {
        try {
            if (progressDialog != null) {
                progressDialog!!.dismiss()
            }
            progressDialog = null
            progressDialog = ProgressDialog(context)
            progressDialog!!.setMessage("Please wait...")
            progressDialog!!.isIndeterminate = true
            progressDialog!!.setCancelable(false)
            progressDialog!!.show()
        } catch (e: Exception) {
            Log.e(ContentValues.TAG, "initAndShowProgressBar: $e")
        }

    }
}