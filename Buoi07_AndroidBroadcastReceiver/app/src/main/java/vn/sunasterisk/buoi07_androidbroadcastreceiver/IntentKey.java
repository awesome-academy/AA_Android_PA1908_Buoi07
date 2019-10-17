package vn.sunasterisk.buoi07_androidbroadcastreceiver;

import androidx.annotation.StringDef;

@StringDef({IntentKey.KEY_PASSWORD, IntentKey.KEY_RESULT})
public @interface IntentKey {
    String KEY_PASSWORD = "key password";
    String KEY_RESULT = "key_result";
}
