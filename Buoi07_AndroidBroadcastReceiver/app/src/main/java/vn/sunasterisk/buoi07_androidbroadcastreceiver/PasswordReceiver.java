package vn.sunasterisk.buoi07_androidbroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class PasswordReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }

        switch (intent.getAction()) {
            case Action.ACTION_VALIDATE_PASSWORD:
                String password = intent.getStringExtra(IntentKey.KEY_PASSWORD);
                boolean isStrength;

                isStrength = validatePassword(password);

                if (isStrength) {
                    Toast.makeText(context, "Power Password", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Weak Password", Toast.LENGTH_SHORT).show();
                }

                Intent resultIntent = new Intent();
                resultIntent.setAction(Action.ACTION_RESULT_PASSWORD);
                resultIntent.putExtra(IntentKey.KEY_RESULT, isStrength);
                context.sendBroadcast(resultIntent);
                break;
            default:
                break;
        }
    }

    private boolean validatePassword(String password) {

        //password = fajij!@#A!@#$

        boolean hasNumber = false;
        boolean hasLowCase = false;
        boolean hasUpperCase = false;
        boolean hasSpecialChars = false;

        String specialChars = "!@#$%^&*";

        if (password.length() < 8) {
            return false;
        }

        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) <= '9' && password.charAt(i) >= '0') {
                hasNumber = true;
            }

            if (password.charAt(i) <= 'z' && password.charAt(i) >= 'a') {
                hasLowCase = true;
            }

            if (password.charAt(i) <= 'Z' && password.charAt(i) >= 'A') {
                hasUpperCase = true;
            }

            if (specialChars.contains(String.valueOf(password.charAt(i)))) {
                hasSpecialChars = true;
            }
        }

        return hasNumber && hasLowCase && hasUpperCase && hasSpecialChars;
    }
}
