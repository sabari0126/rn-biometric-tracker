import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  enableBiometric(): Promise<object>;
  disableBiometric(): Promise<object>;
  isBiometricEnabled(): Promise<boolean>;
  isBiometricChanged(): Promise<object>;
}

export default TurboModuleRegistry.getEnforcing<Spec>('RnBiometricTracker');
