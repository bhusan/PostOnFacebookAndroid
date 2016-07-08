package app.famousenuf.login;

import android.content.Intent;

import com.facebook.CallbackManager;

/**
 * Created by bharatbhusan on 1/7/16.
 */
public class FacebookLoginCallBackManager implements CallbackManager {
    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        return false;
    }
}
