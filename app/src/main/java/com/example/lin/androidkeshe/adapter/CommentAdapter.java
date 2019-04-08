package com.example.lin.androidkeshe.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lin.androidkeshe.R;
import com.example.lin.androidkeshe.entity.Comment;
import com.example.lin.androidkeshe.json.PingLun;

import java.util.List;

/**
 * Created by lin on 2018/7/2.
 * 描述:
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    private List<PingLun.DataBean> commentList;

    private PingLun.DataBean comment;
    private Context mContext ;

    public CommentAdapter(List<PingLun.DataBean> comments){
        this.commentList = comments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            comment = commentList.get(position);

//            String ImgUri = comment.getImageUrls().toString();
//        Glide.with(mContext).load(ImgUri).into(holder.comment_user_imgId);
        holder.comment_user_imgId.setImageResource(R.mipmap.ic_launcher);
            holder.comment_user_name.setText(comment.getCommenterScreenName());
            holder.comment_data.setText(comment.getPublishDateStr());
            holder.comment_rating.setText(comment.getRating()+"");
           // holder.comment_content.setText(comment.getContent().toString());
        holder.comment_content.setText("dddddd");
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView comment_user_imgId;
        TextView comment_user_name;
        TextView comment_rating ;
        TextView comment_content;
        TextView comment_data ;


        public ViewHolder(View itemView) {
            super(itemView);

            comment_user_imgId =(ImageView)itemView.findViewById(R.id.comment_user_imgId);
            comment_user_name = (TextView)itemView.findViewById(R.id.comment_user_name);
            comment_rating =(TextView)itemView.findViewById(R.id.comment_rating);
            comment_content = (TextView)itemView.findViewById(R.id.comment_content);
            comment_data = (TextView)itemView.findViewById(R.id.comment_data);

        }
    }
}
