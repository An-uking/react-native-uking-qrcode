//
//  Created by uking on 2018/1/2.
//  Copyright © 2018年 uking. All rights reserved.
//
#import "RCTQRCode.h"
#import "RCTQRCodeManager.h"

@implementation RCTQRCodeManager

RCT_EXPORT_MODULE()

- (UIView *)view
{
    return [[RCTQRCode alloc] init];
}
RCT_EXPORT_VIEW_PROPERTY(text, NSString);
RCT_EXPORT_VIEW_PROPERTY(size, int);
RCT_EXPORT_VIEW_PROPERTY(forecolor, NSNumber);
RCT_EXPORT_VIEW_PROPERTY(backcolor, NSNumber);
RCT_EXPORT_VIEW_PROPERTY(logo, NSString);
@end
