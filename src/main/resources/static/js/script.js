$(document).ready(function() {
    setTimeout(function() {
        $('.alert').fadeOut('slow');
    }, 5000);

    $('.delete-employee').on('click', function(e) {
        if (!confirm('Are you sure you want to delete this employee?')) {
            e.preventDefault();
        }
    });
});