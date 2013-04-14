Client-server API
=================
MidorI clients communicate with the server by JSON RPC. Each endpoint defined under `/api` accepts a request object with various combinations of keys and returns a result in a fixed format.

### `/news` ###
Request: NewsRequest

	{
		"limit": 10,     // number           - max news items to retrieve, most recent first
		"read": true,    // boolean          - include read news?
		"from": 0124124, // number, optional - news starting at date
		"to": 3522535    // number, optional - news up to date
	}
	
Result: array of NewsResult

	[{
		"date": 34289824,					//unique date identifying item
		"read": false,						//whether item has been read
		"link": "http://feed-url/item", 		
		"title": "News item title", 
		"feed": {
           	"link": "http://feed-url/"
           	"title": "News feed name", 
		}
	}]

### `/mark` ###
Request: array of MarkRequest

	[{
		"date": 32424234234,	//number  - identify item by date
		"read": true			//boolean - mark as either read or unread
	}]
	
Result: MarkResult

	{ 
		"marked": true 			//false if marking failed
	}