import { useEffect, useState } from 'react';
import { Text, View, StyleSheet } from 'react-native';
import { networkListener } from 'react-native-network-listener';

export default function App() {
  const [networkStatus, setNetworkStatus] = useState<{
    type: string;
    isConnected: boolean;
  } | null>(null);

  useEffect(() => {
    const unsubscribe = networkListener((status) => {
      console.log('Network status changed:', status);
      setNetworkStatus(status);
    });

    return () => {
      unsubscribe();
    };
  }, []);

  return (
    <View style={styles.container}>
      <Text>Network type: {networkStatus?.type}</Text>
      <Text>Network Status: {networkStatus?.isConnected.toString()}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
