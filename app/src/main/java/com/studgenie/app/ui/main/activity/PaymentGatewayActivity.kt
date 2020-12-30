package com.studgenie.app.ui.main.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.studgenie.app.R
import kotlinx.android.synthetic.main.activity_payment_gateway.*
import org.json.JSONObject


class PaymentGatewayActivity : AppCompatActivity(), PaymentResultListener {

    val TAG:String = PaymentGatewayActivity::class.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_gateway)

        //If a user logs out (include this in the log out workflow)
        //Checkout.clearUserData(this)

        //To ensure faster loading of the Checkout form
        Checkout.preload(applicationContext)

        button_payment_gateway.setOnClickListener{
            startPayment()
        }
    }

    private fun startPayment(){
        val checkout = Checkout()
        //Note: It is recommended to send the API Key ID from your server-side as app related metadata fetch.
        checkout.setKeyID("rzp_test_idHtQSLjf1EQPo")
        try {
            val options = JSONObject()
            options.put("name", "StudGenie")
            options.put("description", "Education Charges")
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("theme.color", "#D9391D")
            options.put("currency", "INR")
            //Remove in test mode
            //options.put("order_id", "order_DBJOWzybf0sJbb");
            options.put("amount", "99900")//pass amount in currency subunits

            val prefill = JSONObject()
            prefill.put("email", "gaurav.kumar@example.com")
            prefill.put("contact", "9876543210")

            options.put("prefill", prefill)
            checkout.open(this, options)
        } catch (e: Exception) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e)
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this,p0,Toast.LENGTH_SHORT).show()
        Log.d(TAG,p0!!)
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this,p1+"\n"+p0,Toast.LENGTH_SHORT).show()
        Log.e(TAG,p1+"\n"+p0)
    }
}