package com.example.myloans;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myloans.utils.PopUp;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import static com.example.myloans.utils.SharedFunctions.promptLogout;
import static com.example.myloans.utils.SharedFunctions.shareSocial;
import static com.example.myloans.utils.SharedFunctions.showBasic;

public class MainActivity extends AppCompatActivity {

    //    BottomSheetLayout bottomSheet;
    Intent fromLogin ;
    AdView mAdView;
    AdView mAdView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fromLogin = getIntent();

        mAdView = findViewById(R.id.adView);
        loadAdvert(mAdView);

        mAdView1 = findViewById(R.id.adView1);
        loadAdvert(mAdView1);



//        bottomSheet = findViewById(R.id.bottomsheet);


    }

    private void loadAdvert(AdView adView) {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }


    /**
     * Process clients loan application
     *
     * @param view
     */
    @SuppressLint("ResourceAsColor")
    public void applyLoan(View view) {
        // check if a loan is already applied
        if (null != fromLogin.getStringExtra("P_AMOUNT") || "".equalsIgnoreCase(fromLogin.getStringExtra("P_AMOUNT"))) {
            PopUp.popApplyLoan(findViewById(android.R.id.content), this, fromLogin.getStringExtra("F_NAME"));
        } else {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            alertDialogBuilder.setMessage("Sorry, your previous loan" + (null != fromLogin.getStringExtra("P_AMOUNT") ? "of Ksh. " + fromLogin.getStringExtra("P_AMOUNT") : "" ) + " is under review. " +
                    "\n Cancel and apply for another loan?");
            alertDialogBuilder.setPositiveButton("Yes!",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            PopUp.popApplyLoan(findViewById(android.R.id.content), MainActivity.this, fromLogin.getStringExtra("F_NAME"));
                        }
                    });
            alertDialogBuilder.setNegativeButton("No!",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setCancelable(true);
            alertDialog.show();

        }
//        bottomSheet.showWithSheetView(LayoutInflater.from(this).inflate(R.layout.appply_loan_layout, bottomSheet, false));
    }

    /**
     * Process customer's airtime purchase
     *
     * @param view
     */
    public void buyAirtime(View view) {
        showBasic(this, "You do not have funds in your account. Apply for a loan to get funds in your account!!");
    }

    /**
     * Transfer funds to Mpesa
     *
     * @param view
     */
    public void toMpesa(View view) {
        showBasic(this, "You do not have funds in your account. Apply for a loan to get funds in your account!!");
    }

    /**
     * Connect to facebook
     *
     * @param view
     */
    public void toFacebook(View view) {
        shareSocial(this, "com.facebook");
    }

    /**
     * Connect to Twitter
     *
     * @param view
     */
    public void toTwitter(View view) {
        shareSocial(this, "com.twitter");
    }

    /**
     * To whatsapp
     *
     * @param view
     */
    public void toWhatsApp(View view) {
        shareSocial(this, "com.whatsapp");
    }

    @Override
    public void onBackPressed() {
        promptLogout(this);
    }
}