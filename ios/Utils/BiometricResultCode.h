#import <Foundation/Foundation.h>

typedef NS_ENUM(NSInteger, BiometricResultCode) {
    BiometricNotSupported = 2001,
    BiometricNoneEnrolled = 2002,
    BiometricRegistrationSuccess = 2003,
    BiometricRegistrationFailed = 2004,
    BiometricDeregisterSuccess = 2005,
    BiometricDeregisterFailed = 2006,
    BiometricNotEnabled = 2007,
    BiometricChangeDetected = 2008,
    BiometricNotChanged = 2009
};
