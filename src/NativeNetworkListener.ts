import {CodegenTypes, TurboModuleRegistry, type TurboModule } from 'react-native';

export type NetworkStatus = {
  isConnected: boolean;
  type: string;
};

export interface Spec extends TurboModule {
  startNetworkListening(): void;
  stopNetworkListening(): void;

  readonly networkStatusChanged: CodegenTypes.EventEmitter<NetworkStatus>;
}

export default TurboModuleRegistry.getEnforcing<Spec>('NetworkListener');

