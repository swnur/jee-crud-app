// Store the delete link URL for confirmation
let deleteUrl = '';

function openDeleteModal(event, url) {
    event.preventDefault();  // Prevents the default link action
    deleteUrl = url;  // Store the delete link URL
    document.getElementById("deleteModal").style.display = "block";  // Show modal
}

function closeDeleteModal() {
    document.getElementById("deleteModal").style.display = "none";  // Hide modal
}

function confirmDelete() {
    if (deleteUrl) {
        window.location.href = deleteUrl;  // Redirect to the delete URL
    }
}
