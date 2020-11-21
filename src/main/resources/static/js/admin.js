$(document).ready(function () {
    $('.Admin__slider').slick({dots: true});
});

function deleteById(id) {
    $.post("/admin/delete/" + id);
}
