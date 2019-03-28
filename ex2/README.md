# Cel zadania
Celem zadania jest implementacja rozproszonej tablicy haszuj�cej. Aplikacje z niej korzystaj�ce powinny mie� mo�liwo�� dodawania element�w do tablicy i jednocze�nie pobierania element�w wcze�niej dodanych, by� mo�e r�wnie� przez inne aplikacje.

W wyniku realizacji zadania powinna powsta� implementacja klasy DistributedMap, implementuj�ca interfejs:

public interface SimpleStringMap { \
    &nbsp;&nbsp;&nbsp;&nbsp;boolean containsKey(String key); \
    &nbsp;&nbsp;&nbsp;&nbsp;Integer get(String key); \
    &nbsp;&nbsp;&nbsp;&nbsp;void put(String key, Integer value); \
    &nbsp;&nbsp;&nbsp;&nbsp;Integer remove(String key); \
}

 

Powinna te� zosta� opracowana przyk�adowa aplikacja korzystaj�ca z rozproszonej tablicy haszuj�cej. Funkcjonalno�� aplikacji powinna umo�liwia� interaktywn� interakcj� i eksponowa� metody zawarte w interfejsie implementowanej tablicy.

# W�asno�ci rozproszonej tablicy
Uwzgl�dniaj�c teori� CAP , implementacja rozwi�zania powinna cechowa� si� dost�pno�ci� i tolerowaniem partycjonowania.

W zwi�zku z tym ka�da instancja klasy DistributedMap powinna mie� w�asn� kopi� tablicy rozproszonej, a usp�jnianie stanu powinno by� zrealizowana podczas operacji dodawania element�w do tablicy. Do rozproszonej komunikacji pomi�dzy instancjami nale�y wykorzysta� bibliotek� JGroups:

- w przypadku tworzenia nowej instancji klasy DistributedMap, powinna ona pozyska� pocz�tkowy stan od cz�onk�w grupy, do kt�rej do��cza,
- w przypadku podzia�u grupy w�z��w na dwie partycje, powinny one utrzymywa� sw�j stan niezale�nie; w przypadku ponownego scalania dw�ch partycji, cz�onkowie jednej z partycji powinni straci� sw�j stan i pozyska� go na nowo od cz�onk�w drugiej z partycji.