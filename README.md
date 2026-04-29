# MP2

## Commit 1

* Primero, cree la interfaz grafica haciendo uso de algunos consejos que me dió mi monitor ASES, como el uso de PaintComponent con cosas como el repaint para refrescar las ventanas o la clase Wraplayout la cual organiza las cartas automaticamente

* Utilice varios comandos para hacer la base de la interfaz, entre ellos el JFrame para la ventana principal, el JPanel para las secciones, JLabel para el texto y JButon para los botones, ademas de setBackground y setForeground para el tema de los colores entre otros muchos otros comandos

* Despues de hacer la interfaz grafica, me tome el tiempo de releer todo el documento y agregar los nuevos requisitos, como lo eran las cartas trampa, el mazo paso a ser de 50 y no de 40, etc. Para esto me tome el tiempo de crear 3 clases nuevas, la de cartas trampa y dos cartas trampas, ademas de editar la clase de monstruos

## Commit 2

* Agregué la lógica de los botones para jugar carta, atacar y terminar turno, conectándolos correctamente con la lógica del juego mediante una clase controladora que se encarga de comunicar la interfaz con las acciones del juego.

* Implementé el flujo básico de turnos: cambio de jugador, robo de carta al iniciar turno y restricciones como jugar solo una carta y realizar un solo ataque por turno. También se validan casos como no poder atacar en el primer turno y no poder atacar si no hay monstruos en el campo.

* El sistema de combate ya funciona, incluyendo ataques directos y enfrentamientos entre monstruos, aplicando el daño correspondiente según el ATK. Además, se agregó la condición de victoria cuando los puntos de vida llegan a 0 y se deshabilitan los botones al finalizar la partida.

* También organicé el repositorio, moviendo los archivos a la carpeta src y agregando .gitignore para evitar subir archivos innecesarios, ya que antes estaban como archivos sueltos.

* Actualmente existe un detalle visual donde los puntos de vida parecen asignarse al jugador incorrecto durante los ataques. Sin embargo, mediante pruebas en consola se verificó que la lógica del juego funciona correctamente y el daño se aplica al jugador adecuado. Este comportamiento se debe a la forma en que la interfaz está mostrando la información, como tenemos jugador actual y oponente pero en  la ventana solo aparece Jugador 1(izquierda) Jugador 2(derecha) entonces se puede llegar a ver como si inflingiera daño a si mismo.

## Commit 3

* Se agregó una pantalla de inicio donde los jugadores pueden ingresar sus nombres antes de comenzar el duelo, en lugar de tener los nombres predefinidos en el código
* Se corrigió el bug visual donde los LP se mostraban invertidos en pantalla, el problema estaba en la forma en que la interfaz asignaba las etiquetas a cada jugador en el método crearPanelJugador
* Se completó el sistema de combate agregando la diferencia entre posición de ataque y defensa, ahora el juego compara ATK vs DEF cuando el monstruo defensor está en posición de defensa
* Se habilitaron las cartas trampa en la interfaz, ahora se pueden colocar boca abajo en el campo y activar cuando se desee mediante un botón
* Se corrigió el sistema de ataques para que cada monstruo pueda atacar una vez por turno de forma independiente
* Se agregó la validación de sacrificio para monstruos de nivel mayor a 4, si no hay monstruos en campo para sacrificar simplemente no se puede invocar
* Al terminar el duelo ahora aparece un mensaje de victoria en lugar de solo deshabilitar los botones
* Se eliminaron todos los println de depuración que quedaron en el código de las clases Campo, MirrorForce, TrapHole y ControladorJuego

## Autores

* Santiago Romero Restrepo - 2559949
* Diego Fernando Pérez - 2559956
* Andrés David Perea - 2559770
