const menus = document.querySelectorAll('.dropdown-toggle');


menus.forEach(menu => {
  menu.addEventListener('show.bs.dropdown', () => {
    menu.classList.add("open");
  });
});

menus.forEach(menu => {
    menu.addEventListener('hide.bs.dropdown', () => {
      menu.classList.remove("open");
  });
});


