package com.example.reza.firstca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewWordActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY_TITLE = "title";
    public static final String EXTRA_REPLY_ADDRESS = "address";

    private EditText mEditTitleView, mEditAddressView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        mEditTitleView = findViewById(R.id.edit_title);
        mEditAddressView = findViewById(R.id.edit_address);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditTitleView.getText()) && TextUtils.isEmpty(mEditAddressView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String title = mEditTitleView.getText().toString();
                    String adress = mEditAddressView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY_TITLE, title);
                    replyIntent.putExtra(EXTRA_REPLY_ADDRESS, adress);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
