#import "NewsItem.h"

@implementation NewsItem
- (id)initWithTitle:(NSString *)title feed:(NSString *)feed link:(NSURL *)link read:(BOOL)read
{
    self = [super init];
    if (self)
    {
        _title = title;
        _feed = feed;
        _link = link;
        _read = read;
    }
    return self;
}

- (id)initWithJSON:(id)item
{
    return [self initWithTitle:@"Item"
                          feed:@"Item"
                          link:[NSURL URLWithString:@"http://google.com"]
                          read:YES];
}
@end
