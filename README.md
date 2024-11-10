# Movie App

This project is designed to enhance Android development skills by applying best practices, the MVVM architecture, and the latest Android features. The goal is to release an app that allows users to find and manage information about movies and series.

## Project Setup

To run this project, you need to set up some environment variables in the `local.properties` file. These variables are used to connect with The Movie Database (TMDb) and YouTube APIs.

### Environment Variables

Add the following lines to your `local.properties` file, located in the root directory of the project:

```properties
MOVIEDB_TOKEN={your_token}
MOVIEDB_BASE_URL=https://api.themoviedb.org/3/
MOVIEDB_IMAGE_BASE_URL=https://image.tmdb.org/t/p/
YOUTUBE_API_KEY={your_api_key}
