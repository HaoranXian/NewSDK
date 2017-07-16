package com.example.administrator.sdk.HttpCenter;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;


public class SubscriptionManager {
    private static SubscriptionManager subscriptionManager = null;
    private static Subscription subscription = null;

    public static SubscriptionManager getInstance() {
        if (subscriptionManager == null) {
            subscriptionManager = new SubscriptionManager();
        }
        return subscriptionManager;
    }

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
