package com.bm.fusionworker.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.bm.fusionworker.R;
import com.bm.fusionworker.constants.Constant;
import com.bm.fusionworker.model.beans.MyLocationBean;
import com.bm.fusionworker.model.beans.RepairDataBean;
import com.bm.fusionworker.model.interfaces.RepairListView;
import com.bm.fusionworker.presenter.RepairListPresenter;
import com.bm.fusionworker.utils.ChoiceManager;
import com.bm.fusionworker.utils.Tools;
import com.corelibs.base.BaseFragment;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.corelibs.utils.rxbus.RxBus;
import com.huawei.idesk.sdk.fsm.IFile;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 维修
 */
public class RepairFragment extends BaseFragment<RepairListView, RepairListPresenter> implements RepairListView, LocationSource, AMapLocationListener, AMap.OnMapTouchListener {

    @Bind(R.id.map)
    MapView map;
    @Bind(R.id.iv_my_location)
    ImageView iv_my_location;

    private AMap aMap;
    private List<RepairDataBean> list = new ArrayList<>();

    private RepairDataBean repairDataBean;
    LocationSource.OnLocationChangedListener mListener;
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;

    @Override
    public void goLogin() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_repair;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        map.onCreate(savedInstanceState);
        aMap = map.getMap();
        UiSettings uiSettings = aMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);//隐藏默认缩放按钮
        location();

//        presenter.getRepairListAction();//请求数据
    }

    @OnClick(R.id.tv_list)
    public void goList() {
        startActivity(HomeListActivity.getLauncher(getContext(),0));
    }

    @Override
    protected RepairListPresenter createPresenter() {
        return new RepairListPresenter();
    }

    private void location() {
        //控制手势
//        aMap.getUiSettings().setRotateGesturesEnabled(false);
//        aMap.getUiSettings().setTiltGesturesEnabled(false);
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 设置定位监听
        aMap.setLocationSource(this);
        // 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        myLocationStyle.interval(10000);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.mipmap.location);
        BitmapDescriptor markerIcon = BitmapDescriptorFactory
                .fromView(imageView);
        myLocationStyle.myLocationIcon(markerIcon);
        //连续定位、蓝点会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);

        aMap.setMyLocationStyle(myLocationStyle);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        //移动时 不让地图到定位点
        aMap.setOnMapTouchListener(this);
        // 定义 Marker 点击事件监听
        AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {
            // marker 对象被点击时回调的接口
            // 返回 true 则表示接口已响应事件，否则返回false
            @Override
            public boolean onMarkerClick(Marker marker) {
                RepairDataBean bean = list.get(Integer.parseInt(marker.getTitle()));
                repairDataBean = bean;
                aMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(bean.latitude, bean.longitude)));
                ToastMgr.show(marker.getTitle() + "");
                return true;
            }
        };
        // 绑定 Marker 被点击事件
        aMap.setOnMarkerClickListener(markerClickListener);
    }

    /**
     * 维修 数据在地图上显示
     *
     * @param list
     */
    @Override
    public void renderData(List<RepairDataBean> list) {
        this.list = list;
        MyLocationBean bean = PreferencesHelper.getData(MyLocationBean.class);
        aMap.clear();
        for (int i = 0; i < 3; i++) {
            RepairDataBean bean1 = new RepairDataBean();
            bean1.latitude = 30.521555 + (i + 1) * 0.2;
            bean1.longitude = 114.348266 + (i + 1) * 0.2;
            bean1.status = i;
            list.add(bean1);
        }
        for (int i = 0; i < list.size(); i++) {
            RepairDataBean dataBean = list.get(i);
            //距离筛选
            if (bean != null) {
                if (Tools.getDistance(dataBean.latitude, dataBean.longitude, bean.latitude, bean.longtitude)
                        > ChoiceManager.instance.getDistance()) ;
                continue;
            }
            MarkerOptions markerOption = new MarkerOptions();//标记物
            LatLng latLng = new LatLng(dataBean.latitude, dataBean.longitude);
            markerOption.position(latLng);

            markerOption.title(i + "");
            markerOption.draggable(false);//设置Marker不可拖动
            //----test 根据状态设置不同marker
            if (dataBean.status == 0) {
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.home_ic_position)));
            } else if (dataBean.status == 1) {
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.home_ic_position1)));
            } else if (dataBean.status == 2) {
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icn_cancel_white)));
            } else {
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.list_ic_dian)));
            }

            markerOption.setFlat(false);//设置marker平贴地图效果
            aMap.addMarker(markerOption);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != map) {
            map.onDestroy();
        }
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
        //动态请求权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), R.string.no_permission, Toast.LENGTH_SHORT).show();
                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE}, 10);
            }
        }
    }

    //Android6.0申请权限的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case 10:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                    //TODO 请求数据
                } else {
                    // 没有获取到权限，做特殊处理
                    ToastMgr.show(getString(R.string.no_permission));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        map.onSaveInstanceState(outState);
    }

    private boolean followMove = true;
    private boolean isReportUserLocation = false;

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //显示自己位置点
                mListener.onLocationChanged(aMapLocation);
                MyLocationBean bean = new MyLocationBean();
                bean.latitude = aMapLocation.getLatitude();
                bean.longtitude = aMapLocation.getLongitude();
                PreferencesHelper.saveData(bean);
                RxBus.getDefault().send(bean, Constant.REFRESH_LOCATION);
                aMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(bean.latitude, bean.longtitude)));
                if (followMove) {
                    aMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                }
            } else {
                String errText = "--location_fail," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("dp", errText);
            }
        }
    }

    @Override
    public void onTouch(MotionEvent motionEvent) {
        if (followMove) {
            //用户拖动地图后，不再跟随移动，直到用户点击定位按钮
            followMove = false;
        }
    }

    /**
     * 激活定位
     *
     * @param onLocationChangedListener
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(getContext());
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            mLocationOption.setInterval(10000);//定位时间间隔
            //高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //启用定位参数
            mlocationClient.setLocationOption(mLocationOption);
            //启动定位
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @OnClick(R.id.iv_my_location)
    public void backLcation() {
        MyLocationBean bean = PreferencesHelper.getData(MyLocationBean.class);
        if (bean != null) {
            aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(bean.latitude, bean.longtitude), 14));
        }
    }
}
