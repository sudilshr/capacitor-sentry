import Foundation
import Capacitor
import Sentry

typealias JSObject = [String:Any]
@objc(SentryPlugin)
public class SentryPlugin: CAPPlugin {
    
    @objc func initialize(_ call: CAPPluginCall) {
        let dsn = call.getString("dsn") ?? ""
        do{
            Client.shared  =  try Client.init(dsn:dsn )
            try Client.shared?.startCrashHandler()
            call.success()
        }catch let error{
            call.reject(error.localizedDescription)
        }
    }
    
    @objc func crash(_ call: CAPPluginCall){
        Client.shared?.crash();
    }
    
    @objc func setUser(_ call: CAPPluginCall){
        let email: String = call.getString("email") ?? ""
        let id: String = call.getString("id") ?? ""
        let ip: String = call.getString("ipAddress") ?? ""
        let username: String = call.getString("username") ?? ""
        let data: JSObject  = call.getObject("data") ?? [:]
        let builder: User = User.init()
        if (email != "") {
            builder.email = email
        }
        if (id != "") {
            builder.userId = id
        }
        
        if (ip != "") {
            builder.setValue(id, forKey: "ipAddress");
        }
        if(username != ""){
            builder.username = username
        }
        builder.extra = [:]
        for key in data.keys{
            builder.extra?[key] = data[key]
        }
        Client.shared?.user =  builder;
        call.resolve();
    }
    
    @objc func setTags(_ call: CAPPluginCall){
        if(Client.shared?.tags == nil){
            Client.shared?.tags = [:]
        }
        let tags:JSObject = call.getObject("tags") ?? [:]
        for key in tags.keys{
            Client.shared?.tags?[key] = tags[key] as? String
        }
        call.resolve();
    }
    
    @objc func setExtra(_ call: CAPPluginCall){
        if(Client.shared?.extra == nil){
            Client.shared?.extra = [:]
        }
        let extra:JSObject = call.getObject("extra") ?? [:]
        for key in extra.keys{
            Client.shared?.extra?[key] = extra[key]
        }
        call.resolve();
    }
    
    
    @objc func clearContext(_ call: CAPPluginCall){
        Client.shared?.clearContext();
        call.resolve();
    }
    
    @objc func captureMessage(_ call: CAPPluginCall){
        let event = Event.init(level: SentrySeverity.info)
        Client.shared?.send(event: event, completion: { (error) in
            if(error != nil){
                call.reject(error?.localizedDescription ?? "")
            }else{
                call.resolve();
            }
        })
    }
    
    @objc func captureException(_ call: CAPPluginCall){
        /*
        let name: String = call.getString("name") ?? ""
        let reason: String = call.getString("reason") ?? ""
        let language: String = call.getString("language") ?? ""
        let lineOfCode: String = call.getString("lineOfCode") ?? ""
        let stackTrace: [Any] = call.get("stackTrace", [Any].self) ?? []
        let logAllThreads: Bool =  call.getBool("logAllThreads") ?? false
        let terminateProgram: Bool = call.getBool("terminateProgram") ?? false
        */
        
       let exception =  call.getObject("exception");
        if(exception == nil){
            call.reject("Exception empty")
            return;
        }
        
        Client.shared?.reportUserException(exception?["name"] as! String, reason: exception?["message"] as! String, language: "", lineOfCode: "", stackTrace: [exception?["stack"] as! String], logAllThreads: true, terminateProgram: false)
        
        call.resolve();
    }
    
    @objc func captureBreadcrumb(_ call: CAPPluginCall){
        let crumb:Breadcrumb = Breadcrumb.init(level: SentrySeverity.debug, category: "")
        let level = call.getString("level") ?? "debug"
        let category = call.getString("category") ?? ""
        let message = call.getString("message") ?? ""
        let timeStamp = call.getString("timeStamp") ?? ""
        switch level {
        case "info":
            crumb.level = SentrySeverity.info
        case "warning":
            crumb.level = SentrySeverity.warning
        case "error":
            crumb.level = SentrySeverity.error
        case "critical":
            crumb.level = SentrySeverity.fatal
        default:
            crumb.level = SentrySeverity.debug
        }
        
        crumb.category = category
        crumb.message = message
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
        crumb.timestamp = dateFormatter.date(from: timeStamp)
        Client.shared?.breadcrumbs.add(crumb)
        call.resolve();
    }
    
}
