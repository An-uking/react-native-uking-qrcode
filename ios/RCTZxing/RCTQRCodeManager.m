//
//  RCTQRCodeManager.m
//  RCTZxing
//
//  Created by uking on 2018/1/2.
//  Copyright © 2018年 uking. All rights reserved.
//
#import <UIKit/UIKit.h>
#import "RCTQRCode.h"
#import "RCTQRCodeManager.h"
#import "RCTBridge.h"           //进行通信的头文件
#import "RCTEventDispatcher.h"  //事件派发，不导入会引起Xcode警告
#import "RCTConvert.h"
#import "LBXScanNative.h"
#import "UIImageView+CornerRadius.h"
//#import "ZXingWrapper.h"
//#import "ZXBarcodeFormat.h"

@implementation RCTQRCodeManager
RCT_EXPORT_MODULE(RCTQRCodeManager)

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

- (UIView *)view
{
    
    return [[RCTQRCode alloc] init];
    //return self;
}
RCT_CUSTOM_VIEW_PROPERTY(options, NSDictionary, RCTQRCode) {
    NSDictionary *options = [RCTConvert NSDictionary:json];
    NSString *content = [options objectForKey:@"content"];
    NSNumber *codewidth =[options objectForKey:@"size"];
    NSNumber *codeheight =[options objectForKey:@"size"];
    NSString *forecolor =[options objectForKey:@"forecolor"];
    NSString *backcolor =[options objectForKey:@"backcolor"];
    NSString *logo =[options objectForKey:@"logo"];
    @try{
        //[CIColor]
        view.image=[LBXScanNative createQRWithString:content QRSize:CGSizeMake([RCTConvert CGFloat:codewidth], [RCTConvert CGFloat:codeheight]) QRColor:[RCTConvert UIColor:forecolor] bkColor:[RCTConvert UIColor:backcolor]];
        //view.image=[LBXScanNative cre];
        //view.image=[SGQRCodeGenerateManager generateWithColorQRCodeData:content backgroundColor:[RCTConvert UIColor:backcolor].CGColor mainColor:[RCTConvert UIColor:forecolor].CGColor];
        //view.image=[SGQRCodeGenerateManager gen]
        if(logo!=nil){
            CGSize logoSize=CGSizeMake(30, 30);
            
            NSString *imgpath=[NSString stringWithFormat:@"%@/Documents/%@",NSHomeDirectory(),logo];
            UIImage *logoImg=[[UIImage alloc]initWithContentsOfFile:imgpath];
            //UIImageView* imageView3=[[UIImageView alloc]initWithImage:imgFromUrl3];
            
            //self.logoImgView =[[UIImageView alloc]init];
            self.logoImgView = [self roundCornerWithImage:logoImg size:logoSize];
            //self.logoImgView = [self roundCornerWithImage:[UIImage imageNamed:logo] size:logoSize];
            _logoImgView.bounds = CGRectMake(0, 0, logoSize.width, logoSize.height);
            _logoImgView.center = CGPointMake([RCTConvert CGFloat:codeheight]/2,[RCTConvert CGFloat:codewidth]/2);
            [view addSubview:_logoImgView];
        }
    }
    @catch(NSException *exception) {
        NSLog(@"exception:%@", exception);
    }
    @finally {
        
    }
    
    //view.image=[LBXScanNative createQRWithString:content QRSize:CGSizeMake([RCTConvert CGFloat:codewidth], [RCTConvert CGFloat:codeheight])];
}
- (UIImageView*)roundCornerWithImage:(UIImage*)logoImg size:(CGSize)size
{
    //logo圆角
    UIImageView *backImage = [[UIImageView alloc] initWithCornerRadiusAdvance:6.0f rectCornerType:UIRectCornerAllCorners];
    backImage.frame = CGRectMake(0, 0, size.width, size.height);
    backImage.backgroundColor = [UIColor whiteColor];
    
    UIImageView *logImage = [[UIImageView alloc] initWithCornerRadiusAdvance:6.0f rectCornerType:UIRectCornerAllCorners];
    logImage.image =logoImg;
    CGFloat diff  =2;
    logImage.frame = CGRectMake(diff, diff, size.width - 2 * diff, size.height - 2 * diff);
    
    [backImage addSubview:logImage];
    
    return backImage;
}
@end
