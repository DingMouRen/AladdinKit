package com.dingmouren.aladdinkit.widget.floatview;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dingmouren.aladdinkit.core.AdKit;
import com.dingmouren.aladdinkit.ui.AdKitFunBoardActivity;
import com.dingmouren.aladdinkit.ui.AdKitH5DebugToggleActivity;
import com.dingmouren.aladdinkit.ui.AdKitHostChangeActivity;
import com.dingmouren.aladdinkit.ui.AdKitOkHttpLogActivity;
import com.dingmouren.aladdinkit.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 悬浮窗管理类
 */
public class FloatViewManager implements IFloatViewManger{
    //缓存添加悬浮窗的Activity
    private static Map<Activity,Map<String,AbsFloatView>> sMapActivity = new HashMap<Activity,Map<String,AbsFloatView>>();

    //缓存悬浮窗的位置
    private static Map<String, FrameLayout.LayoutParams> sMapFloatViewLayoutParams = new HashMap<>();

    //缓存需要添加的悬浮窗类型，元素唯一
    private static LinkedHashSet<String> sFloatViewSet = new LinkedHashSet<String>();

    //缓存不显示悬浮窗的Activity
    private static List<String> sActivitiesNoFloat = new ArrayList<>();

    /**
     * 静态初始化块，设置不需要显示主Icon的页面列表。
     */
    static {
        sActivitiesNoFloat.add(AdKitFunBoardActivity.class.getTypeName());
        sActivitiesNoFloat.add(AdKitHostChangeActivity.class.getTypeName());
        sActivitiesNoFloat.add(AdKitH5DebugToggleActivity.class.getTypeName());
        sActivitiesNoFloat.add(AdKitOkHttpLogActivity.class.getTypeName());
    }

    /**
     *  当Activity恢复时，调用此方法来确保悬浮窗处于正确状态。
     * @param activity
     */
    public static void onActivityResume(Activity activity){

        try {
            // 遍历需要添加的悬浮窗类型
            for (String fullClassName:sFloatViewSet){
                Class<? extends AbsFloatView> clazz = (Class<? extends AbsFloatView>) Class.forName(fullClassName);
                AbsFloatView absFloatView = clazz.newInstance();
                absFloatView.performCreate(activity);
                //检查当前Activity是否已缓存了悬浮窗实例
                boolean activityInMap = sMapActivity.containsKey(activity);
                if (activityInMap){
                    Map<String,AbsFloatView> valueMapActivity = sMapActivity.get(activity);
                    //检查当前悬浮窗类型是否已在当前Activity中存在
                    boolean hasAbsFloatView = valueMapActivity.containsKey(fullClassName);
                    if (hasAbsFloatView){
                        absFloatView.mRootView.setLayoutParams(sMapFloatViewLayoutParams.get(fullClassName));
                        absFloatView.mRootView.requestLayout();
                    }else {
                        //判断当前Activity是否可以显示悬浮窗
                        boolean showFloat = !sActivitiesNoFloat.contains(activity.getClass().getTypeName());
                        if (showFloat ){//可以显示
                            ViewGroup decorView = getDecorView(activity);
                            int index = decorView.indexOfChild(absFloatView.mRootView);
                            if (index == -1){
                                //位置同步
                                boolean hasLayoutParams = sMapFloatViewLayoutParams.containsKey(fullClassName);
                                if (hasLayoutParams){
                                    absFloatView.mLayoutParams = sMapFloatViewLayoutParams.get(fullClassName);
                                }

                                decorView.addView(absFloatView.mRootView,absFloatView.mLayoutParams);
                                valueMapActivity.put(fullClassName,absFloatView);
                                sMapActivity.put(activity,valueMapActivity);
                            }
                        }
                    }

                }else {
                    //判断当前Activity是否可以显示悬浮窗
                    boolean showFloat = !sActivitiesNoFloat.contains(activity.getClass().getTypeName());
                    if (showFloat ){//可以显示
                        ViewGroup decorView = getDecorView(activity);
                        int index = decorView.indexOfChild(absFloatView.mRootView);
                        if (index == -1){
                            //位置同步
                            boolean hasLayoutParams = sMapFloatViewLayoutParams.containsKey(fullClassName);
                            if (hasLayoutParams){
                                absFloatView.mLayoutParams = sMapFloatViewLayoutParams.get(fullClassName);
                            }

                            decorView.addView(absFloatView.mRootView,absFloatView.mLayoutParams);
                            Map<String,AbsFloatView> valueMapActivity = new HashMap<>();
                            valueMapActivity.put(fullClassName,absFloatView);
                            sMapActivity.put(activity,valueMapActivity);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 当Activity销毁时，调用此方法来移除对应的悬浮窗。
     * @param activity 当前销毁的Activity。
     */
    public static void onActivityDestroy(Activity activity) {
        //activity是否添加过悬浮窗
        boolean activityInMap = sMapActivity.containsKey(activity);
        if (activityInMap){
            Map<String,AbsFloatView> absFloatViewMap = sMapActivity.get(activity);
            ViewGroup decorView = getDecorView(activity);
           /* for (String fullClassName : absFloatViewMap.keySet()){
                AbsFloatView absFloatView = absFloatViewMap.get(fullClassName);
                int index = decorView.indexOfChild(absFloatView.mRootView);
                if (index != -1){
                    decorView.removeView(absFloatView.mRootView);
                    //Activity中删除悬浮窗
                    absFloatViewMap.remove(fullClassName);
                }
            }*/
            Iterator<Map.Entry<String,AbsFloatView>> iterator = absFloatViewMap.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String,AbsFloatView> entry = iterator.next();
                String fullClassName = entry.getKey();
                AbsFloatView absFloatView = absFloatViewMap.get(fullClassName);
                int index = decorView.indexOfChild(absFloatView.mRootView);
                if (index != -1){
                    decorView.removeView(absFloatView.mRootView);
                    //Activity中删除悬浮窗
                    iterator.remove();
                }
            }

            sMapActivity.remove(activity);
        }
    }

    /**
     * 将指定的悬浮窗类附加到当前的顶级Activity上。
     * @param clazz 悬浮窗类。
     */
    public static void attach(Class<? extends AbsFloatView> clazz){
        try {
            String clazzName = clazz.getTypeName();
            //缓存悬浮窗类型
            sFloatViewSet.add(clazzName);
            //创建新的悬浮窗对象
            AbsFloatView absFloatView = clazz.newInstance();
            absFloatView.performCreate(AdKit.sApplication);
            //获取当前栈顶Activity
            Activity activity = ActivityUtils.getTopActivity();
            //activity是否添加过悬浮窗
            boolean activityInMap = sMapActivity.containsKey(activity);
            if (activityInMap){
                Map<String,AbsFloatView> valueMapActivity = sMapActivity.get(activity);
                //activity是否添加过此悬浮窗
                boolean hasAbsFloatView = valueMapActivity.containsKey(clazzName);
                if (hasAbsFloatView){

                }else {
                    //判断当前Activity是否可以显示悬浮窗
                    boolean showFloat = !sActivitiesNoFloat.contains(activity.getClass().getTypeName());
                    if (showFloat ){//可以显示
                        ViewGroup decorView = getDecorView(activity);
                        int index = decorView.indexOfChild(absFloatView.mRootView);
                        if (index == -1){
                            //缓存悬浮窗位置
                            sMapFloatViewLayoutParams.put(clazzName,absFloatView.mLayoutParams);

                            decorView.addView(absFloatView.mRootView,absFloatView.mLayoutParams);
                            valueMapActivity.put(clazzName,absFloatView);
                            sMapActivity.put(activity,valueMapActivity);
                        }
                    }
                }

            }else {
                //判断当前Activity是否可以显示悬浮窗
                boolean showFloat = !sActivitiesNoFloat.contains(activity.getClass().getTypeName());
                if (showFloat ){//可以显示
                    ViewGroup decorView = getDecorView(activity);
                    int index = decorView.indexOfChild(absFloatView.mRootView);
                    if (index == -1){
                        //缓存悬浮窗位置
                        sMapFloatViewLayoutParams.put(clazzName,absFloatView.mLayoutParams);

                        decorView.addView(absFloatView.mRootView,absFloatView.mLayoutParams);
                        Map<String,AbsFloatView> valueMapActivity = new HashMap<>();
                        valueMapActivity.put(clazzName,absFloatView);
                        sMapActivity.put(activity,valueMapActivity);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 添加过对应悬浮窗的所有Activity，删除指定悬浮窗,但是不删除Activity，因为Activity中可能会有其他悬浮窗
     * @param clazz
     */
    public static void detach(Class<? extends AbsFloatView> clazz){
        try {
            String clazzName = clazz.getTypeName();
            //遍历添加悬浮窗的Activity
            for (Activity activity : sMapActivity.keySet()){
                Map<String,AbsFloatView> absFloatViewMap = sMapActivity.get(activity);
                boolean hasAbsFloatView = absFloatViewMap.containsKey(clazzName);
                //如果Activity添加过此悬浮窗，则需要删除此Activity种此类悬浮窗
                if (hasAbsFloatView){
                    AbsFloatView absFloatView = absFloatViewMap.get(clazzName);
                    ViewGroup decorView = getDecorView(activity);
                    int index = decorView.indexOfChild(absFloatView.mRootView);
                    if (index != -1){
                        decorView.removeView(absFloatView.mRootView);
                        //Activity中删除悬浮窗
                        absFloatViewMap.remove(clazzName);
                        //悬浮窗集合删除悬浮窗
                        sFloatViewSet.remove(clazzName);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 改变FloatView的位置
     * @param clazz
     * @param layoutParams
     */
    public static void moveFloatViewPosition(Class<? extends AbsFloatView> clazz,ViewGroup.LayoutParams layoutParams){

        String clazzName = clazz.getTypeName();
        for (Activity activity : sMapActivity.keySet()){
            Map<String,AbsFloatView> absFloatViewMap = sMapActivity.get(activity);
            boolean hasAbsFloatView = absFloatViewMap.containsKey(clazzName);
            if (hasAbsFloatView){
                AbsFloatView absFloatView = absFloatViewMap.get(clazzName);
                if (null != absFloatView){
                    //缓存悬浮窗位置
                    sMapFloatViewLayoutParams.put(clazzName,absFloatView.mLayoutParams);

                    absFloatView.mRootView.setLayoutParams(layoutParams);
                    absFloatView.mRootView.requestLayout();
                }

            }
        }


    }


   private static ViewGroup getDecorView(Activity activity){
        return (ViewGroup) activity.getWindow().getDecorView();
   }

   public static void notifyDataChange(Class<? extends AbsFloatView> clazz){
       try {
           String clazzName = clazz.getTypeName();
           //检查是否含有对应的悬浮窗，不包含结束流程。
           boolean hasFloatView = sFloatViewSet.contains(clazzName);
           if (!hasFloatView)return;

           //遍历添加悬浮窗的Activity
           for (Activity activity : sMapActivity.keySet()){
               Map<String,AbsFloatView> absFloatViewMap = sMapActivity.get(activity);
               boolean hasAbsFloatView = absFloatViewMap.containsKey(clazzName);
               //如果Activity添加过此悬浮窗，则需要删除此Activity种此类悬浮窗
               if (hasAbsFloatView){
                   AbsFloatView absFloatView = absFloatViewMap.get(clazzName);
                   absFloatView.onDataChange();
               }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

   public static boolean hasFloatView(Class<? extends AbsFloatView> clazz){
        return sFloatViewSet.contains(clazz.getTypeName());
   }


}
