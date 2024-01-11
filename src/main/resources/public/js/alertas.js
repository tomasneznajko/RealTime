function mostrarAlertaExito(exito) {
        Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: exito,
            showConfirmButton: false,
            timer: 2000
        });
};

function mostrarAlertaError(error) {
    Swal.fire({
        icon: 'error',
        title: "Error",
        text: error
    });
};