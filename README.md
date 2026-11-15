# react-native-network-listener

Listen to device network. This library is created using TurboModule to use it in react-native. Easy to implement and easy to make the the changes native

## Installation


```sh
npm install react-native-network-listener
```


## Usage


```js
import { networkListener } from 'react-native-network-listener';

// ...

useEffect(() => {
    const unsubscribe = networkListener((status) => {
        console.log(status)
    });
    
    return () => {
      unsubscribe();
      
    };
  }, []);
```


## Contributing

- [Development workflow](CONTRIBUTING.md#development-workflow)
- [Sending a pull request](CONTRIBUTING.md#sending-a-pull-request)
- [Code of conduct](CODE_OF_CONDUCT.md)

## License

MIT. For more information see [LICENSE](LICENSE)
