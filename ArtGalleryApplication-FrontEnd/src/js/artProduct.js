export default {
    name: "ArtProduct",
    props: ["artworkId", "title", "artists", "creationDate", "artworkStatus", "price", "imageUrl"],
    data() {
        return {
            id: this.artworkId
        }

    },
    methods: {
        goToArtwork: function() {
            this.$router.push({ name: "ViewArtwork", params: { artworkId: this.id } })
        }
    },
    mounted: function() {
        console.log(this.artworkStatus)
    }
};


onload = function() {
    /* if(imageurl !== "undefined"){
         document.getElementById('art-img').src = imageUrl;
     }
     else{
         setTimeout(waitForElement, 250);
     }*/
};

/*onload = function() {
    if(imageurl !== "undefined"){
        document.getElementById('art-img').src = imageurl;
    }
    else{
        setTimeout(waitForElement, 250);
    }
 };*/