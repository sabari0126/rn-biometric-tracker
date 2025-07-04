import { NativeModules } from 'react-native';

const LINKING_ERROR =
  `The package 'rn-biometric-tracker' doesn't seem to be linked. Make sure: \n\n` +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

// @ts-expect-error
const isTurboModuleEnabled = global.__turboModuleProxy != null;

const RnBiometricTrackerModule = isTurboModuleEnabled
  ? require('./NativeRnBiometricTracker').default
  : NativeModules.RnBiometricTracker;

const RnBiometricTracker = RnBiometricTrackerModule
  ? RnBiometricTrackerModule
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

function enableBiometric(): Promise<object> {
  return RnBiometricTracker.enableBiometric();
}

function disableBiometric(): Promise<object> {
  return RnBiometricTracker.disableBiometric();
}

function isBiometricEnabled(): Promise<boolean> {
  return RnBiometricTracker.isBiometricEnabled();
}

function isBiometricChanged(): Promise<object> {
  return RnBiometricTracker.isBiometricChanged();
}

const BioTrack = {
  enableBiometric,
  disableBiometric,
  isBiometricEnabled,
  isBiometricChanged,
};

export default BioTrack;
