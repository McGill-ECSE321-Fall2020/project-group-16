export default {
  name: "ArtProduct",
  props: ["artworkId", "title", "artists", "creationDate", "artworkStatus", "price", "imageUrl"]
};

/*onload = function() {
    if(imageurl !== "undefined"){
        document.getElementById('art-img').src = imageurl;
    }
    else{
        setTimeout(waitForElement, 250);
    }
 };*/