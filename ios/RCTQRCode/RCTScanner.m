//#import "RCTScannerManager.h"
#import "RCTScanner.h"
#import "UIView+React.h"
#import "SGQRCodeScanManager.h"
#import "SGQRCodeAlbumManager.h"
#import "RCTScannerViewController.h"

@interface RCTScanner()
@property (nonatomic, strong) RCTScannerViewController *vController;
@property (nonatomic, strong) UIView *myView;
//@property (nonatomic, strong) RCTScannerManager *rctManager;
@end

@implementation RCTScanner

//- (dispatch_queue_t)methodQueue
//{
//    return dispatch_get_main_queue();
//}
//RCT_EXPORT_MODULE()

RCT_EXPORT_MODULE(RCTScanner)
RCT_EXPORT_VIEW_PROPERTY(onSuccess, RCTBubblingEventBlock)
// RCT_EXPORT_VIEW_PROPERTY(onError, RCTBubblingEventBlock)

- (UIView *)view
{
    if(!self.vController){
        RCTScannerViewController *vc =[[RCTScannerViewController alloc] init];
        self.vController=vc;
        //[self.view addSubview:self.vController.view];
    }

    return (UIView*)self.vController.view ;
}

@end
