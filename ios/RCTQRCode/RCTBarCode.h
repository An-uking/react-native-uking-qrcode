//
//  RCTBarCode.h
//  RCTZxing
//
//  Created by uking on 2018/1/1.
//  Copyright © 2018年 uking. All rights reserved.
//


#import <UIKit/UIKit.h>
#import "RCTView.h"

@interface RCTBarCode : RCTView
/**
 @text 条码文本内容
 */
@property (nonatomic, copy) NSString *text;
/**
 @format 条码格式
 */
@property (nonatomic, copy) NSString *format;
/**
 @width 条码宽度
 */
@property  (nonatomic, assign) int width;

/**
 @height 条码高度
 */
@property  (nonatomic, assign) int height;

@end
