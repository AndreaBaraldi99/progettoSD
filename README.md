# Progetto Sistemi Distribuiti 2022-2023

Lo scheletro propone degli esempi dei tre componenti da cui partire.

## Componenti del gruppo

* Andrea Baraldi (839452) <a.baraldi3@campus.unimib.it>
* Lucrezia Penati (886092) <l.penati9@campus.unimib.it>

## Compilazione ed esecuzione

Il server Web e il database sono dei progetti Java che utilizano Maven per gestire le dipendenze, la compilazione e l'esecuzione. È necessario eseguire in seguente i seguenti obiettivi per compilare ed eseguire: `clean`, che rimuove la cartella `target`, `compile` per compilare e `exec:java` per avviare il
componente.

I tre obiettivi possono essere eseguiti insieme in una sola riga di comando da terminale tramite `./mvnw clean compile exec:java` per Linux/Mac e `mvnw.cmd clean compile exec:java` per Windows. L'unico requisito è un'istallazione di Java (almeno la versione 11), verificando che la variabile `JAVA_PATH` sia correttamente configurata.

Si può anche utilizzare un IDE come Eclipse o IntelliJ IDEA, in tal caso va configurato (per Eclipse si possono seguire le istruzioni mostrate nelle slide del laboratorio 5 sulle Servlet).

Il client Web è invece un solo file HTML chiamato `index.html`, può essere aperto su un qualsiasi browser. È importante disabilitare CORS, come mostrato nel laboratorio 8 su JavaScript (AJAX).

## Porte e indirizzi

Il server Web si pone in ascolto all'indirizzo `localhost` alla porta `8080`. Il database si pone in ascolto allo stesso indirizzo del server Web ma alla porta `3030`.

## Note del gruppo

Abbiamo notato che, per il file che contiene gli esempi di partenza dei film, abbiamo un comportamento differente in base all'IDE che si utilizza: il progetto è settato per funzionare
su eclipse e tramite riga di comando ma se il progetto viene aperto con Visual Studio Code, il file va spostato all'interno della cartella 'database' sotto 'finalproject'
altrimenti il sistema non lo rileverà.

Sul progetto: come da screenshot, alcuni dati dei film richiesti sono all'interno della pagina di prenotazione che si raggiunge cliccando l'orario del film che si desidera prenotare
