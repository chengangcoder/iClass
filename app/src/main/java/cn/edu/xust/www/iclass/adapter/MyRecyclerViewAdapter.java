package cn.edu.xust.www.iclass.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.edu.xust.www.iclass.R;

/**
 * Created by chengang on 2016/11/23.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<String> list;


    public MyRecyclerViewAdapter(Context context, List<String> list) {
        super();

        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false));

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(list.get(position));
        holder.image.setImageResource(R.drawable.chat);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}


class MyViewHolder extends RecyclerView.ViewHolder {


    public final ImageView image;
    public final TextView tv;

    public MyViewHolder(View itemView) {
        super(itemView);

        image = (ImageView) itemView.findViewById(R.id.recyclerview_iv);
        tv = (TextView) itemView.findViewById(R.id.recyclerview_tv);
    }
}