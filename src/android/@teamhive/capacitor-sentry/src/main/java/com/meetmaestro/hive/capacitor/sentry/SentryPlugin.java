package com.meetmaestro.hive.capacitor.sentry;

import android.content.Context;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;

import java.util.HashMap;
import java.util.Iterator;

import io.sentry.Sentry;
import io.sentry.android.AndroidSentryClientFactory;
import io.sentry.event.Breadcrumb;
import io.sentry.event.BreadcrumbBuilder;
import io.sentry.event.Event;
import io.sentry.event.EventBuilder;
import io.sentry.event.User;
import io.sentry.event.UserBuilder;

@NativePlugin()
public class SentryPlugin extends Plugin {

    @PluginMethod()
    public void initialize(PluginCall call) {
        String dsn = call.getString("dsn");
        try {
            Context ctx = getContext();
            Sentry.init(dsn, new AndroidSentryClientFactory(ctx));
            call.success();
        } catch (Exception e) {
            call.error(e.getMessage());
        }
    }

    @PluginMethod()
    public void crash() {
        throw new RuntimeException("TEST - Sentry Client Crash");
    }

    @PluginMethod()
    public void setUser(PluginCall call) {
        String email = call.getString("email");
        String id = call.getString("id");
        String ip = call.getString("ipAddress");
        String username = call.getString("username");
        JSObject data = call.getObject("data");
        UserBuilder builder = new UserBuilder();
        if (email != null) {
            builder.setEmail(email);
        }
        if (id != null) {
            builder.setId(id);
        }
        if (ip != null) {
            builder.setIpAddress(ip);
        }
        if(username != null){
            builder.setUsername(username);
        }
        HashMap<String,Object> dataMap = new HashMap<String, Object>();
        Iterator<String> keys = data.keys();
        if(keys.hasNext()){
            String key = keys.next();
            Object item = data.opt(key);
            dataMap.put(key,item);
        }
        if(!dataMap.isEmpty()){
            builder.setData(dataMap);
        }

        Sentry.getContext().setUser(builder.build());
        call.resolve();
    }


    @PluginMethod()
    public void setTags(PluginCall call) {
        JSObject tags = call.getObject("tags");
        Iterator<String> keys = tags.keys();
        if(keys.hasNext()){
            String key = keys.next();
            String item = tags.getString(key);
            Sentry.getContext().addTag(key,item);
        }
        call.resolve();
    }

    @PluginMethod()
    public void setExtra(PluginCall call) {
        JSObject tags = call.getObject("extra");
        Iterator<String> keys = tags.keys();
        if(keys.hasNext()){
            String key = keys.next();
            Object item = tags.opt(key);
            Sentry.getContext().addExtra(key,item);
        }
    }

    @PluginMethod()
    public void clearContext(PluginCall call) {
        Sentry.clearContext();
    }

    @PluginMethod()
    public void captureMessage(PluginCall call) {
        String message = call.getString("message");
        EventBuilder eventBuilder = new EventBuilder();
        eventBuilder.withMessage(message);
        Sentry.capture(eventBuilder.build());
        call.resolve();
    }

    @PluginMethod()
    public void captureException(PluginCall call) {
        Object exception = call.getData().opt("exception");
        Sentry.capture(new Exception(exception.toString()));
    }

    @PluginMethod()
    public void captureBreadcrumb(PluginCall call) {
        BreadcrumbBuilder builder = new BreadcrumbBuilder();
        Sentry.getContext().recordBreadcrumb(builder.build());
    }

}
