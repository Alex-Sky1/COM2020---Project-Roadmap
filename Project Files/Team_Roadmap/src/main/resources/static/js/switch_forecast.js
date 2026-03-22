const forecast_button = document.getElementById('forecast_button');
const recommendation_button = document.getElementById('recommendation_button');

const title = document.getElementById('title');

const forecast_table = document.getElementById('forecast_table');
const recommendation_text = document.getElementById('recommendation_text')


function change_forecast(){
    title.innerHTML = "Today's Bundle Postings"
    forecast_button.style.border = '10px solid cornflowerblue';
    recommendation_button.style.border = 'none';
    forecast_table.style.display = 'table';
    recommendation_text.style.display = 'block';

}

function change_recommendation(){
    title.innerHTML = "Today's Recommendations"
    recommendation_button.style.border = '10px solid cornflowerblue';
    forecast_button.style.border = 'none';
    forecast_table.style.display = 'none';
    recommendation_text.style.display = 'block';
}