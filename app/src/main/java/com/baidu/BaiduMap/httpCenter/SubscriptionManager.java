package com.baidu.BaiduMap.httpCenter;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Administrator on 2017/7/4.
 */

public class SubscriptionManager {
    private static SubscriptionManager subscriptionManager = null;
    private static Subscription subscription = null;

    public static SubscriptionManager getInstance() {
        if (subscriptionManager == null) {
            subscriptionManager = new SubscriptionManager();
        }
        return subscriptionManager;
    }

    /**
     * @param o          Observable
     * @param scheduler  运行时线程
     * @param scheduler2 运行后操作所在线程
     * @param subscriber 回调的Subscriber
     * @return
     */
    public Subscription getSubscription(Observable<String> o, Scheduler scheduler, Scheduler scheduler2, Subscriber subscriber) {
        try {
            subscription = o.subscribeOn(scheduler).observeOn(scheduler2).subscribe(subscriber);
            if (null != subscription) {
                return subscription;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
