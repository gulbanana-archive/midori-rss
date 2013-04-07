//
//  DetailViewController.h
//  MidorI
//
//  Created by Thomas Castiglione on 7/04/13.
//  Copyright (c) 2013 Thomas Castiglione. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "NewsItem.h"

@interface ItemDetailController : UIViewController <UISplitViewControllerDelegate>

@property (strong, nonatomic) NewsItem* detailItem;

@property (weak, nonatomic) IBOutlet UILabel *detailDescriptionLabel;
@end
