# News App
It's simple kotlin news app that parses and shows artilces from [NewsApi](https://newsapi.org).
It persists downloaded articles and source in database via Room. 
List of articles has endless scrolling, so you could read as much as you can.

![](app/src/main/res/drawable-xxxhdpi/ic_launcher_rounded.png)

## Compatibility
* **Minimum Android SDK**: requires a minimum API level of 16.
* **Compile Android SDK**: requires you to compile against API 28.

## Built With
* [Dagger](https://google.github.io/dagger/android) - Dependency injection
* [Glide](https://github.com/bumptech/glide) - Image loading framework
* [Room](https://developer.android.com/topic/libraries/architecture/room) - SQLite wrapper
* [Retrofit](https://square.github.io/retrofit/) - HTTP client
* [GSON](https://github.com/google/gson) - JSON serialization/deserialization library
* [RxJava](https://github.com/ReactiveX/RxJava) - Reactive extensions
* [threetenabp](https://github.com/JakeWharton/ThreeTenABP) - Date and time API backport for android
* [mockito](https://github.com/mockito/mockito) - Framework for Unit Testing
* [mockito-kotlin](https://github.com/nhaarman/mockito-kotlin) - Wrapper for Mockito

## Tests

Unit tests:
```
./gradlew test
```

## Authors
Vadim Mezentsev
