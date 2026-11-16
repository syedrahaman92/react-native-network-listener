#import "NetworkListener.h"

@implementation NetworkListener{
  NetworkListenerSwift* _networkListener;
}

- (instancetype)init {
  if (self = [super init]) {
    __weak NetworkListener* weakSelf = self;
      _networkListener = [[NetworkListenerSwift alloc]
                          initWithNetworkStatusChanged:^(NSDictionary * data) {
        if (weakSelf) {
          [weakSelf emitNetworkStatusChanged:data];
        }
      }];
  }
  return self;
}

- (std::shared_ptr<facebook::react::TurboModule>)getTurboModule:
    (const facebook::react::ObjCTurboModule::InitParams &)params
{
  return std::make_shared<facebook::react::NativeNetworkListenerSpecJSI>(params);
}

+ (NSString *)moduleName
{
  return @"NetworkListener";
}

- (void)startNetworkListening {
  [_networkListener startNetworkListening];
}

- (void)stopNetworkListening { 
  [_networkListener stopNetworkListening];
}

@end
