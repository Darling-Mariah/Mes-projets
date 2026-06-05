
const spanMot = document.getElementById("mot");

const mots = ["Logiciels Embarqués","Vision par IA","Robotique"];

let index = 0;
let charactIndex = 0;


function text_animation() {
     
            
    charactIndex++;
    spanMot.textContent = mots[index].slice(0,charactIndex);
    
    if (charactIndex === mots[index].length) {

        index++;
        charactIndex = 0;
    }

     if (index === mots.length) {
        index = 0;
    }

    setTimeout(text_animation, 370);

   
}

text_animation(); 
