package com.bm.fusionworker.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bm.fusionworker.R;
import com.bm.fusionworker.model.beans.UserInspectionListItemBean;
import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;

/**
 * Created by issuser on 2018/5/15.
 */

public class UserInspectionListAdapter extends QuickAdapter<UserInspectionListItemBean> implements View.OnClickListener {
    private Context mContext;

    public UserInspectionListAdapter(Context context) {
        super(context, R.layout.item_user_inspection_list);
        this.mContext = context;
    }

    private InnerItemOnClickListener listener;

    boolean isChecked = false;

    @Override
    protected void convert(BaseAdapterHelper helper, UserInspectionListItemBean item, final int position) {
        helper.setText(R.id.address_tv, item.location)
                .setText(R.id.inspection_person_tv, item.inspector)
                .setText(R.id.inspection_time_tv, item.inspectionTime)
                .setText(R.id.inspection_cycle_tv, item.inspectionCycle);

        final RadioButton rb = helper.getView(R.id.rb);
        final TextView name = helper.getView(R.id.inspection_person_tv);
        final TextView time = helper.getView(R.id.inspection_time_tv);
        final TextView cycle = helper.getView(R.id.inspection_cycle_tv);

        changeUi(isChecked, name, time, cycle);
        rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.rb:
                        if (isChecked) {
                            isChecked = false;
                            changeUi(isChecked, name, time, cycle);
                        } else {
                            isChecked = true;
                            changeUi(isChecked, name, time, cycle);
                            listener.itemChecked(position);
                        }
                        rb.setChecked(isChecked);
                        break;
                    default:
                        break;
                }
            }
        });
        name.setOnClickListener(this);
        time.setOnClickListener(this);
        cycle.setOnClickListener(this);
    }

    private void changeUi(boolean isChecked, TextView name, TextView time, TextView cycle) {
        if (isChecked) {
            Drawable dl = mContext.getResources().getDrawable(R.mipmap.pop_ic_down);
            name.setBackgroundResource(R.drawable.rb_checked_gray_bg);
            name.setCompoundDrawablesWithIntrinsicBounds(null, null, dl, null);
            time.setBackgroundResource(R.drawable.rb_checked_gray_bg);
            time.setCompoundDrawablesWithIntrinsicBounds(null, null, dl, null);
            cycle.setBackgroundResource(R.drawable.rb_checked_gray_bg);
            cycle.setCompoundDrawablesWithIntrinsicBounds(null, null, dl, null);
        } else {
            name.setBackgroundResource(R.drawable.rb_unchecked_white_bg);
            name.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            time.setBackgroundResource(R.drawable.rb_unchecked_white_bg);
            time.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            cycle.setBackgroundResource(R.drawable.rb_unchecked_white_bg);
            cycle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }

    public interface InnerItemOnClickListener {
        void viewClick(View view);
        void itemChecked(int position);
    }

    public void setListener(InnerItemOnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.viewClick(v);
    }
}
