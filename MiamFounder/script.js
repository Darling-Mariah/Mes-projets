
const searchInput = document.getElementById("search-input");
const searchBtn = document.getElementById("search-btn");
const mealsContainer = document.getElementById("meals");
const resultHeading = document.getElementById("result-heading");
const errorContainer = document.getElementById("error-container");
const mealDetails = document.getElementById("meal-details");
const mealDetailsContent = document.querySelector(".meal-details-content");
const backBtn = document.getElementById("back-btn");

//lien de l'API (serveur) de la référence des recettes
const BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
const SEARCH_URL = `${BASE_URL}search.php?s=`
const LOOKUP_URL = `${BASE_URL}lookup.php?i=`


//fonctions

//fonction permettant d'afficher les recettes
function displayMeals(meals){
    mealsContainer.innerHTML = "";

    meals.forEach(meal => {
        mealsContainer.innerHTML += ` 
        
          <div class="meal" data-meal-id="${meal.idMeal}">
          <img src="${meal.strMealThumb}" alt="${meal.strMeal}"> 
          <div class="meal-info">
          <h3 class="meal-title">${meal.strMeal}</h3>
          ${meal.strCategory ? `<div class="meal-category">${meal.strCategory}</div>` : ""}
        </div>
      </div>
    `;
        
    });
}

//fonction permettant de chercher une recette
async function searchMeals(){
    const searchTerm = searchInput.value.trim();//récupération de la valeur saisie par l'user et trim() méthode permettant de delete space before and after ce que l'user a écrit
    //gestion des erreurs de recherches
    if (!searchTerm) {
        errorContainer.textContent = "Svp entrez une valeur";
        errorContainer.classList.remove("hidden");
        return;
    }

    try{
            //on affiche ce que l'utilisateur saisie au clavier
             resultHeading.textContent = ` Résultats pour "${searchTerm}"`;
             mealsContainer.innerHTML = ""; //on vide ce container avant d'afficher des nouveaux
             //on masque la fenêtre d'erreur
             errorContainer.classList.add("hidden");
             
             //requête HTTP vers l'API
             const response = await fetch(`${SEARCH_URL}${searchTerm}`); //assemble l’URL finale, en ajoutant ce que l’utilisateur a tapé à la fin
             const data = await response.json();  //.json() pour lire les données au format JSON   
             
             //aucune recettes trouvées
             if(data.meals === null) {
                 resultHeading.textContent = ``;
                 mealsContainer.innerHTML = "";
                 errorContainer.textContent = `Aucune recettes trouvées pour "${searchTerm}". Essayez à nouveau!`;
                 errorContainer.classList.remove("hidden");
                 return;
             }

             //recettes trouvées
             else{
                resultHeading.textContent = ` Résultats pour "${searchTerm}"`;
                //affichage des recettes
                displayMeals(data.meals);
                searchInput.value = "";
                
             }
     }  catch (error){
        //ds le cas d'un cas d'erreur non relevé précédemment
         errorContainer.textContent = " Une erreur est survenue. Réessayez!!";
         errorContainer.classList.remove("hidden");
     }
}

//fonction pour afficher le détail de chaque recette
async function clickDetails(e){

     const mealElement = e.target.closest(".meal"); //recette qu'on aurait cliqué
     if(!mealElement) return;
    
     const mealId = mealElement.getAttribute("data-meal-id");
     
     try {

        const response = await fetch(`${LOOKUP_URL}${mealId}`); //assemble l’URL finale, en ajoutant ce que l’utilisateur a tapé à la fin
        const data = await response.json();

        if (data.meals && data.meals[0]) {
      const meal = data.meals[0];

      const ingredients = [];

      for (let i = 1; i <= 20; i++) {
        if (meal[`strIngredient${i}`] && meal[`strIngredient${i}`].trim() !== "") {
          ingredients.push({
            ingredient: meal[`strIngredient${i}`],
            measure: meal[`strMeasure${i}`],
          });
        }
      }

      // afficher les détails de la recette choisie
      mealDetailsContent.innerHTML = `
           <img src="${meal.strMealThumb}" alt="${meal.strMeal}" class="meal-details-img">
           <h2 class="meal-details-title">${meal.strMeal}</h2>
           <div class="meal-details-category">
             <span>${meal.strCategory || "Uncategorized"}</span>
           </div>
           <div class="meal-details-instructions">
             <h3>Instructions</h3>
             <p>${meal.strInstructions}</p>
           </div>
           <div class="meal-details-ingredients">
             <h3>Ingredients</h3>
             <ul class="ingredients-list">
               ${ingredients
                 .map(
                   (item) => `
                 <li><i class="fas fa-check-circle"></i> ${item.measure} ${item.ingredient}</li>
               `
                 )
                 .join("")}
             </ul>
           </div>
           ${
             meal.strYoutube
               ? `
             <a href="${meal.strYoutube}" target="_blank" class="youtube-link">
               <i class="fab fa-youtube"></i> Watch Video
             </a>
           `
               : ""
           }
         `;

      mealDetails.classList.remove("hidden");
      mealDetails.scrollIntoView({ behavior: "smooth" });
    }
     } catch (error) {
        errorContainer.textContent = "Impossible d'afficher les détails de la recette. Réessayez!!";
        errorContainer.classList.add("hidden");
     }

}

//évènement pour activer la recherche lorqu'on appui sur le bouton rechercher
searchBtn.addEventListener("click",searchMeals);

//évènement pour activer la recherche lorqu'on enfonce le bouton entrer sur l'écran
searchInput.addEventListener("keypress",(e) =>{
    if(e.key === "Enter") searchMeals();
});

//bouton de retour
backBtn.addEventListener("click",()=> mealDetails.classList.add("hidden"));

//évènement pour voir les détails d'une recette
mealsContainer.addEventListener("click", clickDetails)