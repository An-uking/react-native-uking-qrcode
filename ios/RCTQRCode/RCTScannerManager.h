#import <UIKit/UIKit.h>
#import <AVFoundation/AVFoundation.h>

#import "RCTBridge.h"
#import "UIView+React.h"
#import "RCTScanner.h"
#import "SGQRCodeScanManager.h"
#import "SGQRCodeAlbumManager.h"

//@interface RCTQrCodeScanner : NSObject <RCTBridgeModule>
//
//@end

@interface RCTScannerManager: UIView

- (id)initWithManager:(RCTScanner *)manager bridge:(RCTBridge *)bridge;
//-(instancetype)initWithViewController:(UIViewController *)vc;
@property (nonatomic, strong) id<SGQRCodeScanManagerDelegate> delegate;
@property (nonatomic, copy) RCTBubblingEventBlock onSuccess;
@property (nonatomic, copy) RCTBubblingEventBlock onScanError;
@end
