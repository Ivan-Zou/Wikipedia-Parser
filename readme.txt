Answers: 
- Question 1 - 
	Discontinued Categories: 
	- Best Assistant Director
	- Best Director, Comedy Picture
	- Best Director, Dramatic Picture
	- Best Dance Direction
	- Best Original Musical or Comedy Score
	- Best Original Story
	- Best Short Subject – 1 Reel
	- Best Short Subject – 2 Reel
	- Best Short Subject – Color
	- Best Short Subject – Comedy
	- Best Short Subject – Novelty
	- Best Sound Editing
	- Best Title Writing
	- Best Unique and Artistic Production
Question 2 - 
	- 5 award categories were added in the 1940s
Question 3 -
	Movies nominated for at least 4 in 2022: 
	- Elvis
	- TÃr
	- Top Gun: Maverick
	- The Fabelmans
	- Avatar: The Way of Water
	- Black Panther: Wakanda Forever
	- The Banshees of Inisherin
	- All Quiet on the Western Front
	- Everything Everywhere All at Once
Question 4 -
	- The winner of Best Animated Feature in 2016 is Zootopia!
Question 5 -
	- The winner of Best Original Screenplay in 2022, Everything Everywhere All at Once,
	  has a budget of $14.3–25 million, and made $133.2 million in box office sales!
Question 6 -
	- The academic institution with the highest number of alumni nominated for 
	  Best Actress is Yale University!!!
Question 7 -
	For the countries that were nominated for/won Best International Feature Film: 
	- Cyprus was nominated 1 times!
	- Kazakhstan was nominated 1 times!
	- Iceland was nominated 1 times!
	- Greece was nominated 5 times!
	- Austria was nominated 4 times!
	- Mongolia was nominated 1 times!
	- Iran was nominated 1 times!
	- Luxembourg was nominated 1 times!
	- Brazil was nominated 4 times!
	- Czechoslovakia was nominated 4 times!
	- Chile was nominated 1 times!
	- Argentina was nominated 4 times!
	- Hungary was nominated 5 times!	
	- Japan was nominated 12 times!
	- West Germany was nominated 9 times!
	- India was nominated 1 times!
	- Canada was nominated 4 times!
	- Turkey was nominated 3 times!
	- Belgium was nominated 8 times!
	- Finland was nominated 1 times!
	- North Macedonia was nominated 1 times!
	- Italy was nominated 16 times!
	- South Africa was nominated 1 times!
	- Poland, USSR was nominated 1 times!
	- Germany was nominated 19 times!
	- United States was nominated 2 times!
	- Russia was nominated 6 times!
	- Netherlands was nominated 4 times!
	- Sweden was nominated 17 times!
	- China was nominated 2 times!
	- Qatar was nominated 1 times!
	- Ireland was nominated 1 times!
	- Poland was nominated 10 times!
	- Estonia, Georgia was nominated 1 times!
	- France was nominated 40 times!
	- Bulgaria was nominated 1 times!
	- Jordan was nominated 1 times!
	- Tunisia was nominated 1 times!
	- Bosnia and Herzegovina was nominated 1 times!
	- Bhutan was nominated 1 times!
	- Romania was nominated 2 times!
	- United Kingdom was nominated 4 times!
	- United Arab Emirates was nominated 1 times!
	- Switzerland was nominated 3 times!
	- Spain was nominated 19 times!
	- Palestine was nominated 1 times!
	- Lebanon was nominated 2 times!
	- Czech Republic was nominated 2 times!
	- Hungary, West Germany, Austria was nominated 1 times!
	- Mauritania was nominated 1 times!
	- Norway was nominated 8 times!
	- Denmark was nominated 12 times!
	- Mexico was nominated 7 times!
	- Israel was nominated 9 times!
	- Soviet Union was nominated 7 times!
	- Montenegro was nominated 1 times!

Question 8 - For everyone who was ever nominated for __Best_Actor__, how many nominees were born in __November__?
	- For those who were nominated for Best Actor, 28 were born in November!

How to Run Program:
	- I used Jsoup, so download it if haven't already.
	- Run the Questions Class.
	- Enter a number from 1 to 8 to select a question.
	- Enter inputs corresponding to the question selected.
	- Enter Y to answer another question. Enter anything else to end the program.

Algorithms:
- Question 1 - 
	- Starting from the Academy Awards wiki page, go to the Discontinued Categories heading by using the id 
	  "Discontinued_categories". Then, go the list under this heading, and get all the items (discontinued categories)
	  in this list. Finally, iterate through all list items, and only print out the name of the discontinued category.
- Question 2 - 
	- Starting from the Academy Awards wiki page, go to the Current Categories heading by using the id 
 	  "Current_categories". Then, go to the table under this heading. In this table, go to the column of the year
	  the current categories were introduced. Iterate through this column, and for each year, check to see if it is
	  within the decade input. If it is, then we increment count, which keep track of the number of categories added.
	  Next, initialize a hash map to map a discontinued category to the year its introduced. Get the list items under
	  the discontinued categories heading in the same way as Question 1. Iterate through the list items of discontinued
 	  categories. For each category, get the its date (that is after :). If the categorie's start and end date is in 
	  the within a year or decade of each other, then we ignore that category. Otherwise we add this category to our map.
	  with the value of its year introduced. Then, we iterate through the map, and get the year introduced of each category
	  in this map. If the year introduced is within the decade input, then increment count. After everything print the
	  results.
- Question 3 - 
	- Starting on the Academy Awards wiki page, get the ceremony's wiki page from the year input. We get this ceremony's
	  wiki page by going into the Academy Awards tab at the very bottom of the Academy Awards wiki page, and then go
	  to the Ceremonies section using the id "fn_‡_back", then get the list of all the years from the Ceremonies
	  section. After that, get the link of the ceremony corresponding to the input year by iterating through the list of
	  years until we find the year matching the input year, and then get the URL of the ceremony from the matching
	  year. After getting the ceremony URL, get the wiki page from this URL. Now from the ceremony of the input year's
	  wiki page, go to the awards table, get all the movies in this table using the fact that all the movies are
	  italicized. Initialize a map tracking the frequency a movie appears in the awards table. Iterate through all the
	  movies, and for each movie increment its frequency in the map. After that, iterate through the map, if the frequency
	  of which the movie appeared in the awards table is at least the numAwards input, then we print this movie.
- Question 4 - 
	- Starting from the Academy Awards wiki page, go to the ceremony's wiki page from the year input in the same way 
	  as Question 3. Then, in the year input's ceremony page, go to the awards table. In the awards table, find the
	  award matching the award category input. Then, from this award, we get the winner element and print out the name.
- Question 5 -
	- Starting from the Academy Awards wiki page, get the winner of the award category in the year input using the method
	  used in Question 4 to get the winner. From the winner element, get the URL of the wiki page associated with this 
	  winner, and go into this wiki page. From the winner's wiki page, go to its info box and go to the heading "budget"
	  and "box office". From there, get the numbers associated with those two headings and print them out.
- Question 6 - 
	- Starting from the Academy Awards wiki page, get the URL to the award wiki page of the award category input. We do this by 
	  going into the Current Categories heading using its id, get the table under this heading, look in second column
	  with all of the current award categories and get the category that matches the category input. From the category,
	  get the URL associated with it. With the URL, go to the award wiki page, and go to the winner and nominees heading.
	  Then, under the winner and nominees heading, get the each table that is under a decade heading and put them in a 
	  linked list. Iterate through each of the tables in the list, and in each row of the first column of each table, get the
	  URL in that cell and put the URLs in a list. Now we have a list of URLs to the wiki pages of the nominees of the 
	  category input. Initialize a map to track the number of nominees that have attended the academic insitution. Next,
	  iterate through the list of nominee URLs. For each URL, go to the nominee's wiki page, go to their info box, get the
	  academic institution they went to by finding the heading "Education" or "Alma mater", and update the map accordingly. 
	  After going to all the nominee's wiki page in the list, we iterate through the map and find the academic institution with
	  the largest number of nominees. After finding that academic institution, we print it out.
- Question 7 -
	- Starting from the Academy Awards wiki page, go to the awards wiki page of the input category, go to the winner and nominees heading,
	  and get all the table of nominees under a heading of a decade and put them in a list in the same way as Question 6. If there are no 
	  nominee tables under the winner and nominees heading, then we know that there is at least a URL, so we'll get that URL and 
	  go into the wiki page that URL links to. Do the same thing in that URL, and go to the winner and nominees heading and get 
	  all the tables that are under a decade heading that are all under the winner and nominees heading and put the tables into
	  a list. Now that we have a list of nominee tables, get all the URL to the nominee's wiki pages and put them in a list in the
	  same way as Question 6. Initialize a map to track the number of times a country is linked to a nominee. Next, iterate through
	  the list of nominee URLs. For each URL, go into the nominee's wiki page, go to their info box, go the heading "country" or 
	  "countries" in the info box and get the country/countries in these headings, and update the map accordingly. After going through
	  all wiki pages of the nominees in the list, we iterate through the map and print the name of the country and number of movie nominees
	  that country is linked to.
- Question 8 -
	- Starting from the Academy Awards wiki page, get the award URL of the category input, and get the award's wiki page in the 
	  same way as Question 6. Next, initialize a hash map to track the number of nominees that were born in each of the 12 months. In
	  the award's wiki page, go to the winners and nominees heading, and get the nominees tables under it in the same way as
	  Question 6 and put them in a list. Then, from the list of tables, get the nominee URLs and put them in a list in the same way as
	  Question 6. Iterate through the list of URLs. For each URL, get the wiki page it links to. In the nominee's wiki page, go to
	  its info box, and in the info box, go to the "Born" heading and get all the contents in there. Iterate through the map of the 12 
	  months, if a month is in the contents of under the "Born" heading, then we update the map by incrementing the value for that month.
	  After going through all the URLs, we get the integer value of the birthMonth input in the map, and print the results. 

Struggles: 
	- It was very much a struggle starting to code with Jsoup, but after watching many YouTube tutorials on Jsoup, I
	  slowly got the hang of it. :D

I have repeated code in my print academic institution and my print num of nominees for countries method. This is necessary
because in the print countries method, if there are no nominee tables, then I need to enter a URL and do the same thing
again, but inorder to enter the URL I need winnerAndNomineesHead to get the URL, so anything up to that heading I put into
a method. 

Assumptions:
- General - 
	- Only the earliest year is considered for the year values that represent two years.
	  (e.g. for 1929/30, only 1929 is considered)
	- The Ceremonies row with the links to all the award ceremonies in the Academy Awards table at the very bottom of 
	  the page is accessed with the ID "fn_‡_back"
	- from how I get the Wiki page using Jsoup, there will be odd symbols printed out in place of unsupported symbols.
	  How I get the wiki pages allows Jsoup to connect to URLs with symbols like %, so it is necessary.
- Question 1 - 
	- discontinued special categories are not included
- Question 2 - 
	- Only current award categories and discontinued categories are considered.
	- for the discontinued category, categories that were discontinued within a year or decade are not counted
- Question 3 - 
	- ceremony pages are formatted similarly.
- Question 4 - 
	- the name changes of award categories are not accounted for
	- if you want to get the winner of an award category in a given year, then the category input must the be the 
	  name of that award category used in that given year.
- Question 5 -
	- the name changes of award categories are not accounted for
	- if you want to get the winner of an award category in a given year, then the category input must the be the 
	  name of that award category used in that given year.
	- For the budget or box office that have a range, the dashed line is not properly printed because of how I use
	  Jsoup to get the movie's wiki page. This method of getting the movie is necessary to handle URLs with symbols like %.
	  So I assume that this is fine. The dashed lines are printed as â followed by 2 rectangles.
	- only considers movies with BOTH budget and box office information listed in the movie's info box in their wiki page.
- Question 6 - 
	- All pages are formatted the same as the page relevant to the question
	- only current award categories are considered.
	- only consider nominees with URLs in the award page.
	- only consider academic institution of alumni nominees with the academic institution list in their infobox 
	  in their Wikipedia page (that are under "education" or "alma mater").
	- only consider academic institutions with URLs in the info boxes.
	- all academic institutions have names greater than a length of 5 (valid because words like "school" and 
	  "college" have length greater than 5).
	- I only output one academic institution. If there are ties, only one will be printed.
- Question 7 - 
	- only current award categories are considered.
	- only consider movies with URLs in the award page
	- only consider countries of movies with their country listed in the movie's info box in their Wikipedia page
	  (that are under "country" or "countries").
	- pages are formatted somewhat similarly
- Question 8 -
	- All award pages are formatted similarly.
	- only current categories are considered
	- only people are considered
	- only nominees with URLs in the award page are considered
	- only consider birth months of nominees that have their birth months listed in their infobox in their Wikipedia page
	  (that are under "born")
	- the nominees do not have a name of a birth month or are from somewhere with a name of a birth month.
 	


