Football News
This application is used to get the latest football news feeds and game statistics.
1.	Make screen gives the news feed. Clicking on the newsfeed, opens the news feed in browser
2.	Click on the Navigation drawer on the top left corner which contains additional features.
3.	Click on the standings to get the latest standings of the league
4.	Click on results to get the results
5.	Click on the live scores to get the live scores.
Application details:
?	In this application, the navigation drawer is used to give additional functionalities to the application and for the ease of use.
?	In this application, the MainActivity is divided into various fragments like the HomeFragment which displays the sports newsfeed, the LiveScoreFragment which displays the live score, the ResultsFragment which displays the results of the matches and StandingsFragment displays the current standings of the league.
?	This application makes use of the AsyncTask, which fetches data from external API’s in the doInBackground method and display the required content on the screen in the onPostExecute method.
?	In this application HTTPClient is used to make network connections, which fetches the data from the external API’s in JSON format. The JSON data is then displayed on the views.
?	This application makes use of custom colors, dividers, list adapters and many more.


