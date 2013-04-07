#import "ItemListController.h"
#import "ItemDetailController.h"
#import "NewsItem.h"
#import "FeedService.h"
#import "ItemCell.h"

@interface ItemListController () {
    NSMutableArray* _items;
    FeedService* _service;
    dispatch_queue_t _mainQueue;
    dispatch_queue_t _defaultQueue;
}
- (IBAction)refresh:(id)sender;
- (void)initFeeds;
- (NSMutableArray*)updateFeeds;
@end

@implementation ItemListController

- (void)initFeeds
{
    _items = [[NSMutableArray alloc] init];
    _service = [[FeedService alloc] init];
    _mainQueue = dispatch_get_main_queue();
    _defaultQueue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
}

- (NSMutableArray*)updateFeeds
{
    NSArray* news = [_service retrieveNews];
    
    NSMutableArray* paths = [NSMutableArray arrayWithCapacity:news.count];
    for (int i = 0; i < news.count; i++)
    {
        [_items insertObject:news[i] atIndex:0];
        [paths insertObject:[NSIndexPath indexPathForRow:i inSection:0]
                    atIndex:i];
    }
    return paths;
}

#pragma mark - Lifecycle

- (void)awakeFromNib
{
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPad) {
        self.clearsSelectionOnViewWillAppear = NO;
        self.contentSizeForViewInPopover = CGSizeMake(320.0, 600.0);
    }
    
    [self initFeeds];
    
    [super awakeFromNib];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    self.navigationItem.leftBarButtonItem = self.editButtonItem;
    self.detailViewController = (ItemDetailController *)[[self.splitViewController.viewControllers lastObject] topViewController];
    [self.refreshControl addTarget:self action:@selector(refresh:) forControlEvents:UIControlEventValueChanged]; //works around a bug
    
    [self refresh:self];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
}

#pragma mark - UI

- (IBAction)refresh:(id)sender
{
    if (sender != self.refreshControl)
    {
        [self.refreshControl beginRefreshing];
        [self.tableView setContentOffset:CGPointMake(0, -self.refreshControl.frame.size.height) animated:YES];
    }
    
    dispatch_async(_defaultQueue, ^ {
        NSMutableArray* newPaths = [self updateFeeds];
        dispatch_async(_mainQueue, ^ {
            [self.tableView insertRowsAtIndexPaths:newPaths
                                  withRowAnimation:UITableViewRowAnimationAutomatic];
            [self.refreshControl endRefreshing];
        });
    });
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    if ([[segue identifier] isEqualToString:@"showDetail"]) {
        NSIndexPath* indexPath = [self.tableView indexPathForSelectedRow];
        NewsItem* item = _items[indexPath.row];
        [[segue destinationViewController] setDetailItem:item];
    }
}

#pragma mark - Table View

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return _items.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    NewsItem* item = _items[indexPath.row];
    NSString* cellIdentifier = (item.read ? @"Read" : @"Unread");
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier forIndexPath:indexPath];
    cell.textLabel.text = item.title;
    cell.detailTextLabel.text = item.feed;
     
    return cell;
}

- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    return YES;
}

- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        [_items removeObjectAtIndex:indexPath.row];
        [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
    } else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view.
    }
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPad) {
        NewsItem* item = _items[indexPath.row];
        self.detailViewController.detailItem = item;
    }
}

@end
