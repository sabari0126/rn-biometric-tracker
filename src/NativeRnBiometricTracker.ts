import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  enableBiometricTracker(): Promise<object>;
  disableBiometricTracker(): Promise<object>;
  isBiometricTrackerEnabled(): Promise<boolean>;
  isBiometricChanged(): Promise<object>;
}

export default TurboModuleRegistry.getEnforcing<Spec>('RnBiometricTracker');
