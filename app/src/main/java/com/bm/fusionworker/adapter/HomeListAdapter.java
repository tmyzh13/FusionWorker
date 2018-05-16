package com.bm.fusionworker.adapter;

import android.content.Context;
import android.widget.TextView;

import com.bm.fusionworker.R;
import com.bm.fusionworker.model.beans.RepairDataBean;
import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;

/**
 * Created by issuser on 2018/5/9.
 */

public class HomeListAdapter extends QuickAdapter<RepairDataBean> {
    private Context mContext;

    public HomeListAdapter(Context context) {
        super(context, R.layout.item_home_list);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseAdapterHelper helper, RepairDataBean item, int position) {
        helper.setText(R.id.title_tv, item.title)
                .setText(R.id.time_tv, item.time)
                .setText(R.id.distance_tv, item.distance)
                .setText(R.id.address_tv, item.address);

        TextView statusTv = helper.getView(R.id.status_tv);
        if (item.status == 0) {
            statusTv.setText(mContext.getString(R.string.status_unconfirmed));
            statusTv.setTextColor(mContext.getResources().getColor(R.color.white));
            statusTv.setBackgroundResource(R.drawable.red_conner_shape_bg);
        } else if (item.status == 1) {
            statusTv.setText(mContext.getString(R.string.status_ongoing));
            statusTv.setTextColor(mContext.getResources().getColor(R.color.yellow));
            statusTv.setBackgroundResource(R.drawable.gray_stroke_bg);
        } else if (item.status == 2) {
            statusTv.setText(mContext.getString(R.string.status_not_acceptance));
            statusTv.setTextColor(mContext.getResources().getColor(R.color.green));
            statusTv.setBackgroundResource(R.drawable.gray_stroke_bg);
        }
    }
}
