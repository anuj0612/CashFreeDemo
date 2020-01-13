package com.android.cashfreedemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.gocashfree.cashfreesdk.CFPaymentService
import com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_EMAIL
import com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_PHONE
import com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_NAME
import com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_NOTE
import com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_AMOUNT
import com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_ID
import com.gocashfree.cashfreesdk.CFPaymentService.PARAM_APP_ID

class MainActivity : AppCompatActivity() {
    lateinit var button_pg: Button
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_pg = findViewById(R.id.button_pg)
        button_pg.setOnClickListener {
            doPayment()
        }
    }

    private fun doPayment() {
        /*
         * token can be generated from your backend by calling cashfree servers. Please
         * check the documentation for details on generating the token.
         * READ THIS TO GENERATE TOKEN: https://bit.ly/2RGV3Pp
         */
        val token = "TOKEN"


        /*
         * stage allows you to switch between sandboxed and production servers
         * for CashFree Payment Gateway. The possible values are
         *
         * 1. TEST: Use the Test server. You can use this service while integrating
         *      and testing the CashFree PG. No real money will be deducted from the
         *      cards and bank accounts you use this stage. This mode is thus ideal
         *      for use during the development. You can use the cards provided here
         *      while in this stage: https://docs.cashfree.com/docs/resources/#test-data
         *
         * 2. PROD: Once you have completed the testing and integration and successfully
         *      integrated the CashFree PG, use this value for stage variable. This will
         *      enable live transactions
         */
        val stage = "TEST"

        /*
         * appId will be available to you at CashFree Dashboard. This is a unique
         * identifier for your app. Please replace this appId with your appId.
         * Also, as explained below you will need to change your appId to prod
         * credentials before publishing your app.
         */
        val appId = "YOUR_APP_ID"
        val orderId = "YOUR_ORDER_ID"
        val orderAmount = "ORDER_AMOUNT"
        val orderNote = "ORDER_NOTE"
        val customerName = "CUSTOMER_NAME"
        val customerPhone = "CUSTOMER_PHONE_NUMBER"
        val customerEmail = "CUSTOMER_EMAIL_ID"

        val params = HashMap<String, String>()

        params[PARAM_APP_ID] = appId
        params[PARAM_ORDER_ID] = orderId
        params[PARAM_ORDER_AMOUNT] = orderAmount
        params[PARAM_ORDER_NOTE] = orderNote
        params[PARAM_CUSTOMER_NAME] = customerName
        params[PARAM_CUSTOMER_PHONE] = customerPhone
        params[PARAM_CUSTOMER_EMAIL] = customerEmail

        for (entry in params.entries) {
            Log.d("CFSKDSample", "" + entry.key + " " + entry.value)
        }

        val cfPaymentService = CFPaymentService.getCFPaymentServiceInstance()
        cfPaymentService.setOrientation(applicationContext, 0)

        // Use the following method for initiating Payments
        // First color - Toolbar background
        // Second color - Toolbar text and back arrow color
        cfPaymentService.doPayment(this, params, token, stage, "#000000", "#FFFFFF")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            val bundle = data.extras
            if (bundle != null) {
                for (key in bundle.keySet()) {
                    if (bundle.getString(key) != null) {
                        Log.d(TAG, key + " : " + bundle.getString(key))
                    }
                }
            }
        }
    }
}

