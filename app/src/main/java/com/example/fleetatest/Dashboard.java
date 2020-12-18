package com.example.fleetatest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class Dashboard extends AppCompatActivity {

    CardView btnTrip,btnFillup,btnExpenses,btnReport,btnLogout2,btnService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Dashboard");

        btnTrip=findViewById(R.id.btnTrip);
        btnFillup=findViewById(R.id.btnFillup);
        btnExpenses=findViewById(R.id.btnExpenses);
        btnReport=findViewById(R.id.btnReport);
        btnLogout2=findViewById(R.id.btnLogout2);
        btnService=findViewById(R.id.btnService);

        btnTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this,AddTrip.class));
            }
        });
        btnLogout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this,AddVehicle.class));
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.btnLogout:
                Toast.makeText(this, "Busy signing you out...please wait...", Toast.LENGTH_LONG).show();
                Backendless.UserService.logout(new AsyncCallback<Void>() {
                    @Override
                    public void handleResponse(Void response) {
                        Toast.makeText(Dashboard.this, "User signed out successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Dashboard.this,Login.class));
                        Dashboard.this.finish();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(Dashboard.this, "Error: "+fault.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.btnVehicle:
                startActivity(new Intent(Dashboard.this,AddVehicle.class));
                break;
            case R.id.btnDriver:
                startActivity(new Intent(Dashboard.this,AddDriver.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}