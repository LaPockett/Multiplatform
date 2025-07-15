import SwiftUI
import FirebaseCore
import FirebaseMessaging

class AppDelegate: NSObject, UIApplicationDelegate, UNUserNotificationCenterDelegate {
  func application(_ application: UIApplication,
                   didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
    FirebaseApp.configure()
      UNUserNotificationCenter.current().delegate = self
      let authOptions : UNAuthorizationOptions = [.alert, .badge, .sound]
      UNUserNotificationCenter.current().requestAuthorization(options: authOptions){ (_, error) in
          guard error == nil else {
              print(error!.localizedDescription)
              return
          }
      }
      application.registerForRemoteNotifications()
    return true
  }
    func messaging(_ messaging: Messaging, didReceiveRegistrationToken fcmToken: String){
        print("Firebase registration token: $fcmToken")
        print("\(#function)\nFirebase registration token: \(String(describing: fcmToken))")
        let dataDict:[String:String] = ["token": fcmToken]
        NotificationCenter.default.post(name: Notification.Name("FCMToken"), object:nil, userInfo:dataDict)
    }
}

@main
struct iOSApp: App {
    // register app delegate for Firebase setup
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}