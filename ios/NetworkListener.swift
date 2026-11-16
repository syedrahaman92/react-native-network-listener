//
//  NetworkListenerSwift.swift
//  NetworkListener
//
//  Created by Syed Rahaman on 16/11/26.
//

import Foundation
import Network

@objcMembers public class NetworkListenerSwift: NSObject {
  
  private var networkMonitor: NWPathMonitor?
  private let networkQueue = DispatchQueue(label: "NetworkMonitor")
  private var networkStatusChanged: ((NSDictionary) -> Void)?
  
  @objc(initWithNetworkStatusChanged:)
  public init(networkStatusChanged: @escaping((NSDictionary) -> Void)) {
    super.init()
    self.networkStatusChanged = networkStatusChanged
  }
  
  public func startNetworkListening() {
    networkMonitor = NWPathMonitor()
    networkMonitor?.pathUpdateHandler = {[weak self] path in
      let isConnected = path.status == .satisfied
      let type = path.usesInterfaceType(.cellular) ? "Cellular" :
      path.usesInterfaceType(.wifi) ? "Wi-Fi" :
      path.usesInterfaceType(.wiredEthernet) ? "Wired Ethernet" : ""
      DispatchQueue.main.async {
        self?.networkStatusChanged?(["isConnected": isConnected, "type": type])
      }
    }
    networkMonitor?.start(queue: networkQueue)
  }
  
  public func stopNetworkListening() {
    networkMonitor?.cancel()
    networkMonitor = nil
  }
}
