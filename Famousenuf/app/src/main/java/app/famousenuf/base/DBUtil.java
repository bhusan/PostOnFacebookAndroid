package app.famousenuf.base;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.util.List;

import app.famousenuf.util.StringUtil;

/**
 * Created by bharatbhusan on 2/7/16.
 */
public class DBUtil <T> {
    private DatabaseHelper helper = null;
    private Dao dao = null;

    public DBUtil(Context context, Class cl) {
        helper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        this.dao = helper.getDaoFromClass(cl);
    }

    public synchronized void add(T obj) {
        if (obj == null) {
            return;
        }
        try {
            dao.create(obj);
        } catch (Exception e) {
            update(obj);
        }
    }

    public synchronized void update(T obj) {
        if (obj == null) {
            return;
        }
        try {
            dao.update(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void remove(T obj) {
        if (obj == null) {
            return;
        }
        try {
            dao.delete(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void removeAll() {

        try {
            dao.deleteBuilder().delete();
        } catch (Exception e) {

        }
    }

    public List<T> fetch() {
        try {
            return dao.queryForAll();
        } catch (Exception e) {
            return null;
        }
    }

    public synchronized List<T> fetch(Pair pair, String groupBy) {
        try {
            QueryBuilder queryBuilder = dao.queryBuilder();
            if (pair != null) {
                queryBuilder.where().eq(pair.first.toString(), pair.second);
            }
            if (!StringUtil.isNullOrEmpty(groupBy)) {
                queryBuilder.groupBy(groupBy);
            }
            return dao.query(queryBuilder.prepare());
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

}
