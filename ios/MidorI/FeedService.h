//
//  FeedService.h
//  MidorI Feed
//
//  Created by Thomas Castiglione on 7/04/13.
//  Copyright (c) 2013 Thomas Castiglione. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "NewsItem.h"

@interface FeedService : NSObject

- (NSArray*)retrieveNews;
- (void)markRead:(NewsItem*)item;
- (void)markUnread:(NewsItem*)item;

@end
