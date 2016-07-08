package app.famousenuf.login;

import com.facebook.FacebookException;

/**
 * Created by bharatbhusan on 2/7/16.
 */
public interface FacebookStatus {
    public void onFacebookPostSuccess(String postId);
    public void onFacebookPostCancel();
    public void onFacebookError(FacebookException error);

}
