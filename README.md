#Task - rss. Entrance exercise for hiring

## Exercise text

You need to write a news aggregator. The user submits the address of the news site or its RSS feed and the parsing rule (the format of the rule is at the discretion of the developer). The aggregator database is automatically updated with news from this site. The user has the opportunity to view a list of news from the database and search for them by substring in the heading of the news. As an example, you need to add any two news sites to choose from. The result is the source code of the aggregator, as well as the working addresses and parsing rules that can be submitted to it at the input. The language is Java. Storage - any relational database. A plus would be to use of ORM Hibernate.

## For local use
To package the project, you have to open a command line, go to the project folder and execute the command:
    
    mvn clean install
    
After the conversation, you have to open the command line, go to the project folder and execute uber jar with the command:

    java -jar target/rss_test-0.0.1-SNAPSHOT.jar

## Using it

### Adding new rule

Open your browser and go to the page: http://localhost:8080/ and click new subscription button.

![main page](src\main\resources\docs\main_page.png "page screen")

Than you have to add new or choose existing conversion rule.

![choose conv](src\main\resources\docs\choose_conv_rule.png "page screen")

if you want to add new rule, you have to populate it by extra info.

![new conv](src\main\resources\docs\new_conv_rule.png "page screen")

Than type the link to rss and choose your conversation rule.

![Add sub](src\main\resources\docs\add_sub.png "page screen")
 
Wait about two minutes for the aggregator to load your news.

###Finding a news

To find the news you are interested in, type its part and click "search"

![news search](src\main\resources\docs\search.png "page screen")