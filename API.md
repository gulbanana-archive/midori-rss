Client-server API
=================
MidorI clients communicate with the server by JSON RPC. Each endpoint defined under `/api/` accepts a request object with various combinations of keys and returns a result in a fixed format.

### `/api/news` ###
Request: 
```javascript
{
    "limit": 10,     // number           - max news items to retrieve, most recent first
    "read": true,    // boolean          - include read news?
    "from": 0124124, // number, optional - news starting at date
    "to": 3522535    // number, optional - news up to date
}
```
Result: 
```json 
[
    {
        "title": "News item title", 
        "link": "http://feed-url/item", 
	"read": false,
        "date": 1000,
        "feed": {
            "title": "News feed name", 
            "link": "http://feed-url/"
        }
    }, 
    {
        "title": "Next item", 
        "link": "http://feed-url/item2", 
	"read": true,
        "date": 990,
        "feed": {
            "title": "News feed name", 
            "link": "http://feed-url/"
        }
    }
]
```
