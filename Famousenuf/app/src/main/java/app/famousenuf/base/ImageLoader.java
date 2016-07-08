package app.famousenuf.base;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import app.famousenuf.login.SharedImage;
import app.famousenuf.util.StringUtil;

/**
 * Created by bharatbhusan on 2/7/16.
 */
public class ImageLoader extends AsyncTask {
    private ImageView imageView;
    private String urllink;
    private int height;
    private int width;
    private int mHeight;
    private int mWidth;

    public ImageLoader(ImageView imageView, String urllink) {
        this.imageView = imageView;
        this.urllink = urllink;
        this.width = 0;
        this.height = 0;
        if (!isCached()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                execute();
            }
        }
    }

    private boolean isCached() {
        Bitmap bitmap = null;

        SharedImage imageObject = SharedImage.fetchImage(imageView.getContext(), urllink);
        if (imageObject != null) {
            byte[] bytes = imageObject.getImageBytes();
            if (bytes != null) {
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                if (bitmap != null) {
                    setImage(bitmap);
                    return true;
                }
            }
        }
        return false;
    }

    public ImageLoader(ImageView imageView, String urllink, int width, int height) {
        this.imageView = imageView;
        this.urllink = urllink;
        this.height = height;
        this.width = width;
        if (!isCached()) {
            execute();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Object o) {
        super.onCancelled(o);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected Object doInBackground(Object[] params) {

        HttpURLConnection urlConnection = null;
        Bitmap bitmap = null;
        try {

            decodeBoundsInfo();

            URL url = new URL(urllink);


            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inJustDecodeBounds = false;

            options.inSampleSize = calculateInSampleSize(width, height);
            bitmap = BitmapFactory.decodeStream(inputStream, null, options);
//            if (bitmap != null) {
//                if (height > 0 && width > 0) {
//                    bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
//                }
//            }
            inputStream.close();
        } catch (Exception ex) {

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return bitmap;
    }

    private void decodeBoundsInfo() {
        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(urllink);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inJustDecodeBounds = true;


            BitmapFactory.decodeStream(inputStream, null, options);
            mHeight = options.outHeight;
            mWidth = options.outWidth;
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

    }

    public int calculateInSampleSize(
            int reqWidth, int reqHeight) {
        // Raw height and width of image
        int inSampleSize = 1;

        if (mHeight > reqHeight || mWidth > reqWidth) {

            final int halfHeight = mHeight / 2;
            final int halfWidth = mHeight / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    @Override
    protected void onPostExecute(Object o) {
        Bitmap bitmap = (Bitmap) o;
        if (bitmap == null) {
            return;
        }
        setImage(bitmap);
        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, blob);
        byte[] array = blob.toByteArray();
        SharedImage imageObject = new SharedImage(urllink, array);
        imageObject.add(imageView.getContext());

    }

    private void setImage(Bitmap bitmap) {
        String tag = (String) imageView.getTag();
        if (StringUtil.isNullOrEmpty(tag) || tag.equalsIgnoreCase(urllink)) {
            imageView.clearAnimation();
//            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
            imageView.setImageBitmap(bitmap);
        }
    }

    protected int byteSizeOf(Bitmap data) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
            return data.getRowBytes() * data.getHeight() * 4;
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return data.getByteCount();
        } else {
            return data.getAllocationByteCount();
        }
    }

}
