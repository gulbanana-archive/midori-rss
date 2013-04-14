### check the top 2 news items ###
curl http://localhost:9000/api/news -H "Content-Type: application/json" -d '`{"limit": 2, "read": true}`'

	[{"link":"http://www.mspaintadventures.com/?s=6&p=008142","title":"04/14/13 : ==>","posted":1365923982000,"read":true,"feed":
		{"link":"http://www.mspaintadventures.com","title":"MS Paint Adventures","url":"http://www.mspaintadventures.com/rss/rss.xml"}},
	{"link":"http://www.mspaintadventures.com/?s=6&p=008141","title":"04/14/13 : ==>","posted":1365923950000,"read":true,"feed":
		{"link":"http://www.mspaintadventures.com","title":"MS Paint Adventures","url":"http://www.mspaintadventures.com/rss/rss.xml"}}]

### check the top 2 *unread* news items ###
curl http://localhost:9000/api/news -H "Content-Type: application/json" -d '`{"limit": 2, "read": false}`'

	[{"link":"http://www.scarygoround.com/?date=20130307","title":"Bad Machinery for March 7th 2013","posted":1362646480000,"read":false,"feed":
		{"link":"http://scarygoround.com","title":"Bad Machinery","url":"http://badmachinery.com/index.xml"}},
	{"link":"http://www.doublefine.com/forums/viewthread/8841","title":"\n\t\t\t\tDFA Game Club: Sam &amp; Max: Culture Shock\n\t\t\t","posted":1362594399000,"read":false,"feed":
		{"link":"http://www.doublefine.com/dfa","title":"\n\t\tDFA Table of Contents\n\t","url":"http://www.doublefine.com/dfa/dfarss/"}}] 

### mark one of the read ones as unread###
curl http://localhost:9000/api/mark -H "Content-Type: application/json" -d '`[{"feed": "http://www.mspaintadventures.com/rss/rss.xml", "item": "http://www.mspaintadventures.com/?s=6&p=008142", "read": false}]`'

	{"marked":true}

### check again and now it shows up as top-2 unread ###
sikka:MidorI banana$ curl http://localhost:9000/api/news -H "Content-Type: application/json" -d '`{"limit": 2, "read": false}`'



	[{"link":"http://www.mspaintadventures.com/?s=6&p=008142","title":"04/14/13 : ==>","posted":1365923982000,"read":false,"feed":
		{"link":"http://www.mspaintadventures.com","title":"MS Paint Adventures","url":"http://www.mspaintadventures.com/rss/rss.xml"}},
	{"link":"http://www.scarygoround.com/?date=20130307","title":"Bad Machinery for March 7th 2013","posted":1362646480000,"read":false,"feed":
		{"link":"http://scarygoround.com","title":"Bad Machinery","url":"http://badmachinery.com/index.xml"}}]sikka:test banana$ cat top2all.json 
