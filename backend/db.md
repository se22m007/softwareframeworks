# Abgabe SFR  Backend/DB

Wir haben uns für InfluxDB entschieden, da wir uns dachten, dass bei uns Schreiben und Lesen von zeitabhängigen Daten eigentlich das einzige ist das wir machen müssen.  Auszug aus deren Website:

InfluxDB simplifies time series data management. Designed to handle high speed and high volume data ingest and real-time data analysis, InfluxDB's robust data collectors, common API across the entire platform, highly performant time series engine, and optimized storage lets you build once and deploy across multiple products and environments.

Selbst wenn wir daher mehrere Objekte gleichzeitig reinschicken, lesen und analysieren wollen ist das damit kein Problem.

Weiters **sollte** das Setup eigentlich recht einfach sein, jedoch kam es da zu Problemen.

# Setup

Das grundsätzliche Setup war sehr simpel. Wir haben eine Configklasse welche die notwendigen Parameter aus den Properties ausliest. Mittels der "influxdb-client-java" dependency konnten wir dann recht schnell einen Client erstellen und eine Verbindung herstellen. Da das Ganze in der Cloud rennt war sonst kein Setup notwendig. 

## Create

Das Erstellen von Daten funktioniert einwandfrei. Wir mussten dazu nur ein paar Annotations in der Klasse und einen Bucket in unserer InfluxDB Cloud erstellen.

## Read

Beim Lesen sind wir auf ein Problem gestoßen. Das grundsätzliche Abrufen funktioniert zwar, jedoch das mappen in unser Objekt nicht. Beim Abruf kann man eigentlich eine Klasse mitnehmen in das das Objekt gemappt werden soll. Der Client ruft dann den FluxResultMapper auf, welcher wiederrum aus den abgerufenen Einträgen sich die Keys aus unserem Objekt raussucht und die fields setzt. Leider kommen bei unserem Abruf die fields nicht mit außer bei dem Feld das mit "tag=true" annotiert ist. Unser Objekt ist daher bis auf das eine Feld leer. 

## Weitere Abgaben 

Je nachdem was für weitere Abgaben notwendig ist, werden wir das ganze auf eine simple H2 mit Jpa umstellen, damit wir normal weitermachen können. Da InfluxDB jedoch recht interessant ist, wollten wir den Effort nicht einfach so wegschmeißen. 