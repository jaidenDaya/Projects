'use strict';
//API
const url = 'http://colormind.io/api/';

const schemeContainer = document.querySelector('.scheme-container');
const reloadBtn = document.querySelector('.reload-btn');
reloadBtn.addEventListener("click", fetchColors);


async function fetchColors(){
   try{
       let schemeData = {
           model: "default",
           input : ["N","N","N","N","N"]
       };
       const response = await fetch(url,{
           method: "POST",
           body: JSON.stringify(schemeData)
       } );

       if(!response.ok){
           throw Error(`Error: ${response.url} ${response.statusText}`);
       }
       const data = await response.json();
       addColorScheme(data);

   }catch(error){
       showError(error.message);
   }
} 

function addColorScheme(e){
   schemeContainer.innerHTML = '';
   const colors = e.result;
   for(let i = 0; i < 5; i++){
       const hex = colors[i];

       const cardContainer = document.createElement('div');
       const card = document.createElement('div');
       card.classList.add('color-card');
       card.style.backgroundColor = 'rgb(' + hex[0] + ',' + hex[1] + ',' + hex[2] +')';

       const text = document.createElement('p');
       text.classList.add('color-hex');
       text.textContent = 'rgb(' + hex[0] + ', ' + hex[1] + ', ' + hex[2] +')';
       cardContainer.append(card);
       cardContainer.append(text);
       schemeContainer.append(cardContainer);
       
   }
}