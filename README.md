# Project 3 - *SimpleTweet*

**Simple tweet** is an app that using the [Twitter API](https://developer.twitter.com/en/docs/twitter-api/early-access) serves a Twitter client that makes GET and POST requests, in particular SimpleTweet shows the user a simplified version of his Twitter timeline and allows him to compose and reply a Tweet.

This app was developed using the Twitter API [https://developer.twitter.com/en/docs/twitter-api/early-access](https://developer.twitter.com/en/docs/twitter-api/early-access)

Submitted by: **Jose Angel Del Angel**

Time spent: **24** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can sign in to Twitter using OAuth login
* [x] User can view the tweets from their home timeline
* [x] User can Logout 
* [x] User can compose a new tweet
* [x] User can pull to refresh, view character count and embed images

The following **bonus** features are implemented:

* [x] Improve the user interface and theme the app to feel "twitter branded" with colors and styles (1 to 5 points)
* [x] When any background or network task is happening, user sees an indeterminate progress indicator (1 point)
* [x] User can "reply" to any tweet from their home timeline (1 point)
* [ ] User can click on a tweet to be taken to a "detail view" of that tweet (2 points)
* [ ] User can take favorite (and unfavorite) or reweet actions on a tweet
* [x] User can view more tweets as they scroll with Endless Scrolling. Number of tweets is unlimited. (2 points)
* [ ] Compose activity is replaced with a modal overlay (2 points)
* [x] Links in tweets are clickable and will launch the web browser (see autolink) (1 point)
* [ ] Replace all icon drawables and other static image assets with vector drawables where appropriate. (1 point)
* [ ] User can view following / followers list through any profile they view. (2 points)
* [ ] Apply the View Binding library to reduce view boilerplate. (1 point)
* [ ] Experiment with fancy scrolling effects on the Twitter profile view. (2 points)
* [x] User can open the twitter app offline and see last loaded tweets persisted into SQLite (2 points)

None **additional features** were implemented

## Video Walkthrough

Here are walkthroughs of implemented user stories:

<img src= 'walkthrough3.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

In this gif we can see that the user is able to:
* Sign in to Twitter using OAuth login
* User can view the tweets from their home timeline
* User can pull to refresh and embed images
* Links in tweets are clickable and will launch the web browser

<img src= 'walkthrough.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

In this gif we can see that the user is able to:
* User can pull to refresh and embed images
* User reply a tweet and view character count
* User can compose a new tweet and view character count
* When any background or network task is happening, user sees an indeterminate progress indicator
* User can Log out
* ser can view more tweets as they scroll with Endless Scrolling

<img src= 'walkthrough2.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

In this gif we can see that the user is able to:
* User can open the twitter app offline and see last loaded tweets

GIF created with [LiceCap](https://www.cockos.com/licecap/).

## Challenges when developing the app

For this assignment in order to acomplish some of the required stories and many of the bonus features we were required to make individual research, some of the features that represented a challenge where the following:

* Understanding the REST model, the template itself and how the Async requests 
* Understanding the Twitter API's response structure in order to find the media object (image URL)
* Undertanding the way in wich POST requests are made to Twitter API to post a Twitter request

## License

    Copyright 2021 Jose Angel Del Angel Dominguez

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
