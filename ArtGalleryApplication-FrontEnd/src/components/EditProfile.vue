<template>

        <div v-if="!this.errorTargetUser">

            <h3 v-if="this.theCurrentUser.admin == false && this.theCurrentUser.username != this.theTargetUser.username">You cannot edit the profile of other users</h3>
            
            <!-- if the current user is the target or is admin -->
            <div v-else>
                <h3>Edit {{this.theTargetUser.username}}'s profile here</h3>

                <!-- update email -->
                <label>Update user email: 
                    <input type="text" v-model="newEmail" placeholder="New Email">
                    <button v-bind:disabled="!newEmail" @click="updateEmail(newEmail)">Update</button>
                    <span v-if="errorNewEmail" style="color:red"> {{errorNewEmail}} </span>
                </label>
                <br>

                <!-- update firstname and lastname -->
                <label>Update first name: 
                    <input type="text" v-model="newFirstName" placeholder="New First Name">
                    <label> and last name: </label>
                    <input type="text" v-model="newLastName" placeholder="New Last Name">
                    <button v-bind:disabled="!newFirstName || !newLastName" @click="updateFirstLastName(newFirstName, newLastName)">Update</button>
                    <span v-if="errorNewFirstLastName" style="color:red"> {{errorNewFirstLastName}} </span>
                </label>
                <br>

                <!-- update password -->
                <label>Update password: 
                    <input type="password" v-model="verificationPassword" placeholder="Current Password">
                    <label> to be changed to: </label>
                    <input type="password" v-model="newPassword" placeholder="New Password">
                    <button v-bind:disabled="!newPassword || !verificationPassword" @click="updatePassword(verificationPassword, newPassword)">Update</button>
                    <span v-if="errorNewPassword" style="color:red"> {{errorNewPassword}} </span>
                </label>
                <br>
                
                <!-- update description -->
                <label>Update user Description: 
                    <input type="text" v-model="newDescription" placeholder="New Description">
                    <button v-bind:disabled="!newDescription" @click="updateDescription(newDescription)">Update</button>
                    <span v-if="errorNewDescription" style="color:red"> {{errorNewDescription}} </span>
                </label>
                <br>

                <!-- update imageUrl -->
                <label>Update profile Image: 
                    <input type="text" v-model="newImageUrl" placeholder="New Image">
                    <button v-bind:disabled="!newImageUrl" @click="updateImage(newImageUrl)">Update</button>
                    <span v-if="errorNewImageUrl" style="color:red"> {{errorNewImageUrl}} </span>
                </label>



            </div>
        </div>
        <p v-else style="color:red">{{this.errorTargetUser}}</p>

</template>

<script src="../js/editProfile.js"></script>

<style>
</style>