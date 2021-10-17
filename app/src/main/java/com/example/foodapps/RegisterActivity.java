package com.example.foodapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.editTextName)
    TextInputEditText editName;
    @BindView(R.id.editTextEmail)
    TextInputEditText editEmail;
    @BindView(R.id.editTextMobile)
    TextInputEditText editMobile;
    @BindView(R.id.editTextPassword)
    TextInputEditText editPassword;
    @BindView(R.id.cirRegisterButton)
    CircularProgressButton regisButton;
    @BindView(R.id.showHideBtn)
    ImageButton showPassword;

    @OnClick(R.id.showHideBtn)
    void showPassword(){
        if (flag) {
            flag = false;
            editPassword.setTransformationMethod(null);

            if (editPassword.getText().length() > 0){
                editPassword.setSelection(editPassword.getText().length());
            }

        } else {
            flag = true;
            editPassword.setTransformationMethod(new PasswordTransformationMethod());

            if (editPassword.getText().length() > 0) {
                editPassword.setSelection(editPassword.getText().length());
            }
        }
    }



    boolean flag = true;
    String phoneNumber;
    FirebaseDatabase mDatabase;
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();
        init();
        registUser();
    }

    private void registUser() {


        regisButton.setOnClickListener(v -> {
            String number = editMobile.getText().toString();
            if (number.isEmpty() || number.length() < 13) {
                editMobile.setError("Number is Required");
                editMobile.requestFocus();
                return;
            }

            if (Common.isConnectedToInternet(getBaseContext())) {
                phoneNumber = number;

                Intent intent = new Intent(RegisterActivity.this, AuthSignUp.class);
                intent.putExtra("phoneNumber", phoneNumber);
                startActivity(intent);


                final ProgressDialog mDialog = new ProgressDialog(RegisterActivity.this);
                mDialog.show();

                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // check if user already exits  through phone number

                        if (dataSnapshot.child(editMobile.getText().toString()).exists()) {
                            mDialog.dismiss();
                        } else {
                            mDialog.dismiss();
                            // add the user with phone number as key value with name and password as child.
                            // use user class to get the name and password and save it to user object

                            User user = new User(editName.getText().toString(), editPassword.getText().toString(), editEmail.getText().toString());
                            // below this table user have phone number with value name and password through obj user

                            userRef.child(editMobile.getText().toString()).setValue(user);
                            Toast.makeText(RegisterActivity.this, "Sign Up Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            } else {
                Toast.makeText(RegisterActivity.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                return;
            }
        });

    }

    private void init(){
        ButterKnife.bind(this);
        mDatabase = FirebaseDatabase.getInstance();
        userRef = mDatabase.getReference("User");

    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick(View view){
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);

    }
}