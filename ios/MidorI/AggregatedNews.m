//
//  AggregatedNews.m
//  MidorI Feed
//
//  Created by Thomas Castiglione on 10/04/13.
//  Copyright (c) 2013 Thomas Castiglione. All rights reserved.
//

#import "AggregatedNews.h"

@interface AggregatedNews ()
{
    NSMutableArray* _items;
}
@end

@implementation AggregatedNews

- (id)init
{
    if (self = [super init])
    {
        _items = [NSMutableArray array];
    }
    return self;
}

@end
