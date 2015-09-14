package stu.dex.report;

import com.demondevelopers.example.crashreporting.ReportHandler;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ReportHandler.install(this, "dexterwork2010@gmail.com");
    }
}

