<template>
  <div v-if="!errorTargetUser">
    <h3
      v-if="
        theCurrentUser.admin == false &&
        theCurrentUser.username != theTargetUser.username
      "
    >
      You cannot edit the profile of other users
    </h3>

    <!-- if the current user is the target or is admin -->
    <div class="m-auto" v-else>
      <!-- Success Alert -->
      <div
        v-if="success"
        class="alert alert-success alert-dismissible fade show mx-auto mb-4"
        role="alert"
      >
        <strong>Changes Saved!</strong>
        <button
          type="button"
          class="close"
          data-dismiss="alert"
          aria-label="Close"
        >
          <span aria-hidden="true">&times;</span>
        </button>
      </div>

      <div class="edit-form mx-auto mb-5 pt-5 px-5">
        <h1 class="">Edit Your Profile</h1>
        <div class="current-info">
          <img
            class="profile-image my-3"
            src="../assets/Profile Pic.png"
            alt=""
          />
          <div class="name">
            <h2 class="">
              {{ theTargetUser.firstName }}&nbsp;{{
                theTargetUser.lastName
              }}&nbsp;&nbsp;
            </h2>
            <div class="icon">
              <i
                style=""
                class="fas fa-edit fa-lg"
                @click="displayNameModal"
              ></i>
            </div>
          </div>
          <h3>{{ theTargetUser.username }}</h3>
          <div
            class="change-password px-3 py-1 mx-auto"
            @click="displayPasswordModal()"
          >
            CHANGE PASSWORD
          </div>
          <br />
          <div class="row">
            <div class="col-10">
              <p><b>Email: </b>{{ theTargetUser.email }}</p>
            </div>
            <div class="col-2">
              <i class="fas fa-edit fa-lg" @click="displayEmailModal"></i>
            </div>
          </div>
          <div class="row">
            <div class="col-10">
              <p>
                <b>Description: </b
                >{{
                  theTargetUser.description === ""
                    ? "No decsription yet."
                    : theTargetUser.description
                }}
              </p>
            </div>
            <div class="col-2">
              <i class="fas fa-edit fa-lg" @click="displayDescriptionModal"></i>
            </div>
          </div>
        </div>
        <div class="done mx-auto px-3 py-2 mb-3" @click="done()">DONE</div>
      </div>
      <!-- Modal -->
      <div
        v-if="
          nameModalActive ||
          emailModalActive ||
          descriptionModalActive ||
          passwordModalActive
        "
        class="modal-container"
      >
        <!-- Error Alert -->
        <div
          v-if="error !== ''"
          class="alert alert-danger alert-dismissible fade show mx-auto mb-4"
          role="alert"
        >
          <strong>{{ error }}</strong>
          <button
            type="button"
            class="close"
            data-dismiss="alert"
            aria-label="Close"
          >
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <!-- Edit Name -->
        <div v-if="nameModalActive" class="edit-modal p-4 modal">
          <i class="fas fa-times fa-lg close" @click="hideNameModal()"></i
          ><br />
          <h1 class="mb-3">Edit Your Name</h1>
          <input
            id="first-name"
            v-model="newFirstName"
            type="text"
            placeholder="New First Name"
          /><br />
          <input
            id="last-name"
            v-model="newLastName"
            type="text"
            placeholder="New Last Name"
          />
          <div
            class="save-button px-3 py-2 mx-auto mt-3"
            @click="updateFirstLastName(newFirstName, newLastName)"
          >
            SAVE
          </div>
        </div>

        <!-- Update Password -->
        <div class="edit-modal p-4 modal" v-if="passwordModalActive">
          <i class="fas fa-times fa-lg close" @click="hidePasswordModal()"></i
          ><br />
          <h1 class="mb-3">Change Your Password</h1>
          <input
            type="text"
            placeholder="Old Password"
            v-model="verificationPassword"
          />
          <input
            type="text"
            name=""
            id=""
            placeholder="New Password"
            v-model="newPassword"
          />
          <div
            class="save-button px-3 py-2 mx-auto mt-3"
            @click="updatePassword(verificationPassword, newPassword)"
          >
            SAVE
          </div>
        </div>

        <!-- Edit Email -->
        <div v-if="emailModalActive" class="edit-modal p-4 modal">
          <i class="fas fa-times fa-lg close" @click="hideEmailModal()"></i>
          <h1 class="mb-3">Edit Your Email</h1>
          <input type="text" placeholder="New Email" v-model="newEmail" />
          <div
            class="save-button px-3 py-2 mx-auto mt-3"
            @click="updateEmail(newEmail)"
          >
            SAVE
          </div>
        </div>

        <!-- Edit Description -->
        <div v-if="descriptionModalActive" class="edit-modal p-4 modal">
          <i
            class="fas fa-times fa-lg close"
            @click="hideDescriptionModal()"
          ></i>
          <h2 class="mb-3">Edit Your Description</h2>
          <textarea
            rows="5"
            cols="50"
            placeholder="Description"
            v-model="newDescription"
          ></textarea>
          <div
            class="save-button px-3 py-2 mx-auto mt-3"
            @click="updateDescription(newDescription)"
          >
            SAVE
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script src="../js/editProfile.js"></script>

<style scoped src="../css/editProfile.css">
</style>