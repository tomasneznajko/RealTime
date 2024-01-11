document.addEventListener("DOMContentLoaded", function() {
    var editarPerfilButton = document.getElementById("editarPerfil");

    editarPerfilButton.addEventListener("click", function() {
        var userId = editarPerfilButton.getAttribute("data-user-id");

        console.log("userId:", userId);

        if (userId !== null) {
            var url = "/usuario/" + userId + "/editar";
            console.log("url:", url);
            window.location.href = url;
        }
    });
});


