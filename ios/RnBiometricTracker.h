#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>

#ifdef RCT_NEW_ARCH_ENABLED

#import "RnBiometricTrackerSpec.h"
@interface RnBiometricTracker : NSObject <NativeRnBiometricTrackerSpec>
@end

#else

@interface RnBiometricTracker : RCTEventEmitter <RCTBridgeModule>
@end

#endif
