#import "KeychainManager.h"
#import <LocalAuthentication/LocalAuthentication.h>
#import <Security/Security.h>

@implementation KeychainManager

+ (NSString *)biometricKey {
   NSString *bundleID = [[NSBundle mainBundle] bundleIdentifier] ?: @"default.bundle.id";
  return [NSString stringWithFormat:@"%@.biometric.state", bundleID];
}

+ (NSDictionary *)enableBiometric {
  LAContext *context = [[LAContext alloc] init];
  NSError *error = nil;

  // Check if biometric is available and enrolled
  if (![context canEvaluatePolicy:LAPolicyDeviceOwnerAuthenticationWithBiometrics error:&error]) {
    return @{ @"resultCode": @(BiometricNoneEnrolled), @"message": @"No biometrics enrolled" };
  }

  NSData *state = context.evaluatedPolicyDomainState;
  if (!state) {
    return @{ @"resultCode": @(BiometricRegistrationFailed), @"message": @"Unable to retrieve biometric state" };
  }

  // Convert to base64 string
  NSString *base64State = [state base64EncodedStringWithOptions:0];
  NSData *encodedData = [base64State dataUsingEncoding:NSUTF8StringEncoding];

  // Prepare Keychain attributes
  NSString *key = [self biometricKey];
  NSDictionary *query = @{
    (__bridge id)kSecClass: (__bridge id)kSecClassGenericPassword,
    (__bridge id)kSecAttrService: key,
    (__bridge id)kSecValueData: encodedData,
    (__bridge id)kSecAttrAccessible: (__bridge id)kSecAttrAccessibleWhenUnlockedThisDeviceOnly
  };

  // Delete if exists
  SecItemDelete((__bridge CFDictionaryRef)query);

  // Save new item
  OSStatus status = SecItemAdd((__bridge CFDictionaryRef)query, NULL);

  if (status == errSecSuccess) {
    return @{ @"resultCode": @(BiometricRegistrationSuccess), @"message": @"Biometric state stored successfully" };
  } else {
    return @{ @"resultCode": @(BiometricRegistrationFailed), @"message": @"Failed to store biometric state" };
  }
}

+ (NSDictionary *)disableBiometric {
  if (![self isBiometricEnabled]) {
    return @{
      @"resultCode": @(BiometricNotEnabled),
      @"message": @"Biometric not enabled"
    };
  }

  NSString *key = [self biometricKey];
  NSDictionary *query = @{
    (__bridge id)kSecClass: (__bridge id)kSecClassGenericPassword,
    (__bridge id)kSecAttrService: key
  };

  OSStatus status = SecItemDelete((__bridge CFDictionaryRef)query);

  if (status == errSecSuccess) {
    return @{
      @"resultCode": @(BiometricDeregisterSuccess),
      @"message": @"Biometric state deleted successfully"
    };
  } else {
    return @{
      @"resultCode": @(BiometricDeregisterFailed),
      @"message": @"Failed to delete biometric state"
    };
  }
}


+ (BOOL)isBiometricEnabled {
  NSString *key = [self biometricKey];

  NSDictionary *query = @{
    (__bridge id)kSecClass: (__bridge id)kSecClassGenericPassword,
    (__bridge id)kSecAttrService: key,
    (__bridge id)kSecReturnData: @YES,
    (__bridge id)kSecMatchLimit: (__bridge id)kSecMatchLimitOne
  };

  CFTypeRef result = NULL;
  OSStatus status = SecItemCopyMatching((__bridge CFDictionaryRef)query, &result);

  if (result) CFRelease(result);

  return (status == errSecSuccess);
}


+ (NSDictionary *)checkBiometricChange {
  if (![self isBiometricEnabled]) {
    return @{
      @"resultCode": @(BiometricNotEnabled),
      @"message": @"Biometric not enabled"
    };
  }

  LAContext *context = [[LAContext alloc] init];
  NSError *error = nil;

  // Evaluate policy to ensure context is valid
  if (![context canEvaluatePolicy:LAPolicyDeviceOwnerAuthenticationWithBiometrics error:&error]) {
    return @{
      @"resultCode": @(BiometricNotEnabled),
      @"message": @"Biometric not available"
    };
  }

  NSData *domainStateData = context.evaluatedPolicyDomainState;
  if (!domainStateData) {
    return @{
      @"resultCode": @(BiometricChangeDetected),
      @"message": @"Unable to read current biometric state"
    };
  }

  // Current domain state encoded
  NSString *currentState = [domainStateData base64EncodedStringWithOptions:0];

  // Get stored state from Keychain
  NSString *key = [self biometricKey];
  NSDictionary *query = @{
    (__bridge id)kSecClass: (__bridge id)kSecClassGenericPassword,
    (__bridge id)kSecAttrService: key,
    (__bridge id)kSecReturnData: @YES,
    (__bridge id)kSecMatchLimit: (__bridge id)kSecMatchLimitOne
  };

  CFTypeRef result = NULL;
  OSStatus status = SecItemCopyMatching((__bridge CFDictionaryRef)query, &result);

  if (status != errSecSuccess || !result) {
    return @{
      @"resultCode": @(BiometricChangeDetected),
      @"message": @"Stored biometric state not found or unreadable"
    };
  }

  NSData *storedData = (__bridge_transfer NSData *)result;
  NSString *storedState = [[NSString alloc] initWithData:storedData encoding:NSUTF8StringEncoding];

  if ([storedState isEqualToString:currentState]) {
    return @{
      @"resultCode": @(BiometricNotChanged),
      @"message": @"No changes detected in biometric data"
    };
  } else {
    return @{
      @"resultCode": @(BiometricChangeDetected),
      @"message": @"Biometric data has changed"
    };
  }
}


@end
