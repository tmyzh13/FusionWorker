package com.bm.fusionworker.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.fusionworker.R;
import com.bm.fusionworker.constants.Constant;
import com.bm.fusionworker.constants.Urls;
import com.bm.fusionworker.utils.FileSizeUtil;
import com.bm.fusionworker.utils.SelectImageHelper;
import com.bm.fusionworker.utils.Tools;
import com.bm.fusionworker.utils.UploadImage;
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

public class RepairingActivity extends BaseActivity implements View.OnClickListener {
    private Context context = RepairingActivity.this;
    private static final int MY_PERMISSIONS_REQUEST_CALL_CAMERA = 1;
    @Bind(R.id.nav)
    NavBar navBar;

    @Bind(R.id.start_dot)
    View start_dot;
    @Bind(R.id.end_dot)
    View end_dot;
    @Bind(R.id.start_repair_tv)
    TextView start_repair_tv;
    @Bind(R.id.repair_history_tv)
    TextView repair_history_tv;

    //图片
    @Bind(R.id.layout_pic)
    LinearLayout layout_pic;
    @Bind(R.id.gv_pic)
    NoScrollingGridView gv_pic;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.arrive_time_tv)
    TextView arrive_time_tv;

    //记录信息
    @Bind(R.id.layout_info)
    LinearLayout layout_info;
    @Bind(R.id.repaired_time_tv)
    TextView repaired_time_tv;
    @Bind(R.id.repaired_parts_tv)
    TextView repaired_parts_tv;
    @Bind(R.id.handle_result_et)
    EditText handle_result_et;
    @Bind(R.id.remarks_info_et)
    EditText remarks_info_et;

    private SelectImageHelper helper;
    private ChooseImagePopupWindow window;
    //需要图片数量
    private int count = 4;
    private ArrayList<String> list = new ArrayList<>();
    private HashMap<String, String> mFiles;
    private int kind = 2;//0：拍照、相册 1：拍照 2：相册


    public static Intent getLauncher(Context context) {
        Intent intent = new Intent(context, RepairingActivity.class);
        return intent;
    }

    @Override
    public void goLogin() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_repairing;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setColorRes(R.color.app_blue);
        navBar.setNavTitle(getString(R.string.repairing));
        arrive_time_tv.setText(Tools.getCurrentTime());
        iv_back.setOnClickListener(this);
        mFiles = (HashMap<String, String>) getIntent().getSerializableExtra("files");
        if (mFiles == null) {
            mFiles = new HashMap<>();
        }
        helper = new SelectImageHelper(count, gv_pic, R.layout.item_select_pic);
        helper.toObservable().subscribe(new Action1<Integer>() {
            @Override
            public void call(final Integer position) {

                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //如果没有授权，则请求授权
                    ActivityCompat.requestPermissions(RepairingActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CALL_CAMERA);
                } else {
//                    helper.openCamera(position, "");
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
        if (mFiles != null && mFiles.size() != 0) {
            Log.e("dp", "currentFile");
            ArrayList<String> localFiles = new ArrayList<>();
            for (String path : mFiles.keySet()) {
                localFiles.add(path);
            }
            helper.setPicFiles(localFiles);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //判断请求码
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_CAMERA) {
            //grantResults授权结果
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                //授权失败
                Toast.makeText(getViewContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @OnClick(R.id.submit_tv)
    public void submit() {
        //----test
        layout_pic.setVisibility(View.GONE);
        layout_info.setVisibility(View.VISIBLE);
        //----test
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
                    String uploadPath = mFiles.get(path);
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
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                showDialogView();
                break;
            default:
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            showDialogView();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void showDialogView() {
        AlertDialog alert = new AlertDialog.Builder(context).setTitle(R.string.information_tips)
                .setMessage(R.string.exsit)
                .setPositiveButton(R.string.action_confirm, new DialogInterface.OnClickListener() {
                    @Override//处理确定按钮点击事件
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
                    @Override//取消按钮点击事件
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();//对话框关闭。
                    }
                }).create();

        alert.show();
    }
}
