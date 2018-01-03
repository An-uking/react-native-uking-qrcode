#import "RCTScanner.h"
#import "RCTBridgeModule.h"
#import "RCTEventDispatcher.h"
//#import "RCTEventEmitter.h"
#import "UIView+React.h"

@implementation RCTScanner{
    RCTEventDispatcher *_eventDispatcher;
    BOOL _started;
    BOOL _muted;
    BOOL _islive;
    BOOL _autoPlay;
    BOOL _loop;
    BOOL _first;
    BOOL _isSeeking;
    NSTimer *_timer;
    //CMTime _currentTime;
}


- (instancetype)initWithEventDispatcher:(RCTEventDispatcher *)eventDispatcher
{
    if ((self = [super init])) {
        _eventDispatcher = eventDispatcher;
        _started = YES;
        _muted = NO;
        _timer = nil;
        _first = YES;
        //self.reconnectCount = 0;
    }
    
    return self;
};
@end
