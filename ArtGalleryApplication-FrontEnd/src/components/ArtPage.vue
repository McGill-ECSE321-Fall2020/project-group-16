<template>
    <div id="artpage">
        <div class="box-1">
            <div class="outline">
                <img src="../assets/birth-of-venus-botticelli.jpg"> 
            </div>
        </div>
        <div class="box-2">
            <p class="title">Title</p>
            <p class="artist">Artist</p>
            <p class="date">November 11, 2020</p>
            <p class="descrip">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce placerat commodo elit. Morbi vel iaculis augue. Cras egestas aliquam consectetur. Sed lacinia, nisi sit amet fringilla convallis, lectus orci venenatis nunc, id tincidunt lorem ex nec arcu. Curabitur quis sapien efficitur, vulputate tellus vel, finibus urna. </p>   
            <p> <button class="purchase">$100 - PURCHASE</button> </p>
            <p> <button class="more">MORE FROM THIS ARTIST</button> </p>
        </div>
    </div>
</template>

<script>
export default {
     name: "ArtPage"
}
</script>

<style scoped>
    #artpage {
        padding: 60px;
        display: flex; 
    }

    .box-1 {
        display: flex;
        flex: 1;
        align-items: center;
        justify-content: center;
    }
    
    .outline {
        display: flex;
        align-items: center;
        justify-content: center;

        border: 1px solid #000000;
        width: 90%;
        height: 95%;
    }

    img {
        width: 90%;
    }

    .box-2 {
        text-align: left;
        flex: 1;
    }

   .title {
        font-family: Montserrat;
        font-style: normal;
        font-weight: 600;
        font-size: 48px;
        line-height: 59px;
        color: #000000;
    }

    .artist {
        font-family: Montserrat;
        font-style: normal;
        font-weight: normal;
        font-size: 48px;
        line-height: 59px;
        color: #000000;
    }

    .date {
        font-family: Montserrat;
        font-style: italic;
        font-weight: normal;
        font-size: 24px;
        line-height: 29px;

        color: #000000;
    }

    .descrip {
        font-family: Montserrat;
        font-style: italic;
        font-weight: normal;
        font-size: 24px;
        line-height: 29px;

        color: #000000;
    }

    button {
        width: 350px;
        height: 50px;
        border-radius: 3px;
        font-family: Montserrat;
        font-style: normal;
        font-weight: normal;
        font-size: 24px;
        line-height: 29px;
        text-align: center;
    }

    .purchase {
        background: #006F45;
        color: #E9E7DB;
    }

    .more {
        color: #000000;
        border: 1px solid #000000;
    }

</style>