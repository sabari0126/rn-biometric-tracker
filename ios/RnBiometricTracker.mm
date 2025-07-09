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

RCT_EXPORT_METHOD(enableBiometric: (RCTPromiseResolveBlock)resolve
                    reject:(RCTPromiseRejectBlock)reject) {
  NSDictionary *result = [KeychainManager enableBiometric];
  resolve(result);
}

RCT_EXPORT_METHOD(disableBiometric: (RCTPromiseResolveBlock)resolve
                    reject:(RCTPromiseRejectBlock)reject) {
    NSDictionary *result = [KeychainManager disableBiometric];
    resolve(result);
}

RCT_EXPORT_METHOD(isBiometricEnabled: (RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject) {
  BOOL enabled = [KeychainManager isBiometricEnabled];
  resolve(@(enabled));
}

RCT_EXPORT_METHOD(isBiometricChanged: (RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject) {
  NSDictionary *result = [KeychainManager checkBiometricChange];
  resolve(result);
}

@end
