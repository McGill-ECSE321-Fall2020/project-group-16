export default {
  name: "ArtProduct",
  props: ["title", "artist", "date", "status", "price", "imageurl"]
};

/*onload = function() {
    if(imageurl !== "undefined"){
        document.getElementById('art-img').src = imageurl;
    }
    else{
        setTimeout(waitForElement, 250);
    }
 };*/