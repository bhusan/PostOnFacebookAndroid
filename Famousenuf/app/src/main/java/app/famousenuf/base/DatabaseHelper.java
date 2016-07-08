package app.famousenuf.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.famousenuf.R;
import app.famousenuf.login.SharedImage;

/**
 * Created by bharatbhusan on 2/7/16.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "Famousenuf", null, Integer.parseInt(context.getResources().getString(R.string.DATABASE_VERSION)));
        initializeTableClasses();

    }

    private static List<Class> classList = new ArrayList<>();

    private void initializeTableClasses() {
        addToTableClasses(SharedImage.class);


    }

    private void addToTableClasses(Class T) {
        try {
            if (!classList.contains(T)) {
                classList.add(T);
            }
        } catch (Exception e) {
            Log.i(T.getName(), "Can not add to class list");
        }

    }

    private static Map<Class, DBUtil> dbUtilMap = new HashMap<Class, DBUtil>();
    private Map<Class, Dao> daoMap = new HashMap<Class, Dao>();


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        for (Class cl : classList) {
            try {
                TableUtils.createTableIfNotExists(connectionSource, cl);
            } catch (SQLException e) {
                e.printStackTrace();
                Log.i(cl.getName(), "Can not create table");
            }
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            for (Class cl : classList) {
                try {
                        TableUtils.dropTable(connectionSource, cl, true);
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.i(cl.getName(), "Can not delete table");
                }
            }
        }
        onCreate(database, connectionSource);

    }

    public static DBUtil getDBUtil(Context context, Class cl) {
        DBUtil dbUtil = dbUtilMap.get(cl);
        if (dbUtil == null) {
            dbUtil = new DBUtil(context, cl);
            dbUtilMap.put(cl, dbUtil);
        }
        return dbUtil;
    }

    public Dao getDaoFromClass(Class cl) {
        Dao dao = daoMap.get(cl);
        if (dao == null) {
            try {
                dao = getDao(cl);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            daoMap.put(cl, dao);
        }
        return dao;
    }
}
