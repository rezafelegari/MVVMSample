package com.example.reza.firstca;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reza.firstca.DB.UserInfo;
import com.example.reza.firstca.DB.Word;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private UserViewModel mUserViewModel;
    TextView tv_fname, tv_lname, size_tv;
    EditText editText_fname, editText_lname, fname_update, lname_update;
    Button insert_btn, updae_btn, get_btn;
    UserInfo userInfo;
    MutableLiveData<String> mutableLiveData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mutableLiveData = new MutableLiveData<>();
        mutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Toast.makeText(ProfileActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });

        size_tv = (TextView) findViewById(R.id.size_tv);

        tv_fname = (TextView) findViewById(R.id.tv_fname);
        tv_lname = (TextView) findViewById(R.id.tv_lname);
        //
        editText_fname = (EditText) findViewById(R.id.fname_edt);
        editText_lname = (EditText) findViewById(R.id.lname_edt);
        //
        fname_update = (EditText) findViewById(R.id.fname_update);
        lname_update = (EditText) findViewById(R.id.lname_update);
        //
        insert_btn = (Button) findViewById(R.id.insert_btn);
        insert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserViewModel.insert(new UserInfo(editText_fname.getText().toString(), editText_lname.getText().toString()));
            }
        });
        updae_btn = (Button) findViewById(R.id.update_btn);
        updae_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserViewModel.update(userInfo.getId(), fname_update.getText().toString(), lname_update.getText().toString());
                mutableLiveData.setValue(fname_update.getText().toString());
            }
        });
        get_btn = (Button) findViewById(R.id.get_btn);
        get_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mUserViewModel.getmovie().observe(ProfileActivity.this, new Observer<Response<List<Search>>>() {
                        @Override
                        public void onChanged(@Nullable Response<List<Search>> listResponse) {
                            if (listResponse.getData() != null && listResponse.getData().size() > 0) {
                                Toast.makeText(ProfileActivity.this, "Size:" + listResponse.getData().size() + "/n Status:" + listResponse.getStatusCode(), Toast.LENGTH_SHORT).show();
                                size_tv.setText(listResponse.getData().size() + "");
                            } else {
                                Toast.makeText(ProfileActivity.this, "Statuscode" + listResponse.getStatusCode(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        mUserViewModel.getAlluser().observe(this, new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(@Nullable final List<UserInfo> users) {
                // Update the cached copy of the words in the adapter.
                if (users.size() > 0) {
                    tv_fname.setText(users.get(users.size() - 1).getFirstname());
                    tv_lname.setText(users.get(users.size() - 1).getLastname());
                    fname_update.setText(users.get(users.size() - 1).getFirstname());
                    lname_update.setText(users.get(users.size() - 1).getLastname());
                    userInfo = users.get(0);
                }

            }
        });

    }
}
