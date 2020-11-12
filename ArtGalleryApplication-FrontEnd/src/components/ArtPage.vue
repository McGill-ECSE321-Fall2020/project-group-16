<template>
    <div id="artpage">
        <p class="title">Art Title</p>
        <p class="artist">Artist</p>
        <p class="date">November 11, 2020</p>
        <p class="descrip">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce placerat commodo elit. Morbi vel iaculis augue. Cras egestas aliquam consectetur. Sed lacinia, nisi sit amet fringilla convallis, lectus orci venenatis nunc, id tincidunt lorem ex nec arcu. Curabitur quis sapien efficitur, vulputate tellus vel, finibus urna. </p>
    </div>
</template>

<script>
export default {
     name: "ArtPage",
        components: {
            ArtPage
        },
}
</script>

<style scoped>
   #artpage {
       background: #E5E5E5;
   }

   .title {
        position: absolute;
        width: 381px;
        height: 59px;
        left: 836px;
        top: 218px;

        font-family: Montserrat;
        font-style: normal;
        font-weight: 600;
        font-size: 48px;
        line-height: 59px;
    }

    .artist {
        position: absolute;
        width: 381px;
        height: 59px;
        left: 836px;
        top: 284px;

        font-family: Montserrat;
        font-style: normal;
        font-weight: normal;
        font-size: 48px;
        line-height: 59px;
        color: #000000;
    }

    .date {
        position: absolute;
        width: 381px;
        height: 29px;
        left: 836px;
        top: 356px;

        font-family: Montserrat;
        font-style: italic;
        font-weight: normal;
        font-size: 24px;
        line-height: 29px;

        color: #000000;
    }

    .descrip {
        position: absolute;
        width: 381px;
        height: 348px;
        left: 836px;
        top: 398px;

        font-family: Montserrat;
        font-style: italic;
        font-weight: bold;
        font-size: 24px;
        line-height: 29px;

        color: #000000;
    }



</style>