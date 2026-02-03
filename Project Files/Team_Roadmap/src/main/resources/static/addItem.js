// define the input text box and the place to put the items
const inputField = document.getElementById('item');
const listContainer = document.getElementById('items_list');
const quantityInput = document.getElementById('quantity');

function addListItem() {

    // get rid of whitespace
    const itemText = inputField.value.trim();

    // ensure input is not empty
    if (!itemText) {
        return;
    }

    // add the correct number of new items
    for (let i = 0; i < quantityInput.value; i++) {

        // create a new list element with the input item
        const newListItem = document.createElement('li');
        newListItem.textContent = itemText;

        // add a button to delete the item
        const deleteButton = document.createElement('button');
        deleteButton.textContent = '✖';
        deleteButton.style.float = "right"

        deleteButton.classList.add('delete-btn');

        // handle clicks of the delete button
        deleteButton.addEventListener('click', () => {
            listContainer.removeChild(newListItem);
        });

        // add the item and the delete button to the list
        newListItem.appendChild(deleteButton);
        listContainer.appendChild(newListItem);
    }

    // clear the input fields
    inputField.value = '';
    quantityInput.value = 1;
}