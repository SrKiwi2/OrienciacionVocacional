// Selecciona el contenedor donde estarán las partículas
const particlesContainer = document.getElementById('particles');

// Lista de imágenes (puedes agregar más rutas si es necesario)
const images = [
    '../assets_registro/images/img_preguntas/imagen1.png',
    '../assets_registro/images/img_preguntas/imagen2.png',
    '../assets_registro/images/img_preguntas/imagen3.png',
    '../assets_registro/images/img_preguntas/imagen4.png',
    '../assets_registro/images/img_preguntas/imagen5.png',
    '../assets_registro/images/img_preguntas/imagen6.png',
    '../assets_registro/images/img_preguntas/imagen7.png',
    '../assets_registro/images/img_preguntas/imagen8.png',
    '../assets_registro/images/img_preguntas/imagen9.png',
    '../assets_registro/images/img_preguntas/imagen10.png',
    '../assets_registro/images/img_preguntas/imagen11.png',
    '../assets_registro/images/img_preguntas/imagen12.png',
    '../assets_registro/images/img_preguntas/imagen13.png',
    '../assets_registro/images/img_preguntas/imagen14.png',
    // '../assets_registro/images/img_preguntas/imagen15.png'
];

// Función para obtener un número aleatorio en un rango
function getRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min)) + min;
}

// Crear las imágenes como partículas
images.forEach((src, index) => {
    const img = document.createElement('img');
    img.src = src;
    img.alt = `Imagen ${index + 1}`;
    img.classList.add('particle');

    // Asignar posiciones y animaciones aleatorias
    img.style.left = `${getRandomInt(0, 100)}%`; // Posición horizontal aleatoria
    img.style.animationDuration = `${getRandomInt(10, 20)}s`; // Duración de la animación aleatoria
    img.style.width = `${getRandomInt(50, 120)}px`; // Tamaño aleatorio
    img.style.animationDelay = `${getRandomInt(0, 5)}s`; // Añadimos un pequeño retraso solo para la animación

    // Añadir la imagen al contenedor
    particlesContainer.appendChild(img);
});
