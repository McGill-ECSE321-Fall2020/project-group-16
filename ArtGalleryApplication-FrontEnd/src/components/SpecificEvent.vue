<template>
  <div id="specificEvent">
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
            <input type="text" v-model="toRegisterUsername" placeholder="username">
          </td>
          <td>
            <button v-bind:disabled="!toRegisterUsername" @click="registerToEvent(toRegisterUsername)">Register</button>
          </td>
        </tr>
      </table>

      <br>

      <h3>List of the event participants:</h3>
      <table>
        <tr>
          <td>Username</td>
          <td>First name</td>
          <td>Last name</td>
          <td>Email address</td>
        </tr>
        <tr v-for="participant in this.theEvent.participants">
          <td>{{participant.username}}</td>
          <td>{{participant.firstName}}</td>
          <td>{{participant.lastName}}</td>
          <td>{{participant.email}}</td>
        </tr>
      </table>
    </div>




  </div>
</template>

<script src="./SpecificEvent.js">
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
