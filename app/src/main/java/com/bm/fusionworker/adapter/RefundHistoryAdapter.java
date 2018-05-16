package com.bm.fusionworker.adapter;

import android.content.Context;

import com.bm.fusionworker.R;
import com.bm.fusionworker.model.beans.RefundItemBean;
import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;

/**
 * Created by issuser on 2018/5/10.
 */

public class RefundHistoryAdapter extends QuickAdapter<RefundItemBean> {

    public RefundHistoryAdapter(Context context) {
        super(context, R.layout.item_refund_history_layout);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, RefundItemBean item, int position) {
        helper.setText(R.id.title_tv, item.title)
                .setText(R.id.time_tv, item.time)
                .setText(R.id.distance_tv, "距离您<"+item.distance)
                .setText(R.id.address_tv, item.address)
                .setText(R.id.refund_reason, item.reason);
    }
}
