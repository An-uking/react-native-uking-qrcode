#import <UIKit/UIKit.h>
#import "RCTView.h"
#import "UIView+React.h"
#import "LBXScanViewStyle.h"
#import "LBXScanView.h"

//@class RCTEventDispatcher;
@class RCTEventDispatcher;

@interface RCTScanner: LBXScanView
//@property (nonatomic, assign) int reconnectCount;

@property (nonatomic, copy) RCTBubblingEventBlock onResult;
@property (nonatomic, copy) RCTBubblingEventBlock onError;

- (instancetype)initWithEventDispatcher:(RCTEventDispatcher *)eventDispatcher NS_DESIGNATED_INITIALIZER;
@end
