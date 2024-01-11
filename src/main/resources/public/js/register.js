$(document).ready(function () {
    $('#registroForm').submit(function (e) {
        let contrasenia = $('#contrasenia').val();
        let confirmarContrasenia = $('#confirmarContrasenia').val();


        const mayusculaER = /[A-Z]/;
        const minusculaER = /[a-z]/;
        const digitoER = /[0-9]/;

        let mensajesError = [];

        if (!mayusculaER.test(contrasenia)) {
            mensajesError.push('<li>Debe contener al menos una letra mayúscula.</li>');
        }
        if (!minusculaER.test(contrasenia)) {
            mensajesError.push('<li>Debe contener al menos una letra minúscula.</li>');
        }
        if (!digitoER.test(contrasenia)) {
            mensajesError.push('<li>Debe contener al menos un número.</li>');
        }
        if (contrasenia.length < 9) {
            mensajesError.push('<li>Debe contener al menos 9 caracteres.</li>');
        }
        if(contrasenia !== confirmarContrasenia) {
            mensajesError.push('<li>Las contraseñas no coinciden.</li>');
        }

        if (mensajesError.length > 0) {
            e.preventDefault();
            $('#errorContrasenia').html(mensajesError.join(' '));
        }
        else {
            $('#errorContrasenia').html('');
        }
    });
});

$(document).ready(function() {
    $('#medioNotificacion').change(function() {
        if ($(this).val() === 'telefono') {
            $('#telefonoInput').show();
            $('#telefonoLabel').show();
            $('#telefonoInput input').attr("required", true);
        } else {
            $('#telefonoInput').hide();
            $('#telefonoLabel').hide();
            $('#telefonoInput input').attr("required", false);
        }
    });
});


