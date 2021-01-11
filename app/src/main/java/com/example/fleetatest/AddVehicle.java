package com.example.fleetatest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.fleetatest.datatables.Vehicle;
import com.google.android.material.textfield.TextInputLayout;

import fr.ganfra.materialspinner.MaterialSpinner;

public class AddVehicle extends AppCompatActivity {

    private View mProgressView;
    private View mRegisterFormView;
    private TextView tvLoad;



    String[] type = { "Please Choose","Car", "Truck"};
    String[] fuel = { "Please Choose","Petrol", "Diesel"};
    MaterialSpinner spin,spin2;
    String barcode,vType,fType;

    EditText etMake,etModel,etColor,etIssues,etYear,etVin,etPlate,etOdometer,etVstatus;
    TextInputLayout make,model,year,vin,fuelType,plate,Odometer,status,color;
    TextView barcodeText;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Vehicle");

        mRegisterFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);
        tvLoad = findViewById(R.id.tvLoad);

        //TextInputLayout
        make=findViewById(R.id.make);
        model=findViewById(R.id.model);
        color=findViewById(R.id.color);
        year=findViewById(R.id.year);
        status=findViewById(R.id. vStatus);
        vin=findViewById(R.id.vin);
        spin2=findViewById(R.id.fuelType);
        plate=findViewById(R.id.plate);
        Odometer=findViewById(R.id.Odometer);
       // type=findViewById(R.id.type);


        //EditText Hooks
        etMake=findViewById(R.id.etMake);
        etModel=findViewById(R.id.etModel);
        etColor=findViewById(R.id.etColor);
        etYear=findViewById(R.id.etYear);
        etVin=findViewById(R.id.etVin);
        etPlate=findViewById(R.id.etPlate);
        etOdometer=findViewById(R.id.etOdometer);
        //etType=findViewById(R.id.etType);
        etVstatus=findViewById(R.id.etVstatus);
        barcodeText=findViewById(R.id.barcodeText);

        btnSubmit=findViewById(R.id.btnSubmit);
        spin=findViewById(R.id.spinner);


        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,type);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=-1) {
                    String selected = spin.getItemAtPosition(position).toString();
                    if(selected=="Please Choose")
                        spin.setError("Error Please Select Vehicle Type");
                    else
                        selected=vType;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spin.setError("Error Please Select Vehicle Type");
            }
        });

        ArrayAdapter bb = new ArrayAdapter(this,android.R.layout.simple_spinner_item,fuel);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin2.setAdapter(bb);

        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=-1) {
                    String selected = spin2.getItemAtPosition(position).toString();
                    if(selected=="Please Choose")
                        spin2.setError("Error Please Select Vehicle Fuel Type");
                    else
                        selected=fType;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spin.setError("Error Please Select Vehicle Fuel Type");
            }
        });
        etMake.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                validateMake();
            }
        });
        etModel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                validateModel();
            }
        });
        etYear.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                validateYear();
            }
        });
        etColor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                validateColor();
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* if(etMake.getText().toString().isEmpty()||etModel.getText().toString().isEmpty()||
                        etColor.getText().toString().isEmpty()|| etIssues.getText().toString().isEmpty()|| etYear.getText().toString().isEmpty()||
                        etVin.getText().toString().isEmpty()||etPlate.getText().toString().isEmpty()||etOdometer.getText().toString().isEmpty()||
                         etVstatus.getText().toString().isEmpty())
                {
                    Toast.makeText(AddVehicle.this, "please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else
                {*/
                    Vehicle vehicle=new Vehicle();
                    vehicle.setMake(etMake.getText().toString().trim());
                    vehicle.setModel(etModel.getText().toString().trim());
                    vehicle.setColor(etColor.getText().toString().trim());
                    vehicle.setOdometer(Long.parseLong(etOdometer.getText().toString().trim()));
                    vehicle.setPlate(etPlate.getText().toString().trim());
                    vehicle.setFuelType(fType);
                    vehicle.setVin(etVin.getText().toString().trim());
                    vehicle.setIssues(etIssues.getText().toString().trim());
                   // vehicle.setVehicleStatus("Available");
                    vehicle.setVehicleType(vType);
                    vehicle.setVehicleId(barcode);

                    tvLoad.setText("Creating new Vehicle...Please wait...");
                    showProgress(true);
                    Backendless.Persistence.save(vehicle, new AsyncCallback<Vehicle>() {
                        @Override
                        public void handleResponse(Vehicle response) {
                            Toast.makeText(AddVehicle.this, "Vehicle Successfully saved! ", Toast.LENGTH_SHORT).show();
                            /*etMake.setText(null);
                            etModel.setText(null);
                            etColor.setText(null);
                            etOdometer.setText(null);
                            etPlate.setText(null);
                            etVin.setText(null);
                            etIssues.setText(null);
                            etVstatus.setText(null);*/
                            showProgress(false);
                            AddVehicle.this.finish();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(AddVehicle.this, "Error: "+fault.getMessage(), Toast.LENGTH_SHORT).show();
                            showProgress(false);
                        }
                    });
                }
            //}
        });
        barcodeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(AddVehicle.this,Scanner.class),3);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==3)
        {
            if(resultCode==RESULT_OK)
            {
                barcodeText.setText(data.getStringExtra("barcodeData"));
                barcode=data.getStringExtra("barcodeData");
            }
            if(resultCode==RESULT_CANCELED)
            {
                barcodeText.setText("No data received!");
            }
        }
    }
    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }


    }

    //Form Validation
    private Boolean validateMake()
    {
        String val=make.getEditText().getText().toString().trim();
        if(val.isEmpty())
        {
            make.setError("Field Cannot be empty");
            return false;
        }
        else
        {
            make.setError(null);
            make.setErrorEnabled(false);
             return true;
        }
    }
    private Boolean validateModel()
    {
        String val=model.getEditText().getText().toString().trim();
        if(val.isEmpty())
        {
            model.setError("Field Cannot be empty");
            return false;
        }
        else
        {
            model.setError(null);
            model.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateYear()
    {
        String val=year.getEditText().getText().toString().trim();
        String Checkspaces="\\d{4}$";
        if(val.isEmpty())
        {
            year.setError("Field Cannot be empty");
            return false;
        }
        else if(!val.matches(Checkspaces))
        {
            year.setError("Invalid Year Input");
            return false;
        }
        else
        {
            year.setError(null);
            year.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateColor()
    {
        String val=color.getEditText().getText().toString().trim();
        if(val.isEmpty())
        {
            color.setError("Field Cannot be empty");
            return false;
        }
        else
        {
            color.setError(null);
            color.setErrorEnabled(false);
            return true;
        }
    }
}