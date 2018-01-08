//
//  RCTBarCodeManager.m
//  RCTZxing
//
//  Created by uking on 2018/1/1.
//
#import "RCTBarCode.h"
#import "RCTBarCodeManager.h"

@implementation RCTBarCodeManager

RCT_EXPORT_MODULE()

- (UIView *)view
{
    return [[RCTBarCode alloc] init];
}
RCT_EXPORT_VIEW_PROPERTY(text, NSString);
RCT_EXPORT_VIEW_PROPERTY(format, NSString);
RCT_EXPORT_VIEW_PROPERTY(width, int);
RCT_EXPORT_VIEW_PROPERTY(height, int);

@end
