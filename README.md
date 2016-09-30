*ShouterApp* is an android app that fetches tweets from timeline. This app can also be used to post a tweet to the timeline. Provision to retwet, like or reply to tweets are also provided. It uses fragments to show user timeline, home timeline and mentions. Also, user can now switch between Timeline and Mentions views using tabs

Completed user stories:

* User can switch between Timeline and Mention views using tabs.
* User can view their home timeline tweets.
* User can view the recent mentions of their username.
* User can navigate to view their own profile
* User can see picture, tagline, # of followers, # of following, and tweets on their profile.
* User can click on the profile image in any tweet to see another user's profile.
* User can see picture, tagline, # of followers, # of following, and tweets of clicked user.
* Profile view should include that user's timeline
* User can infinitely paginate any of these timelines (home, mentions, user) by scrolling to the bottom
* User can sign in to Twitter using OAuth login
* User can view the tweets from their home timeline
* User should be displayed the username, name, and body for each tweet
* User should be displayed the relative timestamp for each tweet "8m", "7h" 
* User can view more tweets as they scroll with infinite pagination 
* User can compose a new tweet
* User can click a “Compose” icon in the Action Bar on the top right 
* User can then enter a new tweet and post this to twitter
* User is taken back to home timeline with new tweet visible in timeline
* Robust error handling, check if internet is available, handle error cases, network failures
* When a network request is sent, user sees an indeterminate progress indicator
* While composing a tweet, user can see a character counter with characters remaining for tweet out of 140
* Links in tweets are clickable and will launch the web browser
* User can refresh tweets timeline by pulling down to refresh (i.e pull-to-refresh) 
* User can open the twitter app offline and see last loaded tweets
* Tweets are persisted into sqlite and can be displayed from the local DB
* User can select "reply" to respond to a tweet
* Improve the user interface and theme the app to feel "twitter branded"
* User can see embedded image media within the tweet detail view
* Compose activity is replaced with a modal overlay
* Apply the popular Butterknife annotation library to reduce view boilerplate.
* Leverage the popular GSON library to streamline the parsing of JSON data.
* Leverage RecyclerView as a replacement for the ListView and ArrayAdapter for all lists of tweets
* Move the "Compose" action to a FloatingActionButton instead of on the AppBar
* Replace Picasso with Glide for more efficient image rendering

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
