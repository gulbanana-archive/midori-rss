//
//  FeedService.m
//  MidorI Feed
//
//  Created by Thomas Castiglione on 7/04/13.
//  Copyright (c) 2013 Thomas Castiglione. All rights reserved.
//

#import "FeedService.h"
#import "NewsItem.h"

@implementation FeedService

- (NSArray*) retrieveNews
{
    [NSThread sleepForTimeInterval:1.5];
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

@end
