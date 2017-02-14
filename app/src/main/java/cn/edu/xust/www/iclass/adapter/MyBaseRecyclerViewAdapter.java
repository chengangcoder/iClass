package cn.edu.xust.www.iclass.adapter;


import android.graphics.Color;
import android.support.v7.widget.CardView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.edu.xust.www.iclass.R;
import cn.edu.xust.www.iclass.entity.HomeItem;

/**
 * Created by chengang on 2016/11/25.
 */

public class MyBaseRecyclerViewAdapter extends BaseQuickAdapter<HomeItem> {


    public MyBaseRecyclerViewAdapter(int layoutResId, List<HomeItem> data) {
        super(layoutResId, data);
    }


    // 顾名思义convert相当于转换已废弃的viewholder，重写其中控件的值，以达到重用的目的

    @Override
    protected void convert(BaseViewHolder viewHolder, HomeItem item) {


        // 重写viewholder中的控件的值
        viewHolder.setText(R.id.info_text, item.getTitle());
        // 重新设置控件的background颜色
        CardView cardView = viewHolder.getView(R.id.card_view);
        cardView.setCardBackgroundColor(Color.parseColor(item.getColorStr()));
    }
}
