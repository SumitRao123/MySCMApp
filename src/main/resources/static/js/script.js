console.log("Script loading ");


let currentTheme = getTheme();

document.addEventListener("DOMContentLoaded",()=>{
    changeTheme()

})

function changeTheme(){
      document.querySelector("html").classList.add(currentTheme);
      let changebtn = document.querySelector("#theme_change_button");
      let oldText=  document.querySelector("#btn_text");
      console.log(changebtn);
      changebtn.addEventListener("click",(event)=>{
         console.log("btn click");
          let oldTheme = currentTheme;
           
          if(currentTheme  ===  "light"){
             currentTheme = "dark";
          }
          else currentTheme = "light";

         
         changePageTheme(currentTheme,oldTheme)
      });    

        

}

function changePageTheme(currentTheme,oldTheme){
        
     
     
     
     setTheme(currentTheme);
          
     document.querySelector("html").classList.remove(oldTheme);
      

     document.querySelector("html").classList.add(currentTheme);
  
     document.querySelector("#theme_change_button").querySelector("span").textContent = (currentTheme == "light") ? "Dark" : "Light";
}

function setTheme(theme){
    localStorage.setItem("theme",theme);
}


function getTheme(){
     let theme = localStorage.getItem("theme");

     return theme ? theme : "light";
}