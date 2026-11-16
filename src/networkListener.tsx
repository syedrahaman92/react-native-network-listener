import NetworkListener, { type NetworkStatus } from './NativeNetworkListener';

export const networkListener = (callback: (status: NetworkStatus) => void) => {
  NetworkListener.startNetworkListening();
  const subscription = NetworkListener.networkStatusChanged(callback);
  return () => {
    NetworkListener.stopNetworkListening();
    subscription.remove();
  };
};
