package com.bm.fusionworker.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bm.fusionworker.R;
import com.bm.fusionworker.constants.Constant;
import com.bm.fusionworker.constants.Urls;
import com.bm.fusionworker.utils.FileSizeUtil;
import com.bm.fusionworker.utils.SelectImageHelper;
import com.bm.fusionworker.utils.Tools;
import com.bm.fusionworker.utils.UploadImage;
import com.bm.fusionworker.views.mine.PersonalCenterActivity;
import com.bm.fusionworker.weights.ChooseImagePopupWindow;
import com.bm.fusionworker.weights.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.corelibs.views.NoScrollingGridView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

public class InspectioningActivity extends BaseActivity implements View.OnClickListener {
    private Context context = InspectioningActivity.this;
    private static final int MY_PERMISSIONS_REQUEST_CALL_CAMERA = 1;
    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.layout_work_list_details)
    LinearLayout layout_work_list_details;//工单详情
    @Bind(R.id.arrive_time_tv)
    TextView arrive_time_tv;//到达现场时间
    @Bind(R.id.onsite_person_tv)
    TextView onsite_person_tv;//现场人员
    @Bind(R.id.onsite_des_tv)
    TextView onsite_des_tv;//现场描述
    @Bind(R.id.sw_btn)
    Switch sw_btn;

    @Bind(R.id.layout_fault)
    LinearLayout layout_fault;
    @Bind(R.id.site_pic_gv)
    NoScrollingGridView site_pic_gv;//站点照片
    @Bind(R.id.fault_pic_gv)
    NoScrollingGridView fault_pic_gv;//故障照片

    @Bind(R.id.register_more_fault_tv)
    TextView register_more_fault_tv;//登记更多故障信息

    private SelectImageHelper helper;
    private ChooseImagePopupWindow window;
    //需要图片数量
    private int count = 4;
    private ArrayList<String> list = new ArrayList<>();
    private HashMap<String, String> mSiteFiles;
    private HashMap<String, String> mFaultFiles;
    private int kind = 2;//0：拍照、相册 1：拍照 2：相册

    public static Intent getLauncher(Context context) {
        Intent intent = new Intent(context, InspectioningActivity.class);
        return intent;
    }

    @Override
    public void goLogin() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_inspectioning;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setColorRes(R.color.app_blue);
        navBar.setNavTitle(getString(R.string.inspection_list));
        sw_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    layout_fault.setVisibility(View.VISIBLE);
                } else {
                    layout_fault.setVisibility(View.GONE);
                }
            }
        });
        arrive_time_tv.setText(Tools.getCurrentTime());
        clicks();
        choseSitePicture();
        choseFaultPicture();
    }

    private void choseSitePicture() {
        mSiteFiles = (HashMap<String, String>) getIntent().getSerializableExtra("files");
        if (mSiteFiles == null) {
            mSiteFiles = new HashMap<>();
        }
        helper = new SelectImageHelper(count, site_pic_gv, R.layout.item_select_pic);
        helper.toObservable().subscribe(new Action1<Integer>() {
            @Override
            public void call(final Integer position) {

                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //如果没有授权，则请求授权
                    ActivityCompat.requestPermissions(InspectioningActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CALL_CAMERA);
                } else {
                    if (kind == 2) {
                        helper.openGallery(position, "");
                        return;
                    }
                    if (window == null)
                        window = new ChooseImagePopupWindow(context, kind);
                    window.setOnTypeChosenListener(new ChooseImagePopupWindow.OnTypeChosenListener() {
                        @Override
                        public void onCamera() {
                            helper.openCamera(position, "");
                        }

                        @Override
                        public void onGallery() {
                            helper.openGallery(position, "");
                        }
                    });
                    window.showAtBottom(navBar);
                }
            }
        });
        if (mSiteFiles != null && mSiteFiles.size() != 0) {
            Log.e("dp", "currentFile");
            ArrayList<String> localFiles = new ArrayList<>();
            for (String path : mSiteFiles.keySet()) {
                localFiles.add(path);
            }
            helper.setPicFiles(localFiles);
        }
    }
    private void choseFaultPicture() {
        mFaultFiles = (HashMap<String, String>) getIntent().getSerializableExtra("files");
        if (mFaultFiles == null) {
            mFaultFiles = new HashMap<>();
        }
        helper = new SelectImageHelper(count, fault_pic_gv, R.layout.item_select_pic);
        helper.toObservable().subscribe(new Action1<Integer>() {
            @Override
            public void call(final Integer position) {

                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //如果没有授权，则请求授权
                    ActivityCompat.requestPermissions(InspectioningActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CALL_CAMERA);
                } else {
                    if (kind == 2) {
                        helper.openGallery(position, "");
                        return;
                    }
                    if (window == null)
                        window = new ChooseImagePopupWindow(context, kind);
                    window.setOnTypeChosenListener(new ChooseImagePopupWindow.OnTypeChosenListener() {
                        @Override
                        public void onCamera() {
                            helper.openCamera(position, "");
                        }

                        @Override
                        public void onGallery() {
                            helper.openGallery(position, "");
                        }
                    });
                    window.showAtBottom(navBar);
                }
            }
        });
        if (mFaultFiles != null && mFaultFiles.size() != 0) {
            Log.e("dp", "currentFile");
            ArrayList<String> localFiles = new ArrayList<>();
            for (String path : mFaultFiles.keySet()) {
                localFiles.add(path);
            }
            helper.setPicFiles(localFiles);
        }
    }

    private void clicks() {
        layout_work_list_details.setOnClickListener(this);
        onsite_person_tv.setOnClickListener(this);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_work_list_details:
                break;
            case R.id.onsite_person_tv:
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.submit_tv)
    public void submit(){
        //-----上传站点图片start
        List<File> listFiles = helper.getChosenImages();
        for (int i = 0; i < listFiles.size(); i++) {
            String path = FileSizeUtil.compressImage(listFiles.get(i).getPath());
            list.add(path);
        }

        if (list.size() == 0) {
            ToastMgr.show("还未上传图片");
//            finish();
            return;
        }
        showLoading();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                hideLoading();
                switch (msg.arg1) {
                    case 1:
                        HashMap<String, String> map = (HashMap<String, String>) msg.obj;
                        if (map != null && map.size() > 0) {
                            Intent intent = new Intent();
                            intent.putExtra(Constant.TEMP_PIC_LIST, map);
                            setResult(10, intent);
                            ToastMgr.show(getString(R.string.submit_success));
                            finish();
                        } else {
                            ToastMgr.show(getString(R.string.submit_fail));
                        }
                        break;
                    case 0:
                        ToastMgr.show(getString(R.string.submit_fail));
                        break;
                }
            }
        };

        //上传图片
        String token = PreferencesHelper.getData("token");
        final String token1 = token.replaceAll("\"", "");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String picPath = "";
                Message message = new Message();
                HashMap<String, String> files = new HashMap<>();
                for (final String path : list) {
                    String uploadPath = mSiteFiles.get(path);
                    if (TextUtils.isEmpty(uploadPath)) { // 没上传过就上传
                        picPath = UploadImage.uploadFile(Urls.ROOT + Urls.UPLOAD_IMAGE, path, token1);
                        if (picPath == null) {
                            message.arg1 = 0;
                            handler.sendMessage(message);
                            return;
                        }
                        com.alibaba.fastjson.JSONObject object = null;
                        try {
                            object = com.alibaba.fastjson.JSONObject.parseObject(picPath);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (object == null) {
                            message.arg1 = 0;
                            handler.sendMessage(message);
                            return;
                        }

                        uploadPath = object.getString("mess").toString();
                    }
                    // 上传过就直接使用地址
                    files.put(path, uploadPath);
                }
                message.arg1 = 1;
                message.obj = files;
                handler.sendMessage(message);
            }
        }).start();
        //-----上传站点图片end
    }
}
