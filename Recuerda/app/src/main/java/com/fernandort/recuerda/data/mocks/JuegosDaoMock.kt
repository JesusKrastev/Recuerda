package com.fernandort.recuerda.data.mocks

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.LiveHelp
import androidx.compose.material.icons.filled.Token
import androidx.compose.ui.graphics.Color
import javax.inject.Inject

class JuegosDaoMock @Inject constructor() {
    private val juegos = listOf<JuegoMock>(
        JuegoMock(
            id = "1",
            icono = Icons.Filled.Casino,
            nombre = "Adivina palabra",
            descripcion = "Tienes que adivinar una palabra",
            instrucciones = "Piensa en una palabra, cualquier palabra, pero no me la digas. Yo trataré de adivinarla poco a poco, ya sea diciendo letras o haciendo preguntas. Tú me dirás si estoy acertando, si alguna letra forma parte de la palabra o si estoy cerca. Mi objetivo es descubrir esa palabra antes de que se me acaben los intentos. Dime cuántos intentos tengo y, si puedes, dame alguna pista para ayudarme a descubrir de qué se trata esa palabra oculta.",
            fondo = listOf(Color(0xFFE6469E), Color(0xFF9C35E2))
        ),
        JuegoMock(
            id = "2",
            icono = Icons.Filled.LiveHelp,
            nombre = "Qué prefieres",
            descripcion = "Elige una opción según la pregunta",
            instrucciones = "Tú me das dos opciones y yo tengo que elegir una, o al revés: yo te doy las opciones y tú eliges. Las opciones suelen ser divertidas, difíciles o curiosas, como por ejemplo: ¿Qué prefieres, poder volar o hacerte invisible? No hay respuestas correctas o incorrectas, solo decisiones basadas en gustos, imaginación o dilemas personales. Es un juego para conocerse mejor y pasar un buen rato pensando entre elecciones a veces fáciles… y otras no tanto.",
            fondo = listOf(Color(0xFF17F68E), Color(0xFF00AA41))
        ),
        JuegoMock(
            id = "3",
            icono = Icons.Filled.Book,
            nombre = "Historias",
            descripcion = "Crear una historia a partir de frases",
            instrucciones = "En este minijuego, yo soy el protagonista creativo: mi misión es construir una historia a partir de frases que tú me das. Tú eliges o generas frases al azar —pueden ser el inicio de una historia, elementos clave o frases sueltas que debo integrar— y yo debo usarlas tal como están, encajándolas de forma coherente en una narración original. Puedo jugar en modo libre, dejando volar mi imaginación, o aceptar tus desafíos con límite de tiempo, número de palabras o temas específicos. Cuanto más difíciles sean tus frases, más divertida y retadora será la historia que escriba.",
            fondo = listOf(Color(0xFFA254F6), Color(0xFF5C48E8))
        ),
        JuegoMock(
            id = "4",
            icono = Icons.Filled.Token,
            nombre = "Rompecabezas",
            descripcion = "Ordena la palabra para obtenerla.",
            instrucciones = "En este minijuego, yo debo descubrir la palabra correcta a partir de un conjunto de letras desordenadas que tú me das. Tú eliges una palabra secreta, la revuelves completamente y me la presentas como un rompecabezas. Mi tarea es reorganizar esas letras en el orden correcto para revelar la palabra original. A veces puede parecer fácil, pero otras veces las combinaciones me harán dudar y pensar más de la cuenta. Puedes darme pistas si lo consideras necesario, o añadir un límite de tiempo para hacerlo más desafiante.",
            fondo = listOf(Color(0xFF3783F1), Color(0xFF0E8FBA))
        ),
        JuegoMock(
            id = "5",
            icono = Icons.Filled.Extension,
            nombre = "Acertijos",
            descripcion = "Resuelve un acertijo",
            instrucciones = "En este minijuego, yo tengo que poner a prueba mi ingenio para resolver un acertijo que tú me planteas en forma de una frase enigmática. Tú eres quien crea o elige la frase del acertijo, cuidadosamente formulada para ocultar la respuesta entre juegos de palabras, pistas sutiles o dobles sentidos. Mi objetivo es leerla con atención, analizarla y encontrar la solución lógica o creativa que encierra. A veces la respuesta está justo frente a mis ojos, pero otras requiere pensar fuera de lo común. Puedes darme pistas si me atasco o hacer que los acertijos sean cada vez más complejos para subir el nivel del reto.",
            fondo = listOf(Color(0xFFFAC515), Color(0xFFF97B16))
        ),
        JuegoMock(
            id = "6",
            icono = Icons.Filled.Token,
            nombre = "Palabras",
            descripcion = "A partir de una letra di palabras",
            instrucciones = "EEn este minijuego, yo debo pensar rápido y ser creativo para decir tantas palabras como pueda que empiecen con una letra que tú me das. Tú eliges una letra al azar —puede ser fácil o una de las más difíciles— y yo tengo que generar palabras válidas que comiencen con esa letra, siguiendo el ritmo y sin repetir. Puedes ponerme un límite de tiempo, un tema específico (como animales, objetos o profesiones), o incluso darme puntos según la originalidad o dificultad de cada palabra. ¡Es un desafío perfecto para poner a prueba mi vocabulario y agilidad mental!",
            fondo = listOf(Color(0xFFFF1500), Color(0xFFCC0B0B))
        ),
    )

    fun get(): List<JuegoMock> = juegos

    fun getById(id: String): JuegoMock? = juegos.find { it.id == id }
}