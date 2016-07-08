package app.famousenuf.login;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;

import java.io.ByteArrayOutputStream;

import app.famousenuf.util.FacebookUtil;

/**
 * Created by bharatbhusan on 1/7/16.
 */
public class FacebookCallBackMethod implements FacebookCallback<Sharer.Result> {
    private Context context;
    private Bitmap bitmap;
    private FacebookStatus status;
    public FacebookCallBackMethod(Bitmap bitmap,Context context,FacebookStatus status)
    {
        super();
        this.context =context;
        this.bitmap=bitmap;
        this.status=status;

    }
    @Override
    public void onSuccess(Sharer.Result result) {
        FacebookUtil.getLikeCounts(result.getPostId(), new TextView(context));
        if(status!=null) {
            status.onFacebookPostSuccess(result.getPostId());
        }
    }

    @Override
    public void onCancel() {
        if(status!=null) {
            status.onFacebookPostCancel();
        }

    }

    @Override
    public void onError(FacebookException error) {
        if(status!=null) {
            status.onFacebookError(error);
        }

    }
}
