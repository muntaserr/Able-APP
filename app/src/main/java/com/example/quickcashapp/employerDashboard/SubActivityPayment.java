package com.example.quickcashapp.employerDashboard;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.quickcashapp.BuildConfig;
import com.example.quickcashapp.R;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class SubActivityPayment extends AppCompatActivity {
    private static final String TAG = PaymentActivity.class.getName();
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private PayPalConfiguration payPalConfig;

    private EditText enterAmtET;
    private Button payNowBtn;
    private TextView paymentStatusTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_payment);
        init();
        configPayPal();
        startPayPalService(); // Starting PayPal service
        initActivityLauncher();
        setListeners();
    }

    /**
     * Initializes the UI components for entering payment amount, making a payment,
     * and displaying the payment status.
     */
    private void init() {
        enterAmtET = findViewById(R.id.enterAmtET);
        payNowBtn = findViewById(R.id.payNowBtn);
        paymentStatusTV = findViewById(R.id.paymentStatusTV);
    }

    /**
     * Configures PayPal settings, including setting the environment to sandbox
     * for testing and providing the client ID.
     */
    private void configPayPal() {
        payPalConfig = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(BuildConfig.PAYPAL_CLIENT_ID);
    }

    /**
     * Starts the PayPal service for processing payments.
     * Initializes and binds the PayPal configuration settings to the service.
     */
    private void startPayPalService() {
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfig);
        startService(intent);  // Start PayPal service when the activity starts
    }

    /**
     * Registers an activity result launcher to handle PayPal payment responses.
     * Processes payment confirmation details or displays the status if payment
     * is invalid or canceled.
     */
    private void initActivityLauncher() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        final PaymentConfirmation confirmation = result.getData().getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                        if (confirmation != null) {
                            try {
                                String paymentDetails = confirmation.toJSONObject().toString(4);
                                Log.i(TAG, paymentDetails);
                                JSONObject payObj = new JSONObject(paymentDetails);
                                String state = payObj.getJSONObject("response").getString("state");
                                paymentStatusTV.setText(String.format("Payment %s", state));
                            } catch (JSONException e) {
                                Log.e("Error", "Unexpected failure: ", e);
                            }
                        }
                    } else if (result.getResultCode() == PaymentActivity.RESULT_EXTRAS_INVALID) {
                        Log.d(TAG, "Payment Invalid");
                        paymentStatusTV.setText("Invalid payment. Please try again.");
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        Log.d(TAG, "Payment Cancelled");
                        paymentStatusTV.setText("Payment canceled.");
                    }
                });
    }

    /**
     * Sets up event listeners for UI components.
     * Specifically, sets up a click listener for the 'Pay Now' button.
     */
    private void setListeners() {
        payNowBtn.setOnClickListener(v -> processPayment());
    }

    /**
     * Initiates the payment process with PayPal using the entered amount.
     * Validates the entered amount and, if valid, launches a PayPal payment activity.
     */
    private void processPayment() {
        final String amount = enterAmtET.getText().toString();
        if (amount.isEmpty()) {
            paymentStatusTV.setText("Please enter a valid amount.");
            return;
        }

        final PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(amount), "CAD", "QuickCash Payment", PayPalPayment.PAYMENT_INTENT_SALE);
        final Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfig);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        activityResultLauncher.launch(intent);
    }

    /**
     * Stops the PayPal service when the activity is destroyed to free up resources.
     */
    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));  // Stop PayPal service when the activity is destroyed
        super.onDestroy();
    }

}
