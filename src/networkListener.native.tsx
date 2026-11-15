import NetworkListener from './NativeNetworkListener';

export const networkListener = (callback: (status: any) => void) => {
  NetworkListener.startNetworkListening();
  const subscription = NetworkListener.networkStatusChanged(callback);
  return () => {
    NetworkListener.stopNetworkListening();
    subscription.remove();
  };
};

