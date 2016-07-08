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
public class CommentAdapter extends ArrayAdapter<FacebookComment> {
    public CommentAdapter(Context context, int resource, List<FacebookComment> list) {
        super(context, resource, list);
    }

    private static class ViewHolder {
        TextView commentUser;
        TextView comment;

        public ViewHolder(View view) {
            comment = (TextView) view.findViewById(R.id.tvComment);
            commentUser = (TextView) view.findViewById(R.id.tvCommentUser);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        FacebookComment comment = getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.comment_post_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.commentUser.setText(comment.getUserDetail().getName());
        holder.comment.setText(comment.getMessage());

        return convertView;
    }
}
