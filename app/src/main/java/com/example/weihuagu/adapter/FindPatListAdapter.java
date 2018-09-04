package com.example.weihuagu.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weihuagu.model.FindComment;
import com.example.weihuagu.websocket.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FindPatListAdapter extends BaseAdapter {

    private ArrayList<FindComment> updata;
    private Context context;
    private String data_id;

    public FindPatListAdapter(Context context) {
        this.context = context;


    }


    public void setDeviceEntity(FindComment entity) {
        if (updata != null && updata.size() > 0) {
            ArrayList<FindComment> list = new ArrayList<>();

            list.add(entity);
            list.addAll(updata);
            updata.clear();
            updata.addAll(list);

        } else {
            updata = new ArrayList<>();
            updata.add(entity);
        }
        notifyDataSetChanged();


    }


    public void setDataId(String data_id) {
        this.data_id = data_id;
    }


    public void setDeviceList(ArrayList<FindComment> list) {
        if (updata != null) {
            updata.addAll(list);


        } else {
            updata = (ArrayList<FindComment>) list.clone();

        }
        notifyDataSetChanged();


    }

    public void clearDeviceList() {
        if (updata != null) {
            updata.clear();

        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return updata == null ? 0 : updata.size();

    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // TODO Auto-generated method stub

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.find_pat_comment, parent, false);
            view.setTag(new DishViewHolder(view));
        }
        DishViewHolder holder = (DishViewHolder) view.getTag();
//        FindComment dEntity = updata.get(position);
//
//
//        GlideUtils.lImgRound(context, dEntity.user_avatar, holder.imgAvator);
//
//        holder.tvTime.setText(dEntity.addtime);
//
//        if (TextUtils.isEmpty(dEntity.f_user_id) || dEntity.f_user_id.equals("0")) {
//            holder.tvName.setText(dEntity.name);
//
//            holder.tvDetials.setText(dEntity.comment);
//        } else {
//            holder.tvName.setText(dEntity.name);
//
//            SpecialTextUnit textUnit = new SpecialTextUnit("回复").setTextColor(Color.parseColor("#353535"));
//            SpecialTextUnit textUnit1 = new SpecialTextUnit(dEntity.f_user_name).setTextColor(Color.parseColor("#A9BDC7"));
//            SpecialTextUnit textUnit2 = new SpecialTextUnit(":").setTextColor(Color.parseColor("#353535"));
//
//            SpannableStringBuilder content = new SimplifySpanBuild().append(textUnit).append(" ").append(textUnit1).append(textUnit2).append(" ").append(dEntity.comment).build();
//            holder.tvDetials.setText(content);
//        }
//
//        holder.tvLikeCount.setText("" + dEntity.likes);
//
//        if (dEntity.is_like == 0) {
//            holder.imgLike.setImageResource(R.drawable.icon_new_articles_like_normal);
//        } else {
//            holder.imgLike.setImageResource(R.drawable.icon_new_condition_praise);
//        }
//
//
//        holder.imgLike.setOnClickListener(v -> {
//            // presenter.setLike(id, "4");
//            //holder.imgLike.setImageResource(R.drawable.icon_new_condition_praise);
//
//            if (dEntity.is_like == 0) {
//                setCommitListLike(data_id, holder.tvLikeCount, holder.imgLike);
//            } else {
//                Toasts.toast(context, "您已经点过赞了");
//            }
//        });


        return view;
    }


//    public void setCommitListLike(String id, TextView tvLikeCount, ImageView iv) {
//
//        OkHttpUtils okHttpUtils = new OkHttpUtils(NetEngine.getClient());
//        okHttpUtils
//                .get()
//                .url(Constants.BASE_URL + "User/Find/likes")
//                .addParams("data_id", id)
//                .addParams("type", "4")
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//
//
//                        JSONObject obj = null;
//                        try {
//                            obj = new JSONObject(response);
//                            String status = obj.getString("status");
//                            String info = obj.getString("info");
//                            Toasts.toast(context, info);
//                            if (!status.equals("1")) {
//                                return;
//                            }
//                            if (TextUtils.isEmpty(tvLikeCount.getText().toString())) {
//                                try {
//                                    int a = Integer.parseInt(tvLikeCount.getText().toString());
//                                    a = a + 1;
//                                    tvLikeCount.setText(String.valueOf(a));
//
//                                } catch (Exception e) {
//
//                                }
//
//
//                            } else {
//                                tvLikeCount.setText("1");
//                            }
//                            iv.setImageResource(R.drawable.icon_new_condition_praise);
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                });
//
//
//    }


    public class DishViewHolder {


        ImageView imgAvator;
        TextView tvName;
        TextView tvTime;
        TextView tvLikeCount;
        ImageView imgLike;
        TextView tvDetials;

        public DishViewHolder(View view) {

            imgAvator = view.findViewById(R.id.img_avator);
            tvName = view.findViewById(R.id.tv_name);
            tvTime = view.findViewById(R.id.tv_time);
            tvLikeCount = view.findViewById(R.id.tv_like_count);
            imgLike = view.findViewById(R.id.img_like);
            tvDetials = view.findViewById(R.id.tv_detials);


        }
    }
}
