//
//  RCTBarCodeManager.m
//  RCTZxing
//
//  Created by uking on 2018/1/1.
//

#import <UIKit/UIKit.h>
#import "RCTBarCode.h"
#import "RCTBarCodeManager.h"
#import "RCTBridge.h"           //进行通信的头文件
#import "RCTEventDispatcher.h"  //事件派发，不导入会引起Xcode警告
#import "RCTConvert.h"
#import "ZXingWrapper.h"
#import "ZXBarcodeFormat.h"

@implementation RCTConvert (ZXBarcodeFormat)
RCT_ENUM_CONVERTER(ZXBarcodeFormat, (@{ @"AZTEC" : @(kBarcodeFormatAztec),
                                        @"CODABAR" : @(kBarcodeFormatCodabar),
                                        @"CODE_39" : @(kBarcodeFormatCode39),
                                        @"CODE_93" : @(kBarcodeFormatCode93),
                                        @"CODE_128" : @(kBarcodeFormatCode128),
                                        @"DATA_MATRIX" : @(kBarcodeFormatDataMatrix),
                                        @"EAN_8" : @(kBarcodeFormatEan8),
                                        @"EAN_13" : @(kBarcodeFormatEan13),
                                        @"ITF" : @(kBarcodeFormatITF),
                                        @"MAXICODE" : @(kBarcodeFormatMaxiCode),
                                        @"PDF_417" : @(kBarcodeFormatPDF417),
                                        @"QR_CODE" : @(kBarcodeFormatQRCode),
                                        @"RSS_14" : @(kBarcodeFormatRSS14),
                                        @"RSS_EXPANDED" : @(kBarcodeFormatRSSExpanded),
                                        @"UPC_A" : @(kBarcodeFormatUPCA),
                                        @"UPC_E" : @(kBarcodeFormatUPCE),
                                        @"UPC_EAN_EXTENSION" : @(kBarcodeFormatUPCEANExtension)}),
                   kBarcodeFormatEan13, intValue)
@end

@implementation RCTBarCodeManager

RCT_EXPORT_MODULE(RCTBarCodeManager)

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
- (UIView *)view
{
    return [[RCTBarCode alloc] init];
}
RCT_CUSTOM_VIEW_PROPERTY(options, NSDictionary, RCTBarCode) {
    NSDictionary *options = [RCTConvert NSDictionary:json];
    NSString *code = [options objectForKey:@"code"];
    NSNumber *codewidth =[options objectForKey:@"width"];
    NSNumber *codeheight =[options objectForKey:@"height"];
    NSString *codeformat =[options objectForKey:@"format"];
    view.image=[ZXingWrapper createCodeWithString:code size:CGSizeMake([RCTConvert CGFloat:codewidth], [RCTConvert CGFloat:codeheight]) CodeFomart: [RCTConvert ZXBarcodeFormat:codeformat]];
}
@end
