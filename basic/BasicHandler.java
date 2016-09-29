package basic;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Created by Dexter on 2016/9/5.
 */
public class BasicHandler {

    private Handler handler;
    BasicHandlerListener basicHandlerListener;

    public BasicHandler(BasicHandlerListener basicHandlerListener) {
        this.basicHandlerListener = basicHandlerListener;
        init();
    }

    private void init() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                basicHandlerListener.handleMessage(msg);
            }
        };
    }

    public void sendMessage(Bundle bundle) {
        Message message = new Message();
        message.setData(bundle);
        handler.sendMessage(message);
    }

    public interface BasicHandlerListener {
        void handleMessage(Message msg);
    }
}
