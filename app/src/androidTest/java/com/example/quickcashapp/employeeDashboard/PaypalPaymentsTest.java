package com.example.quickcashapp.employeeDashboard;

import android.content.Intent;
import android.widget.EditText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.example.quickcashapp.BuildConfig;
import com.example.quickcashapp.R;
import com.example.quickcashapp.employerDashboard.SubActivityPayment;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalService;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class PaypalPaymentsTest {
    @Rule
    public ActivityTestRule<SubActivityPayment> activityRule = new ActivityTestRule<>(SubActivityPayment.class);

    private PayPalConfiguration payPalConfig;

    @Before
    public void setUp() {
        payPalConfig = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(BuildConfig.PAYPAL_CLIENT_ID);

        Intent intent = new Intent(activityRule.getActivity(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfig);
        activityRule.getActivity().startService(intent);
    }

    @After
    public void tearDown() {
        activityRule.getActivity().stopService(new Intent(activityRule.getActivity(), PayPalService.class));
    }

    @Test
    public void testPaymentProcess() {
        ActivityScenario<SubActivityPayment> scenario = ActivityScenario.launch(SubActivityPayment.class);

        scenario.onActivity(activity -> {
            EditText enterAmtET = activity.findViewById(R.id.enterAmtET);
            enterAmtET.setText("10.00");
        });

        Espresso.onView(ViewMatchers.withId(R.id.payNowBtn)).perform(ViewActions.click());
        //Incomplete
    }
}
