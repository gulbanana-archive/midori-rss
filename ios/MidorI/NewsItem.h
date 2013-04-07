#import <Foundation/Foundation.h>

@interface NewsItem : NSObject

@property (readonly) NSString* title;
@property (readonly) NSString* feed;
@property (readonly) NSURL* link;
@property BOOL read;

- (id)initWithTitle:(NSString*)title feed:(NSString*)feed link:(NSURL*)link read:(BOOL)read;
- (id)initWithJSON:(id)item;

@end
