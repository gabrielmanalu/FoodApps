package com.example.foodapps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapps.Common.Common;
import com.example.foodapps.Model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.editTextEmail)
    TextInputEditText loginMail;
    @BindView(R.id.editTextPassword)
    TextInputEditText loginPassword;
    @BindView(R.id.toPhoneNumber)
    TextView toPhoneNumber;
    @BindView(R.id.cirLoginButton)
    CircularProgressButton loginButton;
    @BindView(R.id.ckbRemember)
    CheckBox rememberId;

    @OnClick(R.id.toPhoneNumber)
    void usingNumber(){
        if(flag){
            if(loginMail.getHint() != null){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                    loginMail.setRevealOnFocusHint(false);
                }
            }
            flag = false;
            loginMail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            loginMail.setHint(R.string.login_email_hint);
            toPhoneNumber.setText(R.string.using_phone);
        } else{
            flag = true;
            loginMail.setInputType(InputType.TYPE_CLASS_NUMBER);
            loginMail.setHint(R.string.login_hint);
            toPhoneNumber.setText(R.string.using_mail);
        }
    }

    FirebaseDatabase mDatabase;
    DatabaseReference userRef;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);

        init();
        login();
    }

    private void login() {
        if(flag){
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Common.isConnectedToInternet(getBaseContext())) {
                        // save user and password
                        if (rememberId.isChecked()) {
                            Paper.book().write(Common.USER_KEY, loginMail.getText().toString());
                            Paper.book().write(Common.PWD_KEY, loginPassword.getText().toString());
                        }


                        final ProgressDialog mDialog = new ProgressDialog(LoginActivity.this);
                        mDialog.setMessage("Please Wait....");
                        mDialog.show();

                        userRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //Check if user not exist in database
                                if (dataSnapshot.child(loginMail.getText().toString()).exists()) {
                                    //get user information
                                    mDialog.dismiss();

                                    User user = dataSnapshot.child(loginMail.getText().toString()).getValue(User.class);
                                    user.setPhone(loginMail.getText().toString()); //set the phone of user
                                    if (user.getPassword().equals(loginPassword.getText().toString())) {

                                        Intent homeIntent = new Intent(LoginActivity.this, Home.class);
                                        Common.currentUser = user;
                                        startActivity(homeIntent);
                                        finish();

                                    } else {
                                        Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    mDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "User not exists", Toast.LENGTH_SHORT).show();

                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    } else {
                        Toast.makeText(LoginActivity.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            });
        } else {
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Common.isConnectedToInternet(getBaseContext())) {
                        // save user and password
                        if (rememberId.isChecked()) {
                            Paper.book().write(Common.USER_EMAIL_KEY, loginMail.getText().toString());
                            Paper.book().write(Common.PWD_KEY, loginPassword.getText().toString());
                        }


                        final ProgressDialog mDialog = new ProgressDialog(LoginActivity.this);
                        mDialog.setMessage("Please Wait....");
                        mDialog.show();

                        userRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //Check if user not exist in database
                                if (dataSnapshot.child(loginMail.getText().toString()).exists()) {
                                    //get user information
                                    mDialog.dismiss();

                                    User user = dataSnapshot.child(loginMail.getText().toString()).getValue(User.class);
                                    user.setEmail(loginMail.getText().toString()); //set the phone of user
                                    if (user.getPassword().equals(loginPassword.getText().toString())) {

                                        Intent homeIntent = new Intent(LoginActivity.this, Home.class);
                                        Common.currentUser = user;
                                        startActivity(homeIntent);
                                        finish();

                                    } else {
                                        Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    mDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "User not exists", Toast.LENGTH_SHORT).show();

                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    } else {
                        Toast.makeText(LoginActivity.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            });

        }

    }

    private void init(){
        ButterKnife.bind(this);
        mDatabase = FirebaseDatabase.getInstance();
        userRef = mDatabase.getReference();
        loginMail.setHint(R.string.login_hint);
    }

    public void onLoginClick(View View){
        startActivity(new Intent(this,RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);

    }
}