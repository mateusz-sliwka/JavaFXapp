# Szkoła jazdy

Aplikacja wykonana w ramach realizacji kursu Bazy Danych 2 na Politechnice Wrocławskiej. Program z interfejsem desktopowym stworzony w języku Java z wykorzystaniem JavaFX, Hibernate oraz Maven. Całośc opiera się na bazie danych MySQL hostowanej na zdalnym serwerze z którym połączona jest aplikacja. Plik *baza.sql* zawiera skrypt .sql stworzony w celu postawienia bazy danych i uzupełnienia jej początkowymi danymi razem z uwzględieniem wszystkich działan na bazie powstałych podczas testowania aplikacji. 
Skład grupy: *Mateusz Śliwka*, *Miłosz Stolarczyk*

## Instalacja i używanie aplikacji
Najpierw należy zaimportować dołączoną bazę danych (*baza.sql*) do bazy MySQL. Następnie w pliku *persistence.xml* należy podać dane połączenia, po pomyślnym imporcie można przejść do uruchamiania programu.
Aplikację uruchomić można na trzy sposoby:
* niezależny plik **Aplikacja.jar**, która zawiera w sobie wszystkie wymagane biblioteki (**java -jar Aplikacja.jar**)
* otwierając powyższe repozytorium jako projekt i kompilując klasę **NewMain.java**
* przy pomocy komendy **mvn exec:java** wykonanej na projekcie