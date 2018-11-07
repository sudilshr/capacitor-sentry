#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(SentryPlugin, "SentryPlugin",
           CAP_PLUGIN_METHOD(initialize, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(crash, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setUser, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setTags, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setExtra, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(clearContext, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(captureMessage, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(captureException, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(captureBreadcrumb, CAPPluginReturnPromise);
           )
