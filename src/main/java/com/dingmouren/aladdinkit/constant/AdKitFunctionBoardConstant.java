package com.dingmouren.aladdinkit.constant;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.dingmouren.aladdinkit.core.AdKit;
import com.dingmouren.aladdinkit.R;
import com.dingmouren.aladdinkit.model.FunctionClassifyModel;
import com.dingmouren.aladdinkit.model.FunctionModel;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class AdKitFunctionBoardConstant {

    public static List<FunctionClassifyModel> functionData = new ArrayList<>();
    //业务专区1
    public static final String TAG_BUS_1 = "业务专区1";
    public static final String TAG_BUS_1_CHANGE_HOST = "切换环境";
    public static final String TAG_BUS_2_H5_DEBUG = "DebugH5";
    public static final String TAG_BUS_3_OKHTTP_LOG = "OkhttpLog";
    //平台工具
    public static final String TAG_PLATFORM_TOOLS = "平台工具";
    public static final String TAG_PLATFORM_TOOLS_MOCK = "数据Mock";
    public static final String TAG_PLATFORM_TOOLS_HEALTH = "健康体检";
    public static final String TAG_PLATFORM_TOOLS_FILE_SYNC = "文件同步助手";
    public static final String TAG_PLATFORM_TOOLS_ONE_MAC_CONTROL = "一机多控";
    public static final String TAG_PLATFORM_TOOLS_PTHREAD_HOOK = "pthread hook";
    //常用工具
    public static final String TAG_COMMON_TOOLS = "常用工具";
    public static final String TAG_COMMON_TOOLS_APP_INFO = "App信息";
    public static final String TAG_COMMON_TOOLS_THIRD_LIB = "三方库信息";
    public static final String TAG_COMMON_TOOLS_KIT_DEVLOP = "开发者选项";
    public static final String TAG_COMMON_TOOLS_KIT_LOCAL_LANG = "本地语言";
    public static final String TAG_COMMON_TOOLS_FILE_EXPLORER = "沙盒浏览";
    public static final String TAG_COMMON_TOOLS_WEB_DOOR = "H5任意门";
    public static final String TAG_COMMON_TOOLS_DATA_CLEAN = "清理缓存";
    public static final String TAG_COMMON_TOOLS_LOG_INFO = "日志";
    public static final String TAG_COMMON_TOOLS_H5_HELPER = "H5助手";
    //LBS
    public static final String TAG_LBS = "LBS";
    public static final String TAG_LBS_GPS_MOCK = "位置模拟";
    public static final String TAG_LBS_MOCK_LOCATION_ROUTE = "实时导航";
    //性能监控
    public static final String TAG_PERFORM_MONITOR = "性能监控";
    public static final String TAG_PERFORM_MONITOR_FRAME_HIST = "帧率";
    public static final String TAG_PERFORM_MONITOR_CPU = "CPU";
    public static final String TAG_PERFORM_MONITOR_RAM = "内存";
    public static final String TAG_PERFORM_MONITOR_NET = "流量监控";
    public static final String TAG_PERFORM_MONITOR_CRASH = "Crash";
    public static final String TAG_PERFORM_MONITOR_BLOCK_MONITOR = "卡顿";
    public static final String TAG_PERFORM_MONITOR_PERFORMANCE_LARGE_PICTURE = "大图检测";
    public static final String TAG_PERFORM_MONITOR_WEAK_NET = "模拟弱网";
    public static final String TAG_PERFORM_MONITOR_METHOD_USE_TIME = "启动耗时";
    public static final String TAG_PERFORM_MONITOR_UI_PERFORMANCE = "UI层级";
    public static final String TAG_PERFORM_MONITOR_METHOD_COST = "函数耗时";
    //视觉工具
    public static final String TAG_VISUAL_TOOL = "视觉工具";
    public static final String TAG_VISUAL_TOOL_COLOR_PICK = "取色器";
    public static final String TAG_VISUAL_TOOL_ALIGN_RULER = "对齐标尺";
    public static final String TAG_VISUAL_TOOL_VIEW_CHECK = "控件检查";
    public static final String TAG_VISUAL_TOOL_VIEW_BORDER = "布局边框";

    static {
        //业务专区1
        FunctionModel modelBus1 = new FunctionModel(TAG_BUS_1_CHANGE_HOST, AdKit.sApplication.getDrawable(R.drawable.ak_server));
        FunctionModel modelBus2 = new FunctionModel(TAG_BUS_2_H5_DEBUG, AdKit.sApplication.getDrawable(R.drawable.ak_html5_debug));
        FunctionModel modelBus3 = new FunctionModel(TAG_BUS_3_OKHTTP_LOG, AdKit.sApplication.getDrawable(R.drawable.ak_okhttp_log));
        List<FunctionModel> listBus1 = new ArrayList<>();
        listBus1.add(modelBus1);
        listBus1.add(modelBus2);
        listBus1.add(modelBus3);
        FunctionClassifyModel funClassifyBus1 = new FunctionClassifyModel(TAG_BUS_1,listBus1);
        //平台工具
        FunctionModel modelMock = new FunctionModel(TAG_PLATFORM_TOOLS_MOCK, AdKit.sApplication.getDrawable(R.drawable.ak_net_mock));
        FunctionModel modelHealth = new FunctionModel(TAG_PLATFORM_TOOLS_HEALTH, AdKit.sApplication.getDrawable(R.drawable.ak_health));
        FunctionModel modelFileSync = new FunctionModel(TAG_PLATFORM_TOOLS_FILE_SYNC, AdKit.sApplication.getDrawable(R.drawable.ak_icon_mc));
        FunctionModel modeOneMacControl = new FunctionModel(TAG_PLATFORM_TOOLS_ONE_MAC_CONTROL, AdKit.sApplication.getDrawable(R.drawable.ak_icon_mc));
        FunctionModel modePthreadHook = new FunctionModel(TAG_PLATFORM_TOOLS_PTHREAD_HOOK, AdKit.sApplication.getDrawable(R.drawable.ak_ram));
        List<FunctionModel> modelPlatformToolsList = new ArrayList<>();
        modelPlatformToolsList.add(modelMock);
        modelPlatformToolsList.add(modelHealth);
        modelPlatformToolsList.add(modelFileSync);
        modelPlatformToolsList.add(modeOneMacControl);
        modelPlatformToolsList.add(modePthreadHook);
        FunctionClassifyModel funClassifyPlatformTools = new FunctionClassifyModel(TAG_PLATFORM_TOOLS,modelPlatformToolsList);
        //常用工具
        FunctionModel modelAppInfo = new FunctionModel(TAG_COMMON_TOOLS_APP_INFO, AdKit.sApplication.getDrawable(R.drawable.ak_sys_info));
        FunctionModel modelThirdLib = new FunctionModel(TAG_COMMON_TOOLS_THIRD_LIB, AdKit.sApplication.getDrawable(R.drawable.ak_icon_third_lib));
        FunctionModel modelKitDevlop = new FunctionModel(TAG_COMMON_TOOLS_KIT_DEVLOP, AdKit.sApplication.getDrawable(R.drawable.ak_kit_devlop));
        FunctionModel modelKitLocalLang = new FunctionModel(TAG_COMMON_TOOLS_KIT_LOCAL_LANG, AdKit.sApplication.getDrawable(R.drawable.ak_kit_local_lang));
        FunctionModel modelFileExplorer = new FunctionModel(TAG_COMMON_TOOLS_FILE_EXPLORER, AdKit.sApplication.getDrawable(R.drawable.ak_file_explorer));
        FunctionModel modelWebDoor = new FunctionModel(TAG_COMMON_TOOLS_WEB_DOOR, AdKit.sApplication.getDrawable(R.drawable.ak_web_door));
        FunctionModel modelDataClean = new FunctionModel(TAG_COMMON_TOOLS_DATA_CLEAN, AdKit.sApplication.getDrawable(R.drawable.ak_data_clean));
        FunctionModel modelLogInfo = new FunctionModel(TAG_COMMON_TOOLS_LOG_INFO, AdKit.sApplication.getDrawable(R.drawable.ak_log_info));
        FunctionModel modelH5Help = new FunctionModel(TAG_COMMON_TOOLS_H5_HELPER, AdKit.sApplication.getDrawable(R.drawable.ak_icon_h5help));
        List<FunctionModel> listCommonToolsList = new ArrayList<>();
        listCommonToolsList.add(modelAppInfo);
        listCommonToolsList.add(modelThirdLib);
        listCommonToolsList.add(modelKitDevlop);
        listCommonToolsList.add(modelKitLocalLang);
        listCommonToolsList.add(modelFileExplorer);
        listCommonToolsList.add(modelWebDoor);
        listCommonToolsList.add(modelDataClean);
        listCommonToolsList.add(modelLogInfo);
        listCommonToolsList.add(modelH5Help);
        FunctionClassifyModel funClassifyCommonTools = new FunctionClassifyModel(TAG_COMMON_TOOLS,listCommonToolsList);
        //LBS
        FunctionModel modelGpsMock = new FunctionModel(TAG_LBS_GPS_MOCK, AdKit.sApplication.getDrawable(R.drawable.ak_gps_mock));
        FunctionModel modelMockLocationRoute = new FunctionModel(TAG_LBS_MOCK_LOCATION_ROUTE, AdKit.sApplication.getDrawable(R.drawable.ak_mock_location_route));
        List<FunctionModel> listLbs = new ArrayList<>();
        listLbs.add(modelGpsMock);
        listLbs.add(modelMockLocationRoute);
        FunctionClassifyModel funClassifyLbs = new FunctionClassifyModel(TAG_LBS,listLbs);
        //性能监控
        FunctionModel modelFrameHist = new FunctionModel(TAG_PERFORM_MONITOR_FRAME_HIST, AdKit.sApplication.getDrawable(R.drawable.ak_frame_hist));
        FunctionModel modelCpu = new FunctionModel(TAG_PERFORM_MONITOR_CPU, AdKit.sApplication.getDrawable(R.drawable.ak_cpu));
        FunctionModel modelRam = new FunctionModel(TAG_PERFORM_MONITOR_RAM, AdKit.sApplication.getDrawable(R.drawable.ak_ram));
        FunctionModel modelNet = new FunctionModel(TAG_PERFORM_MONITOR_NET, AdKit.sApplication.getDrawable(R.drawable.ak_net));
        FunctionModel modelCrashCatch = new FunctionModel(TAG_PERFORM_MONITOR_CRASH, AdKit.sApplication.getDrawable(R.drawable.ak_crash_catch));
        FunctionModel modelBlockMonitor = new FunctionModel(TAG_PERFORM_MONITOR_BLOCK_MONITOR, AdKit.sApplication.getDrawable(R.drawable.ak_block_monitor));
        FunctionModel modelPerformanceLargePicture = new FunctionModel(TAG_PERFORM_MONITOR_PERFORMANCE_LARGE_PICTURE, AdKit.sApplication.getDrawable(R.drawable.ak_performance_large_picture));
        FunctionModel modelWeakNet = new FunctionModel(TAG_PERFORM_MONITOR_WEAK_NET, AdKit.sApplication.getDrawable(R.drawable.ak_weak_network));
        FunctionModel modelMethodUseTime = new FunctionModel(TAG_PERFORM_MONITOR_METHOD_USE_TIME, AdKit.sApplication.getDrawable(R.drawable.ak_method_use_time));
        FunctionModel modelUiPerformance = new FunctionModel(TAG_PERFORM_MONITOR_UI_PERFORMANCE, AdKit.sApplication.getDrawable(R.drawable.ak_ui_performance));
        FunctionModel modelMethodCost = new FunctionModel(TAG_PERFORM_MONITOR_METHOD_COST, AdKit.sApplication.getDrawable(R.drawable.ak_method_cost));
        List<FunctionModel> listPerformMonitor = new ArrayList<>();
        listPerformMonitor.add(modelFrameHist);
        listPerformMonitor.add(modelCpu);
        listPerformMonitor.add(modelRam);
        listPerformMonitor.add(modelNet);
        listPerformMonitor.add(modelCrashCatch);
        listPerformMonitor.add(modelBlockMonitor);
        listPerformMonitor.add(modelPerformanceLargePicture);
        listPerformMonitor.add(modelWeakNet);
        listPerformMonitor.add(modelMethodUseTime);
        listPerformMonitor.add(modelUiPerformance);
        listPerformMonitor.add(modelMethodCost);
        FunctionClassifyModel funClassifyPerformMonitor = new FunctionClassifyModel(TAG_PERFORM_MONITOR,listPerformMonitor);
        //视觉工具
        FunctionModel modelColorPick = new FunctionModel(TAG_VISUAL_TOOL_COLOR_PICK, AdKit.sApplication.getDrawable(R.drawable.ak_color_pick));
        FunctionModel modelAlignRuler = new FunctionModel(TAG_VISUAL_TOOL_ALIGN_RULER, AdKit.sApplication.getDrawable(R.drawable.ak_align_ruler));
        FunctionModel modelViewCheck = new FunctionModel(TAG_VISUAL_TOOL_VIEW_CHECK, AdKit.sApplication.getDrawable(R.drawable.ak_view_check));
        FunctionModel modelViewBorder = new FunctionModel(TAG_VISUAL_TOOL_VIEW_BORDER, AdKit.sApplication.getDrawable(R.drawable.ak_view_border));
        List<FunctionModel> listVisualToolList = new ArrayList<>();
        listVisualToolList.add(modelColorPick);
        listVisualToolList.add(modelAlignRuler);
        listVisualToolList.add(modelViewCheck);
        listVisualToolList.add(modelViewBorder);
        FunctionClassifyModel funClassifyVisualTool = new FunctionClassifyModel(TAG_VISUAL_TOOL,listVisualToolList);


        //添加到面板
        functionData.add(funClassifyBus1);
        functionData.add(funClassifyPlatformTools);
        functionData.add(funClassifyCommonTools);
        functionData.add(funClassifyLbs);
        functionData.add(funClassifyPerformMonitor);
        functionData.add(funClassifyVisualTool);
    }
}
