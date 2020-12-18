package com.example.fleetatest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class Register extends AppCompatActivity {

    private View mProgressView;
    private View mRegisterFormView;
    private TextView tvLoad;

   EditText etName,etSurname,etEmail,etPassword,etPasswordConfirm;
   Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Register New User");

        mRegisterFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);
        tvLoad = findViewById(R.id.tvLoad);


        etName=findViewById(R.id.etName);
        etSurname=findViewById(R.id.etSurname);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        etPasswordConfirm=findViewById(R.id.etPasswordConfirm);
        btnSubmit=findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etName.getText().toString().isEmpty()||etSurname.getText().toString().isEmpty()||etEmail.getText().toString().isEmpty()||etPassword.getText().toString().isEmpty()||
                        etPasswordConfirm.getText().toString().isEmpty())
                {
                    Toast.makeText(Register.this, "Please Enter all Fields", Toast.LENGTH_SHORT).show();
                }

                else {
                    if (etPassword.getText().toString().trim().equals(etPasswordConfirm.getText().toString().trim())) {
                        BackendlessUser User = new BackendlessUser();
                        User.setEmail(etEmail.getText().toString().trim());
                        User.setPassword(etPassword.getText().toString().trim());
                        User.setProperty("name", etName.getText().toString().trim());
                        User.setProperty("surname", etSurname.getText().toString().trim());
                        tvLoad.setText("Loading...please wait...");
                        showProgress(true);

                        Backendless.UserService.register(User, new AsyncCallback<BackendlessUser>() {
                            @Override
                            public void handleResponse(BackendlessUser response) {
                                Toast.makeText(Register.this, "User successfully registered!", Toast.LENGTH_SHORT).show();
                                Register.this.finish();
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Toast.makeText(Register.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                                showProgress(false);
                            }
                        });
                    } else {
                        Toast.makeText(Register.this, "Please make sure that both passwords are the same!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

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
}