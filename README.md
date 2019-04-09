# Android GitHub Application

Simple Android application for browsing GitHub. Features:

- Screens: 
    * List of all public repositories
    * Single repository details
    * List of repository stargazers 
    * List of repository contributors

## Important
Authentication is not yet implemented. For unauthenticated requests, the rate limit allows for up to 60 requests per hour. Unauthenticated requests are associated with the originating IP address, and not the user making requests.


## Installation
Clone this repository and import into **Android Studio**
```bash
git clone https://github.com/SasaJug/github-app.git
```

## Maintainers
This project is maintained by:
* [Sasa Jugurdzija](http://github.com/SasaJug)

## ToDo
1. Implement 25 results per page for repository list.
2. Implement authentication.
3. Add tests.

## Contributing

1. Fork it
2. Create your feature branch (git checkout -b my-new-feature)
3. Commit your changes (git commit -m 'Add some feature')
4. Run the linter (ruby lint.rb').
5. Push your branch (git push origin my-new-feature)
6. Create a new Pull Request
