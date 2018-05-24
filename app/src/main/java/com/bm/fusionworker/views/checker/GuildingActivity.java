package com.bm.fusionworker.views.checker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.bm.fusionworker.R;
import com.bm.fusionworker.constants.Constant;
import com.bm.fusionworker.fragment.RepairingActivity;
import com.bm.fusionworker.model.beans.MyLocationBean;
import com.bm.fusionworker.model.beans.RepairDataBean;
import com.bm.fusionworker.utils.DrivingRouteOverLay;
import com.bm.fusionworker.weights.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.RxBusSubscriber;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.corelibs.utils.rxbus.RxBus;

import butterknife.Bind;
import butterknife.OnClick;

public class GuildingActivity extends BaseActivity implements LocationSource, AMapLocationListener, RouteSearch.OnRouteSearchListener {

    private Context context = GuildingActivity.this;
    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.start_accept_tv)
    TextView start_accept_tv;

    @Bind(R.id.map)
    MapView map;
    private AMap aMap;
    private RouteSearch mRouteSearch;
    private DriveRouteResult mDriveRouteResult;
    private final int ROUTE_TYPE_DRIVE = 2;
    private LatLonPoint mStartPoint = null;//起点，30.508301, 114.396919
    private LatLonPoint mEndPoint = null;//终点，30.521244, 114.348697
    private double endLatitude, endLongitude;
    private RepairDataBean bean;
    private int status = 0;//0:开始维修导航 1：开始验收导航

    /**
     * @param context
     * @param status  0:开始维修导航 1：开始验收导航
     * @param bean    获取经纬度等信息
     * @return
     */
    public static Intent getLauncher(Context context, int status, RepairDataBean bean) {
        Intent intent = new Intent(context, GuildingActivity.class);
        intent.putExtra("status", status);
        intent.putExtra("bean", bean);
        return intent;
    }

    @Override
    public void goLogin() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guilding;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setColorRes(R.color.app_blue);
        navBar.setNavTitle(getString(R.string.guilding));
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setImageResource(R.drawable.pop_ic_del);
        status = getIntent().getIntExtra("status", 0);
        bean = (RepairDataBean) getIntent().getSerializableExtra("bean");
        if (status == 0) {
            start_accept_tv.setText(getString(R.string.start_repair));
        } else if (status == 1) {
            start_accept_tv.setText(getString(R.string.start_accept));
        }
        map.onCreate(savedInstanceState);
        aMap = map.getMap();

        mRouteSearch = new RouteSearch(context);
        mRouteSearch.setRouteSearchListener(this);

        RxBus.getDefault().toObservable(MyLocationBean.class, Constant.REFRESH_LOCATION)
                .compose(this.<MyLocationBean>bindToLifecycle())
                .subscribe(new RxBusSubscriber<MyLocationBean>() {

                    @Override
                    public void receive(MyLocationBean data) {
                        //刷新坐标 位置不一致时才更新路线
                        if (mStartPoint != null) {
                            if (mStartPoint.getLatitude() != data.latitude || mStartPoint.getLongitude() != data.longtitude) {
                                mStartPoint = new LatLonPoint(data.latitude, data.longtitude);
//                                searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DrivingDefault);
                            }
                        }

                    }
                });
        location();
        showTargetMarker();
    }

    private void location() {
        UiSettings uiSettings = aMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);//去掉自带缩放按钮
        //去掉地图手势
        uiSettings.setRotateGesturesEnabled(false);
        // 设置定位监听
        aMap.setLocationSource(this);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        // 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.list_btn_on);
        BitmapDescriptor markerIcon = BitmapDescriptorFactory
                .fromView(imageView);
        myLocationStyle.myLocationIcon(markerIcon);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、蓝点会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。

        aMap.setMyLocationStyle(myLocationStyle);
        // 定义 Marker 点击事件监听
        AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {
            // marker 对象被点击时回调的接口
            // 返回 true 则表示接口已响应事件，否则返回false
            @Override
            public boolean onMarkerClick(Marker marker) {
//                RepairDataBean bean = list.get(Integer.parseInt(marker.getTitle()));
//                currentMapDataBean = bean;
//                presenter.getInfo(bean.id, bean.type);
                ToastMgr.show(marker.getTitle() + "");
                return true;
            }
        };
        // 绑定 Marker 被点击事件
        aMap.setOnMarkerClickListener(markerClickListener);
    }

    /**
     * 目的地
     */
    private void showTargetMarker() {
        MarkerOptions markerOptionStart = new MarkerOptions();
        LatLng latLngStart = new LatLng(30.508301, 114.396919);//出发地
        markerOptionStart.position(latLngStart);
        markerOptionStart.draggable(false);//设置Marker不可拖动
        markerOptionStart.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_photo)));
        markerOptionStart.setFlat(false);//设置marker平贴地图效果

        MarkerOptions markerOptionEnd = new MarkerOptions();
        LatLng latLng = new LatLng(30.521244, 114.348697);//目的地
        markerOptionEnd.position(latLng);
        markerOptionEnd.draggable(false);//设置Marker不可拖动
        markerOptionEnd.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.home_ic_position)));
        markerOptionEnd.setFlat(false);//设置marker平贴地图效果

        aMap.addMarker(markerOptionStart);
        aMap.addMarker(markerOptionEnd);
    }

    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType, int mode) {
        Log.e("dp", "----开始搜索路径规划方案");
        mStartPoint = new LatLonPoint(30.508301, 114.396919);
        mEndPoint = new LatLonPoint(30.521244, 114.348697);
        aMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(mStartPoint.getLatitude(), mEndPoint.getLongitude())));
        if (mStartPoint == null) {
            ToastMgr.show(getString(R.string.guilding_get_location));
            return;
        }
        if (mEndPoint == null) {
            ToastMgr.show(getString(R.string.guilding_no_end));
            return;
        }
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);
        if (routeType == ROUTE_TYPE_DRIVE) {// 驾车路径规划
            RouteSearch.DriveRouteQuery
                    query = new RouteSearch.DriveRouteQuery(fromAndTo, mode, null,
                    null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
            mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.start_accept_tv)
    public void startAccept() {
        if (status == 0) {
            startActivity(RepairingActivity.getLauncher(context));
        } else if (status == 1) {
            startActivity(AcceptWorkActivity.getLauncher(context));
        }
    }

    @OnClick(R.id.iv_back)
    public void cancel() {
        finish();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {
    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
        Log.e("dp", "----i=" + i);
        aMap.clear();// 清理地图上的所有覆盖物
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            if (driveRouteResult != null && driveRouteResult.getPaths() != null) {
                if (driveRouteResult.getPaths().size() > 0) {
                    mDriveRouteResult = driveRouteResult;
                    final DrivePath drivePath = mDriveRouteResult.getPaths()
                            .get(0);
                    DrivingRouteOverLay drivingRouteOverlay = new DrivingRouteOverLay(
                            context, aMap, drivePath,
                            mDriveRouteResult.getStartPos(),
                            mDriveRouteResult.getTargetPos(), null);
                    drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                    drivingRouteOverlay.setIsColorfulline(false);//是否用颜色展示交通拥堵情况，默认true
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();
                    int dis = (int) drivePath.getDistance();
                    int dur = (int) drivePath.getDuration();
                    Log.e("yzh", "dur=" + dur + "---dis=" + dis);
                } else if (driveRouteResult != null && driveRouteResult.getPaths() == null) {
//                    ToastUtil.show(mContext, R.string.no_result);
                }
            } else {
//                ToastUtil.show(mContext, R.string.no_result);
            }
        } else {
//            ToastUtil.showerror(this.getApplicationContext(), errorCode);
        }
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
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
}
