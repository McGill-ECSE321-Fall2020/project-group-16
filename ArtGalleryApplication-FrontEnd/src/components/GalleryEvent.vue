<template>
  <div id="galleryEvents">
    <h2>Events</h2>

    <br>
    <br>
    <br>

    <h3>Create new Event</h3>
    <table>
      <tr>
        <td>
          <input type="text" v-model="newEvent.name" placeholder="Event Name">
        </td>
        <td>
          <input type="text" v-model="newEvent.description" placeholder="Event Description">
        </td>
        <td>
          <input type="text" v-model="newEvent.imageUrl" placeholder="Event Image">
        </td>
        <td>
          <input type="date" v-model="newEvent.date" placeholder="YYYY-MM-DD">
        </td>
        <td>
          <input type="time" v-model="newEvent.startTime" placeholder="HH:mm">
        </td>
        <td>
          <input type="time" v-model="newEvent.endTime" placeholder="HH:mm">
        </td>
        <td>
          <button v-bind:disabled="!newEvent.name" @click="createEvent(newEvent.name, newEvent.description, newEvent.imageUrl, newEvent.date, newEvent.startTime, newEvent.endTime)">Create</button>
        </td>
      </tr>
    </table>
    <p>
      <span v-if="errorRequest" style="color:red"> {{errorRequest}} </span>
    </p>




    <br>
    <br>
    <br>

    <h3>List of existing events</h3>
    <table v-if="events.length !== 0">
      <tr>
        <td>Name</td>
        <td>Event description</td>
        <td>Event date</td>
        <td>Event start time</td>
        <td>Event end time</td>
      </tr>
      <tr v-for="event in events">
        <td>{{event.name}}</td>
        <td>{{event.eventDescription}}</td>
        <td>{{event.eventDate}}</td>
        <td>{{event.startTime}}</td>
        <td>{{event.endTime}}</td>
      </tr>
    </table>
    <p v-else>No events created, yet</p>
  </div>
</template>

<script src="./GalleryEvent.js"></script>

<style>
  table, th, td {
    border: 1px solid black;
  }
</style>
