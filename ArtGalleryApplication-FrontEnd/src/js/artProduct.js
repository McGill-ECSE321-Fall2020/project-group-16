export default {
    name: "ArtProduct",
    props: ["artworkId", "title", "artists", "creationDate", "artworkStatus", "price", "imageUrl"],
    
    /**
     * declaration of the page's data
     */
    data() {
        return {
            id: this.artworkId
        }

    },
    methods: {

        /**
         * Go to the specific artwork page
         */
        goToArtwork: function() {
            this.$router.push({ name: "ViewArtwork", params: { artworkId: this.id } })
        }
    },
};
