# Assignement 4 - *Shouter App* with fragments.

*ShouterApp* is an android app that fetches tweets from timeline. This app can also be used to post a tweet to the timeline. Provision to retwet, like or reply to tweets are also provided. It now uses fragments to show user timeline, home timeline and mentions. Also, user can now switch between Timeline and Mentions views using tabs

Submitted by: **Nayan Kumar K**

Time spent: 12 hours spent in total

Completed user stories:

* [x] [Required] [New] User can switch between Timeline and Mention views using tabs.
* [x] [Required] [New] User can view their home timeline tweets.
* [x] [Required] [New] User can view the recent mentions of their username.
* [x] [Required] [New] User can navigate to view their own profile
* [x] [Required] [New] User can see picture, tagline, # of followers, # of following, and tweets on their profile.
* [x] [Required] [New] User can click on the profile image in any tweet to see another user's profile.
* [x] [Required] [New] User can see picture, tagline, # of followers, # of following, and tweets of clicked user.
* [x] [Required] [New] Profile view should include that user's timeline
* [x] [Required] [New] User can infinitely paginate any of these timelines (home, mentions, user) by scrolling to the bottom
* [x] [Required] User can sign in to Twitter using OAuth login (2 points)
* [x] [Required] User can view the tweets from their home timeline
* [x] [Required] User should be displayed the username, name, and body for each tweet (2 points)
* [x] [Required] User should be displayed the relative timestamp for each tweet "8m", "7h" (1 point)
* [x] [Required] User can view more tweets as they scroll with infinite pagination (1 point)
* [x] [Required] User can compose a new tweet
* [x] [Required] User can click a “Compose” icon in the Action Bar on the top right (1 point)
* [x] [Required] User can then enter a new tweet and post this to twitter (2 points)
* [x] [Required] User is taken back to home timeline with new tweet visible in timeline (1 point)
* [x] [Advanced] [New] Robust error handling, check if internet is available, handle error cases, network failures
* [x] [Advanced] [New] When a network request is sent, user sees an indeterminate progress indicator
* [x] [Advanced] While composing a tweet, user can see a character counter with characters remaining for tweet out of 140 (1 point)
* [x] [Advanced] Links in tweets are clickable and will launch the web browser (see autolink) (1 point)
* [x] [Advanced] User can refresh tweets timeline by pulling down to refresh (i.e pull-to-refresh) (1 point)
* [x] [Advanced] User can open the twitter app offline and see last loaded tweets
* [x] [Advanced] Tweets are persisted into sqlite and can be displayed from the local DB (2 points)
* [x] [Advanced] User can select "reply" to respond to a tweet (1 point)
* [x] [Advanced] Improve the user interface and theme the app to feel "twitter branded" (1 to 5 points)
* [x] [Bonus] User can see embedded image media within the tweet detail view (1 point)
* [x] [Bonus] Compose activity is replaced with a modal overlay (2 points)
* [x] [Bonus] Apply the popular Butterknife annotation library to reduce view boilerplate. (1 point)
* [x] [Bonus] Leverage the popular GSON library to streamline the parsing of JSON data. (1 point)
* [x] [Bonus] Leverage RecyclerView as a replacement for the ListView and ArrayAdapter for all lists of tweets. (2 points)
* [x] [Bonus] Move the "Compose" action to a FloatingActionButton instead of on the AppBar. (1 point)
* [x] [Bonus] Replace Picasso with Glide for more efficient image rendering. (1 point)

Walkthrough of all user stories:

![Video Walkthrough](demo.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## License


    Copyright [2016] [Nayan Kumar K]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
