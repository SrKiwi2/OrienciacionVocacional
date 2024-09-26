// Selecciona el botón y los inputs
const themeToggleButton = document.querySelector('.theme-toggle');
const body = document.body;
const inputs = document.querySelectorAll('input');

// Función para alternar el tema
themeToggleButton.addEventListener('click', () => {
    // Alterna la clase del body entre light-theme y dark-theme
    body.classList.toggle('light-theme');
    body.classList.toggle('dark-theme');

    // Alterna la clase de los inputs entre light-theme y dark-theme
    inputs.forEach(input => {
        input.classList.toggle('light-theme');
        input.classList.toggle('dark-theme');
    });

    // Cambia el texto del botón
    themeToggleButton.textContent = body.classList.contains('dark-theme') ? 'Tema Claro' : 'Tema Oscuro';
});
