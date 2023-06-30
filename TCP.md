# Progetto Sistemi Distribuiti 2022-2023 - TCP

Il protocollo implementato è ispirato al protocollo di Redis, con alcune modifiche per migliorare la leggibilità. In particolare, tutti i comandi hanno la seguente struttura:

**"comando_chiave{&dati}"**

Il client web, tramite una stringa, manda quindi al database il comando che vuole eseguire seguito dal trattino basso e seguito dalla chiave dell'oggetto che si vuole recuperare dal database.
La parte dei dati è facoltativa e serve nel caso si voglia aggiungere una coppia chiave-valore al database o modificarne una già esistente. In generale, il database risponderà
sempre con una stringa dedicata al valore cercato (o valori) con un "OK" finale per garantire la fine della trasmissione dei dati o l'avvenuta esecuzione del comando.
I comandi implementati sono i seguenti:

### "GET_chiave"

**Descrizione**: il comando recupera l'oggetto nel database associato alla chiave passata tramite testo e invia il valore associato alla chiave.

### "GETBYTYPE_chiave"

**Descrizione**: il comando recupera e invia tutti gli oggetti nel database che hanno la chiave che inizia con la chiave inviata nel comando.
Serve nel caso le chiavi siano impostate in modo che ogni parte di chiave identifica qualcosa (come in questo caso).

### "POST_chiave&dati"

**Descrizione**: il comando serve per inserire nel database una nuova coppia chiave-valore. Il valore è derivato dal parametro 'dati' passati nel comando e la chiave dal parametro 'chiave'. La chiave e il relativo valore sono divisi dalla &.

### "DELETE_chiave"

**Descrizione**: il comando serve per eliminare dal database l'oggetto associato alla chiave passata nel comando.

### "UPDATE_chiave&dati"

**Descrizione**: il comando serve ad aggiornare il valore relativo alla chiave passata nel comando con i dati passati nel comando.

### DATI SCAMBIATI

I dati che si scambiano il database e il server-web per quanto riguarda i film sono un oggetto JSON così composto: sono presenti tre interi `dayIndex`, `movieIndex`, `room` (che andranno a formare parte della chiave) e un oggetto `room` che contiene tutte le informazioni del film da salvare. Per quanto riguarda la prenotazione, il dato sarà un oggetto
JSON che rappresenta la prenotazione.
Non viene specificato oltre perchè il modello dei dati e delle chiavi non è specifico del protocollo: infatti, essendo le chiavi e i dati delle semplici stringhe,
il database può salvare qualsiasi tipo di dato sotto forma di stringa con qualsiasi tipo di chiave sotto forma di stringa, a patto di rispettare la struttura del comando.