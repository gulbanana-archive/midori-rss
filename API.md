Client-server API
=================
MidorI clients communicate with the server by JSON RPC. Each endpoint defined under `/api/` accepts a request object with various combinations of keys and returns a result in a fixed format.

### `/api/news` ###
Request: ```json
{
    read: boolean,
    from: date,
    to: date
}
```All parameters optional. Returns news items in the following format: ```json
{
}
```