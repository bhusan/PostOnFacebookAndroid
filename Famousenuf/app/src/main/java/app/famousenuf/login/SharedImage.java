package app.famousenuf.login;

import android.content.Context;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

import app.famousenuf.base.DatabaseHelper;
import app.famousenuf.base.Pair;
import app.famousenuf.util.ListUtil;

/**
 * Created by bharatbhusan on 2/7/16.
 */
@DatabaseTable
public class SharedImage {
    @DatabaseField(id = true, unique = true)
    private String imageLink;

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] imageBytes;


    public SharedImage() {

    }

    public SharedImage(String imageLink, byte[] imageBytes) {
        this.imageBytes = imageBytes;
        this.imageLink = imageLink;
    }

    public String getImageId() {
        return imageLink;
    }

    public void setImageId(String imageLink) {
        this.imageLink = imageLink;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public void add(Context context) {
        DatabaseHelper.getDBUtil(context, this.getClass()).add(this);
    }


    public static SharedImage fetchImage(Context context, String imageLink) {
        List<SharedImage> imageObjectList = DatabaseHelper.getDBUtil(context, SharedImage.class).fetch(new Pair("imageLink", imageLink), null);
        if (!ListUtil.isListEmpty(imageObjectList)) {
            return imageObjectList.get(0);
        }
        return null;
    }
}
