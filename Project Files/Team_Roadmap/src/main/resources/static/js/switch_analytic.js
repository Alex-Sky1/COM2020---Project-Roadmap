const sell_through_button = document.getElementById('sell_through_button');
const waste_proxy_button = document.getElementById('waste_proxy_button');
const pricing_effectiveness_button = document.getElementById('pricing_effectiveness_button');
const operational_insights_button = document.getElementById('operational_insights_button');

const title = document.getElementById('title');

const sell_through_text = document.getElementById('sell_through_text');
const waste_proxy_text = document.getElementById('waste_proxy_text');
const pricing_effectiveness_text = document.getElementById('pricing_effectiveness_text');
const pricing_effectiveness_table = document.getElementById('pricing_effectiveness_table')
const operational_insights_text = document.getElementById('operational_insights_text');


function change_sell_through() {
    title.innerHTML = "Sell Through";
    sell_through_button.style.border = '10px solid cornflowerblue';
    waste_proxy_button.style.border = 'none';
    pricing_effectiveness_button.style.border ='none';
    operational_insights_button.style.border ='none';
    sell_through_text.style.display = 'block';
    waste_proxy_text.style.display = 'none';
    pricing_effectiveness_text.style.display = 'none';
    pricing_effectiveness_table.style.display = 'none';
    operational_insights_text.style.display = 'none';
}

function change_waste_proxy() {
    title.innerHTML = "Waste Proxy";
    sell_through_button.style.border = 'none';
    waste_proxy_button.style.border = '10px solid cornflowerblue';
    pricing_effectiveness_button.style.border ='none';
    operational_insights_button.style.border ='none';
    sell_through_text.style.display = 'none';
    waste_proxy_text.style.display = 'block';
    pricing_effectiveness_text.style.display = 'none';
    pricing_effectiveness_table.style.display = 'none';
    operational_insights_text.style.display = 'none';
}

function change_pricing_effectiveness() {
    title.innerHTML = "Pricing Effectiveness";
    sell_through_button.style.border = 'none';
    waste_proxy_button.style.border ='none';
    pricing_effectiveness_button.style.border = '10px solid cornflowerblue';
    operational_insights_button.style.border ='none';
    sell_through_text.style.display = 'none';
    waste_proxy_text.style.display = 'none';
    pricing_effectiveness_text.style.display = 'block';
    pricing_effectiveness_table.style.display = 'block';
    operational_insights_text.style.display = 'none';
}

function change_operational_insights() {
    title.innerHTML = "Operational Insights";
    sell_through_button.style.border = 'none';
    waste_proxy_button.style.border = 'none';
    pricing_effectiveness_button.style.border = 'none';
    operational_insights_button.style.border = '10px solid cornflowerblue';
    sell_through_text.style.display = 'none';
    waste_proxy_text.style.display = 'none';
    pricing_effectiveness_text.style.display = 'none';
    pricing_effectiveness_table.style.display = 'none';
    operational_insights_text.style.display = 'block';
}