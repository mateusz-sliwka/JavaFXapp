# Bazy danych 2 [projekt] - szkoła jazdy

Aplikacja wykonana w ramach realizacji kursu Bazy Danych 2 na Politechnice Wrocławskiej. Program z interfejsem desktopowym stworzony w języku Java z wykorzystaniem JavaFX, Hibernate oraz Maven. Całośc opiera się na bazie danych MySQL hostowanej na zdalnym serwerze z którym połączona jest aplikacja. Plik *baza.sql* zawiera skrypt .sql stworzony w celu postawienia bazy danych i uzupełnienia jej początkowymi danymi razem z uwzględieniem wszystkich działan na bazie powstałych podczas testowania aplikacji. 
Skład grupy: *Mateusz Śliwka*, *Miłosz Stolarczyk*

## Instalacja i używanie aplikacji

Aplikację uruchomić można na trzy sposoby:
* niezależny plik **SzkolaJazdy.jar**, która zawiera w sobie wszystkie wymagane biblioteki
* otwierając powyższe repozytorium jako projekt i kompilując klasę **NewMain.java**
* przy pomocy komendy **mvn exec:java** wykonanej na projekcie