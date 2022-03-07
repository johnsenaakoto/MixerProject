# AndroidUProject
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
[Description of your app]

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category:**
- **Mobile:**
- **Story:**
- **Market:**
- **Habit:**
- **Scope:**

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

* Main Screen with a list of cocktails
   * [list associated required story here]
   * ...
* 
   * [list associated required story here]
   * ...

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
<img src="YOUR_WIREFRAME_IMAGE_URL" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]
### Models
[Add table of models]
### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
