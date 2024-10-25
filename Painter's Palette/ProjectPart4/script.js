 'use strict';
 
 
 //ACCORDION
const root = document.documentElement;
const button = Array.from(document.getElementsByClassName('accordion-label'));

button.forEach(button =>{
    button.addEventListener('click', buttonClick);
});

function buttonClick(e){
    const btn = e.target;
    btn.classList.toggle('open');
    btn.nextElementSibling.classList.toggle('open');
    root.style.setProperty('--content-height', btn.nextElementSibling.scrollHeight + 'px');
    button.forEach(button1 => {
        if(button1 != btn && button1.classList.contains('open')){
            button1.classList.remove('open');
            button1.nextElementSibling.classList.remove('open');
        }
    });
}



// //CAROUSEL

const leftBtn = document.querySelector('.left');
const rightBtn = document.querySelector('.right');

const carouselItems= Array.from(document.querySelectorAll('.carousel-item'));
const navItems = Array.from(document.querySelectorAll('.nav-item'));
const CAROUSEL_SIZE = carouselItems.length;

leftBtn.addEventListener('click', swipe);
rightBtn.addEventListener('click', swipe);

function swipe(e){
    const currentCarouselItem = document.querySelector('.carousel-item.active');
    const currentIndex = carouselItems.indexOf(currentCarouselItem);

    let nextIndex;

    if(e.currentTarget.classList.contains('left')){
        if(currentIndex === 0){
            nextIndex = CAROUSEL_SIZE - 1;
        }
        else{
            nextIndex = currentIndex - 1;
        }
    }
    else{
        if(currentIndex === CAROUSEL_SIZE - 1){
            nextIndex = 0;
        }
        else{
            nextIndex = currentIndex + 1;
        }
    }

    carouselItems[nextIndex].classList.add('active');
    navItems[nextIndex].classList.add('active');
    currentCarouselItem.classList.remove('active');
    navItems[currentIndex].classList.remove('active');
}

for(let i =0; i < navItems.length; i++){
    navItems[i].addEventListener('click', clickDot);
    
    function clickDot(e){
        const currentCarouselItem = document.querySelector('.carousel-item.active');
        const currentNavItem = document.querySelector('.nav-item.active');

        if(e.currentTarget.classList === currentNavItem){
        }
        else{
            currentCarouselItem.classList.remove('active');
            currentNavItem.classList.remove('active');
            e.currentTarget.classList.add('active');
            carouselItems[i].classList.add('active');
           
        }
    }
}


// //MODAL
const openBtn = document.querySelector(".open-btn");
const modal = document.querySelector(".modal");
const closeBtn = document.querySelector(".close-btn");

function openModal() {
    modal.classList.remove("hide");
}
function closeModal(e){
    if(e.target.classList.contains("modal")){
        modal.classList.add("hide");
    }
    else{
        modal.classList.add("hide");
    }
}

openBtn.addEventListener("click", openModal);
modal.addEventListener("click", (e) => closeModal(e, true));
closeBtn.addEventListener("click", closeModal);


