# AOT vs JIT 

Diese Entscheidung ist abhängig von unseren Ressourcen und der verwendeten Umgebung. AOT ist für Plattformen wie Docker optimal, da es Programme im Vergleich zu JIT schneller starten lässt. Da die Umgebung, in der die Programme ausgeführt werden statisch ist, kann AOT angewandt werden.

Bei JIT ist ein Vorteil, dass durch die Kompilierung in der Runtime das Programm an die Umgebung angepasst werden kann und damit in mehreren Umgebungen lauffähig ist und optimiert werden kann. 
Es ist jedoch auch deutlich ressourcenintensiver, beispielsweise durch das verzögerte Starten des Programmes aufgrund der Kompilierung. Auch der Performance Impact aufgrund der mangelhaften Effizienz von Intermediate Languages wie dem Java Bytecode oder der Microsoft Common Intermediate Language sollte nicht unterschätzt werden. Aus diesem Grund würden wir JIT nur wählen, wenn uns viele Ressourcen zur Verfügung stehen, oder wenn die Läuffähigkeit auf unterschiedlichen Plattformen einen Zwang darstellt.
