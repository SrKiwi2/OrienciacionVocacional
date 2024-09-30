// Seleccionamos solo el botón de cambio de tema
const themeToggleButton = document.querySelector('.theme-toggle');
const body = document.body;

// Función para aplicar el tema guardado
function applyTheme(theme) {
    if (theme === 'dark') {
        body.classList.add('dark-theme');
        body.classList.remove('light-theme');
        themeToggleButton.querySelector('i').classList.remove('bi-moon');
        themeToggleButton.querySelector('i').classList.add('bi-sun');
    } else {
        body.classList.add('light-theme');
        body.classList.remove('dark-theme');
        themeToggleButton.querySelector('i').classList.remove('bi-sun');
        themeToggleButton.querySelector('i').classList.add('bi-moon');
    }
}

// Al cargar la página, aplicamos el tema guardado en LocalStorage
document.addEventListener('DOMContentLoaded', () => {
    const savedTheme = localStorage.getItem('theme') || 'light'; // Predeterminado: tema claro
    applyTheme(savedTheme);
});

// Agregamos un evento de clic al botón de cambio de tema
themeToggleButton.addEventListener('click', function(event) {
    event.preventDefault();
    event.stopPropagation();

    // Alternar entre los temas claro y oscuro
    const currentTheme = body.classList.contains('dark-theme') ? 'dark' : 'light';
    const newTheme = currentTheme === 'dark' ? 'light' : 'dark';

    // Aplicar el nuevo tema
    applyTheme(newTheme);

    // Guardar el tema en LocalStorage
    localStorage.setItem('theme', newTheme);
});

// Prevenir que el formulario refresque la página al hacer submit
const form = document.querySelector('form');
form.addEventListener('submit', function(event) {
    event.preventDefault();
    console.log("Formulario enviado, pero sin recargar la página.");
});
