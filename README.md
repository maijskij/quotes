# Quotes App

Android app that allows users to view quotes at FavQs (https://favqs.com)

Implemented use cases:
- As a user, I want to see a random quote when I open the app.
- As a user, I want to be able to browse through a list of public quotes.
- As a user, I want to be able to search through public quotes.
- As a user, I want to see the contents and author of any displayed quote.
- As a user, I want to be able to log in with my existing FavQs account.
- Optional: As a user, I want to be able to tap on any displayed quote to see its details.
- Optional: As a user, I want to see the tags of any displayed quote.


### Setup
 - Add your FavQs API key(https://favqs.com/api_keys) to `local.gradle`. See `local.gradle.example` for details.
 - Run the build on the latest Android Studio.

### Limitations

- Quotes list displays the first page of all quotes. Pagination is not implemented.
- Login functionality allows to enter Login/Password and obtain the token from the APIs. App does not use the token for other API calls.

