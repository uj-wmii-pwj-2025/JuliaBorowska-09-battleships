### URUCHOMIENIE <br>
* Należy w terminalu przejść do folderu: `JuliaBorowska-09-battleships`
* Kompilacja Main.java: `javac -d run battleship/**/*.java`
* Kompilacja pozostałych plików:`javac -d run battleship/*.java`
* Uruchomienie: `java -cp run battleship.Main -mode [server|client] -port N -timeout time`

### PARAMETRY URUCHOMIENIOWE <br>
* `-mode [server|client]` - wskazuje tryb działania (jako serwer lub jako klient)
* `-port N` - port, na którym aplikacja ma się komunikować
* `-host hostName` - tylko w trybie klienta: nazwa hosta (`localhost` lub adres IP w sieci lokalnej, np. `192.168.1.121`)
* `-map map-file` - `random` lub ścieżka do pliku zawierającego mapę z rozmieszczeniem statków (*opcjonalnie, domyślnie losowa plansza*)
* `-timeout time` - czas oczekiwania na odpowiedź przeciwnika w sekundach (*opcjonalnie, domyślnie: 1s*)

### MAPA <br>
Jeżeli uda się wczytać mapę z podanej ścieżki to ona jest używana w grze.
Jeżeli wczytywanie się nie powiedzie, nie podano tego parametru lub podano wartość `random` generowana jest losowa mapa.

### STRUKTURA PROJEKTU <br>
`JuliaBorowska-09-battleships` <br>
├── battleship <br>
│   ├── Main.java <br>
│   │ <br>
│   ├── game <br>
│   │   ├── Board.java <br>
│   │   ├── Game.java <br>
│   │   ├── GameStatus.java `ENUM` <br>
│   │   ├── ShotResult.java `ENUM` <br>
│   │   ├── TurnLoop.java <br>
│   │   ├── TurnStatus.java `ENUM` <br>
│   │<br>
│   ├── network <br>
│   │   ├── Server.java<br>
│   │   ├── Client.java<br>
│   │<br>
│   ├── protocol <br>
│   │   ├── Command.java `ENUM` <br>
│   │   ├── CoordsParser.java <br>
│   │   ├── Message.java <br>
│   │   ├── MessageIO.java <br>
│   │   ├── ShotInput.java <br>
│   │<br>
│   └── utils <br>
│       ├── Arguments.java <br>
│       ├── BoardsRender.java <br>
│       ├── ConsoleRender.java <br>
│       ├── EndGameRender.java <br>
│       ├── Field.java <br>
│       ├── FieldStatus.java `ENUM` <br>
│       ├── GameMode.java `ENUM` <br>
│       ├── MapGenerator.java <br>
│       ├── MapLoader.java <br>
│       ├── Ship.java <br>
