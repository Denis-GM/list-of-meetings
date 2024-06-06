var userInfo = document.getElementById("user-info");
var editForm = document.getElementById("edit-form");

function editProfile() {
    userInfo.style.visibility = "hidden";
    editForm.style.visibility = "visible";

    document.getElementById('user-last-name').style.display = 'none';
    document.getElementById('last-name-input').style.display = 'inline';
    document.getElementById('user-first-name').style.display = 'none';
    document.getElementById('first-name-input').style.display = 'inline';
    document.getElementById('user-phone').style.display = 'none';
    document.getElementById('phone-input').style.display = 'inline';
    document.getElementById('edit-button').style.display = 'none';
    document.getElementById('save-button').style.display = 'inline';
}

document.getElementById('save-button').addEventListener('click', function() {
    document.getElementById('edit-form').submit();
});