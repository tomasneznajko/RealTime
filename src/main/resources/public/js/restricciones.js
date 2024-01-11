document.addEventListener("DOMContentLoaded", function () {
    const medioSelect = document.getElementById("medioSelect");
    const whatsappDiv = document.getElementById("whatsappDiv");

    console.log("DOMContentLoaded event fired.");

    medioSelect.addEventListener("change", function () {
        const selectedValue = medioSelect.value;
        if (selectedValue === "whatsapp") {
            whatsappDiv.style.display = "block";
        } else {
            whatsappDiv.style.display = "none";
        }
    });
});


// Example starter JavaScript for disabling form submissions if there are invalid fields
(() => {
    'use strict'

    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    const forms = document.querySelectorAll('.needs-validation')

    // Loop over them and prevent submission
    Array.from(forms).forEach(form => {
        form.addEventListener('submit', event => {
            if (!form.checkValidity()) {
                event.preventDefault()
                event.stopPropagation()
            }
            form.classList.add('was-validated')
        }, false)
    })
})()