package vn.sunasterisk.buoi07_androidbroadcastreceiver;

import androidx.annotation.StringDef;

@StringDef({Action.ACTION_VALIDATE_PASSWORD, Action.ACTION_RESULT_PASSWORD})
public @interface Action {
    String ACTION_VALIDATE_PASSWORD = "action validate password";
    String ACTION_RESULT_PASSWORD = "action result password";
}
