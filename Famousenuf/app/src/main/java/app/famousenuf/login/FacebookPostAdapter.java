package app.famousenuf.login;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.widget.LikeView;

import java.util.List;

import app.famousenuf.R;
import app.famousenuf.base.ImageLoader;
import app.famousenuf.util.FacebookUtil;
import app.famousenuf.util.ListUtil;
import app.famousenuf.util.StringUtil;

/**
 * Created by bharatbhusan on 2/7/16.
 */
public class FacebookPostAdapter extends ArrayAdapter<UserFacebookPost> {
    public FacebookPostAdapter(Context context, int resource) {
        super(context, resource);
    }

    private static class ViewHolder {
        ImageView sharedImage;
        TextView message;
        TextView likeCount;
        TextView commentCount;
        TextView addLike;
        TextView addComment;

        public ViewHolder(View view) {
            sharedImage = (ImageView) view.findViewById(R.id.ivPost);
            likeCount = (TextView) view.findViewById(R.id.tvLike);
            message = (TextView) view.findViewById(R.id.tvMessage);
            commentCount = (TextView) view.findViewById(R.id.tvComment);
            addLike = (TextView) view.findViewById(R.id.tvAddLike);
            addComment = (TextView) view.findViewById(R.id.tvAddComment);

        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        UserFacebookPost userFacebookPost = getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user_post, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        if (StringUtil.isNullOrEmpty(userFacebookPost.getFullPicture())) {
            holder.sharedImage.setVisibility(View.GONE);
        } else {
            holder.sharedImage.setVisibility(View.VISIBLE);
            holder.sharedImage.setImageBitmap(null);
            new ImageLoader(holder.sharedImage, userFacebookPost.getFullPicture(), metrics.widthPixels, metrics.widthPixels / 3);
        }
        if (userFacebookPost.getFacebookLikeList() == null || ListUtil.isListEmpty(userFacebookPost.getFacebookLikeList().getFacebookLikeList())) {
            holder.likeCount.setText(Html.fromHtml("<u>" + 0 + " Like</u>"));

        } else {
            if (userFacebookPost.getFacebookLikeList().getFacebookLikeList().size() < 2) {
                holder.likeCount.setText(Html.fromHtml("<u>" + userFacebookPost.getFacebookLikeList().getFacebookLikeList().size() + " Like</u>"));

            } else {
                holder.likeCount.setText(Html.fromHtml("<u>" + userFacebookPost.getFacebookLikeList().getFacebookLikeList().size() + " Likes</u>"));
            }
        }
        if (userFacebookPost.getFacebookCommentList() == null || ListUtil.isListEmpty(userFacebookPost.getFacebookCommentList().getFacebookCommentList())) {
            holder.commentCount.setText(Html.fromHtml("<u>" + 0 + " Comment</u>"));

        } else {
            if (userFacebookPost.getFacebookCommentList().getFacebookCommentList().size() < 2) {
                holder.commentCount.setText(Html.fromHtml("<u>" + userFacebookPost.getFacebookCommentList().getFacebookCommentList().size() + " Comment</u>"));

            } else {
                holder.commentCount.setText(Html.fromHtml("<u>" + userFacebookPost.getFacebookCommentList().getFacebookCommentList().size() + " Comments</u>"));
            }
        }
        if (StringUtil.isNullOrEmpty(userFacebookPost.getMessage())) {
            holder.message.setVisibility(View.GONE);
        } else {
            holder.message.setVisibility(View.VISIBLE);
            holder.message.setText(Html.fromHtml(userFacebookPost.getMessage()));
        }
        holder.likeCount.setTag(userFacebookPost.getFacebookLikeList());
        holder.likeCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FacebookLikeList likeList = (FacebookLikeList) v.getTag();
                if (likeList == null || ListUtil.isListEmpty(likeList.getFacebookLikeList())) {
                    Toast.makeText(v.getContext(), "Sorry, No one has liked this post", Toast.LENGTH_LONG).show();
                    return;
                }
                openLikeDialog(likeList.getFacebookLikeList());
            }
        });
        holder.commentCount.setTag(userFacebookPost.getFacebookCommentList());
        holder.commentCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FacebookCommentList commentList = (FacebookCommentList) v.getTag();
                if (commentList == null || ListUtil.isListEmpty(commentList.getFacebookCommentList())) {
                    Toast.makeText(v.getContext(), "Sorry, No one has comment on this post", Toast.LENGTH_LONG).show();
                    return;
                }
                openCommentDialog(commentList.getFacebookCommentList());
            }
        });
        holder.addLike.setTag(userFacebookPost.getId());
        holder.addLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postId = (String) v.getTag();
                FacebookUtil.likePost(postId);
            }
        });
        holder.addComment.setTag(userFacebookPost.getId());
        holder.addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postId = (String) v.getTag();
                openCommentDialog(postId);
            }
        });
        return convertView;
    }

    private void openCommentDialog(final String postId) {
        final EditText editText = new EditText(getContext());
        new AlertDialog.Builder(getContext()).setTitle("Comment").setView(editText).setCancelable(true).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        }).setPositiveButton("Post comment", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editText.getText() != null || !StringUtil.isNullOrEmpty(editText.getText().toString())) {
                    FacebookUtil.addComment(postId, editText.getText().toString());
                    dialog.dismiss();

                } else {
                    Toast.makeText(getContext(), "Please enter something special about post", Toast.LENGTH_SHORT).show();
                }
            }
        }).show();
    }

    private void openLikeDialog(List<FacebookUserDetail> likeList) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_view, null);
        LikeAdapter adapter = new LikeAdapter(getContext(), R.layout.post_like_view, likeList);
        ListView listView = (ListView) view.findViewById(R.id.lvListView);
        listView.setAdapter(adapter);

        new AlertDialog.Builder(getContext()).setTitle("Likes").setView(view).setCancelable(true).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        }).show();

    }

    private void openCommentDialog(List<FacebookComment> commentList) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_view, null);
        CommentAdapter adapter = new CommentAdapter(getContext(), R.layout.comment_post_view, commentList);
        ListView listView = (ListView) view.findViewById(R.id.lvListView);
        listView.setAdapter(adapter);

        new AlertDialog.Builder(getContext()).setTitle("Comments").setView(view).setCancelable(true).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        }).show();
    }
}
