//
//  Created by uking on 2018/1/7.
//  Copyright © 2018年 uking. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "RCTView.h"

@interface RCTQRCode : RCTView
/**
 @text 二维码文本内容
 */
@property (nonatomic, copy) NSString *text;
/**
 @size 二维码大小
 */
@property  (nonatomic, assign) int size;
/**
 @forecolor 二维码前景色
 */
@property (nonatomic, copy) NSNumber *forecolor;
/**
 @backcolor 二维码背景色
 */
@property (nonatomic, copy) NSNumber *backcolor;
/**
 @logo 二维码中心图标
 */
@property (nonatomic, copy) NSString *logo;
@end
