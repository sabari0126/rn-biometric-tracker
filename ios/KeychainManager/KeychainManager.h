#import <Foundation/Foundation.h>
#import "BiometricResultCode.h"

NS_ASSUME_NONNULL_BEGIN

@interface KeychainManager : NSObject

+ (NSDictionary *)enableBiometric;
+ (NSDictionary *)disableBiometric;
+ (BOOL)isBiometricEnabled;
+ (NSDictionary *)checkBiometricChange;

@end

NS_ASSUME_NONNULL_END
