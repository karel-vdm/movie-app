# movie-app

This app uses the clean architecture https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html with MVVM as well as kotlin flow and live data for handling the data flow between the data, domain and presentation layers.

The clean architecture enforces dependency inversion High-level modules should not depend on low-level modules. Both should depend on abstractions.

![110373716-7a2a4500-8058-11eb-8396-fde929432ee8](https://user-images.githubusercontent.com/80144326/111739714-2c99ad80-888c-11eb-9f53-7fd6d909fdb2.png)



More on the diffrent layers defined in clean archtecture:

Data layer: The data layer is a one to one mapping of the api. There should be no business or display logic in the data layer. The data layer is purely responseble for getting data from the defined data source.

Domain layer: The domain layer is responsible for busniss logic as well as any changes to the api defined object structures. To satisfy dependency inversion we map the data layer object to a domain layer object. The data layer should not know about any low level domain layer objects.

Presentation layer: The presentation layer is responsible for handling the display of data as well as view rendering logic. The domain layer object is mapped to a presentation layer object (ViewModel). Any changes to data for display purposes should be handled in the presentation layer.

![CleanArchitecture](https://user-images.githubusercontent.com/80144326/111739735-37ecd900-888c-11eb-8c49-0f3024cdf5ba.jpg)

Dependency injection: The app does make use of dependency injection(constructor) but not a depenency injection framework. If it was a production application I would make use of either dagger or hilt

https://developer.android.com/jetpack/androidx/releases/hilt
https://developer.android.com/training/dependency-injection/dagger-basics
