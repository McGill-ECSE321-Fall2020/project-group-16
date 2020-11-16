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
};
