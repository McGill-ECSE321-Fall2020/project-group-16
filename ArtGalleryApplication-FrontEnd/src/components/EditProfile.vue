<template>

        <div v-if="!this.errorTargetUser">

            <h3 v-if="this.theCurrentUser.admin == false && this.theCurrentUser.username != this.theTargetUser.username">You cannot edit the profile of other users</h3>
            
            <!-- if the current user is the target or is admin -->
            <div v-else>
                <h3>Edit {{this.theTargetUser.username}}'s profile here</h3>
            </div>
        </div>
        <p v-else style="color:red">{{this.errorTargetUser}}</p>

</template>

<script src="../js/editProfile.js"></script>

<style>
</style>