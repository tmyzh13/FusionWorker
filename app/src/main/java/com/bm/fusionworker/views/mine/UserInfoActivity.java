package com.bm.fusionworker.views.mine;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.fusionworker.R;
import com.bm.fusionworker.utils.GlideImageLoader;
import com.bm.fusionworker.weights.NavBar;
import com.bumptech.glide.Glide;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;


import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.http.PUT;

import static com.bm.fusionworker.views.mine.ImageActivity.REQUEST_PERMISSION_ALL;

public class UserInfoActivity extends BaseActivity {

    public static final String tag = UserInfoActivity.class.getSimpleName();
    private Context context = UserInfoActivity.this;
    private final int REQUEST_CODE_SELECT_CAMERA = 0x0001;
    private final String USER_PHOTO_PATH = "";

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.head_iv)
    ImageView head_iv;

    //普通工号
    @Bind(R.id.name)
    TextView nameTv;
    @Bind(R.id.gender)
    TextView genderTv;
    @Bind(R.id.write_email)
    TextView write_email_Tv;
    @Bind(R.id.birthday_ll)
    LinearLayout birthday_ll;
    @Bind(R.id.birthday)
    TextView birthdayTv;
    @Bind(R.id.address)
    TextView addressTv;
    //华为工号
    @Bind(R.id.hw_ll)
    LinearLayout hw_ll;
    @Bind(R.id.department)
    TextView departmentTv;
    @Bind(R.id.write_phone)
    TextView write_phone_tv;
    @Bind(R.id.worker_number)
    TextView worker_number_tv;
    @Bind(R.id.user_type)
    TextView user_type_tv;
    @Bind(R.id.w3_ll)
    LinearLayout w3_ll;
    @Bind(R.id.w3account)
    TextView w3accountTv;

    private TextView cameraTv, galleryTv, cancelTv;
    private boolean isHwUser = false;
    private boolean isGoToCarema = true;
    private String uploadImageName = "";

    public static Intent getLauncher(Context context) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        return intent;
    }

    @Override
    public void goLogin() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setColorRes(R.color.app_blue);
        navBar.setNavTitle(getString(R.string.user_info));
        initView();
        initImagePicker();
    }

    private void initView() {
        if (isHwUser) {//华为账号
            birthday_ll.setVisibility(View.GONE);
        } else {
            birthday_ll.setVisibility(View.VISIBLE);
            hw_ll.setVisibility(View.GONE);
            w3_ll.setVisibility(View.GONE);
        }
    }

    private void initImagePicker(){
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());//设置图片加载器
        imagePicker.setShowCamera(false);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setMultiMode(false); //单选
        imagePicker.setSelectLimit(1);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(400);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(400);                        //保存文件的高度。单位像素
    }
    /**
     * 点击头像
     */
    @OnClick(R.id.head_iv)
    public void updateHeadIv() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chose_image_dialog, null);
        builder.setView(view);
        cameraTv = view.findViewById(R.id.camera);
        galleryTv = view.findViewById(R.id.gallery);
        cancelTv = view.findViewById(R.id.cancel);
        final Dialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();
        cameraTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                isGoToCarema = true;
                boolean isGo = checkPermisson();
                if (!isGo) {
                    return;
                }
                Intent intent = new Intent(UserInfoActivity.this, ImageActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                startActivityForResult(intent, REQUEST_CODE_SELECT_CAMERA);
            }
        });
        galleryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                isGoToCarema = false;
                boolean isGo1 = checkPermisson();
                if (!isGo1) {
                    return;
                }
                //从相册选取
                Intent intent1 = new Intent(UserInfoActivity.this, ImageActivity.class);
                startActivityForResult(intent1, REQUEST_CODE_SELECT_CAMERA);
            }
        });
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastMgr.show(R.string.action_cancel);
                dialog.dismiss();
            }
        });
    }

    private boolean checkPermisson() {
        if (!(checkPermission(Manifest.permission.CAMERA)) || !(checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_ALL);
            return false;
        }
        return true;
    }
    public boolean checkPermission(@NonNull String permission) {
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_ALL) {
            if(grantResults.length <2 || grantResults[0] == PackageManager.PERMISSION_DENIED || grantResults[1] == PackageManager.PERMISSION_DENIED){
                showToast(getString(R.string.permission_denied));
                return;
            } else {
                if(isGoToCarema) {
                    //相机拍照
                    Intent intent = new Intent(this, ImageActivity.class);
                    intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
                    startActivityForResult(intent, REQUEST_CODE_SELECT_CAMERA);
                } else {
                    //从相册选取
                    Intent intent1 = new Intent(this, ImageActivity.class);
                    startActivityForResult(intent1, REQUEST_CODE_SELECT_CAMERA);
                }
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQUEST_CODE_SELECT_CAMERA) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if(images != null && images.size() != 0) {
                    File file = new File(images.get(0).path);
                    Uri uri = Uri.fromFile(file);
                    Log.e(tag,"uri: " + uri.toString() + " .. " + file.exists());
                    //上传名称
                    String[] imgPath = uri.toString().split("/");
                    uploadImageName = "img/" + imgPath[imgPath.length - 1];
                    Log.e(tag,"uploadImageName: " + uploadImageName);

                    Glide.with(this).load(Uri.fromFile(file))
                            .into(head_iv);
                    PreferencesHelper.saveData(USER_PHOTO_PATH,images.get(0).path);
                    //更新图片
//                    presenter.uploadImage(file);
                }
            } else {
                Toast.makeText(this, R.string.data_null, Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
