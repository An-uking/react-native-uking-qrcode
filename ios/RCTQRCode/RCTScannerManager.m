#import "RCTUIManager.h"
#import "UIView+React.h"
#import "RCTScannerManager.h"
#import "SGQRCodeScanningView.h"


@interface RCTScannerManager () <SGQRCodeScanManagerDelegate, SGQRCodeAlbumManagerDelegate>
@property (nonatomic, strong) SGQRCodeScanManager *manager;
@property (nonatomic, strong) SGQRCodeScanningView *scanningView;
@property (nonatomic, strong) UIButton *flashlightBtn;
@property (nonatomic, strong) UILabel *promptLabel;
@property (nonatomic, assign) BOOL isSelectedFlashlightBtn;
@property (nonatomic, strong) UIView *bottomView;
@property (nonatomic, strong) RCTBridge *bridge;
@property (nonatomic, strong) RCTScanner *viewManager;
@property (nonatomic, strong) UIViewController *viewController;
@end

@implementation RCTScannerManager
//UIViewController * viewController;
- (id)initWithManager:(RCTScanner *)manager bridge:(RCTBridge *)bridge
{
    if ((self = [super init])) {
        self.viewManager = manager;
        self.viewController=manager.testController;
        self.bridge = bridge;
        //self.delegate=self.viewController
        //self.viewController=manager.view.reactViewController;
        //self.viewController=self.inputAccessoryViewController;
    }
    return self;
}
//-(instancetype)initWithViewController:(UIViewController *)vc{
//    self=[super init];
//    self.viewController=vc;
//    return self;
//}
- (SGQRCodeScanningView *)scanningView: (CGRect)frame{
    if (!_scanningView) {
        _scanningView = [[SGQRCodeScanningView alloc] initWithFrame:frame];
        _scanningView.borderColor = [UIColor whiteColor];
        _scanningView.scanningImageName = @"SGQRCode.bundle/QRCodeScanningLineGrid";
        _scanningView.scanningAnimationStyle = ScanningAnimationStyleGrid;
        _scanningView.cornerColor = [UIColor orangeColor];
    }
    return _scanningView;
}

- (void)reactSetFrame:(CGRect)frame
{
    // Do something with the height here
    [super reactSetFrame: frame];
    NSLog(@"reactSetFrame Width & Height: %f, %f", frame.size.width, frame.size.height);
    self.scanningView = [self scanningView:frame];
    [self addSubview:self.scanningView];
    [self setupQRCodeScanning];
}

- (void)reactSetInheritedBackgroundColor:(UIColor *)inheritedBackgroundColor
{
    self.backgroundColor = inheritedBackgroundColor;
}


- (void)removeScanningView {
    [self.scanningView removeTimer];
    [self.scanningView removeFromSuperview];
    self.scanningView = nil;
}

- (void)setupQRCodeScanning {
    self.manager = [SGQRCodeScanManager sharedManager];
    NSArray *arr = @[AVMetadataObjectTypeQRCode, AVMetadataObjectTypeEAN13Code, AVMetadataObjectTypeEAN8Code, AVMetadataObjectTypeCode128Code];
    // AVCaptureSessionPreset1920x1080 推荐使用，对于小型的二维码读取率较高
    //[_manager setupSessionPreset:AVCaptureSessionPreset1920x1080 metadataObjectTypes:arr c:self.re];
    //UIViewController *viewController = (UIViewController *)self.manager..reactViewController;
    //UIViewController ss=[[UIViewController alloc]init]
    
    
    RCTUIManager *uiManager = _bridge.uiManager;
//    dispatch_async(uiManager.methodQueue, ^{
//        [uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *,UIView *> *viewRegistry) {
//            UIView *view = viewRegistry[self.bridge.id];
//            UIViewController *viewController = (UIViewController *)view.reactViewController;
//        }
//         }
    
    [_manager setupSessionPreset:AVCaptureSessionPreset1920x1080 metadataObjectTypes:arr currentController:self.viewController];
    [_manager cancelSampleBufferDelegate];
    _manager.delegate = self;
}

#pragma mark - - - SGQRCodeAlbumManagerDelegate
- (void)QRCodeAlbumManagerDidCancelWithImagePickerController:(SGQRCodeAlbumManager *)albumManager {
    NSLog(@"Picking albums qrcode.");
    // [self addSubview:self.scanningView];
}
- (void)QRCodeAlbumManager:(SGQRCodeAlbumManager *)albumManager didFinishPickingMediaWithResult:(NSString *)result {
    NSLog(@"Jump to result: %@", result);
}

#pragma mark - - - SGQRCodeScanManagerDelegate
- (void)QRCodeScanManager:(SGQRCodeScanManager *)scanManager didOutputMetadataObjects:(NSArray *)metadataObjects {
    NSLog(@"metadataObjects - - %@", metadataObjects);
    if (metadataObjects != nil && metadataObjects.count > 0) {
        // [scanManager palySoundName:@"SGQRCode.bundle/sound.caf"];
        [scanManager stopRunning];
        [scanManager videoPreviewLayerRemoveFromSuperlayer];
        
        AVMetadataMachineReadableCodeObject *meta = metadataObjects[0];
        NSLog(@"Scan value: %@", meta.stringValue);
        self.onSuccess(@{ @"result": meta.stringValue });
    } else {
        // NSLog(@"暂未识别出扫描的二维码");
        // onError is preserved
        self.onScanError(@{ @"error": @"Could not recognize the QrCode scanned" });
    }
}

@end
