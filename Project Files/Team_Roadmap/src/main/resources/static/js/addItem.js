// array to store the items in the bundle
let items = [];

// define the input text box and the place to put the items
const inputField = document.getElementById('item');
const listContainer = document.getElementById('items_list');
const quantityInput = document.getElementById('quantity');
const hiddenItems = document.getElementById('hidden_items')

// adds an item to the list of items in the bundle and displays it on the screen
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
        newListItem.style.margin = "10px 0";

        // add the item to the array
        items.push(itemText);

        // add the full array to the hidden input
        hiddenItems.setAttribute('value', items.toString())

        // add a button to delete the item
        const deleteButton = document.createElement('button');
        deleteButton.textContent = '✖';
        deleteButton.style.float = "right"

        deleteButton.classList.add('delete-btn');

        // handle clicks of the delete button
        deleteButton.addEventListener('click', () => {
            listContainer.removeChild(newListItem);

            // remove the item from the array
            // if there are multiple items of the same name, it doesn't matter which one is removed
            let index = items.indexOf(itemText);
            // only splice array when item is found
            if (index > -1) {
                items.splice(index, 1);
            }
            hiddenItems.setAttribute('value', items.toString())
        });

        // add the item and the delete button to the list
        newListItem.appendChild(deleteButton);
        listContainer.appendChild(newListItem);
    }

    // clear the input fields
    inputField.value = '';
    quantityInput.value = 1;
}