#import "RnBiometricTracker.h"
#import "KeychainManager.h"

@implementation RnBiometricTracker

// We won't compile this code when we build for the old architecture.
#ifdef RCT_NEW_ARCH_ENABLED

- (std::shared_ptr<facebook::react::TurboModule>)getTurboModule:
    (const facebook::react::ObjCTurboModule::InitParams &)params
{
    return std::make_shared<facebook::react::NativeRnBiometricTrackerSpecJSI>(params);
}
#endif

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(enableBiometricTracker: (RCTPromiseResolveBlock)resolve
                    reject:(RCTPromiseRejectBlock)reject) {
  NSDictionary *result = [KeychainManager enableBiometricTracker];
  resolve(result);
}

RCT_EXPORT_METHOD(disableBiometricTracker: (RCTPromiseResolveBlock)resolve
                    reject:(RCTPromiseRejectBlock)reject) {
    NSDictionary *result = [KeychainManager disableBiometricTracker];
    resolve(result);
}

RCT_EXPORT_METHOD(isBiometricTrackerEnabled: (RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject) {
  BOOL enabled = [KeychainManager isBiometricTrackerEnabled];
  resolve(@(enabled));
}

RCT_EXPORT_METHOD(isBiometricChanged: (RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject) {
  NSDictionary *result = [KeychainManager checkBiometricChange];
  resolve(result);
}

@end
