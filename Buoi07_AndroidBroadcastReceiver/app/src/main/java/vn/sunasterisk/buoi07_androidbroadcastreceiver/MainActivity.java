package vn.sunasterisk.buoi07_androidbroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    /**
     * Broadcast receiver có 2 cách dùng.
     * cách 1: static - khai báo cứng trong file Mainifest:
     * chạy lâu dài trong background, có thể vẫn sống dù đã tắt ứng dụng. gây tốn pin ...
     * cách 2: dynamic - dùng java code, cách này ưu tiên hơn
     * sử dụng tùy hoàn cảnh, thường phụ thuộc vào ng dùng và lifecycle ...
     */

    private EditText mTextPassword;
    private Button mButtonValidatePassword;
    private TextView mTextResult;

    //public static final String ACTION_VALIDATE_PASSWORD = "action validate password";

    //public static final String KEY_PASSWORD = "key password";

    private PasswordReceiver mPasswordReceiver;
    private MainReceiver mMainReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        registerBroadcastReceiver();
    }

    private void registerBroadcastReceiver() {
        mPasswordReceiver = new PasswordReceiver();
        IntentFilter intentFilter = new IntentFilter();
        //intentFilter.addAction(ACTION_VALIDATE_PASSWORD);
        intentFilter.addAction(Action.ACTION_VALIDATE_PASSWORD);
        registerReceiver(mPasswordReceiver, intentFilter);


        mMainReceiver = new MainReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Action.ACTION_RESULT_PASSWORD);
        registerReceiver(mMainReceiver, filter);

    }

    private void initViews() {
        mTextPassword = findViewById(R.id.text_password);
        mButtonValidatePassword = findViewById(R.id.button_validate_password);
        mTextResult = findViewById(R.id.text_result);
        mButtonValidatePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_validate_password:
                validatePassword();
                break;
            default:
                break;
        }
    }

    /**
     * password mạnh là password >= 8 ký tự, có chữ thường, chữa in hoa, có số,
     * có các kí tự đặc biệt như !@#$%^&
     */
    private void validatePassword() {
        String password = mTextPassword.getText().toString();
        if (password.isEmpty()) {
            Toast.makeText(this, "Password is not Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.setAction(Action.ACTION_VALIDATE_PASSWORD);
        intent.putExtra(IntentKey.KEY_PASSWORD, password);
        sendBroadcast(intent);
    }

    private class MainReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getAction() == null) {
                return;
            }

            switch (intent.getAction()) {
                case Action.ACTION_RESULT_PASSWORD:
                    boolean isStrength = intent.getBooleanExtra(IntentKey.KEY_RESULT, false);
                    if (isStrength) {
                        mTextResult.setText("Password is Strength!");
                    } else {
                        mTextResult.setText("Password is Weak!");
                    }

                    break;
                default:
                    break;
            }
        }
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(mPasswordReceiver);
        unregisterReceiver(mMainReceiver);
        super.onDestroy();
    }
}
