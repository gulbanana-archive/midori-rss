//
//  FeedService.m
//  MidorI Feed
//
//  Created by Thomas Castiglione on 7/04/13.
//  Copyright (c) 2013 Thomas Castiglione. All rights reserved.
//

#import "FeedService.h"

@implementation FeedService

- (NSArray*) retrieveNews
{
    //[NSJSONSerialization JSONObjectWithData:<#(NSData *)#> options:<#(NSJSONReadingOptions)#> error:<#(NSError *__autoreleasing *)#>
    
    [NSThread sleepForTimeInterval:0.5];
    return @[
             [[NewsItem alloc] initWithTitle:@"[A5I6] ==>"
                                        feed:@"MS Paint Adventures"
                                        link:[NSURL URLWithString:@"http://www.mspaintadventures.com/?s=6&p=008010"]
                                        read:YES],
             [[NewsItem alloc] initWithTitle:@"[A5I6] SS: Gaze over balcony at horizon."
                                        feed:@"MS Paint Adventures"
                                        link:[NSURL URLWithString:@"http://www.mspaintadventures.com/?s=6&p=008013"]
                                        read:NO]
             ];
}

- (void)markRead:(NewsItem*)item
{
    [NSThread sleepForTimeInterval:1.5];
    item.read = YES;
}

- (void)markUnread:(NewsItem*)item
{
    [NSThread sleepForTimeInterval:1.5];
    item.read = NO;
}

@end
