package com.meetmaestro.hive.capacitor.sentry;

import android.content.Context;
import android.util.Log;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimeZone;

import io.sentry.Sentry;
import io.sentry.android.AndroidSentryClientFactory;
import io.sentry.event.Breadcrumb;
import io.sentry.event.BreadcrumbBuilder;
import io.sentry.event.EventBuilder;
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
        if (username != null) {
            builder.setUsername(username);
        }
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        Iterator<String> keys = data.keys();
        if (keys.hasNext()) {
            String key = keys.next();
            Object item = data.opt(key);
            dataMap.put(key, item);
        }
        if (!dataMap.isEmpty()) {
            builder.setData(dataMap);
        }

        Sentry.getContext().setUser(builder.build());
        call.resolve();
    }


    @PluginMethod()
    public void setTags(PluginCall call) {
        JSObject tags = call.getObject("tags");
        Iterator<String> keys = tags.keys();
        if (keys.hasNext()) {
            String key = keys.next();
            String item = tags.getString(key);
            Sentry.getContext().addTag(key, item);
        }
        call.resolve();
    }

    @PluginMethod()
    public void setExtra(PluginCall call) {
        JSObject tags = call.getObject("extra");
        Iterator<String> keys = tags.keys();
        if (keys.hasNext()) {
            String key = keys.next();
            Object item = tags.opt(key);
            Sentry.getContext().addExtra(key, item);
        }
        call.resolve();
    }

    @PluginMethod()
    public void clearContext(PluginCall call) {
        Sentry.clearContext();
        call.resolve();
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
        JSObject exception = call.getObject("exception");
        Exception e = new Exception(exception.getString("message"), new Throwable(exception.getString("stack")));
        Sentry.capture(e);
        call.resolve();
    }


    private Date fromISO8601UTC(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        TimeZone tz = TimeZone.getTimeZone("UTC");
        dateFormat.setTimeZone(tz);
        return dateFormat.parse(date);
    }

    @PluginMethod()
    public void captureBreadcrumb(PluginCall call) {
        BreadcrumbBuilder builder = new BreadcrumbBuilder();
        String level = call.getString("level", "debug");
        String category = call.getString("category", "");
        String message = call.getString("message", "");
        String timeStamp = call.getString("timeStamp", "");
        JSObject data = call.getObject("data");
        switch (level) {
            case "info":
                builder.setLevel(Breadcrumb.Level.INFO);
                break;
            case "warning":
                builder.setLevel(Breadcrumb.Level.WARNING);
                break;
            case "error":
                builder.setLevel(Breadcrumb.Level.ERROR);
                break;
            case "critical":
                builder.setLevel(Breadcrumb.Level.CRITICAL);
                break;
            default:
                builder.setLevel(Breadcrumb.Level.DEBUG);
        }

        if (data != null) {
            HashMap<String, String> dataMap = new HashMap<>();
            Iterator<String> keys = data.keys();
            if (keys.hasNext()) {
                String key = keys.next();
                String item = data.getString(key);
                dataMap.put(key, item);
            }
            builder.setData(dataMap);
        }

        if (!category.isEmpty()) {
            builder.setCategory(category);
        }

        if (!message.isEmpty()) {
            builder.setMessage(message);
        }

        if (!timeStamp.isEmpty()) {
            try {
                builder.setTimestamp(fromISO8601UTC(timeStamp));
            } catch (ParseException e) {
                call.error(e.getMessage(), e);
                return;
            }
        }
        Sentry.getContext().recordBreadcrumb(builder.build());
        call.resolve();
    }

}
