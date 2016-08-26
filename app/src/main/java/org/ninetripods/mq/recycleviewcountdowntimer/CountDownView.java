package org.ninetripods.mq.recycleviewcountdowntimer;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by MQ on 2016/8/26.
 */
public class CountDownView extends TextView {
    private long day = 0;
    private int type;
    private String mStrTime = "";

    public CountDownView(Context context) {
        super(context);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    long mMills;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (mMills / 1000 > 0) {
                        mStrTime = formatTime(mMills);
                        setText(mStrTime);
                        mMills -= 1000;
                        handler.sendEmptyMessageDelayed(0, 1000);
                    } else {
                        setText("抢购已结束了哟");
                    }
                    break;
            }
        }
    };

    /**
     * 设置倒计时的时长(毫秒)
     *
     * @param mills
     */
    public void setTypeMills(long mills) {
        mMills = mills;
        if (mMills > 0) {
            handler.removeMessages(0);
            handler.sendEmptyMessage(0);
        } else {
            setText("抢购已结束了哟");
        }

    }

    /**
     * convert mills left to 00时00分00秒
     *
     * @param mills
     * @return
     */
    private String formatTime(long mills) {

        long hourMills = 60 * 60 * 1000;
        long minuteMills = 60 * 1000;
        long secondMills = 1000;
        long hours = mills / hourMills;
        mills %= hourMills;
        long minutes = mills / minuteMills;
        mills %= minuteMills;
        long seconds = mills / secondMills;

        if (hours > 24) {
            day = hours / 24;
            hours = hours % 24;
        }
        String lastTime = ((hours < 10) ? "0" + hours : "" + hours) + ":"
                + ((minutes < 10) ? "0" + minutes : "" + minutes) + ":"
                + ((seconds < 10) ? "0" + seconds : "" + seconds);
        if (day != 0) {
            lastTime = day + "天" + lastTime;
        }
        switch (type) {
            case 1:
                //尚未开始抢购
                lastTime = lastTime + "   开始";
                break;
            case 2:
                //已经开始抢购
                lastTime = lastTime;
                break;
            default:
                break;
        }

        return lastTime;
    }
}
