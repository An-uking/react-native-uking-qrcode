//
//  RCTBarCode.m
//  RCTZxing
//
//  Created by uking on 2018/1/1.
//  Copyright © 2018年 uking. All rights reserved.
//

#import "RCTBarCode.h"
#import "RCTConvert.h"
#import <UIKit/UIKit.h>
#import "RCTZXing.h"

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

@implementation RCTBarCode{
    UIImageView *_image;
    NSString *_text;
    NSString *_format;
    int _width;
    int _height;
}
/**
 * 设置条码内容
 */
-(void) setText:(NSString *)text{
    _text=text;
    [self removeImage];
    
}
-(void) setFormat:(NSString *)format{
    _format=format;
    [self removeImage];
}
-(void) setWidth:(int )width{
    _width=width;
    [self removeImage];
}
-(void) setHeight:(int )height
{
    _height=height;
    [self removeImage];
}

-(void) removeImage
{
    if (_image) {
        [_image removeFromSuperview];
    }
}
-(void)layoutSubviews
{
    NSLog(@"In layoutSubviews");
    [super layoutSubviews];
    UIImage *image =   [RCTZXing createCodeWithString:_text size:CGSizeMake(_width, _height) CodeFomart: [RCTConvert ZXBarcodeFormat:_format]];
    _image = [[UIImageView alloc] init];
    [_image setImage:image];
    _image.frame = self.bounds;
    [self insertSubview:_image atIndex:0];
}
@end
