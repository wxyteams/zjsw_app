package zjsw.myapp.zj.arcgis;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureQueryResult;
import com.esri.arcgisruntime.data.QueryParameters;
import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.io.RequestConfiguration;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.layers.ArcGISMapImageSublayer;
import com.esri.arcgisruntime.layers.WebTiledLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.LayerList;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.symbology.Symbol;
import java.util.concurrent.ExecutionException;
import zjsw.myapp.zj.arcgis.TDT.TianDiTuMethodsClass;

public class MapActivity extends AppCompatActivity {

    private MapView mMapView;
    private ArcGISMap map;
    private RequestConfiguration requestConfiguration;
    private Basemap tdtBasemap;
    private GraphicsOverlay graphicsOverlay;
    private Toolbar toolbar;
    private LocationClient mLocationClient = null;
    private MyLocationListener myListener = null;
    private FloatingActionButton fab01Add;
    private boolean isAdd = false;
    private boolean isDelay = true;//开启动画效果
    private int[] llId = new int[]{R.id.ll01, R.id.ll02, R.id.ll03, R.id.ll04, R.id.ll05, R.id.ll06};
    private RelativeLayout rlAddBill;
    private LinearLayout[] ll = new LinearLayout[llId.length];
    private AnimatorSet addBillTranslate1;
    private AnimatorSet addBillTranslate2;
    private AnimatorSet addBillTranslate3;
    private AnimatorSet addBillTranslate4;
    private AnimatorSet addBillTranslate5;
    private AnimatorSet addBillTranslate6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        initView();
        setDefaultValues();
        setToolBar();

        mMapView = (MapView) findViewById(R.id.mapView);
        requestConfiguration = new RequestConfiguration();
        requestConfiguration.getHeaders().put("referer", "http://map.tianditu.gov.cn/");

        WebTiledLayer webTiledLayer = TianDiTuMethodsClass.CreateTianDiTuTiledLayer(TianDiTuMethodsClass.LayerType.TIANDITU_VECTOR_2000);
        webTiledLayer.setId("TIANDITU_VECTOR_2000");
        webTiledLayer.setRequestConfiguration(requestConfiguration);
        tdtBasemap = new Basemap(webTiledLayer);

        WebTiledLayer webTiledLayer1 = TianDiTuMethodsClass.CreateTianDiTuTiledLayer(TianDiTuMethodsClass.LayerType.TIANDITU_VECTOR_ANNOTATION_CHINESE_2000);
        webTiledLayer1.setId("TIANDITU_VECTOR_ANNOTATION_CHINESE_2000");
        webTiledLayer1.setRequestConfiguration(requestConfiguration);
        tdtBasemap.getBaseLayers().add(webTiledLayer1);

        // create a graphics overlay to show the results in
        graphicsOverlay = new GraphicsOverlay();
        mMapView.getGraphicsOverlays().add(graphicsOverlay);

        map = new ArcGISMap(tdtBasemap);
        mMapView.setMap(map);

        LocationSet();
        Point center = new Point(117.036709, 36.662112, SpatialReference.create(4490));// CGCS2000
        mMapView.setViewpointCenterAsync(center, 150000);
    }

    //设置定位参数
    private void LocationSet() {
        mLocationClient = new LocationClient(getApplicationContext());

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("GCJ02");
        //可选，设置返回经纬度坐标类型，默认GCJ02
        //GCJ02：国测局坐标；
        //BD09ll：百度经纬度坐标；
        //BD09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标

        option.setScanSpan(0);
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
        //可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5 * 60 * 1000);
        //可选，V7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位

        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        option.setIsNeedAddress(true);
        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true

        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明

        //声明LocationClient类
        myListener = new MyLocationListener() {
            public void onReceiveLocation(BDLocation location) {
                String key = mLocationClient.getAccessKey();
                double latitude = location.getLatitude();    //获取纬度信息
                double longitude = location.getLongitude();    //获取经度信息
                float radius = location.getRadius();    //获取定位精度，默认值为0.0f
                double xlatitude = -0.000227;//纬度校准值
                double xlongitude = -0.006193;//经度校准值
                latitude = latitude + xlatitude;
                longitude = longitude + xlongitude;
                String coorType = location.getCoorType();
                //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

                int errorCode = location.getLocType();
                //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
                String addr = location.getAddrStr();    //获取详细地址信息
                String country = location.getCountry();    //获取国家
                String province = location.getProvince();    //获取省份
                String city = location.getCity();    //获取城市
                String district = location.getDistrict();    //获取区县
                String street = location.getStreet();    //获取街道信息

                Point center = new Point(longitude, latitude, SpatialReference.create(4490));// CGCS2000
                mMapView.setViewpointCenterAsync(center, 13000);
                SimpleMarkerSymbol buoyMarker = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 10);
                Graphic buoyGraphic1 = new Graphic(center, buoyMarker);
                GraphicsOverlay graphicsOverlay = addGraphicsOverlay(mMapView);
                graphicsOverlay.getGraphics().clear();
                graphicsOverlay.getGraphics().add(buoyGraphic1);
                mLocationClient.stop();
            }
        };
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数

    }

    private GraphicsOverlay addGraphicsOverlay(MapView mapView) {
        //create the graphics overlay
        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
        //add the overlay to the map view
        mapView.getGraphicsOverlays().add(graphicsOverlay);
        return graphicsOverlay;
    }

    private void setToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_menu, menu);
        return true;
    }

    //Toolbar的事件---返回
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.toolbar_r_1:
                final EditText et = new EditText(this);
                AlertDialog builder = new AlertDialog.Builder(this)
                        .setTitle("地图查询")
                        .setView(et)
                        .setPositiveButton("查询", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ArcGISMapImageLayer imageLayer = null;
                                LayerList list = map.getOperationalLayers();
                                for (int i = 0; i < list.size(); i++) {
                                    if (list.get(i).getId().equals("yali") || list.get(i).getId().equals("liuliang") || list.get(i).getId().equals("shipin") || list.get(i).getId().equals("guanxian")) {
                                        imageLayer = (ArcGISMapImageLayer) map.getOperationalLayers().get(i);
                                        if (imageLayer.getLoadStatus() == LoadStatus.LOADED) {

                                            SimpleMarkerSymbol citySymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, 0xFFFF0000, 16);
                                            // get and load each sublayer to query
                                            ArcGISMapImageSublayer citiesSublayer = (ArcGISMapImageSublayer) imageLayer.getSublayers().get(0);
                                            // clear previous results
                                            graphicsOverlay.getGraphics().clear();
                                            // create query parameters filtering based on population and the map view's current viewpoint
                                            QueryParameters populationQuery = new QueryParameters();
                                            populationQuery.setWhereClause("upper(Name) like '%" + et.getText() + "%'");
                                            populationQuery
                                                    .setGeometry(mMapView.getCurrentViewpoint(Viewpoint.Type.BOUNDING_GEOMETRY).getTargetGeometry());

                                            QueryAndDisplayGraphics(citiesSublayer, citySymbol, populationQuery, graphicsOverlay);
                                        }
                                    }
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                builder.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                builder.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        fab01Add = (FloatingActionButton) findViewById(R.id.fab01Add);
        rlAddBill = (RelativeLayout) findViewById(R.id.rlAddBill);
        for (int i = 0; i < llId.length; i++) {
            ll[i] = (LinearLayout) findViewById(llId[i]);
        }
    }

    private void setDefaultValues() {
        addBillTranslate1 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.add_bill_anim);
        addBillTranslate2 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.add_bill_anim);
        addBillTranslate3 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.add_bill_anim);
        addBillTranslate4 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.add_bill_anim);
        addBillTranslate5 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.add_bill_anim);
        addBillTranslate6 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.add_bill_anim);
    }

    //定位按钮事件
    public void fab_onClick(View v) {
        mLocationClient.start();
    }

    //图层控制按钮事件
    public void fab01Add_onClick(View v) {
        fab01Add.setImageResource(isAdd ? R.mipmap.ic_add_white_24dp : R.mipmap.ic_close_white_24dp);
        isAdd = !isAdd;
        rlAddBill.setVisibility(isAdd ? View.VISIBLE : View.GONE);
        if (isAdd) {
            addBillTranslate1.setTarget(ll[0]);
            addBillTranslate1.start();
            addBillTranslate2.setTarget(ll[1]);
            addBillTranslate2.setStartDelay(isDelay ? 150 : 0);
            addBillTranslate2.start();
            addBillTranslate3.setTarget(ll[2]);
            addBillTranslate3.setStartDelay(isDelay ? 200 : 0);
            addBillTranslate3.start();
            addBillTranslate4.setTarget(ll[3]);
            addBillTranslate4.setStartDelay(isDelay ? 250 : 0);
            addBillTranslate4.start();
            addBillTranslate5.setTarget(ll[4]);
            addBillTranslate5.setStartDelay(isDelay ? 300 : 0);
            addBillTranslate5.start();
            addBillTranslate6.setTarget(ll[5]);
            addBillTranslate6.setStartDelay(isDelay ? 350 : 0);
            addBillTranslate6.start();
        }
    }

    //图层选择CheckBox按钮事件
    public void cbLayers_onClick(View v) {
        CheckBox cb = (CheckBox) v;
        switch (v.getId()) {
            case R.id.cb_IMAGE:
                if (cb.isChecked()) {
                    WebTiledLayer webTiledLayer = TianDiTuMethodsClass.CreateTianDiTuTiledLayer(TianDiTuMethodsClass.LayerType.TIANDITU_IMAGE_2000);
                    webTiledLayer.setId("TIANDITU_IMAGE_2000");
                    webTiledLayer.setRequestConfiguration(requestConfiguration);
                    map.getOperationalLayers().add(webTiledLayer);

                    WebTiledLayer webTiledLayer1 = TianDiTuMethodsClass.CreateTianDiTuTiledLayer(TianDiTuMethodsClass.LayerType.TIANDITU_IMAGE_ANNOTATION_CHINESE_2000);
                    webTiledLayer1.setId("TIANDITU_IMAGE_ANNOTATION_CHINESE_2000");
                    webTiledLayer1.setRequestConfiguration(requestConfiguration);
                    map.getOperationalLayers().add(webTiledLayer1);
                } else {
                    LayerList list = map.getOperationalLayers();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getId().equals("TIANDITU_IMAGE_2000")) {
                            map.getOperationalLayers().remove(i);
                        }
                        if (list.get(i).getId().equals("TIANDITU_IMAGE_ANNOTATION_CHINESE_2000")) {
                            map.getOperationalLayers().remove(i);
                        }
                    }
                }
                break;
            case R.id.cb_TERRAIN:
                if (cb.isChecked()) {
                    WebTiledLayer webTiledLayer = TianDiTuMethodsClass.CreateTianDiTuTiledLayer(TianDiTuMethodsClass.LayerType.TIANDITU_TERRAIN_2000);
                    webTiledLayer.setId("TIANDITU_TERRAIN_2000");
                    webTiledLayer.setRequestConfiguration(requestConfiguration);
                    map.getOperationalLayers().add(webTiledLayer);

                    WebTiledLayer webTiledLayer1 = TianDiTuMethodsClass.CreateTianDiTuTiledLayer(TianDiTuMethodsClass.LayerType.TIANDITU_TERRAIN_ANNOTATION_CHINESE_2000);
                    webTiledLayer1.setId("TIANDITU_TERRAIN_ANNOTATION_CHINESE_2000");
                    webTiledLayer1.setRequestConfiguration(requestConfiguration);
                    map.getOperationalLayers().add(webTiledLayer1);
                } else {
                    LayerList list = map.getOperationalLayers();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getId().equals("TIANDITU_TERRAIN_2000")) {
                            map.getOperationalLayers().remove(i);
                        }
                        if (list.get(i).getId().equals("TIANDITU_TERRAIN_ANNOTATION_CHINESE_2000")) {
                            map.getOperationalLayers().remove(i);
                        }
                    }
                }
                break;
            case R.id.cb_guanxian:
                if (cb.isChecked()) {
                    ArcGISMapImageLayer arcgisTiledLayer = new ArcGISMapImageLayer("http://172.16.3.179:6080/arcgis/rest/services/guanxian/MapServer");
                    arcgisTiledLayer.setId("guanxian");
                    map.getOperationalLayers().add(arcgisTiledLayer);

                    arcgisTiledLayer.addDoneLoadingListener(() -> {
                        if (arcgisTiledLayer.getLoadStatus() == LoadStatus.LOADED) {
                            // get and load each sublayer to query
                            ArcGISMapImageSublayer sublayer = (ArcGISMapImageSublayer) arcgisTiledLayer.getSublayers().get(0);
                            sublayer.loadAsync();
                        }
                    });
                } else {
                    LayerList list = map.getOperationalLayers();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getId().equals("guanxian")) {
                            map.getOperationalLayers().remove(i);
                        }
                    }
                }
                break;
            case R.id.cb_jiankong:
                if (cb.isChecked()) {
                    ArcGISMapImageLayer arcgisTiledLayer = new ArcGISMapImageLayer("http://172.16.3.179:6080/arcgis/rest/services/shipin/MapServer");
                    arcgisTiledLayer.setId("shipin");
                    map.getOperationalLayers().add(arcgisTiledLayer);

                    ArcGISMapImageLayer arcgisTiledLayer_ANNOTATION = new ArcGISMapImageLayer("http://172.16.3.179:6080/arcgis/rest/services/shipin_zj/MapServer");
                    arcgisTiledLayer_ANNOTATION.setId("shipin_zj");
                    map.getOperationalLayers().add(arcgisTiledLayer_ANNOTATION);

                    arcgisTiledLayer.addDoneLoadingListener(() -> {
                        if (arcgisTiledLayer.getLoadStatus() == LoadStatus.LOADED) {
                            // get and load each sublayer to query
                            ArcGISMapImageSublayer sublayer = (ArcGISMapImageSublayer) arcgisTiledLayer.getSublayers().get(0);
                            sublayer.loadAsync();
                        }
                    });
                } else {
                    LayerList list = map.getOperationalLayers();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getId().equals("shipin")) {
                            map.getOperationalLayers().remove(i);
                        }
                        if (list.get(i).getId().equals("shipin_zj")) {
                            map.getOperationalLayers().remove(i);
                        }
                    }
                }
                break;
            case R.id.cb_liuliang:
                if (cb.isChecked()) {
                    ArcGISMapImageLayer arcgisTiledLayer = new ArcGISMapImageLayer("http://172.16.3.179:6080/arcgis/rest/services/liuliang/MapServer");
                    arcgisTiledLayer.setId("liuliang");
                    map.getOperationalLayers().add(arcgisTiledLayer);

                    ArcGISMapImageLayer arcgisTiledLayer_ANNOTATION = new ArcGISMapImageLayer("http://172.16.3.179:6080/arcgis/rest/services/liuliang_zj/MapServer");
                    arcgisTiledLayer_ANNOTATION.setId("liuliang_zj");
                    map.getOperationalLayers().add(arcgisTiledLayer_ANNOTATION);

                    arcgisTiledLayer.addDoneLoadingListener(() -> {
                        if (arcgisTiledLayer.getLoadStatus() == LoadStatus.LOADED) {
                            // get and load each sublayer to query
                            ArcGISMapImageSublayer sublayer = (ArcGISMapImageSublayer) arcgisTiledLayer.getSublayers().get(0);
                            sublayer.loadAsync();
                        }
                    });
                } else {
                    LayerList list = map.getOperationalLayers();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getId().equals("liuliang")) {
                            map.getOperationalLayers().remove(i);
                        }
                        if (list.get(i).getId().equals("liuliang_zj")) {
                            map.getOperationalLayers().remove(i);
                        }
                    }
                }
                break;
            case R.id.cb_yali:
                if (cb.isChecked()) {
                    ArcGISMapImageLayer arcgisTiledLayer = new ArcGISMapImageLayer("http://172.16.3.179:6080/arcgis/rest/services/yali/MapServer");
                    arcgisTiledLayer.setId("yali");
                    map.getOperationalLayers().add(arcgisTiledLayer);

                    ArcGISMapImageLayer arcgisTiledLayer_ANNOTATION = new ArcGISMapImageLayer("http://172.16.3.179:6080/arcgis/rest/services/yali_zj/MapServer");
                    arcgisTiledLayer_ANNOTATION.setId("yali_zj");
                    map.getOperationalLayers().add(arcgisTiledLayer_ANNOTATION);

                    arcgisTiledLayer.addDoneLoadingListener(() -> {
                        if (arcgisTiledLayer.getLoadStatus() == LoadStatus.LOADED) {
                            // get and load each sublayer to query
                            ArcGISMapImageSublayer sublayer = (ArcGISMapImageSublayer) arcgisTiledLayer.getSublayers().get(0);
                            sublayer.loadAsync();
                        }
                    });
                } else {
                    LayerList list = map.getOperationalLayers();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getId().equals("yali")) {
                            map.getOperationalLayers().remove(i);
                        }
                        if (list.get(i).getId().equals("yali_zj")) {
                            map.getOperationalLayers().remove(i);
                        }
                    }
                }
                break;
        }
    }

    //执行查询并绘制查询结果
    private static void QueryAndDisplayGraphics(ArcGISMapImageSublayer sublayer, Symbol sublayerSymbol, QueryParameters query,
                                                GraphicsOverlay graphicsOverlay) {

        ServiceFeatureTable sublayerTable = sublayer.getTable();

        ListenableFuture<FeatureQueryResult> sublayerQuery = sublayerTable.queryFeaturesAsync(query);
        sublayerQuery.addDoneListener(() -> {
            try {
                FeatureQueryResult result = sublayerQuery.get();
                for (Feature feature : result) {
                    Graphic sublayerGraphic = new Graphic(feature.getGeometry(), sublayerSymbol);
                    graphicsOverlay.getGraphics().add(sublayerGraphic);
                }
            } catch (InterruptedException | ExecutionException e) {
                Log.e(MapActivity.class.getSimpleName(), e.toString());
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.dispose();
    }
}
