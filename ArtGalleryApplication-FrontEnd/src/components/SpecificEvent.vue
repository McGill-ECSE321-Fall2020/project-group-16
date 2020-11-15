<template>
  <div v-if="!this.errorNotEvent" id="specificEvent">
    <h1>{{ this.theEvent.name }}</h1>

    <br />

    <div id="eventDetails">
      <p><strong>Date of the event:</strong> {{ this.theEvent.eventDate }}</p>
      <p>
        Starts at <strong>{{ this.theEvent.startTime }}</strong> and ends at
        <strong>{{ this.theEvent.endTime }}</strong>
      </p>
      <p><strong>Descrption:</strong> {{ this.theEvent.eventDescription }}</p>

      <div
        v-if="this.isCurrUserRegistered !== 'true'"
        @click="registerToEvent()"
        class="button py-2 px-3"
      >
        CLICK TO REGISTER
      </div>
      <strong
        style="color: #006F45"
        v-if="this.isCurrUserRegistered === 'true'"
      >
        You are registered to this event!
      </strong>
      <p v-if="!errorRegister">{{ errorRegister }}</p>

      <br />

      <h1 style="text-align: center">Participants</h1>
      <hr />
      <div v-if="this.theEvent.participants.length !== 0">
        <div class="row">
          <div class="col"><h3>Username</h3></div>
          <div class="col"><h3>First Name</h3></div>

          <div class="col"><h3>Last Name</h3></div>
          <div class="col"><h3>Email Address</h3></div>
        </div>
        <div
          class="row participant py-3"
          v-for="participant in theEvent.participants"
          v-bind:key="participant.username"
          @click="$router.push('/user/' + participant.username)"
        >
          <div class="col">{{ participant.username }}</div>
          <div class="col">{{ participant.firstName }}</div>
          <div class="col">{{ participant.lastName }}</div>
          <div class="col">{{ participant.email }}</div>
        </div>
      </div>
      <span v-else>No participants registered to this event, yet</span>

      <br />

      <!-- for staff only: Delete event and unregister user -->
      <div v-if="this.theCurrentUser.admin == true">
        <h3>Art Gallery Admin tools:</h3>
        <table>
          <tr>
            <td><label>Unregister a user:</label></td>
            <td>
              <label>
                Select the user:
                <select v-model="userToUnregister">
                  <option value="">username</option>
                  <option
                    v-for="participant in this.theEvent.participants"
                    v-bind:key="participant.username"
                  >
                    {{ participant.username }}
                  </option>
                </select>
              </label>
            </td>
            <td>
              <button
                v-bind:disabled="!userToUnregister"
                @click="unregisterUser()"
              >
                Unregister
              </button>
            </td>
          </tr>
          <tr>
            <td>Delete this event:</td>
            <td>Caution this will permanently delete the event</td>
            <td>
              <button @click="deleteEvent()">Delete</button>
            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
  <p v-else>This event was deleted or does not exist, yet</p>
</template>

<script src="../js/SpecificEvent.js">
</script>

<style scoped src="../css/specificEvent.css">
#eventDetails {
  text-align: left;
}
</style>
