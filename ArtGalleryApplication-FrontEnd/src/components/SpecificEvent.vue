<template>
  <div v-if="!this.errorNotEvent" id="specificEvent">
    <h2>Welcome to the page of the Event: {{this.theEvent.name}}</h2>

    <br>

    <div id="eventDetails">
      <p>Date of the event: {{this.theEvent.eventDate}}</p>
      <p>Starts at {{this.theEvent.startTime}} and ends at {{this.theEvent.endTime}}</p>
      <p>Descrption: {{this.theEvent.eventDescription}}</p>
      <p>This is the image url ---> {{this.theEvent.eventImageUrl}}</p>

      <br>

      <h3>Register to this event:</h3>
      <table>
        <tr>
          <td>Register here:</td>
          <td>
            <button v-bind:disabled="this.isCurrUserRegistered === 'true'" @click="registerToEvent()">Register</button>
          </td>
        </tr>
      </table>
      <p v-if="this.isCurrUserRegistered === 'true'">You are already registered to this event!</p>
      <p v-if="!errorRegister">{{errorRegister}}</p>

    
      <br>

      <h3>List of the event participants:</h3>
        <table v-if="this.theEvent.participants.length !== 0">
          <tr>
            <td>Username</td>
            <td>First name</td>
            <td>Last name</td>
            <td>Email address</td>
          </tr>
          <tr v-for="participant in this.theEvent.participants" v-bind:key="participant.username">
            <td>{{participant.username}}</td>
            <td>{{participant.firstName}}</td>
            <td>{{participant.lastName}}</td>
            <td>{{participant.email}}</td>
          </tr>
        </table>
      <span v-else>No participants registered to this event, yet</span>
    
      <br>

      <!-- for staff only: Delete event and unregister user -->
      <div v-if="this.theCurrentUser.admin == true">
        <h3>Art Gallery Admin tools:</h3>
        <table>
          <tr>
            <td><label>Unregister a user:</label></td>
            <td>
              <label> Select the user: 
                <select v-model="userToUnregister">
                  <option value="">username</option>
                  <option v-for="participant in this.theEvent.participants" v-bind:key="participant.username">{{ participant.username }}</option>
                </select>
              </label>
            </td>
            <td>
              <button v-bind:disabled="!userToUnregister" @click="unregisterUser()">Unregister</button>
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

<style>
  #eventDetails {
    text-align: left;
  }

  table,
  th,
  td {
    border: 1px solid black;
  }
</style>
