package com.haizj.alistore;

import android.app.ActivityManager;
import android.content.Context;

import com.haizj.alistore.base.database.StoreDataBase;
import com.haizj.alistore.utils.BoxServiceManager;
import com.hyena.framework.database.DataBaseManager;
import com.hyena.framework.servcie.ServiceProvider;
import com.hyena.framework.utils.BaseApp;

import java.util.Iterator;
import java.util.List;

/**
 * Created by weilei on 17/3/6.
 */
public class App extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        if (processAppName == null || !processAppName.equalsIgnoreCase("com.haizj.alistore")) {
            return;
        }

        DataBaseManager.getDataBaseManager().registDataBase(new StoreDataBase());
        //注册应用系统服务
        ServiceProvider.getServiceProvider().registServiceManager(new BoxServiceManager());
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> l = am.getRunningAppProcesses();
        Iterator<ActivityManager.RunningAppProcessInfo> i = l.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
            }
        }
        return processName;
    }
}
