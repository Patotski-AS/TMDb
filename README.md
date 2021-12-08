<h1>TMDb</h1>


TMDb is a application to show list of movies on Android, built using Kotlin with Clean Architecture
concepts and MVVM with Flow

## Screenshots

<p>
<img src="screenshots/5.png" width="24%" height="40%"/>
<img src="screenshots/4.png" width="24%" height="40%"/>
<img src="screenshots/1.png" width="24%" height="40%"/>
<img src="screenshots/3.png" width="24%" height="40%"/>
<img src="screenshots/2.png" width="40%" height="20%"/>
<img src="screenshots/6.png" width="40%" height="20%"/>
</p>

## Prerequisites

Add your [TMDB](https://www.themoviedb.org/) API key in the `local.properties` file:

```
api_key=YOUR_API_KEY
```

## Features


- Search for movies
- View movie details like release date, rating, overview
- Adding movies to favorites
- Change dark and light themes in the settings
- Works offline by caching data into a database.

## Architecture


<p>
<img src="screenshots/clean.png" width="100%" height="100%"/>
</p>

## Technologies


- [Kotlin](https://kotlinlang.org/) - %100 Kotlin
- [View Binding](https://developer.android.com/topic/libraries/view-binding)
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
- [Navigation](https://developer.android.com/guide/navigation)
- [Glide](https://github.com/bumptech/glide) for image loading
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for asynchronous operations
- [StateFlow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/)
- [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for
  Dependency Injection
- [OkHttp](https://github.com/square/okhttp) and [Retrofit](https://github.com/square/retrofit) for
  network operations
- [TheMovieDb(Tmdb) Api](https://developers.themoviedb.org/3) for movies
- Built on a Single-Activity Architecture. Every screen in the app is a fragment.
- Completely offline ready. MovieDB uses [Room](https://developer.android.com/training/data-storage/room) for managing a local SQLite  database, which means that if you have seen some content already while you were online, you won't  need an internet connection to see it again.
