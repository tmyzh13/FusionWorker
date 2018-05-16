package com.bm.fusionworker.adapter;

import android.content.Context;

import com.bm.fusionworker.R;
import com.bm.fusionworker.model.beans.ChargeFeeBean;
import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;

/**
 * Created by issuser on 2018/4/19.
 */

public class FeeDialogListAdapter extends QuickAdapter<ChargeFeeBean> {

    public FeeDialogListAdapter(Context context){
        super(context, R.layout.item_dialog_fee_list);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, ChargeFeeBean item, int position) {
        helper.setText(R.id.tv_time,item.startTime+"~"+item.endTime)
                .setText(R.id.tv_unit,item.multiFee+context.getString(R.string.yuan));
    }
}
