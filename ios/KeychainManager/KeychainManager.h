#import <Foundation/Foundation.h>
#import "BiometricResultCode.h"

NS_ASSUME_NONNULL_BEGIN

@interface KeychainManager : NSObject

+ (NSDictionary *)enableBiometricTracker;
+ (NSDictionary *)disableBiometricTracker;
+ (BOOL)isBiometricTrackerEnabled;
+ (NSDictionary *)checkBiometricChange;

@end

NS_ASSUME_NONNULL_END
