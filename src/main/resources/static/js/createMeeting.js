document.addEventListener("DOMContentLoaded", function() {
    const tags = document.querySelectorAll('.tag-button');
    let selectedTags = [];

    tags.forEach(tag => {
        tag.addEventListener('click', function() {
            this.classList.toggle('selected');
            const tagValue = this.getAttribute('value');

            if (this.classList.contains('selected')) {
                selectedTags.push(tagValue);
            } else {
                selectedTags = selectedTags.filter(t => t !== tagValue);
            }

            console.log(selectedTags);
        });
    });

    const submitButton = document.querySelector('.create-button');
    submitButton.addEventListener('click', function() {
        //fetch
    });
});








