//
//  DetailViewController.m
//  MidorI
//
//  Created by Thomas Castiglione on 7/04/13.
//  Copyright (c) 2013 Thomas Castiglione. All rights reserved.
//

#import "ItemDetailController.h"

@interface ItemDetailController ()

    @property (strong, nonatomic) UIPopoverController *masterPopoverController;
    @property (weak, nonatomic) IBOutlet UIWebView *webView;

    - (void)configureView;

@end

@implementation ItemDetailController

- (void)configureView
{
    if (self.detailItem) {
        [self.webView loadRequest:[NSURLRequest requestWithURL:self.detailItem.link]];
        self.navigationItem.title = self.detailItem.title;
    }
}

#pragma mark - Managing the detail item

- (void)setDetailItem:(id)newDetailItem
{
    if (_detailItem != newDetailItem) {
        _detailItem = newDetailItem;
        
        // Update the view.
        [self configureView];
    }

    if (self.masterPopoverController != nil) {
        [self.masterPopoverController dismissPopoverAnimated:YES];
    }        
}

#pragma mark - Lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
	self.webView.scalesPageToFit = YES;
    [self configureView];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Split view

- (void)splitViewController:(UISplitViewController *)splitController willHideViewController:(UIViewController *)viewController withBarButtonItem:(UIBarButtonItem *)barButtonItem forPopoverController:(UIPopoverController *)popoverController
{
    barButtonItem.title = NSLocalizedString(@"News", @"News");
    [self.navigationItem setLeftBarButtonItem:barButtonItem animated:YES];
    self.masterPopoverController = popoverController;
}

- (void)splitViewController:(UISplitViewController *)splitController willShowViewController:(UIViewController *)viewController invalidatingBarButtonItem:(UIBarButtonItem *)barButtonItem
{
    // Called when the view is shown again in the split view, invalidating the button and popover controller.
    [self.navigationItem setLeftBarButtonItem:nil animated:YES];
    self.masterPopoverController = nil;
}

@end
