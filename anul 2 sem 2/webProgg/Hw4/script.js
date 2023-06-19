// const links = document.querySelectorAll(".container a");
// const bg = document.querySelector(".bg");
// const showClass = "bg-show";

// for (const link of links) {
//   const img = new Image();
//   img.src = link.dataset.bg;

//   link.addEventListener("mouseenter", function() {
//     bg.style.backgroundImage = `url(${this.dataset.bg})`;
//     document.body.classList.add(showClass);
//   });

//   link.addEventListener("mouseleave", () => {
//     document.body.classList.remove(showClass);
//   });
// }



function bigImg(x) {
  console.log(x);
  var id= x.getAttribute("id");
  console.log(id);
  var image = document.getElementById(id).getAttribute("src");
  console.log(image);
  //element.style.backgroundImage = url('${image}');
  document.getElementById('content').style.backgroundImage=`url("${image}")`;
  // document.getElementById('content').style.height="750px";
  // document.getElementById('content').style.width="750px";
}

function defaultImg(){
  document.getElementById('content').style.backgroundImage='url("images/default.png")';
}



