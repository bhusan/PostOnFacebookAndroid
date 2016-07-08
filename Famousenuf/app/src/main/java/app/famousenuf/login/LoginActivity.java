package app.famousenuf.login;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookException;
import com.facebook.login.widget.LoginButton;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import app.famousenuf.R;
import app.famousenuf.util.FacebookUtil;
import app.famousenuf.util.ListUtil;
import app.famousenuf.util.StringUtil;

public class LoginActivity extends AppCompatActivity implements FacebookStatus, UserFacebookPostInterface {
    private Bitmap bitmap;
    private ListView postList;
    private FacebookPostAdapter postAdapter;
    private Button login;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int GET_FROM_GALLERY = 2;
    private CallbackManager callbackManager = CallbackManager.Factory.create();
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.btShare).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();

            }
        });
        login = (Button) findViewById(R.id.btFacebookLogin);
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccessToken.getCurrentAccessToken() == null) {
                    FacebookUtil.facebookLogin(LoginActivity.this, callbackManager);
                }
                else
                {
                    FacebookUtil.logOut();
                    login.setText("LOG IN WITH FACEBOOK");
                }
            }
        });
        initializeUIElement();
        if (AccessToken.getCurrentAccessToken() != null) {
            login.setText("LOG OUT");
            progressDialog.setMessage("fetching posts ....");
            progressDialog.setCancelable(false);
            progressDialog.show();
            FacebookUtil.getPosts(this);
        } else {
            findViewById(R.id.tvNotLogedIn).setVisibility(View.VISIBLE);
            postList.setVisibility(View.GONE);
        }

//        login.setPublishPermissions(Arrays.asList("publish_actions"));
    }

    private void initializeUIElement() {
        progressDialog = new ProgressDialog(this);
        postList = (ListView) findViewById(R.id.lvFacebookPost);
        postAdapter = new FacebookPostAdapter(this, R.layout.user_post);
        postList.setAdapter(postAdapter);
    }

    private void openCamera() {
        if (LoginActivity.this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        }

    }

    private void openGallery() {
        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
    }

    private void uploadImage() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View uploadView = inflater.inflate(R.layout.upload_image, null);

        final AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Upload image").setView(uploadView).setCancelable(false).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        }).show();
        uploadView.findViewById(R.id.llSavedImage).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
                dialog.dismiss();
            }
        });
        uploadView.findViewById(R.id.llCamera).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (AccessToken.getCurrentAccessToken() != null) {
            login.setText("LOG OUT");
        } else {
            login.setText("LOG IN WITH FACEBOOK");
        }
        if (AccessToken.getCurrentAccessToken() != null && postAdapter.isEmpty()) {
            progressDialog.setMessage("fetching posts ....");
            progressDialog.setCancelable(false);
            progressDialog.show();
            FacebookUtil.getPosts(this);
            findViewById(R.id.tvNotLogedIn).setVisibility(View.GONE);
            postList.setVisibility(View.VISIBLE);
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            FacebookUtil.shareImageOnFacebook(bitmap, this, LoginActivity.this, callbackManager);
            if (bitmap != null) {
                shareImage(bitmap);
            }

        } else if (requestCode == GET_FROM_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                if (bitmap != null) {
                    shareImage(bitmap);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void shareImage(final Bitmap bitmap) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View shareView = inflater.inflate(R.layout.share_view, null);

        final AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Share image").setView(shareView).setCancelable(false).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        }).setPositiveButton("Share", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                progressDialog.setMessage("Sharing image ....");
                progressDialog.setCancelable(false);
                progressDialog.show();
                FacebookUtil.shareImageOnFacebook(bitmap, LoginActivity.this, LoginActivity.this, callbackManager);
                dialog.dismiss();
            }
        }).show();
        ImageView shareImage = (ImageView) shareView.findViewById(R.id.ivSelectedImage);
        shareImage.setImageBitmap(bitmap);
    }


    @Override
    public void onFacebookPostSuccess(String postId) {
        progressDialog.dismiss();
        if (AccessToken.getCurrentAccessToken() != null) {
            FacebookUtil.getPosts(this);
        }
        Toast.makeText(this, "Image shared successfully", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onFacebookPostCancel() {
        progressDialog.dismiss();
        Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFacebookError(FacebookException error) {
        progressDialog.dismiss();
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setUserPosts(UserFacebookPostList userFacebookPostList) {
        progressDialog.dismiss();
        if (userFacebookPostList != null && !ListUtil.isListEmpty(userFacebookPostList.getFacebookPosts())) {
            postAdapter.clear();
            for (UserFacebookPost userFacebookPost : userFacebookPostList.getFacebookPosts()) {
                if (!StringUtil.isNullOrEmpty(userFacebookPost.getMessage()) || !StringUtil.isNullOrEmpty(userFacebookPost.getFullPicture())) {
                    postAdapter.add(userFacebookPost);
                }
            }
            postAdapter.notifyDataSetChanged();
        }
    }
}

