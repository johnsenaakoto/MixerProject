# Mixer app
This is the repo for the student led app dev course, offered via CodePath.
Original App Design Project - README Template
===

# MIXER

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
**Mixer** is an android app that allows users to view the latest cocktails and recipes to the drinks. Users can create a favorites drink list. API to use: https://www.thecocktaildb.com/api.php
Contributer: **John Sena Akoto**, **Karl Devlin**

### App Evaluation
- **Category:** Food and Drink
- **Mobile:** Mobile is essential for quick access to recipes for cocktails. Also, at the club/bar you can look up popular drinks and order them.
- **Story:** Helps people find popular drinks to order at the bar or make themselves at home.
- **Market:** College students, and any young adults over the age of 21.
- **Habit:** Young adults are making their own cocktails at their kickbacks, game nights and date nights at home
- **Scope:** V1 would allow users to access popular cocktails and recipes. V2 would incorporate users making and submitting custom cocktail recipes

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User can log in and out using Parse authentication
* User can sign up using Parse Authentication
* User can see a recyclerlist of popular cocktails (drink name, picture, overview) in main activity
* User can expose details of a cocktail in a separate activity (detail activity)
* User can navigate between tabs (stream and favorites)

**Optional Nice-to-have Stories**

* User can delete drinks in favorite list
* In MainActivity, user can click on random drink button to reveal cocktail of the day
* User can create and post custom cocktails specific to their id
* User can favorite drinks in mainactivity and detail activity
* User can expose favorite drinks recyclerlist in a separate activity (favorite activity)

### 2. Screen Archetypes

* Login/Register Screen
   * User can log in and out using Parse authentication
   * User can sign up using Parse Authentication

* Stream Screen
   * User can see a recyclerlist of popular cocktails (drink name, picture, overview) in stream activity
   * User can favorite drinks in stream activity
   * User can expose details of a cocktail in a separate activity (detail activity)
   * User can expose favorite drinks recyclerlist in a separate activity (favorite activity)

* Detail Screen
   * User can favorite drink in detail activity
   * User can see details of recipe
   * User can expose favorite drinks recyclerlist in a separate activity (favorite activity)
   * User can navigate to stream activity

* Favorites Screen
   * User can see a recyclerlist of favorited cocktails (drink name, picture, overview) in favorite activity
   * User can navigate to stream activity

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Stream
* Favorites

**Flow Navigation** (Screen to Screen)

* Login/Register
   * Stream
* Stream
   * Detail
   * Favorites
* Detail
   * Stream
   * Favorites
* Favorites
   * Detail
   * Stream

## Wireframes
[Add picture of your hand sketched wireframes in this section]
<img src=Wireframes.png width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]
### Models
[Add table of models]
| Property	| Type	| Description |
| Image	| File	| Image of cocktail |
| Ingredients	| Array	| List of ingredients pertaining to cocktail |
| Name	| String	| Name of cocktail |
| Description	| String	| Description of cocktail | 
| Type	| String	| Type of cocktail |
| IsAlcoholic	| Boolean |	True if the cocktail has alcohol |
| Username | Pointer to user | The username of the user |
| Password |	Pointer to user |	The password of the user |
| Favorite |	Boolean	| True if cocktail is a favorite |
### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='walkthrough.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).
