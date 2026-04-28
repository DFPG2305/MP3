# MP2

## Commit 1

## Commit 2

* Agregué la lógica de los botones para jugar carta, atacar y terminar turno, conectándolos correctamente con la lógica del juego mediante una clase controladora que se encarga de comunicar la interfaz con las acciones del juego.

* Implementé el flujo básico de turnos: cambio de jugador, robo de carta al iniciar turno y restricciones como jugar solo una carta y realizar un solo ataque por turno. También se validan casos como no poder atacar en el primer turno y no poder atacar si no hay monstruos en el campo.

* El sistema de combate ya funciona, incluyendo ataques directos y enfrentamientos entre monstruos, aplicando el daño correspondiente según el ATK. Además, se agregó la condición de victoria cuando los puntos de vida llegan a 0 y se deshabilitan los botones al finalizar la partida.

* También organicé el repositorio, moviendo los archivos a la carpeta src y agregando .gitignore para evitar subir archivos innecesarios, ya que antes estaban como archivos sueltos.

* Actualmente existe un detalle visual donde los puntos de vida parecen asignarse al jugador incorrecto durante los ataques. Sin embargo, mediante pruebas en consola se verificó que la lógica del juego funciona correctamente y el daño se aplica al jugador adecuado. Este comportamiento se debe a la forma en que la interfaz está mostrando la información, como tenemos jugador actual y oponente pero en  la ventana solo aparece Jugador 1(izquierda) Jugador 2(derecha) entonces se puede llegar a ver como si inflingiera daño a si mismo.

## Commit 3