package com.example.myloans;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myloans.data.MyAdsPersistence;
import com.example.myloans.model.NewCustomer;

import static com.example.myloans.utils.SharedFunctions.formatPhone;
import static com.example.myloans.utils.SharedFunctions.showError;
import static com.example.myloans.utils.SharedFunctions.successSignUp;

public class SignUp extends AppCompatActivity {
    EditText fName, lName, oName, phoneNumber, nationalId, dOb;
    Button createAcc;
    Context context = SignUp.this;
    MyAdsPersistence dbInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        dbInstance = new MyAdsPersistence(context);

        fName = findViewById(R.id.first_name);
        lName = findViewById(R.id.last_name);
        oName = findViewById(R.id.other_name);
        phoneNumber = findViewById(R.id.phone_number);
        nationalId = findViewById(R.id.national_id);
        dOb = findViewById(R.id.date_of_birth);
        createAcc = findViewById(R.id.create_accont_button);


        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fName.getText().toString().equalsIgnoreCase(""))
                    showError(context, "Sorry, please enter your first name!");
                else if (lName.getText().toString().equalsIgnoreCase(""))
                    showError(context, "Sorry, please enter your last name!");
                else if (phoneNumber.getText().toString().equalsIgnoreCase(""))
                    showError(context, "Sorry, please enter your valid phone number!");
                else if (nationalId.getText().toString().equalsIgnoreCase(""))
                    showError(context, "Sorry, please enter your national ID number!");
                else if (dOb.getText().toString().equalsIgnoreCase(""))
                    showError(context, "Sorry, please specify your date of birth. format: 1997-05-25!");
                else if (formatPhone(phoneNumber.getText().toString()).equalsIgnoreCase("0"))
                    showError(context, "Please enter a valid phone number");
                else
                    createNewAccount(fName.getText().toString(),
                            lName.getText().toString(),
                            oName.getText().toString(),
                            phoneNumber.getText().toString(),
                            nationalId.getText().toString(),
                            dOb.getText().toString());
            }
        });

    }

    private void createNewAccount(String fName, String lName, String oName, String phoneNumber, String nationalId, String dOb) {
        NewCustomer customer = new NewCustomer();
        customer.setfName(fName);
        customer.setlName(lName);
        customer.setoName(oName);
        customer.setPhoneNumber(phoneNumber);
        customer.setNationalId(nationalId);
        customer.setdOb(dOb);

        try {
            dbInstance.createNewUser(customer);
            successSignUp(context, customer);
        } catch (Exception e) {
            showError(context, "Sorry, your account could not be created. Try again or contact us!");
        }
    }
}