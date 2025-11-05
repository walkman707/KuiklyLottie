
#import "KTLottieView.h"
#import <kLottieViewIOS/kLottieViewIOS-Swift.h>

@interface KTLottieView ()

@property(nonatomic) KTBLottieView *rootView;

@end

@implementation KTLottieView

@synthesize hr_rootView;

- (instancetype)init {
    self = [super init];
    if (self) {
        _rootView = [KTBLottieView new];
        [self addSubview:_rootView];
    }
    return self;
}

- (void)layoutSubviews {
    [super layoutSubviews];
    _rootView.frame = self.bounds;
}

#pragma mark - KuiklyRenderViewExportProtocol

- (void)hrv_setPropWithKey:(NSString *)propKey propValue:(id)propValue {
    KUIKLY_SET_CSS_COMMON_PROP;
}

- (void)hrv_callWithMethod:(NSString *)method params:(NSString *)params callback:(KuiklyRenderCallback)callback {
    KUIKLY_CALL_CSS_METHOD;
}

- (void)setCss_src:(NSString *)css_src {
    NSLog(@"LottieView css_src %@", css_src);
    [_rootView setSrcWithPath:css_src];
}

- (void)setCss_autoPlay:(NSNumber *)css_autoPlay {
    NSLog(@"LottieView css_autoPlay %@", css_autoPlay);
    [_rootView setAutoPlayWithValue:css_autoPlay.boolValue];
}

- (void)setCss_loop:(NSNumber *)css_loop {
    NSLog(@"LottieView css_loop %@", css_loop);
    [_rootView setLoopWithValue:css_loop.boolValue];
}

- (void)css_pause:(NSDictionary *)value {
    NSLog(@"LottieView css_pause");
    [_rootView pause];
}

- (void)css_resume:(NSDictionary *)value {
    NSLog(@"LottieView css_resume");
    [_rootView resume];
}

- (void)css_play:(NSDictionary *)value {
    NSLog(@"LottieView css_play");
    [_rootView play];
}

- (void)css_progress:(NSDictionary *)value {
    NSLog(@"LottieView css_progress %@", value);
    [_rootView progressWithValue:value];
}

@end
