package com.example.fleetatest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.fleetatest.datatables.Trip;
import com.example.fleetatest.datatables.Vehicle;

public class AddTrip extends AppCompatActivity {

    String barcode,barcode2;
    EditText etOdometer,etLocation,etVehicleIssues;
    TextView barcodeText,barcodeText1;
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        etOdometer=findViewById(R.id.etOdometer);
        etLocation=findViewById(R.id.etLocation);
        etVehicleIssues=findViewById(R.id.etVehicleIssues);
        barcodeText=findViewById(R.id.barcodeText);
        barcodeText1=findViewById(R.id.barcodeText1);
        btnSubmit=findViewById(R.id.btnSubmit);

        barcodeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(AddTrip.this,Scanner.class),3);
            }
        });
        barcodeText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(AddTrip.this,Scanner.class),4);
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etOdometer.getText().toString().isEmpty()||etLocation.getText().toString().isEmpty()||
                        etVehicleIssues.getText().toString().isEmpty())

                {
                    Toast.makeText(AddTrip.this, "please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Trip trip=new Trip();
                    trip.setLocation(etLocation.getText().toString().trim());
                    trip.setDriverId(barcode2);
                    trip.setVehicleId(barcode);
                    trip.setVehicleIssues(etVehicleIssues.getText().toString().trim());
                    trip.setOdometer(Long.parseLong(etOdometer.getText().toString().trim()));

                    Backendless.Persistence.save(trip, new AsyncCallback<Trip>() {
                        @Override
                        public void handleResponse(Trip response) {
                            Toast.makeText(AddTrip.this, "Vehicle Successfully saved! ", Toast.LENGTH_SHORT).show();
                            etLocation.setText(null);
                            etOdometer.setText(null);
                            etVehicleIssues.setText(null);
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(AddTrip.this, "Error: "+fault.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }
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
        if(requestCode==4)
        {
            if(resultCode==RESULT_OK)
            {
                barcodeText1.setText(data.getStringExtra("barcodeData"));
                barcode2=data.getStringExtra("barcodeData");
            }
            if(resultCode==RESULT_CANCELED)
            {
                barcodeText.setText("No data received!");
            }
        }

    }
}