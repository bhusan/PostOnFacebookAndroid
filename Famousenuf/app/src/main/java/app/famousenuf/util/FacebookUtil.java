package app.famousenuf.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;

import java.util.Arrays;

import app.famousenuf.login.FacebookCallBackMethod;
import app.famousenuf.login.FacebookStatus;
import app.famousenuf.login.UserFacebookPostInterface;
import app.famousenuf.login.UserFacebookPostList;

/**
 * Created by bharatbhusan on 1/7/16.
 */
public class FacebookUtil {
    public static void shareImageOnFacebook(Bitmap bitmap, final Activity activity, FacebookStatus facebookStatus, CallbackManager callbackManager) {
        if (AccessToken.getCurrentAccessToken() == null) {
          facebookLogin(activity,callbackManager);

        } else {
            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(bitmap)
                    .build();
            SharePhotoContent content = new SharePhotoContent.Builder()
                    .addPhoto(photo)
                    .build();
            ShareApi.share(content, new FacebookCallBackMethod(bitmap, activity, facebookStatus));
        }
    }

    public static void facebookLogin(final Activity activity, CallbackManager callbackManager)
    {
        final LoginManager loginManager = LoginManager.getInstance();
        loginManager.logInWithReadPermissions(activity, Arrays.asList("email", "user_photos", "public_profile", "user_posts"));
        loginManager.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        if(!AccessToken.getCurrentAccessToken().getPermissions().contains("publish_actions")) {
                            loginManager.logInWithPublishPermissions(activity, Arrays.asList("publish_actions"));
                        }
//                        Toast.makeText(activity, loginResult.getAccessToken().getToken(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(activity, "Cancel", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public static void getLikeCounts(String objectId, final TextView textView) {
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                objectId + "/likes",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        textView.setText(response.getRawResponse());
            /* handle the result */
                    }
                }
        ).executeAsync();


    }

    public static void likePost(String objectId) {
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                objectId + "/likes",
                null,
                HttpMethod.POST,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                    }
                }
        ).executeAsync();


    }

    public static void getPosts(final UserFacebookPostInterface userFacebookPostInterface) {
        Bundle params = new Bundle();
        params.putString("fields", "id,name,link,message,likes,comments,full_picture");
        params.putString("limit", "500");
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/feed",
                params,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        userFacebookPostInterface.setUserPosts(CommonUtilMethod.getObjectFromJson(UserFacebookPostList.class, response.getRawResponse()));
                    }
                }
        ).executeAsync();
    }

    public static void addComment(String postId,String comment)
    {
        Bundle params = new Bundle();
        params.putString("message", comment);
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/"+postId+"/comments",
                params,
                HttpMethod.POST,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                    }
                }
        ).executeAsync();
    }
    public static void logOut()
    {
        LoginManager.getInstance().logOut();
    }
}
