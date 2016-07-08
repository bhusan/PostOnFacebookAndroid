package app.famousenuf.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import app.famousenuf.R;

/**
 * Created by bharatbhusan on 3/7/16.
 */
public class LikeAdapter extends ArrayAdapter<FacebookUserDetail> {
    public LikeAdapter(Context context, int resource, List<FacebookUserDetail> list) {
        super(context, resource, list);
    }

    private static class ViewHolder {
        TextView likeUser;

        public ViewHolder(View view) {
            likeUser = (TextView) view.findViewById(R.id.tvLikeUser);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        FacebookUserDetail userDetail = getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.post_like_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.likeUser.setText(userDetail.getName());

        return convertView;
    }
}
